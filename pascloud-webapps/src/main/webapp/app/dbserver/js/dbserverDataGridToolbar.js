var toolbar = function(){
	return [{
		text : '新建数据库实例',  
        iconCls : 'icon-database_add',  
        handler : function(){
        	addDB();
        }
	},{
		text : '删除数据库实例',  
        iconCls : 'icon-database_delete',  
        handler : function(){
        	delDB();
        }
	},{
		text : '初始化数据',  
        iconCls : 'icon-database_go',  
        handler : function(){
        	impDmpWithSid();
        }
	},{
		text : '重启监听器',  
        iconCls : 'icon-database_go',  
        handler : function(){
        	restartListener();
        }
	},{
		text : '新建数据库管理员',  
        iconCls : 'icon-database_go',  
        handler : function(){
        	createManagerUser();
        }
	},{
		text : '新建数据库用户',  
        iconCls : 'icon-database_go',  
        handler : function(){
        	addUser();
        }
	}/*,{
		text : '设置行员',  
        iconCls : 'icon-database_go',  
        handler : function(){
        	updateHyDialog();
        }
	}*/];
}();