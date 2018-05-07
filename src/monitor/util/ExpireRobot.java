package monitor.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.ReadProperties;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.GrabTask;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

/**
 * 到期停止机器人
 * @author chwx
 *
 */
public class ExpireRobot {

	public IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
	
	/*
	 * 停止
	 */
//	public void stopRobot(String missionid){
//		/*
//		 * 获取静态变量中运行的机器人
//		 * 将机器人与missionid匹配是否有相同的，
//		 * 如果有将机器人停止，并且可创建的机器人数量+1
//		 * 修改任务表的状态为3，在修改机器人表的状态为3
//		 */
//		List<GrabTask> list = InitSystemServlet.grabTaskList;
//		for (GrabTask grabTask : list) {
//			String grabtaskid = grabTask.getTaskid()+"";
//			//如果相同，认为是同一个任务
//			if(grabtaskid.trim().equals(missionid)){
////				Map<String, String> params = new HashMap<String, String>();
////				params.put("style", "3");
////				params.put("missionid", missionid);
//				//修改任务表
////				missionService.missioncontrol(params);
//				//修改机器人任务表
//				missionService.updateTaskExpireBymissionid(missionid);
//				ConfigInfo.NUM++;
//				//执行机器人暂停操作
//				grabTask.getSinasearchRobot().stoprobot();
//			}
//		}
//	}
	
	/**
	 * 获取最新的任务结束时间
	 * @param missionid
	 * @return
	 */
	public static long getUpdateTimeByMissionid(String missionid){
		IMissionService missionServices=(IMissionService) SpringUtil.getObject("missionService");
		Collecttask collecttask = missionServices.getUpdateTimeByMissionid(missionid);
		long endTime = collecttask.getUpdatetime().getTime();
		return endTime;
	}
	
	/*
	 * 更新机器人执行时间
	 */
	public void updateTimeByMissionid(String endTime, String missionid){
		Map<String, String> params = new HashMap<String, String>();
		params.put("endTime", endTime);
		params.put("missionid", missionid);
		missionService.updateUpdateTimeByMissionid(params);
	}
	
	/**
	 * 根据id获取
	 * @param missionid
	 */
	public void updateMissionStyleByid(String missionid){
		
		//根据id获取对应的任务信息，将对应的任务修改任务名称
		List<dbinfo> dbinfoList = missionService.getInfoListById(missionid);
		for (dbinfo info : dbinfoList) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("query", "磁盘空间不足_"+info.getQuery());
			params.put("style", "4");
			params.put("missionid", info.getMissionid()+"");
			//更新暂停任务信息
			missionService.updateMissionStyleByid(params);
		}
		
	}
	
	/**
	 * 判断磁盘是否有空间
	 * @return
	 */
	public int getFreeDiskSpace(){
		ReadProperties readProperties = new ReadProperties();
		String path = readProperties.getValueByKey("tomcatpath");
		File file = new File(path);  
		//G
        long freeSpace = 2;
        freeSpace = (file.getFreeSpace() / 1024 / 1024 / 1024);
        //空间不足2G,返回状态标识0
        int flag = 1;
        if(freeSpace<2){
        	flag = 0;
        }
        return flag;
	}
	
	public static void main(String[] args) {
		ReadProperties readProperties = new ReadProperties();
		String path = readProperties.getValueByKey("tomcatpath");
//		String path = "c://";
		File file = new File(path);  
        long totalSpace = file.getTotalSpace();  
        long freeSpace = file.getFreeSpace();  
        long usedSpace = totalSpace - freeSpace;  
  
        System.out.println("总空间大小 : " + totalSpace / 1024 / 1024 / 1024 + "G");  
        System.out.println("剩余空间大小 : " + freeSpace / 1024 / 1024 / 1024 + "G");  
        System.out.println("已用空间大小 : " + usedSpace / 1024 / 1024 / 1024 + "G"); 
	}
}
