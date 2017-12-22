/**
 * 参数定义工具栏按钮
 */
var toolbar = function(){
	return [{
		text : '添加',  
        iconCls : 'icon-add',  
        handler : function(){
        	addDialog();
        }
	},{
		text : '复制',  
        iconCls : 'icon-table_edit',  
        handler : function(){
            alert('复制')
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
		text : '销毁',  
        iconCls : 'icon-table_edit',  
        handler : function(){
            alert('销毁')
        }
	}];
}();