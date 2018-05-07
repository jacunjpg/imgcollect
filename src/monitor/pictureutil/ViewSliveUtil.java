package monitor.pictureutil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monitor.util.DateUtil;
import monitor.util.SpringUtil;
import monitor.webview.entity.LoginUser;
import monitor.webview.entity.ViewBean;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IAuthorityService;
import monitor.webview.service.IMissionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ViewSliveUtil {
	public static IAuthorityService authorityService=(IAuthorityService) SpringUtil.getObject("authorityService");
	public static IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
	
	
	public static void main(String[] args) {
		
		try {
			String token = getTokenById("97");
			List<Map<String, String>> taskList = getTaskList(token);
			for (Map<String, String> map : taskList) {
				System.out.println(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据任务id获取token
	 * @param taskname
	 * @return
	 */
	private static String getTokenById(String missionid) throws Exception {
		String scsc2 = "";
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		LoginUser userinfo=authorityService.getAuthorityByMissionid(params);
		
		String appid = userinfo.getAppid();			//应用的AppID
		String appkey = userinfo.getAppkey();		//应用分配的AppKey
		String username = userinfo.getSinausername();		//用户的登录用户名
		String password = userinfo.getSinapassword();
		String url = "http://sm.viewslive.cn/api1.2/authorize?appid="+appid+"&appkey="+appkey
						+"&username="+username+"&password="+password;
//		try {
			URL u1 = new URL(url);
			HttpURLConnection c1 = (HttpURLConnection) u1.openConnection();
			c1.connect();
			
			InputStream fi = c1.getInputStream();
			Scanner fs = new Scanner(fi);
			
			while (fs.hasNext()) {
				scsc2 = fs.nextLine();
				System.out.println(scsc2);
			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String regex1 = "\\{\"token\":\"([\\d\\D]*?)\",";
		return getContent(regex1, scsc2);
	}
	
	/*
	 * 获取token
	 */
	public static String getToken(){
		String scsc2 = "";
		String appid = "23088";			//应用的AppID
		String appkey = "VVTpgCDsCcFRLP3Zi7Ig";		//应用分配的AppKey
		String username = "chwx";		//用户的登录用户名
		String password = "chwx62115358";		//用户的登录密码
		String url = "http://sm.viewslive.cn/api1.2/authorize?appid="+appid+"&appkey="+appkey
						+"&username="+username+"&password="+password;
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

	/*
	 * 返回token
	 */
	private static String getContent(String regex, String scsc) {
		String content = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(scsc);
		while (matcher.find()) {
			content = matcher.group(1).toString();
		}
		return content;
	}

	/**
	 * 
	 * <p>Title void</p>
	 * <p>Description </p>
	 *
	 * @author chwx</p>
	 * @date 2017-3-31 </p>
	 * @param taskname		任务名称
	 * @param tasktypes		任务类型 0:话题监测任务, 1:用户监测任务（仅仅支持新浪通道、腾讯通道、Twitter通道）
	 * @param channeltypes	通道选择 1:新浪微博，2:腾讯微博，3:网易微博，4:搜狐微博，5:Twitter，6:博客通道，7:微信通道，11:长微博；多个通道采用"_"分隔开,例如:1_2_7
	 * @param tasknotroles	关键词(需要匹配的)，例如: (中国|日本)+(南海|钓鱼岛) ，需要转换成urlencode编码；参数tasktypes=2有效。
	 * @throws Exception
	 */
	public static void addTopic(String taskname,String channeltypes,int tasktypes, String taskroles) {
		try {
			//获取token
			String token = getToken();
			taskname = URLEncoder.encode(taskname, "utf-8");
			taskroles = URLEncoder.encode(taskroles, "utf-8");
			String url = "http://sm.viewslive.cn/api1.2/task/add?token="+token+"&taskname="+taskname
					+"&channeltypes="+channeltypes+"&tasktypes="+tasktypes+"&taskroles=("+taskroles+")";
			System.out.println(url);
			URL u1 = new URL(url);
			HttpURLConnection c1 = (HttpURLConnection) u1.openConnection();
			c1.connect();
			InputStream fi = c1.getInputStream();
			Scanner fs = new Scanner(fi);
			String scsc2 = "";
			while (fs.hasNext()) {
				scsc2 = fs.nextLine();
				System.out.println("结果"+scsc2);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static List<Map<String, String>> getTaskByTaskName(String taskName,String date,String path,String fileName,String zip, int startid) {
	
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		try {
			long starTime = System.currentTimeMillis();
			//获取token
			String token = getToken();
			//获取idkey
			String idkey = getIdkeyByTaskName(token,taskName);
			//獲取任務詳情
			List<Map<String, String>> taskDetailList = getTaskDetailByIdkey(token,idkey,startid);
			System.out.println("任务结果数量："+taskDetailList.size());
			System.out.println(taskDetailList);
			long midTime = System.currentTimeMillis();
			List<Map<String, String>> downPicList = PictureDownloadUtil.downloadpic(taskDetailList,path+fileName);
			if(downPicList.size()>0){
				
				// ===========打包开始=============
				/**
				 * 复制下载的图片 原文件夹(全路径)=add+nameid 复制的文件夹(全路径)=add+nameid_bak
				 */
				String oldaddress = path + fileName;
				String newaddress = path + fileName + "_bak";
				DirectoryUtil.copy(oldaddress, newaddress);
				/**
				 * 给复制的文件夹中的文件重新命名
				 */
				// System.out.println("开始复制文件"+newaddress);
				File files = new File(newaddress);
				File[] file = files.listFiles();
				// System.out.println("开始更改文件名称"+newaddress);
				List<Map<String, String>> nameList = WebSiteProcess
						.changeNameList(file, fileName);
				
				System.out.println("改名文件数量："+nameList.size());
				
				// System.out.println("开始压缩文件"+nameid);
				file = files.listFiles();
				File[] cout=new File[20];
				new File(zip).mkdirs();
				for(int i=0;i<file.length;i++)
				{
					cout[i%20]=file[i];
					if((i%20==19&&i!=0)||i==file.length-1)
					{
						
						File zip1 = new File(zip, DateUtil.getCurrentTimeMillis()+"_"+fileName + ".zip");
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
				// ===========打包结束=============
				
				// 数据匹配,返回对应的图片名称和下载的 url
				result = WebSiteProcess.convertMap(downPicList, nameList);
				
			}
			
			long endTime = System.currentTimeMillis();
			long zTime = midTime - starTime;
			long Time = endTime - starTime;
			System.out.println("獲取信息所花时间:"+zTime+"ms");
			System.out.println("所花时间:"+Time+"ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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

	/*
	 * 获取任务列表
	 */
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
			e.printStackTrace();
		}
		return taskList;
		
	}
	
	public static List<Map<String, String>> getTaskDetailByIdkey(String token,String idkey,int startid) throws Exception {
		List<Map<String, String>> picrelist=new ArrayList<Map<String, String>>();
		
		
		String url = "";
		
		url = "http://sm.viewslive.cn/api1.2/task/stream?token=" + token
					+ "&idkey=" + idkey + "" + "&count=200&startid="+startid;
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
		
		//解析json
		String picurl = "\"url\":\"([\\d\\D]*?)\",";
		String picRegx = "\"original_pic\":\"([\\d\\D]*?)\",";
		String picRetRegx = "\"retweeted_original_pic\":\"([\\d\\D]*?)\",";
		 JSONObject jsonobj=JSONObject.fromObject(scsc2);
	     JSONArray jsonlist=JSONArray.fromObject(jsonobj.get("data"));
		List<Map<String, String>> picList = getContentList(scsc2);
		for (int i = 0; i < jsonlist.size(); i++) {
			System.out.println(jsonlist.get(i));
		}
		picrelist.addAll(picList);
		while(jsonlist.size()>0)
		{
		Thread.sleep(15000);
		String startids =(String) JSONObject.fromObject(jsonlist.get(jsonlist.size()-1)).get("Id");
		startid =Integer.parseInt(startids);
		url = "http://sm.viewslive.cn/api1.2/task/stream?token=" + token
					+ "&idkey=" + idkey + "" + "&count=200&startid="+startid;
		u1 = new URL(url);
		c1 = (HttpURLConnection) u1.openConnection();
		c1.connect();
		fi = c1.getInputStream();
		fs = new Scanner(fi);
		scsc2 = "";
		while (fs.hasNext()) {
			scsc2 = fs.nextLine();
			System.out.println(scsc2);

		}
		
		jsonobj=JSONObject.fromObject(scsc2);
	    jsonlist=JSONArray.fromObject(jsonobj.get("data"));
		picList = getContentList(scsc2);
//		for (int i = 0; i < picrelist.size(); i++) {
//			System.out.println(JSONObject.fromObject(picrelist.get(i)));
//		}
		for (int i = 0; i < jsonlist.size(); i++) {
			System.out.println(jsonlist.get(i));
		}
		picrelist.addAll(picList);
		}
		
		return picrelist;
		
	}
	
	/*
	 * 获取某个任务的结果数据
	 */
	public static List<Map<String, String>> getTaskDetailByIdkey(String token,String idkey,String date) throws Exception {
		List<Map<String, String>> picrelist=new ArrayList<Map<String, String>>();
		
		
		String url = "";
		if (date.equals("")) {
			url = "http://sm.viewslive.cn/api1.2/task/result?token=" + token
					+ "&idkey=" + idkey + "" + "&pagesize=200&page=1";
		} else {
			url = "http://sm.viewslive.cn/api1.2/task/result?token=" + token
					+ "&idkey=" + idkey + "" + "&date=" + date + ""
					+ "&pagesize=200&page=1";
		}
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
		
		//解析json
		String picurl = "\"url\":\"([\\d\\D]*?)\",";
		String picRegx = "\"original_pic\":\"([\\d\\D]*?)\",";
		String picRetRegx = "\"retweeted_original_pic\":\"([\\d\\D]*?)\",";
		List<Map<String, String>> picList = getContentList(scsc2);
		
		for (int i = 0; i < picList.size(); i++) {
			System.out.println(picList.get(i).toString());
		}
		picrelist.addAll(picList);
		
		return picrelist;
		
	}


	private static List<Map<String, String>> getContentList(String result) {
		List<Map<String, String>> contentList = new ArrayList<Map<String, String>>();
        JSONObject jsonobj=JSONObject.fromObject(result);
        JSONArray jsonlist=JSONArray.fromObject(jsonobj.get("data"));
        for(int i=0;i<jsonlist.size();i++)
        {
        	JSONObject json = JSONObject.fromObject(jsonlist.get(i));
//        	System.out.println(json.get("url"));
//        	System.out.println(json.get("original_pic"));
//        	System.out.println(json.get("retweeted_original_pic"));
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
		
		return contentList;
	}
	
	
	

	/*
	 * 查看任务详细信息
	 
	private static void getTaskDetial(String token,String idkey) throws Exception {
		String url = "http://sm.viewslive.cn/api1.2/task/info?token="+token+"&idkey="+idkey;
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
		
	}

	
	 * 高级搜索
	 
	public static void searchContent() throws Exception {
		String keyword = "中国";
		keyword =  URLEncoder.encode(keyword, "UTF-8");
		String url = "http://sm.viewslive.cn/api1.2/state/search?token=60o03i1CTPSO94d838f6&keyword="+keyword
				+"&channel_types=1&daterange=2016-03-28&wtype=0";
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
		
	}

	*/
	
	/*
	 * 删除用户配置的任务
	 */
	public static String deleteTaskByIdkey(String missionid) throws Exception {
        String str = "";
        //获取token
        String token = getTokenById(missionid);
        //token为空，直接返回错误
        if(token.length()<1){
            return "";
        }
        String taskName = "mission"+missionid;
        //获取idkey
        String idkey = getIdkeyByTaskName(token,taskName);
        //idkey为空，直接返回错误
        if(idkey.length()<1){
            return "";
        }
        String url = "http://sm.viewslive.cn/api1.2/task/delete?token="+token+"&idkey="+idkey;
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
        String regex1 = "\\{\"code\":\"([\\d\\D]*?)\",";
        String content = getContent(regex1, scsc2);
        if(content.equals("200")){
            str="success";
        }else{
            String regex = ",\"msg\":\"([\\d\\D]*?)\"}";
            String content1 = getContent(regex, scsc2);
            //当返回的错误信息没有msg时候，判断error
            if(content1.length()<1){
                String regex2 = ",\"error\":\"([\\d\\D]*?)\"}";
                content1 = getContent(regex2, scsc2);
            }
            //根据任务id查询任务信息
            Map<String, String> params = new HashMap<String, String>();
            params.put("missionid", missionid);
            dbinfo info = missionService.getMissionStatusById(params);
            String query = info.getQuery();
            content1 = content1+"_过期停止_"+query;
            missionService.updateMissionById(content1,missionid);
            
        }
        return str;
        
	}		

	
	/* 编辑话题监测任务
	 
	private static void editTask(String taskname, String tasktypes) throws Exception {
		//a1112BM
		taskname = URLEncoder.encode(taskname, "UTF-8");
		tasktypes = URLEncoder.encode(tasktypes, "UTF-8");
		String url = "http://sm.viewslive.cn/api1.2/task/edit?token=60o03i1CTPSO94d838f6&taskname="+taskname+"&channeltypes=1&taskroles=("+tasktypes+")&idkey=14f12BP";
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
	}*/

}
