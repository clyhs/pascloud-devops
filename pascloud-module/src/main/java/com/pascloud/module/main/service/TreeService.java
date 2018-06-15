package com.pascloud.module.main.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.constant.Constants;
import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.database.DBInfo;
import com.thoughtworks.xstream.XStream;

@Service
public class TreeService extends AbstractBaseService {
	@Autowired
	private PasCloudConfig   m_config;
	
	private String getTreePath(){
		String path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_TREE()+"/trees.xml";
		return path;
	}
	
	public List<TreeVo> getTrees(){
		List<TreeVo> trees = new ArrayList<>();
		File file = new File(getTreePath());
		XStream xstream = new XStream();
		xstream.alias("tree", TreeVo.class);
		trees =  (List<TreeVo>) xstream.fromXML(file);
		
		Collections.sort(trees, new Comparator<TreeVo>() {  
            @Override  
            public int compare(TreeVo o1, TreeVo o2) { 
                int i =getValue(o1)-getValue(o2);
                return i;  
            }
            private Integer getValue(TreeVo o){
            	return Integer.valueOf(o.getId());
            }
        }); 
		return trees;
	}
	
	public List<TreeVo> getParentTrees(){
		List<TreeVo> trees = new ArrayList<>();
		List<TreeVo> result = new ArrayList<>();
		trees = getTrees();
		
		for(TreeVo vo:trees){
			if(vo.getPid().equals("0")){
				result.add(vo);
			}
		}
		Collections.sort(result, new Comparator<TreeVo>() {  
            @Override  
            public int compare(TreeVo o1, TreeVo o2) { 
                int i =getValue(o1)-getValue(o2);
                return i;  
            }
            private Integer getValue(TreeVo o){
            	return Integer.valueOf(o.getId());
            }
        });
		
		return result;
	}
	
	public List<TreeVo> getChildTrees(String pid){
		List<TreeVo> trees = new ArrayList<>();
		List<TreeVo> result = new ArrayList<>();
		trees = getTrees();
		
		for(TreeVo vo:trees){
			if(vo.getId().equals(pid)){
				result.addAll(vo.getChildren());
			}
		}
		Collections.sort(result, new Comparator<TreeVo>() {  
            @Override  
            public int compare(TreeVo o1, TreeVo o2) { 
                int i =getValue(o1)-getValue(o2);
                return i;  
            }
            private Integer getValue(TreeVo o){
            	return Integer.valueOf(o.getId());
            }
        });
		
		return result;
	}
	
	

}
