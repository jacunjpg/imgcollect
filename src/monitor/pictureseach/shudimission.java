package monitor.pictureseach;

import java.io.File;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.ReadProperties;
import monitor.util.DateUtil;
import monitor.util.JsonHelper;
import monitor.util.JsonUtil;
import monitor.util.SpringUtil;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.LoginUser;
import monitor.webview.entity.ViewBean;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IAuthorityService;
import monitor.webview.service.IMissionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.log4j.Logger;

public class shudimission implements Runnable {
	private static Logger logger = Logger.getLogger(shudimission.class);
	public static IMissionService missionService = (IMissionService) SpringUtil
			.getObject("missionService");
	public static IAuthorityService authorityService = (IAuthorityService) SpringUtil
			.getObject("authorityService");
	private String collecttask;
	private Collecttask collectTask =null;
	private LoginUser userinfo = null;
	private Integer startid = 1;
	private Integer MaxId = 1;
	private String missionid;
	private static File[] files = new File[2];
	private static List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
	private static String idkey = "nothing";
	private static int count = 0;
	private static int filecount = 0;
	private Map<String, String> mapToken;// 把token放入内存中，方便读取
	public final static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Integer.parseInt(ConfigInfo.MAXTHREADPOOL));
	private ExecutorService backThreadPool = Executors.newFixedThreadPool(50);
	private boolean stopFlag = false;
	private int status = 0;
	private int countNum = 0;

	public shudimission(String collecttask,String missionid) {
		this.collecttask = collecttask;
		this.missionid = missionid;
		connectionManager.setMaxTotalConnections(20);// 总的连接数
		connectionManager.setMaxConnectionsPerHost(50);
		collectTask = (Collecttask) JsonHelper
				.JSONToObj(collecttask, Collecttask.class);
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", collectTask.getMissionid());
		//获取该任务的创建人信息
		userinfo = authorityService.getAuthorityByMissionid(params);
		
	}

	public void run() {
		
		
		long t0 = new Date().getTime();
		long t1 = t0;
		
		long status0 = t0;
		long status1 = t0;
		while (!stopFlag) {
			t1 = new Date().getTime();
			status1 = t1;
			if ((t1 - t0) > 1000) {
				logger.debug("相差时间" + (t1 - t0));
				t0 = t1;
				logger.debug("任务开始执行=================================="+DateUtil.getCurrentTime());
				runTask();
				logger.debug("任务结束执行----------------------------------"+DateUtil.getCurrentTime());
			}
			
			if ((status1 - status0) > 1000*10*6) {
				updateTaskStatus();
				status0 = status1;
			}
		}

	}

	/**
	 * 停止任务
	 */
	public void stopTask() {
		stopFlag = true;
	}

	/**
	 * 采集任务
	 */
	public void runTask() {

		try {
			logger.debug("shudimission run+++++++++++++++++++++++++++++++++++++++Missionid==="
					+ collectTask.getMissionid());
			
			logger.debug("getToken+++++++++++++++++++++++++++++++++++++++");
			String token = getToken(userinfo.getSinausername(),
					userinfo.getSinapassword(), userinfo.getSinaappid(),
					userinfo.getSinaappkey());
			logger.debug("getToken完成" + token);
			//获取sinalocal,多个城市任务时，将任务城市拼接
			String sinalocal = getSinaLocal(userinfo.getSinaappid(),userinfo.getSinaappkey());
			getTaskDetailByIdkey(token, "", collectTask, sinalocal);
			logger.debug("结束读取接口获取任务详情============当前的微博起始id===" + startid+"|"+MaxId);
			logger.debug("shudimission end+++++++++++++++++++++++++++++++++++++++Missionid==="
					+ collectTask.getMissionid());
		} catch (Exception e) {
			logger.error("shudimission interrupt++++++++++++++++++++"
					+ e.getMessage());

		}

	}
	
	public void updateTaskStatus(){
		String missionid = collectTask.getMissionid();
		if(countNum >= 60){
			status = 1;
			//修改数据库状态为繁忙
			missionService.updateMissionStatusById(missionid,status);
		}
		if(countNum >= 300){
			status = 2;
			//修改数据库状态为阻塞
			missionService.updateMissionStatusById(missionid,status);
		}
		if(countNum == 0){
			status = 0;
			//修改数据库状态为正常
			missionService.updateMissionStatusById(missionid,status);
		}
		if(countNum == 3){
			status = 3;
			//修改数据库状态为异常
			missionService.updateMissionStatusById(missionid,status);
		}
	}

	/**
	 * 获取sinalocal,多个城市任务时，将任务城市拼接
	 * 
	 * 1、先查询appid和appkey对应的用户有多少，
	 * 2、根据用户查询出正在执行的任务，
	 * 3、如果属地任务有正在执行的，把用户的属地城市拼接到local中
	 * 4、如果一个正在任务为省级任务，将local返回""
	 */
	private String getSinaLocal(String sinaappid, String sinaappkey) {
		String local = "";
		List<LoginUser> userList = authorityService.getdbinfoListByAppInfo(sinaappid,sinaappkey);
		for (LoginUser loginUser : userList) {
			String sinalocal = loginUser.getSinalocal();
			if(sinalocal==null){
				sinalocal="";
			}
			//根据用户名查询正在执行的任务信息
			List<dbinfo> missionList = missionService.getdbinfoRunningListByName(loginUser.getName());
			if(missionList.size()>0){
				//有正在执行的省级任务
				if(sinalocal.trim().length()<1){
					return "";
				}
				//有正在执行的省级任务
				local += ","+sinalocal;
			}
		}
		if(local.length()>0){
			local = local.substring(1);
		}
		return local;
	}

	public List<Map<String, String>> getTaskDetailByIdkey(String token,
			String idkey, Collecttask collectTask, String sinalocal)
			throws Exception {
		List<Map<String, String>> picrelist = new ArrayList<Map<String, String>>();
		ReadProperties readProperties = new ReadProperties();
		String tomcatpath = readProperties.getValueByKey("tomcatpath");
		String add = tomcatpath + "pages/savedpictures/mission/"
				+ "xinlangweibo/" + collectTask.getMissionid();

		String scsc2 = getpiclist(token, idkey, startid);
		logger.debug("scsc2 ===================== " +scsc2);
		logger.debug("startid ===================== " + startid);
		
		JSONObject jsonobj = JSONObject.fromObject(scsc2);
		Object obj = jsonobj.get("count");
		logger.debug("count = " + obj);
		if (obj == null) {
			//解析结果是否是token过期
			String errcode = jsonobj.getString("errcode");
			String error = jsonobj.getString("error");
			if(errcode.equals("10002")&&error.indexOf("expired")!=-1){
				mapToken=null;
			}
			logger.debug("中断执行,返回的微博列表为空obj==null");
			return null;
		}
		logger.debug("本次请求的记录数=="+Integer.parseInt(obj.toString()));
		if(Integer.parseInt(obj.toString()) >= 200){
			countNum++;
			logger.debug("countNum="+countNum);
		}else{
			countNum=0;
			logger.debug("countNum重置为0="+countNum);
		}
		
		if (startid != 1) {
			picdownload picdown = new picdownload(add, collectTask, scsc2,
					sinalocal);
			logger.debug("下载开始=============================");
			fixedThreadPool.execute(picdown);
			logger.debug("下载结束=============================");
		}
		
		if (startid == 1) {
			startid = jsonobj.getInt("maxid");
		} else {
			startid = jsonobj.getInt("next_id") + 1;
		}
		logger.debug("maxid==== = " + jsonobj.get("maxid"));
		if(jsonobj.get("maxid")!=null && jsonobj.getString("maxid")!="null" && jsonobj.get("maxid")!=""){
			MaxId = jsonobj.getInt("maxid");
		}
		
//		dbinfo info = new dbinfo();
//		info.setPictureid(startid);
//		info.setMissionid(Integer.parseInt(collectTask.getMissionid()));
//		missionService.updatestartid(info);
		
		
		return picrelist;
	}

	private static void insertmissionpicture(dbinfo dbinfo,
			List<Map<String, String>> result) {
		dbinfo picture = null;
		List<dbinfo> picturelist = new ArrayList<dbinfo>();
		for (int i = 0; i < result.size(); i++) {
			picture = new dbinfo();
			String oldUrl = result.get(i).get("oldUrl");
			// System.out.println(oldUrl);
			oldUrl = oldUrl.substring(oldUrl.indexOf("//") + 2);
			// System.out.println(oldUrl);
			oldUrl = oldUrl.substring(0, oldUrl.indexOf("/"));
			// System.out.println(oldUrl);
			picture.setPicturepath(result.get(i).get("picturepath"));
			picture.setPicturename(result.get(i).get("newName"));
			picture.setPictureurl(result.get(i).get("oldUrl"));
			picture.setWeb(oldUrl);
			picture.setMissionid(dbinfo.getMissionid());
			picturelist.add(picture);
		}
		Map<String, String> params = new HashMap<String, String>();

		if (picturelist != null && picturelist.size() != 0) {
			missionService.insertpicture(picturelist);
		}
	}

	private static String getpiclist(String token, String idkey, int startId) {
		String scsc2 = "";
		String url = "";
		HttpURLConnection c1 = null;
		Scanner fs = null;
		try {
			url = "http://sm.viewslive.cn/api1.2/analyse/province?token="
					+ token + "&count=200&startid=" + startId;
			URL u1 = new URL(url);
			logger.debug("开始获取微博内容=============" + url);

			c1 = (HttpURLConnection) u1.openConnection();

			c1.connect();
			logger.debug("开始获取微博内容=c1.connect连接成功=====");
			InputStream fi = c1.getInputStream();
			fs = new Scanner(fi);

			while (fs.hasNext()) {
				scsc2 = fs.nextLine();
				logger.debug("执行while....=======");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.getStackTrace());
			e.printStackTrace();
		} finally {

			logger.debug("执行finally....======");
			c1.disconnect();
			fs.close();
		}
		logger.debug("结束获取微博内容=============" + url);
		return scsc2;
	}

	private static List<Map<String, String>> getContentList(String result,
			String sinalocal) {
		List<Map<String, String>> contentList = new ArrayList<Map<String, String>>();
		JSONObject jsonobj = JSONObject.fromObject(result);
		JSONArray jsonlist = JSONArray.fromObject(jsonobj.get("data"));
		for (int i = 0; i < jsonlist.size(); i++) {
			JSONObject json = JSONObject.fromObject(jsonlist.get(i));
			if (json.get("user_city").equals(sinalocal)) {
				if (json.get("original_pic") != null
						&& !json.get("original_pic").equals("")
						&& !json.get("original_pic").equals("null")) {
					Map<String, String> content = new HashMap<String, String>();
					content.put("bdUrl", json.get("original_pic").toString());
					content.put("ywUrl", (String) json.get("url"));
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

	public String getToken(String username, String password, String appid,
			String appkey) throws Exception {
		String scsc2 = "";
		// String appid = "23088"; //应用的AppID
		// String appkey = "VVTpgCDsCcFRLP3Zi7Ig"; //应用分配的AppKey
		// String username = "chwx"; //用户的登录用户名
		// String password = "chwx62115358"; //用户的登录密码

		// /=================================
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
				System.out.println(scsc2);
			}
			mapToken = JsonUtil.getTokenAndEndTime(scsc2);
		} catch (Exception e) {
			e.printStackTrace();
			//接口异常
			countNum=3;
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
					System.out.println(idkey + "<----idkey");
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
				System.out.println(scsc2);
			}
			// 解析任务列表
			JSONObject json = JSONObject.fromObject(scsc2);
			String data = json.getString("data");
			JSONArray array = JSONArray.fromObject(data);

			List<ViewBean> a = JSONArray.toList(array, ViewBean.class);
			for (int i = 0; i < a.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("idkey", a.get(i).getIdkey());
				map.put("name", a.get(i).getName());
				taskList.add(map);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				System.out.println("接口异常,等待5秒");
				Thread.sleep(5000);
				taskList = getTaskList(token);

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return taskList;

	}
}
