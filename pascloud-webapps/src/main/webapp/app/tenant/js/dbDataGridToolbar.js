
var toolbar = function(){
	return [{
		text : '一键开户',
		iconCls : 'icon-script_start',
		handler : function(){
        	openOneTenant();
        }
	}/*,{
		text : '添加数据库',  
        iconCls : 'icon-add',  
        handler : function(){
        	addDbDialog();
        }
	}*/,{
		text : '删除租户',  
        iconCls : 'icon-delete',  
        handler : function(){
        	delDB();
        }
	},{
		text : '配置同步',  
        iconCls : 'icon-disk_upload',  
        handler : function(){
        	uploadConfig();
        }
	},{
		text : '行员同步',  
        iconCls : 'icon-20130406125647919_easyicon_net_16',  
        handler : function(){
        	sysHy();
        }
	},'-',{
		text : '关闭检查',  
        iconCls : 'icon-bin_closed',  
        handler : function(){
        	closeConnCheck();
        }
	}];
}();