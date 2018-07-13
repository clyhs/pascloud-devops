package com.pascloud.module.mversion.web;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.mversion.service.MVersionService;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.utils.DBUtils;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.mversion.MVersionTreeVo;
import com.pascloud.vo.mversion.XtcdVo;

@Controller
@RequestMapping("module/mversion")
public class MVersionController extends BaseController {
	
	@Autowired
	private ConfigService    m_configService;
	@Autowired
	private MVersionService  m_mVersionService;
	
	@RequestMapping("/index.html")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view = new ModelAndView("mversion/index");
		return view;
	}
	
	@RequestMapping("/menu.html")
	public ModelAndView menu(HttpServletRequest request){
		ModelAndView view = new ModelAndView("mversion/menu");
		return view;
	}
	
	@RequestMapping("/getMVersionTree.json")
	@ResponseBody
	public List<TreeVo> getMVersionTree(HttpServletRequest request){
		List<TreeVo> trees = new ArrayList<>();
		
		List<DBInfo> result = new ArrayList<>();
		
		result = m_configService.getDBFromConfig();
		
		if(result.size() > 0){
			for(DBInfo dbf:result){
				TreeVo t = new TreeVo();
				
				if(dbf.getId().equals("dn0")){
					continue;
				}
				
				t.setId(dbf.getId());
				t.setText(dbf.getCn());
				t.setLeaf(true);
				t.setUrl("#");
				t.setIconCls("icon-folder");
				trees.add(t);
			}
		}
		return trees;
	}
	@RequestMapping("/getMVersionMenuTree.json")
	@ResponseBody
    public List<MVersionTreeVo>	getMVersionMenuTree(HttpServletRequest request,
    		@RequestParam(value="Id",defaultValue="",required=true) String Id){
    	List<MVersionTreeVo> result = new ArrayList<>();
       
        List<XtcdVo> cds = new ArrayList<>();
        
        log.info("查询Id="+Id+"的功能菜单");
        
        if(Id.length() <=0){
        	return result;
        }
        cds = m_mVersionService.getXtcdById(Id);
        result = buildTreeVo(cds,"0");
    	
        Gson g = new Gson();
		System.out.println(g.toJson(result));
        
    	return result;
    }
	
	private List<MVersionTreeVo> buildTreeVo(List<XtcdVo> cds,String parentId){
		List<MVersionTreeVo> childtree = new ArrayList<MVersionTreeVo>(); 
		//JSONArray childMenu = new JSONArray();
		for (XtcdVo cd : cds) {
			///int menuId = Integer.parseInt(jg.getZbdh());
            //int pid = jg.getSjzb() == null?0:Integer.parseInt(jg.getSjzb());
            String id = cd.getXmdh()+"";
            String pid = cd.getSjxm()+"";
			if (parentId.equals(pid)) {
				MVersionTreeVo t = new MVersionTreeVo();
				t.setId(id);
				t.setText(cd.getXmmc());
				t.setUrl(cd.getXmdz());
				t.setLevel(cd.getCdjb());
				
				if(cd.getCdjb().equals("0") || cd.getCdjb().equals("1") || cd.getCdjb().equals("3")){
					t.setVersion("#");
				}else{
					if(null!=cd.getVersion()){
						t.setVersion(cd.getVersion());
					}else{
						t.setVersion("1.0.0");
					}
				}
				if(cd.getCdjb().endsWith("2")){
					t.setIconCls("icon-world_link");
				}
				if(cd.getCdjb().equals("3")){
					t.setIconCls("icon-bullet_green");
				}
				
				
				
				List<MVersionTreeVo> lists = buildTreeVo(cds, id);
				if(lists.size()>0){
					t.setLeaf(false);
					t.setChildren(lists);
				}else{
					t.setLeaf(true);
				}
				
				if(cd.getCdjb().equals("2") && lists.size()>0){
					t.setState("closed");
				}
				
				childtree.add(t);
			}
		}
		return childtree;
	}

}
