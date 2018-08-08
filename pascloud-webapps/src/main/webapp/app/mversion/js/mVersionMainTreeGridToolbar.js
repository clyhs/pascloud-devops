var mtoolbar = function(){
	return [{
		text : '添加',  
        iconCls : 'icon-add',  
        handler : function(){
        	addMVersionDialog();
        }
	},{
		text : '分配',  
        iconCls : 'icon-arrow_branch',  
        handler : function(){
        	createTenantTreeDialog(tenantTreeGridToolbar);
        }
	},{
		text : '删除',  
        iconCls : 'icon-delete',  
        handler : function(){
        	delMVersion();
        }
	},{
		text : '开启',  
        iconCls : 'icon-bullet_tick',  
        handler : function(){
        	changeMVersionSfxs('0');
        }
	},{
		text : '禁用',  
        iconCls : 'icon-cancel',  
        handler : function(){
        	changeMVersionSfxs('1');
        }
	},{
		text : '同步',  
        iconCls : 'icon-arrow_switch',  
        handler : function(){
        	createTenantTreeDialog(tenantTreeGridToolbarForSys);
        }
	}];
}();