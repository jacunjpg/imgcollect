package monitor.robot.service;

import monitor.pictureseach.BaiduOfStream;
import monitor.pictureseach.SougouOfStream;
import monitor.robot.IRobot;
import monitor.util.JsonHelper;
import monitor.webview.entity.Collecttask;

import org.apache.log4j.Logger;

public class SougouSearchRobot implements IRobot, Runnable {
	
	private static Logger logger = Logger.getLogger(SougouSearchRobot.class);
	private Collecttask collecttask;
	private Thread thread;
	private SougouOfStream task;
	
	public SougouSearchRobot(Collecttask collecttask){
		this.collecttask = collecttask;
        logger.debug("构造完成"+JsonHelper.Object2Json(collecttask));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean startrobot() {
		if(collecttask.getRobot().indexOf("sougou")!=-1) {
			String keywords = collecttask.getRobot().substring(collecttask.getRobot().indexOf("|")+1);
			try {
				task = new SougouOfStream(JsonHelper.Object2Json(collecttask), keywords, collecttask.getMissionid()+"",collecttask);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void destoryrobot() {
		// TODO Auto-generated method stub

	}

}
