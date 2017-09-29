package com.qingting.platform.sso.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.qingting.platform.common.Result;
import com.qingting.platform.config.ConfigUtils;
import com.qingting.platform.exception.ServiceException;
import com.qingting.platform.sso.rpc.AuthenticationRpcService;
import com.qingting.platform.util.StringUtils;

/**
 * 单点登录权限系统Filter基类
 * 
 * @author zlf
 */
public abstract class ClientFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientFilter.class);
	
	/**
	 * 单点登录服务端URL
	 */
	protected static String ssoServerUrl = ConfigUtils.getProperty("sso.server.url");
	
	/**
	 * 当前应用关联权限系统的应用编码
	 */
	protected static String ssoAppCode = ConfigUtils.getProperty("sso.app.code");
	/**
	 * 单点登录服务端提供的RPC服务，由Spring容器注入
	 */
	protected static AuthenticationRpcService authenticationRpcService = SpringUtils
			.getBean(AuthenticationRpcService.class);
	/**
	 * 排除拦截
	 */
	protected List<String> excludeList = null;
	
	/**
	 * 初始化排除拦截path
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludes = filterConfig.getInitParameter("excludes");
		if (StringUtils.isNotBlank(excludes)) {
			excludeList = Arrays.asList(excludes.split(","));
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (excludeList != null && excludeList.contains(httpRequest.getServletPath())){
			LOGGER.debug("Path拦截排除：{}",httpRequest.getServletPath());
			chain.doFilter(request, response);
		}else {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			try {
				doFilter(httpRequest, httpResponse, chain);
			}
			catch (ServiceException e) {
				httpResponse.setContentType("application/json;charset=UTF-8");
				httpResponse.setStatus(HttpStatus.OK.value());
				PrintWriter writer = httpResponse.getWriter();
				writer.write(JSON.toJSONString(Result.create(e.getCode()).setMessage(e.getMessage())));
				writer.flush();
				writer.close();
			}
		}
	}

	public abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException, ServiceException;

	@Override
	public void destroy() {
	}
}