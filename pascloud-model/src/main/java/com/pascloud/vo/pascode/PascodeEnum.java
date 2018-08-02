package com.pascloud.vo.pascode;



public enum PascodeEnum {

	TGZ(0,".tar.gz"),
	WAR(1, ".war");
	
	private String value;

	private Integer index;

	private PascodeEnum(Integer index, String value) {
		this.value = value;
		this.index = index;
	}

	public static String getValue(Integer index) {
		for (PascodeEnum p : PascodeEnum.values()) {
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
