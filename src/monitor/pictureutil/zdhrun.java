package monitor.pictureutil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import monitor.activemq.faketrans;
import monitor.util.DateUtil;
import monitor.util.JsonHelper;
import monitor.util.SpringUtil;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IZiDonghuaService;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class zdhrun implements Runnable{
	public IZiDonghuaService zdhService  =(IZiDonghuaService) SpringUtil.getObject("zdhService");
	public IZiDonghuaService zdh2Service  =(IZiDonghuaService) SpringUtil.getObject("zdh2Service");
	private static Logger logger = Logger.getLogger(zdhrun.class);
	private String dbinfo;
	private HttpClient client;
	public zdhrun(String dbinfo, HttpClient client)
    {
        this.dbinfo = dbinfo;
        this.client = client;
    }
    public void run()
    {
    	dbinfo dbinfo2 = (dbinfo) JsonHelper.JSONToObj(dbinfo, dbinfo.class);
    	zidonghuaapi(dbinfo2,client);
//		pictureresult.setPoint(point);
    }
    public static void main(String[] args)
    {
    	for(int i=0;i<10;i++)
    	{
//        zdhrun zdrun = new zdhrun();
////        zdrun.setName("world"+Math.random());
//        Thread thread = new Thread(zdrun);
//        thread.start();
    	}
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
			logger.info("senstart =the "+i+" time =="+file1.getName());
			
			ReadProperties readProperties = new ReadProperties();
			String tomcatpath = readProperties.getValueByKey("tomcatpath");
//			HttpClient httpClient=zidonghuaapi2.initHttpClient("http://api.bit3.cn/api/img-comp",50000, client);
			Part[] builder = zidonghuaapi2.creatbuilder("f91a7f0a8457904fchgx",new File(tomcatpath+cbkpicture.get(i).getPicturepath()),file1,dbinfo.getCheckmode());
			String result=zidonghuaapi2.upload(client,"http://api.bit3.cn/api/img-comp",builder);
			logger.info("result =the "+i+" time =="+result);
			logger.info("sendend =the "+i+" time =="+file1.getName());
//			System.out.println(file1.getName()+"::"+result);
//			if(!result.equals("err"))
//			{r
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
}
