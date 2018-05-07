package monitor.robot.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import monitor.pictureseach.BackDateMission;
import monitor.pictureseach.shudimission;
import monitor.robot.IRobot;
import monitor.util.JsonHelper;
import monitor.util.SpringUtil;
import monitor.webview.entity.Collecttask;
import monitor.webview.service.IMissionService;

import org.apache.log4j.Logger;

public class sinalocalsearchRobot implements IRobot,Runnable{
	private static Logger logger = Logger.getLogger(sinalocalsearchRobot.class);
	public static IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
	private Collecttask collecttask;
	private ScheduledExecutorService  fixedThreadPool ;
	private ScheduledExecutorService  stoprobotpool ;
	private Thread thread;
	private shudimission task;
	
	public sinalocalsearchRobot(Collecttask collecttask)
    {

        this.collecttask = collecttask;
        System.out.println("构造完成"+JsonHelper.Object2Json(collecttask));
       	
    }
	@Override
	public boolean startrobot() {
//		fixedThreadPool = Executors.newSingleThreadScheduledExecutor();
//		stoprobotpool = Executors.newSingleThreadScheduledExecutor();
		if(collecttask.getRobot().contains("www.sinalocalmission"))
       	{
			logger.debug("启动属地化任务=============================Missionid==="+collecttask.getMissionid());
			try {
//				SimpleDateFormat sdf  =new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
				logger.debug("启动采集任务=============================");
				task = new shudimission(JsonHelper.Object2Json(collecttask),collecttask.getMissionid()+"");
				thread=new Thread(task);
				thread.start();
				
//       		fixedThreadPool.scheduleAtFixedRate(view, 0 , 1, TimeUnit.SECONDS);//部署线程，每隔1s启动一次，立即启动一次
	       		logger.debug("部署线程，每隔1s启动一次=============================");
//				stoprobotpool.scheduleAtFixedRate(this, (sdf.parse(dbinfo.getEndtime()).getTime()-new Date().getTime()), 60*60*1000, TimeUnit.MILLISECONDS);
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

	/**
	 * 销毁机器人
	 */
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
		logger.debug("机器人自动停止=============================");
	}

}

