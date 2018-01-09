function initDatabaseTableTrees(){
    //EasyUILoad('databaseTableTrees');
    $('#databaseTableTrees').tree({
	    url:'tableTrees.json',
	    cascadeCheck: false,
	    checkbox: false,
		method:'get',
		border:false,
		loadFilter:function(data){
		    //dispalyEasyUILoad( 'databaseTableTrees' );	
		    dispalyEasyUILoad( 'databaseTableDiv' );
		    //alert(data);
		    return data;
		},
		onContextMenu: onTableContextMenu,
		onClick: onTableClick,
		onDblClick: onTableDblClick,
		onSelect: function (node) {
        },
        onLoadSuccess: function (node, data) {   
        }
	});
}

/**右键菜单**/
function onTableContextMenu(e,node){
    e.preventDefault();
	$(this).tree('select',node.target);
	if($(this).tree('isLeaf', node.target)){
	    $('#mm-databaseTable').menu('show',{
		    left: e.pageX,
		    top: e.pageY
	    });
	}
}

function tableTreeReload(id){
	EasyUILoadSimple('databaseTableDiv');
    $('#databaseTableTrees').tree("options").url="tableTrees.json?dsId="+id;
    $('#databaseTableTrees').tree("reload");
    	
}

function onTableDblClick(node){
	if($(this).tree('isLeaf', node.target)){
        var title = node.text;
        var url = node.url;
        var icon = node.iconCls;
        title = title +"("+dsName+")";
        addDBTab(title,url,icon);
    }
}

/**单击事件**/
function onTableClick(node){
    
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

function editTable(){
	//alert('edit');
	var node = $('#databaseTableTrees').tree('getSelected');
	//alert(node.text);
	var tablename = node.text;
	var url = '/module/database/tableEdit.html?tablename='+tablename+'&dsId='+dsId;
	var title = tablename+'_'+dsId;
	var icon = 'icon-table_edit';
	addDBTab(title,url,icon);
}

function openTable(){
	var node = $('#databaseTableTrees').tree('getSelected');
	var title = node.text;
    var url = node.url;
    var icon = node.iconCls;
    title = title +"("+dsName+")";
    addDBTab(title,url,icon);
}

function addTable(){
	
}

function exportTable(){
	
}

function truncateTable(){
	var node = $('#databaseTableTrees').tree('getSelected');
	var tablename = node.text;
	$.messager.confirm('?','确定要提交吗?',function(r){
		if(r){
			var sql = "truncate table "+tablename;
			var param = {sql:sql,dsId:dsId};
			$.post("execSql.json",param,function(data,status){
				if(data.code = 10000){
					$.messager.show({
				         title: '提示',
				         msg: '成功！',
				         showType: 'fade',      
				         style: { left: 500, top: 100 },     
				         width:200, 
				         height:100,
				         timeout: 1000 
				    });
				}
			});
		}
	});
}

function dropTable(){
	
}

function infoTable(){
	
}