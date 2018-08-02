package com.pascloud.module.pascode.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pascloud.constant.Constants;
import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.database.service.DataBaseService;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.vo.mversion.XtcdVo;
import com.pascloud.vo.pascode.PascodeEnum;
import com.pascloud.vo.pascode.PascodeVo;
import com.pascloud.vo.pasdev.PasfileVo;
import com.pascloud.vo.pass.PasTypeEnum;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.result.ResultPageVo;

@Service
public class PascodeService extends AbstractBaseService {
	
	@Autowired
	private DataBaseService    m_databaseService;
	
	@Autowired
	private PasService         m_pasService;
	
	@Autowired
	private PasCloudConfig     m_config;
	
	public ResultPageVo<PascodeVo> getPageData(Integer pageNo,Integer pageSize){
		ResultPageVo<PascodeVo> result = null;
		List<PascodeVo> list = new ArrayList<>();
		Connection conn = null;
		Integer start = 0;
		Integer totals = 0;
		try{
			conn = m_databaseService.getConnectionById(Constants.PASCLOUD_PUBLIC_DB);
			QueryRunner qRunner = new QueryRunner(); 
			start = (pageNo - 1) * pageSize;
			String sql = "select * from xtb_pascode order by id desc limit ?,?";
			String tSql= "select count(1) from xtb_pascode ";
			
			Object[] tparams = {};
			Object[] params = {start,pageSize};
			
			Number num =  (Number)qRunner.query(conn,tSql, new ScalarHandler(),tparams);
			if(null!=num){
				totals = num.intValue();
				if(totals>0){
					list =  qRunner.query(conn,sql, new BeanListHandler<PascodeVo>(PascodeVo.class),params);
				}
			}
			result = new ResultPageVo<PascodeVo>(PasCloudCode.SUCCESS);
			result.setTotal(totals);
			result.setRows(list);
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			result = new ResultPageVo<PascodeVo>(PasCloudCode.EXCEPTION);
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
	
	public ResultPageVo<PascodeVo> getPageData(String name,Integer pageNo,Integer pageSize){
		ResultPageVo<PascodeVo> result = null;
		List<PascodeVo> list = new ArrayList<>();
		Connection conn = null;
		Integer start = 0;
		Integer totals = 0;
		try{
			conn = m_databaseService.getConnectionById(Constants.PASCLOUD_PUBLIC_DB);
			QueryRunner qRunner = new QueryRunner(); 
			start = (pageNo - 1) * pageSize;
			String sql = "select * from xtb_pascode where name like ? order by id desc limit ?,?";
			String tSql= "select count(1) from xtb_pascode where name like ?  ";
			
			Object[] tparams = {"%"+name+"%"};
			Object[] params = {"%"+name+"%",start,pageSize};
			
			Number num =  (Number)qRunner.query(conn,tSql, new ScalarHandler(),tparams);
			if(null!=num){
				totals = num.intValue();
				if(totals>0){
					list =  qRunner.query(conn,sql, new BeanListHandler<PascodeVo>(PascodeVo.class),params);
				}
			}
			result = new ResultPageVo<PascodeVo>(PasCloudCode.SUCCESS);
			result.setTotal(totals);
			result.setRows(list);
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			result = new ResultPageVo<PascodeVo>(PasCloudCode.EXCEPTION);
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
	
	public ResultCommon uploadPascode(CommonsMultipartFile file){
        ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		String filepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_PASCODE();
  
		FileUtils.createOrExistsDir(filepath);
		PascodeVo vo = null;
		String filename = "";
		String fname =  "";
		String newfilename = "";
		String newfname = "";
		String suffix = "";
		Integer type = 0;
		if (file != null && !file.isEmpty()) {
			log.info(file.getOriginalFilename());
			InputStream input = null;
			BufferedOutputStream bos = null;
			long size = 0;
			try {
				
				//pas-cloud-service-demo-1.0.1-SNAPSHOT-assembly.tar.gz
				//root.war
				filename = file.getOriginalFilename();
				//testhydr
				size = file.getSize();
				
				if(filename.contains(PascodeEnum.TGZ.getValue())){
					suffix = PascodeEnum.TGZ.getValue();
				}else if(filename.contains(PascodeEnum.WAR.getValue())){
					suffix = PascodeEnum.WAR.getValue();
				}
				
				if(filename.contains(Constants.PASCLOUD_SERVICE_DEMO)){
					type = PasTypeEnum.DEMO.getIndex();
				}else if(filename.contains(Constants.PASCLOUD_SERVICE_PASPM)){
					type = PasTypeEnum.PASPM.getIndex();
				}else{
					if(suffix.equals(PascodeEnum.WAR.getValue())){
						type = PasTypeEnum.TOMCAT.getIndex();
					}
				}
				
				fname = filename.replace(suffix, "");
				Date now = new Date();
				log.info(fname);
				//xxx/upload/pasfile/dn0/testhydr.zip
				newfname = fname + "-" + getRandomFileName(now);
				newfilename = newfname + suffix;
				//filepath = destpath+"/"+filename;
				filepath = filepath+"/"+newfilename;
				log.info(filepath);
				
				File destfile = new File(filepath);
				file.transferTo(destfile);
				
				vo = new PascodeVo();
				vo.setName(newfilename);
				vo.setCreateTime(now);
				vo.setType(type);
				vo.setSize(size);
				result = addPascode(vo);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.error(e.getMessage());
				result = new ResultCommon(PasCloudCode.EXCEPTION);
			}finally{
			}
		}
		return result;
	}
	
	public ResultCommon addPascode(PascodeVo vo){
		ResultCommon result = null;
		Connection conn = null;
		String sql = "";
		Integer row = 0;
		try {
			conn = m_databaseService.getConnectionById(Constants.PASCLOUD_PUBLIC_DB);
			QueryRunner qRunner = new QueryRunner(); 
			
			sql = "INSERT INTO xtb_pascode(name,type,createTime,size)"
						+ "VALUES(?,?,?,?)";
			Object[] params = {vo.getName(),vo.getType(),vo.getCreateTime(),vo.getSize()};
			row = qRunner.update(conn, sql, params);
			
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
					conn = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		return result;
	}

	
	public ResultCommon deletePascode(PascodeVo vo){
		ResultCommon result = null;
		Connection conn = null;
		String sql = "";
		Integer row = 0;
		String filepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_PASCODE();
		try {
			conn = m_databaseService.getConnectionById(Constants.PASCLOUD_PUBLIC_DB);
			QueryRunner qRunner = new QueryRunner(); 
			
			sql = "delete from xtb_pascode where "
						+ "id=? ";
			Object[] params = {vo.getId()};
			row = qRunner.update(conn, sql, params);
			
			log.info(sql);

			if(row>0){
				
				filepath = filepath + "/" +vo.getName();
				FileUtils.deleteFile(filepath);
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
					conn = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public ResultCommon uploadPascodeAndRestart(PascodeVo vo){
		ResultCommon result = null;
		
		return result;
	}
	
	private ResultCommon uploadServiceToServer(PascodeVo vo){
        ResultCommon result = null;
        log.info("上传到/home/pascloud/pas-cloud-service-demo 或者 pas-cloud-service-paspm");
        
        log.info("更新配置文件");
        
        log.info("停止服务，重启服务");
        
		
		return result;
	}
	
	private ResultCommon uploadWarToServer(PascodeVo vo){
        ResultCommon result = null;
        
        log.info("上传到/home/pascloud/tomcat");
        
        log.info("更新配置文件");
        
        log.info("停止服务，重启服务");
		
		return result;
	}
}
