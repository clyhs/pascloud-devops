
var treetoolbar = function(){
	return [{
		text : '复制',  
        iconCls : 'icon-add',  
        handler : function(){
        	addTenantPasfile();
        }
	},{
		text : '删除',  
        iconCls : 'icon-delete',  
        handler : function(){
        	delPasfile();
        }
	},{
		text : '上传',  
        iconCls : 'icon-disk_upload',  
        handler : function(){
        	uploadPasfile();
        }
	}];
}();