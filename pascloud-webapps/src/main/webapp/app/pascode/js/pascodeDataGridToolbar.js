
var toolbar = function(){
	return [{
		text : '上传',  
        iconCls : 'icon-20130406125647919_easyicon_net_16',  
        handler : function(){
        	createUploadDataGridDialog();
        }
	},{
		text : '删除',  
        iconCls : 'icon-delete',  
        handler : function(){
        	deletePascode();
        }
	},{
		text : '升级',  
        iconCls : 'icon-folder_up',  
        handler : function(){
        	uploadPascodeAndRestart();
        }
	}];
}();