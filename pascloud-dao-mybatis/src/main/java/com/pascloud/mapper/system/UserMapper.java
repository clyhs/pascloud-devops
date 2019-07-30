/**
 * 
 */
package com.pascloud.mapper.system;

import java.util.List;
import java.util.Map;

import com.pascloud.bean.system.User;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author chenly 
 *
 * @version createtime:2016-8-8 下午4:04:56 
 */

public interface UserMapper  extends Mapper<User> {
	

	public List<User> select_test(Map map);
	
	public int insert_test(User t);
}
