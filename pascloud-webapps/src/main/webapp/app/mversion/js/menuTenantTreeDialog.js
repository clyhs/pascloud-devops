var tenantTreeGrid;

function createTenantTreeDialog(toolbar){
	var div = '';
	
	div +='<div class="easyui-layout" data-options="fit:true">';  
    div +='<div data-options="region:\'center\'" >';
    div +='    <table id="tenantTreeGrid"></table>';
    div +='</div>';
    div +='</div>';
    
    createDialogDivWithSize('menuTreeGrid', 'tenantTreeDialog','租户应用', '',400,300,div);
    
    initTenantTreeGrid(toolbar);
}

var tenantTreeGridToolbar = function(){
	return [{
		text : '提交',  
        iconCls : 'icon-bullet_tick',  
        handler : function(){
        	
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