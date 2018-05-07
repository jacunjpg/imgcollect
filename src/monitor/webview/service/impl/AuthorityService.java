package monitor.webview.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import monitor.basic.IBasicDaoSupport;
import monitor.webview.entity.LoginUser;
import monitor.webview.entity.Page;
import monitor.webview.entity.User_Authority;
import monitor.webview.entity.dbinfo;
import monitor.webview.service.IAuthorityService;

@Service("authorityService")
public class AuthorityService implements IAuthorityService {

	private static Logger logger = Logger.getLogger(AuthorityService.class);
	@Resource(name = "basicDaoSupport")
	public IBasicDaoSupport daoSupport;

	// 查詢所有權限
	@SuppressWarnings("unchecked")
	@Override
	public List<User_Authority> getAuthority(Map<String, String> params) {
		// TODO Auto-generated method stub
		List<User_Authority> list = null;
		try {
			list = (List<User_Authority>) daoSupport.findForList(
					"AuthorityMapper.getAuthorities", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查詢所有用戶
	@SuppressWarnings("unchecked")
	@Override
	public List<LoginUser> getAllUser(Map<String, String> params) {
		List<LoginUser> list = null;
		try {
			list = (List<LoginUser>) daoSupport.findForList(
					"userMapper.getAllUser", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 添加用户权限
	@Override
	public String upDateAuthority(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginUser getAuthorityByName(Map<String, String> params) {
		LoginUser loginUser = null;
		try {
			loginUser = (LoginUser) daoSupport.findForObject(
					"userMapper.getUserInfoByName", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginUser;
	}

	// 刪除用戶
	@Override
	public Object deleteUser(Map<String, String> params) {
		Object num = 0;
		try {
			num = (Integer) daoSupport.findForObject("userMapper.deleteUser", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	// 暫未實現
	@Override
	public void deleteCheckedUser(Map<String, String> params) {
		// TODO Auto-generated method stub

	}

	// 添加用户
	@Override
	public void addUser(Map<String, String> maps) {
		// TODO Auto-generated method stub
		try {
			daoSupport.findForObject("userMapper.addUser", maps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginUser> searchUser(Map<String, String> maps) {
		List<LoginUser> users = null;
		try {
			users = (List<LoginUser>) daoSupport.findForMoreObject(
					"userMapper.searchUser", maps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public LoginUser findUser(Map<String, String> maps) {
		LoginUser user = null;
		try {
			user = (LoginUser) daoSupport.findForObject(
					"userMapper.getUserInfoByName", maps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public LoginUser findUserByID(Map<String, String> maps) {
		LoginUser user = null;
		try {
			user = (LoginUser) daoSupport.findForObject(
					"userMapper.findUserByID", maps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Object updateUserByID(Map<String, String> maps) {
		Object num = 0;
		try {
			num = daoSupport.findForObject("userMapper.updateUserByID", maps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public Object getAllUserCount(Map<String, String> maps) {
		Object num = 0;
		try {
			num = daoSupport.findForObject("userMapper.getAllUserCount", maps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginUser> getUsers(Page page) {
		List<LoginUser> list = null;
		try {
			list = (List<LoginUser>) daoSupport.findForObjectByPage(
					"userMapper.getPageUsers", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginUser> searchAllUser(Map<String, String> maps) {
		List<LoginUser> users = null;
		try {
			users = (List<LoginUser>) daoSupport.findForMoreObject(
					"userMapper.getAllUser", maps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public Object getSearchUserCount(Map<String, String> mapss) {
		Object num = 0;
		try {
			num = daoSupport.findForObject("userMapper.getSearchUserCount",
					mapss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginUser> getSearchUsers(Page page) {
		List<LoginUser> list = null;
		try {
			list = (List<LoginUser>) daoSupport.findForObjectByPage(
					"userMapper.getSearchPageUsers", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginUser> getSearchedUsers(Map<String, String> mapss) {
		List<LoginUser> users = null;
		try {
			users = (List<LoginUser>) daoSupport.findForList(
					"userMapper.searchedUser", mapss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public Object getAllUserCountMinAdmin(Map<String, String> maps) {
		Object num = 0;
		try {
			num = daoSupport.findForObject("userMapper.getSearchMinUserCount",
					maps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public void insertToFlag(Map<String, String> params) {
		 try {
		 daoSupport.findForObject("userMapper.insertToTableGlag",params);
		 } catch (Exception e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
	}

	/**
	 * 根据missionid查询创建用户的信息
	 */
	@Override
	public LoginUser getInfoByMissionid(String missionid) {
		LoginUser loginUser = null;
		Map<String, String> params =new HashMap<String, String>();
		params.put("missionid", missionid);
		try {
			loginUser = (LoginUser) daoSupport.findForObject(
					"userMapper.getInfoByMissionid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginUser;
	}

	/**
	 * 根据appid和appkey获取相同的的用户信息
	 */
	@Override
	public List<LoginUser> getdbinfoListByAppInfo(String sinaappid,
			String sinaappkey) {
		//;
		Map<String, String> params =new HashMap<String, String>();
		params.put("sinaappid", sinaappid);
		params.put("sinaappkey", sinaappkey);
		List<LoginUser> users = null;
		try {
			users = (List<LoginUser>) daoSupport.findForList(
					"userMapper.getdbinfoListByAppInfo", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public LoginUser getAuthorityByMissionid(Map<String, String> params) {
		LoginUser loginUser = null;
		try {
			loginUser = (LoginUser) daoSupport.findForObject(
					"userMapper.getAuthorityByMissionid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loginUser;
	}
}
