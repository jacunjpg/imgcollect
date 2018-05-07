package monitor.robot.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpClient;
import org.springframework.stereotype.Service;

import monitor.pictureseach.downloadforshudi;
import monitor.pictureseach.fakerunable;
import monitor.pictureseach.shudimission;
import monitor.pictureseach.viewstream;
import monitor.robot.IRobot;
import monitor.util.JsonHelper;
import monitor.util.SpringUtil;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

public class localmissionrobot implements IRobot,Runnable{

	public static IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
	private dbinfo dbinfo;
	static ScheduledExecutorService  fixedThreadPool ;
	static ScheduledExecutorService  stoprobotpool ;
	public localmissionrobot(dbinfo dbinfo)
    {

//		dbinfo.setMissionid(68);
        this.dbinfo = dbinfo;
        System.out.println("构造完成"+JsonHelper.Object2Json(dbinfo));
       	
    }
	
	@Override
	public boolean startrobot() {
		// TODO Auto-generated method stub
		fixedThreadPool = Executors.newSingleThreadScheduledExecutor();
		stoprobotpool = Executors.newSingleThreadScheduledExecutor();
		
			SimpleDateFormat sdf  =new  SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
       		System.out.println(dbinfo.getEndtime());
       		downloadforshudi view = new downloadforshudi(JsonHelper.Object2Json(dbinfo));
       		fixedThreadPool.scheduleAtFixedRate(view, 0 , 60, TimeUnit.SECONDS);//部署线程，每隔1分钟启动一次，立即启动一次
       	//	System.out.println("第一次启动");
       	
		
		return true;
	}

	@Override
	public void stoprobot() {
		// TODO Auto-generated method stub
		System.out.println("if + ="+fixedThreadPool.isTerminated());
		if(fixedThreadPool.isTerminated()){
		fixedThreadPool.shutdown();
		}
		else{
		fixedThreadPool.shutdownNow();
		}
		fixedThreadPool=null;
		System.out.println("停止机器人");
		
	}

	@Override
	public void destoryrobot() {
		// TODO Auto-generated method stub
		stoprobot();
		dbinfo=null;
		fixedThreadPool=null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", ""+dbinfo.getMissionid());
		params.put("style", "3");
		missionService.missioncontrol(params);
		destoryrobot();
		stoprobotpool.shutdownNow();
		stoprobotpool=null;
		System.out.println("机器人自动停止");
		
	}

}

