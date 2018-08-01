
var toolbar = function(){
	return [{
		 text: '关键字：<input type="text" id="key" value="" size="20" />'
	},{
		text : '搜索',  
        iconCls : 'icon-2012092109942', 
        plain:true,
        handler : function(){
        	
        	searchByKey();
        }
	},'-',{
		text : '上传',  
        iconCls : 'icon-20130406125647919_easyicon_net_16',  
        handler : function(){
        	createUploadDataGridDialog();
        }
	},{
		text : '删除',  
        iconCls : 'icon-delete',  
        handler : function(){
        	deletePasfile();
        }
	},{
		text : '同步到服务器',  
        iconCls : 'icon-disk_upload',  
        handler : function(){
        	uploadPasfile();
        }
	},{
		text : '同步到数据库',  
        iconCls : 'icon-database_copy',  
        handler : function(){
        	sysPasfileToDB();
        }
	}];
}();