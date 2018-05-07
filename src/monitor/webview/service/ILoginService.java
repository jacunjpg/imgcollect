package monitor.webview.service;

import java.util.Map;

import monitor.webview.entity.LoginUser;

public interface ILoginService {
	public LoginUser getUserInfo(Map<String,String> params);
	public LoginUser getUserByTel(Map<String,String> params);
	public LoginUser getUserInfoByName(Map<String,String> params);
}
