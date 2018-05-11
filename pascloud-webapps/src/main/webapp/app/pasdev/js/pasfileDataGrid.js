
function initMainDataGrid(){
    
    $('#mainDataGrid').datagrid({
        height: 'auto',
        url: '/module/pasdev/pasfiles.json',
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
        	{ field: 'title', title: '名称', width: 120, align: 'left' },
            { field: 'funId', title: '编号', width: 80, align: 'left'  },
            
            { field: 'filename', title: '文件名称', width: 80, align: 'left' },
            { field: 'type', title: '类型', width: 80, align: 'left' },
            { field: 'filepara', title: 'para文件', width: 80, align: 'center' },
            //{ field: 'suffix', title: '后缀名', width: 80, align: 'center' },
            { field: 'version', title: '版本号', width: 80, align: 'center' }
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

