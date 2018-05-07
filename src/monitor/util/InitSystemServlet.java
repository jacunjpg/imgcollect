package monitor.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import monitor.robot.service.BaiduSearchRobot;
import monitor.robot.service.QihuSearchRobot;
import monitor.robot.service.SougouSearchRobot;
import monitor.robot.service.sinalocalsearchRobot;
import monitor.robot.service.sinasearchRobot;
import monitor.webview.entity.BackTask;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.GrabTask;
import monitor.webview.entity.RobotManage;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

import org.apache.log4j.Logger;

public class InitSystemServlet extends HttpServlet{

	/**
	 * 
	 */
	private static Logger logger = Logger.getLogger(InitSystemServlet.class);
	private static final long serialVersionUID = 1L;
	public static Map<String, String> mapToken;// 把token放入内存中，方便读取
	//存放机器人的变量
	public static List<GrabTask> grabTaskList = new ArrayList<GrabTask>();
	//存放回溯机器人的变量
	public static List<BackTask> backTaskList = new ArrayList<BackTask>();
	//待启动的任务
	public static List<dbinfo> missionList =null ;
	//启动的任务
	public static List<dbinfo> missionStartList =null ;
	
	public void init() throws ServletException {
		IMissionService missionService=(IMissionService) SpringUtil.getObject("missionService");
		//获取本机ip
		String addr = "";
		try {
			addr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		List<Collecttask> collecttask = missionService.getTaskList();
		for (Collecttask task : collecttask) {
			String status = task.getStatus();
			String missionid = task.getMissionid();
			String addrt = task.getAddr();
			//判断是否是本台采集机器
			if(addr.equals(addrt)){
				//启动和暂停的状态执行初始化
				if(!status.equals("1") && !status.equals("3")){
					if(status.equals("2")){
						//执行启动初始化
						RobotManage robotManage = new RobotManage();
						GrabTask grabTask = new GrabTask();
//						dbinfo info= missionService.getDbinfoById(missionid);
						robotManage.createRobot();
						if(task.getRobot().contains("www.sinaweibo.com"))
						{
							sinasearchRobot robot = new sinasearchRobot(task);
							robot.startrobot();
							grabTask.setTaskid(Integer.parseInt(missionid));
							grabTask.setSinasearchRobot(robot);
							grabTaskList.add(grabTask);
						}
						if(task.getRobot().contains("www.sinalocalmission"))
						{
							sinalocalsearchRobot robot = new sinalocalsearchRobot(task);
							robot.startrobot();
							grabTask.setTaskid(Integer.parseInt(missionid));
							grabTask.setSinasearchRobot(robot);
							grabTaskList.add(grabTask);
						}
						if(task.getRobot().contains("baidu"))
						{
							BaiduSearchRobot robot = new BaiduSearchRobot(task);
							robot.startrobot();
							grabTask.setTaskid(Integer.parseInt(missionid));
							grabTask.setSinasearchRobot(robot);
							grabTaskList.add(grabTask);
						}
						if(task.getRobot().contains("sougou"))
						{
							SougouSearchRobot robot = new SougouSearchRobot(task);
							robot.startrobot();
							grabTask.setTaskid(Integer.parseInt(missionid));
							grabTask.setSinasearchRobot(robot);
							grabTaskList.add(grabTask);
						}
						if(task.getRobot().contains("qihu"))
						{
							QihuSearchRobot robot = new QihuSearchRobot(task);
							robot.startrobot();
							grabTask.setTaskid(Integer.parseInt(missionid));
							grabTask.setSinasearchRobot(robot);
							grabTaskList.add(grabTask);
						}
						
					}else if(status.equals("4")){
						//执行暂停的初始化
						GrabTask grabTask = new GrabTask();
//						dbinfo info= missionService.getDbinfoById(missionid);
						if(task.getRobot().contains("www.sinaweibo.com"))
						{
							sinasearchRobot robot = new sinasearchRobot(task);
							grabTask.setTaskid(Integer.parseInt(missionid));
							grabTask.setSinasearchRobot(robot);
							grabTaskList.add(grabTask);
						}
						if(task.getRobot().contains("www.sinalocalmission"))
						{
							sinalocalsearchRobot robot = new sinalocalsearchRobot(task);
							grabTask.setTaskid(Integer.parseInt(missionid));
							grabTask.setSinasearchRobot(robot);
							grabTaskList.add(grabTask);
						}
						if(task.getRobot().contains("baidu"))
						{
							BaiduSearchRobot robot = new BaiduSearchRobot(task);
							grabTask.setTaskid(Integer.parseInt(missionid));
							grabTask.setSinasearchRobot(robot);
							grabTaskList.add(grabTask);
						}
						if(task.getRobot().contains("sougou"))
						{
							SougouSearchRobot robot = new SougouSearchRobot(task);
							grabTask.setTaskid(Integer.parseInt(missionid));
							grabTask.setSinasearchRobot(robot);
							grabTaskList.add(grabTask);
						}
						if(task.getRobot().contains("qihu"))
						{
							QihuSearchRobot robot = new QihuSearchRobot(task);
							grabTask.setTaskid(Integer.parseInt(missionid));
							grabTask.setSinasearchRobot(robot);
							grabTaskList.add(grabTask);
						}
						
					}
				}
			}
		}
		
		logger.debug("初始化完成。。");
	}
	
}

