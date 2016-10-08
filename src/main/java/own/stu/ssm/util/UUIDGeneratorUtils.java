package own.stu.ssm.util;

public class UUIDGeneratorUtils {

    public static String uuid32(){
        return java.util.UUID.randomUUID().toString().replace("-","");
    }
}