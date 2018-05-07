package monitor.webview.entity;

import java.util.Date;

public class Collecttask {

	private String taskid;  //主键
	private String missionid;//任务id
	private String robot;//抓取机器人
	private String addr;//机器ip
	private String status;//运行状态
	private int flag;//状态标识
	private String remark;//备注
	private Date updatetime;//更新时间
	private Date createtime;//创建时间
	
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getMissionid() {
		return missionid;
	}
	public void setMissionid(String missionid) {
		this.missionid = missionid;
	}
	public String getRobot() {
		return robot;
	}
	public void setRobot(String robot) {
		this.robot = robot;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
