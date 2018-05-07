package monitor.webview.service;

import java.util.List;
import java.util.Map;


import monitor.webview.entity.Collecttask;

import monitor.webview.entity.LoginUser;
import monitor.webview.entity.WebSitePicture;
import monitor.webview.entity.dbinfo;

public interface IMissionService {

	public List<dbinfo> getmissionlist(
			Map<String, String> params);

	public void deletemission(Map<String, String> params);

	public void deletepicture(Map<String, String> params);

	public void deleteresult(Map<String, String> params);

	public List<dbinfo> getcbjccbk(Map<String, String> params);

	public List<dbinfo> getrljccbk(Map<String, String> params);

	public void insertmission(Map<String, String> params);

	public dbinfo getmissioninfo(Map<String, String> params);

	public List<monitor.webview.entity.dbinfo> getresult(
			Map<String, String> params);

	public dbinfo getmissionbydatetime(Map<String, String> params);

	public void insertpicture(List<dbinfo> list);

	public void insertresult(List<dbinfo> list);

	public void updatemission(dbinfo dbinfo);

	public void updatepicturepath(List<dbinfo> list);

	//保存爬虫的图片信息
	public void insertWebSitePicture(List<WebSitePicture> webSiteList);

	public List<monitor.webview.entity.dbinfo> getWebList(
			Map<String, String> params);

	public List<dbinfo> getmissionalive(Map<String, String> params);

	public void updatestartid(dbinfo dbinfo);

	public dbinfo getcount(Map<String, String> params);
	
	public List<dbinfo> getcbjccbkincbkid(Map<String, String> params);

	public List<dbinfo> getrljccbkincbkid(Map<String, String> params);

	public void missioncontrol(Map<String, String> params);

	public void insertDetectTask(dbinfo dbinfo);

	public List<dbinfo> queryMissionList();

	public List<dbinfo> queryMissionStartList();

	public List<monitor.webview.entity.dbinfo> getresultexport(
			Map<String, String> params);
	
	public dbinfo getMissionResultById(Map<String, String> params);

	public dbinfo pictureCount(Map<String, String> params);

	public dbinfo getMissionStatusById(Map<String, String> params);
	
	public List<dbinfo> getpictures(dbinfo dbinfo);


	public void insertTaskInfo(Map<String, String> params);

	public List<dbinfo> getMissionList();

	public List<Collecttask> getTaskList();
	
	public List<Collecttask> getRunningTaskList();

	public void updateTaskForTBymissionid(String missionid);

	public void updateTaskForQBymissionid(String missionid);

	public void updateTaskForZBymissionid(String missionid);
	
	public void updateTaskExpireBymissionid(String missionid);

	public dbinfo getDbinfoById(String missionid);

	public void saveMissionRobot(int missionid, String robotid, String remark);

	public List<monitor.webview.entity.dbinfo> getdbinfoListByMissionid(
			String missionid);

	public List<monitor.webview.entity.dbinfo> getdbinfoRunningListByName(String name);

	public Collecttask getCollecttaskById(Map<String, String> params);

	public Collecttask getUpdateTimeByMissionid(String missionid);

	public void updateUpdateTimeByMissionid(Map<String, String> params);

	public List<monitor.webview.entity.dbinfo> getMissionListByTask(
			String missionidt);

	public List<monitor.webview.entity.dbinfo> getDbinfoListForOverdue();

	public void updateStyleByMissionid(String missionid);

	public List<dbinfo> getInfoListById(String missionid);

	public void updateMissionStyleByid(Map<String, String> params);

	//根据开始结束时间查询符合条件的属地数据
	public List<dbinfo> getPicturesByTime(String missionid, String nowTime, String e_time);

	public void updateMissionById(String content1, String missionid);

	public void updateMissionStatusById(String missionid, int status);


}
