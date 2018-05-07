package monitor.activemq;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import monitor.util.SpringUtil;
import monitor.webview.entity.WebSitePicture;
import monitor.webview.service.IFaceDetectService;
import monitor.webview.service.IMissionService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class SavePicMessage {

	public static void saveMessage(String jsonData, String website) {
		System.out.println("接收数据jsonData"+jsonData);
		System.out.println("接收数据website"+website);
		List<WebSitePicture> webSiteList = jsonData(jsonData,website);
		
		IFaceDetectService faceDetectService = (IFaceDetectService) SpringUtil
				.getObject("faceDetectService");
		faceDetectService.insertWebSitePicture(webSiteList);
	}
	
	public static List<WebSitePicture> jsonData(String jsonData,String website){
		List<WebSitePicture> webSiteList = new ArrayList<WebSitePicture>();
//		net.sf.json.JSONArray jsonArr = net.sf.json.JSONArray.fromObject(jsonData);
//		for(int i=0;i<jsonArr.size();i++){
//			net.sf.json.JSONObject job = jsonArr.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//			System.out.println(job.get("name"));
//			System.out.println(job.get("url"));
//		JSONArray jsonArr = JSONArray.fromObject(jsonData);
		JSONArray ja = JSONArray.parseArray(jsonData);
		for (Iterator iterator2 = ja.iterator(); iterator2.hasNext();) {
			JSONObject obj = (JSONObject) iterator2.next();
			WebSitePicture webSitePicture = new WebSitePicture();
			webSitePicture.setPicname(obj.get("name").toString());
			webSitePicture.setPicurl(obj.get("url").toString());
			webSitePicture.setWebsite(website);
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			webSitePicture.setCreatetime(date);
			webSiteList.add(webSitePicture);
		}
		return webSiteList;
	}

	/**
	 * 
	 * 
	 * Title void
	 * Description 爬虫数据保存到数据库
	 *
	 * @author jacun
	 * @date 2017-5-2下午4:29:36
	 * @param imageData
	 * @param website
	 * @param mossionid
	 */
	public static void saveWebsiteMessage(String imageData, String website,
			int missionid) {
		List<WebSitePicture> webSiteList = jsonImageData(imageData,website,missionid);
		
		IMissionService missionService= (IMissionService) SpringUtil.getObject("missionService");
		missionService.insertWebSitePicture(webSiteList);
		
	}

	private static List<WebSitePicture> jsonImageData(String imageData,
			String website, int missionid) {
		List<WebSitePicture> webSiteList = new ArrayList<WebSitePicture>();
		JSONArray ja = JSONArray.parseArray(imageData);
		for (Iterator iterator2 = ja.iterator(); iterator2.hasNext();) {
			JSONObject obj = (JSONObject) iterator2.next();
			WebSitePicture webSitePicture = new WebSitePicture();
			webSitePicture.setPicname(obj.get("name").toString());
			webSitePicture.setPicurl(obj.get("url").toString());
			webSitePicture.setWebsite(website);
			webSitePicture.setId(missionid);
			webSiteList.add(webSitePicture);
		}
		return webSiteList;
	}
}
