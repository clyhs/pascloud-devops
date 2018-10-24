
var toolbar = function(){
	return [{
		text : '添加',  
        iconCls : 'icon-control_add',  
        handler : function(){
        	add();
        }
	},{
		text : '停止',  
        iconCls : 'icon-control_pause',  
        handler : function(){
        	stop();
        }
	},{
		text : '恢复',  
        iconCls : 'icon-control_repeat_blue',  
        handler : function(){
        	resume();
        }
	},{
		text : '删除',  
        iconCls : 'icon-delete',  
        handler : function(){
        	deleteJob();
        }
	},{
		text : '编辑',  
        iconCls : 'icon-edit',  
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
        iconCls : 'icon-bullet_start',  
        handler : function(){
        	startNow();
        }
	},{
		text : 'cron生成器',  
        iconCls : 'icon-script',  
        handler : function(){
        	addCron();
        }
	}];
}();

