package monitor.pictureseach;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.DirectoryUtil;
import monitor.pictureutil.ReadProperties;
import monitor.util.DateUtil;
import monitor.util.ExpireRobot;
import monitor.util.ImgCompress;
import monitor.util.SpringUtil;
import monitor.util.TestFtp;
import monitor.webview.entity.Collecttask;
import monitor.webview.service.IAuthorityService;
import monitor.webview.service.IMissionService;

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
public class TupianProcessByBaidu {
	
	private static Logger logger = Logger.getLogger(BaiduOfStream.class);
	public static IMissionService missionService = (IMissionService) SpringUtil
			.getObject("missionService");
	public static IAuthorityService authorityService = (IAuthorityService) SpringUtil
			.getObject("authorityService");
	private String collecttask;
	private String missionid;
	private  File[] files = new File[20];
	private int count = 0;
	private int filecount = 0;

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
	public List<Map<String, String>> downloadPic(String query, int page, String nameid, Collecttask collectTask) {
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
		ReadProperties readProperties = new ReadProperties();
		String tomcatpath = readProperties.getValueByKey("tomcatpath");
		String downloadPath = tomcatpath + "pages/savedpictures/mission/"
				+ "xinlangweibo/" + nameid;
		
		File path = new File(downloadPath);
		if (!path.exists())
			path.mkdirs();
		try {
//			for (int i = 0; i < page; i++) {
				String params = "http://image.baidu.com/search/acjson?"
						+ "tn=resultjson_com" + "&ipn=rj" + "&ct=201326592"
						+ "&is=&fp=result" + "&queryWord="
						+ URLEncoder.encode(query, "utf-8")
						+ "&cl=2&lm=-1&ie=utf-8" + "&oe=utf-8&adpicid=&st=-1"
						+ "&z=&ic=0" + "&word="
						+ URLEncoder.encode(query, "utf-8")
						+ "&s=&se=&tab=&width=&height="
						+ "&face=0&istype=2&qc=&nc=1&fr=" + "&pn=" + page * 30
						+ "&rn=30" + "&gsm=" + Integer.toHexString(page * 30)
						+ "&1475982089536=";
				logger.debug("url========"+params);
				String html = WebSiteProcess.openUrl(params, "utf-8");

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
					} catch (Exception e) {
						e.printStackTrace();
					}
					ywUrl = ywUrl.replaceAll("\\\\/", "/");
					t.put("bdUrl", bdUrl);
					t.put("ywUrl", ywUrl);
					t.put("content", "");
					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        Date date=new Date();
					t.put("created_at", dateFormater.format(date));
					urlList.add(t);
				}
//			}
			download(urlList, downloadPath, nameid, collectTask);

			// 数据匹配,返回对应的图片名称和url
//			result = WebSiteProcess.convertMap(urlList, nameList);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void download(List<Map<String, String>> listUrl,
			String downloadPath,String missionid, Collecttask collectTask) {
		List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
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
				
				//==

				InputStream in = c2.getInputStream();
				int t = imageName.lastIndexOf(".");
				if (t > 0) {
					imageName = imageName.substring(t);
				} else {
					imageName = ".jpg";
				}
				imageName = missionid+"_"
						+ DateUtil.getCurrentTimeMillis() + "_"
						+ (count + 1) + imageName;
				count++;
//				String file = downloadPath + imageName;
//				ReadProperties readProperties = new ReadProperties();
//				String picpathflag = readProperties
//						.getValueByKey("picpathflag");
				
				//======
				ReadProperties readProperties = new ReadProperties();
				String tomcatpath = readProperties
						.getValueByKey("tomcatpath");
				String ftpenabled = readProperties
						.getValueByKey("ftpenabled");
				String file = tomcatpath + "pages/savedpictures/mission/"
						+ "xinlangweibo/" + missionid + "/"
						+ imageName;
				
				String picpathflag = readProperties.getValueByKey("picpathflag");
				//======
				Map<String, String> p = new HashMap<String, String>();

				logger.debug("start down file====");
				// 如果使用weibo图片url,此方法执行
				if (picpathflag.equals("true")) {
					// picturepath存放微博图片的地址url
					p.put("pictureurl", urlMap.get("bdUrl"));
				} else {
					// picturepath存放服务器图片的地址
					p.put("pictureurl", "pages/savedpictures/mission/"
							+ "xinlangweibo/" + missionid
							+ "/" + imageName);
				}

				p.put("picturepath", "pages/savedpictures/mission/"
						+ "xinlangweibo/" + missionid
						+ "/" + imageName);
				p.put("picturename", imageName);
				p.put("newName", imageName);
				// 保存数据库使用
				p.put("originalurl", urlMap.get("bdUrl"));// 微博图片地址
				p.put("picturetime", urlMap.get("created_at"));// 微博创建时间
				p.put("tasktype", "K");// 图片所属的任务类型
				p.put("oldUrl", urlMap.get("ywUrl"));// 微博地址
				p.put("content", urlMap.get("content"));// 微博内容

//				String tomcatpath = readProperties
//						.getValueByKey("tomcatpath");
//				String ftpenabled = readProperties
//						.getValueByKey("ftpenabled");
				
				if (!new File(file).exists()) {
					downloadlist.add(p);
					FileOutputStream fo = new FileOutputStream(new File(
							file));
					byte[] buf = new byte[1024];
					int length = 0;
					while ((length = in.read(buf, 0, buf.length)) != -1)
						fo.write(buf, 0, length);
					in.close();
					fo.close();

					ImgCompress a = new ImgCompress(file);
					if (a.getheight() > 500 && a.getwidth() > 500) {
						a.resizeFix(ConfigInfo.picturesize,
								ConfigInfo.picturesize, file);
					}

					// 如果使用FTP方式上传图片,此方法执行
					if (ftpenabled.equals("true")) {
						logger.debug("start upload file====");
						// 上传到ftp服务器开始====
						TestFtp tf = new TestFtp(missionid+ "");
						File fileFtp = new File(file);
						try {
							tf.upload(fileFtp);
						} catch (IOException e) {
							logger.debug("upload IOException "+e.getStackTrace());
							ExpireRobot expireRobot = new ExpireRobot();
							int freeDiskSpace = expireRobot
									.getFreeDiskSpace();
							// 如果空间不足1G，执行停止操作
							if (freeDiskSpace < 1) {
								expireRobot.updateMissionStyleByid(missionid);
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug("upload exception "+e.getStackTrace());
						}
						// 上传到ftp服务器结束====
					}

					files[filecount] = new File(file);
					filecount++;

					String psend = readProperties.getValueByKey("psend");
					int ps = Integer.parseInt(psend);
					if (filecount == ps) {
						filecount = 0;

						//
						SendPicToMQForKeyWord sendPicToMQForKeyWord = new SendPicToMQForKeyWord();
						sendPicToMQForKeyWord.sendMessage(collectTask,
								downloadlist);
						//
						// SendMessageByMqresult.sendpicture(dbinfo,null,downloadlist);
						// insertmissionpicture(dbinfo,downloadlist);

						downloadlist = new ArrayList<Map<String, String>>();
					}

					// 如果使用FTP方式上传图片，则删除本地图片
					if (ftpenabled.equals("true")) {
						DirectoryUtil.deleteDir(new File(file));
					}

				}
				//==
				c2.disconnect();
			} catch (FileNotFoundException e1) {
				System.out.println("无法下载图片！" + url);
			} catch (IOException e2) {
				System.out.println("发生IO异常！" + url);
				ExpireRobot expireRobot = new ExpireRobot();
				int freeDiskSpace = expireRobot.getFreeDiskSpace();
				// 如果空间不足1G，执行停止操作
				if (freeDiskSpace < 1) {
					expireRobot.updateMissionStyleByid(missionid);
				}
				System.out.println("发生IO异常！" + url);
				logger.debug("upload io exception "+e2.getStackTrace());
			}
		}
	}

}
