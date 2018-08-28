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
	}/*,{
		text : '导入数据',  
        iconCls : 'icon-database_go',  
        handler : function(){
        	impDmpWithSid();
        }
	},{
		text : '设置行员',  
        iconCls : 'icon-database_go',  
        handler : function(){
        	updateHyDialog();
        }
	}*/];
}();