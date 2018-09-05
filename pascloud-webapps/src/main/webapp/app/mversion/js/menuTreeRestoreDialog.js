var menuRestoreGrid;
var filename;

function createMenuRestoreDialog(toolbar) {
	var div = '';

	div += '<div class="easyui-layout" data-options="fit:true">';
	div += '<div data-options="region:\'center\',fit:true" >';
	div += '    <table id="menuRestoreTreeGrid"></table>';

	div += '</div>';
	div += '</div>';

	createDialogDivWithSize('menuTreeGrid', 'menuRestoreDialog', '备份文件', '',
			400, 300, div);

	initMenuRestoreGrid(toolbar);
}

var menuRestoreTreeGridToolbar = function() {
	return [ {
		text : '提交',
		iconCls : 'icon-accept',
		handler : function() {
			var row = $('#menuRestoreTreeGrid').treegrid('getSelected');
			if (null == row) {
				$('#menuRestoreDialog').dialog('close');
				$.messager.alert('提示', '请先选择一个节点!');
				return;
			}
			
			filename = row.text;
			restore();
		}
	} ];
}();

function initMenuRestoreGrid(toolbar) {
	menuRestoreGrid = $('#menuRestoreTreeGrid').treegrid(
			{
				url : 'getBackupfilelist.json',
				method : 'get',
				border : false,
				animate : true,// 是否用动画效果
				collapsible : false,// 是否可折叠
				rownumbers : false,// 行号
				fitColumns : true,
				fit : true,
				idField : 'id',// 根据那个字段判断树节点关系
				treeField : 'text',// 根据那个列展现树
				showFooter : false,// 是否使用页脚
				singleSelect : true,
				queryParams : {
					'Id' : dnId
				},
				columns : [ [
				{
					field : 'ck',
					checkbox : true
				}, {
					title : '文件名称',
					field : 'text',
					width : 200,
					sortable : true
				}

				] ],
				toolbar : toolbar,
				onLoadSuccess : function(node, data) {
					$("#menuRestoreTreeGrid").parent().find(
							"div .datagrid-header-check").children(
							"input[type=\"checkbox\"]").eq(0).attr("style",
							"display:none;");
				},
				onDblClickCell : function(rowIndex, field, value) {
				}
			});
}

function restore(){
	
	if(filename.length<=0){
		$.messager.alert('提示','选择一条记录');
    	return ;
	}
	
	var params = {Id:dnId,filename:filename};
	$('#menuRestoreDialog').dialog('close');
	$.messager.confirm('提示框','你确定恢复菜单，请再确定？',function(r){
	    if (r){
	    	EasyUILoad('mainCenter');
	    	$.post("restore.json",params,function(data,status){
				if(data.code == 10000){
					//alert(data.desc);
					reloadTreeGridWithID(dnId);
	    			$.messager.alert('提示',data.desc);
	    			dispalyEasyUILoad( 'mainCenter' );
				}else{
					$.messager.alert('提示',data.desc);
					//dispalyEasyUILoad( 'mainCenter' );
					dispalyEasyUILoad( 'mainCenter' );
				}
			});
	    }
	});
	
	
}