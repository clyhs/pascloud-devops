function initMainTreeGrid(){
    EasyUILoad('mainCenter');
    $('#mMainTreeGrid').treegrid({
	    url:'getscripts.json',
		method:'get',
		border:false,
		width: "auto",
		fit:true,
		//queryParams:{'Id':'dn0'},
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
        //toolbar:mtoolbar,
		//onContextMenu: onContextMenu,
		//onClickRow: onClickRow,
		onLoadSuccess:function(node, data){
			dispalyEasyUILoad( 'mainCenter' );	
		},
		onDblClickCell:function(rowIndex, field, value){
			
		}
	});
}



function reloadTree(){
	$('#mMainTreeGrid').treegrid('options').url ='getscripts.json';
	$('#mMainTreeGrid').treegrid("reload");
}



