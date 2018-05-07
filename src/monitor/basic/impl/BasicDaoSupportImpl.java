package monitor.basic.impl;

import java.util.List;

import monitor.basic.IBasicDaoSupport;
import monitor.webview.entity.Page;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 主要的 dao 类
 * 
 * @author Administrator
 * 
 */

@Repository("basicDaoSupport")
public class BasicDaoSupportImpl extends SqlSessionDaoSupport implements
		IBasicDaoSupport {

	/**
	 * mybatis-spring-1.2.x.jar 版本的 sqlSessionTemplate 注入有所改动，必须重写次方法
	 */
	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	@Override
	public Object delete(String sql, Object obj) throws Exception {
		SqlSession session = null;
		Object object = null;
		try {
			session = this.getSqlSession();
			object = session.delete(sql, obj);
		} catch (Exception e) {
			throw e;
		}
		return object;
	}

	@Override
	public Object deleteBatch(String sql, Object[] obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deleteBatch(String sql, List<?> list) throws Exception {
		int count = 0;
		SqlSession session = null;
		try {
			session = this.getSqlSession();
			for (Object entity : list) {
				count += session.delete(sql, entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			count = -1;
		}
		return count;
	}

	@Override
	public Object findForList(String sql, Object obj) throws Exception {
		SqlSession session = null;
		Object object = null;
		try {
			session = this.getSqlSession();
			object = session.selectList(sql, obj);
		} catch (Exception e) {
			throw e;
		}
		return object;
	}

	@Override
	public Object findForList(String sql) throws Exception {
		SqlSession session = null;
		Object object = null;
		try {
			session = this.getSqlSession();
			object = session.selectList(sql);
		} catch (Exception e) {
			throw e;
		}
		return object;
	}

	@Override
	public Object findForMap(String sql, Object obj, String key, String value)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findForObject(String sql, Object obj) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session = null;
		Object object = null;
		try {
			session = this.getSqlSession();
			object = session.selectOne(sql, obj);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return object;
	}

	@Override
	public Object findForObject(String sql) throws Exception {
		SqlSession session = null;
		Object object = null;
		try {
			session = this.getSqlSession();
			object = session.selectOne(sql);
		} catch (Exception e) {
			throw e;
		}
		return object;
	}

	@Override
	public long getcount(String sql, Object obj) throws Exception {
		SqlSession session = null;
		int n = 0;
		try {
			session = this.getSqlSession();
			n = session.selectOne(sql, obj);
		} catch (Exception e) {
			throw e;
		}
		return n;
	}

	@Override
	public Object save(String sql, Object paramObj) throws Exception {
		SqlSession session = null;
		Object object = null;
		try {
			session = this.getSqlSession();
			object = session.insert(sql, paramObj);
		} catch (Exception e) {
			throw e;
		}
		return object;
	}

	public int saveBatch(String path, List<?> entitys) throws Exception {
		int count = 0;
		SqlSession session = null;
		try {
			session = this.getSqlSession();
			for (Object entity : entitys) {
				count += session.insert(path, entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			count = -1;
		}
		return count;
	}

	@Override
	public Object update(String sql, Object obj) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session = null;
		Object object = null;
		try {
			session = this.getSqlSession();
			object = session.update(sql, obj);
		} catch (Exception e) {
			throw e;
		}
		return object;
	}

	@Override
	public Object updateBatch(String sql, List<Object> list) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object updateBatch(String sql, Object[] obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findForObjectByPage(String sql, Page page) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session = null;
		List list = null;
		try {
			session = this.getSqlSession();
			list = session.selectList(sql, page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return list;
	}

	@Override
	public List findForMoreObject(String sql, Object obj) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session = null;
		List list = null;
		try {
			session = this.getSqlSession();
			list = session.selectList(sql, obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return list;
	}

}
