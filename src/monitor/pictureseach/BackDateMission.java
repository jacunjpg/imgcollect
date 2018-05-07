package monitor.pictureseach;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.ReadProperties;
import monitor.util.DateUtil;
import monitor.util.DirectoryUtil;
import monitor.util.InitSystemServlet;
import monitor.util.JsonHelper;
import monitor.util.SpringUtil;
import monitor.webview.entity.BackTask;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

import org.apache.log4j.Logger;

public class BackDateMission implements Runnable {
	private static Logger logger = Logger.getLogger(BackDateMission.class);
	public static IMissionService missionService = (IMissionService) SpringUtil
			.getObject("missionService");
	private dbinfo info = null;
	private int filecount = 0;
	private boolean ttt = false;
	// 回溯的当前时间
	private String nowTime = "";
	// 回溯的结束时间
	private String endTime = "";

	public BackDateMission(String dinfo, String missionid, String startTime) {
		info = (dbinfo) JsonHelper.JSONToObj(dinfo, dbinfo.class);
		DirectoryUtil directoryUtil = new DirectoryUtil();
		// 获取回溯的开始时间
		this.nowTime = directoryUtil.plusMinuteAtStartTime(-4);//往前推5分钟
//		this.nowTime = startTime;
		// 从任务中获取回溯的结束时间
		this.endTime = directoryUtil.plusHour(ConfigInfo.BACKHOUR);
	}

	@Override
	public void run() {
		while (!ttt) {
			// 结束时间小于追溯到的时间，认为没有追溯完全，继续追溯
			if (endTime.compareTo(nowTime) < 0) {
				logger.debug("任务回溯开始执行----------------------------------"
						+ DateUtil.getCurrentTime());
				backTask();
				logger.debug("任务回溯结束执行----------------------------------"
						+ DateUtil.getCurrentTime());
			}else{
				logger.debug("stop do backdate================");
				ttt = true;
				String missionid = info.getMissionid()+"";
				for (int i = 0; i < InitSystemServlet.backTaskList.size(); i++) {
					BackTask backTask = InitSystemServlet.backTaskList.get(i);
					String taskid = backTask.getTaskid()+"";
					if(missionid.trim().equals(taskid.trim())){
						//停止
						backTask.getSinasearchRobot().destoryrobot();
						InitSystemServlet.backTaskList.remove(i);
					}
				}
			}

		}

	}

	/**
	 * 停止回溯任务
	 */
	public void stopTask() {
		ttt = true;
	}

	private void backTask() {
		
		logger.debug("inter backTask========="+DateUtil.getCurrentTimeMillis());
		DirectoryUtil directoryUtil = new DirectoryUtil();
		String e_time = directoryUtil.plusMinute(nowTime, ConfigInfo.BACKMINUTE);

		logger.debug("backTask getData start========="+DateUtil.getCurrentTimeMillis());
		// 获取1分钟的数据
		List<dbinfo> infoList = missionService.getPicturesByTime(info.getMissionid()+"", nowTime, e_time);
		logger.debug("backTask getData end========="+DateUtil.getCurrentTimeMillis());
		
		// 下次查询的时间从结束时间开始
		nowTime = e_time;
		if (infoList != null && infoList.size() > 0) {
			
			/*
			 * 创建线程方法
			 * //每500条数据创建一个线程处理,
			if(infoList.size() > ConfigInfo.CREATETHREADLIMIT){
				List<dbinfo> infoListOne = new ArrayList<dbinfo>();
				//循环获取和移除掉500值
				logger.debug("backTask infoList >500 ========="+DateUtil.getCurrentTimeMillis());
		        for(int x=0;x<infoList.size();x++){
		            //获取
		            infoListOne.add(infoList.get(x));
		            //infoListOne集合够500则发送mq
		            if(infoListOne.size() == ConfigInfo.CREATETHREADLIMIT){
		            	try {
							new Thread(new BackDataCreateThread(infoListOne, info)).start();
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug("backTask exception ========="+DateUtil.getCurrentTimeMillis()+"e.printStackTrace()"+e.getMessage());
						}
		            	infoListOne = new ArrayList<dbinfo>();
		            }
		        }
		        //发送剩余的数据创建新的线程
		        if(infoListOne != null && infoListOne.size()>0){
		        	try {
						new Thread(new BackDataCreateThread(infoListOne, info)).start();
					} catch (Exception e) {
						e.printStackTrace();
						logger.debug("backTask exception ========="+DateUtil.getCurrentTimeMillis()+"e.printStackTrace()"+e.getMessage());
					}
		        }
			}else{
				logger.debug("backTask infoList <500 ========="+DateUtil.getCurrentTimeMillis());
				//创建新的线程
				try {
					new Thread(new BackDataCreateThread(infoList, info)).start();
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("backTask exception ========="+DateUtil.getCurrentTimeMillis()+"e.printStackTrace()"+e.getMessage());
				}
			}*/
			
			long t0 = new Date().getTime();
			long t1 = t0;
			
			// 判断发送url的来源：微博url还是服务器地址
			ReadProperties readProperties = new ReadProperties();
			String picpathflag = readProperties.getValueByKey("picpathflag");
			List<Map<String, String>> downloadlist = new ArrayList<Map<String, String>>();
			logger.debug("backTask loop start=========size="+infoList.size()+"==="+DateUtil.getCurrentTimeMillis());
			// 遍历返回的结果
			for (dbinfo pinfo : infoList) {
				Map<String, String> picInfo = new HashMap<String, String>();
				// 如果使用weibo图片url,此方法执行
				if (picpathflag.equals("true")) {
					// picturepath存放微博图片的地址url
					picInfo.put("pictureurl", pinfo.getOriginalurl());
				} else {
					// picturepath存放服务器图片的地址
					picInfo.put("pictureurl", pinfo.getPicturepath());
				}
				picInfo.put("picturepath", pinfo.getPicturepath());
				picInfo.put("originalurl", pinfo.getOriginalurl());// 微博图片地址
				picInfo.put("picturetime", pinfo.getPicturetime());// 微博创建时间
				picInfo.put("tasktype", pinfo.getTasktype());// 图片所属的任务类型
				picInfo.put("picturename", pinfo.getPicturename());
				picInfo.put("newName", pinfo.getPicturename());
				picInfo.put("oldUrl", pinfo.getPictureurl());
				picInfo.put("content", pinfo.getContent());

				downloadlist.add(picInfo);
				
				//满1张图片发送mq
				filecount++;
				String psend = readProperties.getValueByKey("psend");
				int ps = Integer.parseInt(psend);
				if (filecount == ps) {
					filecount = 0;
					
					//必须大于100ms才可发送mq
					t1 = new Date().getTime();
					if ((t1 - t0) >= 100) {
						logger.debug("backTask send MQ difference time==相差时间=" + (t1 - t0)+"ms");
						t0 = t1;
						
						logger.debug("backTask send MQ start double========="+DateUtil.getCurrentTimeMillis());
						// 将信息发送到MQ
						SendPicToMQ sendPicToMQ = new SendPicToMQ();
						sendPicToMQ.backSendMessage(info, downloadlist);
						logger.debug("backTask send MQ end double========="+DateUtil.getCurrentTimeMillis());
					}else{
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						logger.debug("backTask send MQ start sleep100========="+DateUtil.getCurrentTimeMillis());
						// 将信息发送到MQ
						SendPicToMQ sendPicToMQ = new SendPicToMQ();
						sendPicToMQ.backSendMessage(info, downloadlist);
						logger.debug("backTask send MQ end sleep100========="+DateUtil.getCurrentTimeMillis());
						
					}
					downloadlist = new ArrayList<Map<String, String>>();
				}
			}

			logger.debug("backTask loop end=========size="+infoList.size()+"==="+DateUtil.getCurrentTimeMillis());
			//不足两个的发送单个
			if(downloadlist.size()>0){
				logger.debug("backTask send MQ start single========="+DateUtil.getCurrentTimeMillis());
				SendPicToMQ sendPicToMQ = new SendPicToMQ();
				sendPicToMQ.backSendMessage(info, downloadlist);
				logger.debug("backTask send MQ end single========="+DateUtil.getCurrentTimeMillis());
			}
		}
		logger.debug("exit backTask========="+DateUtil.getCurrentTimeMillis());

	}

}
