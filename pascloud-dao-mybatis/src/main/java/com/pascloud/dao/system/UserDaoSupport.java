/**
 * 
 */
package com.pascloud.dao.system;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.pascloud.bean.system.User;
import com.pascloud.utils.SpringUtil;

/**
 * @date Jul 28, 2019 10:09:49 PM
 *
 * @author 大鱼
 *
 */
//@Repository
//public class UserDaoSupport extends SqlSessionDaoSupport {
//	
//	@Resource
//    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
//        super.setSqlSessionFactory(sqlSessionFactory);
//	}
//	@Autowired
//	private SpringUtil springUtil;
//	
//	public List<User> selectAll(String db){
//		setSqlSessionFactory(db);
//		return this.getSqlSession().selectList("com.pascloud.mapper.system.UserMapper.select_test");
//	}
//	
//	//@Transactional(value="transactionManager2",readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
//	 
//	public String insert(String db)throws Exception {
//		
//		//this.getSqlSession().commit();
//		DataSourceTransactionManager txManager = getTxManager(db);
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = txManager.getTransaction(def);
//		try {
//			
//			setSqlSessionFactory(db);
//			User u = new User();
//			u.setName("cccc");
//			this.getSqlSession().insert("com.pascloud.mapper.system.UserMapper.insert_test",u);
//			int i = 5/0;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			txManager.rollback(status);
//			throw new Exception("rollback");
//		}
//		txManager.commit(status);
//		return "success";
//	}
////	
////	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
////	// explicitly setting the transaction name is something that can be done only programmatically
////	def.setName("SomeTxName");
////	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
////
////	TransactionStatus status = txManager.getTransaction(def);
////	try {
////	    // execute your business logic here
////	}
////	catch (MyException ex) {
////	    txManager.rollback(status);
////	    throw ex;
////	}
////	txManager.commit(status);
//	
//	public void setSqlSessionFactory(String db) {
//		SqlSessionFactory sqlSessionFactory= null;
//		if("db1".equals(db)) {
//			sqlSessionFactory= (SqlSessionFactory) springUtil.getBean("sqlSessionFactory");
//		}else if("db2".equals(db)) {
//			sqlSessionFactory= (SqlSessionFactory) springUtil.getBean("sqlSessionFactory2");
//		}else{
//			sqlSessionFactory= (SqlSessionFactory) springUtil.getBean("sqlSessionFactory3");
//			
//		}
//		super.setSqlSessionFactory(sqlSessionFactory);
//	}
//	
//	public DataSourceTransactionManager getTxManager(String db) {
//		DataSourceTransactionManager txManager = null;
//		if("db1".equals(db)) {
//			txManager=  (DataSourceTransactionManager) springUtil.getBean("transactionManager");
//		}else if("db2".equals(db)) {
//			txManager=  (DataSourceTransactionManager) springUtil.getBean("transactionManager2");
//		}else {
//			txManager=  (DataSourceTransactionManager) springUtil.getBean("transactionManager3");
//		}
//		return txManager;
//	}
//
//}
