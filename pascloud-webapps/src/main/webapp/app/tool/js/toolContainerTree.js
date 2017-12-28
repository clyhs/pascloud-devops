function initContainerTree(){
    EasyUILoad('containerTree');
    $('#containerTree').tree({
	    url:'containerTree.json',
	    cascadeCheck: false,
	    checkbox: true,
		method:'get',
		border:false,
		loadFilter:function(data){
		    dispalyEasyUILoad( 'containerTree' );	
		    //alert(data);
		    return data;
		},
		onContextMenu: onContextMenu,
		onClick: onClick,
		onSelect: function (node) {
             var checkedNode = $('#containerTree').tree('getChecked');
             if (checkedNode != null && checkedNode.length > 0) {
                 $.each(checkedNode, function (index, value) {
                     $('#containerTree').tree('uncheck', value.target);
                 })
             }
             if (node.checked) {
                 $('#containerTree').tree('uncheck', node.target);
             }
             else {
                 $('#containerTree').tree('check', node.target);
             }
         },
         onLoadSuccess: function (node, data) {
             $(this).find('span.tree-checkbox').unbind().click(function () {
                 $('#containerTree').tree('select', $(this).parent());
                 return false;
             });
         }
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
        alert(node.id);
    }
}