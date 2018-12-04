package com.pascloud.module.kettle.web;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.kettle.bean.RepositoryTree;
import com.pascloud.module.kettle.env.KettleEnvironment;
import com.pascloud.module.kettle.serv.KettleRepoService;

@RequestMapping("/module/kettle")
@Controller
public class KettleRepoController extends BaseController {
	
	@Autowired
	private KettleRepoService kettleRepoService;
	
	@RequestMapping("repoTree")
	@ResponseBody
    public RepositoryTree getRepository(){
		RepositoryTree tree = null;
		
		
		if(kettleRepoService.checkRepository("KettleFileRepository")){
			Repository repo = kettleRepoService.connectRepository("test2", "", "");
			if(null!=repo){
				tree = kettleRepoService.getfilefromRepository(repo);
			}
		}else{
			log.info("资源目录不存在");
		}
		return tree;
    }

}
