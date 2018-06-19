function addServerDialog(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="ip" class="formlabel">IP地址:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="ip" name="ip" data-options="required:true" value="" size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="user" class="formlabel">用户名:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="user" name="user" data-options="required:true" size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="password" class="formlabel">密码:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="password" name="password" data-options="required:true" size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="port" class="formlabel">ssh端口:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="port" name="port" data-options="required:true" size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="typeEnum" class="formlabel">服务器类型:</label>';
	div +=    '<select id="typeEnum" class="easyui-validatebox formInput" name="typeEnum" >';
	div +=      '<option value="1">应用服务器</option>';
	div +=      '<option value="2">数据库服务器</option>';
	div +=    '</select>';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	
	div += createServerFormFooter(); 
	createDialogDivWithSize('mainDataGrid', 'addServer','添加服务器节点', '',500,250,div);
}

function createServerFormFooter(){

	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:10px;">';
	
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:20%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="test()" >测试</a>'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:20%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="addServer()" >确定</a>'; 
	html +=   '</div>';
	html +=   '<div style="border:#ccc 0px solid;float:left;width:35%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'">重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
}

function addServer(){
	var ip = $('#ip').val();
	var user = $('#user').val();
	var password = $('#password').val();
	var port = $('#port').val();
	var typeEnum = $('#typeEnum').val();
	
	if(ip.length == 0 || user.length == 0 || password.length == 0 || port.length == 0 || typeEnum.length == 0){
		$.messager.alert('提示','参数没有填写完整');
		return ;
	}
	
	var regIP= /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
	if(!regIP.test(ip)){
		$.messager.alert('提示','ip格式必须为x.x.x.x x为数字');
		return ;
	}
	
	var regPort=/^[0-9]{1,6}$/;
	if(!regPort.test(port)){
		$.messager.alert('提示','端口的格式必须为4-6位数字');
		return ;
	}
	var params={ip:ip,user:user,password:password,port:port,typeEnum:typeEnum};
	EasyUILoad('mainCenter');
	$('#addServer').dialog('close');
	$.post("addServer.json",params,function(data,status){
		if(data.code == 10000){
			//alert(data.desc);
			//alert("修改成功，请重新启动应用");
			$('#mainDataGrid').datagrid('reload');//刷新
			dispalyEasyUILoad('mainCenter');
		}else{
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);
		}
	});	
	
	
}

function delServer(){
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	var ip = row.ip;
	
	var params={ip:ip};
	EasyUILoad('mainCenter');
	$('#addServer').dialog('close');
	$.post("delServer.json",params,function(data,status){
		if(data.code == 10000){
			//alert(data.desc);
			//alert("修改成功，请重新启动应用");
			$('#mainDataGrid').datagrid('reload');//刷新
			dispalyEasyUILoad('mainCenter');
		}else{
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);
		}
	});	
	
	
}

