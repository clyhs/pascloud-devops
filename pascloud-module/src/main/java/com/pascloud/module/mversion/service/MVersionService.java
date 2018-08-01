package com.pascloud.module.mversion.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pascloud.constant.Constants;
import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.mversion.XtcdVo;
import com.pascloud.vo.result.ResultCommon;

@Service
public class MVersionService extends AbstractBaseService {
	
	@Autowired
	private ConfigService    m_configService;
	
	private List<XtcdVo> getXtcdList(Connection conn,String id){
		List<XtcdVo> result = new ArrayList<>();
		String sql = "select * from xtb_xtcd";
		if(id.equals(Constants.PASCLOUD_PUBLIC_DB)){
			sql = "select * from xtb_xtzycd";
		}
		try {
			log.info(sql);
			QueryRunner qRunner = new QueryRunner();  
			result =  qRunner.query(conn,sql, new BeanListHandler<XtcdVo>(XtcdVo.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("查询表所有数据--失败--");
			log.error(e.getMessage());
			//e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	 
	private List<XtcdVo> getXtcdList(Connection conn,String selectId,String ids){
		List<XtcdVo> result = new ArrayList<>();
		String sql = "select * from xtb_xtcd where xmdh in ("+ids+")";
		if(selectId.equals(Constants.PASCLOUD_PUBLIC_DB)){
			sql = "select * from xtb_xtzycd where xmdh in ("+ids+")";
		}
		try {
			log.info("查询菜单资源");
			log.info(sql);
			QueryRunner qRunner = new QueryRunner();  
			result =  qRunner.query(conn,sql, new BeanListHandler<XtcdVo>(XtcdVo.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("查询表所有数据--失败--");
			log.error(e.getMessage());
			//e.printStackTrace();
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return result;
	}
	
	public List<XtcdVo> getXtcdById(String Id){
		List<DBInfo> dbs = new ArrayList<>();
        List<XtcdVo> cds = new ArrayList<>();
        
        log.info("查询Id="+Id+"的功能菜单");
        
		dbs = m_configService.getDBFromConfig();
		DBInfo db = null;
		if(dbs.size() > 0){
			for(DBInfo dbf:dbs){
				//log.info(dbf.getId());
				if(dbf.getId().equals(Id)){
					db =dbf;
				}
			}
		}
		
		if(null!=db){
			log.info("进入查询");
			Connection conn = null;
			String driverClass = DBUtils.getDirverClassName(db.getDbType());
			DBUtils dbUtils = new DBUtils(driverClass, db.getUrl(), db.getUsername(), db.getPassword());
			conn = dbUtils.getConnection();
			cds =getXtcdList(conn,Id);
		}else{
			log.info("db为空");
		}
		
		return cds;
	}
	
	private Connection getConnectionById(String Id){
		List<DBInfo> dbs = new ArrayList<>();
		Connection conn = null;
		dbs = m_configService.getDBFromConfig();
		DBInfo db = null;
		if(dbs.size() > 0){
			for(DBInfo dbf:dbs){
				//log.info(dbf.getId());
				if(dbf.getId().equals(Id)){
					db =dbf;
				}
			}
		}
		if(null!=db){
			String driverClass = DBUtils.getDirverClassName(db.getDbType());
			if(Id.equals(Constants.PASCLOUD_PUBLIC_DB)){
				db.setUrl(db.getUrl()+"?useUnicode=true&characterEncoding=utf8");
			}
			DBUtils dbUtils = new DBUtils(driverClass, db.getUrl(), db.getUsername(), db.getPassword());
			conn = dbUtils.getConnection();
		}else{
			log.info("db为空");
		}
		return conn;
	}
	
	public ResultCommon addXtcd(XtcdVo cd,String Id){
		ResultCommon result = null;
		Connection conn = null;
		String sql = "";
		Integer row = 0;
		try {
			conn = getConnectionById(Id);
			QueryRunner qRunner = new QueryRunner(); 
			Integer xmdh = getMaxXmdh(conn);
			if(Id.equals(Constants.PASCLOUD_PUBLIC_DB)){
				sql = "INSERT INTO xtb_xtzycd(xmdh,xmmc,xmdz,sjxm,cdjb,dzlx,classid,sfxs,imgurl,qxbs,version)"
						+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
				Object[] params = {xmdh,cd.getXmmc(),cd.getXmdz(),cd.getSjxm(),cd.getCdjb(),
						cd.getDzlx(),cd.getClassid(),cd.getSfxs(),cd.getImgurl(),cd.getQxbs(),cd.getVersion()};
				row = qRunner.update(conn, sql, params);
			}else{
				sql = "INSERT INTO xtb_xtcd(xmdh,xmmc,xmdz,sjxm,cdjb,dzlx,classid,sfxs,imgurl,qxbs,version)"
						+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
				
				Object[] params = {xmdh,cd.getXmmc(),cd.getXmdz(),cd.getSjxm(),cd.getCdjb(),
						cd.getDzlx(),cd.getClassid(),cd.getSfxs(),cd.getImgurl(),cd.getQxbs(),cd.getVersion()};
				row = qRunner.update(conn, sql, params);
			}
			
			log.info(sql);

			if(row>0){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			log.error(e.getMessage());
			//e.printStackTrace();
			result = new ResultCommon(PasCloudCode.EXCEPTION.getCode(),e.getMessage());
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		return result;
	}
	
	//生成id,提供插入ORACLE数据库
	private Integer getMaxXmdh(Connection conn) throws SQLException{
		Integer res = 0;
		Number num = null;
		String sql = "update mycat_sequence set current_value = current_value+increment where name='xtb_xtcd' ";
		QueryRunner qRunner = new QueryRunner(); 
		//Number num =  (Number)qRunner.query(conn,sql, new ScalarHandler());
		
		Integer row = qRunner.update(conn, sql);
		
		if(null!=row && row>0){
			String mSql = "select current_value as count from  mycat_sequence where name='xtb_xtcd' ";
			num =  (Number)qRunner.query(conn,mSql, new ScalarHandler());
		}
		
		if(null!=num){
			res = num.intValue();
		}
		
		return res;
	}
	
	public ResultCommon addXtcd(XtcdVo cd,String[] Ids){
		ResultCommon result = null;
		Connection conn = null;
		String sql = "";
		Integer row = 0;
		try {
			QueryRunner qRunner = new QueryRunner();  
			for(String Id:Ids){
				conn = getConnectionById(Id);
				if(Id.equals(Constants.PASCLOUD_PUBLIC_DB)){
					sql = "INSERT INTO xtb_xtzycd(xmdh,xmmc,xmdz,sjxm,cdjb,dzlx,classid,sfxs,imgurl,qxbs,version)"
							+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
					Object[] params = {cd.getXmdh(),cd.getXmmc(),cd.getXmdz(),cd.getSjxm(),cd.getCdjb(),
							cd.getDzlx(),cd.getClassid(),cd.getSfxs(),cd.getImgurl(),cd.getQxbs(),cd.getVersion()};
					row += qRunner.update(conn, sql, params);
					conn.close();
					conn = null;
				}else{
					sql = "INSERT INTO xtb_xtcd(xmdh,xmmc,xmdz,sjxm,cdjb,dzlx,classid,sfxs,imgurl,qxbs,version)"
							+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
					Object[] params = {cd.getXmdh(),cd.getXmmc(),cd.getXmdz(),cd.getSjxm(),cd.getCdjb(),
							cd.getDzlx(),cd.getClassid(),cd.getSfxs(),cd.getImgurl(),cd.getQxbs(),cd.getVersion()};
					row += qRunner.update(conn, sql, params);
					conn.close();
					conn = null;
				}
				log.info(sql);
			}
			
			if(row>0){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			log.error(e.getMessage());
			//e.printStackTrace();
			result = new ResultCommon(PasCloudCode.EXCEPTION.getCode(),e.getMessage());
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public ResultCommon delXtcd(XtcdVo cd,String Id){
		ResultCommon result = null;
		Connection conn = null;
		String sql = "";
		Integer row = 0;
		try {
			if(Id.equals(Constants.PASCLOUD_PUBLIC_DB)){
				sql = "delete from xtb_xtzycd where xmdh=?";
			}else{
				sql = "delete from xtb_xtcd where xmdh=?";
			}
			conn = getConnectionById(Id);
			log.info(sql);

			Object[] params = {cd.getXmdh()};
			QueryRunner qRunner = new QueryRunner();  

			row = qRunner.update(conn, sql, params);
			if(row>0){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			log.error(e.getMessage());
			//e.printStackTrace();
			result = new ResultCommon(PasCloudCode.EXCEPTION.getCode(),e.getMessage());
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return result;
	}
	
	public ResultCommon changeXtcdStatus(XtcdVo cd,String Id){
		ResultCommon result = null;
		Connection conn = null;
		String sql = "";
		Integer row = 0;
		try {
			if(Id.equals(Constants.PASCLOUD_PUBLIC_DB)){
				sql = "update xtb_xtzycd set sfxs=? where xmdh=?";
			}else{
				sql = "update xtb_xtcd set sfxs=? where xmdh=?";
			}
			conn = getConnectionById(Id);
			log.info(sql);

			Object[] params = {cd.getSfxs(),cd.getXmdh()};
			QueryRunner qRunner = new QueryRunner();  

			row = qRunner.update(conn, sql, params);
			if(row>0){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			//e.printStackTrace();
			result = new ResultCommon(PasCloudCode.EXCEPTION.getCode(),e.getMessage());
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return result;
	}
	
	public ResultCommon sysAllXtcdToTenant(String[] Ids){
		ResultCommon result =null;
		List<XtcdVo> cds = new ArrayList<>();
		Connection conn = null;
		Integer row=0;
		try {
			conn = getConnectionById(Constants.PASCLOUD_PUBLIC_DB);
			log.info("获取公共菜单资源");
			cds = getXtcdList(conn,Constants.PASCLOUD_PUBLIC_DB);
			String sql = "INSERT INTO xtb_xtcd(xmdh,xmmc,xmdz,sjxm,cdjb,dzlx,classid,sfxs,imgurl,qxbs,version)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			
			conn = null;
			log.info(sql);
			QueryRunner qRunner = new QueryRunner();  
			if(cds.size()>0){
				for(String Id:Ids){
					conn = getConnectionById(Id);
					result = truncateXtcd(conn);
					if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
						Object[][] params = new Object[cds.size()][];
						for(int i=0;i<cds.size();i++){
							XtcdVo cd = cds.get(i);
							params[i] = new Object[]{cd.getXmdh(),cd.getXmmc(),cd.getXmdz(),cd.getSjxm(),cd.getCdjb(),
									cd.getDzlx(),cd.getClassid(),cd.getSfxs(),cd.getImgurl(),cd.getQxbs(),cd.getVersion()};
						}
						log.info("同步公共菜单资源到租户："+Id);
						qRunner.batch(conn, sql, params);
						conn.close();
						conn = null;
						row++;
					}
				}
			}
			if(row>0){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			//e.printStackTrace();
			result = new ResultCommon(PasCloudCode.EXCEPTION);
		}finally{
		}
		
		return result;
	}
	
	
	public ResultCommon sysOtherXtcdToTenant(String selectId,String dnId,String idList){
		ResultCommon result =null;
		List<XtcdVo> cds = new ArrayList<>();
		Connection selectConn = null;
		Connection dnConn = null;
		Integer row=0;
		try {
			selectConn = getConnectionById(selectId);
			dnConn     = getConnectionById(dnId);
			log.info("获取菜单资源");
			cds = getXtcdList(selectConn,selectId,idList);
			String sql = "INSERT INTO xtb_xtcd(xmdh,xmmc,xmdz,sjxm,cdjb,dzlx,classid,sfxs,imgurl,qxbs,version)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			log.info(sql);
			QueryRunner qRunner = new QueryRunner();  
			if(cds.size()>0){
				result = truncateXtcd(dnConn);
				if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
					Object[][] params = new Object[cds.size()][];
					for(int i=0;i<cds.size();i++){
						XtcdVo cd = cds.get(i);
						params[i] = new Object[]{cd.getXmdh(),cd.getXmmc(),cd.getXmdz(),cd.getSjxm(),cd.getCdjb(),
								cd.getDzlx(),cd.getClassid(),cd.getSfxs(),cd.getImgurl(),cd.getQxbs(),cd.getVersion()};
					}
					log.info("同步菜单资源到租户："+dnId);
					qRunner.batch(dnConn, sql, params);
					row = 1;
				}
				
			}
			if(row>0){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			//e.printStackTrace();
			result = new ResultCommon(PasCloudCode.EXCEPTION);
		}finally{
			try {
				if(null!=dnConn){
					dnConn.close();
					dnConn = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	private ResultCommon truncateXtcd(Connection conn){
		ResultCommon result = null;
		Integer row = 0;
		try {
			log.info("清空菜单资源表");
			String sql = "truncate table xtb_xtcd";
			
			log.info(sql);
			QueryRunner qRunner = new QueryRunner();  
			//vo = qRunner.query(conn, sql, params,new BeanHandler<XtcdVo>(XtcdVo.class));
			row = qRunner.update(conn, sql);
			result = new ResultCommon(PasCloudCode.SUCCESS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			//e.printStackTrace();
			result = new ResultCommon(PasCloudCode.ERROR);
		}finally{
		}
		return result;
	}
	
	
	public XtcdVo getXtcdVoByXmdh(XtcdVo cd,String Id){
		Connection conn = null;
		String sql = "";
		XtcdVo vo  = null;
		try {
			if(Id.equals(Constants.PASCLOUD_PUBLIC_DB)){
				sql = "select * from xtb_xtzycd where xmdh=?";
			}else{
				sql = "select * from xtb_xtcd where xmdh=?";
			}
			conn = getConnectionById(Id);
			log.info(sql);

			Object[] params = {cd.getXmdh()};
			QueryRunner qRunner = new QueryRunner();  

			//vo = qRunner.query(conn, sql, params,new BeanHandler<XtcdVo>(XtcdVo.class));
			vo = qRunner.query(conn, sql, new BeanHandler<XtcdVo>(XtcdVo.class), params);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			//e.printStackTrace();
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return vo;
	}
	
	

}
