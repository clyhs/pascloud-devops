var dbtoolbar = function(){
	return [{
		text : '连接测试',  
        iconCls : 'icon-table_edit',  
        handler : function(){
        	EasyUILoad('infoLayout')
        	var rows = $('#eastDataGrid').datagrid('getRows');
        	var driverClassVal = rows[0].value;
        	var urlVal = rows[1].value;
        	var usernameVal = rows[2].value;
        	var passwordVal = rows[3].value;
        	
        	//alert(driverClassVal + passwordVal);
        	$.get('connectDb.json',{driverClass:driverClassVal,url:urlVal,username:usernameVal,password:passwordVal},function(data,status){
        		dispalyEasyUILoad('infoLayout')
        		if(data.code == 10000){
        			alert("连接成功");
        		}
        	});
        }
	}];
}();