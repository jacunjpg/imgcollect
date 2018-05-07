package monitor.pictureutil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import monitor.util.SpringUtil;
import monitor.webview.entity.PictureInfo;
import monitor.webview.service.IAuthorityService;
import monitor.webview.service.IFaceDetectService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class SaveMqData{

	public static void main(String[] args) {
		String json = "[{\"name\":\"40013386.jpg\",\"url\":\"http://h.hiphotos.baidu.com/b0323e5.jpg\"}]";
		saveData(json);
	}
	
	public static void saveData(String json) {
		JSONArray jarr = JSONArray.parseArray(json);
		List<PictureInfo> list = new ArrayList<PictureInfo>();
		for (Iterator<Object> iterator = jarr.iterator(); iterator.hasNext();) {
			PictureInfo pictureInfo = new PictureInfo();
			JSONObject job = (JSONObject) iterator.next();
			pictureInfo.setPicturename((String) job.get("name"));
			pictureInfo.setPicturepath("lj");
			pictureInfo.setPictureurl((String) job.get("url"));
			pictureInfo.setWeb("baidu00");
			String name = (String) job.get("name");
			String url = (String) job.get("url");
			System.out.println("name" + name);
			System.out.println("url" + url);
			list.add(pictureInfo);
		}

		// 保存到数据库
		IFaceDetectService faceDetectService = (IFaceDetectService) SpringUtil.getObject("faceDetectService");
		faceDetectService.addPictrueInfo(list);
		System.out.println(faceDetectService);
	}

}
