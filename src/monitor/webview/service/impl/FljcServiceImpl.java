package monitor.webview.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import monitor.basic.IBasicDaoSupport;
import monitor.webview.entity.LoginUser;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IFljcService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("fljcService")
public class FljcServiceImpl implements IFljcService {
	
	private static Logger logger = Logger.getLogger(FljcServiceImpl.class);
	
	@Resource(name="basicDaoSupport")
	public IBasicDaoSupport daoSupport;

	@Override
	public void insertmission(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("fljcMapper.inserttomission", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public dbinfo getmaxmissionid(Map<String, String> params) {
		// TODO Auto-generated method stub
		dbinfo fljc=null;
		try {
			fljc=(dbinfo) daoSupport.findForObject("fljcMapper.getmaxmissionid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fljc;
	}

	@Override
	public void inserttopicture(List<dbinfo> list) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("fljcMapper.inserttopicture", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<dbinfo> getmissionlistbytypeanduser(Map<String, String> params) {
		// TODO Auto-generated method stub
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("fljcMapper.getmissionlistbytypeanduser", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public List<dbinfo> getresultsbymissionid(Map<String, String> params) {
		// TODO Auto-generated method stub
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("fljcMapper.getresultsbymissionid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public void addPictrueInfo(List<dbinfo> listInfo) {
		try {
			logger.info("批量插入群体事件检测结果开始！");
			daoSupport.findForObject("fljcMapper.insertResultList", listInfo);
			logger.info("批量插入群体事件检测结果成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public dbinfo getmissioninfo(Map<String, String> params) {

		dbinfo fljc=null;
		try {
			fljc=(dbinfo) daoSupport.findForObject("fljcMapper.getmissioninfo", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fljc;
		
	}

	@Override
	public void updatemission(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("fljcMapper.updatemission", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deletemission(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("fljcMapper.deletemission", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	
}
