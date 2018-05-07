package monitor.pictureseach;

import java.util.Date;

import monitor.pictureutil.ConfigInfo;
import monitor.util.SpringUtil;
import monitor.webview.entity.Collecttask;
import monitor.webview.service.IAuthorityService;
import monitor.webview.service.IMissionService;

import org.apache.log4j.Logger;

public class SougouOfStream implements Runnable {
	private static Logger logger = Logger.getLogger(SougouOfStream.class);
	public static IMissionService missionService = (IMissionService) SpringUtil
			.getObject("missionService");
	public static IAuthorityService authorityService = (IAuthorityService) SpringUtil
			.getObject("authorityService");
	private String collectask;
	private String missionid;
	private boolean stopFlag = false;
	private String query = "";
	private int page = 0;
	private Collecttask collectTask;
	
	public SougouOfStream(String collectask, String query, String missionid, Collecttask collectTask) {
		this.collectask = collectask;
		this.collectTask = collectTask;
		this.missionid = missionid;
		this.query = query;
		this.page=Integer.parseInt(ConfigInfo.BDPAGENUM);
	}

	public void run() {

		long t0 = new Date().getTime();
		long t1 = t0;
		int num = 0;
		while (!stopFlag) {
			
			t1 = new Date().getTime();
			if ((t1 - t0) > 1000 * 2) {
				logger.debug("相差时间" + (t1 - t0));
				t0 = t1;
				runTask(num);
				num++;
			}
			
			if(num>page){
				stopFlag = true;
			}
		}

	}

	public void stopTask() {
		stopFlag = true;
	}

	public void runTask(int num) {
		try {
			logger.debug("sougou_robot");
			
			new TupianProcessBySougou().downloadPic(query, num, missionid, collectTask);
			
		} catch (Exception e) {
			logger.error("sougou_robot interrupt++++++++++++++++++++"
					+ e.getMessage());

		}
	}


}
