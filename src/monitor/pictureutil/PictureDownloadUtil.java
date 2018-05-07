package monitor.pictureutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 圖片下載
 */
public class PictureDownloadUtil {

	
	public static List<Map<String, String>> newdownloadpic(
			List<Map<String, String>> urllist, String downloadpath)
			throws Exception {
		downloadpath = downloadpath + "\\";
		File path = new File(downloadpath);
		if (!path.exists())
			path.mkdirs();
		List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
		int count=1 ;
		for (int i = 0; i < urllist.size(); i++) {
			String url = urllist.get(i).get("bdUrl");
//			System.out.println(url);
			if (!url.equals("")) {
//				System.out.println(url+"实际下载数量："+(count++));
				String host = url.substring(url.indexOf("//") + 2);
				host = host.substring(0, host.indexOf("/"));
				String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
				try {
					URL u2 = new URL(url);
					HttpURLConnection c2 = (HttpURLConnection) u2.openConnection();
					c2.addRequestProperty(
									"User-Agent",
									"Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; HUAWEI MT7-TL10 Build/HuaweiMT7-TL10) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/37.0.0.0 MQQBrowser/6.5 Mobile Safari/537.36");
					c2.addRequestProperty("Accept",
									"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
					c2.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
					c2.addRequestProperty("Connection", "keep-alive");
					c2.addRequestProperty("Host", host);
					c2.addRequestProperty("Referer", url);
					c2.connect();
					InputStream in = c2.getInputStream();
					String file = downloadpath + imageName;
					
					Map<String, String> p = new HashMap<String, String>();
					p.put("bdUrl", url);
					p.put("ywUrl", urllist.get(i).get("ywUrl"));
//					System.out.println(new File(file).exists());
					if (!new File(file).exists()) {
						downloadlist.add(p);
//						System.out.println(p+"数量："+(count));
//						System.out.println(imageName);
//						System.out.println(urllist.get(i).get("address"));
//						System.out.println(urllist.get(i).get("url"));
					}
					FileOutputStream fo = new FileOutputStream(new File(file));
					byte[] buf = new byte[1024];
					int length = 0;
					while ((length = in.read(buf, 0, buf.length)) != -1)
						fo.write(buf, 0, length);
					in.close();
					fo.close();
					
				} catch (FileNotFoundException e1) {
					System.out.println("无法下载图片！" + url);
				} catch (IOException e2) {
					System.out.println("发生IO异常！" + url);
				}
			}
		}
		return downloadlist;
	}
	
	public static List<Map<String, String>> downloadpic(
			List<Map<String, String>> urllist, String downloadpath)
			throws Exception {
		downloadpath = downloadpath + "\\";
		File path = new File(downloadpath);
		if (!path.exists())
			path.mkdirs();
		List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
		int count=1 ;
		for (int i = 0; i < urllist.size(); i++) {
			String url = urllist.get(i).get("bdUrl");
//			System.out.println(url);
			if (!url.equals("")) {
//				System.out.println(url+"实际下载数量："+(count++));
				String host = url.substring(url.indexOf("//") + 2);
				host = host.substring(0, host.indexOf("/"));
				String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
				try {
					URL u2 = new URL(url);
					HttpURLConnection c2 = (HttpURLConnection) u2.openConnection();
					c2.addRequestProperty(
									"User-Agent",
									"Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; HUAWEI MT7-TL10 Build/HuaweiMT7-TL10) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/37.0.0.0 MQQBrowser/6.5 Mobile Safari/537.36");
					c2.addRequestProperty("Accept",
									"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
					c2.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
					c2.addRequestProperty("Connection", "keep-alive");
					c2.addRequestProperty("Host", host);
					c2.addRequestProperty("Referer", url);
					c2.connect();
					InputStream in = c2.getInputStream();
					String file = downloadpath + imageName;
					
					Map<String, String> p = new HashMap<String, String>();
					p.put("bdUrl", url);
					p.put("ywUrl", urllist.get(i).get("ywUrl"));
//					System.out.println(new File(file).exists());
					if (!new File(file).exists()) {
						downloadlist.add(p);
//						System.out.println(p+"数量："+(count));
//						System.out.println(imageName);
//						System.out.println(urllist.get(i).get("address"));
//						System.out.println(urllist.get(i).get("url"));
					}
					FileOutputStream fo = new FileOutputStream(new File(file));
					byte[] buf = new byte[1024];
					int length = 0;
					while ((length = in.read(buf, 0, buf.length)) != -1)
						fo.write(buf, 0, length);
					in.close();
					fo.close();
					
				} catch (FileNotFoundException e1) {
					System.out.println("无法下载图片！" + url);
				} catch (IOException e2) {
					System.out.println("发生IO异常！" + url);
				}
			}
		}
		return downloadlist;
	}
}
