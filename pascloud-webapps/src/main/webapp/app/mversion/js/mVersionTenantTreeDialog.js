var tenantTreeGrid;

function createTenantTreeDialog(toolbar){
	var div = '';
	
	div +='<div class="easyui-layout" data-options="fit:true">';  
    div +='<div data-options="region:\'center\'" >';
    div +='    <table id="tenantTreeGrid"></table>';
    div +='</div>';
    div +='</div>';
    
    createDialogDivWithSize('mVersionMainTreeGrid', 'tenantTreeDialog','租户应用', '',400,300,div);
    
    initTenantTreeGrid(toolbar);
}

var tenantTreeGridToolbar = function(){
	return [{
		text : '提交',  
        iconCls : 'icon-bullet_tick',  
        handler : function(){
        	setTenantXtcd();
        }
	}];
}();

var tenantTreeGridToolbarForSys = function(){
	return [{
		text : '提交',  
        iconCls : 'icon-bullet_tick',  
        handler : function(){
        	sysAllXtcdToTenant();
        }
	}];
}();

function initTenantTreeGrid(toolbar){
	tenantTreeGrid = $('#tenantTreeGrid').treegrid({
		url:'getMVersionTree.json',
		method:'get',
		border:false,
		animate:true,//是否用动画效果
        collapsible:false,//是否可折叠
        rownumbers:true,//行号
        fitColumns:true,
        idField:'id',//根据那个字段判断树节点关系
        treeField:'text',//根据那个列展现树
        showFooter:false,//是否使用页脚
        singleSelect : false,
		columns:[[
            //{title:'编号',field:'id',width:40,sortable:true},
			{ field: 'ck',checkbox:true },
            {title:'应用名称',field:'text',width:200,sortable:true}

        ]],
        toolbar:toolbar,
		onLoadSuccess:function(node, data){
			
		},
		onDblClickCell:function(rowIndex, field, value){
		}
	});
}

function setTenantXtcd(){
	var rows = tenantTreeGrid.treegrid('getSelections');
	//alert(rows.length);
	var len = rows.length;
	if (len <= 0) {
        $.messager.alert('警告', '您没有选择');
        return;
    } 
	var tIds = "";
	for(var i=0;i<len;i++){
		 //alert(rows[i].id);
		if(i==len-1){
			tIds+=rows[i].id;
		}else{
			tIds+=rows[i].id+",";
		}
	}
	
	var row = $('#mVersionMainTreeGrid').treegrid('getSelected');
	if(null == row){
		$('#tenantTreeDialog').dialog('close');
		$.messager.alert('提示','请先选择一个节点!');
		return ;
	}
	
	var xmdh = "";
	xmdh=row.id;
	
	var param = {Id:'dn0',tIds:tIds,xmdh:xmdh};
	$('#tenantTreeDialog').dialog('close');
	EasyUILoad('mainCenter');
	$.post("sysOneMenuToTenant.json",param,function(data,status){
			
		if(data.code == 10000){
			reloadTree();
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示','成功');
		}else{
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示',data.desc);
		}
			
	});
}

function sysAllXtcdToTenant(){
	var rows = tenantTreeGrid.treegrid('getSelections');
	//alert(rows.length);
	var len = rows.length;
	if (len <= 0) {
        $.messager.alert('警告', '您没有选择');
        return;
    } 
	var tIds = "";
	for(var i=0;i<len;i++){
		 //alert(rows[i].id);
		if(i==len-1){
			tIds+=rows[i].id;
		}else{
			tIds+=rows[i].id+",";
		}
	}
	
	var param = {tIds:tIds};
	$('#tenantTreeDialog').dialog('close');
	EasyUILoad('mainCenter');
	$.post("sysAllMenuToTenant.json",param,function(data,status){
			
		if(data.code == 10000){
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示','成功');
		}else{
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示',data.desc);
		}
			
	});
}