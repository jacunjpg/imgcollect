package monitor.webview.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import monitor.basic.IBasicDaoSupport;
import monitor.webview.entity.LoginUser;
import monitor.webview.service.ILoginService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	
	private static Logger logger = Logger.getLogger(LoginServiceImpl.class);
	
	@Resource(name="basicDaoSupport")
	public IBasicDaoSupport daoSupport;

	@Override
	public LoginUser getUserInfo(Map<String, String> params) {
		LoginUser user = null;
		try {
			if(params.get("name")!=null && params.get("password")==null){
				logger.info("userMapper.getUserInfoByName");
				user = (LoginUser)daoSupport.findForObject("userMapper.getUserInfoByName", params);
			}else{
				logger.info("userMapper.getUserInfo");
				user = (LoginUser) daoSupport.findForObject("userMapper.getUserInfo", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public LoginUser getUserByTel(Map<String, String> params) {
		LoginUser user = null;
		try {
			user = (LoginUser)daoSupport.findForObject("userMapper.getUserByTel", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public LoginUser getUserInfoByName(Map<String, String> params) {
		LoginUser user = null;
		try {
			user = (LoginUser)daoSupport.findForObject("userMapper.getUserInfoByName", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
