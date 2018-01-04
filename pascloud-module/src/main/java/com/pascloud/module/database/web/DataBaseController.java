package com.pascloud.module.database.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
		return result;
	}
	
	@RequestMapping("exec.json")
	@ResponseBody
	public ResultPageVo execAction(HttpServletRequest request,
			@RequestParam(value="dsId",defaultValue="",required=true) String dsId,
			@RequestParam(value="sql",defaultValue="",required=true) String sql){
		ResultPageVo result = new ResultPageVo(10000,"success");
		log.info(sql);
		List<Map<String, Object>> list = new ArrayList<>();
		Integer page = (null == request.getParameter("page"))?1:Integer.parseInt(request.getParameter("page"));
		Integer pageSize = (null == request.getParameter("rows"))?20:Integer.parseInt(request.getParameter("rows"));
		Integer startRow = (page -1)*pageSize;
		list = m_dbService.getDataListBySql(dsId,startRow,pageSize,sql);
		Integer total = -1;
		total = m_dbService.getDataCountsBySql( dsId,sql );
		result.setRows(list);
		result.setTotal(total);
		Gson g = new Gson();
		log.info(g.toJson(result));
		return result;
		
	}
	
	
	
	

}
