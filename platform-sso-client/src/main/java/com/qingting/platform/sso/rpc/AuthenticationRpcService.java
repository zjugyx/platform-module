package com.qingting.platform.sso.rpc;

import java.util.List;

import com.qingting.platform.common.Result;



/**
 * 认证服务接口
 * @author zlf
 */
public interface AuthenticationRpcService {
	
	/**
	 * 验证是否已经登录
	 * @param token 授权码
	 * @return boolean
	 */
	public boolean validate(String token);

	/**
	 * 根据登录的Token和应用编码获取授权用户信息
	 * @param token 授权码
	 * @return RpcUser 用户信息
	 */
	public RpcUser findAuthInfo(String token);
	
	/**
	 * 获取当前应用所有权限(含菜单)
	 * @param token 授权码 (如果token不为空，获取当前用户的所有权限)
	 * @param appCode 应用编码
	 * @return List<RpcPermission> 权限对象集合(含菜单)
	 */
	public List<RpcPermission> findPermissionList(String token, String appCode);
	
	/**
	 * 验证用户是否存在
	 * @param account
	 * @return Result
	 */
	public Result findByAccount(String account);
	
	/**
	 * 注册用户
	 * @param token 当前登陆账号token(权限验证用)
	 * @param appCode 应用编码
	 * @param account 账号
	 * @param password 密码
	 * @return Result 返回结果
	 */
	public Result register(String token,String appCode,String account,String password);
	
	/**
	 * 删除用户
	 * @param token 当前登陆账号token
	 * @param appCode 应用编码
	 * @param accountList 账户list
	 * @return Result 返回结果
	 */
	public Result deleteByAccount(String token,String appCode,List<String> accountList);
}
