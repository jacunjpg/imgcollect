package monitor.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import monitor.util.CommonUtil;
import monitor.util.ConfigInfo;
import monitor.webview.entity.LoginUser;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CommonInterceptor implements HandlerInterceptor{
	
	private static Logger logger = Logger.getLogger(CommonInterceptor.class);
	private static final String NOLOGIN_URL = "/login/loginPage.do"; 
	
	/** 
     * 在DispatcherServlet完全处理完请求后被调用  
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
     */
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
	//在业务处理器处理请求执行完成后,生成视图之前执行的动作
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse res,
			Object arg2, ModelAndView arg3) throws Exception {
	}
	/** 
     * 在业务处理器处理请求之前被调用 
     * 如果返回false 
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     *  
     * 如果返回true 
     *    执行下一个拦截器,直到所有的拦截器都执行完毕 
     *    再执行被拦截的Controller 
     *    然后进入拦截器链, 
     *    从最后一个拦截器往回执行所有的postHandle() 
     *    接着再从最后一个拦截器往回执行所有的afterCompletion() 
     */  
	@SuppressWarnings("static-access")
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse res,
			Object arg2) throws Exception {
		// 取得请求相关的ActionContext实例
		LoginUser user = (LoginUser) CommonUtil.getSessionAttribute(ConfigInfo.SESSION_USER);
		String wx = (String)CommonUtil.getSession().getAttribute("wxcode");
        String baseUri = arg0.getContextPath();
        String path = arg0.getServletPath();
		if(wx==null) wx = "0";
		//如果没有登陆
		if (user != null ) {
			return true;
		} else {
			if (path.endsWith(".jsp")) {
				res.setStatus(res.SC_GATEWAY_TIMEOUT);
				res.sendRedirect(baseUri+NOLOGIN_URL);
                return false;
			}
			res.sendRedirect(baseUri + NOLOGIN_URL);
			logger.info("not logged in: " + (baseUri + NOLOGIN_URL));
			return false;
		}
	}

}
