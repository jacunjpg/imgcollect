package monitor.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonHelper {
	
	private static Logger logger = Logger.getLogger(JsonHelper.class);

	public static void main(String[] args) {
//		String json = "{'items':[{'cid':1,'crontab':'0 0/20 10-11 8 5 ?','id':1500,type:'sina.status','ifid':0,'taskid':908,'url':'http://www.hao123.com/'}],'totalRecords':1}";
//		FetchTaskRiceverBean jh = (FetchTaskRiceverBean)parseJson2Object(json,FetchTaskRiceverBean.class);
//		System.out.println(jh.getId()+"---"+jh.getTotalRecords());
//		List<FetchTaskRiceverBean> list = jh.getRlist();
//		for(int i=0;i<list.size();i++){
//			System.out.println(list.get(i).getType()+"---"+list.get(i).getCrontab());
//		}
		String ss = "{\"id\":50011005,\"name\":\"%CE%AA%C1%CB%C4%FA%B5%C4%D5%CA%BA%C5%B0%B2%C8%AB%A3%AC%C7%EB%CA%E4%C8%EB%D1%E9%D6%A4%C2%EB\",info:{\"id\":5252}}";
		//WeiboInter ff = (WeiboInter) JsonHelper.parseJson2Object(ss,WeiboInter.class);
		//System.out.println(ff.getId()+"  name: "+ff.getName());
		//
		parseJsonString2Object(ss);
		
//		JSONObject returnStr = new JSONObject();
//		returnStr.put("success", false);
//		returnStr.put("number", "123yy");
//		returnStr.put("success", true);
//		String ss  = returnStr.toJSONString();
//		System.out.println(ss);
	}
	
	 /**
     * 将json转化为实体POJO
     * @param jsonStr
     * @param obj
     * @return
     */
    public static<T> Object JSONToObj(String jsonStr,Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            t = objectMapper.readValue(jsonStr,
                    obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
	/**
	 * @param jsonStr
	 * jsonStr = "{\"id\":50011005,\"name\":\"\u8bf7\u8f93\u5165\u9a8c\u8bc1\u7801\"}";
	 * 字符串转化成jsonObject对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject parseJsonString2Object(String jsonStr){
		JSONObject jb = JSONObject.parseObject(jsonStr);
		Set s = jb.keySet();
		for (Object str : s) {  
			logger.debug("key=="+str+", value=="+jb.get(str));
			if(str.equals("info")){
//				JSONObject jb2 = JSONObject.toJSONString(jb.getJSONObject(str.toString()));
//				for (Object str2 : s) { 
//					logger.info("key=="+str2+", value=="+jb.get(str2));
//				}
					
			}
		}
		return jb;
	}
	
	/**
	 * 从json字符串中解析出java对象
	 * json1={"name":"ll","age":"20","sex":"男"}
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object parseJson2Object(String jsonStr,Class clazz){
		return JSON.toJavaObject(JSON.parseObject(jsonStr), clazz);
	}
	/**
	 * 输出Ext的Store格式的JSON（用于GridPanel）
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	public static void printJsonList(HttpServletResponse response, List<?> items, long totalRecords) {
		GridStore store = new GridStore();
		store.setTotalRecords(totalRecords);
		store.setItems(items);
		String json = JSON.toJSONString(store);
		response.setContentType("text/json;charset=UTF-8");
		try {
			logger.debug(json);
			response.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出Ext的Store格式的JSON（用于GridPanel）
	 * @param obj
	 * @throws IOException
	 */
	public static String printJsonList2(List<?> items, long totalRecords) {
		GridStore store = new GridStore();
		store.setTotalRecords(totalRecords);
		store.setItems(items);
		String json = JSON.toJSONString(store);
		logger.debug("msg=="+json);
		return json;
	}
	/**
	 * 输出Ext的Store格式的JSON（用于tree）
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	public static void printJsonListfilerStr(HttpServletResponse response, List<?> items, long totalRecords,String filter) {
		GridStore store = new GridStore();
		store.setTotalRecords(totalRecords);
		store.setItems(items);
		String json = JSON.toJSONString(store);
		response.setContentType("text/json;charset=UTF-8");
		try {
			json = json.replace(filter, "");
			response.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 输出对象JSON
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	public static void printJsonObject(HttpServletResponse response,Object obj) {
		String json = JSON.toJSONString(obj);
		response.setContentType("text/html;charset=UTF-8");
		try {
			logger.debug(json);
			response.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	/**
	 * 输出对象JSON
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	public static String Object2Json(Object obj) {
		String json = JSON.toJSONString(obj);
		logger.debug(json);	
		return json;
	}
	/**
	 * 不带总数的json
	 * @param response
	 * @param items
	 * @param totalRecords
	 */
	public static void printJsonList_chartdata(HttpServletResponse response, List<?> items, long totalRecords) {
		GridStore store = new GridStore();
		store.setTotalRecords(totalRecords);
		store.setItems(items);
		String json = JSON.toJSONString(store);
		int st = json.indexOf("[");
		int end = json.lastIndexOf("]");
		json = json.substring(st, end+1);
		response.setContentType("text/json;charset=UTF-8");
		try {
			logger.debug(json);
			response.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 不带总数的json
	 * @param response
	 * @param items
	 * @param totalRecords
	 */
	public static String printJsonList_ChangeTree(HttpServletResponse response, List<?> items, long totalRecords) {
		GridStore store = new GridStore();
		store.setTotalRecords(totalRecords);
		store.setItems(items);
		String json = JSON.toJSONString(store);
		int st = json.indexOf("[");
		int end = json.lastIndexOf("]");
		json = json.substring(st, end+1);
		return json;
	}

	/**
	 * 输出Ext的Store格式的JSON（用于GridPanel）
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	public static void printJsonTreeList(HttpServletResponse response, List<?> items, long totalRecords, String regex, String replacement) {
		GridStore store = new GridStore();
		store.setTotalRecords(totalRecords);
		store.setItems(items);
		String json = JSON.toJSONString(store);
		if (json.contains(regex)) {
			json = json.replaceAll(regex, replacement);
		}
		response.setContentType("text/json;charset=UTF-8");
		try {
			logger.debug(json);
			response.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class GridStore {
	private long totalRecords;
	private List<?> items;
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<?> getItems() {
		return items;
	}
	public void setItems(List<?> items) {
		this.items = items;
	}
}