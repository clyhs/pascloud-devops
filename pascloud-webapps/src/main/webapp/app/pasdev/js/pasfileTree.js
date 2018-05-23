function initPasfileTree(){
    EasyUILoad('pasfileTree');
    $('#pasfileTree').treegrid({
	    url:'pasfiledirsfortree.json',
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
        toolbar:treetoolbar,
		//onContextMenu: onContextMenu,
		//onClick: onDBClick,
		onLoadSuccess:function(node, data){
			if (data.length > 0) {
		         //找到第一个元素
				for(var i=0;i<data.length;i++){
					if(data[i].text == 'pasdev'){
						$('#pasfileTree').treegrid('select', data[i].id);
					}
				}          
		    }
		},
		onDblClickCell:function(rowIndex, field, value){
			var node = $('#pasfileTree').treegrid('getSelected');
			var title = node.text;
			var index= node.id;
			var icon = "";
			//alert(title);
			reloadTableWithID(title);
		}
	});
}


function reloadTree(){
	$('#pasfileTree').treegrid('options').url ='pasfiledirsfortree.json';
	$('#pasfileTree').treegrid("reload");
}





