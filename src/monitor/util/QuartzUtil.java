package monitor.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import monitor.pictureutil.ConfigInfo;
import monitor.pictureutil.ViewSliveUtil;
import monitor.webview.entity.BackTask;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.GrabTask;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

import org.apache.log4j.Logger;

//定时器定时查询数据库，比对数据状态，控制机器人状态
public class QuartzUtil {

	private long t0 = new Date().getTime();
	private long t1 = t0;
	private static Logger logger = Logger
			.getLogger(QuartzUtil.class);
	public IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
	
	public void test(){
		System.out.println("测试");
	}
	public void taskJob(){
		logger.debug("定时任务启动");
		/**
		 * 1.查询未完成的任务表，查询未完成的机器人任务
		 * 2.判断机器人的ip是否是本机的ip
		 * 3.比对对应的任务状态，以mission表为主表，task为副表，副表随着主表改变状态，机器人的状态也随之改变
		 */
		//获取本机ip
		String addr = "";
		try {
			addr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		//采集服务器上的机器人
		List<GrabTask> list = InitSystemServlet.grabTaskList;
		//查询未完成的任务表
		List<dbinfo> dbinfo = missionService.getMissionList();
		//查询未完成的机器人任务
		List<Collecttask> collecttask = missionService.getTaskList();
		
		//遍历机器人集合，判断是否是本机器的任务
		//根据机器人查询出与之关联的任务列表，
		for (Collecttask task : collecttask) {
			String missionidt = task.getMissionid();
			String addrt = task.getAddr();
			String statust = task.getStatus();
			//判断该任务是否是在本机上执行的，否则不执行下面的代码
			if(addr.equals(addrt)){
				
				//机器人的状态有:2启动,4暂停
				
				//2启动
				//查询出与该机器人相关的任务列表
				List<dbinfo> infos = missionService.getMissionListByTask(missionidt);
				//status_s为所有与机器人相关任务的状态
				String status_s = "";
				for (dbinfo info : infos) {
					String status =info.getStyle();
					status_s += ","+status;
					
					//操作回溯任务机器人
					ctrolBackRobot(info.getMissionid()+"",status);
					
				}
				if(status_s.length()>0){
					status_s = status_s.substring(1);
				}
				
				//判断机器人是启动
				if(statust.equals("2")){
					//当任务中有2时，说明有正在执行的任务,没有时执行此方法
					//首先判断是否有正在执行的任务2；没有的话判断是否有暂停,有的话执行暂停操作
					if(!(status_s.indexOf("2")!=-1) && status_s.indexOf("4")!=-1){
						//暂停机器人操作
						//遍历本地静态变量中的机器任务
						for (GrabTask grabTask : InitSystemServlet.grabTaskList) {
							String grabtaskid = grabTask.getTaskid()+"";
							//如果相同，认为是同一个任务
							if(grabtaskid.trim().equals(missionidt)){
								//修改数据库
								missionService.updateTaskForZBymissionid(missionidt);
								ConfigInfo.NUM++;
								//执行机器人暂停操作
								grabTask.getSinasearchRobot().stoprobot();
							}
						}
						//首先判断是否有正在执行的任务2，判断有停止操作
					}else if(!(status_s.indexOf("2")!=-1) && !(status_s.indexOf("4")!=-1)){
						//停止机器人操作，按最后停止的任务状态来修改机器人的状态
						//手工停止和到期停止
						//机器人的最后状态
						String lastStatus = "3";
						//任务的最长时间
						long start = 0;
						SimpleDateFormat sdf  =new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
						for (dbinfo info2 : infos) {
							String endtime = info2.getEndtime();
							String status = info2.getStyle();
							try {
								long endTime = sdf.parse(endtime).getTime();
								if(endTime-start > 0){
									start = endTime;
									lastStatus = status;
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						//停止方法
						if(status_s!="" && status_s.trim().length()>0){
							stopRobot(lastStatus,missionidt);
						}
					}
					
					
				}
				
				//4暂停
				if(statust.equals("4")){
					//当任务中有2时，说明有正在执行的任务,启动对应的机器人
					if(status_s.indexOf("2")!=-1){
						//遍历本地静态变量中的机器任务
						for (GrabTask grabTask : list) {
							String grabtaskid = grabTask.getTaskid()+"";
							//如果相同，认为是同一个任务
							if(grabtaskid.trim().equals(missionidt)){
								ConfigInfo.NUM--;
								//执行机器人启动操作
								if(ConfigInfo.NUM>0){
									System.out.println("执行机器人开始start操作");
									grabTask.getSinasearchRobot().startrobot();
								}
								//修改机器人状态为2
								missionService.updateTaskForQBymissionid(missionidt);
							}
						}
					//当任务中有4时，说明有暂停的任务,没有时执行此方法
					}else if(!(status_s.indexOf("2")!=-1) && !(status_s.indexOf("4")!=-1)){
						//停止机器人操作，按最后停止的任务状态来修改机器人的状态
						//手工停止和到期停止
						//机器人的最后状态：3是到期停止，1是手工停止
						String lastStatus = "3";
						//任务的最长时间
						long start = 0;
						SimpleDateFormat sdf  =new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
						for (dbinfo info2 : infos) {
							String endtime = info2.getEndtime();
							String status = info2.getStyle();
							try {
								long endTime = sdf.parse(endtime).getTime();
								if(endTime-start > 0){
									start = endTime;
									lastStatus = status;
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						//停止方法
						if(status_s!="" && status_s.trim().length()>0){
							stopRobot(lastStatus,missionidt);
						}
					}
					
				}
				
				
			}
			
		}
		
		t1 = new Date().getTime();
		//60s执行一次
		//定时执行任务的过期检测
		if ((t1 - t0) > 1000*60) {
			//查询未停止的任务表
			List<dbinfo> dbinfoList = missionService.getDbinfoListForOverdue();
			SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			//将过期的任务执行状态修改为3
			for (dbinfo info : dbinfoList) {
				String missionid = info.getMissionid()+"";
				String endtime = info.getEndtime();
				long endTime = 0;
				try {
					endTime = sdf.parse(endtime).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				long nowTime = new Date().getTime();
				if(nowTime-endTime > 0){
					missionService.updateStyleByMissionid(missionid);
				}
			}
			logger.debug("定时执行任务的过期检测=================");
		}
		
		logger.debug("list==="+list.size()+"...dbinfo====="+dbinfo.size()+"...collecttask===="+collecttask.size());
		//遍历机器人任务
		System.out.println(DateUtil.getCurrentTime()+"===The number of robots currently allowed to be turned on==========="+ConfigInfo.NUM+"///Current robot static 数量==========="+InitSystemServlet.grabTaskList.size());
		
	}
	private void stopRobot(String lastStatus, String missionidt) {
		//判断是到期停止还是手工停止,3是到期停止，1是手动停止
		if(lastStatus.equals("3")){
			
			//判断该任务是关键词任务还是属地任务
			dbinfo info = missionService.getDbinfoById(missionidt);
			//修改数据库
			missionService.updateTaskExpireBymissionid(missionidt);
			String searchmode = info.getSearchmode();
			if(searchmode.equals("www.sinaweibo.com")){
				//删除曹总关键词的接口
				try {
					String deleteTask = ViewSliveUtil.deleteTaskByIdkey(missionidt);
					logger.debug("delete keywords interface at CAOZONG -------------"+deleteTask);
					if(!deleteTask.equals("success")){
						boolean flag = true;
						int i =0;
						while(flag){
							i++;
							Thread.sleep(3000);
							String result = ViewSliveUtil.deleteTaskByIdkey(missionidt);
							//删除成功或者循环删除三次后跳出循环
							if(result.equals("success") || i > 2){
								flag = false;
							}
							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("delete keywords interface at CAOZONG has Exception.........."+e.getStackTrace());
				}
			}
			
		}else{
			//修改数据库
			missionService.updateTaskForTBymissionid(missionidt);
		}
		
		//遍历本地静态变量中的机器任务
		for (int i = 0; i < InitSystemServlet.grabTaskList.size(); i++) {
			GrabTask grabTask = InitSystemServlet.grabTaskList.get(i);
			String grabtaskid = InitSystemServlet.grabTaskList.get(i).getTaskid()+"";
			//如果相同，认为是同一个任务
			if(grabtaskid.trim().equals(missionidt)){
				InitSystemServlet.grabTaskList.remove(i);
				ConfigInfo.NUM++;
				//停止
				grabTask.getSinasearchRobot().destoryrobot();
				
			}
		}
		
	}
	
	/**
	 * 执行回溯机器人的操作
	 * @param missionid
	 * @param status
	 */
	public void ctrolBackRobot(String missionid,String status){
		
		//遍历回溯任务的静态机器人
		for (int i = 0; i < InitSystemServlet.backTaskList.size(); i++) {
			BackTask backTask = InitSystemServlet.backTaskList.get(i);
			String taskid = backTask.getTaskid()+"";
			if(missionid.trim().equals(taskid.trim())){
				if(!status.equals(backTask.getStatus())){
					if(status != "" && status.equals("4")){
						//执行暂停
						backTask.getSinasearchRobot().stoprobot();
						InitSystemServlet.backTaskList.get(i).setStatus("4");
					}else if(status.equals("2")){
						//执行启动
						backTask.getSinasearchRobot().startrobot();
						InitSystemServlet.backTaskList.get(i).setStatus("2");
					}else{
						//执行停止
						backTask.getSinasearchRobot().destoryrobot();
						InitSystemServlet.backTaskList.remove(i);
					}
				}
			}
		}
		
	}
		
}
	
