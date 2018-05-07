package monitor.pictureseach;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.DirectoryUtil;
import monitor.pictureutil.ReadProperties;
import monitor.util.DateUtil;
import monitor.util.ExpireRobot;
import monitor.util.ImgCompress;
import monitor.util.JsonHelper;
import monitor.util.SpringUtil;
import monitor.util.TestFtp;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.LoginUser;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IAuthorityService;
import monitor.webview.service.IMissionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class KeyWordsMission implements Runnable {

	private static Logger logger = Logger.getLogger(KeyWordsMission.class);
	public static IMissionService missionService = (IMissionService) SpringUtil
			.getObject("missionService");
	public static IAuthorityService authorityService = (IAuthorityService) SpringUtil
			.getObject("authorityService");
	private String collecttask;
	private static File[] files = new File[20];
	private static String idkey = "nothing";
	private static int count = 0;
	private static int filecount = 0;
	private boolean stopFlag = false;// 机器人运行的标志位，false继续执行，true中断执行

	public KeyWordsMission(String collecttask) {
		this.collecttask = collecttask;
	}

	public void run() {
		long t0 = new Date().getTime();
		long t1 = t0;
		while (!stopFlag) {
			t1 = new Date().getTime();
			if ((t1 - t0) > 1000) {
				logger.debug("相差时间" + (t1 - t0));
				t0 = t1;
				runTask();
			}
		}
	}

	/**
	 * 停止任务
	 */
	public void stopTask() {
		stopFlag = true;
	}

	private void runTask() {

		try {
			Collecttask collectTask = (Collecttask) JsonHelper.JSONToObj(
					collecttask, Collecttask.class);
			Map<String, String> params = new HashMap<String, String>();
			params.put("missionid", collectTask.getMissionid());
			LoginUser userinfo = authorityService
					.getAuthorityByMissionid(params);

			logger.debug("getToken+++++++++++++++++++++++++++++++++++++++");
			WeiBoInfoContent infoContent = new WeiBoInfoContent();
			String token = infoContent.getToken(userinfo.getSinausername(),
					userinfo.getSinapassword(), userinfo.getAppid(),
					userinfo.getAppkey());
			logger.debug("getToken完成" + token);

			// 获取idkey
			idkey = infoContent.getIdkeyByTaskName(token, "mission"
					+ collectTask.getMissionid());
			params.put("missionid", "" + collectTask.getMissionid());
			dbinfo a = missionService.getmissioninfo(params);
			getTaskDetailByIdkey(token, idkey, a.getPictureid(), collectTask);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getTaskDetailByIdkey(String token, String idkey, int startid,
			Collecttask collectTask) throws Exception {
		List<Map<String, String>> picrelist = new ArrayList<Map<String, String>>();
		ReadProperties readProperties = new ReadProperties();
		String tomcatpath = readProperties.getValueByKey("tomcatpath");
		String add = tomcatpath + "pages/savedpictures/mission/"
				+ "xinlangweibo/" + collectTask.getMissionid();

		String scsc2 = getpiclist(token, idkey, startid);
		JSONObject jsonobj = JSONObject.fromObject(scsc2);
		JSONArray jsonlist = JSONArray.fromObject(jsonobj.get("data"));
		List<Map<String, String>> picList = getContentList(scsc2);

		// 处理piclist,下图,压缩，入库，发送；
		piclistdownload(picList, add, collectTask);

		while (jsonlist.size() > 0) {
			String startids = (String) JSONObject.fromObject(
					jsonlist.get(jsonlist.size() - 1)).get("Id");
			startid = Integer.parseInt(startids) + 1;
			dbinfo dbinfo = new dbinfo();
			dbinfo.setPictureid(startid);
			dbinfo.setMissionid(Integer.parseInt(collectTask.getMissionid()));
			missionService.updatestartid(dbinfo);
			scsc2 = getpiclist(token, idkey, startid);
			jsonobj = JSONObject.fromObject(scsc2);
			jsonlist = JSONArray.fromObject(jsonobj.get("data"));
			picList = getContentList(scsc2);
			piclistdownload(picList, add, collectTask);
		}

	}

	// 根据token等信息，获取结果信息
	private String getpiclist(String token, String idkey, int startid) {
		String scsc2 = "";
		String url = "";
		try {
			url = "http://sm.viewslive.cn/api1.2/task/stream?token=" + token
					+ "&idkey=" + idkey + "" + "&count=200&startid=" + startid;
			URL u1 = new URL(url);
			HttpURLConnection c1;
			c1 = (HttpURLConnection) u1.openConnection();
			c1.connect();
			InputStream fi = c1.getInputStream();
			Scanner fs = new Scanner(fi);

			while (fs.hasNext()) {
				scsc2 = fs.nextLine();
				System.out.println(scsc2);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scsc2;
	}

	// 获取结果中的url以及微博内容，把信息存入集合contentList中
	private static List<Map<String, String>> getContentList(String result) {
		List<Map<String, String>> contentList = new ArrayList<Map<String, String>>();
		JSONObject jsonobj = JSONObject.fromObject(result);
		JSONArray jsonlist = JSONArray.fromObject(jsonobj.get("data"));
		for (int i = 0; i < jsonlist.size(); i++) {
			JSONObject json = JSONObject.fromObject(jsonlist.get(i));
			if (json.get("original_pic") != null
					&& !json.get("original_pic").equals("")
					&& !json.get("original_pic").equals("null")) {
				Map<String, String> content = new HashMap<String, String>();
				content.put("bdUrl", json.get("original_pic").toString());
				content.put("ywUrl", (String) json.get("url"));
				content.put("content", (String) json.get("content"));
				try {
					contentList.add(content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (json.get("retweeted_original_pic") != null
					&& !json.get("retweeted_original_pic").toString()
							.equals("")
					&& !json.get("retweeted_original_pic").toString()
							.equals("null")) {
				Map<String, String> content = new HashMap<String, String>();
				content.put("bdUrl", json.get("retweeted_original_pic")
						.toString());
				content.put("ywUrl", (String) json.get("url"));
				content.put("content", (String) json.get("content"));
				try {
					contentList.add(content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		return contentList;
	}

	// 下载图片、保存、并发送mq
	private static void piclistdownload(List<Map<String, String>> urllist,
			String downloadpath, Collecttask collectTask) {
		downloadpath = downloadpath + "\\";
		File path = new File(downloadpath);
		if (!path.exists())
			path.mkdirs();
		List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
		for (int i = 0; i < urllist.size(); i++) {
			String url = urllist.get(i).get("bdUrl");
			if (!url.equals("")) {
				String host = url.substring(url.indexOf("//") + 2);
				host = host.substring(0, host.indexOf("/"));
				String imageName = url.substring(url.lastIndexOf("/") + 1,
						url.length());
				try {
					URL u2 = new URL(url);
					HttpURLConnection c2 = (HttpURLConnection) u2
							.openConnection();
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
					int t = imageName.lastIndexOf(".");
					if (t > 0) {
						imageName = imageName.substring(t);
					} else {
						imageName = ".jpg";
					}
					imageName = "" + collectTask.getMissionid() + "_"
							+ DateUtil.getCurrentTimeMillis() + "_"
							+ (count + 1) + imageName;
					count++;
					String file = downloadpath + imageName;
					Map<String, String> p = new HashMap<String, String>();
					p.put("picturepath", "pages/savedpictures/mission/"
							+ "xinlangweibo/" + collectTask.getMissionid()
							+ "/" + imageName);
					p.put("newName", imageName);
					p.put("oldUrl", urllist.get(i).get("ywUrl"));
					p.put("content", urllist.get(i).get("content"));

					ReadProperties readProperties = new ReadProperties();
					String ftpenabled = readProperties
							.getValueByKey("ftpenabled");
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
							// 上传到ftp服务器开始====
							TestFtp tf = new TestFtp(collectTask.getMissionid()
									+ "");
							File fileFtp = new File(file);
							try {
								tf.upload(fileFtp);
							} catch (IOException e) {
								ExpireRobot expireRobot = new ExpireRobot();
								int freeDiskSpace = expireRobot
										.getFreeDiskSpace();
								// 如果空间不足1G，执行停止操作
								if (freeDiskSpace < 1) {
									expireRobot
											.updateMissionStyleByid(collectTask
													.getMissionid() + "");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							// 上传到ftp服务器结束====
						}

						files[filecount] = new File(file);
						filecount++;

						String psend = readProperties.getValueByKey("psend");
						int ps = Integer.parseInt(psend);
						if (filecount == ps) {
							filecount = 0;
							SendPicToMQForKeyWord sendPicToMQForKeyWord = new SendPicToMQForKeyWord();
							sendPicToMQForKeyWord.sendMessage(collectTask,
									downloadlist);
							downloadlist = new ArrayList<Map<String, String>>();
						}

						// 如果使用FTP方式上传图片，则删除本地图片
						if (ftpenabled.equals("true")) {
							DirectoryUtil.deleteDir(new File(file));
						}

					}

				} catch (FileNotFoundException e1) {
					System.out.println("无法下载图片！" + url);
				} catch (IOException e2) {
					ExpireRobot expireRobot = new ExpireRobot();
					int freeDiskSpace = expireRobot.getFreeDiskSpace();
					// 如果空间不足1G，执行停止操作
					if (freeDiskSpace < 1) {
						expireRobot.updateMissionStyleByid(collectTask
								.getMissionid() + "");
					}
					System.out.println("发生IO异常！" + url);
				}
			}
		}
		insertmissionpicture(collectTask, downloadlist);
		downloadlist = new ArrayList<Map<String, String>>();
	}

	public static void insertmissionpicture(Collecttask collectTask,
			List<Map<String, String>> result) {
		dbinfo picture = null;
		List<dbinfo> picturelist = new ArrayList<dbinfo>();
		for (int i = 0; i < result.size(); i++) {
			picture = new dbinfo();
			String oldUrl = result.get(i).get("oldUrl");
			oldUrl = oldUrl.substring(oldUrl.indexOf("//") + 2);
			oldUrl = oldUrl.substring(0, oldUrl.indexOf("/"));
			picture.setPicturepath(result.get(i).get("picturepath"));
			picture.setPicturename(result.get(i).get("newName"));
			picture.setPictureurl(result.get(i).get("oldUrl"));
			picture.setContent(result.get(i).get("content"));
			picture.setMissionid(Integer.parseInt(collectTask.getMissionid()));
			picturelist.add(picture);
		}

		if (picturelist != null && picturelist.size() != 0) {
			missionService.insertpicture(picturelist);
		}
	}

}
