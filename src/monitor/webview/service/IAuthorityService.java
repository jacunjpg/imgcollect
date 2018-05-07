package monitor.webview.service;

import java.util.List;
import java.util.Map;

import monitor.webview.entity.LoginUser;
import monitor.webview.entity.Page;
import monitor.webview.entity.User_Authority;

public interface IAuthorityService {

	public List<LoginUser> getAllUser(Map<String, String> params);
	public List<User_Authority> getAuthority(Map<String, String> params);
	//用户所有的权限
	public LoginUser getAuthorityByName(Map<String, String> params);
	public String upDateAuthority(Map<String, String> params);
	public Object deleteUser(Map<String, String> params);
	public void deleteCheckedUser(Map<String, String> params);
	public void addUser(Map<String, String> maps);
	public List<LoginUser> searchUser(Map<String, String> maps);
	//根据name查询
	public LoginUser findUser(Map<String, String> maps);
	//根据ID查询
	public LoginUser findUserByID(Map<String, String> maps);
	//修改用戶權限
	public Object updateUserByID(Map<String, String> maps);
	public List<LoginUser> getUsers(Page page);
	public Object getAllUserCount(Map<String, String> maps);
	//查询全部用户
	public List<LoginUser> searchAllUser(Map<String, String> maps);
	//模糊查询的分页
	public Object getSearchUserCount(Map<String, String> mapss);
	public List<LoginUser> getSearchUsers(Page page);
	public List<LoginUser> getSearchedUsers(Map<String, String> mapss);
	public Object getAllUserCountMinAdmin(Map<String, String> maps);
	public void insertToFlag(Map<String, String> params);
	public LoginUser getInfoByMissionid(String missionid);
	public List<LoginUser> getdbinfoListByAppInfo(
			String sinaappid, String sinaappkey);
	public LoginUser getAuthorityByMissionid(Map<String, String> params);

}
