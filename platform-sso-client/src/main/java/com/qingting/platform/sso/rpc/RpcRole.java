package com.qingting.platform.sso.rpc;


public class RpcRole {
	
	/** ID */
	private Integer id;
	
	/** 名称 */
	private String name;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** 以下为显示辅助参数 */
	/**
	 * 是否分配了该角色
	 */
	private Boolean isChecked = Boolean.valueOf(false);
	
	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
}
