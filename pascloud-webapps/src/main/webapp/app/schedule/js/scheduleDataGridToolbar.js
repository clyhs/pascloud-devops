
var toolbar = function(){
	return [{
		text : '添加',  
        iconCls : 'icon-add',  
        handler : function(){
        	add();
        }
	},{
		text : '停止',  
        iconCls : 'icon-stop',  
        handler : function(){
        	stop();
        }
	},{
		text : '恢复',  
        iconCls : 'icon-folder_up',  
        handler : function(){
        	resume();
        }
	},{
		text : '删除',  
        iconCls : 'icon-folder_up',  
        handler : function(){
        	deleteJob();
        }
	},{
		text : '编辑',  
        iconCls : 'icon-folder_up',  
        handler : function(){
        	upd();
        }
	},{
		text : '保存',  
        iconCls : 'icon-save',  
        handler : function(){
        	accept();
        }
	},{
		text : '取消',  
        iconCls : 'icon-cancel',  
        handler : function(){
        	cancel();
        }
	},{
		text : '立即运行一次',  
        iconCls : 'icon-folder_up',  
        handler : function(){
        	startNow();
        }
	},{
		text : 'cron',  
        iconCls : 'icon-add',  
        handler : function(){
        	addCron();
        }
	}];
}();

