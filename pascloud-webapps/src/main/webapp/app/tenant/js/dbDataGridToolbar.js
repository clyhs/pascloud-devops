
var toolbar = function(){
	return [{
		text : '添加数据库',  
        iconCls : 'icon-add',  
        handler : function(){
        	addDbDialog();
        }
	},{
		text : '删除数据库',  
        iconCls : 'icon-delete',  
        handler : function(){
        	delDB();
        }
	},{
		text : '同步上传',  
        iconCls : 'icon-disk_upload',  
        handler : function(){
        	uploadConfig();
        }
	}];
}();