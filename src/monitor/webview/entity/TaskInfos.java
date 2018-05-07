package monitor.webview.entity;

public class TaskInfos {
	private int missionid;//任务id
	private int detectnum;//已经检测的图片数量
	private int waringnum;//预警图片数量
	private int capturenum;//抓取图片数量
	private int missionstatus;//任务状态
	
	public int getDetectnum() {
		return detectnum;
	}
	public void setDetectnum(int detectnum) {
		this.detectnum = detectnum;
	}
	public int getWaringnum() {
		return waringnum;
	}
	public void setWaringnum(int waringnum) {
		this.waringnum = waringnum;
	}
	public int getCapturenum() {
		return capturenum;
	}
	public void setCapturenum(int capturenum) {
		this.capturenum = capturenum;
	}
	public int getMissionid() {
		return missionid;
	}
	public void setMissionid(int missionid) {
		this.missionid = missionid;
	}
	public int getMissionstatus() {
		return missionstatus;
	}
	public void setMissionstatus(int missionstatus) {
		this.missionstatus = missionstatus;
	}
	

}
