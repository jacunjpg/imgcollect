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
import monitor.webview.service.IMissionService;
import monitor.webview.service.IZiDonghuaService;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class zdhrun2 implements Runnable{
		public static IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
		public IZiDonghuaService zdhService  =(IZiDonghuaService) SpringUtil.getObject("zdhService");
		public IZiDonghuaService zdh2Service  =(IZiDonghuaService) SpringUtil.getObject("zdh2Service");
		private static Logger logger = Logger.getLogger(zdhrun.class);
		private dbinfo dbinfo;
		private HttpClient client;
		public zdhrun2(dbinfo dbinfo, HttpClient client)
	    {
	        this.dbinfo = dbinfo;
	        this.client = client;
	    }
	    public void run()
	    {
//	    	dbinfo dbinfo2 = (dbinfo) JsonHelper.JSONToObj(dbinfo, dbinfo.class);
	    	zidonghuaapi(dbinfo,client);
//			pictureresult.setPoint(point);
	    }
	    public static void main(String[] args)
	    {
	    	for(int i=0;i<10;i++)
	    	{
//	        zdhrun zdrun = new zdhrun();
////	        zdrun.setName("world"+Math.random());
//	        Thread thread = new Thread(zdrun);
//	        thread.start();
	    	}
	    }
	    
	    public void zidonghuaapi(dbinfo dbinfo,  HttpClient client) {
			// TODO Auto-generated method stub
//			zidonghuaapi zdh=new zidonghuaapi();
//			System.out.println(dbinfo);
			List<dbinfo> resultlist= new ArrayList<dbinfo>();
			List<dbinfo> cbkpicture= new ArrayList<dbinfo>();
			Map<String, String> params=new HashMap<String, String>();
			File file1 =new File(dbinfo.getPicturepath());
			String[] cbks=dbinfo.getCheckcbk().split(",");
//			logger.info("cbkname"+dbinfo.getCbkname());
			String[] cbknames=dbinfo.getCbkname().split(",");
			String cbkname="";
			String similarpicturename="";
			Double point = -1.0;
			dbinfo pictureresult =new dbinfo();
			pictureresult.setPicturename(file1.getName());
			pictureresult.setMissionid(dbinfo.getMissionid());
			pictureresult.setDatetime(DateUtil.getCurrentTime());
			for(int f=0;f<cbks.length;f++)
			{
			params.put("cbkid", ""+cbks[f]);
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
//				HttpClient httpClient=zidonghuaapi2.initHttpClient("http://api.bit3.cn/api/img-comp",50000, client);
				Part[] builder = zidonghuaapi2.creatbuilder("f91a7f0a8457904fchgx",file1,new File(tomcatpath+cbkpicture.get(i).getPicturepath()),dbinfo.getCheckmode());
				String result=zidonghuaapi2.upload(client,"http://api.bit3.cn/api/img-comp",builder);
				logger.info("sendend =the "+i+" time =="+file1.getName());
//				System.out.println(file1.getName()+"::"+result);
//				if(!result.equals("err"))
//				{r
				JSONObject json = JSONObject.parseObject(result);
//				logger.info(result);
				if(point<Double.parseDouble(json.get("similarity").toString()))
				{
					point = Double.parseDouble(json.get("similarity").toString());
					cbkname=cbknames[f];
					similarpicturename=cbkpicture.get(i).getPicturename();
				}
				
//				point =point +Double.parseDouble(json.get("similarity").toString());
////			ConfigInfo.tomcatpath+cbkpicture.get(i).
//				}else
//				{
//					zidonghuaapi(dbinfo);
//				}
			}
////			point =point/cbkpicture.size();
		}
			
			logger.info("getresultformapi"+file1.getName());
			pictureresult.setPoint(point);
			pictureresult.setCbkname(cbkname);
			pictureresult.setSimilarpicturename(similarpicturename);
			resultlist.add(pictureresult);
			if (dbinfo.getCheckmode().equals("face"))
			{
				missionService.insertresult(resultlist);
				logger.info("Taskend==="+DateUtil.getCurrentTime());
			}
			else
			{
				missionService.insertresult(resultlist);
				logger.info("taskEnd==="+DateUtil.getCurrentTime());
			}
	    }
	}

