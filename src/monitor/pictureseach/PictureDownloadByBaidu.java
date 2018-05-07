package monitor.pictureseach;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.PicProcessByBaidu;
import monitor.pictureutil.ReadProperties;
import monitor.util.SpringUtil;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

public class PictureDownloadByBaidu implements Runnable{

	private dbinfo dbinfo;
	public static IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
	
	public void PictureDownloadByBaidu(dbinfo dbinfo) {
		this.dbinfo= dbinfo;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		ReadProperties readProperties = new ReadProperties();
		String tomcatpath = readProperties.getValueByKey("tomcatpath");
		String add=tomcatpath+ConfigInfo.zipadd+"baidudownload/";
		List<Map<String, String>> result=PicProcessByBaidu.downloadPic(dbinfo.getQuery(), add,10, ""+dbinfo.getMissionid(), ConfigInfo.zipadd);
		File[] zip=new File(ConfigInfo.zipadd).listFiles();
		insertmissionpicture(dbinfo,result);
//		picturetrans(dbinfo,zip);
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
	}
}
