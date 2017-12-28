
function initMainDataGrid(){
    
    $('#mainDataGrid').datagrid({
        height: '250px',
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
            { field: 'status', title: '状态', width: 100, align: 'left' },
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

function formatStyle(value,row,index){
	if(index == 2){
		//return '<span style="color:red;">('+value+')</span>';
		var val = value.substring(0,value.length-1)
		if(val>80){
			return '<span style="color:red;">'+value+'(你的CPU使用率已经高于警界点)</span>';
		}else{
			return value;
		}
		
	}else if(index == 5){
		var val = value.substring(0,value.length-1)
		if(val>80){
			return '<span style="color:red;">'+value+'(你的内存使用率已经高于警界点)</span>';
		}else{
			return value;
		}
	}
	else{
		return value;
	}
	
	
		
}
