var menutoolbar = function(){
	return [{
		text : '添加',  
        iconCls : 'icon-add',  
        handler : function(){
        	addMenuDialog();
        }
	},{
		text : '删除',  
        iconCls : 'icon-delete',  
        handler : function(){
        	delMenu();
        }
	},{
		text : '开启',  
        iconCls : 'icon-bullet_tick',  
        handler : function(){
        	changeMenuSfxs('0');
        }
	},{
		text : '禁用',  
        iconCls : 'icon-cancel',  
        handler : function(){
        	changeMenuSfxs('1');
        }
	}/*,{
		text : '发布',  
        iconCls : 'icon-arrow_turn_left',  
        handler : function(){
        	
        }
	}*/,{
		text : '初始化',  
        iconCls : 'icon-cog_go',  
        handler : function(){
        	createTenantTreeDialog(tenantTreeGridToolbar);
        }
	}];
}();