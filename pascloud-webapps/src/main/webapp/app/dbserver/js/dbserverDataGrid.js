var checkLsnrArr =  new Array();

function initMainDataGrid(){
	EasyUILoad('mainDataGrid');
    $('#mainDataGrid').datagrid({
        height: 'auto',
        url: 'getDBsWithServer.json',
        method: 'get',
        fit:true ,
        idField: 'id',
        queryParams:{'ip':defaultIp},
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
        	{ field: 'id', title: '编号', width: 40, align: 'left'  },
        	{ field: 'name', title: '名称', width: 40, align: 'left' },
        	{ field: 'url', title: '连接地址', width: 80, align: 'left' }//,
        	//{ field: 'lsnrctl', title: '监听状态', width: 60, align: 'left' }
        ]],
        toolbar:toolbar,
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
        	
        	for(var i=0;i<data.rows.length;i++)
            {
        		//checkLsnrArr[i]=setInterval("checkLsnrStatus('"+data.rows[i].id+"','"+i+"')",1000*40);
                //alert(data.rows[i].name);
            }
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

function reloadTableWithID(ip){
	$('#mainDataGrid').datagrid('clearSelections'); 
    $('#mainDataGrid').datagrid('load',{'ip' : ip});
}


function checkLsnrStatus(sid,index){
	var $tr=$(".datagrid-view2 .datagrid-btable tbody tr:eq("+index+")");
    var obj=$tr.find("td[field='lsnrctl'] div");
	var params = {sid:sid,ip:defaultIp};
	//alert(driverClassVal + passwordVal);
	$.get('checkLsnrctlStatus.json',params,function(data,status){
		if(data.code == 10000){
			
			obj.html('<font color="blue">已启动</font>'); 
		}else{
			obj.html('<font color="red">已关闭</font>'); 
		}
	});
	
}
