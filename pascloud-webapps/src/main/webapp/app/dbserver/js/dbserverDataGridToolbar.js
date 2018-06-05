var toolbar = function(){
	return [{
		text : '新建数据库',  
        iconCls : 'icon-add',  
        handler : function(){
        	addDB();
        }
	},{
		text : '删除数据库',  
        iconCls : 'icon-add',  
        handler : function(){
        	delDB();
        }
	},{
		text : '导入数据',  
        iconCls : 'icon-add',  
        handler : function(){
        	impDmpWithSid();
        }
	}];
}();