package monitor.pictureutil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import monitor.webview.entity.Collecttask;
import monitor.webview.entity.dbinfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestSinaKeyWords {
	
	private static String token= "60v05V1DUcj72a6e76db";
	private static String idkey = "05322Uq";
	private static int startid = 401;

	public static void main(String[] args) {
		TestSinaKeyWords testSinaKeyWords = new TestSinaKeyWords();
		Collecttask collectTask = new Collecttask();
		collectTask.setMissionid("208");
		try {
			testSinaKeyWords.getTaskDetailByIdkey(token, idkey, startid, collectTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Map<String, String>> getTaskDetailByIdkey(String token,
			String idkey, int startid, Collecttask collectTask)
			throws Exception {


		List<Map<String, String>> picrelist = new ArrayList<Map<String, String>>();
		ReadProperties readProperties = new ReadProperties();
		String tomcatpath = readProperties.getValueByKey("tomcatpath");
		String add = tomcatpath + "pages/savedpictures/mission/"
				+ "xinlangweibo/" + collectTask.getMissionid();

		String scsc2 = getpiclist(token, idkey, startid);
		JSONObject jsonobj = JSONObject.fromObject(scsc2);
		JSONArray jsonlist = JSONArray.fromObject(jsonobj.get("data"));
		List<Map<String, String>> picList = getContentList(scsc2);
		/**
		 * 处理piclist,下图,压缩，入库，发送
		 * 
		 */

		//下载图片
//		piclistdownload(picList, add, collectTask);

		while (jsonlist.size() > 0) {
			String startids = (String) JSONObject.fromObject(jsonlist.get(jsonlist.size() - 1)).get("Id");
			int new_startid = Integer.parseInt(startids) + 1;
			dbinfo dbinfo = new dbinfo();
			dbinfo.setPictureid(new_startid);
			dbinfo.setMissionid(Integer.parseInt(collectTask.getMissionid()));
//			missionService.updatestartid(dbinfo);
			
			scsc2 = getpiclist(token, idkey, startid);
			jsonobj = JSONObject.fromObject(scsc2);
			jsonlist = JSONArray.fromObject(jsonobj.get("data"));
			picList = getContentList(scsc2);
			//下载图片
//			piclistdownload(picList, add, collectTask);
		}

		return picrelist;

	}
	
	private static String getpiclist(String token, String idkey, int startid) {
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
}
