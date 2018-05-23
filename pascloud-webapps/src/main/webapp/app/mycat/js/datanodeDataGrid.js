
function initMainDataGrid(){
    
    $('#mainDataGrid').datagrid({
        height: 'auto',
        url: '/module/mycat/datanodes.json',
        method: 'get',
        fit:true ,
        idField: 'id',
        border:false,
        striped: true,
        fitColumns: true,
        singleSelect: true,
        selectOnCheck: true,
        checkOnSelect: true,
        rownumbers: true,
        pagination: false,
        nowrap: false,
        //pageSize: 20,
        //pageList: [ 20, 50, 100],
        showFooter: true,
        columns: [[
            { field: 'ck', checkbox: true },
        	{ field: 'name', title: '节点代号', width: 60, align: 'left' },
        	{ field: 'dataHost', title: '节点名称', width: 60, align: 'left' },
            { field: 'dbType', title: '数据库类型', width: 80, align: 'left'  },
            { field: 'url', title: '数据库连接', width: 150, align: 'left'  },
            { field: 'database', title: '数据库实例', width: 40, align: 'left' },
            { field: 'user', title: '用户', width: 40, align: 'left' },
            { field: 'password', title: '密码', width: 40, align: 'left' },
            { field: 'sysc', title: '是否配置到服务器', width: 60, align: 'left' }
        ]],
        toolbar:toolbar,
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
        	for(var i=0;i<data.rows.length;i++)
            {
                setInterval("checkSysc('"+data.rows[i].name+"','"+i+"')",1000*10);
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
            //alert(addr);
            
        }
    });
}

function checkSysc(name,index){
	var $tr=$(".datagrid-view2 .datagrid-btable tbody tr:eq("+index+")");
    var obj=$tr.find("td[field='sysc'] div");
	var params = {name:name};
	//alert(driverClassVal + passwordVal);
	$.get('getDataSourceByName.json',params,function(data,status){
		if(data.code == 10000){
			obj.html('<font color="blue">已配置到服务器</font>');
			//alert(obj);
		}else{
			obj.html('<font color="red">未配置到服务器</font>');
		}
	});
	
}


