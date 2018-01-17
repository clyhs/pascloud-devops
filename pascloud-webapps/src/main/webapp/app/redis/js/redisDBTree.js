function initRedisTree(){
    EasyUILoad('redisTree');
    $('#redisTree').treegrid({
	    url:'redisTrees.json',
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
        toolbar:'#redisTreeToolbar',
		//onContextMenu: onContextMenu,
		//onClick: onDBClick,
		onLoadSuccess:function(){
			dispalyEasyUILoad( 'redisTree' );
		},
		onDblClickCell:function(rowIndex, field, value){
			var node = $('#redisTree').treegrid('getSelected');
			//alert(node.id+" "+node.text);
			var title = node.text;
			var index= node.id;
			var icon = "";
			var url = "table.html?redisServerId="+redisServerId+"&index="+index;
			addRedisDBTab(title, url,icon)
		}
	});
}


function addRedisDBTab(title, url,icon){
	if ($('#mainCenterTab').tabs('exists', title)){
		$('#mainCenterTab').tabs('select', title);
	} else {
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		$('#mainCenterTab').tabs('add',{
			title:title,
			content:content,
			iconCls:icon,
			closable:true
		});
	}
}


function changeRedisServer(){
	
	$('#server').combobox({  
        onChange: function (newValue, oldValue) {  
        	redisServerId = newValue;
        	$('#redisServerDetailDataGrid').datagrid('clearSelections'); 
            $('#redisServerDetailDataGrid').datagrid('options').url ='/module/redis/redisServerInfo.json';
            $('#redisServerDetailDataGrid').datagrid('load',{'redisServerId' : redisServerId});
        }  
    });
}
