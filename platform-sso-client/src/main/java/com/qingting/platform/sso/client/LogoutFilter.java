package com.qingting.platform.sso.client;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 单点退出Filter
 * 
 * @author zlf
 */
public class LogoutFilter extends ClientFilter {

	/**
	 * 单点退出成功后跳转页(配置当前应用上下文相对路径，不设置或为空表示项目根目录)
	 */
	protected String ssoBackUrl;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		ssoBackUrl = filterConfig.getInitParameter("ssoBackUrl");
		if (ssoBackUrl == null){
			ssoBackUrl = "";
		}
	}

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.getSession().invalidate();//清除本地的用户缓存
		String ssoLogoutUrl = new StringBuilder().append(ssoServerUrl).append("/logout?backUrl=")
					.append(getLocalUrl(request)).append(ssoBackUrl).toString();
		response.sendRedirect(ssoLogoutUrl);
	}

	/**
	 * 获取当前上下文路径
	 * @param request
	 * @return String
	 */
	private String getLocalUrl(HttpServletRequest request) {
		return new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName())
				.append(":").append(request.getServerPort() == 80 ? "" : request.getServerPort())
				.append(request.getContextPath()).toString();
	}
}