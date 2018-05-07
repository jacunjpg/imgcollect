package monitor.webview.entity;

import java.io.Serializable;

public class MissionRobot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String missionid;
	private String robotid;
	private String flag;
	private String remark;
	private String createtime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMissionid() {
		return missionid;
	}
	public void setMissionid(String missionid) {
		this.missionid = missionid;
	}
	public String getRobotid() {
		return robotid;
	}
	public void setRobotid(String robotid) {
		this.robotid = robotid;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
}
