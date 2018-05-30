/**
 * 
 */
package com.pascloud.utils;

import com.pascloud.utils.EnumUtils;

/**
 * @author chenly 
 *
 * @version createtime:2016-7-12 上午11:26:13 
 */
public enum PasCloudCode {

	SUCCESS(10000,"操作成功"),
	
    ERROR(20001,"操作失败"),
    
    TIMEOUT(30001,"时间超时"),
    
    EXCEPTION(40001,"服务异常"),
    
    PARAMEXCEPTION(2002,"参数异常"),
    
    NULLDATA(2003,"数据为空"),
    
    NONEAUTH(50001,"该用户未授权或授权过期"),
	
	NOLOGINSTATUS(60001,"用户未登录");
	
	private Integer code;
	
	private String  desc;
	
	private PasCloudCode(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public static String getDesc(Integer code){
		for(PasCloudCode c:PasCloudCode.values()){
			if(c.getCode() == code){
				return c.getDesc();
			}
		}
		return null;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
