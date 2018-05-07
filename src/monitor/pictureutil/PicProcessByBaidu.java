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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import monitor.util.DateUtil;

/**
 * 百度图片处理
 * 
 * <p>
 * Title PicProcessByBaidu
 * </p>
 * <p>
 * Description
 * </p>
 * 
 * @author chwx</p>
 * @date 2017-3-29 </p>
 */
public class PicProcessByBaidu {

	/**
	 * 
	 * 
	 * <p>
	 * Title List<Map<String,String>>
	 * </p>
	 * <p>
	 * Description
	 * </p>
	 * 
	 * @author chwx</p>
	 * @date 2017-3-29 </p>
	 * @param query
	 *            查询条件
	 * @param add
	 *            保存图片地址
	 * @param page
	 * @param nameid
	 *            文件夹名称
	 * @param zip
	 *            压缩文件地址
	 * @return
	 */
	public static List<Map<String, String>> downloadPic(String query,
			String add, int page, String nameid, String zip) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		// 获取百度图片地址和源文件地址
		List<Map<String, String>> urlList = new ArrayList<Map<String, String>>();
		// 新闻正文正则
		String regex1 = "\"middleURL\":\"([\\d\\D]*?)\",";
		// String regex2 = "\"objURL\":\"([\\d\\D]*?)\",";
		// String regex2 = "\"FromURL\":\"([\\d\\D]*?)\"";

		String regex2 = "\"fromURL\":\"([\\d\\D]*?)\"";
		// 获取网页源代码
		String regexString = "\"adType\":([\\d\\D]*?)}";
		String downloadPath = add + nameid + "\\";
		File path = new File(downloadPath);
		if (!path.exists())
			path.mkdirs();
		try {
			for (int i = 0; i < page; i++) {
				String params = "http://image.baidu.com/search/acjson?"
						+ "tn=resultjson_com" + "&ipn=rj" + "&ct=201326592"
						+ "&is=&fp=result" + "&queryWord="
						+ URLEncoder.encode(query, "utf-8")
						+ "&cl=2&lm=-1&ie=utf-8" + "&oe=utf-8&adpicid=&st=-1"
						+ "&z=&ic=0" + "&word="
						+ URLEncoder.encode(query, "utf-8")
						+ "&s=&se=&tab=&width=&height="
						+ "&face=0&istype=2&qc=&nc=1&fr=" + "&pn=" + i * 30
						+ "&rn=30" + "&gsm=" + Integer.toHexString(i * 30)
						+ "&1475982089536=";
				String html = WebSiteProcess.openUrl(params, "utf-8");
				System.out.println("开始打印" +i+"次");
				System.out.println(html);
				System.out.println("结束打印" + new Date());

				// 获取图片URL
				List<String> listUrl = WebSiteProcess.getContent(regexString,
						html);

				for (int j = 0; j < listUrl.size(); j++) {
					Map<String, String> t = new HashMap<String, String>();
					String bdUrl = WebSiteProcess
							.getUrl(regex1, listUrl.get(j));
					String ywUrl = "";
					try {
						ywUrl = BaiduPicScript.getUrlTrue(WebSiteProcess
								.getUrl(regex2, listUrl.get(j)));
						// ywUrl = getUrl(regex2,listUrl.get(j));
					} catch (Exception e) {
						e.printStackTrace();
					}
					ywUrl = ywUrl.replaceAll("\\\\/", "/");
					// System.out.println("bdUrl====="+bdUrl);
					// System.out.println("ywUrl====="+ywUrl);
					t.put("bdUrl", bdUrl);
					t.put("ywUrl", ywUrl);
					urlList.add(t);
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
				DirectoryUtil.doDeleteEmptyDir(newaddress);
//				DirectoryUtil.deleteDir(new File(oldaddress));
				// ===========打包结束=============

				// 数据匹配,返回对应的图片名称和url
				result = WebSiteProcess.convertMap(urlList, nameList);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void download(List<Map<String, String>> listUrl,
			String downloadPath) {
		for (Map<String, String> urlMap : listUrl) {
			String url = urlMap.get("bdUrl");
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
				c2.addRequestProperty("Host", "img5.imgtn.bdimg.com");
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
