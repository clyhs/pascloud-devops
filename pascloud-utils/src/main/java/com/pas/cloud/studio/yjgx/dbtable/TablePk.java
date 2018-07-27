package com.pas.cloud.studio.yjgx.dbtable;

import java.util.HashMap;
import java.util.Map;

/**
 * 表主键
 * 
 * @author NongFei
 * 
 */
public class TablePk {
	private String tabname;// 表名

	private String idx; // 主键

	public String getIdx() {
		return idx;
	}

	public String getTabname() {
		return tabname;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public void setTabname(String tabname) {
		this.tabname = tabname;
	}
	
	public Map getColsMap(){
		String[] idxArr = this.idx.split("\\+");
		int len = idxArr.length;
		Map map = new HashMap();
		for(int i=0;i<len;i++){
			map.put(idxArr[i], idxArr[i]);
		}
		return map;
	}

}
