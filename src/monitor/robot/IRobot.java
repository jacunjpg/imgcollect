package monitor.robot;

import monitor.webview.entity.dbinfo;

public interface IRobot {

	
	public boolean startrobot();
	public void stoprobot();
	/**
	 *  机器人内部数据已销毁，手动释放指针 robot=null;
	 * author:liyunlong
	 * date  :2017-6-13
	 */
	public void destoryrobot();
}