package com.qingting.platform.sso.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.qingting.platform.common.ResultCode;
import com.qingting.platform.exception.ServiceException;
import com.qingting.platform.sso.rpc.RpcPermission;

/**
 * 权限控制Filter
 * 
 * @author zlf
 */
public class PermissionFilter extends ClientFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionFilter.class);	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		ApplicationPermissionUtils.initApplicationPermissions(authenticationRpcService, ssoAppCode);
	}

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.debug("sso权限拦截：{}",request.getServletPath());
		String path = request.getServletPath();
		if (isPermitted(request, path))
			chain.doFilter(request, response);
		else if (!ApplicationPermissionUtils.getApplicationPermissionSet().contains(path))
			chain.doFilter(request, response);
		else {
			throw new ServiceException(ResultCode.SSO_PERMISSION_ERROR, "没有访问权限");
		}
	}

	private boolean isPermitted(HttpServletRequest request, String path) {
		Set<String> permissionSet = getLocalPermissionSet(request);
		for (String string : permissionSet) {
			LOGGER.debug("用户的权限：{}",string);
		}
		return permissionSet.contains(path);
	}

	private Set<String> getLocalPermissionSet(HttpServletRequest request) {
		SessionPermission sessionPermission = SessionUtils.getSessionPermission(request);
		if (sessionPermission == null) {
			sessionPermission = invokePermissionInSession(request);
		}
		return sessionPermission.getPermissionSet();
	}

	/**
	 * 保存权限信息
	 * @param request
	 * @return SessionPermission
	 */
	public SessionPermission invokePermissionInSession(HttpServletRequest request) {
		SessionUser user = SessionUtils.getSessionUser(request);
		List<RpcPermission> dbList = authenticationRpcService.findPermissionList(user.getToken(), ssoAppCode);

		List<RpcPermission> menuList = new ArrayList<RpcPermission>();
		Set<String> operateSet = new HashSet<String>();
		for (RpcPermission menu : dbList) {
			if (menu.getIsMenu()) {
				menuList.add(menu);
			}
			if (!com.qingting.platform.util.StringUtils.isBlank(menu.getUrl())) {
				operateSet.add(menu.getUrl());
			}
		}

		SessionPermission sessionPermission = new SessionPermission();
		// 设置登录用户菜单列表
		sessionPermission.setMenuList(menuList);

		// 保存登录用户没有权限的URL，方便前端去隐藏相应操作按钮
		Set<String> noPermissionSet = new HashSet<String>(ApplicationPermissionUtils.getApplicationPermissionSet());
		noPermissionSet.removeAll(operateSet);

		sessionPermission.setNoPermissions(StringUtils.arrayToDelimitedString(noPermissionSet.toArray(), ","));

		// 保存登录用户权限列表
		sessionPermission.setPermissionSet(operateSet);
		SessionUtils.setSessionPermission(request, sessionPermission);

		// 添加权限监控集合，当前session已更新最新权限
		if (PermissionJmsMonitor.isChanged) {
			PermissionJmsMonitor.tokenSet.add(user.getToken());
		}
		return sessionPermission;
	}
}