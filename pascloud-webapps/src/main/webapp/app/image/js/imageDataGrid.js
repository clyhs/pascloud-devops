
function initMainDataGrid(){
    
    $('#mainDataGrid').datagrid({
        height: 'auto',
        url: '/module/image/images.json',
        method: 'get',
        fit:true ,
        idField: 'id',
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
        	{ field: 'repoTags', title: '名称', width: 120, align: 'left' },
            { field: 'id', title: '编号', width: 80, align: 'left'  },
            
            //{ field: 'size', title: '大小', width: 80, align: 'left' },
            { field: 'virtualSizeM', title: '虚拟容量', width: 80, align: 'left' },
            { field: 'ip', title: 'IP地址', width: 80, align: 'center' }
        ]],
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
            //alert(addr);
            
        }
    });
}

