package own.stu.ssm.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/9/20.
 */
public class CrypographyUtil {

    /**
     * base64加密
     * @param source
     * @return
     */
    public static String encBase64(String source){
        return Base64.encodeToString(source.getBytes());
    }

    /**
     * base64解密
     * @param source
     * @return
     */
    public static String decBase64(String source){
        return Base64.decodeToString(source);
    }

    /**
     * md5加密
     * @param source
     * @return
     */
    public static String encMd5(String source){
        return new Md5Hash(source, "own").toString();
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("3");
        list.add("2");
        list.add("4");
        System.out.println(list);
    }
}
