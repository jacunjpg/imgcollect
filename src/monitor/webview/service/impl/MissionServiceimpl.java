package monitor.webview.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import monitor.basic.IBasicDaoSupport;
import monitor.util.DateUtil;
import monitor.webview.entity.Collecttask;
import monitor.webview.entity.WebSitePicture;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IMissionService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("missionService")
public class MissionServiceimpl implements IMissionService {

	private static Logger logger = Logger.getLogger(MissionServiceimpl.class);

	@Resource(name="basicDaoSupport")
	public IBasicDaoSupport daoSupport;

	
	@Override
	public List<dbinfo> getmissionlist(Map<String, String> params) {
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getmissionlist", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public void deletemission(Map<String, String> params) {
		try {
		daoSupport.findForList("missionMapper.deletemission", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletepicture(Map<String, String> params) {
		try {
			daoSupport.findForList("missionMapper.deletepicture", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public void deleteresult(Map<String, String> params) {
		try {
			daoSupport.findForList("missionMapper.deleteresult", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public List<dbinfo> getcbjccbk(Map<String, String> params) {
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdh2Mapper.getcbklist", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public List<dbinfo> getrljccbk(Map<String, String> params) {
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdhMapper.getcbklist", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public void insertmission(Map<String, String> params) {
		try {
			daoSupport.findForList("missionMapper.insertmission", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public dbinfo getmissioninfo(Map<String, String> params) {
		dbinfo dbinfo=null;
		try {
			dbinfo=(dbinfo) daoSupport.findForObject("missionMapper.getmissioninfo", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public List<dbinfo> getresult(Map<String, String> params) {
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getresult", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public dbinfo getmissionbydatetime(Map<String, String> params) {
		dbinfo dbinfo=null;
		try {
			dbinfo=(dbinfo) daoSupport.findForObject("missionMapper.getmissionbydatetime", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public void insertpicture(List<dbinfo> list) {
		try {
			daoSupport.findForObject("missionMapper.insertpicture", list);
		} catch (Exception e) {
			logger.info(list.size());
			e.printStackTrace();
		}
	}

	@Override
	public void insertresult(List<dbinfo> list) {
		try {
			daoSupport.findForObject("missionMapper.insertresult", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updatemission(dbinfo dbinfo) {
		try {
			daoSupport.findForObject("missionMapper.updatemission", dbinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updatepicturepath(List<dbinfo> list) {
		try {
			daoSupport.findForObject("missionMapper.updatepicturepath", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertWebSitePicture(List<WebSitePicture> webSiteList) {
		try {
			daoSupport.findForObject("missionMapper.insetimagemessage", webSiteList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<dbinfo> getWebList(Map<String, String> params) {
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getWebList", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public List<dbinfo> getmissionalive(Map<String, String> params) {
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getmissionalive", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public void updatestartid(dbinfo dbinfo) {
		try {
			daoSupport.findForObject("missionMapper.updatestartid", dbinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public dbinfo getcount(Map<String, String> params) {
		dbinfo dbinfo=null;
		try {
			dbinfo=(dbinfo) daoSupport.findForObject("missionMapper.getcount", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
		
	}

	@Override
	public void missioncontrol(Map<String, String> params) {
		//判断操作类型
		try {
			daoSupport.findForObject("missionMapper.updateStatusById", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void insertDetectTask(dbinfo dbinfo) {
		Map<String, String> params = new HashMap<String, String>();
		String datetime = DateUtil.getCurrentTime();
		params.put("missionid", dbinfo.getMissionid()+"");
		params.put("user", dbinfo.getUser());
		params.put("query", dbinfo.getQuery());
		params.put("style", "2");
		params.put("searchmode", dbinfo.getSearchmode());
		params.put("checkmode", dbinfo.getCheckmode());
		params.put("createtime", datetime);
		
		try {
			daoSupport.findForList("missionMapper.insertDetectTask", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

	@Override
	public List<dbinfo> queryMissionList() {
		List<dbinfo> dbinfo=null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.queryMissionList", params);
		} catch (Exception e) {
		}
		return dbinfo;
	}
	
	@Override
	public List<dbinfo> getcbjccbkincbkid(Map<String, String> params) {
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdh2Mapper.getcbklistincbkid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public List<dbinfo> queryMissionStartList() {
		List<dbinfo> dbinfo=null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.queryMissionStartList", params);
		} catch (Exception e) {
			
		}
		return dbinfo;
	}
	
	@Override
	public List<dbinfo> getrljccbkincbkid(Map<String, String> params) {
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdhMapper.getcbklistincbkid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}


	@Override
	public List<dbinfo> getresultexport(Map<String, String> params) {
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getresultexport", params);
		} catch (Exception e) {
		}
		return dbinfo;
		
	}
	
	/**
	 * 查询任务结果表，查看是否有数据
	 */
	@Override
	public dbinfo getMissionResultById(Map<String, String> params) {
		dbinfo dbinfo=null;
		try {
			dbinfo=(dbinfo) daoSupport.findForObject("missionMapper.getMissionResultById", params);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return dbinfo;
	}

	/**
	 * 已经抓取的图片数量
	 */
	@Override
	public dbinfo pictureCount(Map<String, String> params) {
		dbinfo dbinfo=null;
		try {
			dbinfo=(dbinfo) daoSupport.findForObject("missionMapper.getPictureCountById", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	/**
	 * 查询任务状态
	 */
	@Override
	public dbinfo getMissionStatusById(Map<String, String> params) {
		dbinfo dbinfo=null;
		try {
			dbinfo=(dbinfo) daoSupport.findForObject("missionMapper.getMissionStatusById", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public List<dbinfo> getpictures(dbinfo map) {
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getpictures", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}


	/**
	 * 新增创建的抓取任务
	 */
	@Override
	public void insertTaskInfo(Map<String, String> params) {
		try {
			daoSupport.findForObject("CollecttaskMapper.insertTaskInfo", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 查询未完成的任务表
	 */
	@Override
	public List<dbinfo> getMissionList() {
		Map<String, String> params = new HashMap<String, String>();
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getMissionListByFlag", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	/**
	 * 查询未完成的机器人任务
	 */
	@Override
	public List<Collecttask> getTaskList() {
		Map<String, String> params = new HashMap<String, String>();
		List<Collecttask> collecttask=null;
		try {
			collecttask=(List<Collecttask>) daoSupport.findForList("CollecttaskMapper.getTaskList", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collecttask;
	}
	
	/**
	 * 查询正在执行的机器人任务
	 */
	@Override
	public List<Collecttask> getRunningTaskList() {
		Map<String, String> params = new HashMap<String, String>();
		List<Collecttask> collecttask=null;
		try {
			collecttask=(List<Collecttask>) daoSupport.findForList("CollecttaskMapper.getRunningTaskList", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collecttask;
	}

	/**
	 * 更新任务状态为停止
	 */
	@Override
	public void updateTaskForTBymissionid(String missionid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		params.put("status", "1");
		params.put("flag", "0");
		try {
			daoSupport.findForObject("CollecttaskMapper.updateTaskInfo", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新任务状态为启动
	 */
	@Override
	public void updateTaskForQBymissionid(String missionid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		params.put("status", "2");
		try {
			daoSupport.findForObject("CollecttaskMapper.updateTaskInfo", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 更新任务状态为暂停
	 */
	@Override
	public void updateTaskForZBymissionid(String missionid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		params.put("status", "4");
		try {
			daoSupport.findForObject("CollecttaskMapper.updateTaskInfo", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 更新任务状态为暂停(到期停止)
	 */
	@Override
	public void updateTaskExpireBymissionid(String missionid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		params.put("status", "3");
		params.put("flag", "0");
		try {
			daoSupport.findForObject("CollecttaskMapper.updateTaskInfo", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public dbinfo getDbinfoById(String missionid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		List<dbinfo> dbinfos=null;
		try {
			dbinfos=(List<dbinfo>) daoSupport.findForList("missionMapper.getDbinfoById", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbinfo dbinfo = new dbinfo();
		if(dbinfos.size()>0){
			dbinfo=dbinfos.get(0);
		}
		return dbinfo;
	}

	/**
	 * 保存新任务与机器人的关系
	 */
	@Override
	public void saveMissionRobot(int missionid, String robotid, String remark) {
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
		params.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
		params.put("missionid", missionid+"");
		params.put("robotid", robotid);
		params.put("remark", remark);
		params.put("createtime", dateFormater.format(date));
		try {
			daoSupport.findForObject("MissionRobotMapper.insertMissionRobot", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 根据任务id，从表mission_mission中查询正在执行的任务列表，返回一个list集合
	 */
	@Override
	public List<dbinfo> getdbinfoListByMissionid(String missionid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getdbinfoListByMissionid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	/**
	 * 根据用户名查询正在执行的任务信息
	 */
	@Override
	public List<dbinfo> getdbinfoRunningListByName(String name) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getdbinfoRunningListByName", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	/**
	 * 根据机器人的id获取机器人的信息
	 */
	@Override
	public Collecttask getCollecttaskById(Map<String, String> params) {
		List<Collecttask> collecttasks=null;
		try {
			collecttasks=(List<Collecttask>) daoSupport.findForList("CollecttaskMapper.getCollecttaskById", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collecttask collecttask = new Collecttask();
		if(collecttasks.size()>0){
			collecttask = collecttasks.get(0);
		}
		return collecttask;
	}

	/**
	 * 根据任务id获取机器人信息
	 */
	@Override
	public Collecttask getUpdateTimeByMissionid(String missionid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		Collecttask collecttask = null;
		try {
			collecttask = (Collecttask) daoSupport.findForObject(
					"CollecttaskMapper.getUpdateTimeByMissionid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collecttask;
	}

	/**
	 * 更新机器人执行时间
	 */
	@Override
	public void updateUpdateTimeByMissionid(Map<String, String> params) {
		try {
			daoSupport.findForObject("CollecttaskMapper.updateUpdateTimeByMissionid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询出与该机器人相关的任务列表
	 */
	@Override
	public List<dbinfo> getMissionListByTask(String missionidt) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionidt);
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getMissionListByTask", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	/**
	 * 查询未停止的任务表
	 */
	@Override
	public List<dbinfo> getDbinfoListForOverdue() {
		Map<String, String> params = new HashMap<String, String>();
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getDbinfoListForOverdue", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	/**
	 * 修改任务状态为3
	 */
	@Override
	public void updateStyleByMissionid(String missionid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		params.put("style", "3");
		try {
			daoSupport.findForObject("missionMapper.updateStyleByMissionid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * 根据id获取对应的任务信息
	 */
	@Override
	public List<dbinfo> getInfoListById(String missionid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getInfoListById", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	/**
	 * 更新暂停任务信息
	 */
	@Override
	public void updateMissionStyleByid(Map<String, String> params) {
		try {
			daoSupport.findForObject("missionMapper.updateMissionStyleByid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据开始结束时间查询符合条件的属地数据
	 */
	@Override
	public List<dbinfo> getPicturesByTime(String missionid, String nowTime, String e_time) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		params.put("nowTime", nowTime);
		params.put("e_time", e_time);
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("missionMapper.getPicturesByTime", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbinfo;
	}

	/**
	 * 根据id更新任务信息达到上限
	 */
	@Override
	public void updateMissionById(String content1, String missionid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("query", content1);
		params.put("missionid", missionid);
		try {
			daoSupport.findForList("missionMapper.updateMissionById", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateMissionStatusById(String missionid, int status) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("missionid", missionid);
		params.put("status", status+"");
		try {
			daoSupport.findForList("missionMapper.updateMissionStatusById", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
