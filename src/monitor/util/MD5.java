package monitor.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    
    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public MD5() {
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    @SuppressWarnings("unused")
	private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
    //数据库用户名密码加密算法
    public static String md5JiaMi(String src) {
        char psw = '*';
        char srcArray[] = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            srcArray[i] = (char) (srcArray[i] ^ psw);
        }
        return new String(srcArray);
    }
    //数据库用户名密码解密算法
    public static String md5JieMi(String src) {
        char psw = '*';
        char srcArray[] = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            srcArray[i] = (char) (srcArray[i] ^ psw);
        }
        return new String(srcArray);
    }
    @SuppressWarnings("static-access")
	public static void main(String[] args) {
        MD5 getMD5 = new MD5();
        String pwd = "root";
        System.out.println("MD5 32位 小写: 密码:"+pwd+",MD5:"+getMD5.GetMD5Code(pwd));
        // u:fjx p:fengjinxin
        System.out.println(getMD5.md5JiaMi("123456"));

    }
}
