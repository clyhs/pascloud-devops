package com.pas.cloud.studio.parameters;

/**
 * 封装各种类型功能的的一个接口，提供了访问序列化类的相关方法
 * @author luoxt
 *
 */
public interface Parameter {
	
	static final long serialVersionUID = 7302712038059549758L;
	
	/**
	 * 返回功能id号
	 * @return 如 hyyjcx
	 */
	public String getFunId();
	/**
	 * 返回功能的名称
	 * @return 如 行员业绩查询
	 */
	public String getFunName();
	/**
	 * 返回功能类型
	 * @return 包括以下几种类型query、import、admin
	 */
	public String getFunType();
	
	/**
	 * 返回版本号
	 * @return
	 */
	public String getVersion();
	
	/**
	 * 返回根节点ID
	 * @return
	 */
	public String getPid();
	
	public String getDesc();
	/**
	 * 返回该功能所有参数的json格式参数描述，用于前端编辑功能时重新描绘页面、初始化参数
	 * @return
	 */
	public String toJsonString();
}
