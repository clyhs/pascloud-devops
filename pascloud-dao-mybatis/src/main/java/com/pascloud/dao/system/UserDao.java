/**
 * 
 */
package com.pascloud.dao.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.pascloud.bean.system.User;
import com.pascloud.mapper.system.UserMapper;



/**
 * @author chenly 
 *
 * @version createtime:2016-8-8 下午4:05:46 
 */
@Repository
public class UserDao {
	
	private static final Logger log = Logger.getLogger(UserDao.class);

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(User t) {
		// TODO Auto-generated method stub	
		//return userMapper.insert_test(t);
		return 1;
	}
	
	
	public List<User> selectall(){
		return userMapper.selectAll();
	}
	
	public List<User> selectall2(){
		
		return sqlSession.selectList("com.pascloud.mapper.system.UserMapper.select_test");
	}
	
	
	public List<User> selecttest(Map map){
		return userMapper.select_test(map);
	}
}
