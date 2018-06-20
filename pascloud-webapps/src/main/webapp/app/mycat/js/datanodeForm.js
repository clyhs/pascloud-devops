
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
	createDialogDivWithSize('mainDataGrid', 'datanodeAddDb','添加数据库节点', '',500,360,div);
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
	var name = $("#name").val();
	var dbType = $("#dbType").val();
	var port = $("#port").val();
	var database = $("#database").val();
	var ip = $("#ip").val();
	var user = $("#username").val();
	var password = $("#password").val();
	
	var params = {name:name,dbType:dbType,ip:ip,port:port,database:database,username:user,password:password};
	//alert(name.length);
	if(name.length<=0 || dbType.length<=0 || port.length<=0 || database.length<=0 || ip.length<=0 || user.length<=0 || password.length<=0){
		$.messager.alert('提示','参数没有填写完整');
		return ;
	}
	
	var regName=/^dn[1-9]{1}[0-9]{0,2}$/;
	if(!regName.test(name)){
		$.messager.alert('提示','节点代号的格式必须为dn1,dn2,dn10,dn99...等');
		return ;
	}
	
	var regPort=/^[0-9]{4,6}$/;
	if(!regPort.test(port)){
		$.messager.alert('提示','端口的格式必须为4-6位数字');
		return ;
	}
	
	var regDatabase=/^[a-zA-Z0-9]+$/;
	if(!regDatabase.test(database)){
		$.messager.alert('提示','端口的格式必须为4-6位数字');
		return ;
	}
	
	var regIP= /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
	if(!regIP.test(ip)){
		$.messager.alert('提示','ip格式必须为x.x.x.x x为数字');
		return ;
	}
	
	
	
	
	EasyUILoad('mainCenter');
	$('#datanodeAddDb').dialog('close');
	$.post("addDatanode.json",params,function(data,status){
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

        if(data.code == 10000){
			$.messager.alert('提示','连接成功');
		}else{
			$.messager.alert('提示','连接失败');
		}
	});
}

function delDatanode(){
	//alert('设置数据库');
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	var name = row.name;
	var params = {name:name};
	
	if(null==row){
		$.messager.alert('提示','请选择要删除的一条');
		return ;
	}else{
		
		if(name == 'dn0'){
			$.messager.alert('提示','dn0不能删除，请重新选择');
			return ;
		}else{
			EasyUILoad('mainCenter');
			$.messager.confirm('提示框','你确定要删除些节点，会影响到云平台的租户，请再确定？',function(r){
			    if (r){
			    	$.post("delDatanode.json",params,function(data,status){
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
			});
		}
		
		
	}
	
	
	
}

function uploadConfig(){
	EasyUILoad('mainCenter');
	$.post("uploadConfig.json",{},function(data,status){
		if(data.code = 10000){
			//alert(data.desc);
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示','成功');
			
		}else{
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示','失败');
			
		}
	});
}


