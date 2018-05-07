package monitor.activemq;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.PicProcessByBaidu;
import monitor.pictureutil.PicProcessByBdst;
import monitor.pictureutil.PicProcessByQihu;
import monitor.pictureutil.PicProcessBySougou;
import monitor.pictureutil.viewstream;
import monitor.pictureutil.zidonghuaapi2;
import monitor.util.DateUtil;
import monitor.util.HttpDeal;
import monitor.util.JsonHelper;
import monitor.util.SpringUtil;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;
import monitor.webview.service.IZiDonghuaService;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class faketrans {
	
	public IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
	public IZiDonghuaService zdhService  =(IZiDonghuaService) SpringUtil.getObject("zdhService");
	public IZiDonghuaService zdh2Service  =(IZiDonghuaService) SpringUtil.getObject("zdh2Service");
	static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
	static ExecutorService zdhfixedThreadPool = Executors.newFixedThreadPool(10);  
	private static String tomcatadd=ConfigInfo.tomcatpath;
	private static String zipadd=ConfigInfo.zipadd;
	private static String savepath="pages/savedpictures/mission/";
	private static int page=10;
	private static Logger logger = Logger.getLogger(faketrans.class);
	public void missiontrans(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		String searchmode=dbinfo.getSearchmode();
		String query=dbinfo.getQuery();
		String nameid=""+dbinfo.getMissionid();
		
		new File(zipadd).mkdirs();
		if(searchmode.contains("www.baidu.com"))
		{
			System.out.println("百度搜索任务");
			String add=tomcatadd+savepath+"baidudownload/";
			List<Map<String, String>> result=PicProcessByBaidu.downloadPic(query, add,10, nameid, zipadd);
			File[] zip=new File(zipadd).listFiles();
			insertmissionpicture(dbinfo,result);
			picturetrans(dbinfo,zip);
			
		}
		if(searchmode.contains("www.360.com"))
		{
			System.out.println("360搜索任务");
			String add=tomcatadd+savepath+"360download/";
			List<Map<String, String>> result=PicProcessByQihu.downloadPic(query, add, 10, nameid, zipadd);
			File[] zip=new File(zipadd).listFiles();
			insertmissionpicture(dbinfo,result);
			picturetrans(dbinfo,zip);
		}
		if(searchmode.contains("www.sougou.com"))
		{
			System.out.println("Sougou搜索任务");
			String add=tomcatadd+savepath+"sougou/";
			List<Map<String, String>> result=PicProcessBySougou.downloadPic(query, add, 10, nameid, zipadd);
			File[] zip=new File(zipadd).listFiles();
			insertmissionpicture(dbinfo,result);
			picturetrans(dbinfo,zip);
		}
		if(searchmode.contains("www.sinaweibo.com"))
		{
			System.out.println("新浪微博搜索任务");
			String dbinfo2=JsonHelper.Object2Json(dbinfo);
			System.out.println(dbinfo2);
			viewstream view=new viewstream(dbinfo2);
//			Thread thread = new Thread(view);
//	        thread.start();
	        fixedThreadPool.execute(view);
		}
		
		if(searchmode.contains("www.baiduwangye.com"))
		{
			System.out.println("百度网页搜索任务");
			String url = "http://localhost:8088/fetch3.0/bdfetch/saveKey.do";
			Map<String,String> params1 = new HashMap<String,String>();
			params1.put("keyword", query);
			String json  = JsonHelper.Object2Json(dbinfo);
			params1.put("yid", ""+dbinfo.getMissionid());
			HttpDeal.post(url,params1);
			System.out.println(query);
		}
		//西祠
		if(searchmode.contains("www.xici.net"))
		{
			System.out.println("西祠");
			String url = "http://115.34.135.30:8080/fetch0/pic";
//			String url = "http://192.168.20.110:8080/fetch0/pic";
			Map<String,String> params1 = new HashMap<String,String>();
			params1.put("links", "http://www.xici.net");
			String json  = JsonHelper.Object2Json(dbinfo);
			params1.put("dbinfo", json);
			HttpDeal.post(url,params1);
			System.out.println(query);
		}
		//新高淳
		if(searchmode.contains("www.xingaochun.com"))
		{
			System.out.println("xingaochun");
			String url = "http://115.34.135.30:8080/fetch0/pic";
//			String url = "http://192.168.20.110:8080/fetch0/pic";
			Map<String,String> params1 = new HashMap<String,String>();
			params1.put("links", "http://www.xingaochun.com/forum.php");
			String json  = JsonHelper.Object2Json(dbinfo);
			params1.put("dbinfo", json);
			HttpDeal.post(url,params1);
			System.out.println(query);
		}
		if(searchmode.contains("bdst"))
		{
			System.out.println("百度识图");
			String add=tomcatadd+savepath+"bdst/";
			//  测试用query
			//			query="http://115.34.135.30:8080/pic/pages/picdownload/baidu/148/52017.jpg";
			List<Map<String, String>> result=PicProcessByBdst.downloadPic(query, add, 1, nameid, zipadd);
			File[] zip=new File(zipadd).listFiles();
			insertmissionpicture(dbinfo,result);
			picturetrans(dbinfo,zip);
		}
		//华侨路茶坊
		if(searchmode.contains("www.huaqiaoluchafang.com"))
		{
			String url = "http://115.34.135.30:8080/fetch0/pic";
			Map<String,String> params1 = new HashMap<String,String>();
			params1.put("links", "http://bbs.house365.com/index.html");
			String json  = JsonHelper.Object2Json(dbinfo);
			params1.put("dbinfo", json);
			HttpDeal.post(url,params1);
			System.out.println(query);
		}
		//极速社区
		if(searchmode.contains("www.jisushequ.com"))
		{
			String url = "http://115.34.135.30:8080/fetch0/pic";
			Map<String,String> params1 = new HashMap<String,String>();
			params1.put("links", "http://bbs.8080.net/forum.php");
			String json  = JsonHelper.Object2Json(dbinfo);
			params1.put("dbinfo", json);
			HttpDeal.post(url,params1);
			System.out.println(query);
		}
		//龙虎网新闻
		if(searchmode.contains("www.longhuwang.com"))
		{
			String url = "http://115.34.135.30:8080/fetch0/pic";
			Map<String,String> params1 = new HashMap<String,String>();
			params1.put("links", "http://mynews.longhoo.net/portal.php");
			String json  = JsonHelper.Object2Json(dbinfo);
			params1.put("dbinfo", json);
			HttpDeal.post(url,params1);
			System.out.println(query);
		}
		//溧水114
		if(searchmode.contains("www.lishui.com"))
		{
			String url = "http://115.34.135.30:8080/fetch0/pic";
			Map<String,String> params1 = new HashMap<String,String>();
			params1.put("links", "http://www.ls114.cn/forum.php");
			String json  = JsonHelper.Object2Json(dbinfo);
			params1.put("dbinfo", json);
			HttpDeal.post(url,params1);
			System.out.println(query);
		}
		//美篇网
		if(searchmode.contains("www.meipianwang.com"))
		{
			String url = "http://115.34.135.30:8080/fetch0/pic";
			Map<String,String> params1 = new HashMap<String,String>();
			params1.put("links", "https://www.meipian.cn");
			String json  = JsonHelper.Object2Json(dbinfo);
			params1.put("dbinfo", json);
			HttpDeal.post(url,params1);
			System.out.println(query);
		}
		//聚优秀
		if(searchmode.contains("www.juyouxiu.com"))
		{
			String url = "http://115.34.135.30:8080/fetch0/pic";
			Map<String,String> params1 = new HashMap<String,String>();
			params1.put("links", "http://www.juyouxiu.com");
			String json  = JsonHelper.Object2Json(dbinfo);
			params1.put("dbinfo", json);
			HttpDeal.post(url,params1);
			System.out.println(query);
		}
		//微民网
		if(searchmode.contains("www.weiminwang.com"))
		{
			String url = "http://115.34.135.30:8080/fetch0/pic";
			Map<String,String> params1 = new HashMap<String,String>();
			params1.put("links", "http://www.vimiy.com");
			String json  = JsonHelper.Object2Json(dbinfo);
			params1.put("dbinfo", json);
			HttpDeal.post(url,params1);
			System.out.println(query);
		}
		//大中网
		if(searchmode.contains("www.dazhongwang.com"))
		{
			String url = "http://115.34.135.30:8080/fetch0/pic";
			Map<String,String> params1 = new HashMap<String,String>();
			params1.put("links", "http://www.ffyk.cn");
			String json  = JsonHelper.Object2Json(dbinfo);
			params1.put("dbinfo", json);
			HttpDeal.post(url,params1);
			System.out.println(query);
		}
	}
	
	//更新任务状态为开始抓取
	public void updateMissionStatus(int missionid){
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid+"");
		params.put("style", "2");
		missionService.missioncontrol(params);
	}
	
	private void picturetrans(dbinfo dbinfo, File[] zip) {
		// TODO Auto-generated method stub
		for(int i=0;i<zip.length;i++)
		{
//		SendMessageByMq.sendpicture(dbinfo,zip[i]);
		zip[i].delete();
		}	
	}

	private void insertmissionpicture(dbinfo dbinfo,
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
			picture.setPicturename(result.get(i).get("newName"));
			picture.setPictureurl(result.get(i).get("oldUrl"));
			picture.setWeb(oldUrl);
			picture.setMissionid(dbinfo.getMissionid());
			picturelist.add(picture);
		}
		Map<String, String> params = new HashMap<String, String>();
		
		if(picturelist!=null)
		{
		missionService.insertpicture(picturelist);
		}
		System.out.println(ConfigInfo.tomcatpath);
	}

	public void zidonghuaapi(dbinfo dbinfo,  HttpClient client) {
		// TODO Auto-generated method stub
//		zidonghuaapi zdh=new zidonghuaapi();
		System.out.println(dbinfo);
		List<dbinfo> resultlist= new ArrayList<dbinfo>();
		List<dbinfo> cbkpicture= new ArrayList<dbinfo>();
		Map<String, String> params=new HashMap<String, String>();
		params.put("cbkid", ""+dbinfo.getCbkid());
		Double point = -1.0;
		dbinfo pictureresult =new dbinfo();
		File file1 =new File(dbinfo.getPicturepath());
		pictureresult.setPicturename(file1.getName());
		pictureresult.setMissionid(dbinfo.getMissionid());
		pictureresult.setDatetime(DateUtil.getCurrentTime());
		if (dbinfo.getCheckmode().toString().equals("face"))
		{
		cbkpicture=zdhService.getcbkpicbyid(params);
		}
		else
		{
		cbkpicture=zdh2Service.getcbkpicbyid(params);
		}
		////		logger.info(cbkpicture.size());
		logger.info("sendpicturetoapi==="+file1.getName());
		for(int i=0;i<cbkpicture.size();i++)
		{	
			
//			HttpClient httpClient=zidonghuaapi2.initHttpClient("http://api.bit3.cn/api/img-comp",50000, client);
			Part[] builder = zidonghuaapi2.creatbuilder("f91a7f0a8457904fchgx",file1,new File(ConfigInfo.tomcatpath+cbkpicture.get(i).getPicturepath()),dbinfo.getCheckmode());
			logger.info("sendpicture=the "+i+" time =="+file1.getName());
			String result=zidonghuaapi2.upload(client,"http://api.bit3.cn/api/img-comp",builder);
//			String result=zidonghuaapi2.upload(client,"http://www.baidu.com",builder);
			logger.info("receivepicture=the "+i+" time =="+file1.getName());
			System.out.println(file1.getName()+"::"+result);
//			if(!result.equals("err"))
//			{
			JSONObject json = JSONObject.parseObject(result);
//			logger.info(result);
			if(point<Double.parseDouble(json.get("similarity").toString()))
				point = Double.parseDouble(json.get("similarity").toString());
//			point =point +Double.parseDouble(json.get("similarity").toString());
////		ConfigInfo.tomcatpath+cbkpicture.get(i).
//			}else
//			{
//				zidonghuaapi(dbinfo);
//			}
		}
////		point =point/cbkpicture.size();
		logger.info("getresultformapi"+file1.getName());
		pictureresult.setPoint(point);
		resultlist.add(pictureresult);
		if (dbinfo.getCheckmode().equals("face"))
		{
			zdhService.insertresult(resultlist);
			dbinfo picturecount=zdhService.getpicturecount(dbinfo);
			dbinfo resultcount =zdhService.getresultcount(dbinfo);
			if(picturecount.getPictureid()==resultcount.getPictureid())
			{
			dbinfo.setStyle("2");
			zdhService.updatemission(dbinfo);
			logger.info("Taskend==="+DateUtil.getCurrentTime());
			}
		}
		else
		{
			zdh2Service.insertresult(resultlist);
			dbinfo picturecount=zdh2Service.getpicturecount(dbinfo);
			dbinfo resultcount =zdh2Service.getresultcount(dbinfo);
			if(picturecount.getPictureid()==resultcount.getPictureid())
			{
			dbinfo.setStyle("2");
			zdh2Service.updatemission(dbinfo);
			logger.info("taskEnd==="+DateUtil.getCurrentTime());
			}
		}
		
	}

	public void insertDetectTask(dbinfo dbinfo) {
		logger.info("insertDetectTask==="+DateUtil.getCurrentTime());
		missionService.insertDetectTask(dbinfo);
		
	}

	
}
