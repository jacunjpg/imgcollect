package monitor.webview.entity;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import monitor.pictureseach.SendPicToMQForKeyWord;
import monitor.pictureutil.ConfigInfo;
import monitor.util.SpringUtil;
import monitor.webview.service.IMissionService;


public class RobotManage {
	private static Logger logger = Logger.getLogger(RobotManage.class);
	private int num;

	public IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");

	
	public void createRobot(){
		ConfigInfo.NUM--;
//		sinasearchRobot robot = new sinasearchRobot(missioninfo);
		logger.debug("消费了一个====="+ConfigInfo.NUM);

	}
	
	public void stopRobot(int taskid){
		ConfigInfo.NUM++;
		logger.debug("停止了一个任务====="+taskid);
		logger.debug("创建了一个====="+ConfigInfo.NUM);
		
	}
	
	public int getNum() {
		num = ConfigInfo.NUM;
		return num;
	}
	

	//remark 0普通任务，1属地任务
	public String saveRobot(dbinfo dbinfo,String robot,String remark){
		String robotid = UUID.randomUUID().toString().replaceAll("-", "");
		//获取本机ip
		String addr = "";
		try {
			addr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Map<String,String> params = new HashMap<String,String>();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
		params.put("taskid", robotid);
		params.put("missionid", dbinfo.getMissionid()+"");
		params.put("robot", robot);
		params.put("addr", addr);
		params.put("remark", remark);
		params.put("updatetime", dbinfo.getEndtime());
		params.put("createtime", dateFormater.format(date));
		missionService.insertTaskInfo(params);
		return robotid;
	}
	
		//remark 0普通任务
		public String saveSearchRobot(dbinfo dbinfo,String robot,String remark){
			String robotid = UUID.randomUUID().toString().replaceAll("-", "");
			//获取本机ip
			String addr = "";
			try {
				addr = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			Map<String,String> params = new HashMap<String,String>();
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date=new Date();
			params.put("taskid", robotid);
			params.put("missionid", dbinfo.getMissionid()+"");
			params.put("robot", robot+"|"+dbinfo.getQuery());
			params.put("addr", addr);
			params.put("remark", remark);
			params.put("updatetime", dbinfo.getEndtime());
			params.put("createtime", dateFormater.format(date));
			missionService.insertTaskInfo(params);
			return robotid;
		}
	
	//根据机器人的id获取机器人的信息
	public Collecttask getCollecttaskById(String taskid){
		Map<String,String> params = new HashMap<String,String>();
		params.put("taskid", taskid);
		Collecttask collecttask = missionService.getCollecttaskById(params);
		return collecttask;
	}
	


}
