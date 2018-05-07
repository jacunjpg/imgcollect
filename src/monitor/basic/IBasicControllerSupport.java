package monitor.basic;

import javax.servlet.http.HttpServletRequest;

public interface IBasicControllerSupport {
	public String getIpAddr(HttpServletRequest request);
}
