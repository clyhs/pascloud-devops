package com.pascloud.module.main.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.vo.common.TreeVo;

@Controller
@RequestMapping("module/main")
public class MainController extends BaseController {
	
	@RequestMapping("index.html")
	public String index(){
		return "main/index";
	}
	
	@RequestMapping("trees.json")
	@ResponseBody
	public List<TreeVo> getLeftMenu(){
		List<TreeVo> trees = new ArrayList<>();
		/*
		TreeVo t1 = new TreeVo();
		t1.setId("1");
		t1.setText("服务器节点");
		t1.setLeaf(true);
		t1.setUrl("/module/pas/index.html");*/
		
		TreeVo t2 = new TreeVo();
		t2.setId("1");
		t2.setText("分行管理");
		t2.setLeaf(true);
		t2.setUrl("/module/pas/index.html");
		
		TreeVo t3 = new TreeVo();
		t3.setId("2");
		t3.setText("天维服务");
		t3.setLeaf(true);
		t3.setUrl("/module/service/index.html");
		
		TreeVo t4 = new TreeVo();
		t4.setId("3");
		t4.setText("天维工具");
		t4.setLeaf(true);
		t4.setUrl("/module/tool/index.html");
		
		//trees.add(t1);
		trees.add(t2);
		trees.add(t3);
		trees.add(t4);
		
		return trees;
	}

}
