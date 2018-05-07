package monitor.robot.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.ReadProperties;
import monitor.util.ExportExcel;
import monitor.webview.entity.LoginUser;
import monitor.webview.entity.dbinfo;

public class test {

	public static void main(String[] args)
	{
		
		String str = "j|fdshja";
		System.out.println(str.substring(str.indexOf("|")+1));
//		String endtime="2017-06-15 14:49:45.0";
//		SimpleDateFormat sdf  =new  SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
//		try {
//			Date date = sdf.parse(endtime);
//			Date datenow =new Date();
//			System.out.println(date);
//			System.out.println(datenow);
//			System.out.println(date.getTime()-datenow.getTime());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static String exportExcel( List<List<Object>> list,String filename) {
		// 创建列标头LIST
		List<String> fialList = new ArrayList<String>();
		fialList.add("序号");
		fialList.add("过检时间");
		fialList.add("最佳推测");
		fialList.add("符合度");
		fialList.add("原文网址");
		
		ReadProperties readProperties = new ReadProperties();
		String tomcatpath = readProperties.getValueByKey("tomcatpath");
		String fileName = tomcatpath+ "pages/download/";
		new File(fileName).mkdirs();
		fileName=fileName+filename+".xls";
		Map<String, Object> temp = new HashMap<String, Object>();
		ExportExcel.exportExcel(fialList, list, temp, fileName, "图像检测结果");
		return fileName;
	}
	
	public static List<List<Object>> formatListresult(List<dbinfo> list,LoginUser userinfo){
		List<List<Object>> result = new ArrayList<List<Object>>();
		for(int i=0;i<list.size();i++){
				List<Object> temp = new ArrayList<Object>();
				dbinfo p = list.get(i);
			
				temp.add(""+(i+1));

				if(p.getDatetime()!=null && !p.getDatetime().equals("")){
					temp.add(p.getDatetime().substring(0, 19));
				}else{
					temp.add("--");
				}
			
			
				if(p.getPoint()>userinfo.getBorder1())
				{
					temp.add("极似 "+p.getCbkname());					
				}
				else if(p.getPoint()>userinfo.getBorder2()&&p.getPoint()<=userinfo.getBorder1())
				{
					temp.add("疑似 "+p.getCbkname());	
				}
				else if(p.getPoint()<=userinfo.getBorder2()&&p.getPoint()>=0)
				{
					temp.add("初步推测不相关");
				}
				else if(p.getPoint()==-1)
				{
					temp.add("无法识别");
				}
				else
				{
					temp.add("--");
				}
				if(p.getPoint()!=0){
					temp.add((""+p.getPoint()*100).substring(0, 6));
				}else{
					temp.add("0.000");
				}
							
				
				if(p.getPictureurl()!=null && !p.getPictureurl().equals("")){
					temp.add(p.getPictureurl());
				}else{
					temp.add("--");
				}
				
				result.add(temp);
		}
		return result;
	}
}
