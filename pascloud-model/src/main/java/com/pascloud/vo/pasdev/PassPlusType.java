package com.pascloud.vo.pasdev;

import com.pascloud.utils.EnumWithValue;
import com.pascloud.vo.pascode.PascodeEnum;

public enum PassPlusType {
	Query(1, "query"), // 查询功能
	Import(2, "import"), // 导入功能
	Manage(3, "manage"), // 管理功能
	Yjgx(4, "yjgx"), // 业绩关系
	;

	public int index;
	public String value;

	PassPlusType(int index, String value) {
		this.value = value;
		this.index = index;
	}
	
	public static String getValue(Integer index) {
		for (PassPlusType p : PassPlusType.values()) {
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