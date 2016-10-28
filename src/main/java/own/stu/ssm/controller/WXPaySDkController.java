package own.stu.ssm.controller;

import com.github.pagehelper.StringUtil;
import com.tencent.common.util.QRCode.QRCodeUtil;
import com.tencent.common.util.WXPay.*;
import com.tencent.common.util.WXPay.Util;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import own.stu.ssm.util.ImageToBase64;
import sdk.tencent.common.*;
import sdk.tencent.protocol.pay_protocol.ScanPayReqData;
import sdk.tencent.protocol.pay_protocol.ScanPayResData;
import sdk.tencent.protocol.pay_query_protocol.ScanPayQueryReqData;
import sdk.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import sdk.tencent.service.ScanPayService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * Created by dell on 2016/10/25.
 */
@Controller
@RequestMapping("/WXSDK")
public class WXPaySDkController {

    protected final Logger logger = Logger.getLogger(getClass());

    //每次调用订单查询API时的等待时间，因为当出现支付失败的时候，如果马上发起查询不一定就能查到结果，所以这里建议先等待一定时间再发起查询

    private int waitingTimeBeforePayQueryServiceInvoked = 5000;
    
    @ResponseBody
    @RequestMapping("/pay")
    public Msg weixinPay(ScanPayReqData scanPayReqData, HttpServletRequest request) throws Exception {

        //商品名称
        if (StringUtil.isEmpty(scanPayReqData.getBody())) {
            return new Msg("0", "body字段不能为空.");
        }

        //商品价格
        if (scanPayReqData.getTotal_fee() <= 0) {
            return new Msg("0", "total_fee字段应为正整数.");
        }
        //订单号
        if (StringUtil.isEmpty(scanPayReqData.getOut_trade_no())) {
            return new Msg("0", "out_trade_no字段不能为空.");
        }

        //TODO 根据订单生成流水号，插入对应的表

        //构造请求“被扫支付API”所需要提交的数据
        setInfo(scanPayReqData, scanPayReqData.getBody(), scanPayReqData.getOut_trade_no(), scanPayReqData.getTotal_fee());

        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());
        System.out.println(request.getContextPath());
        System.out.println(request.getPathInfo());

        return weixin_pay(scanPayReqData, request);
    }

    public Msg weixin_pay(ScanPayReqData scanPayReqData, HttpServletRequest request) throws Exception {

        ScanPayService scanPayService = new ScanPayService();
        String payServiceResponseString = scanPayService.request(scanPayReqData);

        //将从API返回的XML数据映射到Java对象
        ScanPayResData scanPayResData = (ScanPayResData) Util.getObjectFromXML(payServiceResponseString, ScanPayResData.class);

        String msg = "";
        if (scanPayResData == null || scanPayResData.getReturn_code() == null) {
            
            logger.error(msg = "【支付失败】支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
            return new Msg("0", msg);
        }

        if (scanPayResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            logger.error(msg = "【支付失败】支付API系统返回失败，请检测Post给API的数据是否规范合法");
            return new Msg("0", msg);
        } else {
            logger.info("支付API系统成功返回数据");
            //--------------------------------------------------------------------
            //收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
            //--------------------------------------------------------------------
            if (!Signature.checkIsSignValidFromResponseString(payServiceResponseString)) {
                logger.error(msg = "【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
                return new Msg("0", msg);
            }

            //获取错误码
            String errorCode = scanPayResData.getErr_code();
            //获取错误描述
            String errorCodeDes = scanPayResData.getErr_code_des();

            if (scanPayResData.getResult_code().equals("SUCCESS")) {

                logger.info("返回预支付链接成功");
                logger.info("预支付链接 ： " + scanPayResData.getCode_url());
                String result = generateQRCodeBaseStr(scanPayResData.getCode_url(), request);
                return new Msg("1", result);
            } else {

                //出现业务错误
                logger.info("业务返回失败");
                logger.info("err_code:" + errorCode);
                logger.info("err_code_des:" + errorCodeDes);
                return new Msg("1", errorCodeDes);
            }
        }
    }

    /**
     * 将微信返回的预支付链接生成二维码，并将二维码转为base64格式的字符串
     * @return
     */
    private String generateQRCodeBaseStr(String code_url, HttpServletRequest request) throws Exception {

        String logoPath = request.getServletContext().getRealPath("/") + "/static/image/timg.jpg";
        logger.info("嵌入二维码的图片地址：" + logoPath);
        System.out.println(request.getContextPath());
        System.out.println(request.getServletContext().getRealPath("/"));

        BufferedImage image = QRCodeUtil.createImage(code_url, logoPath, true);

        return ImageToBase64.getImageBinary(image);
    }

    class Msg{
        private String state;
        private String message;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Msg(String state, String message) {
            this.state = state;
            this.message = message;
        }
    }

    /**
     * 微信回调异步通知
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/notify")
    public void weixin_notify(HttpServletRequest request,HttpServletResponse response) throws Exception{

        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XMLUtil.doXMLParse(sb.toString());

        //过滤空 设置 TreeMap
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        // 账号信息
        String key = PayConfigUtil.getKey(); // key

        logger.info(packageParams);
        //判断签名是否正确
        if(PayCommonUtil.isTenpaySign("UTF-8", packageParams,key)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String)packageParams.get("mch_id");
                String openid = (String)packageParams.get("openid");
                String is_subscribe = (String)packageParams.get("is_subscribe");
                String out_trade_no = (String)packageParams.get("out_trade_no");

                String total_fee = (String)packageParams.get("total_fee");

                logger.info("mch_id:"+mch_id);
                logger.info("openid:"+openid);
                logger.info("is_subscribe:"+is_subscribe);
                logger.info("out_trade_no:"+out_trade_no);
                logger.info("total_fee:"+total_fee);

                //////////执行自己的业务逻辑////////////////

                logger.info("支付成功");
                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

            } else {
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            logger.info("通知签名验证失败");
        }
    }

    /**
     * 进行一次支付订单查询操作
     *
     * @param outTradeNo    商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @return 该订单是否支付成功
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/payQuery")
    public boolean doOnePayQuery(String outTradeNo) throws Exception {
        sleep(waitingTimeBeforePayQueryServiceInvoked);//等待一定时间再进行查询，避免状态还没来得及被更新

        String payQueryServiceResponseString;

        ScanPayQueryReqData scanPayQueryReqData = new ScanPayQueryReqData("",outTradeNo);


        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(scanPayQueryReqData);

        Util.log("API，POST过去的数据是：");
        Util.log(postDataXML);
        payQueryServiceResponseString = HttpUtil.postData(PayConfigUtil.PAY_QUERY_API, postDataXML);

         logger.info("支付订单查询API返回的数据如下：");
         logger.info(payQueryServiceResponseString);

        //将从API返回的XML数据映射到Java对象
        ScanPayQueryResData scanPayQueryResData = (ScanPayQueryResData) Util.getObjectFromXML(payQueryServiceResponseString, ScanPayQueryResData.class);
        if (scanPayQueryResData == null || scanPayQueryResData.getReturn_code() == null) {
             logger.info("支付订单查询请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
            return false;
        }

        if (scanPayQueryResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
             logger.info("支付订单查询API系统返回失败，失败信息为：" + scanPayQueryResData.getReturn_msg());
            return false;
        } else {
            if (scanPayQueryResData.getResult_code().equals("SUCCESS")) {//业务层成功
                if (scanPayQueryResData.getTrade_state().equals("SUCCESS")) {
                    //表示查单结果为“支付成功”
                     logger.info("查询到订单支付成功");
                    return true;
                } else {
                    //支付不成功
                     logger.info("查询到订单支付不成功");
                    return false;
                }
            } else {
                 logger.info("查询出错，错误码：" + scanPayQueryResData.getErr_code() + "     错误信息：" + scanPayQueryResData.getErr_code_des());
                return false;
            }
        }
    }


    private void setInfo(ScanPayReqData scanPayReqData, String body, String outTradeNo, int totalFee) throws Exception {
        //微信分配的公众号ID（开通公众号之后可以获取到）
        scanPayReqData.setAppid(Configure.getAppid());

        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        scanPayReqData.setMch_id(Configure.getMchid());

        //要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
        scanPayReqData.setBody(body);

        //商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
        scanPayReqData.setOut_trade_no(outTradeNo);

        //订单总金额，单位为“分”，只能整数
        scanPayReqData.setTotal_fee(totalFee);

        //订单生成的机器IP
        scanPayReqData.setSpbill_create_ip(sdk.tencent.common.Util.localIp());

        //随机字符串，不长于32 位
        scanPayReqData.setNonce_str(sdk.tencent.common.Util.geneStr());

        //回调路径
        //scanPayReqData.setNotify_url("http://" + getAccessDomain() + "/notify"));

        //交易类型
        String trade_type = "NATIVE"; //扫码支付
        scanPayReqData.setTrade_type(trade_type);

        //根据API给的签名规则进行签名
        String sign = Signature.getSign(scanPayReqData.toMap());
        scanPayReqData.setSign(sign);//把签名数据设置到Sign这个属性中

    }
}
