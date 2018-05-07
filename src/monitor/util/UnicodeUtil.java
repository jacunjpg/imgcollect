package monitor.util;

public class UnicodeUtil {
	//把gbk转化成utf8
	public static String gbk2Utf8(String str) throws Throwable {
		return new String(getUTF8BytesFromGBKString(str), "UTF-8");
	}
	public static byte[] getUTF8BytesFromGBKString(String gbkStr) {  
	        int n = gbkStr.length();  
	        byte[] utfBytes = new byte[3 * n];  
	        int k = 0;  
	        for (int i = 0; i < n; i++) {  
	            int m = gbkStr.charAt(i);  
	            if (m < 128 && m >= 0) {  
	                utfBytes[k++] = (byte) m;  
	                continue;  
	            }  
	            utfBytes[k++] = (byte) (0xe0 | (m >> 12));  
	            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));  
	            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));  
	        }  
	        if (k < utfBytes.length) {  
	            byte[] tmp = new byte[k];  
	            System.arraycopy(utfBytes, 0, tmp, 0, k);  
	            return tmp;  
	        }  
	        return utfBytes;
	}  
}
