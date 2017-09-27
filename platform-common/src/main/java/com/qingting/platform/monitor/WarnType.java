package com.qingting.platform.monitor;

public enum WarnType {
	ONE_FILTER("one_filter_warn","第一级滤芯将到期",(byte)1),
	TWO_FILTER("two_filter_warn","第二级滤芯将到期",(byte)2),
	THREE_FILTER("three_filter_warn","第三级滤芯将到期",(byte)3),
	FOUR_FILTER("four_filter_warn","第四级滤芯将到期",(byte)4),
	FIVE_FILTER("five_filter_warn","第五级滤芯将到期",(byte)5),
	MICRO_EXCEED("micro_limit","微生物超标预警",(byte)6),
	EQUIP_LEAK("equip_leak","设备漏水异常",(byte)7),
	TDS_WAVE("TDS_WAVE","TDS突变异常",(byte)8),
	FLOW_UNCHANGE("flow_unchange","流量长时间未变异常",(byte)9);
	
	private String code;
	private String name;
	private Byte type;
	private WarnType(String code, String name, Byte type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	
}
