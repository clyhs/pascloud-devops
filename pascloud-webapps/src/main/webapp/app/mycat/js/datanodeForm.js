
function addDbDialog(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="name" class="formlabel">节点代号:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="name" name="name" data-options="required:true" value="" size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="dbType" class="formlabel">数据库类型:</label>';
	div +=    '<select id="dbType" class="easyui-validatebox formInput" name="dbType" >';
	div +=      '<option value="db2">db2</option>';
	div +=      '<option value="oracle">oracle</option>';
	div +=      '<option value="mysql">mysql</option>';
	div +=    '</select>';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	/*
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="driverClass" class="formlabel">driverClass:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="driverClass" name="driverClass" data-options="required:true" value="com.ibm.db2.jcc.DB2Driver" size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	*/
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="database" class="formlabel">数据库:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="database" name="database" data-options="required:true" size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="ip" class="formlabel">数据库IP地址:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="ip" name="ip" data-options="required:true" size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="port" class="formlabel">端口:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="port" name="port" data-options="required:true" size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="username" class="formlabel">用户名:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="username" name="username" data-options="required:true" value="root"  size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="password" class="formlabel">密码:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="password" name="password" data-options="required:true" size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	div += createDBFormFooter(); 
	createDialogDivWithSize('mainDataGrid', 'pasAddDb','添加数据库节点', '',500,360,div);
}

function createDBFormFooter(){

	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:10px;">';
	
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:20%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="test()" >测试</a>'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:20%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="addDB()" >确定</a>'; 
	html +=   '</div>';
	html +=   '<div style="border:#ccc 0px solid;float:left;width:35%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'">重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
}




function addDB(){
	//alert('设置数据库');
	var dbType = $("#dbType").val();
	var port = $("#port").val();
	var database = $("#database").val();
	var ip = $("#ip").val();
	var user = $("#username").val();
	var password = $("#password").val();
	var params = {dbType:dbType,ip:ip,port:port,database:database,username:user,password:password};
	
	//alert(driverClassVal);
	/*
	$.post("modifyContainerSpringXml.json",param,function(data,status){
		if(data.code = 10000){
			//alert(data.desc);
			alert("修改成功，请重新启动应用");
			$('#pasAddDb').dialog('close');
		}
		
	});*/
}


function test(){
	var dbType = $("#dbType").val();
	var port = $("#port").val();
	var database = $("#database").val();
	var ip = $("#ip").val();
	var user = $("#username").val();
	var password = $("#password").val();
	var params = {dbType:dbType,ip:ip,port:port,database:database,username:user,password:password};
	//alert(driverClassVal + passwordVal);
	$.get('/module/database/connectDb.json',params,function(data,status){
		dispalyEasyUILoad('infoLayout')
		if(data.code == 10000){
			
			alert("连接成功");
		}
	});
}

function delDatanode(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	var name = row.name;
	alert(name);
	
}


