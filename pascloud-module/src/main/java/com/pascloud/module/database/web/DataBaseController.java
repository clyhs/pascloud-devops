package com.pascloud.module.database.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.database.service.DataBaseService;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.db.DataSourceUtils;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.database.DBColumnVo;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.database.DBTableVo;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.result.ResultPageVo;
import com.thoughtworks.xstream.XStream;

@Controller
@RequestMapping("module/database")
public class DataBaseController extends BaseController {
	
	@Autowired
	private PasCloudConfig  m_config;
	@Autowired
	private DataBaseService m_dbService;
	
	@RequestMapping("index.html")
	public String index(){
		return "database/index";
	}
	
	@RequestMapping("table.html")
	public ModelAndView table(HttpServletRequest request,
			@RequestParam(value="tablename",defaultValue="",required=true) String tablename,
			@RequestParam(value="dsId",defaultValue="",required=true) String dsId){
		
		List<DBColumnVo> columns = new ArrayList<>();
		columns = m_dbService.getColumns(tablename,dsId);
		ModelAndView view = new ModelAndView("database/table");
		String url = "/module/database/datas.json?tablename="+tablename+"&dsId="+dsId;
		Gson g = new Gson();
		log.info(g.toJson(columns));
		view.addObject("columns", columns);
		view.addObject("url", url);
		return view;
	}
	
	@RequestMapping("dbTrees.json")
	@ResponseBody
	public List<TreeVo> getDataBaseList(HttpServletRequest request){
		List<TreeVo> trees = new ArrayList<>();
		ConcurrentHashMap<String,ComboPooledDataSource> dataSourceMap = DataSourceUtils.getDataSources();
		Iterator it = dataSourceMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			ComboPooledDataSource ds = (ComboPooledDataSource) entry.getValue();
			TreeVo tree = new TreeVo();
			tree.setText(key);
			tree.setId(key);
			tree.setLeaf(true);
			tree.setIconCls("icon-database");
			trees.add(tree);
		}
		
		
		return trees;
	}
	
	@RequestMapping("tableTrees.json")
	@ResponseBody
	public List<TreeVo> getTableList(HttpServletRequest request,
			@RequestParam(value="dsId",defaultValue="",required=true) String dsId){
		List<TreeVo> trees = new ArrayList<>();
		List<DBTableVo> tables = new ArrayList<>();
		log.info(dsId);
		tables = m_dbService.getTables(dsId);
		for(DBTableVo vo : tables){
			TreeVo tree = new TreeVo();
			tree.setText(vo.getName());
			tree.setId(vo.getId());
			tree.setLeaf(true);
			tree.setUrl("/module/database/table.html?tablename="+vo.getName()+"&dsId="+dsId);
			tree.setIconCls("icon-table");
			trees.add(tree);
		}
		return trees;
	}
	@RequestMapping("datas.json")
	@ResponseBody
	public ResultPageVo/*List<Map<String, Object>>*/ getDataList(HttpServletRequest request,
			@RequestParam(value="tablename",defaultValue="",required=true) String tablename,
			@RequestParam(value="dsId",defaultValue="",required=true) String dsId){
		ResultPageVo result = new ResultPageVo(10000,"success");
		List<Map<String, Object>> list = new ArrayList<>();
		Integer page = (null == request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page"));
		Integer pageSize = (null == request.getParameter("rows"))?20:Integer.parseInt(request.getParameter("rows"));
		Integer startRow = (page -1)*pageSize;
		list = m_dbService.getDataList(tablename,dsId,startRow,pageSize);
		Integer total = -1;
		total = m_dbService.getDataCounts(tablename, dsId);
		result.setRows(list);
		result.setTotal(total);
		Gson g = new Gson();
		log.info(g.toJson(result));
		return result;
	}
	
	@RequestMapping("execSql.json")
	@ResponseBody
	public ResultCommon executeSql(HttpServletRequest request,
			@RequestParam(value="dsId",defaultValue="",required=true) String dsId,
			@RequestParam(value="sql",defaultValue="",required=true) String sql){
		ResultCommon result = new ResultCommon(10000,"成功");
		
		String desc = m_dbService.execBySql(dsId, sql);
		result.setDesc(desc);
		
		return result;
	}
	
	
	@RequestMapping("execHeader.json")
	@ResponseBody
	public List<String> execHeaderAction(HttpServletRequest request,
			@RequestParam(value="dsId",defaultValue="",required=true) String dsId,
			@RequestParam(value="sql",defaultValue="",required=true) String sql){
		ResultPageVo result = new ResultPageVo(10000,"success");
		log.info(sql);
		List<String> headers = new ArrayList<>();
		headers = m_dbService.getSqlColumnName(dsId, sql);
		return headers;
		
	}
	
	@RequestMapping("exec.json")
	@ResponseBody
	public ResultPageVo execAction(HttpServletRequest request,
			@RequestParam(value="dsId",defaultValue="",required=true) String dsId,
			@RequestParam(value="sql",defaultValue="",required=true) String sql){
		ResultPageVo result = new ResultPageVo(10000,"success");
		log.info(sql);
		Integer page = (null == request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page"));
		Integer pageSize = (null == request.getParameter("rows"))?20:Integer.parseInt(request.getParameter("rows"));
		Integer startRow = (page -1)*pageSize;
		result = m_dbService.getDataListBySql(dsId,startRow,pageSize,sql);
		Integer total = -1;
		total = m_dbService.getDataCountsBySql( dsId,sql );
		result.setTotal(total);
		Gson g = new Gson();
		log.info(g.toJson(result.getDesc()));
		return result;
		
	}
	
	@RequestMapping("grammarTree.json")
	@ResponseBody
	public List<TreeVo> getGrammarTree(HttpServletRequest request){
		List<TreeVo> trees = new ArrayList<>();
		
		List<TreeVo> ch1 = new ArrayList<>();
		
		TreeVo vo1 = new TreeVo();
		vo1.setId("1000");
		vo1.setLeaf(false);
		vo1.setText("常用");
		trees.add(vo1);
		
		TreeVo v1 = new TreeVo();
		v1.setId("1");
		v1.setText("SELECT");
		v1.setLeaf(true);
		v1.setIconCls("icon-page_white_code");
		ch1.add(v1);
		
		
		TreeVo v2 = new TreeVo();
		v2.setId("2");
		v2.setText("INSERT");
		v2.setLeaf(true);
		v2.setIconCls("icon-page_white_code");
		ch1.add(v2);
		
		TreeVo v3 = new TreeVo();
		v3.setId("3");
		v3.setText("UPDATE");
		v3.setLeaf(true);
		v3.setIconCls("icon-page_white_code");
		ch1.add(v3);
		
		TreeVo v4 = new TreeVo();
		v4.setId("4");
		v4.setText("DELETE");
		v4.setLeaf(true);
		v4.setIconCls("icon-page_white_code");
		ch1.add(v4);
		
	    vo1.setChildren(ch1);
	    
	    TreeVo vo2 = new TreeVo();
		vo2.setId("2000");
		vo2.setLeaf(false);
		vo2.setText("存储过程");
		trees.add(vo2);
		
		TreeVo vo3 = new TreeVo();
		vo3.setId("3000");
		vo3.setLeaf(false);
		vo3.setText("函数");
		trees.add(vo3);
		
		return trees;
	}
	
	
	
	

}
