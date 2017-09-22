package com.qingting.platform.message;

public enum MessageType {
	/**
	 * 服务提醒
	 */
	SERVER_REMIND("server_remind","服务提醒"),
	/**
	 * 评价提醒
	 */
	EVALUATE_REMIND("evaluate_remind","评价提醒"),
	/**
	 * 申请关注
	 */
	APPLY_ATTENT("apply_attent","申请关注"),
	/**
	 * 续费提醒
	 */
	RENEW_REMIND("renew_remind","续费提醒"),
	/**
	 * 服务完成
	 */
	SERVER_FINISH("server_finish","服务完成"),
	/**
	 * 设备绑定
	 */
	EQUIP_BIND("equip_bind","设备绑定"),
	/**
	 * 设备预警
	 */
	EQUIP_WARN("equip_warn","设备预警");
	
	private String type;
	private String title;
	
	private MessageType(String type,String title){
		this.type=type;
		this.title=title;
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
}
