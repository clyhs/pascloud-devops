
var toolbar = function(){
	return [{
		text : '环境',  
        iconCls : 'icon-control_add_blue',  
        handler : function(){
        	addBaseContainer();
        }
	},{
		text : '公共服务',  
        iconCls : 'icon-add',  
        handler : function(){
        	copyMainServiceContainer();
        }
	},{
		text : '管家',  
        iconCls : 'icon-add',  
        handler : function(){
        	copyPaspmServiceContainer();
        }
	},{
		text : '前端',  
        iconCls : 'icon-web',  
        handler : function(){
        	addTomcatContainer();
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