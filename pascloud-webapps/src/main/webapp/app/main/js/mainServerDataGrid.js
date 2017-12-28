function initServerDataGrid(index){
    
    $('#serverDataGrid_'+index).datagrid({
        height: 'auto',
        url: '/module/docker/getServerInfo.json',
        method: 'get',
        fit:true ,
        idField: 'key',
        border:false,
        queryParams: { 'index': index },
        striped: true,
        fitColumns: true,
        singleSelect: true,
        rownumbers: true,
        pagination: false,
        nowrap: false,
        showFooter: true,
        columns: [[
            { field: 'key', title: '属性名称', width: 50, align: 'left'  },
            { field: 'value', title: '属性值', width: 100, align: 'left'  }
            
        ]],
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
            
        },
        onLoadError: function () {
        
        },
        onClickCell: function (rowIndex, field, value) {
            
            //alert(csmc);
        }
    });
}

