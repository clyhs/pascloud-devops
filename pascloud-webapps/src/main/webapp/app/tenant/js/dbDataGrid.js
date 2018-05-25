var checkConnArr =  new Array();


function initMainDataGrid(){
	EasyUILoad('mainDataGrid');
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
        	{ field: 'id', title: '编号', width: 40, align: 'left' ,hidden:'true' },
        	{ field: 'name', title: '名称', width: 40, align: 'left' ,hidden:'true'},
        	{ field: 'alianame', title: '名称', width: 40, align: 'left'},
        	{ field: 'desc', title: '租户名称', width:120, align: 'left' },
            { field: 'dbType', title: '类型', width: 40, align: 'left' },
            { field: 'url', title: '地址', width: 180, align: 'left' },
            { field: 'username', title: '用户', width: 40, align: 'center' },
            { field: 'password', title: '密码', width: 40, align: 'center' },
            { field: 'userCount', title: '行员数', width: 40, align: 'center' },
            { field: '_operate',title: '状态', width: 80, align: 'center',formatter:formatOper }
        ]],
        toolbar:toolbar,
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
        	dispalyEasyUILoad( 'mainDataGrid' );
        	for(var i=0;i<data.rows.length;i++)
            {
        		
        		checkConnArr[i]=setInterval("checkConnStatus('"+data.rows[i].id+"','"+data.rows[i].dbType+"','"+data.rows[i].url+"','"+data.rows[i].username+"','"+data.rows[i].password+"','"+i+"')",1000*20);
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
    var obj3=$tr.find("td[field='xmmc'] div");
	var params = {id:id,dbType:dbType,url:url,username:user,password:password};
	//alert(driverClassVal + passwordVal);
	$.get('connectDbWidthUrl.json',params,function(data,status){
		if(data.code == 10000){
			obj.html('<font color="blue">连接成功</font>'); 
			//obj2.html(data.bean);
			//obj3.html(data.desc);
			//alert(obj);
		}else{
			obj.html('<font color="red">连接失败</font>'); 
			//obj2.html('未知');
			//obj3.html('未知');
		}
	});
	
}


function sysHy(){
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	if(null == row){
		$.messager.alert('提示','请选择一个要同步的租户');
		return ;
	}
	
	var id = row.id;
	var dbType = row.dbType;
	var url = row.url;
	var user = row.username;
	var password = row.password;
	
	if(id == 'dn0'){
		$.messager.alert('提示','不能选择公共库，请重新选择');
	}else{
		var params = {id:id,dbType:dbType,url:url,username:user,password:password};
		//alert(driverClassVal + passwordVal);
		EasyUILoad('mainCenter');
		//alert(EasyUILoad('mainDataGrid'));
		$.get('syscHy.json',params,function(data,status){
			if(data.code == 10000){
				dispalyEasyUILoad( 'mainCenter' );
				$.messager.alert('提示','同步成功');
				//alert(obj);
			}else{
				dispalyEasyUILoad( 'mainCenter' );
				$.messager.alert('提示',data.desc);
			}
		});
	}
}

function closeConnCheck(){
	var len = checkConnArr.length;
	if(len>0){
		for(var i=0;i<len;i++){
			clearInterval(checkConnArr[i]);
		}
	}
}

function openOneTenant(){
	addSelectDbDialog();
}
