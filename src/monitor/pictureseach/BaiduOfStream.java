package monitor.pictureseach;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.DirectoryUtil;
import monitor.pictureutil.ReadProperties;
import monitor.util.DateUtil;
import monitor.util.ExpireRobot;
import monitor.util.ImgCompress;
import monitor.util.JsonHelper;
import monitor.util.JsonUtil;
import monitor.util.SpringUtil;
import monitor.util.TestFtp;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.LoginUser;
import monitor.webview.entity.ViewBean;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IAuthorityService;
import monitor.webview.service.IMissionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class BaiduOfStream implements Runnable {
	private static Logger logger = Logger.getLogger(BaiduOfStream.class);
	public static IMissionService missionService = (IMissionService) SpringUtil
			.getObject("missionService");
	public static IAuthorityService authorityService = (IAuthorityService) SpringUtil
			.getObject("authorityService");
	private String collectask;
	private String missionid;
	private  File[] files = new File[20];
	private String idkey = "nothing";
	private int count = 0;
	private int filecount = 0;
	private Map<String, String> mapToken;// 把token放入内存中，方便读取
	private boolean stopFlag = false;
	private String query = "";
	private int page = 0;
	private Collecttask collectTask;
	
	public BaiduOfStream(String collectask, String query, String missionid, Collecttask collectTask) {
		this.collectask = collectask;
		this.collectTask = collectTask;
		this.missionid = missionid;
		this.query = query;
		this.page=Integer.parseInt(ConfigInfo.BDPAGENUM);
	}

	public void run() {

		long t0 = new Date().getTime();
		long t1 = t0;
		int num = 0;
		while (!stopFlag) {
			
			t1 = new Date().getTime();
			if ((t1 - t0) > 1000 * 2) {
				logger.debug("相差时间" + (t1 - t0));
				t0 = t1;
				runTask(num);
				num++;
			}
			
			if(num>page){
				stopFlag = true;
			}
		}

	}

	public void stopTask() {
		stopFlag = true;
	}

	public void runTask(int num) {
		try {
			logger.debug("baidu_robot");
			
			new TupianProcessByBaidu().downloadPic(query, num, missionid,collectTask);
			
		} catch (Exception e) {
			logger.error("baidu_robot interrupt++++++++++++++++++++"
					+ e.getMessage());

		}
	}

	public List<Map<String, String>> getTaskDetailByIdkey(String token,
			String idkey, int startid, Collecttask collectTask)
			throws Exception {


		List<Map<String, String>> picrelist = new ArrayList<Map<String, String>>();
		ReadProperties readProperties = new ReadProperties();
		String tomcatpath = readProperties.getValueByKey("tomcatpath");
		String add = tomcatpath + "pages/savedpictures/mission/"
				+ "xinlangweibo/" + collectTask.getMissionid();

		String scsc2 = getpiclist(token, idkey, startid);
		logger.debug("key word scsc2 127===================== " + scsc2);
		logger.debug("key word startid 128===================== " + startid);
		//正确格式：{"count":2,"data":[{"id":"11196","name":"mission217","start":"2017\/09\/18 15:17:17","end":"2017\/09\/25 15:17:17","type":"0","idkey":"71cd2UA"},{"id":"11186","name":"mission208","start":"2017\/09\/18 09:36:33","end":"2017\/09\/25 09:36:33","type":"0","idkey":"05322Uq"}]}
		//错误结果：{"code":"10005","msg":"This AppID access to the API's frequency is too high, beyond the specified range. appid:23095"}
		logger.debug("1111111111111");
		JSONObject jsonobj = JSONObject.fromObject(scsc2);
		logger.debug("22222222222222"+jsonobj.get("data"));
		JSONArray jsonlist = JSONArray.fromObject(jsonobj.get("data"));
		logger.debug("33333333333333"+jsonlist);
//		try {
		List<Map<String, String>> picList = getContentList(scsc2);
		logger.debug("get piclist success===========");
		// 处理piclist,下图,压缩，入库，发送
		piclistdownload(picList, add, collectTask);
		logger.debug("get piclistdownload success===========");
		
		logger.debug("key word startid="+startid+"missionid="+collectTask.getMissionid());
		logger.debug("key word jsonlist.size() ="+jsonlist.size());
		//====更新pictureid==========.
		if(jsonlist.size() > 0){
			logger.debug("get startids start===================missionid="+collectTask.getMissionid());
			String startids = (String) JSONObject.fromObject(jsonlist.get(jsonlist.size() - 1)).get("Id");
			logger.debug("get startids end==================="+startids);
			int new_startid = Integer.parseInt(startids) + 1;
			dbinfo dbinfo = new dbinfo();
			dbinfo.setPictureid(new_startid);
			dbinfo.setMissionid(Integer.parseInt(collectTask.getMissionid()));
			logger.debug("pictureid==================="+new_startid);
			missionService.updatestartid(dbinfo);
		}
		
		return picrelist;

	}

	private void piclistdownload(List<Map<String, String>> urllist,
			String downloadpath, Collecttask collectTask) {
		logger.debug("start download=====");
		try{
		downloadpath = downloadpath + "/";
		File path = new File(downloadpath);
		if (!path.exists())
			path.mkdirs();
		List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
		logger.debug("start loop urllist====="+urllist.size());
		for (int i = 0; i < urllist.size(); i++) {
			String url = urllist.get(i).get("bdUrl");
			if (!url.equals("")) {
				String host = url.substring(url.indexOf("//") + 2);
				host = host.substring(0, host.indexOf("/"));
				String imageName = url.substring(url.lastIndexOf("/") + 1,
						url.length());
				logger.debug("start down file====");
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
					ReadProperties readProperties = new ReadProperties();
					String picpathflag = readProperties
							.getValueByKey("picpathflag");
					Map<String, String> p = new HashMap<String, String>();

					logger.debug("start down file====");
					// 如果使用weibo图片url,此方法执行
					if (picpathflag.equals("true")) {
						// picturepath存放微博图片的地址url
						p.put("pictureurl", urllist.get(i).get("bdUrl"));
					} else {
						// picturepath存放服务器图片的地址
						p.put("pictureurl", "pages/savedpictures/mission/"
								+ "xinlangweibo/" + collectTask.getMissionid()
								+ "/" + imageName);
					}

					p.put("picturepath", "pages/savedpictures/mission/"
							+ "xinlangweibo/" + collectTask.getMissionid()
							+ "/" + imageName);
					p.put("picturename", imageName);
					p.put("newName", imageName);
					// 保存数据库使用
					p.put("originalurl", urllist.get(i).get("bdUrl"));// 微博图片地址
					p.put("picturetime", urllist.get(i).get("created_at"));// 微博创建时间
					p.put("tasktype", "K");// 图片所属的任务类型
					p.put("oldUrl", urllist.get(i).get("ywUrl"));// 微博地址
					p.put("content", urllist.get(i).get("content"));// 微博内容

					String tomcatpath = readProperties
							.getValueByKey("tomcatpath");
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
							logger.debug("start upload file====");
							// 上传到ftp服务器开始====
							TestFtp tf = new TestFtp(collectTask.getMissionid()+ "");
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
									expireRobot.updateMissionStyleByid(collectTask.getMissionid() + "");
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
					c2.disconnect();

				} catch (FileNotFoundException e1) {
					System.out.println("无法下载图片！" + url);
					logger.debug("download pic exception "+e1.getStackTrace());
				} catch (IOException e2) {
					ExpireRobot expireRobot = new ExpireRobot();
					int freeDiskSpace = expireRobot.getFreeDiskSpace();
					// 如果空间不足1G，执行停止操作
					if (freeDiskSpace < 1) {
						expireRobot.updateMissionStyleByid(collectTask
								.getMissionid() + "");
					}
					System.out.println("发生IO异常！" + url);
					logger.debug("upload io exception "+e2.getStackTrace());
				}
			}
		}
		logger.debug("end loop urllist====="+urllist.size());
		if (downloadlist.size() > 0) {
			SendPicToMQForKeyWord sendPicToMQForKeyWord = new SendPicToMQForKeyWord();
			sendPicToMQForKeyWord.sendMessage(collectTask, downloadlist);
			// insertmissionpicture(collectTask,downloadlist);
		}
		downloadlist = new ArrayList<Map<String, String>>();
		}catch(Exception e){
			logger.debug("exceptioneeeeeeeeeeeeeeeeeeeeee"+e.getStackTrace());
		}
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
			picture.setWeb(oldUrl);
			picture.setMissionid(Integer.parseInt(collectTask.getMissionid()));
			picturelist.add(picture);
		}
		Map<String, String> params = new HashMap<String, String>();

		if (picturelist != null && picturelist.size() != 0) {
			missionService.insertpicture(picturelist);
		}
	}

	private static String getpiclist(String token, String idkey, int startid) {
		String scsc2 = "";
		String url = "";
		try {
			url = "http://sm.viewslive.cn/api1.2/task/stream?token=" + token
					+ "&idkey=" + idkey + "" + "&count=200&startid=" + startid;
			URL u1 = new URL(url);
			logger.debug("keyword send url=="+url);
			HttpURLConnection c1;
			c1 = (HttpURLConnection) u1.openConnection();
			c1.connect();
			InputStream fi = c1.getInputStream();
			Scanner fs = new Scanner(fi);

			while (fs.hasNext()) {
				scsc2 = fs.nextLine();
				logger.debug("keyword taskdetail===="+scsc2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scsc2;
	}

	private static List<Map<String, String>> getContentList(String result) {
		List<Map<String, String>> contentList = new ArrayList<Map<String, String>>();
		JSONObject jsonobj = JSONObject.fromObject(result);
		JSONArray jsonlist = JSONArray.fromObject(jsonobj.get("data"));
		for (int i = 0; i < jsonlist.size(); i++) {
			JSONObject json = JSONObject.fromObject(jsonlist.get(i));
			// System.out.println(json.get("url"));
			// System.out.println(json.get("original_pic"));
			// System.out.println(json.get("retweeted_original_pic"));
			if (json.get("original_pic") != null
					&& !json.get("original_pic").equals("")
					&& !json.get("original_pic").equals("null")) {
				Map<String, String> content = new HashMap<String, String>();
				content.put("bdUrl", json.get("original_pic").toString());
				content.put("ywUrl", (String) json.get("url"));
				content.put("content", (String) json.get("content"));
				content.put("created_at", (String) json.get("created_at"));
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
				// System.out.println(json.get("retweeted_original_pic"));
				Map<String, String> content = new HashMap<String, String>();
				content.put("bdUrl", json.get("retweeted_original_pic")
						.toString());
				content.put("ywUrl", (String) json.get("url"));
				content.put("content", (String) json.get("content"));
				content.put("created_at", (String) json.get("created_at"));
				try {
					contentList.add(content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		return contentList;
	}

	public String getToken(String username, String password, String appid,
			String appkey) throws Exception {
		// 判断token是否过期，如果已经过期，获取新的token
		String token = "";
		if (mapToken != null) {
			String token_n = mapToken.get("token");
			String exprise_n = mapToken.get("exprise");
			if (exprise_n != null && token_n != null) {
				SimpleDateFormat dateFormater = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String nw = dateFormater.format(date);
				int compareTon = nw.compareTo(exprise_n);
				// 如果compareTon大于0说明token过期，需要重新获取
				if (compareTon >= 0) {
					token = getTokenFromMap(username, password, appid, appkey);
				} else {
					token = token_n;
				}
			}
		} else {
			token = getTokenFromMap(username, password, appid, appkey);
		}
		return token;
	}

	public String getTokenFromMap(String username, String password,
			String appid, String appkey) {
		String scsc2 = "";
		String url = "http://sm.viewslive.cn/api1.2/authorize?appid=" + appid
				+ "&appkey=" + appkey + "&username=" + username + "&password="
				+ password;
		System.out.println("appid:" + appid + "          appkey:" + appkey);
		try {
			URL u1 = new URL(url);
			HttpURLConnection c1 = (HttpURLConnection) u1.openConnection();
			c1.connect();

			InputStream fi = c1.getInputStream();
			Scanner fs = new Scanner(fi);

			while (fs.hasNext()) {
				scsc2 = fs.nextLine();
				logger.debug(scsc2);
			}
			mapToken = JsonUtil.getTokenAndEndTime(scsc2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String regex1 = "\\{\"token\":\"([\\d\\D]*?)\",";
		return getContent(regex1, scsc2);
	}

	public static String getIdkeyByTaskName(String token, String taskName) {
		String idkey = "";
		try {
			List<Map<String, String>> taskList = getTaskList(token);
			for (Map<String, String> map : taskList) {
				String name = map.get("name");
				if (name.equals(taskName)) {
					idkey = map.get("idkey");
					logger.debug(idkey + "<----idkey");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idkey;
	}

	private static String getContent(String regex, String scsc) {
		String content = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(scsc);
		while (matcher.find()) {
			content = matcher.group(1).toString();
		}
		return content;
	}

	public static List<Map<String, String>> getTaskList(String token) {
		List<Map<String, String>> taskList = new ArrayList<Map<String, String>>();
		;
		try {
			String url = "http://sm.viewslive.cn/api1.2/task/list?token="
					+ token;
			URL u1 = new URL(url);
			HttpURLConnection c1 = (HttpURLConnection) u1.openConnection();
			c1.connect();
			InputStream fi = c1.getInputStream();
			Scanner fs = new Scanner(fi);
			String scsc2 = "";
			while (fs.hasNext()) {
				scsc2 = fs.nextLine();
				logger.debug("keywords resultdate"+scsc2);
			}
			// 解析任务列表
			JSONObject json = JSONObject.fromObject(scsc2);
			String data = json.getString("data");
			
//			Object obj = json.get("data");
//			logger.debug("count = " + obj);
//			if (obj == null) {
//				logger.debug("中断keyword执行,返回的微博列表为空obj==null");
//				return null;
//			}
			JSONArray array = JSONArray.fromObject(data);

			List<ViewBean> a = JSONArray.toList(array, ViewBean.class);
			for (int i = 0; i < a.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("idkey", a.get(i).getIdkey());
				map.put("name", a.get(i).getName());
				taskList.add(map);
			}
		} catch (Exception e) {
			try {
				logger.debug("接口异常,等待5秒");
				Thread.sleep(5000);
				taskList = getTaskList(token);

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		return taskList;

	}
}
