var tenantTreeGrid;
var tenantMenuTreeGrid;
var selectId = "";
function createTenantTreeDialog(toolbar) {
	var div = '';

	div += '<div class="easyui-layout" data-options="fit:true">';
	div += '<div data-options="region:\'center\',fit:true" >';
	div += '    <table id="tenantTreeGrid"></table>';

	div += '</div>';
	div += '</div>';

	createDialogDivWithSize('menuTreeGrid', 'tenantTreeDialog', '租户应用', '',
			400, 300, div);

	initTenantTreeGrid(toolbar);
}

var tenantTreeGridToolbar = function() {
	return [ {
		text : '下一步',
		iconCls : 'icon-accept',
		handler : function() {
			var row = $('#tenantTreeGrid').treegrid('getSelected');
			if (null == row) {
				$('#tenantTreeDialog').dialog('close');
				$.messager.alert('提示', '请先选择一个节点!');
				return;
			}

			selectId = row.id;
			createMenuTreeGridDialog(menuTreeGridToolbar)
		}
	} ];
}();

function initTenantTreeGrid(toolbar) {
	tenantTreeGrid = $('#tenantTreeGrid').treegrid(
			{
				url : 'getMVersionTreeExcludeId.json',
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
				// {title:'编号',field:'id',width:40,sortable:true},
				{
					field : 'ck',
					checkbox : true
				}, {
					title : '应用名称',
					field : 'text',
					width : 200,
					sortable : true
				}

				] ],
				toolbar : toolbar,
				onLoadSuccess : function(node, data) {
					$("#tenantTreeGrid").parent().find(
							"div .datagrid-header-check").children(
							"input[type=\"checkbox\"]").eq(0).attr("style",
							"display:none;");
				},
				onDblClickCell : function(rowIndex, field, value) {
				}
			});
}

function createMenuTreeGridDialog(toolbar) {
	var div = '';

	div += '<div class="easyui-layout" data-options="fit:true">';
	div += '<div data-options="region:\'center\',fit:true" >';
	div += '    <table id="tenantMenuTreeGridDialog"></table>';

	div += '</div>';
	div += '</div>';

	createDialogDivWithSize('menuTreeGrid', 'selectMenuDialog', '菜单资源', '',
			600, 400, div);

	initMenuTreeGridDialog(toolbar);
}

var menuTreeGridToolbar = function() {
	return [ {
		text : '提交',
		iconCls : 'icon-accept',
		handler : function() {
			getSelected();
		}
	} ];
}();

function initMenuTreeGridDialog(toolbar) {
	tenantMenuTreeGrid = $('#tenantMenuTreeGridDialog').treegrid(
			{
				url : 'getMVersionMenuTreeWith.json',
				method : 'get',
				border : false,
				width : "auto",
				fit : true,
				queryParams : {
					'selectId' : selectId,
					'dnId':dnId
				},
				animate : true,// 是否用动画效果
				collapsible : false,// 是否可折叠
				rownumbers : false,// 行号
				fitColumns : true,
				singleSelect : false,
				idField : 'id',// 根据那个字段判断树节点关系
				treeField : 'text',// 根据那个列展现树
				showFooter : false,// 是否使用页脚
				columns : [ [
						{title:'编号',field:'id',width:40,sortable:true,hidden:false},
						{
							title : '菜单名称',
							field : 'text',
							width : 200,
							sortable : true,
							formatter : function(value, rowData, rowIndex) {
								return "<input type='checkbox' onclick=show('"+rowData.id+"') id='cd_"
										+ rowData.id + "' "
										+ (rowData.checked ? 'checked' : '')
										+ "/>" + rowData.text;
							}
						}, {
							title : '版本',
							field : 'version',
							width : 40,
							sortable : true
						}, {
							title : '地址',
							field : 'url',
							width : 160,
							sortable : true
						} ] ],
				toolbar : toolbar,
				// onContextMenu: onContextMenu,
				//onClickRow : onClickRow,
				onLoadSuccess : function(node, data) {
				},
				onDblClickCell : function(rowIndex, field, value) {
					var node = $('#tenantMenuTreeGridDialog').treegrid(
							'getSelected');
					var title = node.id;
					var index = node.id;
					var icon = "";
					// alert(title);
				}
			});

}

function show(checkid) {
	var s = '#cd_' + checkid;
	// alert( $(s).attr("id"));
	// alert($(s)[0].checked);
	/* 选子节点 */
	var nodes = $("#tenantMenuTreeGridDialog").treegrid("getChildren", checkid);
	for (i = 0; i < nodes.length; i++) {
		$(('#cd_' + nodes[i].id))[0].checked = $(s)[0].checked;

	}
	// 选上级节点
	if (!$(s)[0].checked) {
		var parent = $("#tenantMenuTreeGridDialog").treegrid("getParent", checkid);

		var flag = false;
		
		if(parent){
			var sons = parent.children;
			for (j = 0; j < sons.length; j++) {
				if ($(('#cd_' + sons[j].id))[0].checked) {
					flag = true;
					break;
				}
			}
			if(!flag){
				$(('#cd_' + parent.id))[0].checked = false;
			}
			
			while (!flag) {
				parent = $("#tenantMenuTreeGridDialog").treegrid("getParent", parent.id);
				if (parent) {
					//sons = parent.sondata.split(',');
					sons = parent.children;
					for (j = 0; j < sons.length; j++) {
						if ($(('#cd_' + sons[j].id))[0].checked) {
							flag = true;
							break;
						}
					}
				}
				
				if (!flag){
					$(('#cd_' + parent.id))[0].checked = false;
				}
					
			}
		}
		
	} else {
		
		var parent = $("#tenantMenuTreeGridDialog").treegrid("getParent", checkid);

		
		if(parent){
			$(('#cd_' + parent.id))[0].checked = true;
		}
		
		while (parent) {
			parent = $("#tenantMenuTreeGridDialog").treegrid("getParent", parent.id);
			if(parent){
				$(('#cd_' + parent.id))[0].checked = true;
			}
			
		}
		
	}
}


function getSelected(){ 
    var idList = "";  
    $("input:checked").each(function(){
        var id = $(this).attr("id"); 
        if(id!=undefined){
        	if(id.indexOf("cd_")>-1){
        		idList += id.replace("cd_",'')+',';
        	}
        }    
    });
    if(idList == ""){
    	$.messager.alert('提示','至少选择一个节点！');
    	return ;
    }else{
    	idList = idList.substring(0,idList.length-1);
    }
    
    //alert(idList);
    
    var param = {selectId:selectId,dnId:dnId,idList:idList};
	$('#selectMenuDialog').dialog('close');
	$('#tenantTreeDialog').dialog('close');
	EasyUILoad('mainCenter');
	$.post("sysOtherMenuToTenant.json",param,function(data,status){
			
		if(data.code == 10000){
			reloadTreeGridWithID(dnId);
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示','成功');
		}else{
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示',data.desc);
		}
			
	});
}