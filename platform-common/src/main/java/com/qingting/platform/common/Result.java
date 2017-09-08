package com.qingting.platform.common;

import java.io.Serializable;




/**
 * 返回结果
 * 
 * @author zlf
 */
public class Result implements Serializable{

	private static final long serialVersionUID = 68680535878433998L;

	/**
	 * 结果体
	 */
	protected Object data;

	/**
	 * 状态码
	 */
	protected Integer code;

	/**
	 * 信息
	 */
	protected String message;

	private Result() {
		super();
	}

	private Result(Integer code) {
		this.code = code;
	}

	public static Result create(Integer code) {
		return new Result(code);
	}
	
	public static Result createFailureResult(){
		return create(ResultCode.FAILURE);
	}
	
	public static Result createSuccessResult() {
		return create(ResultCode.SUCCESS);
	}

	public static Result createSuccessResult(Object data, String message) {
		return createSuccessResult().setData(message).setMessage(message);
	}
	
	public static Result createResult(Integer code,String message,Object data){
		return create(code).setMessage(message).setData(data);
	}

	public boolean isSuccess() {
		return code != null && code.equals(ResultCode.SUCCESS);
	}

	public Object getData() {
		return data;
	}

	public Result setData(Object data) {
		this.data = data;
		return this;
	}

	public Integer getCode() {
		return code;
	}

	public Result setCode(Integer code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Result setMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public String toString() {
		return "Result [data=" + data + ", code=" + code + ", message=" + message + "]";
	}
	
}
