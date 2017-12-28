var treeRow = [];

function initTreeForLeftMenu(){
    EasyUILoad('leftMenu');
    $('#leftMenu').tree({
	    url:'/module/main/trees.json',
		method:'get',
		border:false,
		loadFilter:function(data){
		    dispalyEasyUILoad( 'leftMenu' );	
		    //alert(data);
		    treeRow = data;
		    return data;
		},
		onContextMenu: onContextMenu,
		onClick: onClick
	});
}
/**右键菜单**/
function onContextMenu(e,node){
    e.preventDefault();
	$(this).tree('select',node.target);
	if($(this).tree('isLeaf', node.target)){
	    $('#mm').menu('show',{
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
        //alert(node.url);
        addTab(title,url);
    }
}



function addTab(title, url){
	if ($('#mainCenter').tabs('exists', title)){
		$('#mainCenter').tabs('select', title);
	} else {
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		$('#mainCenter').tabs('add',{
			title:title,
			content:content,
			closable:true
		});
	}
}