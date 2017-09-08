package com.qingting.platform.sso.client;

import com.qingting.platform.config.ConfigUtils;

public class Config {
	/**
	 * 单点登录服务端URL
	 */
	protected static String ssoServerUrl = ConfigUtils.getProperty("sso.server.url");
	/**
	 * 单点登陆自定义sso页面URL
	 */
	protected static String ssoCustomUrl = ConfigUtils.getProperty("sso.custom.url");
	/**
	 * 当前应用关联权限系统的应用编码
	 */
	protected static String ssoAppCode = ConfigUtils.getProperty("sso.app.code");
	
	public static String getSsoServerUrl() {
		return ssoServerUrl;
	}
	public static void setSsoServerUrl(String ssoServerUrl) {
		Config.ssoServerUrl = ssoServerUrl;
	}
	public static String getSsoCustomUrl() {
		return ssoCustomUrl;
	}
	public static void setSsoCustomUrl(String ssoCustomUrl) {
		Config.ssoCustomUrl = ssoCustomUrl;
	}
	public static String getSsoAppCode() {
		return ssoAppCode;
	}
	public static void setSsoAppCode(String ssoAppCode) {
		Config.ssoAppCode = ssoAppCode;
	}
	
	
}
