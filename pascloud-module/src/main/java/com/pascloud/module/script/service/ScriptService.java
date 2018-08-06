package com.pascloud.module.script.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.constant.Constants;
import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.utils.FileUtils;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.script.ScriptEnum;


@Service
public class ScriptService extends AbstractBaseService {
	
	@Autowired
	private PasCloudConfig m_config;

	public List<TreeVo> getScriptWithLocal(ScriptEnum type){
		List<TreeVo> filetrees = new ArrayList<>();
		String path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SCRIPT_DIR();
		path = path + "/" + type.getValue() + "/script";
		String id = type.getValue();
		if(FileUtils.isDir(path)){
			
			List<File> files = FileUtils.listFilesInDir(path);
			if(null!=files && files.size()>0){
				TreeVo tvo = new TreeVo();
				
				Integer i = 1;
				String tID = id+"-0";
				
				tvo.setId(tID);
				tvo.setText(type.getValue());
				tvo.setIconCls("icon-folder");
				tvo.setLeaf(false);
				
				List<TreeVo> childrens = new ArrayList<>();
				for(File f:files){
					TreeVo t = new TreeVo();
					tID = id+"-"+i;
					t.setId(tID);
					t.setText(f.getName());
					t.setIconCls("icon-file");
					t.setLeaf(true);
					childrens.add(t);
					i++;
				}
				tvo.setChildren(childrens);
				filetrees.add(tvo);
				
			}
		}
		return filetrees;
	}
}
