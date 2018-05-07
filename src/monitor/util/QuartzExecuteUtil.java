package monitor.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import monitor.pictureutil.ConfigInfo;
import monitor.robot.IRobot;
import monitor.robot.service.sinalocalsearchRobot;
import monitor.robot.service.sinasearchRobot;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.GrabTask;
import monitor.webview.entity.RobotManage;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

import org.apache.log4j.Logger;

//定时器定时查询数据库，比对数据状态，控制机器人状态
public class QuartzExecuteUtil {

	private static Logger logger = Logger
			.getLogger(QuartzExecuteUtil.class);
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
		List<GrabTask> list = InitSystemServlet.grabTaskList;
		//查询未完成的任务表
		List<dbinfo> dbinfo = missionService.getMissionList();
		//查询未完成的机器人任务
		List<Collecttask> collecttask = missionService.getTaskList();
		System.out.println("list==="+list.size()+"...dbinfo====="+dbinfo.size()+"...collecttask===="+collecttask.size());
		boolean localTask = false;
		//判断是否有正在运行的属地化任务
		for (Collecttask collecttask2 : collecttask) {
			String localTaskMark = collecttask2.getRemark();
			if(localTaskMark.equals("1")){
				localTask = true;
			}
		}
		
		//遍历机器人任务
		for (Collecttask task : collecttask) {
			//判断是哪一种任务：普通0还是属地1
			String missionidt = task.getMissionid();
			String addrt = task.getAddr();
			String status = task.getStatus();
			//判断该任务是否是在本机上执行的，否则不执行下面的代码
			if(addr.equals(addrt)){
				//遍历任务列表
				for (dbinfo info : dbinfo) {
					//获取当前任务的missionid和状态
					String missionid = info.getMissionid()+"";
					String style = info.getStyle();
					//判断任务id是否相同
					if(missionidt.equals(missionid)){
						
						//判断任务状态是否一致,否则更新机器人的任务状态与任务列表中的任务状态一致
						if(!status.equals(style)){
							
								if(style.equals("1")){
									//遍历本地静态变量中的机器任务
									for (int i = 0; i < InitSystemServlet.grabTaskList.size(); i++) {
										GrabTask grabTask = InitSystemServlet.grabTaskList.get(i);
										String grabtaskid = InitSystemServlet.grabTaskList.get(i).getTaskid()+"";
										//如果相同，认为是同一个任务
										if(grabtaskid.trim().equals(missionid)){
											InitSystemServlet.grabTaskList.remove(i);
											ConfigInfo.NUM++;
											//停止
											grabTask.getSinasearchRobot().destoryrobot();
											//修改数据库
											//========
											missionService.updateTaskForTBymissionid(missionid);
										}
									}
								}else if(style.equals("2")){
									boolean flag = true;
									//遍历本地静态变量中的机器任务
									for (GrabTask grabTask : list) {
										String grabtaskid = grabTask.getTaskid()+"";
										//如果相同，认为是同一个任务
										if(grabtaskid.trim().equals(missionid)){
											ConfigInfo.NUM--;
											//执行机器人启动操作
											if(ConfigInfo.NUM>0){
												System.out.println("执行机器人开始start操作");
												grabTask.getSinasearchRobot().startrobot();
											}
											//修改数据库
											//========
											missionService.updateTaskForQBymissionid(missionid);
											flag = false;
										}
									}
									
									if(flag){
										//执行机器人启动操作
										if(ConfigInfo.NUM>0){
											if(task.getRobot().contains("www.sinaweibo.com")){
												//可创建机器人数量减1
												ConfigInfo.NUM--;
												System.out.println("执行机器人开始操作");
												GrabTask grabTask = new GrabTask();
												//将抓取任务保存起来
												List<GrabTask> grabTaskList = InitSystemServlet.grabTaskList;
												grabTask.setTaskid(Integer.parseInt(missionid));
												//创建机器人
												sinasearchRobot robot = new sinasearchRobot(task);
												robot.startrobot();
												grabTask.setSinasearchRobot(robot);
												grabTaskList.add(grabTask);
												RobotManage robotManage = new RobotManage();
												robotManage.saveRobot(info, robot.toString(), "0");
											}else if(task.getRobot().contains("www.sinalocalmission")){
												//localTask为true说明已经有属地化任务
												if(!localTask){
													//可创建机器人数量减1
													ConfigInfo.NUM--;
													System.out.println("执行机器人开始操作");
													GrabTask grabTask = new GrabTask();
													//将抓取任务保存起来
													grabTask.setTaskid(Integer.parseInt(missionid));
													//创建机器人
													sinalocalsearchRobot robot = new sinalocalsearchRobot(task);
													robot.startrobot();
													grabTask.setSinasearchRobot(robot);
													InitSystemServlet.grabTaskList.add(grabTask);
													RobotManage robotManage = new RobotManage();
													robotManage.saveRobot(info, robot.toString(), "1");
												}else{
													//启动属地化任务
													//判断属地化任务状态是否正在启动2，如果不是，启动属地化任务
													if(!status.equals("2")){
														ConfigInfo.NUM--;
														//执行机器人启动操作
														if(ConfigInfo.NUM>0){
															System.out.println("创建机器人开始start操作");
															GrabTask grabTask = new GrabTask();
															//将抓取任务保存起来
															grabTask.setTaskid(Integer.parseInt(missionid));
															//创建机器人
															sinalocalsearchRobot robot = new sinalocalsearchRobot(task);
															robot.startrobot();
															grabTask.setSinasearchRobot(robot);
															
															InitSystemServlet.grabTaskList.add(grabTask);
														}
														//修改数据库
														//========
														missionService.updateTaskForQBymissionid(missionid);
													}
												}
											}
											
										}
									}
									
									//手动停止
								}else if(style.equals("4")){
									//遍历本地静态变量中的机器任务
									for (GrabTask grabTask : InitSystemServlet.grabTaskList) {
										String grabtaskid = grabTask.getTaskid()+"";
										//如果相同，认为是同一个任务
										if(grabtaskid.trim().equals(missionid)){
											//修改数据库
											missionService.updateTaskForZBymissionid(missionid);
											ConfigInfo.NUM++;
											//执行机器人暂停操作
											grabTask.getSinasearchRobot().stoprobot();
										}
									}
									//到期停止
								}else if(style.equals("3")){
									if(!status.equals("4") && !status.equals("1")){
										//遍历本地静态变量中的机器任务
										for (GrabTask grabTask : InitSystemServlet.grabTaskList) {
											String grabtaskid = grabTask.getTaskid()+"";
											//如果相同，认为是同一个任务
											if(grabtaskid.trim().equals(missionid)){
												//修改数据库
												missionService.updateTaskExpireBymissionid(missionid);
												ConfigInfo.NUM++;
												//执行机器人暂停操作
												grabTask.getSinasearchRobot().stoprobot();
											}
										}
									}
								}
								
								
							  }
								
						}
					}
								
								
							
							
							
			}
						
		}
		System.out.println(DateUtil.getCurrentTime()+"===当前允许开启的机器人数量==========="+ConfigInfo.NUM+"当前机器人静态变量数量==========="+InitSystemServlet.grabTaskList.size());
		
	}
		
}
	
