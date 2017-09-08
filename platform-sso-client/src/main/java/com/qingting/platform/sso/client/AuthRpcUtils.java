package com.qingting.platform.sso.client;


import com.qingting.platform.common.Result;
import com.qingting.platform.sso.rpc.AuthenticationRpcService;

/**
 * sso服务工具
 * @author zlf
 */
public class AuthRpcUtils {
	/**
	 * 单点登录服务端提供的RPC服务，由Spring容器注入
	 */
	protected static AuthenticationRpcService authenticationRpcService = SpringUtils
			.getBean(AuthenticationRpcService.class);
	/**
	 * 注册用户
	 * @param appCode 应用编码
	 * @param phone 账号
	 * @param password 密码
	 * @return Result
	 */
	public static Result register(String token,String appCode,String account,String password){
		return authenticationRpcService.register(token,appCode,account, password);
	}
	/**
	 * 验证账户是否存在
	 * @param account
	 * @return Result 
	 * <p>code={@link com.qingting.platform.common.ResultCode#SUCCESS}存在
	 * <p>code={@link com.qingting.platform.common.ResultCode#FAILURE}不存在
	 */
	public static Result findByAccount(String account){
		return authenticationRpcService.findByAccount(account);
	}
	/**
	 * 更新密码
	 * @param account
	 * @param password
	 * @return Result
	 */
	/*public static Result updatePassword(String account,String password){
		return authenticationRpcService.updatePassword(account, password);
	}*/
	/**
	 * 删除用户
	 * @param idList id集合
	 * @return Result
	 */
	/*public static Result deleteById(List<Integer> idList){
		return authenticationRpcService.deleteById(idList);
	}*/
}
