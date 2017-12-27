var toolbar = function(){
	return [{
		text : '添加节点',  
        iconCls : 'icon-add',  
        handler : function(){
        	alert('添加节点')
        }
	},{
		text : '删除节点',  
        iconCls : 'icon-delete',  
        handler : function(){
            //alert('添加节点')
            var row = $('#mainDataGrid').datagrid('getSelected');
            if(row.role == 'manager'){
            	alert('主节点不能删除 ！');
            }else{
            	$.get('leaveSwarm.json',{ip:row.addr},function(data,status){
            		dispalyEasyUILoad('infoLayout')
            		if(data.code == 10000){
            			alert("删除成功");
            			$('#mainDataGrid').datagrid('reload');
            		}
            	});
            }
        }
	}];
}();