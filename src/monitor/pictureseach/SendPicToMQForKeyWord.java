package monitor.pictureseach;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import monitor.activemq.SendMessageByMqresult;
import monitor.util.SpringUtil;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

import org.apache.log4j.Logger;

public class SendPicToMQForKeyWord {
	private static Logger logger = Logger.getLogger(SendPicToMQForKeyWord.class);
	
	public void sendMessage(Collecttask collectTask,List<Map<String, String>> downloadlist){
		//引入service
		IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
		//根据任务号，查询出与采集机器人相关的mission任务
		//根据任务id，从表mission_mission中查询正在执行的任务列表，返回一个list集合
		List<dbinfo> dbinfoList = missionService.getdbinfoListByMissionid(collectTask.getMissionid());
		for (dbinfo info : dbinfoList) {
			String missionid = info.getMissionid()+"";
			String taskid = collectTask.getMissionid();
			//判断机器人任务和当前任务是否一致，相同则发送
			if(missionid.trim().equals(taskid)){
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
					logger.debug("keywords发送mq信息开始=============================");
					SendMessageByMqresult.sendpicture(info,null,downloadlist);
					logger.debug("keywords发送mq信息结束=============================");
					
				}
				picdownload.insertmissionpicture(info,downloadlist);
				
			}
			
		}
		
	}
}
