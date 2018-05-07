package monitor.pictureseach;

import org.apache.log4j.Logger;

public class BuildSubmissionLocal implements Runnable{

	private static Logger logger = Logger.getLogger(shudimission.class);
	private String dbinfo;
	
	public BuildSubmissionLocal(String dbinfo){
		this.dbinfo=dbinfo;
	}
	
	@Override
	public void run() {
		logger.debug("启动属地线程============");
		//测试方法
		long time = 999999999;
		String missionid = "missionid";
		Thread thread = new Thread(new shudimission(dbinfo,missionid));
		thread.start();
		logger.debug("启动属地线程完成============");
		
	}

}
