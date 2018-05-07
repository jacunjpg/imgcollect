package monitor.webview.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import monitor.basic.IBasicDaoSupport;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.ICbjcService;
import monitor.webview.service.IRljcService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("rljcService")
public class RljcServiceImpl implements IRljcService{

	
private static Logger logger = Logger.getLogger(RljcServiceImpl.class);
	
	@Resource(name="basicDaoSupport")
	public IBasicDaoSupport daoSupport;

	@Override
	public void insertmission(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("rljcMapper.inserttomission", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("rljcMapper.getmaxmissionid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("rljcMapper.getcbklist", params);
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
			 daoSupport.findForObject("rljcMapper.inserttocbklist", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("rljcMapper.getmaxcbkid", params);
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
			daoSupport.findForObject("rljcMapper.insertcbkpicture", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void upadatecbkcover(Map<String, String> params) {
		// TODO Auto-generated method stub
		 try {
			daoSupport.findForObject("rljcMapper.updatecbkcover", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("rljcMapper.getcbkpicbyid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("rljcMapper.getcbkpicbypicid", params);
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
			daoSupport.findForObject("rljcMapper.deletcbkpicbycbkid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void deletcbkpicinpicid(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("rljcMapper.deletcbkpicinpicid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deletcbklistbycbkid(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("rljcMapper.deletcbklistbycbkid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("rljcMapper.getmissionlistbyuser", params);
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
			daoSupport.findForObject("rljcMapper.inserttopicture", list);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("rljcMapper.getresultbymissionid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("rljcMapper.getpictureresultbypictureid", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("rljcMapper.getpictureinfobypictureid", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("rljcMapper.getmissioninfo", params);
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
					daoSupport.findForObject("rljcMapper.insertresult", list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	@Override
	public void updatemission(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("rljcMapper.updatemission", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deletemission(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("rljcMapper.deletemission", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
