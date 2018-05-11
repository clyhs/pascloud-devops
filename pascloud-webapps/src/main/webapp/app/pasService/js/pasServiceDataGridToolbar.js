
var toolbar = function(){
	return [{
		text : '创建环境',  
        iconCls : 'icon-add',  
        handler : function(){
        	addBaseContainer();
        }
	},{
		text : '创建主服务',  
        iconCls : 'icon-add',  
        handler : function(){
        	addMainServiceContainer();
        }
	},{
		text : '创建管家服务',  
        iconCls : 'icon-add',  
        handler : function(){
        	addPaspmServiceContainer();
        }
	},{
		text : '创建前端服务',  
        iconCls : 'icon-add',  
        handler : function(){
        	addTomcatContainer();
        }
	},{
		text : '复制',  
        iconCls : 'icon-table_edit',  
        handler : function(){
        	copyMainServiceContainer();
        }
	},{
		text : '运行',  
        iconCls : 'icon-table_edit',  
        handler : function(){
            startContainer();
        }
	},{
		text : '暂停',  
        iconCls : 'icon-table_edit',  
        handler : function(){
        	pauseContainer();
        }
	},{
		text : '恢复',  
        iconCls : 'icon-table_edit',  
        handler : function(){
        	unpauseContainer();
        }
	},{
		text : '停止',  
        iconCls : 'icon-table_edit',  
        handler : function(){
        	stopContainer();
        }
	},{
		text : '重启',  
        iconCls : 'icon-table_edit',  
        handler : function(){
        	restartContainer();
        }
	},{
		text : '销毁',  
        iconCls : 'icon-table_edit',  
        handler : function(){
            alert('销毁')
        }
	},{
		text : '查看日志',  
        iconCls : 'icon-table_edit',  
        handler : function(){
        	getContainerLog();
        }
	}];
}();