package monitor.pictureseach;

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

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.DirectoryUtil;
import monitor.pictureutil.ReadProperties;
import monitor.util.DateUtil;
import monitor.util.ExpireRobot;
import monitor.util.ImgCompress;
import monitor.util.SpringUtil;
import monitor.util.TestFtp;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class picdownload implements Runnable {

	private static Logger logger = Logger.getLogger(picdownload.class);
	public static IMissionService missionService = (IMissionService) SpringUtil
			.getObject("missionService");
	private String scsc2;
	private String sinalocal;
	private String downloadpath;
	private Collecttask collecttask;
	private int count = 0;
	private int filecount = 0;
	private File[] files = new File[2];
	private List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();

	public picdownload(String downloadpath, Collecttask collecttask, String scsc2,
			String sinalocal) {
		this.downloadpath = downloadpath;
		this.collecttask = collecttask;
		this.scsc2 = scsc2;
		this.sinalocal = sinalocal;
	}

	@Override
	public void run() {
		List<Map<String, String>> picList = getContentList(scsc2, sinalocal);
		/**
		 * 处理piclist,下图,压缩，入库，发送；
		 * 
		 */
		logger.debug("进入下载线程=============================");
		download(picList);
		logger.debug("退出下载线程=============================");

	}

	private static List<Map<String, String>> getContentList(String result,
			String sinalocal) {
		List<Map<String, String>> contentList = new ArrayList<Map<String, String>>();
		JSONObject jsonobj = JSONObject.fromObject(result);
		JSONArray jsonlist = JSONArray.fromObject(jsonobj.get("data"));
		for (int i = 0; i < jsonlist.size(); i++) {
			JSONObject json = JSONObject.fromObject(jsonlist.get(i));
//			logger.debug("微博创建时间============"+json.get("created_at"));
			//属地北京
			if(sinalocal==null || sinalocal.trim()==""){
				if (json.get("original_pic") != null
						&& !json.get("original_pic").equals("")
						&& !json.get("original_pic").equals("null")) {
					Map<String, String> content = new HashMap<String, String>();
					content.put("bdUrl", json.get("original_pic").toString());
					content.put("ywUrl", (String) json.get("url"));
					content.put("content", (String) json.get("content"));
					content.put("created_at", (String) json.get("created_at"));
					String user_city = json.get("user_city").toString();
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
					content.put("created_at", (String) json.get("created_at"));
					String user_city = json.get("user_city").toString();
					try {
						contentList.add(content);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}else if (json.get("user_city") != null
					&& sinalocal.indexOf(json.get("user_city").toString())!=-1) {
				//属地南京
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
		}

		return contentList;
	}

	public void download(List<Map<String, String>> urllist) {
		logger.debug("进入download方法=============================");
		downloadpath = downloadpath + "/";
		File path = new File(downloadpath);
		if (!path.exists())
			path.mkdirs();

		logger.debug("开始下载的图片数量==========================" + urllist.size());
		for (int i = 0; i < urllist.size(); i++) {
			logger.debug("start downloadpic ---i=="+i);
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
					imageName = "" + collecttask.getMissionid() + "_"
							+ DateUtil.getCurrentTimeMillis() + "_"
							+ (count + 1) + imageName;
					count++;
					ReadProperties readProperties = new ReadProperties();
					String tomcatpath = readProperties
							.getValueByKey("tomcatpath");
					String ftpenabled = readProperties
							.getValueByKey("ftpenabled");
					String file = tomcatpath + "pages/savedpictures/mission/"
							+ "xinlangweibo/" + collecttask.getMissionid() + "/"
							+ imageName;
					
					String picpathflag = readProperties.getValueByKey("picpathflag");
					Map<String, String> p = new HashMap<String, String>();
					
					//如果使用weibo图片url,此方法执行
					if(picpathflag.equals("true")){
						//picturepath存放微博图片的地址url
						p.put("pictureurl", urllist.get(i).get("bdUrl"));
					}else{
						//picturepath存放服务器图片的地址
						p.put("pictureurl", "pages/savedpictures/mission/"
								+ "xinlangweibo/" + collecttask.getMissionid() + "/"
								+ imageName);
					}
					p.put("picturepath", "pages/savedpictures/mission/"
							+ "xinlangweibo/" + collecttask.getMissionid() + "/"
							+ imageName);
					p.put("picturename", imageName);
					p.put("tasktype", "L");
					p.put("originalurl", urllist.get(i).get("bdUrl"));//微博图片地址
					p.put("picturetime", urllist.get(i).get("created_at"));//微博创建时间
					p.put("newName", imageName);
					p.put("oldUrl", urllist.get(i).get("ywUrl"));
					p.put("content", urllist.get(i).get("content"));
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
						logger.debug("end downloadpic ---i=="+i);
						logger.debug("start uploadpic ---i=="+i);
						ImgCompress a = new ImgCompress(file);
						if (a.getheight() > 500 && a.getwidth() > 500) {
							a.resizeFix(ConfigInfo.picturesize,
									ConfigInfo.picturesize, file);
						}

						// 如果使用FTP方式上传图片,此方法执行
						String start = DateUtil.getCurrentTimeMillis();
						if (ftpenabled.equals("true")) {
							// 上传到ftp服务器开始====
							logger.debug("create FTP connection"+i);
							TestFtp tf = new TestFtp(collecttask.getMissionid() + "");
							logger.debug("create FTP connection success"+i);
							File fileFtp = new File(file);
							logger.debug("FTP connection new file"+i);
							logger.debug("send file "+i);
							try {
								logger.debug("开始上传=========================");
								tf.upload(fileFtp);
								logger.debug("结束上传=========================");
							} catch (IOException e) {
								ExpireRobot expireRobot = new ExpireRobot();
								int freeDiskSpace = expireRobot.getFreeDiskSpace();
								//如果空间不足1G，执行停止操作
								if(freeDiskSpace<1){
									expireRobot.updateMissionStyleByid(collecttask.getMissionid());
								}
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
							// 上传到ftp服务器结束====
						}
						logger.debug("send file success"+i);
						String end = DateUtil.getCurrentTimeMillis();
						logger.debug("end uploadpic ---i=="+i+"start=="+start+"end=="+end);
						

						files[filecount] = new File(file);
						filecount++;
						String psend = readProperties.getValueByKey("psend");
						int ps = Integer.parseInt(psend);
						if (filecount == ps) {
							filecount = 0;
							logger.debug("shudi start sendmq====");
							// 发送mq并保存图片
							SendPicToMQ sendPicToMQ = new SendPicToMQ();
							sendPicToMQ.sendMessage(collecttask, downloadlist);
							logger.debug("shudi end sendmq====");
							downloadlist = new ArrayList<Map<String, String>>();
						}
						// }
					}
					c2.disconnect();
					// 如果使用FTP方式上传图片，则删除本地图片
					if (ftpenabled.equals("true")) {
						DirectoryUtil.deleteDir(new File(file));
					}

					logger.debug("下载完成！" + url);

				} catch (FileNotFoundException e1) {

					logger.error("FileNotFoundException！" + url + e1);
				} catch (IOException e2) {
					ExpireRobot expireRobot = new ExpireRobot();
					int freeDiskSpace = expireRobot.getFreeDiskSpace();
					//如果空间不足1G，执行停止操作
					if(freeDiskSpace<1){
						expireRobot.updateMissionStyleByid(collecttask.getMissionid());
					}
					logger.error("Exception！" + url + e2);
				}
			}
		}

		logger.debug("结束下载图片数量=========" + urllist.size());
		if (downloadlist.size() > 0) {
			// File zip1 = new File(ConfigInfo.zipadd,
			// DateUtil.getCurrentTimeMillis()+"_"+dbinfo.getMissionid() +
			// ".zip");
			// try {
			// ZipTest.ZipFiles(zip1, "", files);
			//
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// files=new File[2];

			// 发送mq并保存图片
			SendPicToMQ sendPicToMQ = new SendPicToMQ();
			sendPicToMQ.sendMessage(collecttask, downloadlist);

			// logger.debug("发送剩余图片信息mq开始============================="+downloadlist.size());
			// SendMessageByMqresult.sendpicture(dbinfo,null,downloadlist);
			// logger.debug("发送剩余图片信息mq结束============================="+downloadlist.size());
			// insertmissionpicture(dbinfo,downloadlist);

		}
		downloadlist = new ArrayList<Map<String, String>>();
	}

	public static void insertmissionpicture(dbinfo dbinfo,
			List<Map<String, String>> result) {
		logger.debug("进入插入图片信息=============================");
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
			picture.setTasktype(result.get(i).get("tasktype"));
			picture.setOriginalurl(result.get(i).get("originalurl"));
			picture.setPicturetime(result.get(i).get("picturetime"));
			picture.setWeb(oldUrl);
			picture.setMissionid(dbinfo.getMissionid());
			picturelist.add(picture);
		}

		if (picturelist != null && picturelist.size() != 0) {
			missionService.insertpicture(picturelist);
			logger.debug("插入图片信息成功=============================");
		}
	}

}
