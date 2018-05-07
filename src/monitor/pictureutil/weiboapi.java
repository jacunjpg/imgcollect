//package monitor.pictureutil;
//
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//
//public class weiboapi {
//
//	
//	public static List<Map<String, String>> getTaskDetailByIdkey(String q,String appkey) throws Exception {
//		String url = "";
//		q = URLEncoder.encode(q, "utf-8");
//			url = "http://sm.viewslive.cn/api1.2/task/result?source=" + appkey
//					+ "&q=" + q + ""+"&haspic=1";
//		
//		URL u1 = new URL(url);
//		HttpURLConnection c1 = (HttpURLConnection) u1.openConnection();
//		c1.connect();
//		InputStream fi = c1.getInputStream();
//		Scanner fs = new Scanner(fi);
//		String scsc2 = "";
//		while (fs.hasNext()) {
//			scsc2 = fs.nextLine();
//			System.out.println(scsc2);
//
//		}
//		
//		//解析json
//		String picurl = "\"url\":\"([\\d\\D]*?)\",";
//		String picRegx = "\"original_pic\":\"([\\d\\D]*?)\",";
//		String picRetRegx = "\"retweeted_original_pic\":\"([\\d\\D]*?)\",";
//		List<Map<String, String>> picList = getContentList(picurl, picRegx,
//				scsc2);
//		List<Map<String, String>> picRetList = getContentList(picurl,
//				picRetRegx, scsc2);
//		picList.addAll(picRetList);
////		for (int i = 0; i < picRetList.size(); i++) {
////			picList.add(picRetList.get(i));
////		}
//		return picList;
//		
//	}
//}
