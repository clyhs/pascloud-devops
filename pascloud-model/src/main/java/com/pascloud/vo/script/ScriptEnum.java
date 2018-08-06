package com.pascloud.vo.script;



public enum ScriptEnum {

	ORA(0, "oracle"),
	DB2(1, "db2");
	
	private String value;

	private Integer index;

	private ScriptEnum(Integer index, String value) {
		this.value = value;
		this.index = index;
	}

	public static String getValue(Integer index) {
		for (ScriptEnum p : ScriptEnum.values()) {
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
