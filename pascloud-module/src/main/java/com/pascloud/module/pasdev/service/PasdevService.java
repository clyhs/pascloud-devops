package com.pascloud.module.pasdev.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.bean.pasdev.PasfileVo;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.xml.XmlParser;

@Service
public class PasdevService {
	
	@Autowired
	private PasCloudConfig m_config;
	
	public List<PasfileVo> getPasdevFiles(){
		List<PasfileVo> result = new ArrayList<>();
		List<File> files = new ArrayList<File>();
		files = FileUtils.listFilesInDirWithFilter(m_config.getPASCLOUD_DEV_DIR(), ".xml", false);
		//System.out.println(m_config.getPASCLOUD_DEV_DIR());
		if(files.size()>0){
			Iterator<File> it = files.iterator();
			while(it.hasNext()){
				File f = it.next();
				PasfileVo vo = new PasfileVo();
				vo.setFilename(f.getName());
				String funId = FileUtils.getFileNameNoExtension(f);
				vo.setFunId(funId);
				vo.setSuffix(FileUtils.getFileExtension(f));
				vo.setFilepara(funId+".para");
				vo.setFilepath(f.getAbsolutePath());;
				ParserPasfile(f.getAbsolutePath(),vo);
				
				result.add(vo);
			}
		}
		
		return result;
		
	}
	
	private void ParserPasfile(String filepath,PasfileVo vo){
		Document doc = XmlParser.getDocument(filepath);
		Element root = doc.getRootElement();
		vo.setTitle(root.attributeValue("title"));
		vo.setType(root.attributeValue("type"));
		if(null == root.attributeValue("version")){
			
		}else{
			vo.setVersion(root.attributeValue("version"));
		}
		
	}
	
	

}
