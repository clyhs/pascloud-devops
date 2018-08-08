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
import com.pascloud.constant.Constants;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.mversion.service.MVersionService;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.module.redis.service.RedisService;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.mversion.MVersionTreeVo;
import com.pascloud.vo.mversion.XtcdVo;
import com.pascloud.vo.pass.RedisVo;
import com.pascloud.vo.result.ResultCommon;

@Controller
@RequestMapping("module/mversion")
public class MVersionController extends BaseController {
	
	@Autowired
	private ConfigService    m_configService;
	@Autowired
	private MVersionService  m_mVersionService;
	
	@Autowired
	private RedisService     m_redisService;
	
	@Autowired
	private PasService       m_pasService;
	
	@RequestMapping("/index.html")
	public ModelAndView index(HttpServletRequest request){
		m_redisService.initRedisServer();
		ModelAndView view = new ModelAndView("mversion/index");
		return view;
	}
	
	@RequestMapping("/menu.html")
	public ModelAndView menu(HttpServletRequest request){
		m_redisService.initRedisServer();
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
				
				if(dbf.getId().equals(Constants.PASCLOUD_PUBLIC_DB)){
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
	
	
	@RequestMapping("/getMVersionTreeExcludeId.json")
	@ResponseBody
	public List<TreeVo> getMVersionTree(HttpServletRequest request,
			@RequestParam(value="Id",defaultValue="",required=true) String Id){
		List<TreeVo> trees = new ArrayList<>();
		
		List<DBInfo> result = new ArrayList<>();
		
		result = m_configService.getDBFromConfig();
		
		if(result.size() > 0){
			for(DBInfo dbf:result){
				TreeVo t = new TreeVo();
				
				if(dbf.getId().equals(Id)){
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
    	
        //Gson g = new Gson();
		//System.out.println(g.toJson(result));
        
    	return result;
    }
	
	@RequestMapping("/getMVersionMenuTreeWith.json")
	@ResponseBody
    public List<MVersionTreeVo>	getMVersionMenuTreeWith(HttpServletRequest request,
    		@RequestParam(value="selectId",defaultValue="",required=true) String selectId,
    		@RequestParam(value="dnId",defaultValue="",required=true) String dnId){
    	List<MVersionTreeVo> result = new ArrayList<>();
       
        List<XtcdVo> cds = new ArrayList<>();
        List<XtcdVo> dnCDS = new ArrayList<>();
        
        log.info("查询Id="+selectId+"的功能菜单");
        
        if(selectId.length() <=0 || dnId.length()<=0){
        	return result;
        }
        cds = m_mVersionService.getXtcdById(selectId);
        
        dnCDS = m_mVersionService.getXtcdById(dnId);
 
        result = buildTreeVo(cds,"0",dnCDS);
    	
        //Gson g = new Gson();
		//System.out.println(g.toJson(result));
        
    	return result;
    }
	
	/**
	 * 添加资源菜单
	 * @param request
	 * @param Id
	 * @param name
	 * @param cdjb
	 * @param url
	 * @param version
	 * @param pId
	 * @return
	 */
	@RequestMapping("/addXtcd.json")
	@ResponseBody
    public ResultCommon	addXtcd(HttpServletRequest request,
    		@RequestParam(value="Id",defaultValue="",required=true) String Id,
    		@RequestParam(value="name",defaultValue="",required=true) String name,
    		@RequestParam(value="cdjb",defaultValue="0",required=true) String cdjb,
    		@RequestParam(value="url",defaultValue="",required=true) String url,
    		@RequestParam(value="version",defaultValue="",required=true) String version,
    		@RequestParam(value="pId",defaultValue="",required=true) Integer pId,
    		@RequestParam(value="classid",defaultValue="",required=true) String classid){
		ResultCommon result = null;
		XtcdVo vo = new XtcdVo();
		//INSERT INTO xtb_xtzycd(xmmc,xmdz,sjxm,cdjb,dzlx,classid,sfxs,imgurl,qxbs,version)
		log.info("添加菜单资源开始");
		if(name.length() <=0 || Id.length()<=0 || url.length()<=0 || version.length()<=0 || pId <0 || classid.length()<=0){
			result = new ResultCommon(PasCloudCode.PARAMEXCEPTION);
		}
		
		vo.setXmmc(name);
		vo.setXmdz(url);
		vo.setSjxm(pId);
		vo.setCdjb(cdjb);
		vo.setDzlx("1");
		vo.setClassid(classid);
		vo.setSfxs(0);
		vo.setQxbs(0);
		vo.setVersion(version);
		vo.setImgurl("");
		
		result = m_mVersionService.addXtcd(vo, Id);
		
		if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
			m_redisService.deleteRedisApp();
		}
        
		log.info("添加菜单资源结束");
    	return result;
    }
	
	
	@RequestMapping("/delXtcd.json")
	@ResponseBody
    public ResultCommon	delXtcd(HttpServletRequest request,
    		@RequestParam(value="Id",defaultValue="",required=true) String Id,
    		@RequestParam(value="xmdh",defaultValue="0",required=true) Integer xmdh){
		ResultCommon result = null;
		XtcdVo vo = new XtcdVo();
		log.info("删除菜单资源开始");
		if( xmdh <=0 || Id.length()<=0){
			result = new ResultCommon(PasCloudCode.PARAMEXCEPTION);
		}
		vo.setXmdh(xmdh);
		
		result = m_mVersionService.delXtcd(vo, Id);
		if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
			m_redisService.deleteRedisApp();
		}
        
		log.info("删除菜单资源结束");
    	return result;
    }
	
	
	@RequestMapping("/changeXtcdSfxs.json")
	@ResponseBody
    public ResultCommon	changeXtcdSfxs(HttpServletRequest request,
    		@RequestParam(value="Id",defaultValue="",required=true) String Id,
    		@RequestParam(value="xmdh",defaultValue="0",required=true) Integer xmdh,
    		@RequestParam(value="sfxs",defaultValue="0",required=true) Integer sfxs){
		ResultCommon result = null;
		XtcdVo vo = new XtcdVo();
		log.info("改变菜单资源显示方式 开始");
		if( xmdh <=0 || Id.length()<=0){
			result = new ResultCommon(PasCloudCode.PARAMEXCEPTION);
		}
		vo.setXmdh(xmdh);
		vo.setSfxs(sfxs);
		
		result = m_mVersionService.changeXtcdStatus(vo, Id);
		
		if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
			m_redisService.deleteRedisApp();
		}
        
		log.info("改变菜单资源显示方式 结束");
    	return result;
    }
	
	@RequestMapping("/sysOneMenuToTenant.json")
	@ResponseBody
    public ResultCommon	sysOneMenuToTenant(HttpServletRequest request,
    		@RequestParam(value="Id",defaultValue="",required=true) String Id,
    		@RequestParam(value="tIds",defaultValue="",required=true) String tIds,
    		@RequestParam(value="xmdh",defaultValue="0",required=true) Integer xmdh){
		ResultCommon result = null;
		XtcdVo vo = new XtcdVo();
		XtcdVo vop= null;
		log.info("分配菜单资源 开始");
		if( xmdh <=0 || Id.length()<=0 || tIds.length()<=0){
			result = new ResultCommon(PasCloudCode.PARAMEXCEPTION);
			return result;
		}
		
		vo.setXmdh(xmdh);
		vop = m_mVersionService.getXtcdVoByXmdh(vo, Id);
		if(null==vop){
			result = new ResultCommon(PasCloudCode.ERROR);
			return result;
		}else{
			
			String[] Ids = tIds.split(",");
			if(null!=Ids && Ids.length>0){
				result = m_mVersionService.addXtcd(vop, Ids);
				if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
					m_redisService.deleteRedisApp();
				}
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
			
		}
        
		log.info("分配菜单资源结束");
    	return result;
    }
	
	@RequestMapping("/sysAllMenuToTenant.json")
	@ResponseBody
    public ResultCommon	sysAllMenuToTenant(HttpServletRequest request,
    		@RequestParam(value="tIds",defaultValue="",required=true) String tIds){
		ResultCommon result = null;
		log.info("同步菜单资源 开始");
		if( tIds.length()<=0){
			result = new ResultCommon(PasCloudCode.PARAMEXCEPTION);
			return result;
		}

		String[] Ids = tIds.split(",");
		if(null!=Ids && Ids.length>0){
			result = m_mVersionService.sysAllXtcdToTenant(Ids);
			
			if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
				m_redisService.deleteRedisApp();
			}
		}else{
			result = new ResultCommon(PasCloudCode.ERROR);
		}
			
		log.info("同步菜单资源结束");
    	return result;
    }
	
	@RequestMapping("/sysOtherMenuToTenant.json")
	@ResponseBody
	public ResultCommon sysOtherMenuToTenant(HttpServletRequest request,
			@RequestParam(value="selectId",defaultValue="",required=true) String selectId,
			@RequestParam(value="dnId",defaultValue="",required=true) String dnId,
			@RequestParam(value="idList",defaultValue="",required=true) String idList){
		ResultCommon result = null;
		
		if( selectId.length()<=0 || dnId.length()<=0 || idList.length()<=0 ){
			result = new ResultCommon(PasCloudCode.PARAMEXCEPTION);
			return result;
		}
		
		
		result = m_mVersionService.sysOtherXtcdToTenant(selectId,dnId,idList);
		
		if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
			m_redisService.deleteRedisApp();
		}
		
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
				t.setSfxs(cd.getSfxs());
				
				if(cd.getCdjb().equals("0") || cd.getCdjb().equals("1")){
					t.setType("目录");
				}
				if(cd.getCdjb().equals("3")){
					t.setType("按钮");
				}
				if(cd.getCdjb().equals("2")){
					if(cd.getXmdz().contains("/module/parser")){
						t.setType("pas+");
					}else{
						t.setType("java");
					}
				}
				
				if(cd.getCdjb().equals("0") || cd.getCdjb().equals("1") || cd.getCdjb().equals("3")){
					t.setVersion("#");
				}else{
					if(null!=cd.getVersion()){
						t.setVersion(cd.getVersion());
					}else{
						t.setVersion("1.0.0.0");
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
	
	private List<MVersionTreeVo> buildTreeVo(List<XtcdVo> cds,String parentId,List<XtcdVo> dnCDS){
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
				t.setSfxs(cd.getSfxs());
				
				if(cd.getCdjb().equals("0") || cd.getCdjb().equals("1")){
					t.setType("目录");
				}
				if(cd.getCdjb().equals("3")){
					t.setType("按钮");
				}
				if(cd.getCdjb().equals("2")){
					if(cd.getXmdz().contains("/module/parser")){
						t.setType("pas+");
					}else{
						t.setType("java");
					}
				}
				
				if(cd.getCdjb().equals("0") || cd.getCdjb().equals("1") || cd.getCdjb().equals("3")){
					t.setVersion("#");
				}else{
					if(null!=cd.getVersion()){
						t.setVersion(cd.getVersion());
					}else{
						t.setVersion("1.0.0.0");
					}
				}
				if(cd.getCdjb().endsWith("2")){
					t.setIconCls("icon-world_link");
				}
				if(cd.getCdjb().equals("3")){
					t.setIconCls("icon-bullet_green");
				}
				
				if(dnCDS.contains(cd)){
					t.setChecked(true);
				}else{
					t.setChecked(false);
				}
				
				List<MVersionTreeVo> lists = buildTreeVo(cds, id,dnCDS);
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
