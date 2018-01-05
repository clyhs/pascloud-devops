function initDatabaseTableTrees(){
    EasyUILoad('databaseTableTrees');
    $('#databaseTableTrees').tree({
	    url:'tableTrees.json',
	    cascadeCheck: false,
	    checkbox: false,
		method:'get',
		border:false,
		loadFilter:function(data){
		    dispalyEasyUILoad( 'databaseTableTrees' );	
		    //alert(data);
		    return data;
		},
		//onContextMenu: onContextMenu,
		onClick: onTableClick,
		onSelect: function (node) {
        },
        onLoadSuccess: function (node, data) {   
        }
	});
}

function tableTreeReload(id){
	EasyUILoad('databaseTableTrees');
    $('#databaseTableTrees').tree("options").url="tableTrees.json?dsId="+id;
    $('#databaseTableTrees').tree("reload");
    dispalyEasyUILoad( 'databaseTableTrees' );	
}

/**单击事件**/
function onTableClick(node){
    if($(this).tree('isLeaf', node.target)){
        var title = node.text;
        var url = node.url;
        var icon = node.iconCls;
        addDBTab(title,url,icon);
    }
}



function addDBTab(title, url,icon){
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
