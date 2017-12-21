
function initMainDataGrid(){
    
    $('#mainDataGrid').datagrid({
        height: 'auto',
        url: '/module/pas/containers.json',
        method: 'get',
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
            { field: 'id', title: '编号', width: 260, align: 'left'  },
            { field: 'name', title: '名称', width: 100, align: 'left' },
            { field: 'state', title: '状态', width: 40, align: 'left' },
            { field: 'publicPort', title: '映射端口', width: 180, align: 'left' },
            { field: 'ip', title: 'IP地址', width: 60, align: 'right' }
        ]],
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
            
        },
        onLoadError: function () {
        
        }
    });
}