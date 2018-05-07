package monitor.webview.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import monitor.basic.IBasicDaoSupport;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.ICbjcService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("cbjcService")
public class CbjcServiceImpl implements ICbjcService{

	
private static Logger logger = Logger.getLogger(CbjcServiceImpl.class);
	
	@Resource(name="basicDaoSupport")
	public IBasicDaoSupport daoSupport;

	@Override
	public void insertmission(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("cbjcMapper.inserttomission", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public dbinfo getmaxmissionid(Map<String, String> params) {
		// TODO Auto-generated method stub
		dbinfo dbinfo=null;
		try {
			dbinfo=(dbinfo) daoSupport.findForObject("cbjcMapper.getmaxmissionid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public List<dbinfo> getcbklistbyuser(Map<String, String> params) {
		// TODO Auto-generated method stub
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("cbjcMapper.getcbklist", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public void insertcbklist(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			 daoSupport.findForObject("cbjcMapper.inserttocbklist", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public dbinfo getmaxcbkid(Map<String, String> params) {
		// TODO Auto-generated method stub
		dbinfo dbinfo=null;
		try {
			dbinfo=(dbinfo) daoSupport.findForObject("cbjcMapper.getmaxcbkid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public void insertcbkpicture(List<dbinfo> list) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("cbjcMapper.insertcbkpicture", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void upadatecbkcover(Map<String, String> params) {
		// TODO Auto-generated method stub
		 try {
			daoSupport.findForObject("cbjcMapper.updatecbkcover", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<dbinfo> getcbkpicbyid(Map<String, String> params) {
		// TODO Auto-generated method stub
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("cbjcMapper.getcbkpicbyid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public List<dbinfo> getcbkpicbypicid(Map<String, String> params) {
		// TODO Auto-generated method stub
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("cbjcMapper.getcbkpicbypicid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public void deletcbkpicbycbkid(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("cbjcMapper.deletcbkpicbycbkid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void deletcbkpicinpicid(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("cbjcMapper.deletcbkpicinpicid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deletcbklistbycbkid(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("cbjcMapper.deletcbklistbycbkid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<dbinfo> getmissionlistbyuser(Map<String, String> params) {
		// TODO Auto-generated method stub
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("cbjcMapper.getmissionlistbyuser", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public void inserttopicture(List<dbinfo> list) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("cbjcMapper.inserttopicture", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<dbinfo> getresultbymissionid(Map<String, String> params) {
		// TODO Auto-generated method stub
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("cbjcMapper.getresultbymissionid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public List<dbinfo> getpictureresultbypictureid(Map<String, String> params) {
		// TODO Auto-generated method stub
		List<dbinfo> dbinfo=null;
		try {
			dbinfo=(List<dbinfo>) daoSupport.findForList("cbjcMapper.getpictureresultbypictureid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public dbinfo getpictureinfobypictureid(Map<String, String> params) {
		// TODO Auto-generated method stub
		dbinfo dbinfo=null;
		try {
			dbinfo=(dbinfo) daoSupport.findForObject("cbjcMapper.getpictureinfobypictureid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public dbinfo getmissioninfo(Map<String, String> params) {
		// TODO Auto-generated method stub
		dbinfo dbinfo=null;
		try {
			dbinfo=(dbinfo) daoSupport.findForObject("cbjcMapper.getmissioninfo", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbinfo;
	}

	@Override
	public void insertresult(List<dbinfo> list) {
		// TODO Auto-generated method stub
				try {
					daoSupport.findForObject("cbjcMapper.insertresult", list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	@Override
	public void updatemission(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("cbjcMapper.updatemission", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deletemission(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("cbjcMapper.deletemission", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
