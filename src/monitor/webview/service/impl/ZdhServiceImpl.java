package monitor.webview.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import monitor.basic.IBasicDaoSupport;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IZiDonghuaService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("zdhService")
public class ZdhServiceImpl implements IZiDonghuaService{
	
private static Logger logger = Logger.getLogger(ZdhServiceImpl.class);
	
	@Resource(name="basicDaoSupport")
	public IBasicDaoSupport daoSupport;

	@Override
	public void insertmission(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("zdhMapper.inserttomission", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("zdhMapper.getmaxmissionid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdhMapper.getcbklist", params);
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
			 daoSupport.findForObject("zdhMapper.inserttocbklist", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("zdhMapper.getmaxcbkid", params);
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
			daoSupport.findForObject("zdhMapper.insertcbkpicture", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void upadatecbkcover(Map<String, String> params) {
		// TODO Auto-generated method stub
		 try {
			daoSupport.findForObject("zdhMapper.updatecbkcover", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdhMapper.getcbkpicbyid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdhMapper.getcbkpicbypicid", params);
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
			daoSupport.findForObject("zdhMapper.deletcbkpicbycbkid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void deletcbkpicinpicid(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("zdhMapper.deletcbkpicinpicid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deletcbklistbycbkid(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("zdhMapper.deletcbklistbycbkid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdhMapper.getmissionlistbyuser", params);
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
			daoSupport.findForObject("zdhMapper.inserttopicture", list);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdhMapper.getresultbymissionid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdhMapper.getpictureresultbypictureid", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("zdhMapper.getpictureinfobypictureid", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("zdhMapper.getmissioninfo", params);
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
					daoSupport.findForObject("zdhMapper.insertresult", list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	@Override
	public void updatemission(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("zdhMapper.updatemission", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deletemission(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("zdhMapper.deletemission", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public dbinfo getpicturecount(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		dbinfo result=null;
		try {
			result=(dbinfo) daoSupport.findForObject("zdhMapper.getpicturecount", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public dbinfo getresultcount(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		dbinfo result=null;
		try {
			result=(dbinfo) daoSupport.findForObject("zdhMapper.getresultcount", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
