package com.pascloud.module.mversion.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.utils.DBUtils;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.mversion.XtcdVo;

@Service
public class MVersionService extends AbstractBaseService {
	
	@Autowired
	private ConfigService    m_configService;
	
	public List<XtcdVo> getXtcdList(Connection conn,String id){
		List<XtcdVo> result = new ArrayList<>();
		String sql = "select * from xtb_xtcd";
		if(id.equals("dn0")){
			sql = "select * from xtb_xtzycd";
		}
		try {
			log.info(sql);
			QueryRunner qRunner = new QueryRunner();  
			result =  qRunner.query(conn,sql, new BeanListHandler<XtcdVo>(XtcdVo.class));
			Gson g = new Gson();
			System.out.println(g.toJson(result));
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

}
