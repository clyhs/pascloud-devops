/**
 * 
 */
package com.pascloud.vo.result;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.pascloud.utils.PasCloudCode;


/**
 * @author chenly 
 *
 * @version createtime:2016-7-12 下午12:05:20 
 */
public class ResultListData<T > extends ResultCommon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<T> rows = new ArrayList<T>();
	
	public ResultListData(){}
	
	public ResultListData(PasCloudCode c){
		super(c);
	}
	
	public ResultListData(Integer code,String desc){
		super(code,desc);
	}

	@XmlElementWrapper(name = "rows")
	@XmlElement(name="row")
	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
