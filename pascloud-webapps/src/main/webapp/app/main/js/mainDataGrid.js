
function initMainDataGrid(){
    
    $('#mainDataGrid').datagrid({
        height: 'auto',
        url: '/module/docker/getnodes.json',
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
            { field: 'id', title: '编号', width: 200, align: 'left'  },
            { field: 'hostname', title: '服务器名称', width: 150, align: 'left' },
            { field: 'role', title: '节点角色', width: 100, align: 'left' },
            { field: 'addr', title: 'IP地址', width: 180, align: 'left' },
            { field: 'memory', title: '内存大小', width: 80, align: 'right' }
        ]],
        toolbar:toolbar,
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
            
        },
        onLoadError: function () {
        
        }
    });
}
