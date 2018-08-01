function createPasfileDGDialog(Id,divId){
	var div = '';
	div +='<div class="easyui-layout" data-options="fit:true">';  
    div +='<div data-options="region:\'center\'">';
    div +='    <table id="pasfileDataGrid"></table>';
    div +='</div>';
    div +='</div>';
    
    createDialogDivWithSize(divId, 'selectPasfile','选择pasfile文件', '',650,500,div);
    
    initPasfileDG(Id);
}

var pastoolbar = function(){
	return [{
		 text: '关键字：<input type="text" id="key" value="" size="20" />'
	},{
		text : '搜索',  
        iconCls : 'icon-2012092109942', 
        plain:true,
        handler : function(){
        	
        	searchByKey();
        }
	},'-',{
		text : '提交',  
        iconCls : 'icon-bullet_tick', 
        plain:true,
        handler : function(){
        	var row = $('#pasfileDataGrid').datagrid('getSelected'); 
        	
        	if(null == row){
        		$.messager.alert('错误',"请选择一行");
        		return ;
        	}
        	
        	 $('#name').val(row.title);
        	 $('#version').val(row.version);
        	 $('#url').val(buildUrl(row.type,row.funId));
        	 
        	 $('#selectPasfile').dialog('close');
        	
        }
	}];
}();



function initPasfileDG(Id){
	$('#pasfileDataGrid').datagrid({
        height: 'auto',
        url: '/module/pasdev/pasfiles.json',
        method: 'get',
        fit:true ,
        idField: 'id',
        queryParams:{'dirId':Id},
        border:false,
        striped: true,
        fitColumns: true,
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        nowrap: false,
        pageSize: 20,
        pageList: [ 20, 50, 100],
        showFooter: true,
        columns: [[
            { field: 'ck', checkbox: true },
        	{ field: 'title', title: '名称', width: 140, align: 'left' },
            { field: 'funId', title: '编号', width: 50, align: 'left'  },
            { field: 'type', title: '类型', width: 40, align: 'left' },
            { field: 'version', title: '版本号', width: 40, align: 'center' }
        ]],
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
        	$("#pasfileDataGrid").parent().find(
			"div .datagrid-header-check").children(
			"input[type=\"checkbox\"]").eq(0).attr("style",
			"display:none;");
        },
        toolbar:pastoolbar,
        onLoadError: function () {
        
        },
        onClickCell: function (rowIndex, field, value) {
       
            //alert(name);
        },onDblClickCell: function (rowIndex, field, value) {
       
        }
	});
}

function buildUrl(type,funId){
	if(type == 'query'){
		return '/module/parser/query/visitHtml/'+funId+'.html';
	}else if(type == 'manage'){
		return '/module/parser/manage/visitHtml/'+funId+'.html';
	}else if(type == 'import'){
		return '/module/parser/import/visitHtml/'+funId+'.html';
	}else if(type == 'yjgx'){
		return '/module/parser/yjgx/visitHtml/'+funId+'.html';
	}
}

function searchByKey(){
	var key = $('#key').val();
	
	
	
	$('#pasfileDataGrid').datagrid('clearSelections'); 
    $('#pasfileDataGrid').datagrid('load',{'dirId' : 'dn0',key:key});
}