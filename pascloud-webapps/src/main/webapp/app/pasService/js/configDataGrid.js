function initMainDataGrid(){
    
    $('#mainDataGrid').datagrid({
        height: 'auto',
        url: '/module/config/config.json',
        method: 'get',
        fit:true ,
        border:false,
        striped: true,
        fitColumns: true,
        singleSelect: true,
        rownumbers: true,
        pagination: false,
        nowrap: false,
        showFooter: true,
        columns: [[
            //{ field: 'ck', checkbox: true },
            { field: 'key', title: '属性名称', width: 150, align: 'left'  },
            { field: 'value', title: '属性值', width: 200, align: 'left' },
            { field: 'desc', title: '描述', width: 200, align: 'left' }
        ]],
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
            
        },
        onLoadError: function () {
        
        },
        onClickCell: function (rowIndex, field, value) {
        },
        onDblClickCell: function (rowIndex, field, value) {
        }
    });
}


