package monitor.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import monitor.webview.entity.dbinfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
	
	
	public static Map<String,String> getTokenAndEndTime(String scsc2){
		Map<String,String> map = new HashMap<String,String>();
		JSONObject jo = JSON.parseObject(scsc2);
		String token = jo.get("token").toString();
		String exprise = jo.get("exprise").toString();
		map.put("token", token);
		map.put("exprise", exprise);
		return map;
	}
	
	/**
	 * 获取token，保存用户对应的token
	 * username
	 * password
	 * appid
	 * appkey
	 * @param scsc2
	 * @param info
	 * @return
	 */
	public static Map<String,String> getTokenAndEndTime(String scsc2,String info){
		Map<String,String> map = new HashMap<String,String>();
		JSONObject jo = JSON.parseObject(scsc2);
		String token = jo.get("token").toString();
		String exprise = jo.get("exprise").toString();
		map.put("token", token);
		map.put("exprise", exprise);
		map.put("info", info);
		return map;
	}
	
	
	/**
	 * 
	 * 
	 * Title dbinfo Description 拷贝库上传返回结果解析
	 * 
	 * @author jacun
	 * @date 2017-4-14上午10:46:13
	 * @param json
	 * @return
	 */
	public static dbinfo copyLibResultConvertObject(String json) {
		dbinfo info = new dbinfo();
		JSONObject jo = JSON.parseObject(json);
		System.out.println(jo);
		info.setId(Integer.parseInt(jo.get("code").toString()));
		info.setCbkname(jo.get("id").toString());
		info.setCount(Integer.parseInt(jo.get("number").toString()));
		info.setQuery(jo.get("message").toString());
		return info;
	}

	/**
	 * 
	 * 
	 * Title dbinfo Description 过检图片上传
	 * 
	 * @author jacun
	 * @date 2017-4-14上午10:48:15
	 * @param json
	 * @return
	 */
	public static dbinfo ChackImgResultConvertObject(String json) {
		dbinfo info = new dbinfo();
		JSONObject jo = JSON.parseObject(json);
		System.out.println(jo);
		info.setId(Integer.parseInt(jo.get("code").toString()));
		info.setCbkname(jo.get("id").toString());
		info.setPicturename(jo.get("name").toString());
		info.setQuery(jo.get("message").toString());
		return info;
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * Title dbinfo Description 比对识别返回结果解析
	 * 
	 * @author jacun
	 * @date 2017-4-14上午10:53:43
	 * @date 2017-4-24上午9:10:13(修改)
	 * @param json
	 * @return
	 */
	public static List<dbinfo> ChackImgCopyDiscernResultConvertList(
			String json) {
		List<dbinfo> listInfo = new ArrayList<dbinfo>();
		JSONObject jo = JSON.parseObject(json);
		JSONArray ja = JSONArray.parseArray(jo.getString("relatives")
				.toString());
		for (Iterator iterator = ja.iterator(); iterator.hasNext();) {
			JSONObject obj = (JSONObject) iterator.next();
			Map maps = (Map) JSON.parse(obj.toString());
			for (Object map : maps.entrySet()) {
				String key = ((Map.Entry) map).getKey().toString();
				JSONArray ji = JSONArray.parseArray(obj.getString(key)
						.toString());
				for (Iterator iterator2 = ji.iterator(); iterator2.hasNext();) {
					JSONObject obj2 = (JSONObject) iterator2.next();
					dbinfo info = new dbinfo();
					info.setPicturename(key);
					info.setSimilarpicturename(obj2.get("path").toString());
					double point=Double.parseDouble(obj2.get("score").toString());
					point = changepointbycopy(point);
					info.setPoint(point);
					listInfo.add(info);
				}
			}

		}
		return listInfo;
	}
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * Title dbinfo Description 重要敏感人物识别
	 * 
	 * @author 李云龙
	 * @date 2017-4-14上午10:53:43
	 * @date 2017-4-24上午9:10:13(修改)
	 * @date 2017-5-24上午12:43:10(修改)
	 * @param json
	 * @return
	 */
	public static List<dbinfo> FljcUseInFaceResultConvertList(
			String json) {
		List<dbinfo> listInfo = new ArrayList<dbinfo>();
		JSONObject jo = JSON.parseObject(json);
		JSONArray ja = JSONArray.parseArray(jo.getString("relatives")
				.toString());
		for (Iterator iterator = ja.iterator(); iterator.hasNext();) {
			JSONObject obj = (JSONObject) iterator.next();
			Map maps = (Map) JSON.parse(obj.toString());
			for (Object map : maps.entrySet()) {
				String key = ((Map.Entry) map).getKey().toString();
				JSONArray ji = JSONArray.parseArray(obj.getString(key)
						.toString());
				double point1=0;
				dbinfo info = new dbinfo();
				info.setPicturename(key);
				for (Iterator iterator2 = ji.iterator(); iterator2.hasNext();) {
					JSONObject obj2 = (JSONObject) iterator2.next();
					double point=Double.parseDouble(obj2.get("score").toString());
					point = changepointbyface(point);
					if(point1<point)
					point1=point;	
				}
				info.setPoint(point1);
				listInfo.add(info);
			}

		}
		return listInfo;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * Title dbinfo Description 自动化所色情接口结果解析
	 * 
	 * @author 云龙
	 * @date 2017-4-14上午10:53:43
	 * @date 2017-4-24上午9:10:13(修改)
	 * @param json
	 * @return
	 */
	public static List<dbinfo> zidonghuaseqingResultConvertList(
			String json) {
		List<dbinfo> listInfo = new ArrayList<dbinfo>();
		JSONObject jo = JSON.parseObject(json);
		JSONArray ja = JSONArray.parseArray(jo.getString("data")
				.toString());
		
				for (int i=0;i<ja.size();i++) {
					dbinfo info = new dbinfo();
					JSONObject json1 =(JSONObject) ja.get(i);
					info.setPicturename(json1.get("name").toString());
					double point=Double.parseDouble(json1.get("confidence").toString());
					info.setPoint(point);
					listInfo.add(info);
				}
		return listInfo;
	}
	
	
	public static List<dbinfo>tupubaokongResultConvertList(
			String result) {
		List<dbinfo> listInfo = new ArrayList<dbinfo>();
		JSONArray ja = JSONArray.parseArray(result);
				for (int i=0;i<ja.size();i++) {
					dbinfo info = new dbinfo();
					JSONObject json1 =(JSONObject) ja.get(i);
					if(json1.get("label").toString().equals("0"))
					{
						DecimalFormat df = new DecimalFormat("0.000");
						
					info.setPicturename(json1.get("name").toString());
					double point=Double.parseDouble(json1.get("rate").toString());
					info.setPoint(Double.parseDouble(df.format(0.5-point/2)));
					listInfo.add(info);
					}
					else
					{
						DecimalFormat df = new DecimalFormat("0.000");
						info.setPicturename(json1.get("name").toString());
						double point=Double.parseDouble(json1.get("rate").toString());
						info.setPoint(Double.parseDouble(df.format(0.5+point/2)));
						listInfo.add(info);
					}
				}
		return listInfo;
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * Title dbinfo Description 比对识别返回结果解析
	 * 
	 * @author jacun，yunlong
	 * @date 2017-4-14上午10:53:43
	 * @date 2017-4-24上午9:10:13(修改)
	 * @param json
	 * @return
	 */
	public static List<dbinfo> ChackImgFaceDiscernResultConvertList(
			String json) {
		List<dbinfo> listInfo = new ArrayList<dbinfo>();
		JSONObject jo = JSON.parseObject(json);
		JSONArray ja = JSONArray.parseArray(jo.getString("relatives")
				.toString());
		for (Iterator iterator = ja.iterator(); iterator.hasNext();) {
			JSONObject obj = (JSONObject) iterator.next();
			Map maps = (Map) JSON.parse(obj.toString());
			for (Object map : maps.entrySet()) {
				String key = ((Map.Entry) map).getKey().toString();
				JSONArray ji = JSONArray.parseArray(obj.getString(key)
						.toString());
				for (Iterator iterator2 = ji.iterator(); iterator2.hasNext();) {
					JSONObject obj2 = (JSONObject) iterator2.next();
					dbinfo info = new dbinfo();
					info.setPicturename(key);
					info.setSimilarpicturename(obj2.get("path").toString());
					double point=Double.parseDouble(obj2.get("score").toString());
					point = changepointbyface(point);
					info.setPoint(point);
					listInfo.add(info);
				}
			}

		}
		return listInfo;
	}
	
	/**
	 * 
	 * 
	 * Title List<dbinfo> Description 查看指定库中的文件返回值解析
	 * 
	 * @author jacun
	 * @date 2017-4-14下午2:43:25
	 * @param json
	 * @return
	 */
	public static List<dbinfo> viewLibFile(String json) {
		List<dbinfo> listInfo = new ArrayList<dbinfo>();
		JSONObject jo = JSON.parseObject(json);
		System.out.println(jo);
		JSONArray ja = JSONArray
				.parseArray(jo.getString("fileList").toString());
		for (Iterator iterator = ja.iterator(); iterator.hasNext();) {
			JSONObject obj = (JSONObject) iterator.next();
			dbinfo info = new dbinfo();
			info.setPicturepath(obj.get("path").toString());
			listInfo.add(info);
		}
		return listInfo;
	}

	/**
	 * 
	 * 
	 * Title List<Map<String,String>> Description 查看所有拷贝库id返回值解析
	 * 
	 * @author jacun
	 * @date 2017-4-14下午3:10:08
	 * @param json
	 * @return
	 */
	public static List<Map<String, String>> viewCopyLibIdList(String json) {
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		JSONObject jo = JSON.parseObject(json);
		System.out.println(jo);
		// 人脸识别库
		JSONArray fd = JSONArray.parseArray(jo.getString("faceDatabase")
				.toString());
		for (Iterator iterator = fd.iterator(); iterator.hasNext();) {
			Map<String, String> fmap = new HashMap<String, String>();
			JSONObject obj = (JSONObject) iterator.next();
			fmap.put("name", obj.get("name").toString());
			listMap.add(fmap);
		}
		// 拷贝检测库
		JSONArray od = JSONArray.parseArray(jo.getString("objDatabase")
				.toString());
		for (Iterator iterator = od.iterator(); iterator.hasNext();) {
			Map<String, String> omap = new HashMap<String, String>();
			JSONObject obj = (JSONObject) iterator.next();
			omap.put("name", obj.get("name").toString());
			listMap.add(omap);
		}
		return listMap;
	}

	/**
	 * 
	 * 
	 * Title List<dbinfo> Description 群体事件的返回结果，，，，对图像进行检测识别，将多张图片的检测结果
	 * 
	 * @author jacun
	 * @date 2017-4-14下午5:25:53
	 * @date 2017-4-24上午9:15:23(修改)
	 * @param json
	 * @return
	 */
	public static List<dbinfo> imgTestResultConvertList(String json) {
		List<dbinfo> listInfo = new ArrayList<dbinfo>();
		JSONObject jo = JSON.parseObject(json);
		JSONArray ja = JSONArray.parseArray(jo.getString("prob").toString());
		for (Iterator iterator = ja.iterator(); iterator.hasNext();) {
			JSONObject obj = (JSONObject) iterator.next();
			Map maps = (Map) JSON.parse(obj.toString());
			for (Object map : maps.entrySet()) {
				dbinfo info = new dbinfo();
				info.setPicturename(((Map.Entry) map).getKey().toString());
				info.setPoint(Double.parseDouble(((Map.Entry) map).getValue().toString()));
				listInfo.add(info);
			}
		}
		return listInfo;
	}
	
	public static double changepointbycopy(double point)
	{
		point= 1-(point/1.5);
		return point;
	}
	
	public static double changepointbyface(double point)
	{
		double poi=0.8;
		double poi2=1.05;
		double cc = point;
		if (cc < poi) {
			cc = ((poi - cc) / (poi - 0.00))
					* (1.00 - 0.80) + 0.8;
		}
		else if (cc < poi2) {
			cc = ((poi2 - cc) / (poi2 - poi))
					* (0.80 - 0.60) + 0.6;
		}
		else if (cc > poi2) {
			cc = ((1.11 - cc) / (1.11 - 0));
					
		}
		return cc;
	}

}
