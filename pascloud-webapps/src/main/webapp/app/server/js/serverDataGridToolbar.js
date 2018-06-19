var toolbar = function(){
	return [{
		text : '添加服务器',  
        iconCls : 'icon-server_add',  
        handler : function(){
        	addServerDialog();
        }
	},{
		text : '删除服务器',  
        iconCls : 'icon-server_delete',  
        handler : function(){
        	delServer();
        }
	}];
}();