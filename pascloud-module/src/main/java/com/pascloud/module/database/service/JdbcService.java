package com.pascloud.module.database.service;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pascloud.bean.tenant.KhdxHyVo;

@Service
public class JdbcService<T> {
	
	
	public List<T> getDataList(Connection conn,String sql){
		List<T> lists = new ArrayList<>();
		
		try {
			//String sql = "select * from khdx_hy";
			QueryRunner qRunner = new QueryRunner();  
			lists =  qRunner.query(conn,sql, new BeanListHandler(getTClass()));
			Gson g = new Gson();
			//System.out.println(g.toJson(hys));
			System.out.println(lists.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return lists;
	}

	
	private Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }
}
