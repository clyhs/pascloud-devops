function initDatabaseTree(){
    EasyUILoad('databaseTree');
    $('#databaseTree').tree({
	    url:'trees.json',
	    cascadeCheck: false,
	    checkbox: false,
		method:'get',
		border:false,
		loadFilter:function(data){
		    dispalyEasyUILoad( 'databaseTree' );	
		    //alert(data);
		    return data;
		},
		onContextMenu: onContextMenu,
		onClick: onClick,
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
function onClick(node){
    if($(this).tree('isLeaf', node.target)){
        var title = node.text;
        var url = node.url;
        //console.log(node);
        alert(node.id);
    }
}