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
import org.springframework.transaction.annotation.Transactional;

import com.pascloud.bean.system.User;
import com.pascloud.mapper.system.UserMapper;
import com.pascloud.mybatis.multi.DataSource;
import com.pascloud.mybatis.multi.DataSourceHolder;
import com.pascloud.mapper.system.User2Mapper;



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
	
//	@Autowired
//	private User2Mapper user2Mapper;
//	
//	@Autowired
//	private SqlSession sqlSession;
	
	
	//@DataSource(name="dataSource_dn2")
	@Transactional
	public int insert(User t,String db) {
		// TODO Auto-generated method stub
		
		DataSourceHolder.setDataSource(db);
		
		//log.info("now datasource:"+DataSourceHolder.getDataSource());
		//userMapper.insert_test(t);
//		DataSourceHolder.clearDataSource();
		userMapper.insert(t);
		int i = 5/0;
		//return userMapper.insert(t);
		//return userMapper.insertSelective(t);
		return 1;
	}
	
	//@DataSource(name="dn1")
	public List<User> selectall(){
		//return user2Mapper.select_test(null);
		DataSourceHolder.setDataSource("dn1");
		return userMapper.select_test(null);
	}
	
	//@DataSource(name="dn2")
	public List<User> selectall2(){
		DataSourceHolder.setDataSource("dn2");
		return userMapper.select_test(null);
		//return sqlSession.selectList("com.pascloud.mapper.system.UserMapper.select_test");
	}
	
	
	public List<User> selecttest(Map map){
		return userMapper.select_test(map);
	}
}
