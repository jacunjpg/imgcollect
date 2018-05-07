package monitor.activemq;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import net.sf.json.JSONArray;

import com.google.common.io.Files;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.DirectoryUtil;
import monitor.pictureutil.Pictureobj;
import monitor.pictureutil.ReadProperties;
import monitor.pictureutil.TuPuApi;
import monitor.pictureutil.ZipTest;
import monitor.pictureutil.zidonghuaapi;
import monitor.util.DateUtil;
import monitor.util.JsonHelper;
import monitor.util.JsonUtil;
import monitor.util.SpringUtil;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.ICbjcService;
import monitor.webview.service.IFljcService;
import monitor.webview.service.IMissionService;
import monitor.webview.service.IRljcService;

public class picturecheck {

	public IMissionService missionService= (IMissionService) SpringUtil.getObject("missionService");
	public IFljcService		 fljcService = (IFljcService) SpringUtil.getObject("fljcService");
	public ICbjcService		 cbjcService = (ICbjcService) SpringUtil.getObject("cbjcService");
	public IRljcService	 	 rljcService = (IRljcService) SpringUtil.getObject("rljcService");
	public void checkpictures(dbinfo dbinfo, File file,int fwqStyle) {
		// TODO Auto-generated method stub
		System.out.println(file.getPath());
		if(dbinfo.getCheckmode().contains("1"))
		{
			String result1=Pictureobj.qtpicupload(file, ConfigInfo.shenzhenfwq[fwqStyle], "c1");
			String result=Pictureobj.qtpicsearch(ConfigInfo.shenzhenfwq[fwqStyle], "c1");
			System.out.println("result1="+result1);
			System.out.println("result2="+result);
			if(!result.equals("err"))
			{
			List<dbinfo> list=JsonUtil.imgTestResultConvertList(result);
			for(int i=0;i<list.size();i++)
			{
				list.get(i).setMissionid(dbinfo.getMissionid());
				list.get(i).setCheckmode("1");
				list.get(i).setDatetime(DateUtil.getCurrentTime());
			}
			missionService.insertresult(list);
			SendMessageByMqresult.sendresult(dbinfo, file,list);
			}
		}
		if(dbinfo.getCheckmode().contains("6"))
		{
			String[] cbk=dbinfo.getCheckcbk().split(",");
			for(int i=0;i<cbk.length;i++)
			{
				cbk[i]="new_a"+cbk[i];
			}
			String result1=Pictureobj.picupload(file, ConfigInfo.shenzhenfwq[fwqStyle], "d1");
			String result=Pictureobj.copypicsearch(ConfigInfo.shenzhenfwq[fwqStyle], "d1", cbk);
			System.out.println("result1="+result1);
			System.out.println("result2="+result);
			if(!result.equals("err"))
			{
			List<dbinfo> list=JsonUtil.ChackImgCopyDiscernResultConvertList(result);
			for(int i=0;i<list.size();i++)
			{
				System.out.println(list.get(i).getPicturename()+";"+list.get(i).getPoint()+";"+list.get(i).getSimilarpicturename());
				list.get(i).setMissionid(dbinfo.getMissionid());
				list.get(i).setCheckmode("6");
				list.get(i).setDatetime(DateUtil.getCurrentTime());
			}
			missionService.insertresult(list);
			System.out.println(JsonHelper.Object2Json(list));
			SendMessageByMqresult.sendresult(dbinfo, file , list);
			}
		}
		
		if(dbinfo.getCheckmode().contains("7"))
		{
			String[] cbk=dbinfo.getCheckcbk().split(",");
			for(int i=0;i<cbk.length;i++)
			{
				cbk[i]="new_b"+cbk[i];
			}
			String result1=Pictureobj.picupload(file, ConfigInfo.shenzhenfwq[fwqStyle], "d1");
			String result=Pictureobj.facepicsearch(ConfigInfo.shenzhenfwq[fwqStyle], "d1", cbk);
			System.out.println("result1="+result1);
			System.out.println("result2="+result);
			if(!result.equals("err"))
			{
			List<dbinfo> list=JsonUtil.ChackImgCopyDiscernResultConvertList(result);
			for(int i=0;i<list.size();i++)
			{
				System.out.println(list.get(i).getPicturename()+";"+list.get(i).getPoint()+";"+list.get(i).getSimilarpicturename());
				list.get(i).setMissionid(dbinfo.getMissionid());
				list.get(i).setCheckmode("7");
				list.get(i).setDatetime(DateUtil.getCurrentTime());
			}
			missionService.insertresult(list);
			
			SendMessageByMqresult.sendresult(dbinfo, file,list);
			}
		}
		/**
		 * 本地上传型比对检测，结果存于fljc_result
		 */
		if(dbinfo.getCheckmode().contains("fljc_qt"))
		{
			String result1=Pictureobj.qtpicupload(file, ConfigInfo.shenzhenfwq[fwqStyle], "c1");
			String result=Pictureobj.qtpicsearch(ConfigInfo.shenzhenfwq[fwqStyle], "c1");
			System.out.println("result1="+result1);
			System.out.println("result2="+result);
			if(!result.equals("err"))
			{
			List<dbinfo> list=JsonUtil.imgTestResultConvertList(result);
			for(int i=0;i<list.size();i++)
			{
				list.get(i).setMissionid(dbinfo.getMissionid());
				list.get(i).setDatetime(DateUtil.getCurrentTime());
			}
			fljcService.addPictrueInfo(list);
			SendMessageByMqresult.sendresult(dbinfo, file,list);
			}
		}
		if(dbinfo.getCheckmode().contains("fljc_leader"))
		{
			String[] cbk=new String[1];
			cbk[0]="new_b23";
			String result1=Pictureobj.picupload(file, ConfigInfo.shenzhenfwq[fwqStyle], "d1");
			String result=Pictureobj.facepicsearch(ConfigInfo.shenzhenfwq[fwqStyle], "d1", cbk);
			System.out.println("result1="+result1);
			System.out.println("result2="+result);
			if(!result.equals("err"))
			{
			List<dbinfo> list=JsonUtil.FljcUseInFaceResultConvertList(result);
			for(int i=0;i<list.size();i++)
			{
				System.out.println(list.get(i).getPicturename()+";"+list.get(i).getPoint()+";"+list.get(i).getSimilarpicturename());
				list.get(i).setMissionid(dbinfo.getMissionid());
				list.get(i).setDatetime(DateUtil.getCurrentTime());
			}
			fljcService.addPictrueInfo(list);
			SendMessageByMqresult.sendresult(dbinfo, file,list);
			}
		}
		if(dbinfo.getCheckmode().contains("fljc_SensitivePeople"))
		{
			String[] cbk=new String[1];
			cbk[0]="new_b24";
			String result1=Pictureobj.picupload(file, ConfigInfo.shenzhenfwq[fwqStyle], "d1");
			String result=Pictureobj.facepicsearch(ConfigInfo.shenzhenfwq[fwqStyle], "d1", cbk);
			System.out.println("result1="+result1);
			System.out.println("result2="+result);
			if(!result.equals("err"))
			{
			List<dbinfo> list=JsonUtil.FljcUseInFaceResultConvertList(result);
			for(int i=0;i<list.size();i++)
			{
				System.out.println(list.get(i).getPicturename()+";"+list.get(i).getPoint()+";"+list.get(i).getSimilarpicturename());
				list.get(i).setMissionid(dbinfo.getMissionid());
				list.get(i).setDatetime(DateUtil.getCurrentTime());
			}
			fljcService.addPictrueInfo(list);
			SendMessageByMqresult.sendresult(dbinfo, file,list);
			}
		}
		if(dbinfo.getCheckmode().contains("fljc_baokong"))
		{
			List<dbinfo> list =new ArrayList<dbinfo>();
			try {
				
				ZipTest.unZipFiles(file, ConfigInfo.descadd);
				File[] files=new File(ConfigInfo.descadd).listFiles();
				File[] pics= new File[10];
				for(int i=0;i<files.length;i++)
				{
					pics[i%10]=files[i];
					if((i%10==9&&i>0)||i==files.length-1)
					{
						String result=TuPuApi.tupuapi(pics);
						List<dbinfo> infolist=JsonUtil.tupubaokongResultConvertList(result);
						pics= new File[10];
//						System.out.println(result);
						list.addAll(infolist);
					}
				}
				DirectoryUtil.deleteDir(new File(ConfigInfo.descadd));
				new File(ConfigInfo.descadd).mkdirs();
				System.out.println(list.size());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			for(int i=0;i<list.size();i++)
			{
				System.out.println(list.get(i).getPicturename()+";"+list.get(i).getPoint()+";"+list.get(i).getSimilarpicturename());
				list.get(i).setMissionid(dbinfo.getMissionid());
				list.get(i).setDatetime(DateUtil.getCurrentTime());
			}
			SendMessageByMqresult.sendresult(dbinfo, file,list);
		}
		if(dbinfo.getCheckmode().contains("fljc_sex"))
		{
			List<dbinfo> list =new ArrayList<dbinfo>();
			try {
				
				ZipTest.unZipFiles(file, ConfigInfo.descadd);
				File[] files=new File(ConfigInfo.descadd).listFiles();
				File[] pics= new File[10];
				for(int i=0;i<files.length;i++)
				{
					pics[i%10]=files[i];
					if((i%10==9&&i>0)||i==files.length-1)
					{
						CloseableHttpClient httpClient=zidonghuaapi.initHttpClient("http://api.bit3.cn/api/porn-recog",5000);
						MultipartEntityBuilder builder = zidonghuaapi.createseqingbuilder("50366a6d885cda7dchgx",pics);
						String result=zidonghuaapi.upload(httpClient,"http://api.bit3.cn/api/porn-recog",builder);
						List<dbinfo> infolist=JsonUtil.zidonghuaseqingResultConvertList(result);
						pics= new File[10];
//						System.out.println(result);
						list.addAll(infolist);
					}
				}
				DirectoryUtil.deleteDir(new File(ConfigInfo.descadd));
				new File(ConfigInfo.descadd).mkdirs();
				System.out.println(list.size());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			for(int i=0;i<list.size();i++)
			{
//				System.out.println(list.get(i).getPicturename()+";"+list.get(i).getPoint()+";"+list.get(i).getSimilarpicturename());
				list.get(i).setMissionid(dbinfo.getMissionid());
				list.get(i).setDatetime(DateUtil.getCurrentTime());
			}
			SendMessageByMqresult.sendresult(dbinfo, file,list);
		}
		
		if(dbinfo.getCheckmode().contains("cbjc"))
		{
			String[] cbk=dbinfo.getCheckcbk().split(",");
			for(int i=0;i<cbk.length;i++)
			{
				cbk[i]="new_a"+cbk[i];
			}
			String result1=Pictureobj.picupload(file, ConfigInfo.shenzhenfwq[fwqStyle], "d1");
			String result=Pictureobj.copypicsearch(ConfigInfo.shenzhenfwq[fwqStyle], "d1", cbk);
			System.out.println("result1="+result1);
			System.out.println("result2="+result);
			if(!result.equals("err"))
			{
			List<dbinfo> list=JsonUtil.ChackImgCopyDiscernResultConvertList(result);
			for(int i=0;i<list.size();i++)
			{
				System.out.println(list.get(i).getPicturename()+";"+list.get(i).getPoint()+";"+list.get(i).getSimilarpicturename());
				list.get(i).setMissionid(dbinfo.getMissionid());
				list.get(i).setDatetime(DateUtil.getCurrentTime());
			}
			cbjcService.insertresult(list);
			SendMessageByMqresult.sendresult(dbinfo, file,list);
			}
		}
		if(dbinfo.getCheckmode().contains("rljc"))
		{
			String[] cbk=dbinfo.getCheckcbk().split(",");
			for(int i=0;i<cbk.length;i++)
			{
				cbk[i]="new_b"+cbk[i];
			}
			String result1=Pictureobj.picupload(file, ConfigInfo.shenzhenfwq[fwqStyle], "d1");
			String result=Pictureobj.facepicsearch(ConfigInfo.shenzhenfwq[fwqStyle], "d1", cbk);
			System.out.println("result1="+result1);
			System.out.println("result2="+result);
			if(!result.equals("err"))
			{
			List<dbinfo> list=JsonUtil.ChackImgCopyDiscernResultConvertList(result);
			for(int i=0;i<list.size();i++)
			{
				System.out.println(list.get(i).getPicturename()+";"+list.get(i).getPoint()+";"+list.get(i).getSimilarpicturename());
				list.get(i).setMissionid(dbinfo.getMissionid());
				list.get(i).setDatetime(DateUtil.getCurrentTime());
			}
			rljcService.insertresult(list);
			SendMessageByMqresult.sendresult(dbinfo, file,list);
			}
		}
		
	}
	public void savepictures(dbinfo dbinfo, File file, String listjson) {
		// TODO Auto-generated method stub
		if(dbinfo.getCheckmode().contains("1")||dbinfo.getCheckmode().contains("6")||dbinfo.getCheckmode().contains("7"))
		{
//		JsonHelper.parseJsonString2Object(listjson);
//		System.out.println(jsonlist.size());
		dbinfo.setStyle("0");
		missionService.updatemission(dbinfo);
		String descDir="pages/savedpictures/mission/"+dbinfo.getMissionid()+"/";
		ReadProperties readProperties = new ReadProperties();
		String tomcatpath = readProperties.getValueByKey("tomcatpath");
		File dir=new File(tomcatpath+descDir);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		try {
			ZipTest.unZipFiles(file,ConfigInfo.resultpictures+file.getName()+"/");
			File[] files =new File(ConfigInfo.resultpictures+file.getName()+"/").listFiles();
			List<dbinfo> list = new ArrayList<dbinfo>();
			for(int i=0;i<files.length;i++)
			{
				dbinfo element = new dbinfo();
				Files.copy(files[i],new File(tomcatpath+descDir+files[i].getName()));
				element.setPicturename(files[i].getName());
				element.setPicturepath(descDir+files[i].getName());
				list.add(element);
			}
			missionService.updatepicturepath(list);
			ZipTest.deleteDir(new File(ConfigInfo.resultpictures+file.getName()+"/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jsonarray=JSONArray.fromObject(listjson);
		List<dbinfo> resultlist=JSONArray.toList(jsonarray, dbinfo.class);
		missionService.insertresult(resultlist);
		}
		if(dbinfo.getCheckmode().contains("fljc_qt"))
		{
			JSONArray jsonarray=JSONArray.fromObject(listjson);
			List<dbinfo> resultlist=JSONArray.toList(jsonarray, dbinfo.class);
			fljcService.addPictrueInfo(resultlist);
			dbinfo.setStyle("2");
			fljcService.updatemission(dbinfo);
		}
		if(dbinfo.getCheckmode().contains("fljc_baokong"))
		{
			JSONArray jsonarray=JSONArray.fromObject(listjson);
			List<dbinfo> resultlist=JSONArray.toList(jsonarray, dbinfo.class);
			fljcService.addPictrueInfo(resultlist);
			dbinfo.setStyle("2");
			fljcService.updatemission(dbinfo);
		}
		if(dbinfo.getCheckmode().contains("fljc_sex"))
		{
			JSONArray jsonarray=JSONArray.fromObject(listjson);
			List<dbinfo> resultlist=JSONArray.toList(jsonarray, dbinfo.class);
			fljcService.addPictrueInfo(resultlist);
			dbinfo.setStyle("2");
			fljcService.updatemission(dbinfo);
		}
		if(dbinfo.getCheckmode().contains("fljc_leader"))
		{
			JSONArray jsonarray=JSONArray.fromObject(listjson);
			List<dbinfo> resultlist=JSONArray.toList(jsonarray, dbinfo.class);
			fljcService.addPictrueInfo(resultlist);
			dbinfo.setStyle("2");
			fljcService.updatemission(dbinfo);
		}
		if(dbinfo.getCheckmode().contains("fljc_SensitivePeople"))
		{
			JSONArray jsonarray=JSONArray.fromObject(listjson);
			List<dbinfo> resultlist=JSONArray.toList(jsonarray, dbinfo.class);
			fljcService.addPictrueInfo(resultlist);
			dbinfo.setStyle("2");
			fljcService.updatemission(dbinfo);
		}
		if(dbinfo.getCheckmode().contains("cbjc"))
		{
			JSONArray jsonarray=JSONArray.fromObject(listjson);
			List<dbinfo> resultlist=JSONArray.toList(jsonarray, dbinfo.class);
			cbjcService.insertresult(resultlist);
			dbinfo.setStyle("2");
			cbjcService.updatemission(dbinfo);
		}
		if(dbinfo.getCheckmode().contains("rljc"))
		{
			JSONArray jsonarray=JSONArray.fromObject(listjson);
			List<dbinfo> resultlist=JSONArray.toList(jsonarray, dbinfo.class);
			rljcService.insertresult(resultlist);
			dbinfo.setStyle("2");
			rljcService.updatemission(dbinfo);
		}
	}
	public void uploadcbkpictures(dbinfo dbinfo, File file,int fwqStyle) {
		// TODO Auto-generated method stub
		System.out.println(dbinfo.getCbkname());
		if(dbinfo.getStyle().equals("0"))
		{
			String result=Pictureobj.uploadcbk(file, dbinfo.getStyle(), "new_"+dbinfo.getCbkname(), ConfigInfo.shenzhenfwq[fwqStyle]);
		System.out.println(ConfigInfo.shenzhenfwq[fwqStyle]+"上传图片姐过"+result);
		}
		if(dbinfo.getStyle().equals("1"))
		{	
		String result=Pictureobj.uploadcbk(file, dbinfo.getStyle(), "new_"+dbinfo.getCbkname(), ConfigInfo.shenzhenfwq[fwqStyle]);
		System.out.println(ConfigInfo.shenzhenfwq[fwqStyle]+"fwqStyle"+fwqStyle);
		System.out.println(ConfigInfo.shenzhenfwq[fwqStyle]+"上传图片姐过"+result);
		}
		if(dbinfo.getStyle().equals("3"))
		{
		String result=Pictureobj.deletepicture(ConfigInfo.shenzhenfwq[fwqStyle], "new_"+dbinfo.getCbkname(), dbinfo.getPicturename());
		System.out.println(ConfigInfo.shenzhenfwq[fwqStyle]+"上传图片姐过"+result);
		}
	}

}
