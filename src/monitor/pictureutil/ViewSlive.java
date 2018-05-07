package monitor.pictureutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ViewSlive {
	/**
	 * 获取token
	 */
	@SuppressWarnings("null")
	public static String getToken() throws Exception {
//		String userId = "chwx";
//		String pwd = "chwx62115358";
		String userId = "bjwxb";
		String pwd = "bj1234";
		String url = "http://sm.viewslive.cn/api1.2/authorize?" + "appid=23095"
				+ "&appkey=OdeR6YOYpj2f3I25Z0Oo" + "&username=" + userId
				+ "&password=" + pwd;
//		String url = "http://sm.viewslive.cn/api1.2/authorize?" + "appid=23088"
//				+ "&appkey=VVTpgCDsCcFRLP3Zi7Ig" + "&username=" + userId
//				+ "&password=" + pwd;
		// ---------------------------------登录 开始-------------------------

		URL u1 = new URL(url);
		HttpURLConnection c1 = (HttpURLConnection) u1.openConnection();
		// _FakeX509TrustManager.allowAllSSL();

		c1.connect();
		InputStream fi = c1.getInputStream();
		Scanner fs = new Scanner(fi);
		String scsc2 = "";
		while (fs.hasNext()) {
			scsc2 = fs.nextLine();
			System.out.println(scsc2);
		}
		String regex1 = "\\{\"token\":\"([\\d\\D]*?)\",";
		return getContent(regex1, scsc2);
	}
	/**删除任务
	 *@param token    	用户登陆用token
	 *@param idkey		任务的识别用idkey
	 * 
	 */
	public static void deletetask(String token, String idkey) throws Exception {
		String url = "http://sm.viewslive.cn/api1.2/task/delete?token=" + token
				+ "&idkey=" + idkey;
		// ---------------------------------登录 开始-------------------------

		URL u1 = new URL(url);
		HttpURLConnection c1 = (HttpURLConnection) u1.openConnection();
		// _FakeX509TrustManager.allowAllSSL();

		c1.connect();
		InputStream fi = c1.getInputStream();
		Scanner fs = new Scanner(fi);
		String scsc2 = "";
		while (fs.hasNext()) {
			scsc2 = fs.nextLine();
			System.out.println(scsc2);
		}
	}
	
	/**
	 * 
	 * @param urllist 			下载用图片信息 map中包括address(图片链接地址),url(原文所在地址)	 
	 * @param downloadpath  	图片存储地址
	 * */
	public static List<Map<String, String>> downloadpic(
			List<Map<String, String>> urllist, String downloadpath)
			throws Exception {
		int count =0;
		List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
		for (int i = 0; i < urllist.size(); i++) {
			String url = urllist.get(i).get("address");
			// System.out.println(url);
			if (!url.equals("")) {
				// System.out.println(url.indexOf("//"));
				String host = url.substring(url.indexOf("//") + 2);
				host = host.substring(0, host.indexOf("/"));
				// System.out.println(host);
				String imageName = url.substring(url.lastIndexOf("/") + 1, url
						.length());
				try {
					URL u2 = new URL(url);
					HttpURLConnection c2 = (HttpURLConnection) u2
							.openConnection();
					c2
							.addRequestProperty(
									"User-Agent",
									"Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; HUAWEI MT7-TL10 Build/HuaweiMT7-TL10) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/37.0.0.0 MQQBrowser/6.5 Mobile Safari/537.36");
					c2
							.addRequestProperty("Accept",
									"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
					c2.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
					c2.addRequestProperty("Connection", "keep-alive");
					c2.addRequestProperty("Host", host);
					c2.addRequestProperty("Referer", url);
					c2.connect();
					InputStream in = c2.getInputStream();
					String file = downloadpath + imageName;
					// String
					// file=downloadpath+count+imageName.substring(imageName.indexOf("."));
					FileOutputStream fo = new FileOutputStream(new File(file));
					byte[] buf = new byte[1024];
					int length = 0;
					while ((length = in.read(buf, 0, buf.length)) != -1)
						fo.write(buf, 0, length);
					// System.out.println(Thread.currentThread().getName()+url+"下载完成！");
					in.close();
					fo.close();
					Map<String, String> p = new HashMap<String, String>();
					p.put("bdUrl", urllist.get(i).get("address"));
					p.put("ywUrl", urllist.get(i).get("url"));
					if (new File(file).exists()) {
						downloadlist.add(p);
						count++;
						System.out.println(imageName);
						System.out.println(urllist.get(i).get("address"));
						System.out.println(urllist.get(i).get("url"));
					}
				} catch (FileNotFoundException e1) {
					System.out.println("无法下载图片！" + url);
				} catch (IOException e2) {
					System.out.println("发生IO异常！" + url);
				}
			}
		}
		return downloadlist;
	}
	
	/**获取任务列表
	 * 
	 * @param token			登入用识别码 通过gettoken获取		
	 * @return				返回的map包含两个元素 idkey(任务识别编号) name(任务名称)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getTaskList(String token)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String url = "http://sm.viewslive.cn/api1.2/task/list?token=" + token;

		// ---------------------------------登录 开始-------------------------

		URL u1 = new URL(url);
		HttpURLConnection c1 = (HttpURLConnection) u1.openConnection();
		// _FakeX509TrustManager.allowAllSSL();

		c1.connect();
		InputStream fi = c1.getInputStream();
		Scanner fs = new Scanner(fi);
		String scsc2 = "";
		while (fs.hasNext()) {
			scsc2 = fs.nextLine();
			System.out.println(scsc2);
		}
		JSONObject json = JSONObject.fromObject(scsc2);
		String data = json.getString("data");
		JSONArray array = JSONArray.fromObject(data);

		List<ViewBean> a = JSONArray.toList(array, ViewBean.class);
		for (int i = 0; i < a.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			ViewBean tt = a.get(i);
			map.put("idkey", a.get(i).getIdkey());
			map.put("name", a.get(i).getName());
			list.add(map);
		}
		return list;
	}

	/**获取图像姐过
	 * 
	 * @param token				登入识别码,通过gettoken获取
	 * @param idkey				任务的idkey
	 * @param date				输入提取结果的时间
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> getResult(String token,
			String idkey, String date) throws Exception {
		String url = "";
		if (date.equals("")) {
			url = "http://sm.viewslive.cn/api1.2/task/result?token=" + token
					+ "&idkey=" + idkey + "" + "&pagesize=1000";
		} else {
			url = "http://sm.viewslive.cn/api1.2/task/result?token=" + token
					+ "&idkey=" + idkey + "" + "&date=" + date + ""
					+ "&pagesize=1000";
		}
		// ---------------------------------登录 开始-------------------------

		URL u1 = new URL(url);
		HttpURLConnection c1 = (HttpURLConnection) u1.openConnection();
		// _FakeX509TrustManager.allowAllSSL();

		c1.connect();
		InputStream fi = c1.getInputStream();
		Scanner fs = new Scanner(fi);
		String scsc2 = "";
		while (fs.hasNext()) {
			scsc2 = fs.nextLine();
			System.out.println(scsc2);
		}

		String original_pic = "";
		String picurl = "\"url\":\"([\\d\\D]*?)\",";
		String picRegx = "\"original_pic\":\"([\\d\\D]*?)\",";
		String picRetRegx = "\"retweeted_original_pic\":\"([\\d\\D]*?)\",";
		List<Map<String, String>> picList = getContentList(picurl, picRegx,
				scsc2);
		List<Map<String, String>> picRetList = getContentList(picurl,
				picRetRegx, scsc2);
		for (int i = 0; i < picRetList.size(); i++) {
			picList.add(picRetList.get(i));
		}
		return picList;
	}

	/**添加任务(长微博，文字图片)
	 * 			
	 * @param token				登入识别码,通过gettoken获取
	 * @param taskName			任务名称
	 * @param gjc				任务的搜索关键词
	 * @throws Exception
	 */
	public static void addTask(String token, String taskName, String gjc)
			throws Exception {
		String url = "http://sm.viewslive.cn/api1.2/task/add?" + "token="
				+ token + "&taskname=" + URLEncoder.encode(taskName, "utf-8")
				+ "&channeltypes=11" + "&tasktypes=0" + "&taskroles=("
				+ URLEncoder.encode(gjc, "utf-8") + ")";
		// ---------------------------------登录 开始-------------------------

		URL u1 = new URL(url);
		HttpURLConnection c1 = (HttpURLConnection) u1.openConnection();
		// _FakeX509TrustManager.allowAllSSL();

		c1.connect();
		InputStream fi = c1.getInputStream();
		Scanner fs = new Scanner(fi);
		String scsc2 = "";
		while (fs.hasNext()) {
			scsc2 = fs.nextLine();
			System.out.println(scsc2);
		}
	}
	/**添加任务(新浪微博)
	 * 			
	 * @param token				登入识别码,通过gettoken获取
	 * @param taskName			任务名称
	 * @param gjc				任务的搜索关键词
	 * @throws Exception
	 */
	public static void addweiboTask(String token, String taskName, String gjc)
			throws Exception {

		String url = "http://sm.viewslive.cn/api1.2/task/add?" + "token="
				+ token + "&taskname=" + URLEncoder.encode(taskName, "utf-8")
				+ "&channeltypes=1" + "&tasktypes=0" + "&taskroles=("
				+ URLEncoder.encode(gjc, "utf-8") + ")";
		// ---------------------------------登录 开始-------------------------

		URL u1 = new URL(url);
		HttpURLConnection c1 = (HttpURLConnection) u1.openConnection();
		// _FakeX509TrustManager.allowAllSSL();

		c1.connect();
		InputStream fi = c1.getInputStream();
		Scanner fs = new Scanner(fi);
		String scsc2 = "";
		while (fs.hasNext()) {
			scsc2 = fs.nextLine();
			System.out.println(scsc2);
		}

	}

	private static List<Map<String, String>> getContentList(String url,
			String regex, String text) {
		List<Map<String, String>> a = new ArrayList<Map<String, String>>();
		Map<String, String> content = new HashMap<String, String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		Pattern pattern1 = Pattern.compile(url);
		Matcher matcher1 = pattern1.matcher(text);
		System.out.println(text);

		while (matcher.find() && matcher1.find()) {

			content = new HashMap<String, String>();
			content.put("address", matcher.group(1).toString());
			content.put("url", matcher1.group(1).toString());
			try {
				a.add(content);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return a;
	}

	private static String getContent(String regex, String text) {
		String content = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			content = matcher.group(1).toString();
		}
		return content;
	}

	public static void getCWBInfos() {
		try {
			String token = getToken();
			List<Map<String, String>> list = getTaskList(token);
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> a = list.get(i);
				// getResult(token,a.get("idkey"));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addTaskToken() {
		try {
			String token = getToken();
			System.out.println(token);
			addTask(token, "2222", "中国|比较");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String arsg[]) {
		try {
			long starTime = System.currentTimeMillis();
//			getCWBInfos();
//			addTaskToken();
			String token = getToken();
//			deletetask(token,"d8d02YM");
			System.out.println("token="+token);
			List<Map<String, String>> taskList = getTaskList(token);
			for (Map<String, String> map : taskList) {
				System.out.println("id="+map.get("idkey")+"|||name="+map.get("name"));
			}
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("所花时间:"+Time+"ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
