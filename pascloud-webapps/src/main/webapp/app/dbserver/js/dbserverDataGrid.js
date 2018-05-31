function initMainDataGrid(){
	EasyUILoad('mainDataGrid');
    $('#mainDataGrid').datagrid({
        height: 'auto',
        url: 'getDBsWithServer.json',
        method: 'get',
        fit:true ,
        idField: 'id',
        queryParams:{'ip':defaultIp},
        border:false,
        striped: true,
        fitColumns: true,
        singleSelect: true,
        rownumbers: true,
        pagination: false,
        nowrap: false,
        //pageSize: 20,
        //pageList: [ 20, 50, 100],
        showFooter: true,
        columns: [[
            //{ field: 'ck', checkbox: true },
        	{ field: 'id', title: '编号', width: 40, align: 'left'  },
        	{ field: 'name', title: '名称', width: 40, align: 'left' }
        ]],
        toolbar:toolbar,
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
        	
        },
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

function reloadTableWithID(ip){
	$('#mainDataGrid').datagrid('clearSelections'); 
    $('#mainDataGrid').datagrid('load',{'ip' : ip});
}

