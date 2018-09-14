package com.pascloud.vo.pass;

public enum PasTypeEnum {

	OTHER(0,"pascloud_base"),
	PASPM(1, "pascloud_service_paspm"),
	DEMO(2, "pascloud_service_demo"),
	REDIS(3, "pascloud_redis"),
	MYCAT(4,"pascloud_mycat"),
	MQ(5,"pascloud_activemq"),
	TOMCAT(6, "pascloud_tomcat"), 
	ZK(7, "pascloud_zookeeper_admin"),
	MYSQL(8,"pascloud_mysql"),
	NGINX(9,"pascloud_ngnix");
	

	private String value;

	private Integer index;

	private PasTypeEnum(Integer index, String value) {
		this.value = value;
		this.index = index;
	}

	public static String getValue(Integer index) {
		for (PasTypeEnum p : PasTypeEnum.values()) {
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
