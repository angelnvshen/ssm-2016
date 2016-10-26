package own.stu.ssm.controller;

import com.tencent.common.util.WXPay.GenerateQrCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by dell on 2016/10/25.
 */
@RequestMapping("/QR")
@Controller
public class QRGenController {

    /**
     * 生成二维码图片并直接以流的形式输出到页面
     * @param code_url
     * @param response
     */
    @RequestMapping("/img")
    //@ResponseBody
    public void getQRCode(String code_url,HttpServletResponse response){
        GenerateQrCodeUtil.encodeQrcode(code_url, response);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showView(){
        return "QRcode";
    }
}
