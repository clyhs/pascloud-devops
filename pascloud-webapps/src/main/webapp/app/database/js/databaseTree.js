function initDatabaseTree(){
    EasyUILoad('databaseTree');
    $('#databaseTree').tree({
	    url:'dbTrees.json',
	    cascadeCheck: false,
	    checkbox: false,
		method:'get',
		border:false,
		loadFilter:function(data){
		    dispalyEasyUILoad( 'databaseTree' );	
		    //alert(data);
		    return data;
		},
		//onContextMenu: onContextMenu,
		onClick: onDBClick,
		onDblClick:onDblClick,
		onSelect: function (node) {
        },
        onLoadSuccess: function (node, data) {   
        }
	});
}

/**右键菜单**/
function onContextMenu(e,node){
    e.preventDefault();
	$(this).tree('select',node.target);
	if($(this).tree('isLeaf', node.target)){
	    $('#mm-database').menu('show',{
		    left: e.pageX,
		    top: e.pageY
	    });
	}
}
/**单击事件**/
function onDBClick(node){
   
}

function onDblClick(node){
	if($(this).tree('isLeaf', node.target)){
        var title = node.text;
        var url = node.url;
        //console.log(node);
        dsName = title;
        dsId = node.id;
        tableTreeReload(dsId);
        $('#mainLeft').tabs('select', '数据表');
    }
}


