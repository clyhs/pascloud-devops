function initMenuTreeGrid(id){
    EasyUILoad('mainCenter');
    $('#menuTreeGrid').treegrid({
	    url:'getMVersionMenuTree.json',
		method:'get',
		border:false,
		width: "auto",
		fit:true,
		queryParams:{'Id':id},
		animate:true,//是否用动画效果
        collapsible:false,//是否可折叠
        rownumbers:true,//行号
        fitColumns:true,
        idField:'id',//根据那个字段判断树节点关系
        treeField:'text',//根据那个列展现树
        showFooter:false,//是否使用页脚
		columns:[[
            //{title:'编号',field:'id',width:40,sortable:true},
			{title:'名称',field:'text',width:200,sortable:true},
            {title:'级别',field:'level',width:160,sortable:true,hidden:'true'},
            {title:'版本',field:'version',width:40,sortable:true},
            {title:'是否禁用',field:'sfxs',width:40,sortable:true,formatter:formatOper },
            {title:'类型',field:'type',width:40,sortable:true},
            {title:'地址',field:'url',width:160,sortable:true}
        ]],
        toolbar:menutoolbar,
		//onContextMenu: onContextMenu,
        onClickRow: onClickRow,
		onLoadSuccess:function(node, data){
			dispalyEasyUILoad( 'mainCenter' );	
		},
		onDblClickCell:function(rowIndex, field, value){
			var node = $('#menuTreeGrid').treegrid('getSelected');
			var title = node.id;
			var index= node.id;
			var icon = "";
			//alert(title);
		}
	});
}

function onClickRow(node){
	
	level = node.level;
	pId = node.id;
	
}

function formatOper(val,row,index){ 
	if (val == '0'){
		return '<span style="color:blue;">否</span>';
	} else {
		return '<span style="color:red;">是</span>';;
	} 
} 


function delMenu(){
	var node = $('#menuTreeGrid').treegrid('getSelected');
	if(null == node){
		$.messager.alert('提示','请先选择一个节点!');
		return ;
	}
	
	var xmdh = node.id;
	
	if(xmdh.length <=0 ){
		$.messager.alert('提示','请先选择一个节点!');
		return ;
	}
	
	var param = {Id:dnId,xmdh:xmdh};
	$.messager.confirm('提示框','你确定要删除些节点，会影响到云平台的租户的菜单资源，请再确定？',function(r){
	    if (r){
	    	EasyUILoad('mainCenter');
	    	$.post("delXtcd.json",param,function(data,status){
	    		if(data.code == 10000){
	    		    //alert(data.desc);
	    			//reloadTree();
	    			reloadTreeGridWithID(dnId);
	    			dispalyEasyUILoad('mainCenter');
	    			$.messager.alert('提示','删除成功');	
	    		}else{
	    			$.messager.alert('提示','删除失败');	
	    		}
	    	});
	    }
	});
}

function changeMenuSfxs(status){
	var node = $('#menuTreeGrid').treegrid('getSelected');
	if(null == node){
		$.messager.alert('提示','请先选择一个节点!');
		return ;
	}
	
	var xmdh = node.id;
	
	if(xmdh.length <=0 ){
		$.messager.alert('提示','请先选择一个节点!');
		return ;
	}
	
	var param = {Id:dnId,xmdh:xmdh,sfxs:status};
	
	$.messager.confirm('提示框','你确定要你要改变菜单状态，请再确定？',function(r){
	    if (r){
	    	EasyUILoad('mainCenter');
	    	$.post("changeXtcdSfxs.json",param,function(data,status){
	    		if(data.code == 10000){
	    		    //alert(data.desc);
	    			//reloadTree();
	    			reloadTreeGridWithID(dnId);
	    			dispalyEasyUILoad('mainCenter');
	    			$.messager.alert('提示','成功');	
	    		}else{
	    			$.messager.alert('提示','失败');	
	    		}
	    	});
	    }
	});
}
