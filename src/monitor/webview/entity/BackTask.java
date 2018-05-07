package monitor.webview.entity;

import monitor.robot.IRobot;

public class BackTask {
	private int taskid;
	private RobotManage robotManage;
	private IRobot sinasearchRobot;
	private String appid;
	private String appkey;
	private String status;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
