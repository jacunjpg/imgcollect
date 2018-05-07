package monitor.webview.service;

import java.util.List;
import java.util.Map;

import monitor.webview.entity.LoginUser;
import monitor.webview.entity.dbinfo;

public interface IZiDonghuaService {
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
	 * 获取拷贝库列表
	 * @param params
	 * @return
	 */
	public List<dbinfo> getcbklistbyuser(Map<String,String> params);
	/**
	 * 插入拷贝库列表
	 * @param params
	 */
	public void insertcbklist(Map<String, String> params);
	/**
	 * 获取最大的拷贝库id
	 * @param params
	 */
	public dbinfo getmaxcbkid(Map<String, String> params);
	/**
	 * 插入比对库图片
	 * @param list
	 */
	public void insertcbkpicture(List<dbinfo> list);
	/**
	 * 更新比对库封面
	 * @param params
	 */
	public void upadatecbkcover(Map<String, String> params);
	/**
	 * 根据拷贝库编号获取拷贝库图片信息
	 * @param params
	 * @return
	 */
	public List<dbinfo> getcbkpicbyid(Map<String, String> params);
	/**
	 * 根据图片id获取图片信息
	 * @param params
	 * @return
	 */
	public List<dbinfo> getcbkpicbypicid(Map<String, String> params);
	/**
	 * 删除cbkid下的比对库图片	
	 * @param params
	 */
	public void deletcbkpicbycbkid(Map<String, String> params);
	/**
	 * 通过in pictureid删除比对库图片
	 * @param params
	 */
	public void deletcbkpicinpicid(Map<String, String> params);
	/**
	 * 删除对应cbkid的比对库
	 * @param params
	 */
	public void deletcbklistbycbkid(Map<String, String> params);
	/**
	 * 获取对应用户列表
	 * @param params
	 * @return
	 */
	public List<monitor.webview.entity.dbinfo> getmissionlistbyuser(
			Map<String, String> params);
	/**
	 * 添加检测图片
	 * @param list
	 */
	public void inserttopicture(List<dbinfo> list);
	/**
	 * 获取某个任务下所有图片的结果统计 需missionid pointmax(分值上限) pointmin(分值下限) output(输出类型 包括:all(全部) onlypoint(仅有结果) only(仅有空结果))
	 * @param params
	 * @return
	 */
	public List<monitor.webview.entity.dbinfo> getresultbymissionid(
			Map<String, String> params);
	/**
	 * 获取单张图片的结果信息，图片姐过可能出现多条
	 * @param params
	 * @return
	 */
	public List<monitor.webview.entity.dbinfo> getpictureresultbypictureid(
			Map<String, String> params);
	/**
	 * 根据pictureid获取图片信息(检测图片)
	 * @param params
	 * @return
	 */
	public monitor.webview.entity.dbinfo getpictureinfobypictureid(
			Map<String, String> params);
	/**
	 * 获取任务信息
	 * @param params
	 * @return
	 */
	public monitor.webview.entity.dbinfo getmissioninfo(
			Map<String, String> params);
	public void insertresult(List<dbinfo> list);
	
	public void updatemission(dbinfo dbinfo);
	public void deletemission(dbinfo dbinfo);
	public monitor.webview.entity.dbinfo getpicturecount(dbinfo dbinfo);
	public monitor.webview.entity.dbinfo getresultcount(dbinfo dbinfo);
}
