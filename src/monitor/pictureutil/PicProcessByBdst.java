package monitor.pictureutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import monitor.util.DateUtil;

/**
 * 百度识图图片处理 Title PicProcessByBdst Description
 * 
 * @author jacun
 * @date 2017-4-13上午10:35:54
 */
public class PicProcessByBdst {

	/**
	 * 
	 * 
	 * Title List<Map<String,String>> Description 图片下载
	 * 
	 * @author jacun
	 * @date 2017-4-13上午10:36:43
	 * @param downloadPath
	 * @param query
	 * @param page
	 * @return
	 */
	public static List<Map<String, String>> downloadPic(String query,
			String add, int page, String nameid, String zip) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		// 获取百度图片地址和源文件地址
		List<Map<String, String>> urlList = new ArrayList<Map<String, String>>();
		// 新闻正文正则
		String regexPicUrl = "\"MiddleThumbnailImageUrl\":\"([\\d\\D]*?)\",";
		String regexFromUrl = "\"gpg\":\"([\\d\\D]*?)\",";
		String regexHost = "\"ThumbnailDomain\":\"([\\d\\D]*?)\",";

		// 获取网页源代码
		String regexString = "\\{\"ThumbnailContentSign\"([\\d\\D]*?)\"dataFrom\":\"image\"\\}";
		String downloadPath = add + nameid + "\\";
		File path = new File(downloadPath);
		if (!path.exists())
			path.mkdirs();

		String paramsSign;
		String qureySign = "";
		try {
			paramsSign = "http://image.baidu.com/n/pc_search?"
					+ "queryImageUrl=" + URLEncoder.encode(query, "utf-8")
					+ "&fm=result_camera" + "&uptype=paste" + "&drag=1";
			String htmlSign = WebSiteProcess.openUrl(paramsSign, "utf-8");

			String regexSign = "'querySign': '([\\d\\D]*?)',";
			qureySign = WebSiteProcess.getUrl(regexSign, htmlSign);

			System.out.println(qureySign);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try {
			for (int i = 0; i < page; i++) {
				String params = "http://image.baidu.com/n/similar?"
						+ "queryImageUrl=" + URLEncoder.encode(query, "utf-8")
						+ "&querySign=" + qureySign + "&word=&t=1479452407697"
						+ "&rn=30" + "&sort=" + "&fr=pc" + "&pn=" + i * 30;
				String html = WebSiteProcess.openUrl(params, "utf-8");
				// 获取图片URL
				List<String> listUrl = WebSiteProcess.getContent(regexString,
						html);

				for (int j = 0; j < listUrl.size(); j++) {
					System.out.println(listUrl.get(j));
					Map<String, String> t = new HashMap<String, String>();
					String bdUrl = WebSiteProcess.getUrl(regexPicUrl,
							listUrl.get(j));
					String ywUrl = "";

					ywUrl = WebSiteProcess.getUrl(regexFromUrl, listUrl.get(j));
					String host = WebSiteProcess.getUrl(regexHost,
							listUrl.get(j));
					bdUrl = bdUrl.replaceAll("\\\\/", "/");
					ywUrl = ywUrl.replaceAll("\\\\/", "/");
//					System.out.println("bdUrl=====" + bdUrl);
//					System.out.println("ywUrl=====" + ywUrl);
					t.put("bdUrl", bdUrl);
					t.put("ywUrl", ywUrl);
					t.put("host", host);
					if(!ywUrl.equals(""))
					{
					urlList.add(t);
					}
				}
			}
				download(urlList, downloadPath);
				
				// ===========打包开始=============
				/**
				 * 复制下载的图片 原文件夹(全路径)=add+nameid 复制的文件夹(全路径)=add+nameid_bak
				 */
				String oldaddress = add + nameid;
				String newaddress = add + nameid + "_bak";
				DirectoryUtil.copy(oldaddress, newaddress);
				/**
				 * 给复制的文件夹中的文件重新命名
				 */
				// System.out.println("开始复制文件"+newaddress);
				File files = new File(newaddress);
				File[] file = files.listFiles();
				// System.out.println("开始更改文件名称"+newaddress);
				List<Map<String, String>> nameList = WebSiteProcess
						.changeNameList(file, nameid);
				// System.out.println("开始压缩文件"+nameid);
				file = files.listFiles();
				File[] cout=new File[30];
				for(int i=0;i<file.length;i++)
				{
					cout[i%30]=file[i];
					if((i%30==0&&i!=0)||i==file.length-1)
					{
						File zip1 = new File(zip, DateUtil.getCurrentTimeMillis()+"_"+nameid + ".zip");
						try {
							ZipTest.ZipFiles(zip1, "", cout);
							cout=new File[30];
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				// System.out.println("开始删除复制的文件"+newaddress+":"+add+nameid+"_bak");
				DirectoryUtil.deleteDir(new File(newaddress));
//				DirectoryUtil.deleteDir(new File(oldaddress));
//				DirectoryUtil.doDeleteEmptyDir(newaddress);
				// ===========打包结束=============

				// 数据匹配,返回对应的图片名称和url
				result = WebSiteProcess.convertMap(urlList, nameList);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void download(List<Map<String, String>> listUrl,
			String downloadPath) {
		for (Map<String, String> urlMap : listUrl) {
			String url = urlMap.get("bdUrl");
			url = url.replace("\\", "");
			String imageName = url.substring(url.lastIndexOf("/") + 1,
					url.length());
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
				c2.addRequestProperty("Host", urlMap.get("host"));
				c2.addRequestProperty("Referer", url);
				c2.connect();
				InputStream in = c2.getInputStream();
				String file = downloadPath + imageName;
				FileOutputStream fo = new FileOutputStream(new File(file));
				byte[] buf = new byte[1024];
				int length = 0;
				while ((length = in.read(buf, 0, buf.length)) != -1)
					fo.write(buf, 0, length);
				// System.out.println(Thread.currentThread().getName()+url+"下载完成！");
				in.close();
				fo.close();
			} catch (FileNotFoundException e1) {
				System.out.println("无法下载图片！" + url);
			} catch (IOException e2) {
				System.out.println("发生IO异常！" + url);
			}
		}
	}
}
