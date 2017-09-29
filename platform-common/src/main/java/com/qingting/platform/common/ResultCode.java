package com.qingting.platform.common;

/**
 * 基础返回码，具体业务返回码可继承ResultCode
 * 
 * @author zlf
 */
public class ResultCode {
	
	public final static int FAILURE = 0;// 失败
	public final static int SUCCESS = 1;// 成功
	
	//http状态码
	public final static int OK=200; //成功
	
	// SSO 用户授权出错
	public final static int SSO_TOKEN_ERROR = 1001; // TOKEN未授权或已过期
	public final static int SSO_PERMISSION_ERROR = 1002; // 没有访问权限
	
	// 客户端登陆出错
	public final static int CUSTOM_LOGIN_ERROR=2001; //客户端登陆错误
	public final static int CUSTOM_USER_NOEXIST=2002; //客户端用户信息不存在
	
	//操作提示
	public final static int REMIND=3000;//通用提示
	public final static int REPEAT_OPERATE=3001;//重复操作
	public final static int RESULT_NULL=3002;//结果为空
	public final static int DB_NO_DATA=3003;//数据库没有数据
	// 账号注册代码
	public final static int NOEXIST=8001;//数据不存在
	public final static int ISEXIST_ERROR=8002;//数据已经存在错误
	
	// 通用错误以9开头
	public final static int ERROR = 9999;// 未知错误
	public final static int APPLICATION_ERROR = 9000;// 应用级错误
	public final static int VALIDATE_ERROR = 9001;// 参数验证错误
	public final static int SERVICE_ERROR = 9002;// 业务逻辑验证错误
	public final static int CACHE_ERROR = 9003;// 缓存访问错误
	public final static int DAO_ERROR = 9004;// 数据访问错误
	
}
