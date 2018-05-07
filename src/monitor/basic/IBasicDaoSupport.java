package monitor.basic;

import java.util.List;

import monitor.webview.entity.Page;

/**
 * 数据库相应操作
 * @author Administrator
 *
 */
public interface IBasicDaoSupport {
	/**
	 * 保存对象
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object save(String sql, Object obj) throws Exception;

	/**
	 * 批量添加
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int saveBatch(String path, List<?> entitys)throws Exception;

	/**
	 * 修改对象
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object update(String sql, Object obj) throws Exception;

	/**
	 * 批量更新
	 * @param sql
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Object updateBatch(String sql, List<Object> list) throws Exception;

	/**
	 * 批量更新
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object updateBatch(String sql, Object obj[]) throws Exception;

	/**
	 * 删除对象
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object delete(String sql, Object obj) throws Exception;

	/**
	 * 批量删除
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object deleteBatch(String sql, Object obj[]) throws Exception;

	/**
	 * 批量删除
	 * @param sql
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Object deleteBatch(String sql, List<?> list) throws Exception;

	/**
	 * 查找对象
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String sql, Object obj) throws Exception;
	
	/**
	 * 模糊查找对象
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List findForMoreObject(String sql, Object obj) throws Exception;
	
	/**
	 * 分頁查找对象
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List findForObjectByPage(String sql, Page page) throws Exception;
	
	/**
	 * 查找对象
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String sql) throws Exception;

	/**
	 * 查找对象集合
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForList(String sql, Object obj) throws Exception;
	
	/**
	 * 查找对象集合
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForList(String sql) throws Exception;

	/**
	 * 查找对象封装成Map
	 * 
	 * @param s
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForMap(String sql, Object obj, String key, String value)
			throws Exception;

	/**
	 * 得到记录总数
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public long getcount(String sql, Object obj) throws Exception;

}