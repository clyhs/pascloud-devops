
function initMainDataGrid(){
    
    $('#mainDataGrid').datagrid({
        height: 'auto',
        url: '/module/container/containers.json',
        method: 'get',
        queryParams: { 'str': 'pascloud' },
        fit:true ,
        idField: 'id',
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
            //{ field: 'ck', checkbox: true },
            { field: 'id', title: '编号', width: 150, align: 'left'  },
            { field: 'name', title: '名称', width: 120, align: 'left' },
            { field: 'state', title: '状态', width: 40, align: 'left' },
            { field: 'publicPort', title: '映射端口', width: 100, align: 'left' },
            { field: 'ip', title: 'IP地址', width: 80, align: 'center' }//,
            //{ field: '_operate',title: '操作', width:50, align: 'center',formatter:formatOper }
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

function formatOper(val,row,index){ 
	
    return ' <a href="#" onclick="onSetting('+index+')">设置</a>';  
} 


function onSetting(index){
	$('#mainDataGrid').datagrid('selectRow',index);
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	
}
