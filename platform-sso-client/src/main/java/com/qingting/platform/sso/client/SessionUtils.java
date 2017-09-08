package com.qingting.platform.sso.client;

import javax.servlet.http.HttpServletRequest;


/**
 * 当前已登录用户Session
 * @author zlf
 */
public class SessionUtils {
	/**
	 * 单点登陆服务器用户信息
	 */
	public static final String SESSION_USER = "_sessionUser";
	
	/**
	 * 用户权限
	 */
	public static final String SESSION_USER_PERMISSION = "_sessionUserPermission";
	
	/**
	 * 验证码
	 */
	public static final String VALIDATE_CODE="_validateCode";
	
	public static SessionUser getSessionUser(HttpServletRequest request) {
		return (SessionUser) request.getSession().getAttribute(SESSION_USER);
	}

	public static void setSessionUser(HttpServletRequest request, SessionUser sessionUser) {
		request.getSession().setAttribute(SESSION_USER, sessionUser);
	}
	
	public static SessionPermission getSessionPermission(HttpServletRequest request) {
		SessionUser user = SessionUtils.getSessionUser(request);
		if (PermissionJmsMonitor.isChanged && !PermissionJmsMonitor.tokenSet.contains(user.getToken()))
			return null;
		else
			return (SessionPermission) request.getSession().getAttribute(SESSION_USER_PERMISSION);
	}

	public static void setSessionPermission(HttpServletRequest request, SessionPermission sessionPermission) {
		request.getSession().setAttribute(SESSION_USER_PERMISSION, sessionPermission);
	}
	
	public static String getSessionValidateCode(HttpServletRequest request){
		return (String)request.getSession().getAttribute(VALIDATE_CODE);
	}
	
	public static void setSessionValidateCode(HttpServletRequest request,String validateCode){
		request.getSession().setAttribute(VALIDATE_CODE, validateCode);
	}
	
}