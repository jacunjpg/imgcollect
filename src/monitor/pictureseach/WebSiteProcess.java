package monitor.pictureseach;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.RandomUtils;

import monitor.util.DateUtil;

/**
 * 网页内容解析及操作
 * 
 * <p>Title WebSiteProcess</p>
 * <p>Description </p>
 * @author chwx</p>
 * @date 2017-3-30 </p>
 */
public class WebSiteProcess {

	/*ss
	 * 匹配图片名称和url
	 */
	public static List<Map<String, String>> convertMap(
			List<Map<String, String>> urlList,
			List<Map<String, String>> nameList) {

		System.out.println("urlList:"+urlList.size());
		System.out.println("nameList:"+nameList.size());
		List<Map<String, String>> nameUrlList = new ArrayList<Map<String, String>>();
		for (Map<String, String> nameMap : nameList) {
			String oldName = nameMap.get("oldName");
			String newName = nameMap.get("newName");
			for (Map<String, String> urlMap : urlList) {
				Map<String, String> nameUrlMap = new HashMap<String, String>();
				String newUrl = urlMap.get("bdUrl");
				String oldUrl = urlMap.get("ywUrl");
				// 判断
				if (newUrl.indexOf(oldName) != -1) {
//					System.out.println(oldName);
//					System.out.println(newUrl);
//					System.out.println(oldUrl);
					nameUrlMap.put("newName", newName);
					nameUrlMap.put("oldUrl", oldUrl);
					nameUrlList.add(nameUrlMap);
				}
			}
		}

		return nameUrlList;
	}

	/**
	 * 访问url返回url的html代码
	 */
	public static String openUrl(String currentUrl, String charset) {
		InputStream is = null;
		BufferedReader br = null;
		URL url;
		StringBuffer html = new StringBuffer();
		try {
			url = new URL(currentUrl);
			URLConnection conn = url.openConnection();
			conn.setReadTimeout(5000);
			conn.connect();
			is = conn.getInputStream();
			br = new BufferedReader(new InputStreamReader(is, charset));
			String str;
			while (null != (str = br.readLine())) {
				html.append(str).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return html.toString();
	}

	public static List<String> getContent(String regex, String text) {
		List<String> a = new ArrayList<String>();
		String content = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			content = matcher.group(1).toString();
			try {
				// BaiduPicScript.getUrlTrue(content)
				a.add(content);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return a;
	}

	public static String getUrl(String regex, String text) {
		String picUrl = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			picUrl = matcher.group(1).toString();

		}

		return picUrl;
	}

	/**
	 * 为图片换名 换名方式为 cbkid(a##,b##)_系统时间戳_自增数字。后缀
	 * 
	 * @param files
	 * @return
	 */
	public static List<Map<String, String>> changeNameList(File[] files,
			String cbkid) {
		List<Map<String, String>> listName = new ArrayList<Map<String, String>>();
		File[] returnfile = new File[files.length];
		for (int i = 0; i < files.length; i++) {
			Map<String, String> nameMap = new HashMap<String, String>();
			int t = files[i].getName().lastIndexOf(".");
			String name="";
			if(t>0){
				name = files[i].getName().substring(t);
			}else{
				name = ".jpg";
			}
			String newName = cbkid + "_" + DateUtil.getCurrentTimeMillis()
					+ "_" + (i+1) + name;
			String oldName = files[i].getName();
			returnfile[i] = new File(files[i].getParent(), newName);
			files[i].renameTo(returnfile[i]);
			// System.out.println("新名称："+newName);
			// System.out.println("旧名称："+oldName);
			// nameMap.put(oldName,newName);
			nameMap.put("oldName", oldName);
			nameMap.put("newName", newName);
//			System.out.println("旧名称："+files[i].getPath());
//			System.out.println("新名称："+returnfile[i].getPath());
			newName="";
			listName.add(nameMap);
		}
		return listName;
	}
}
