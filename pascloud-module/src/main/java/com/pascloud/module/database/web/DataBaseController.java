package com.pascloud.module.database.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.utils.FileUtils;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.database.DBInfo;
import com.thoughtworks.xstream.XStream;

@Controller
@RequestMapping("module/database")
public class DataBaseController extends BaseController {
	
	@Autowired
	private PasCloudConfig m_config;
	
	@RequestMapping("index.html")
	public String index(){
		return "database/index";
	}
	
	@RequestMapping("trees.json")
	@ResponseBody
	public List<TreeVo> getListDB(HttpServletRequest request){
		List<TreeVo> trees = new ArrayList<>();
		XStream xstream = new  XStream();
		xstream.alias("dbinfo", DBInfo.class);
		String database_dir = m_config.getPASCLOUD_DATABASE_DIR();
		File   database_file= new File(database_dir);
		List<File> files = FileUtils.listFilesInDirWithFilter(database_file, "xml");
		if(files.size()>0){
			for(File file : files){
				DBInfo info = (DBInfo) xstream.fromXML(file);
				TreeVo tree = new TreeVo();
				tree.setText(info.getName());
				tree.setId(info.getId());
				tree.setLeaf(true);
				trees.add(tree);
			}
		}
		return trees;
	}

}
