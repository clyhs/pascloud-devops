

function initMVersionTree(){
    
    $('#mVersionTree').treegrid({
	    url:'getMVersionTree.json',
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
		//onContextMenu: onContextMenu,
		
		onLoadSuccess:function(node, data){
			if (data.length > 0) {
		         //找到第一个元素
				   
				$('#mVersionTree').treegrid('select', data[0].id);
				dnId=data[0].id;
				if(!isInit){
					initMenuTreeGrid(dnId);
				}
		    }
			
		},
		onDblClickCell:function(rowIndex, field, value){
			var node = $('#mVersionTree').treegrid('getSelected');
			//var title = node.id;
			var index= node.id;
			var icon = "";
			//alert(title);
			dnId=node.id
			reloadTreeGridWithID(dnId);
		}
	});
}


function reloadTree(){
	$('#mVersionTree').treegrid('options').url ='getMVersionTree.json';
	$('#mVersionTree').treegrid("reload");
}


function reloadTreeGridWithID(Id){
    $('#menuTreeGrid').treegrid('options').url ='getMVersionMenuTree.json?Id='+Id;
	$('#menuTreeGrid').treegrid('reload');
}




