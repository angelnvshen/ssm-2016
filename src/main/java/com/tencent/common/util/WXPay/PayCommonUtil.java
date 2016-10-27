package com.tencent.common.util.WXPay;

import org.apache.log4j.Logger;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class PayCommonUtil {

    protected final Logger logger = Logger.getLogger(getClass());
    /** 
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。 
     * @return boolean 
     */  
    public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {
        StringBuffer sb = new StringBuffer();  
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();  
            String v = (String)entry.getValue();  
            if(!"sign".equals(k) && null != v && !"".equals(v)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
          
        sb.append("key=" + API_KEY);  
          
        //算出摘要  
        String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();  
        String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();  
          
        //System.out.println(tenpaySign + "    " + mysign);  
        return tenpaySign.equals(mysign);  
    }  
  
    /** 
     * @author 
     * @date 2016-4-22 
     * @Description：sign签名 
     * @param characterEncoding 
     *            编码格式 
     * @param packageParams
     *            请求参数 
     * @return 
     */  
    public static String createSign(String characterEncoding, Map<String, Object> packageParams, String API_KEY) {
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:packageParams.entrySet()){
            //if(entry.getValue()!="" ){
            if (null != entry.getValue() && !"".equals(entry.getValue()) && !"sign".equals(entry.getKey()) && !"key".equals(entry.getKey())) {
                    list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + API_KEY;
        result = MD5Util.MD5Encode(result, characterEncoding).toUpperCase();
        return result;
    }  
  
    /** 
     * @author 
     * @date 2016-4-22 
     * @Description：将请求参数转换为xml格式的string 
     * @param parameters 
     *            请求参数 
     * @return 
     */  
    public static String getRequestXml(SortedMap<Object, Object> parameters) {  
        StringBuffer sb = new StringBuffer();  
        sb.append("<xml>");  
        Set es = parameters.entrySet();  
        Iterator it = es.iterator();  
        while (it.hasNext()) {  
            Map.Entry entry = (Map.Entry) it.next();  
            String k = (String) entry.getKey();  
            String v = (String) entry.getValue();  
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {  
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");  
            } else {  
                sb.append("<" + k + ">" + v + "</" + k + ">");  
            }  
        }  
        sb.append("</xml>");  
        return sb.toString();  
    }  
  
    /** 
     * 取出一个指定长度大小的随机正整数. 
     *  
     * @param length 
     *            int 设定所取出随机数的长度。length小于11 
     * @return int 返回生成的随机数。 
     */  
    public static int buildRandom(int length) {  
        int num = 1;  
        double random = Math.random();  
        if (random < 0.1) {  
            random = random + 0.1;  
        }  
        for (int i = 0; i < length; i++) {  
            num = num * 10;  
        }  
        return (int) ((random * num));  
    }

    //随机字符串，不长于32 位 - 统一一个
    public static String geneStr(){

        String currTime = PayCommonUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = PayCommonUtil.buildRandom(4) + "";
        String result = strTime + strRandom;
        return result;
    }

    /** 
     * 获取当前时间 yyyyMMddHHmmss 
     *  
     * @return String 
     */  
    public static String getCurrTime() {  
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);  
        return s;  
    }

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取本机Ip
     *
     *  通过 获取系统所有的networkInterface网络接口 然后遍历 每个网络下的InterfaceAddress组。
     *  获得符合 <code>InetAddress instanceof Inet4Address</code> 条件的一个IpV4地址
     * @return
     */
    public static String localIp() throws Exception{
        String ip = null;
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                for (InterfaceAddress add : InterfaceAddress) {
                    InetAddress Ip = add.getAddress();
                    if (Ip != null && Ip instanceof Inet4Address) {
                        ip = Ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            throw new Exception(e.getMessage());
        }
        return ip;
    }
}  