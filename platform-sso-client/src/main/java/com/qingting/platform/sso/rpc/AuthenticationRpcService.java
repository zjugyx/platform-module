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
	 * 注册用户(不分配应用，不分配角色)
	 * @param token 当前登陆账号token(权限验证用)
	 * @param appCode 应用编码
	 * @param account 账号
	 * @param password 密码
	 * @return Result 返回结果
	 */
	public Result register(String token,String appCode,String account,String password);
	
	/**
	 * 注册用户并分配当前应用中的默认角色
	 * @param token
	 * @param appCode
	 * @param account
	 * @param password
	 * @return Result
	 */
	public Result registerAndAllocateRole(String token,String appCode,String account,String password);
	
	/**
	 * 删除用户
	 * @param token 当前登陆账号token
	 * @param appCode 应用编码
	 * @param accountList 删除的账户集合
	 * @return Result 返回结果
	 */
	public Result deleteByAccount(String token,String appCode,List<String> accountList);
	
	/**
	 * 查询当前应用中用户的所有角色(不会返回已禁用的角色)
	 * @param token
	 * @param appCode
	 * @param account
	 * @return Result data为List<RpcRole>，其中isChecked属性:true代表用户有这个角色，flase代表无此角色
	 */
	Result listRole(String token,String appCode,String account);
	
	/**
	 * 给用户分配appCode对应的应用的角色，如角色非appCode中的角色，会越权分配失败
	 * <p>如果用户还未分配当前应用，默认会将当前应用分配给用户
	 * @param token
	 * @param appCode
	 * @param account
	 * @param roleIds 
	 * @return Result
	 */
	public Result allocateRole(String token,String appCode,String account,List<Integer> roleIds);
}
