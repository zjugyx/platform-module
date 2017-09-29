package com.qingting.platform.sso.client;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qingting.platform.common.ResultCode;
import com.qingting.platform.exception.ServiceException;
import com.qingting.platform.sso.rpc.RpcUser;
import com.qingting.platform.util.StringUtils;


/**
 * 单点 登录Filter
 * @author zlf
 */
public class SsoFilter extends ClientFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SsoFilter.class);

	/**
	 * sso授权回调参数token名称
	 */
	public static final String SSO_TOKEN_NAME = "__vt_param__";
	/**
	 * 自定义登陆页
	 */
	protected String loginPath=null;
	
	/**
	 * 初始化自定义登陆页
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		String loginPath = filterConfig.getInitParameter("loginPath");
		if (StringUtils.isNotBlank(loginPath)){
			this.loginPath=loginPath;
		}
	}
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.debug("sso登陆拦截");
		String token = getLocalToken(request);
		if (token == null) {
			if (getParameterToken(request) != null) {
				// 再跳转一次当前URL，以便去掉URL中token参数
				response.sendRedirect(request.getRequestURL().toString());
			}
			else
				redirectLogin(request, response);
		}
		else if (isLogined(token)){
			LOGGER.debug("已登陆");
			chain.doFilter(request, response);
		}else{
			redirectLogin(request, response);
		}
	}

	/**
	 * 获取Session中token
	 * 
	 * @param request
	 * @return
	 */
	private String getLocalToken(HttpServletRequest request) {
		SessionUser sessionUser = SessionUtils.getSessionUser(request);
		return sessionUser == null ? null : sessionUser.getToken();
	}

	/**
	 * 获取服务端回传token参数且验证
	 * @param request
	 * @return String
	 * @throws IOException 
	 */
	private String getParameterToken(HttpServletRequest request) throws IOException {
		String token = request.getParameter(SSO_TOKEN_NAME);
		if (token != null) {
			RpcUser rpcUser = authenticationRpcService.findAuthInfo(token);
			if (rpcUser != null) {
				invokeAuthenticationInfoInSession(request, token, rpcUser.getAccount());
				return token;
			}
		}
		return null;
	}

	/**
	 * 跳转登录
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void redirectLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (isAjaxRequest(request)) {
			throw new ServiceException(ResultCode.SSO_TOKEN_ERROR, "未登录或已超时");
		}
		else {
			request.getSession().invalidate();
			
			if(loginPath==null){
				String ssoLoginUrl = new StringBuilder().append(ssoServerUrl).append("/login?backUrl=")
						.append(request.getRequestURL()).append("&appCode=").append(ssoAppCode).toString();
				LOGGER.debug("未登陆，重定向登陆："+ssoLoginUrl);
				response.sendRedirect(ssoLoginUrl);
			}else{
				/*String ssoLoginUrl = new StringBuilder().append(getLocalUrl(request)+loginPath).append("?backUrl=")
						.append(request.getRequestURL()).append("&appCode=").append(ssoAppCode).toString();*/
				String ssoLoginUrl = new StringBuilder().append(SsoLocalUtil.getLocalUrl(request)+loginPath).append("?backUrl=")
						.append(SsoLocalUtil.getLocalUrl(request)+request.getServletPath()).append("&appCode=").append(ssoAppCode).toString();
				LOGGER.debug("未登陆，重定向登陆："+ssoLoginUrl);
				response.sendRedirect(ssoLoginUrl);
			}
		}
	}

	/**
	 * 保存认证信息到Session
	 * 
	 * @param token
	 * @param account
	 * @param profile
	 */
	private void invokeAuthenticationInfoInSession(HttpServletRequest request, String token, String account) {
		SessionUtils.setSessionUser(request, new SessionUser(token, account));
	}

	/**
	 * 判断是否已登录
	 * @param token
	 * @return boolean
	 */
	private boolean isLogined(String token) {
		return authenticationRpcService.validate(token);
	}

	/**
	 * 判断是否Ajax请求
	 * @param request
	 * @return boolean
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}
	
	/**
	 * 获取当前上下文路径
	 * @param request
	 * @return String
	 */
	//已移动到SsoLocalUtil中,方便客户端域名配置使用
	/*private String getLocalUrl(HttpServletRequest request) {
		if(ssoClientUrl!=null && !StringUtils.isBlank(ssoClientUrl)){
			String contextPath=request.getContextPath();
			return ssoClientUrl;//+request.getContextPath();
		}else{
			return new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName())
					.append(":").append(request.getServerPort() == 80 ? "" : request.getServerPort())
					.append(request.getContextPath()).toString();
		}
	}*/
	
}