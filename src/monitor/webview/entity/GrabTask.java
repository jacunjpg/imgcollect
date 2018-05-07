package monitor.webview.entity;

import monitor.robot.IRobot;
import monitor.robot.service.sinalocalsearchRobot;
import monitor.robot.service.sinasearchRobot;

public class GrabTask {
	
	private int taskid;
	private RobotManage robotManage;
	private IRobot sinasearchRobot;
	private String appid;
	private String appkey;
	
	public int getTaskid() {
		return taskid;
	}
	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}
	public RobotManage getRobotManage() {
		return robotManage;
	}
	public void setRobotManage(RobotManage robotManage) {
		this.robotManage = robotManage;
	}
	public IRobot getSinasearchRobot() {
		return sinasearchRobot;
	}
	public void setSinasearchRobot(IRobot sinasearchRobot) {
		this.sinasearchRobot = sinasearchRobot;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	
	
}
