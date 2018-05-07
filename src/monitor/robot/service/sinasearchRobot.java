	package monitor.robot.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import monitor.pictureseach.viewstream;
import monitor.robot.IRobot;
import monitor.util.JsonHelper;
import monitor.util.SpringUtil;
import monitor.webview.entity.Collecttask;
import monitor.webview.service.IMissionService;

import org.apache.log4j.Logger;

public class sinasearchRobot implements IRobot,Runnable{

	private static Logger logger = Logger.getLogger(sinasearchRobot.class);
	public static IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
	private Collecttask collecttask;
	private ScheduledExecutorService  fixedThreadPool ;
	private ScheduledExecutorService  stoprobotpool ;
	private Thread thread;
	private viewstream task;
	public sinasearchRobot(Collecttask collecttask)
    {

        this.collecttask = collecttask;
        logger.debug("构造完成"+JsonHelper.Object2Json(collecttask));
       	
    }
	
	@Override
	public boolean startrobot() {
		if(collecttask.getRobot().contains("www.sinaweibo.com")) {
			try {
				task = new viewstream(JsonHelper.Object2Json(collecttask),collecttask.getMissionid()+"");
				thread=new Thread(task);
				thread.start();
       		} catch (Exception e) {
       			logger.debug("启动机器人线程异常============================="+e.getMessage());
			}
       	}
		
		return true;
	}

	@Override
	public void stoprobot() {
		
		task.stopTask();
		logger.debug("停止机器人");
		
	}

	@Override
	public void destoryrobot() {
		
		stoprobot();
		
	}

	@Override
	public void run() {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", ""+collecttask.getMissionid());
		params.put("style", "3");
		missionService.missioncontrol(params);
		destoryrobot();
		stoprobotpool.shutdownNow();
		stoprobotpool=null;
		System.out.println("机器人自动停止");
		
	}

}

