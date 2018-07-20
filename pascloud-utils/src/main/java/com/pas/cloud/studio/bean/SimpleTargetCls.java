package com.pas.cloud.studio.bean;

/**
 * 处理导入功能生成SQL时，同一个表被关联多次时，生成的表别名用同一个
 * 
 * @author zhouyan
 * 
 */
public class SimpleTargetCls {
	/**
	 * 表名
	 */
	private String tbName;
	/**
	 * 关联字段
	 */
	private String columnName;
	/**
	 * 模板列
	 */
	private String templateClName;

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTemplateClName() {
		return templateClName;
	}

	public void setTemplateClName(String templateClName) {
		this.templateClName = templateClName;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SimpleTargetCls)) {
			return false;
		}
		SimpleTargetCls s = (SimpleTargetCls) obj;
		return s.getTbName().equalsIgnoreCase(this.getTbName())
				&& s.getColumnName().equalsIgnoreCase(this.getColumnName())
				&& s.getTemplateClName().equalsIgnoreCase(this.getTemplateClName());
	}

	@Override
	public int hashCode() {
		return this.getColumnName().toLowerCase().hashCode() + this.getTbName().toLowerCase().hashCode()
				+ this.getTemplateClName().toLowerCase().hashCode();
	}

}
