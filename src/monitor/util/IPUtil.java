package monitor.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IPUtil {
	/**
	 * 根据IP查询所在城市
	 * @param ip String IP地址
	 * @return value String 所在城市
	 * */
	public static String getArea(String ip){
		String value = "未知";
		String soupData = "";
		if (ip != null && !ip.equals("") && !ip.equals("0:0:0:0:0:0:0:1")) {
			String url = "http://ip138.com/ips1388.asp?ip="+ip+"&action=2";//IP转换连接   IP地址库
			try {
				//System.out.println(url);
				Document doc2 =Jsoup.connect(url).get();
				Element masthead =doc2.select(".ul1").first(); //找到class为toc的元素
				Elements content =masthead.getElementsByTag("li"); //找到a属性的元素集合
				for (Element link : content) {
					soupData =link.text();
					break;
				}
			} catch (IOException e) {
				//e.printStackTrace();
				value = "未知";
			}
			if(soupData.length()>6){
				soupData = soupData.substring(6);
				String values[] = soupData.split(" ");
				value = values[0];
			}
		}
		
		return value;		
	}
	public static void main(String[] args) {
//		java.util.Scanner in = new java.util.Scanner(System.in);
//		String ip = "";
//		String city = "";
//		while (in.hasNext()) {
//			ip = in.next();
//			city = getArea(ip);
//			System.out.println(ip + "所在的城市: " + city);
//		}
		String url = "http://reply.autohome.com.cn/Articlecomment.aspx?articleid=890014&page=1&order=0";
		Document doc2;
		try {
			doc2 = Jsoup.connect(url).get();
			System.out.println(doc2.toString().substring(33000, 48000));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
