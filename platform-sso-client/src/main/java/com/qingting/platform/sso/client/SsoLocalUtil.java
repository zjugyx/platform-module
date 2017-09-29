package com.qingting.platform.sso.client;

import javax.servlet.http.HttpServletRequest;

import com.qingting.platform.config.ConfigUtils;
import com.qingting.platform.util.StringUtils;

public class SsoLocalUtil {
	/**
	 * 单点登陆客户端URL
	 */
	protected static String ssoClientUrl=ConfigUtils.getProperty("sso.client.url");
	/**
	 * 获取当前上下文路径
	 * @param request
	 * @return String
	 */
	public static String getLocalUrl(HttpServletRequest request) {
		if(ssoClientUrl!=null && !StringUtils.isBlank(ssoClientUrl)){
			return ssoClientUrl;
		}else{
			return new StringBuilder().append(request.getScheme()).append("://").append(request.getServerName())
					.append(":").append(request.getServerPort() == 80 ? "" : request.getServerPort())
					.append(request.getContextPath()).toString();
		}
	}
}
