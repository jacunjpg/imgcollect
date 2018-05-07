package monitor.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

public class StringUtil {
	
	/**
	 * 字符串去掉重复 1,2,3,2,3,4,5,5
	 * @param str
	 * @return
	 */
	public static String deleteRepeat(String str){
		if(str==null) return str;
		String ag[] = str.split(",");
		Set<String> set = new HashSet<String>();
		for(int i=0;i<ag.length;i++){
			set.add(ag[i]);
		}
		StringBuffer sb = new StringBuffer();
		for(String r : set) {
		      sb.append(r);
		      sb.append(",");
		}
		return sb.toString().substring(0, sb.toString().length()-1);
	}
	/**
	 * 字符串首字母大写
	 * @param 字符串
	 * @return 首字母大写的字符串
	 */
	public static String toFirstUpperCase(String str) {
		if(str == null || str.length() < 1) {
			return "";
		}
		String start = str.substring(0,1).toUpperCase();
		String end = str.substring(1, str.length());
		return start + end;
	}
	/**
	 * 类型转换
	 */
	public static Object castString(String value, Class<?> cls) {
		String name = cls.getSimpleName();
		Object cast = value;
		if(name.equalsIgnoreCase("Integer")) {
			cast = Integer.parseInt(cast.toString().equals("")?"0":cast.toString());
		}
		if(name.equalsIgnoreCase("int")) {
			cast = Integer.parseInt(cast.toString().equals("")?"0":cast.toString());
		}
		if(name.equalsIgnoreCase("Long")) {
			cast = Long.parseLong(cast.toString());
		}
		if(name.equalsIgnoreCase("Short")) {
			cast = Short.parseShort(cast.toString());
		}
		if(name.equalsIgnoreCase("Float")) {
			cast = Float.parseFloat(cast.toString());
		}
		if(name.equalsIgnoreCase("Double")) {
			cast = Double.parseDouble(cast.toString());
		}
		if(name.equalsIgnoreCase("Boolean")) {
			cast = Boolean.parseBoolean(cast.toString());
		}
		return cast;
	}
	/**
	 * 过滤掉字符串中HTML标签
	 * @param input
	 * @return
	 */
	public static String filterHTMl(String input){
		if(input == null) return input;
		String str = input.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "");
		return str;
	}
	public static String toUTF8(String value){
		if(value!=null){
			try {
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return value;
	}
	public static String encodeURI(String value){
		if(value!=null){
			try {
				value =  URLDecoder.decode(value,"utf-8");;
			} catch (UnsupportedEncodingException e) {
			}
		}
		return value;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = deleteRepeat("1,2,3,2,3,4,5,5");
		System.out.println(s);
	}

}
