
function initMainDataGrid(){
    
    $('#mainDataGrid').datagrid({
        height: 'auto',
        url: '/module/tenant/dbs.json',
        method: 'get',
        fit:true ,
        idField: 'id',
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
        	{ field: 'name', title: '名称', width: 40, align: 'left' },
            { field: 'id', title: '编号', width: 40, align: 'left'  },
            { field: 'dbType', title: '类型', width: 40, align: 'left' },
            { field: 'url', title: '地址', width: 150, align: 'left' },
            { field: 'username', title: '用户', width: 80, align: 'center' },
            { field: 'password', title: '密码', width: 80, align: 'center' },
            { field: 'desc', title: '描述', width: 80, align: 'center' },
            { field: 'userCount', title: '行员数', width: 40, align: 'center' },
            { field: '_operate',title: '状态', width: 80, align: 'center',formatter:formatOper }
        ]],
        toolbar:toolbar,
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
        	
        	for(var i=0;i<data.rows.length;i++)
            {
        		
                setInterval("checkConnStatus('"+data.rows[i].id+"','"+data.rows[i].dbType+"','"+data.rows[i].url+"','"+data.rows[i].username+"','"+data.rows[i].password+"','"+i+"')",1000*10);
                //alert(data.rows[i].name);
            }
        	//data = data;
        	
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

function formatOper(val,row,index){ 
    return 'unknown';  
} 

function checkConnStatus(id, dbType, url,user,password,index){
	var $tr=$(".datagrid-view2 .datagrid-btable tbody tr:eq("+index+")");
    var obj=$tr.find("td[field='_operate'] div");
    var obj2=$tr.find("td[field='userCount'] div");
	var params = {id:id,dbType:dbType,url:url,username:user,password:password};
	//alert(driverClassVal + passwordVal);
	$.get('connectDbWidthUrl.json',params,function(data,status){
		if(data.code == 10000){
			obj.html('<font color="blue">连接成功</font>'); 
			obj2.html(data.bean);
			//alert(obj);
		}else{
			obj.html('<font color="red">连接失败</font>'); 
			obj2.html('未知');
		}
	});
	
}
