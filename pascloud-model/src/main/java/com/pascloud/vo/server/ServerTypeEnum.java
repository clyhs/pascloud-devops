package com.pascloud.vo.server;


public enum ServerTypeEnum {
	
	APP(1, "app_server"),
	DB(2, "db_server");
	
	private String value;

	private Integer index;

	private ServerTypeEnum(Integer index, String value) {
		this.value = value;
		this.index = index;
	}

	public static String getValue(Integer index) {
		for (ServerTypeEnum p : ServerTypeEnum.values()) {
			if (p.getIndex() == index) {
				return p.value;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
