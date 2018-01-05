
function createOutputDataGrid(columns,url,param){
	$('#outputDataGrid').datagrid({
        height: 'auto',
        url: url,
        method: 'post',
        queryParams:param,
        fit:true ,
        border:false,
        striped: true,
        fitColumns: true,
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        nowrap: false,
        pageSize: 20,
        pageList: [20, 50, 100],
        showFooter: true,
        columns: columns,
        toolbar:toolbar,
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
        	msgEditor.setValue(data.desc);
        },
        onLoadError: function () {
        
        },
        onClickCell: function (rowIndex, field, value) {

        },onDblClickCell: function (rowIndex, field, value) {
            
        }
    });
}

function createColumns(rows){
	var columns = '';
	columns += '[[';
	
	var columnLen = rows.length;
	for(var i=0;i<columnLen;i++){
		if(i == columnLen-1){
			columns += "{field:'"+rows[i]+"',title:'"+rows[i]+"',width:120,align:'left'}";
		}else{
			columns += "{field:'"+rows[i]+"',title:'"+rows[i]+"',width:120,align:'left'},";
		}
	}
	columns += ']]';
	return eval(columns);
}