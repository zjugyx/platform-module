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
import com.qingting.platform.sso.jwt.JWTProvider;
import com.qingting.platform.sso.rpc.RpcUser;
import com.qingting.platform.util.CookieUtils;
import com.qingting.platform.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;


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
		System.out.println("method:"+request.getMethod());
		/*if(request.getMethod().equals("OPTIONS")){//跨域请求
			if (getRequestToken(request) != null) {
				try {
					Claims claims = JWTProvider.parseJWT(getRequestToken(request));
					if(claims!=null&&claims.getSubject()!=null){
						LOGGER.debug("claims已登陆,subject="+claims.getSubject());
						chain.doFilter(request, response);
					}else{
						LOGGER.debug("claims空，未登陆"+claims);
						throw new ServiceException(ResultCode.SSO_TOKEN_ERROR, "claims空，未登录或已超时");
					}
				} catch (ExpiredJwtException e) {
					e.printStackTrace();
					throw new ServiceException(ResultCode.SSO_TOKEN_ERROR, "未登录或已超时");
				} catch (Exception e){
					e.printStackTrace();
					throw new ServiceException(ResultCode.ERROR, e.getMessage());
				}
			}else{
				redirectLogin(request, response);
			}
		}else{
			String token = getLocalToken(request);
			if (token == null) {
				String requestToken=getRequestToken(request);
				if (requestToken != null) {
					
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
		}*/
		/*if(request.getMethod().equals("OPTIONS")){
			chain.doFilter(request, response);
		}*/
		String token = getLocalToken(request);
		if (token == null) {
			if (getRequestToken(request) != null) {
				// 再跳转一次当前URL，以便去掉URL中token参数
				//response.sendRedirect(request.getRequestURL().toString());
				
				chain.doFilter(request, response);
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
	@Deprecated
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
	 * 获取token参数且验证
	 * @param request
	 * @return String
	 * @throws IOException 
	 */
	public static String getRequestToken(HttpServletRequest request) throws IOException {
		String token=null;
		String authToken=null;
		//检查cookie中的token
		token=CookieUtils.getCookie(request, "token");
		authToken=authentication(request,token);
		if(authToken!=null)
			return authToken;
		//检查请求参数的token
		token = request.getParameter(SSO_TOKEN_NAME);
		authToken=authentication(request,token);
		if(authToken!=null)
			return authToken;
		//检查header中的Authorization
		token=request.getHeader("Authorization");
		authToken=authentication(request,token);
		if(authToken!=null)
			return authToken;
		return null;
	}

	private static String authentication(HttpServletRequest request,String token){
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
	private static void invokeAuthenticationInfoInSession(HttpServletRequest request, String token, String account) {
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
		if(request.getMethod().equals("OPTIONS")){//跨域试探
			String requestedWith=request.getHeader("Access-Control-Request-Headers");
			return requestedWith != null ? "x-requested-with".equals(requestedWith) : false;
		}else{
			String requestedWith = request.getHeader("X-Requested-With");
			return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
		}
	}
}