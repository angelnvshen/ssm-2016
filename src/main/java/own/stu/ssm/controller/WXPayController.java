package own.stu.ssm.controller;

import com.github.pagehelper.StringUtil;
import com.tencent.common.util.QRCode.QRCodeUtil;
import com.tencent.common.util.WXPay.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import own.stu.ssm.util.ImageToBase64;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Created by dell on 2016/10/25.
 */
@Controller
@RequestMapping("/WX")
public class WXPayController {

    protected final Logger logger = Logger.getLogger(getClass());

    @ResponseBody
    @RequestMapping("/pay")
    public String weixin_pay(HttpServletResponse response, HttpServletRequest request) throws Exception {
        // 账号信息
        String appid = PayConfigUtil.getAppID();  // appid
        //String appsecret = PayConfigUtil.APP_SECRET; // appsecret
        String mch_id = PayConfigUtil.getMchID(); // 商业号
        String key = PayConfigUtil.getKey(); // key

        String currTime = PayCommonUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = PayCommonUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;

        String order_price = "1"; // 价格   注意：价格的单位是分
        String body = "goodssssss";   // 商品名称
        String out_trade_no = "113382"; // 订单号

        // 获取发起电脑 ip
        String spbill_create_ip = PayConfigUtil.getIp();
        // 回调接口
        String notify_url = PayConfigUtil.getNotifyUrl();
        String trade_type = "NATIVE";

        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", order_price);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);

        String sign = PayCommonUtil.createSign("UTF-8", packageParams, key);
        packageParams.put("sign", sign);

        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        System.out.println(requestXML);

        String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);

        Map map = XMLUtil.doXMLParse(resXml);
        if(map != null){
            if("SUCCESS".equals(map.get("return_code")) && StringUtil.isNotEmpty((String)map.get("code_url"))){
                String urlCode = (String) map.get("code_url");
                //GenerateQrCodeUtil.encodeQrcode(urlCode, response);


                String logoPath = request.getServletContext().getRealPath("/") + "/static/image/timg.jpg";

                BufferedImage image = QRCodeUtil.createImage(urlCode, logoPath, true);

                return ImageToBase64.getImageBinary(image);
            }else
                return "return_code : " + map.get("return_code") + " , return_msg : " + map.get("return_msg");
        }

        return "FAIL";
    }

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
}
