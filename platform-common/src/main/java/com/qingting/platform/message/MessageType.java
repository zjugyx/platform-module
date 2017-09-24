package com.qingting.platform.message;

public enum MessageType {
	/**
	 * 服务提醒
	 */
	SERVER_REMIND("server_remind","服务提醒",(byte)1),
	/**
	 * 评价提醒
	 */
	EVALUATE_REMIND("evaluate_remind","评价提醒",(byte)2),
	/**
	 * 申请关注
	 */
	APPLY_ATTENT("apply_attent","申请关注",(byte)3),
	/**
	 * 续费提醒
	 */
	RENEW_REMIND("renew_remind","续费提醒",(byte)4),
	/**
	 * 服务完成
	 */
	SERVER_FINISH("server_finish","服务完成",(byte)5),
	/**
	 * 设备绑定
	 */
	EQUIP_BIND("equip_bind","设备绑定",(byte)6),
	/**
	 * 设备预警
	 */
	EQUIP_WARN("equip_warn","设备预警",(byte)7);
	
	private String type;
	private String title;
	private Byte code;
	private MessageType(String type,String title,Byte code){
		this.type=type;
		this.title=title;
		this.code=code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}
}
