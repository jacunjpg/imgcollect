package monitor.pictureseach;

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

import monitor.util.InitSystemServlet;
import monitor.util.JsonUtil;
import monitor.webview.entity.ViewBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WeiBoInfoContent {

	/**
	 * 获取token：如果没有过期返回缓存的token，过期的话则获取最新的token
	 * @param username
	 * @param password
	 * @param appid
	 * @param appkey
	 * @return
	 * @throws Exception
	 */
	public String getToken(String username ,String password,String appid ,String appkey ) throws Exception{
		String scsc2 = "";
		//判断token是否过期，如果已经过期，获取新的token
		String token = "";
		Map<String, String> mapToken = InitSystemServlet.mapToken;// 把token放入内存中，方便读取
		if(mapToken!=null){
			String token_n=mapToken.get("token");
			String exprise_n=mapToken.get("exprise");
			if(exprise_n!=null&&token_n!=null){
				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        Date date=new Date();
		        String nw = dateFormater.format(date);
		        int compareTon = nw.compareTo(exprise_n);
		        //如果compareTon大于0说明token过期，需要重新获取
		        if(compareTon>=0){
		        	token = getTokenFromMap(username ,password, appid , appkey);
		        }else{
		        	token=token_n;
		        }
			}
		}else{
			token = getTokenFromMap(username ,password, appid , appkey);
		}
		return token;
	}
	
	
	public String getIdkeyByTaskName(String token,String taskName) {
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
	
	public List<Map<String, String>> getTaskList(String token){
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
			try {
				System.out.println("接口异常,等待5秒");
				Thread.sleep(5000);
				taskList=getTaskList(token);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		return taskList;
		
	}
	
	public String getTokenFromMap(String username ,String password,String appid ,String appkey ){
		String scsc2 = "";
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
			String info = username+password+appid+appkey;
			InitSystemServlet.mapToken = JsonUtil.getTokenAndEndTime(scsc2,info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String regex1 = "\\{\"token\":\"([\\d\\D]*?)\",";
		return  getContent(regex1, scsc2);
	}
	
	private String getContent(String regex, String scsc) {
		String content = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(scsc);
		while (matcher.find()) {
			content = matcher.group(1).toString();
		}
		return content;
	}
	
}
