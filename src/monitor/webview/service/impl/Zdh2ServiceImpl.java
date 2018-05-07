package monitor.webview.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import monitor.basic.IBasicDaoSupport;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IZiDonghuaService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("zdh2Service")
public class Zdh2ServiceImpl implements IZiDonghuaService{

	
private static Logger logger = Logger.getLogger(Zdh2ServiceImpl.class);
	
	@Resource(name="basicDaoSupport")
	public IBasicDaoSupport daoSupport;

	@Override
	public void insertmission(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("zdh2Mapper.inserttomission", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("zdh2Mapper.getmaxmissionid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdh2Mapper.getcbklist", params);
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
			 daoSupport.findForObject("zdh2Mapper.inserttocbklist", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("zdh2Mapper.getmaxcbkid", params);
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
			daoSupport.findForObject("zdh2Mapper.insertcbkpicture", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void upadatecbkcover(Map<String, String> params) {
		// TODO Auto-generated method stub
		 try {
			daoSupport.findForObject("zdh2Mapper.updatecbkcover", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdh2Mapper.getcbkpicbyid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdh2Mapper.getcbkpicbypicid", params);
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
			daoSupport.findForObject("zdh2Mapper.deletcbkpicbycbkid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void deletcbkpicinpicid(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("zdh2Mapper.deletcbkpicinpicid", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deletcbklistbycbkid(Map<String, String> params) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("zdh2Mapper.deletcbklistbycbkid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdh2Mapper.getmissionlistbyuser", params);
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
			daoSupport.findForObject("zdh2Mapper.inserttopicture", list);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdh2Mapper.getresultbymissionid", params);
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
			dbinfo=(List<dbinfo>) daoSupport.findForList("zdh2Mapper.getpictureresultbypictureid", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("zdh2Mapper.getpictureinfobypictureid", params);
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
			dbinfo=(dbinfo) daoSupport.findForObject("zdh2Mapper.getmissioninfo", params);
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
					daoSupport.findForObject("zdh2Mapper.insertresult", list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	@Override
	public void updatemission(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("zdh2Mapper.updatemission", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deletemission(dbinfo dbinfo) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("zdh2Mapper.deletemission", dbinfo);
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
			result=(dbinfo) daoSupport.findForObject("zdh2Mapper.getpicturecount", dbinfo);
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
			result=(dbinfo) daoSupport.findForObject("zdh2Mapper.getresultcount", dbinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
