function initEastDataGrid(){
    
    $('#eastDataGrid').datagrid({
        height: 'auto',
        url: 'getDbInfo.json',
        method: 'get',
        fit:true ,
        idField: 'key',
        border:false,
        //queryParams: { 'gzxbh': gzxbh },
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
        toolbar:dbtoolbar,
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

function loadDBDataGrid(url,name,addr){
	
    $('#eastDataGrid').datagrid('clearSelections'); 
    $('#eastDataGrid').datagrid('options').url = url;
    $('#eastDataGrid').datagrid('load',{'name' : name,'addr' : addr});
}
