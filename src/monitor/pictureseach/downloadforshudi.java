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
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.ReadProperties;
import monitor.util.DateUtil;
import monitor.util.ImgCompress;
import monitor.util.JsonHelper;
import monitor.util.SpringUtil;
import monitor.webview.entity.LoginUser;
import monitor.webview.entity.ViewBean;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IAuthorityService;
import monitor.webview.service.IMissionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;

public class downloadforshudi implements Runnable{
	public static IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
	public static IAuthorityService authorityService=(IAuthorityService) SpringUtil.getObject("authorityService");
	private String dbinfo;
	private static File[] files=new File[20];
	private static String idkey="nothing";
	private static int count=0;
	private static int filecount=0;
	public final static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
	public downloadforshudi(String dbinfo)
	{
		this.dbinfo=dbinfo;
		connectionManager.setMaxTotalConnections (20);//总的连接数  
	    connectionManager.setMaxConnectionsPerHost (50);
	}
	public void run() {
		
		// TODO Auto-generated method stub
		new File(ConfigInfo.zipadd).mkdirs();
		System.out.println(dbinfo);
		dbinfo dbinfo2 = (dbinfo) JsonHelper.JSONToObj(dbinfo, dbinfo.class);
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", dbinfo2.getUser());
		LoginUser userinfo=authorityService.getAuthorityByName(params);
		String token = getToken(userinfo.getSinausername(),userinfo.getSinapassword(),userinfo.getSinaappid(),userinfo.getSinaappkey());
		//获取idkey
//		idkey = getIdkeyByTaskName(token,"属地任务名");
//		Map<String,String> params = new HashMap<String,String>();
		params.put("missionid", ""+dbinfo2.getMissionid());
		dbinfo a = missionService.getmissioninfo(params);
		int i=1;
		while (i==1)
		{
		try {
			i=0;
			getTaskDetailByIdkey(token,"",a.getPictureid(),dbinfo2,userinfo.getSinalocal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
				// TODO Auto-generated catch block
				System.out.println("任务被打断");
				try {
					Thread.sleep(5000);
					i=1;
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		}
	}
	
	public static List<Map<String, String>> getTaskDetailByIdkey(String token,String idkey,int startid,dbinfo dbinfo, String sinalocal) throws Exception {
		List<Map<String, String>> picrelist=new ArrayList<Map<String, String>>();
		ReadProperties readProperties = new ReadProperties();
		String tomcatpath = readProperties.getValueByKey("tomcatpath");
		String add=tomcatpath+"pages/savedpictures/mission/"+"xinlangweibo/"+dbinfo.getMissionid();
		String url = "";
		
		String scsc2=getpiclist(token,idkey,startid);
		 JSONObject jsonobj=JSONObject.fromObject(scsc2);
	     JSONArray jsonlist=JSONArray.fromObject(jsonobj.get("data"));
		List<Map<String, String>> picList = getContentList(scsc2,sinalocal);
		for (int i = 0; i < jsonlist.size(); i++) {
			System.out.println(jsonlist.get(i));
		}
		/**处理piclist,下图,压缩，入库，发送；
		 * 
		 */
		
		piclistdownload(picList,add,dbinfo);
		
//		picrelist.addAll(picList);
		System.out.println("下次任务测是");
		while(jsonlist.size()>0)
		{
		
		Thread.sleep(15000);
		String startids =(String) JSONObject.fromObject(jsonlist.get(jsonlist.size()-1)).get("Id");
		startid =Integer.parseInt(startids)+1;
		dbinfo.setPictureid(startid);
		missionService.updatestartid(dbinfo);
		scsc2=getpiclist(token,idkey,startid);
		jsonobj=JSONObject.fromObject(scsc2);
	    jsonlist=JSONArray.fromObject(jsonobj.get("data"));
		picList = getContentList(scsc2,sinalocal);
//		for (int i = 0; i < picrelist.size(); i++) {
//			System.out.println(JSONObject.fromObject(picrelist.get(i)));
//		}
		for (int i = 0; i < jsonlist.size(); i++) {
			System.out.println(jsonlist.get(i));
		}
		piclistdownload(picList,add,dbinfo);
		System.out.println("jsonlistsize="+jsonlist.size());
//		picrelist.addAll(picList);
		}
		
		return picrelist;
		
	}
	
	private static void piclistdownload(List<Map<String, String>> urllist,String downloadpath,dbinfo dbinfo) {
		downloadpath = downloadpath + "\\";
		File path = new File(downloadpath);
		if (!path.exists())
			path.mkdirs();
		List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
		// TODO Auto-generated method stub
		for (int i = 0; i < urllist.size(); i++) {
			String url = urllist.get(i).get("bdUrl");
			System.out.println(url);
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
					int t = imageName.lastIndexOf(".");
					if(t>0){
						imageName =imageName.substring(t);
					}else{
						imageName = ".jpg";
					}
					imageName =""+dbinfo.getMissionid() + "_" + DateUtil.getCurrentTimeMillis()
							+ "_" + (count+1) + imageName;
					count++;
					String file = downloadpath + imageName;
					Map<String, String> p = new HashMap<String, String>();
					p.put("picturepath", "pages/savedpictures/mission/"+"xinlangweibo/"+dbinfo.getMissionid()+"/"+imageName);
					p.put("newName", imageName);
					p.put("oldUrl", urllist.get(i).get("ywUrl"));
//					System.out.println(new File(file).exists());
					if (!new File(file).exists()) {
						downloadlist.add(p);
						FileOutputStream fo = new FileOutputStream(new File(file));
						byte[] buf = new byte[1024];
						int length = 0;
						while ((length = in.read(buf, 0, buf.length)) != -1)
							fo.write(buf, 0, length);
						in.close();
						fo.close();
//						if(dbinfo.getCheckmode().contains("6")||dbinfo.getCheckmode().contains("7"))
//						{
//							dbinfo zdh=new dbinfo();
//							zdh.setMissionid(dbinfo.getMissionid());
//							zdh.setPicturepath(file);
//							zdh.setCbkid(Integer.parseInt(dbinfo.getCheckcbk()));
//							if(dbinfo.getCheckmode().contains("6"))
//							{
//								zdh.setCheckmode("image");
//							}
//							else
//							{
//								zdh.setCheckmode("face");
//							}
//							HttpClient client = new HttpClient(connectionManager); 
//							zdhrun2 zdhrr=new zdhrun2(zdh,client);
//							fixedThreadPool.execute(zdhrr);
//						}
//						else{
						ImgCompress a=new ImgCompress(file);
						if(a.getheight()>500&&a.getwidth()>500)
						{
				    	a.resizeFix(ConfigInfo.picturesize, ConfigInfo.picturesize,file);
						}
						files[filecount]=new File(file);
						filecount++;
						if(filecount==19)
						{
							filecount=0;
//							File zip1 = new File(ConfigInfo.zipadd, DateUtil.getCurrentTimeMillis()+"_"+dbinfo.getMissionid() + ".zip");
//							try {
//								ZipTest.ZipFiles(zip1, "", files);
//								
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
							files=new File[20];
//							SendMessageByMq.sendpicture(dbinfo,zip1);
//							zip1.delete();
							insertmissionpicture(dbinfo,downloadlist);
							downloadlist = new ArrayList<Map<String, String>>();
//							new imagedetectRobot(dbinfo,zip1).start();
						}
//						}
//						System.out.println(p+"数量："+(count));
//						System.out.println(imageName);
//						System.out.println(urllist.get(i).get("address"));
//						System.out.println(urllist.get(i).get("url"));
					}
					System.out.println("下载完成！" + url);
					
				} catch (FileNotFoundException e1) {
					System.out.println("无法下载图片！" + url);
				} catch (IOException e2) {
					System.out.println("发生IO异常！" + url);
				}
				System.out.println("下载第"+i+"次完成" );
			}
		}
		System.out.println("下载结束！" );
		insertmissionpicture(dbinfo,downloadlist);
		downloadlist = new ArrayList<Map<String, String>>();
	}

	private static void insertmissionpicture(dbinfo dbinfo,
			List<Map<String, String>> result) {
		// TODO Auto-generated method stub
		dbinfo picture=null;
		List<dbinfo> picturelist=new ArrayList<dbinfo>();
		for(int i=0;i<result.size();i++)
		{
			picture=new dbinfo();
			String oldUrl=result.get(i).get("oldUrl");
//			System.out.println(oldUrl);
			oldUrl=oldUrl.substring(oldUrl.indexOf("//")+2);
//			System.out.println(oldUrl);
			oldUrl=oldUrl.substring(0,oldUrl.indexOf("/"));
//			System.out.println(oldUrl);
			picture.setPicturepath(result.get(i).get("picturepath"));
			picture.setPicturename(result.get(i).get("newName"));
			picture.setPictureurl(result.get(i).get("oldUrl"));
			picture.setWeb(oldUrl);
			picture.setMissionid(dbinfo.getMissionid());
			picturelist.add(picture);
		}
		Map<String, String> params = new HashMap<String, String>();
		
		if(picturelist!=null&&picturelist.size()!=0)
		{
		missionService.insertpicture(picturelist);
		}
	}
	
	private static String getpiclist(String token,String idkey,int startid)
	{
		String scsc2 = "";
		String url = "";
		try {
		url = "http://sm.viewslive.cn/api1.2/analyse/province?token=" + token
					+ "&count=100&startid="+startid;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scsc2;
	}
	
	private static List<Map<String, String>> getContentList(String result, String sinalocal) {
		List<Map<String, String>> contentList = new ArrayList<Map<String, String>>();
        JSONObject jsonobj=JSONObject.fromObject(result);
        JSONArray jsonlist=JSONArray.fromObject(jsonobj.get("data"));
        for(int i=0;i<jsonlist.size();i++)
        {
        	JSONObject json = JSONObject.fromObject(jsonlist.get(i));
//        	System.out.println(json.get("url"));
//        	System.out.println(json.get("original_pic"));
//        	System.out.println(json.get("retweeted_original_pic"));
        	if(json.get("user_city").equals(sinalocal))
        	{
        	if(json.get("original_pic")!=null&&!json.get("original_pic").equals("")&&!json.get("original_pic").equals("null"))
        	{
        	Map<String, String> content = new HashMap<String, String>();
			content.put("bdUrl", json.get("original_pic").toString());
			content.put("ywUrl", (String) json.get("url"));
			try {
				contentList.add(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	}
        	if(json.get("retweeted_original_pic")!=null&&!json.get("retweeted_original_pic").toString().equals("")&&!json.get("retweeted_original_pic").toString().equals("null"))
        	{
//        	System.out.println(json.get("retweeted_original_pic"));
        	Map<String, String> content = new HashMap<String, String>();
			content.put("bdUrl", json.get("retweeted_original_pic").toString() );
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
	
	public static String getToken(String username ,String password,String appid ,String appkey ){
		String scsc2 = "";
//		String appid = "23088";			//应用的AppID
//		String appkey = "VVTpgCDsCcFRLP3Zi7Ig";		//应用分配的AppKey
	//	String username = "chwx";		//用户的登录用户名
	//	String password = "chwx62115358";		//用户的登录密码
		String url = "http://sm.viewslive.cn/api1.2/authorize?appid="+appid+"&appkey="+appkey
						+"&username="+username+"&password="+password;
		System.out.println("appid:"+appid+"          appkey:"+appkey);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		String regex1 = "\\{\"token\":\"([\\d\\D]*?)\",";
		return getContent(regex1, scsc2);
	}
	
	public static String getIdkeyByTaskName(String token,String taskName) {
		String idkey = "";
		try {
			List<Map<String, String>> taskList = getTaskList(token);
			for (Map<String, String> map : taskList) {
				String name = map.get("name");
				if(name.equals(taskName)){
					idkey = map.get("idkey");
					System.out.println(idkey+"<----idkey");
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
	
	public static List<Map<String, String>> getTaskList(String token){
		List<Map<String, String>> taskList = new ArrayList<Map<String, String>>();;
		try {
			String url = "http://sm.viewslive.cn/api1.2/task/list?token="+token;
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
			//解析任务列表
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
//			e.printStackTrace();
			try {
				System.out.println("接口异常,等待5秒");
				Thread.sleep(5000);
				taskList=getTaskList(token);
				
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return taskList;
		
	}
}
