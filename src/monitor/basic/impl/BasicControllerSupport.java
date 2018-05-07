package monitor.basic.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import monitor.basic.IBasicControllerSupport;
import monitor.basic.IBasicDaoSupport;
import monitor.util.CommonUtil;
import monitor.util.DateUtil;
import monitor.util.IPUtil;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

@Controller("basicControllerSupport")
public class BasicControllerSupport implements IBasicControllerSupport {
	private static Logger logger = Logger.getLogger(BasicControllerSupport.class);
	@Resource(name="basicDaoSupport")
	public IBasicDaoSupport daoSupport;
	
	/**
	 * 获取登录者的IP
	 * @param request
	 * @return ip
	 * */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 记录用户的操作
	 * @param operTye String 操作类型 如：登录
	 * @param result int 操作结果 1：成功,0：失败
	 * @param ip String 用户IP
	 * @return num int
	 * */
	public int addUserOper(String operType, int result, String ip) {
		int num = 0;
		String city = null;
		if (ip != null && !ip.equals("")) {
			city = IPUtil.getArea(ip);
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", CommonUtil.getUser().getId());
		params.put("operType", operType);
		params.put("result", result);
		params.put("operTime", DateUtil.getCurrentTime());
		params.put("ip", ip);
		params.put("city", city);
		try {
			num = (Integer) daoSupport.save("historyMapper.addUserOper", params);
		} catch (Exception e) {
			logger.debug("记录用户的操作失败");
			e.printStackTrace();
		}
		return num;
	}
}
