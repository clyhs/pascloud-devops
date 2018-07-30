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
		text : '确定',  
        iconCls : 'icon-add',  
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
            
        },
        toolbar:pastoolbar,
        onLoadError: function () {
        
        },
        onClickCell: function (rowIndex, field, value) {
            var rows = $('#mainDataGrid').datagrid('getRows');
            var row = rows[rowIndex];
            var name = row.name;
            //alert(name);
        },onDblClickCell: function (rowIndex, field, value) {
            var rows = $('#mainDataGrid').datagrid('getRows');
            var row = rows[rowIndex];
            var name = row.name;
            var addr = row.ip;
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