package monitor.robot.service;

import monitor.pictureseach.BackDateMission;
import monitor.robot.IRobot;
import monitor.util.JsonHelper;
import monitor.webview.entity.dbinfo;

import org.apache.log4j.Logger;

public class SinaLocalBackRobot implements IRobot,Runnable {
	private static Logger logger = Logger.getLogger(SinaLocalBackRobot.class);
	private BackDateMission backTask;
	private Thread thread;
	private dbinfo info;
	public SinaLocalBackRobot(dbinfo info){
		this.info=info;
	}
	
	@Override
	public boolean startrobot() {
		logger.debug("启动回溯任务=============================");
		backTask = new BackDateMission(JsonHelper.Object2Json(info),info.getMissionid()+"",info.getRemark());
		thread=new Thread(backTask);
		thread.start();
		return true;
	}

	@Override
	public void stoprobot() {
		backTask.stopTask();
	}

	@Override
	public void destoryrobot() {
		stoprobot();
	}
	
	@Override
	public void run() {
		System.out.println("run");
	}

}
