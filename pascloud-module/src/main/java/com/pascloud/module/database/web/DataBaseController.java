package com.pascloud.module.database.web;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pascloud.constant.Constants;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.database.service.DataBaseService;
import com.pascloud.module.mycat.service.MycatService;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.db.DataSourceUtils;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.database.DBColumnVo;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.database.DBTableVo;
import com.pascloud.vo.mycat.DataNodeVo;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.result.ResultPageVo;
import com.thoughtworks.xstream.XStream;

@Controller
@RequestMapping("module/database")
public class DataBaseController extends BaseController {

	@Autowired
	private PasCloudConfig m_config;
	@Autowired
	private DataBaseService m_dbService;
	@Autowired
	private MycatService m_mycatService;

	@RequestMapping("index.html")
	public String index() {
		initDataSourceForMycat();
		return "database/index";
	}

	private void initDataSourceForMycat() {
		List<DataNodeVo> nodes = m_mycatService.getDataNodes();
		for (DataNodeVo vo : nodes) {
			try {
				ComboPooledDataSource dataSource = new ComboPooledDataSource();
				if(null!=DataSourceUtils.getDataSource(vo.getName())){
					dataSource = (ComboPooledDataSource) DataSourceUtils.getDataSource(vo.getName());
				}else{
					Locale.setDefault( Locale.US );
					log.info(vo.getUrl().trim() + vo.getDbType());
					dataSource = new ComboPooledDataSource();
					dataSource.setUser(vo.getUser().trim());
					dataSource.setDataSourceName(vo.getDatabase());
					dataSource.setPassword(vo.getPassword().trim());
					
					if("mysql".equals(vo.getDbType())){
						String url = DBUtils.getUrlByParams(vo.getDbType(), vo.getIp(), vo.getDatabase(), Integer.valueOf(vo.getPort()));
						dataSource.setJdbcUrl(url);
					}else{
						dataSource.setJdbcUrl(vo.getUrl().trim());
					}
					dataSource.setDriverClass(DBUtils.getDirverClassName(vo.getDbType()));
					dataSource.setInitialPoolSize(5);
					dataSource.setMinPoolSize(5);
					dataSource.setMaxPoolSize(10);
					dataSource.setMaxStatements(0);
					dataSource.setMaxIdleTime(60);
					// dataSource.getConnection();
					DataSourceUtils.addDataSource(vo.getName(), dataSource);
				}
				
			} catch (Exception e) {
				// e.printStackTrace();
				log.error("初始化失败");
			}

		}

		log.info("初始化数据库--结束--");

	}

	private void initDataSource() {
		String database_dir = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DATABASE_DIR();
		if (null != database_dir) {
			log.info("初始化数据库--开始--");
			XStream xstream = new XStream();
			xstream.alias("dbinfo", DBInfo.class);
			log.info(database_dir);
			File database_file = new File(database_dir);
			if (FileUtils.createOrExistsDir(database_file)) {
				List<File> files = FileUtils.listFilesInDirWithFilter(database_file, "xml");
				log.info(files.size() + "");
				if (files.size() > 0) {
					for (File file : files) {
						try {
							ComboPooledDataSource dataSource = new ComboPooledDataSource();
							DBInfo info = (DBInfo) xstream.fromXML(file);
							log.info(info.getUrl().trim() + info.getDbType());
							dataSource = new ComboPooledDataSource();
							dataSource.setUser(info.getUsername().trim());
							dataSource.setDataSourceName(info.getName());
							dataSource.setPassword(info.getPassword().trim());
							dataSource.setJdbcUrl(info.getUrl().trim());
							dataSource.setDriverClass(info.getDriverClassName().trim());
							dataSource.setInitialPoolSize(5);
							dataSource.setMinPoolSize(5);
							dataSource.setMaxPoolSize(10);
							dataSource.setMaxStatements(0);
							dataSource.setMaxIdleTime(60);
							// dataSource.getConnection();
							DataSourceUtils.addDataSource(info.getId(), dataSource);
						} catch (Exception e) {
							// e.printStackTrace();
							log.error("初始化失败");
						}

					}
				}
			}

			log.info("初始化数据库--结束--");
		}
	}

	@RequestMapping("table.html")
	public ModelAndView table(HttpServletRequest request,
			@RequestParam(value = "tablename", defaultValue = "", required = true) String tablename,
			@RequestParam(value = "dsId", defaultValue = "", required = true) String dsId) {

		List<DBColumnVo> columns = new ArrayList<>();
		columns = m_dbService.getColumns(tablename, dsId);
		ModelAndView view = new ModelAndView("database/table");
		String url = "/module/database/datas.json?tablename=" + tablename + "&dsId=" + dsId;
		Gson g = new Gson();
		log.info(g.toJson(columns));
		view.addObject("columns", columns);
		view.addObject("url", url);
		return view;
	}

	@RequestMapping("tableEdit.html")
	public ModelAndView editTable(HttpServletRequest request,
			@RequestParam(value = "tablename", defaultValue = "", required = true) String tablename,
			@RequestParam(value = "dsId", defaultValue = "", required = true) String dsId) {
		ModelAndView view = new ModelAndView("database/tableEdit");

		String url = "/module/database/tableColumns.json?tablename=" + tablename + "&dsId=" + dsId;
		view.addObject("url", url);

		return view;
	}

	@RequestMapping("dbTrees.json")
	@ResponseBody
	public List<TreeVo> getDataBaseList(HttpServletRequest request) {
		List<TreeVo> trees = new ArrayList<>();
		ConcurrentHashMap<String, ComboPooledDataSource> dataSourceMap = DataSourceUtils.getDataSources();
		Iterator it = dataSourceMap.entrySet().iterator();
		while (it.hasNext()) {
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

	@RequestMapping("dbTreesFromMycat.json")
	@ResponseBody
	public List<TreeVo> getDataBaseListFromMycat(HttpServletRequest request) {
		List<TreeVo> trees = new ArrayList<>();
		List<DataNodeVo> nodes = m_mycatService.getDataNodes();
		Iterator<DataNodeVo> it = nodes.iterator();
		while (it.hasNext()) {
			DataNodeVo vo = it.next();
			TreeVo tree = new TreeVo();
			tree.setText(vo.getName());
			tree.setId(vo.getName());
			tree.setLeaf(true);
			tree.setIconCls("icon-database");
			trees.add(tree);
		}
		return trees;
	}

	@RequestMapping("tableTrees.json")
	@ResponseBody
	public List<TreeVo> getTableList(HttpServletRequest request,
			@RequestParam(value = "dsId", defaultValue = "", required = true) String dsId) {
		List<TreeVo> trees = new ArrayList<>();
		List<DBTableVo> tables = new ArrayList<>();
		log.info(dsId);
		if(null!=dsId && !"".equals(dsId)){
			tables = m_dbService.getTables(dsId);
			for (DBTableVo vo : tables) {
				TreeVo tree = new TreeVo();
				tree.setText(vo.getName());
				tree.setId(vo.getId());
				tree.setLeaf(true);
				tree.setUrl("/module/database/table.html?tablename=" + vo.getName() + "&dsId=" + dsId);
				tree.setIconCls("icon-table");
				trees.add(tree);
			}
		}
		
		return trees;
	}

	@RequestMapping("datas.json")
	@ResponseBody
	public ResultPageVo/* List<Map<String, Object>> */ getDataList(HttpServletRequest request,
			@RequestParam(value = "tablename", defaultValue = "", required = true) String tablename,
			@RequestParam(value = "dsId", defaultValue = "", required = true) String dsId) {
		ResultPageVo result = new ResultPageVo(10000, "success");
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> list2= new ArrayList<>(); 
		Integer page = (null == request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = (null == request.getParameter("rows")) ? 20 : Integer.parseInt(request.getParameter("rows"));
		Integer startRow = (page - 1) * pageSize;
		list = m_dbService.getDataList(tablename, dsId, startRow, pageSize);
		if(list.size()>0){
			for(Map map:list){
				Iterator it =map.entrySet().iterator();
				Map<String, Object> map2 = new HashMap<String, Object>();
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry) it.next();  
					Object key = entry.getKey();  
					Object value = entry.getValue();  
					if(value instanceof oracle.sql.TIMESTAMP){
						value = getOracleTimestamp(value);
					}
					map2.put(key.toString(), value);
				}
				list2.add(map2);
			}
		}
		Integer total = -1;
		total = m_dbService.getDataCounts(tablename, dsId);
		result.setRows(list2);
		result.setTotal(total);
		Gson g = new Gson();
		log.info(g.toJson(result));
		return result;
	}
	
	private Timestamp getOracleTimestamp(Object value) {
	    try {
	        Class clz = value.getClass();
	        Method m = clz.getMethod("timestampValue");
	        //m = clz.getMethod("timeValue", null); 时间类型
	        //m = clz.getMethod("dateValue", null); 日期类型
	        return (Timestamp) m.invoke(value);
	 
	    } catch (Exception e) {
	        return null;
	    }
	}

	@RequestMapping("execSql.json")
	@ResponseBody
	public ResultCommon executeSql(HttpServletRequest request,
			@RequestParam(value = "dsId", defaultValue = "", required = true) String dsId,
			@RequestParam(value = "sql", defaultValue = "", required = true) String sql) {
		ResultCommon result = new ResultCommon(10000, "成功");

		String desc = m_dbService.execBySql(dsId, sql);
		result.setDesc(desc);

		return result;
	}

	@RequestMapping("execHeader.json")
	@ResponseBody
	public List<String> execHeaderAction(HttpServletRequest request,
			@RequestParam(value = "dsId", defaultValue = "", required = true) String dsId,
			@RequestParam(value = "sql", defaultValue = "", required = true) String sql) {
		ResultPageVo result = new ResultPageVo(10000, "success");
		log.info(sql);
		List<String> headers = new ArrayList<>();
		headers = m_dbService.getSqlColumnName(dsId, sql);
		return headers;

	}

	@RequestMapping("exec.json")
	@ResponseBody
	public ResultPageVo execAction(HttpServletRequest request,
			@RequestParam(value = "dsId", defaultValue = "", required = true) String dsId,
			@RequestParam(value = "sql", defaultValue = "", required = true) String sql) {
		ResultPageVo result = new ResultPageVo(10000, "success");
		log.info(sql);
		Integer page = (null == request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
		Integer pageSize = (null == request.getParameter("rows")) ? 20 : Integer.parseInt(request.getParameter("rows"));
		Integer startRow = (page - 1) * pageSize;
		result = m_dbService.getDataListBySql(dsId, startRow, pageSize, sql);
		Integer total = -1;
		total = m_dbService.getDataCountsBySql(dsId, sql);
		result.setTotal(total);
		Gson g = new Gson();
		log.info(g.toJson(result.getDesc()));
		return result;

	}

	@RequestMapping("tableColumns.json")
	@ResponseBody
	public List<DBColumnVo> getTableColumns(HttpServletRequest request,
			@RequestParam(value = "dsId", defaultValue = "", required = true) String dsId,
			@RequestParam(value = "tablename", defaultValue = "", required = true) String tablename) {
		List<DBColumnVo> columns = new ArrayList<>();
		columns = m_dbService.getColumns(tablename, dsId);
		return columns;
	}

	@RequestMapping("grammarTree.json")
	@ResponseBody
	public List<TreeVo> getGrammarTree(HttpServletRequest request) {
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

	@RequestMapping(value = "connectDb.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultCommon connectDb(HttpServletRequest request,
			@RequestParam(value = "dbType", defaultValue = "", required = true) String dbType,
			@RequestParam(value = "ip", defaultValue = "", required = true) String ip,
			@RequestParam(value = "port", defaultValue = "0", required = true) int port,
			@RequestParam(value = "database", defaultValue = "", required = true) String database,
			@RequestParam(value = "username", defaultValue = "", required = true) String username,
			@RequestParam(value = "password", defaultValue = "", required = true) String password) {

		ResultCommon result = null;

		String driverClass = DBUtils.getDirverClassName(dbType);
		String url = DBUtils.getUrlByParams(dbType, ip, database, port);
		log.info(url);
		log.info(driverClass);
		DBUtils db = new DBUtils(driverClass, url, username, password);

		if (db.canConnect()) {
			result = new ResultCommon(10000, "成功");
		} else {
			result = new ResultCommon(20000, "失败");
		}

		return result;
	}

	@RequestMapping(value = "connectDbWidthUrl.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultCommon connectDbWithUrl(HttpServletRequest request,
			@RequestParam(value = "dbType", defaultValue = "", required = true) String dbType,
			@RequestParam(value = "url", defaultValue = "", required = true) String url,
			@RequestParam(value = "username", defaultValue = "", required = true) String username,
			@RequestParam(value = "password", defaultValue = "", required = true) String password) {

		ResultCommon result = null;

		String driverClass = DBUtils.getDirverClassName(dbType);
		log.info(url);
		log.info(driverClass);
		DBUtils db = new DBUtils(driverClass, url, username, password);
		try {
			if (db.canConnect()) {
				result = new ResultCommon(10000, "成功");
			} else {
				result = new ResultCommon(20000, "失败");
			}
		} catch (Exception e) {
			result = new ResultCommon(20000, "失败");
		}

		return result;
	}

}
