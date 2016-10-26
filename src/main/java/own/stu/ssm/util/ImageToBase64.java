package own.stu.ssm.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageToBase64 {

    public static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    public static BASE64Decoder decoder = new sun.misc.BASE64Decoder();
    public static void main(String[] args) {
        String path = "C:\\Users\\dell\\Desktop\\images.jpg";
        String destPath = "C:\\Users\\dell\\Desktop\\xx.jpg";
        System.out.println(getImageBinary(path));
        base64StringToImage(getImageBinary(path),destPath);
    }

    /**
     * 编码图片到Base64，文件请放在C盘根目录下，名字必须为wjp.png
     * @param imgPath 图片的路径
     * @return
     */
    public static String getImageBinary(String imgPath){
        File f = new File(imgPath);//("C:/wjp.png");
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            return getImageBinary(bi);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    } 
    
    public static String getImageBinary(BufferedImage bi) throws Exception {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();

            return encoder.encodeBuffer(bytes).trim();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
    * 解码Base64成为图片，保存在D盘根目录下，名字为wjp.png
    * @param base64String   //图片的base64格式的字符串
    * @param destPath       //保存文件的路径
    */
    public static void base64StringToImage(String base64String, String destPath){
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 =ImageIO.read(bais);
            File w2 = new File(destPath);//("d://wjp.png");
            ImageIO.write(bi1, "jpg", w2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}