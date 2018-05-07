package monitor.pictureseach;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import monitor.activemq.SendMessageByMqBack;
import monitor.activemq.SendMessageByMqresult;
import monitor.util.SpringUtil;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

import org.apache.log4j.Logger;

public class SendPicToMQ {
	private static Logger logger = Logger.getLogger(SendPicToMQ.class);
	
	/**
	 * 采集任务发送数据
	 * @param collecttask
	 * @param downloadlist
	 */
	public void sendMessage(Collecttask collecttask,List<Map<String, String>> downloadlist){
		//引入service
		IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
		//根据任务号，查询出与采集机器人相关的mission任务
		//根据任务id，从表mission_mission中查询正在执行的任务列表，返回一个list集合
		List<dbinfo> dbinfoList = missionService.getdbinfoListByMissionid(collecttask.getMissionid());
		for (dbinfo info : dbinfoList) {
			
			//获取当前任务的检测类型typeList
			List<String> typeList = new ArrayList<String>();
			String checkmode = info.getCheckmode();
			if(checkmode!=null){
				//判断是否为多个检测类型
				if(checkmode.indexOf(",")!=-1){
					String[] split = checkmode.split(",");
					for (int i = 0; i < split.length; i++) {
						typeList.add(split[i]);
					}
				}else{
					typeList.add(checkmode);
				}
			}
			
			//遍历typeList,按类型发送mq
			for (String type : typeList) {
				info.setCheckmode(type);
				logger.debug("listinfo============================="+downloadlist);
				logger.debug("send  mq信息开始============================="+info.getMissionid());
				SendMessageByMqresult.sendpicture(info,null,downloadlist);
				logger.debug("send mq信息结束============================="+info.getMissionid());
				logger.debug("发送info信息结束=============================任务创建者=="+info.getUser());
			}
			logger.debug("insert 信息开始============================="+info.getMissionid());
			picdownload.insertmissionpicture(info,downloadlist);
			logger.debug("insert 信息结束============================="+info.getMissionid());
			
		}
		
	}
	
	/**
	 * 回溯数据发送
	 * @param collecttask
	 * @param downloadlist
	 */
	public void backSendMessage(dbinfo info,List<Map<String, String>> downloadlist){
		//获取当前任务的检测类型typeList
		List<String> typeList = new ArrayList<String>();
		String checkmode = info.getCheckmode();
		if(checkmode!=null){
			//判断是否为多个检测类型
			if(checkmode.indexOf(",")!=-1){
				String[] split = checkmode.split(",");
				for (int i = 0; i < split.length; i++) {
					typeList.add(split[i]);
				}
			}else{
				typeList.add(checkmode);
			}
		}
		
		for (String type : typeList) {
			
			info.setCheckmode(type);
			logger.debug("发送回溯mq信息开始=============================");
			//发送到missionback
//			SendMessageByMqBack.sendpicture(info,null,downloadlist);
			//发送到missionpicture
			SendMessageByMqresult.sendpicture(info,null,downloadlist);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.debug("发送回溯info信息结束=============================任务创建者=="+info.getUser());
			logger.debug("发送回溯mq信息结束=============================");
		}
		picdownload.insertmissionpicture(info,downloadlist);
	}
}
