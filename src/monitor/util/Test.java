package monitor.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import monitor.robot.service.sinasearchRobot;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

public class Test {

	/**
	 * @param args
	 */
//	public static IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
//	public static void main(String[] args) {
//		//获取本机ip
//		String addr = "";
//		try {
//			addr = InetAddress.getLocalHost().getHostAddress();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		Map<String,String> params = new HashMap<String,String>();
//		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date=new Date();
//		params.put("taskid", UUID.randomUUID().toString().replaceAll("-", ""));
//		params.put("missionid", "1");
//		params.put("addr", addr);
//		params.put("createtime", dateFormater.format(date));
//		missionService.insertTaskInfo(params);
//
//	}
	
	public static void main(String[] args) {
//		String dbinfos = "{\"cbkid\":0,\"cbkname\":\"天安门\",\"checkcbk\":\"1\",\"checkmode\":\"6\",\"count\":0,\"datetime\":\"2017-07-13 15:42:34.0\",\"endtime\":\"2017-07-16 15:42:34.0\",\"flag\":\"1\",\"id\":0,\"missionid\":57,\"pictureid\":1,\"point\":0,\"query\":\"大闸蟹还好么\",\"searchmode\":\"www.sinaweibo.com\",\"similarpicutreid\":0,\"style\":\"2\",\"user\":\"admin\"}";
//		dbinfo dbinfo1 = new dbinfo();
//		dbinfo1=(dbinfo) JsonHelper.JSONToObj(dbinfos, dbinfo.class);
//		sinasearchRobot robot = new sinasearchRobot(dbinfo1);
//		robot.startrobot();
//		String js = "{\"token\":\"60u03i1DvVeFe00331b0\",\"exprise\":\"2017-07-13 17:21:01\"}";
//		Map<String,String> map = JsonUtil.getTokenAndEndTime(js);
//		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date=new Date();
//        String nw = dateFormater.format(date);
//        String exprise =  map.get("exprise");
//		String nw = "2017-09-04 16:17:33";
//		String exprise = "";
//        int compareTon = nw.compareTo(exprise);
//        System.out.println(compareTon);
//        System.out.println(exprise);
//        System.out.println(nw);
//		String status_s = "2,3,5";
//		if(!(status_s.indexOf("2")!=-1)){
//			System.out.println("存在");
//		}else{
//			System.out.println("bu存在");
//		}
//		DirectoryUtil directoryUtil = new DirectoryUtil();
//		String starttime="2017-09-04 16:17:33";
//		int num = -3;
//		directoryUtil.plusMinute(starttime,num);
//		String error ="fjdkafjl expired123";
//		if(error.indexOf("expired")!=-1){
//			System.out.println("true");
//		}
		
		 //定义集合
        List<String> list=new ArrayList<String>();
        //给集合存150个值
        for(int x=1;x<=101;x++){
            list.add("a"+x);
        }
        System.out.println("原集合内容："+list);
        List<String> list2=new ArrayList<String>();
         
        //循环获取和移除掉100值
        for(int x=0;x<1;x++){
            //获取
            String a=list.get(x);
            list2.add(a);
            //移除
            list.remove(a);
            //list2集合够100则跳出循环
            if(list2.size()==100){
                 
                break;
            }
            //移除掉list集合一个元素，长度减一，标量应该不变所以减一，后面会x++
            x--;
             
        }
        System.out.println("取出的100值:"+list2);
        //输出剩下的值
        System.out.println("剩下的list值："+list);
		
		
	}

}
