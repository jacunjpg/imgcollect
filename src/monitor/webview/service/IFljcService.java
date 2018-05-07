package monitor.webview.service;

import java.util.List;
import java.util.Map;

import monitor.webview.entity.LoginUser;
import monitor.webview.entity.dbinfo;

public interface IFljcService {
	/**
	 * 添加任务
	 * @param params
	 */
	public void insertmission(Map<String,String> params);
	/**
	 * 获取最大任务id
	 * @param params
	 * @return
	 */
	public dbinfo getmaxmissionid(Map<String,String> params);
	/**
	 * 插入图片
	 * @param list
	 */
	public void inserttopicture(List<dbinfo> list);
	/**
	 * 通过查找任务列表
	 * @param params
	 * @return 
	 */
	public List<dbinfo> getmissionlistbytypeanduser(Map<String, String> params);
	/**
	 * 获取结果
	 * @param params
	 * @return
	 */
	public List<monitor.webview.entity.dbinfo> getresultsbymissionid(
			Map<String, String> params);
	
	/**
	 * 
	 * 
	 * Title void
	 * Description 将群体事件检测结果存入数据库
	 *
	 * @author jacun
	 * @date 2017-4-27下午4:31:23
	 * @param listInfo
	 */
	public void addPictrueInfo(List<dbinfo> listInfo);
	/**
	 * 获取信息
	 * @param params
	 * @return 
	 */
	public dbinfo getmissioninfo(Map<String, String> params);
	
	public void updatemission(dbinfo dbinfo);
	public void deletemission(dbinfo dbinfo);
	
}
