
function initMainDataGrid(Id){
    
    $('#mainDataGrid').datagrid({
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
            //{ field: 'ck', checkbox: true },
        	{ field: 'title', title: '名称', width: 120, align: 'left' },
            { field: 'funId', title: '编号', width: 80, align: 'left'  },
            
            { field: 'filename', title: 'xml文件', width: 80, align: 'left' ,formatter:formatXml },
            { field: 'type', title: '类型', width: 80, align: 'left' },
            { field: 'filepara', title: 'para文件', width: 80, align: 'center' ,formatter:formatPara},
            //{ field: 'suffix', title: '后缀名', width: 80, align: 'center' },
            { field: 'version', title: '版本号', width: 80, align: 'center' }
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
            //alert(addr);
           
        }
    });
}

function formatXml(val,row,index){ 
	return row.funId+".xml"; 
} 

function formatPara(val,row,index){ 
	return row.funId+".para"; 
} 

function modifyPasFiles(){
	//alert(driverClassVal + passwordVal);
	EasyUILoad('mainCenter');
	$.post('modifyPasfiles.json',{},function(data,status){
		if(data.code == 10000){
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示','修改成功');
			//alert(obj);
		}else{
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示','修改失败');
		}
	});
}

function reloadTableWithID(Id){
	$('#mainDataGrid').datagrid('clearSelections'); 
    $('#mainDataGrid').datagrid('load',{'dirId' : Id});
}


function searchByKey(){
	var key = $('#key').val();

	$('#mainDataGrid').datagrid('clearSelections'); 
    $('#mainDataGrid').datagrid('load',{'dirId' : dirId,key:key});
}


