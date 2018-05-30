function initDbserverTree(){
    EasyUILoad('dbserverTree');
    $('#dbserverTree').treegrid({
	    url:'dbserverTree.json',
		method:'get',
		border:false,
		animate:true,//是否用动画效果
        collapsible:false,//是否可折叠
        rownumbers:true,//行号
        fitColumns:true,
        idField:'id',//根据那个字段判断树节点关系
        treeField:'text',//根据那个列展现树
        showFooter:false,//是否使用页脚
		columns:[[
            //{title:'编号',field:'id',width:40,sortable:true},
            {title:'名称',field:'text',width:200,sortable:true}

        ]],
        //toolbar:treetoolbar,
		//onContextMenu: onContextMenu,
		//onClick: onDBClick,
		onLoadSuccess:function(node, data){
			
		},
		onDblClickCell:function(rowIndex, field, value){
			var node = $('#dbserverTree').treegrid('getSelected');
			var title = node.id;
			var index= node.id;
			var icon = "";
		}
	});
}


function reloadTree(){
	$('#dbserverTree').treegrid('options').url ='dbserver.json';
	$('#dbserverTree').treegrid("reload");
}





