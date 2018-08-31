function addDB(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="sid" class="formlabel">数据库实例名称:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="sid" name="sid" data-options="required:true" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	
	div += createFormFooter(); 
	createDialogDivWithSize('mainDataGrid', 'addDB','添加数据库实例', '',500,180,div);
}

function addUser(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="sid" class="formlabel">用户名称:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="username" name="username" data-options="required:true" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div += createUserFooter(); 
	createDialogDivWithSize('mainDataGrid', 'addUser','添加数据库用户', '',500,180,div);
}

function updateHyDialog(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="preffix" class="formlabel">设置行员前缀:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="preffix" name="preffix" data-options="required:true" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	
	div += createHYFooter(); 
	createDialogDivWithSize('mainDataGrid', 'updateHyDialog','设置行员前缀', '',500,180,div);
}

function createFormFooter(){

	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:10px;">';
	
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:20%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="addDBSubmit()" >确定</a>'; 
	html +=   '</div>';
	html +=   '<div style="border:#ccc 0px solid;float:left;width:30%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'" onclick="reset()" >重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
}

function createHYFooter(){

	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:10px;">';
	
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:20%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="updateHySubmit()" >确定</a>'; 
	html +=   '</div>';
	html +=   '<div style="border:#ccc 0px solid;float:left;width:30%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'" onclick="reset()" >重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
}

function createUserFooter(){

	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:10px;">';
	
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:20%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="addUserSubmit()" >确定</a>'; 
	html +=   '</div>';
	html +=   '<div style="border:#ccc 0px solid;float:left;width:30%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'" onclick="#" >重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
}

function reset(){
	$('#sid').val("");
}

function addDBSubmit(){
	
	var sid = $('#sid').val();
	var node = $('#dbserverTree').treegrid('getSelected');
	
	
	
	var ip = node.text;
	
	if(null==node){
		$.messager.alert('提示','请选择一个IP地址');
		return ;
	}
	if(''==sid || sid.length<=0){
		$.messager.alert('提示','sid不能为空');
		return ;
	}
	var reg=/^cpas[0-9]{2}$/;
	if(!reg.test(sid)){
		$.messager.alert('提示','数据库实例名称的格式必须为cpas01,cpas02...等');
		return ;
	}
	
	var param = {sid:sid,ip:ip};
	$('#addDB').dialog('close');
	//alert(addrVal+''+nameVal );
	
	//EasyUILoad('mainCenter');
	MaskUtil.mask();
	MaskUtil.mask('创建数据库...');
	$.post("createOracle.json",param,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			reloadTableWithID(defaultIp);
			//dispalyEasyUILoad('mainCenter');
			//$.messager.alert('提示','创建成功');	
			impDmpWithSidW(sid)
		}else{
			//dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
			MaskUtil.unmask(); 
		}
	});
}


function addUserSubmit(){
	
	var username = $('#username').val();
	var node = $('#dbserverTree').treegrid('getSelected');
	
	
	
	var ip = node.text;
	
	if(null==node){
		$.messager.alert('提示','请选择一个IP地址');
		return ;
	}
	if(''==username || username.length<=0){
		$.messager.alert('提示','username不能为空');
		return ;
	}
	
    var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	if(null==row){
		$.messager.alert('提示','请选择行记录');
		return ;
	}
	
	var sid = row.id;
	var url = row.url;
	
	if(''==sid || sid.length<=0){
		$.messager.alert('提示','sid不能为空');
		return ;
	}
	
	if(''==url || url.length<=0){
		$.messager.alert('提示','url不能为空');
		return ;
	}
	
	var reg=/^pas[1-9]{1}[0-9]{0,1}$/;
	if(!reg.test(username)){
		$.messager.alert('提示','数据库实例名称的格式必须为pas1,pas2...pas99等');
		return ;
	}
	
	var param = {sid:sid,ip:ip,url:url,username:username};
	$('#addUser').dialog('close');
	//alert(addrVal+''+nameVal );
	
	//EasyUILoad('mainCenter');
	MaskUtil.mask();
	MaskUtil.mask('创建数据库用户...');
	$.post("createPasUser.json",param,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			$.messager.alert('提示',data.desc);	
			MaskUtil.unmask();
		}else{
			//dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
			MaskUtil.unmask(); 
		}
	});
}

function restartListener(){
	
	//var sid = $('#sid').val();
	var node = $('#dbserverTree').treegrid('getSelected');
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	if(null==row){
		$.messager.alert('提示','请选择行记录');
		return ;
	}
	var sid = row.id;
	var ip = node.text;
	
	if(null==node){
		$.messager.alert('提示','请选择一个IP地址');
		return ;
	}
	if(''==sid || sid.length<=0){
		$.messager.alert('提示','sid不能为空');
		return ;
	}
	
	var param = {sid:sid,ip:ip};
	//alert(addrVal+''+nameVal );
	
	//EasyUILoad('mainCenter');
	MaskUtil.mask();
	MaskUtil.mask('重启监控器...');
	$.post("restartListener.json",param,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			//dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);
			MaskUtil.unmask(); 
		}else{
			//dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
			MaskUtil.unmask(); 
		}
	});
}

function createManagerUser(){
	
	//var sid = $('#sid').val();
	var node = $('#dbserverTree').treegrid('getSelected');
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	if(null==row){
		$.messager.alert('提示','请选择行记录');
		return ;
	}
	var sid = row.id;
	var url = row.url;
	var ip = node.text;
	
	if(null==node){
		$.messager.alert('提示','请选择一个IP地址');
		return ;
	}
	if(''==sid || sid.length<=0){
		$.messager.alert('提示','sid不能为空');
		return ;
	}
	
	if(''==url || url.length<=0){
		$.messager.alert('提示','url不能为空');
		return ;
	}
	
	var param = {sid:sid,ip:ip,url:url};
	//alert(addrVal+''+nameVal );
	
	//EasyUILoad('mainCenter');
	MaskUtil.mask();
	MaskUtil.mask('新建管理员...');
	$.post("createManagerUser.json",param,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			//dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);
			MaskUtil.unmask(); 
		}else{
			//dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
			MaskUtil.unmask(); 
		}
	});
}

function updateHySubmit(){
	
	var preffix = $('#preffix').val();
	var row = $('#mainDataGrid').datagrid('getSelected'); 
   
	if(null==row){
		$.messager.alert('提示','请选择一个IP地址');
		return ;
	}
	if(''==preffix || preffix.length<=0){
		$.messager.alert('提示','前缀不能为空');
		return ;
	}
	var url = row.url;
	var param = {preffix:preffix,url:url};
	$('#updateHyDialog').dialog('close');
	//alert(addrVal+''+nameVal );
	EasyUILoad('mainCenter');
	$.post("updateKhdxHy.json",param,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示','设置成功');
			
		}else{
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
		}
	});
}

function delDB(){
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	var sid = row.id;
	if(sid == 'cloudpas'){
		$.messager.alert('提示','cloudpas不能删除');	
		return ;
	}
	
	
	var params = {sid:sid,ip:defaultIp};
	//EasyUILoad('mainCenter');
	MaskUtil.mask();
	MaskUtil.mask('删除数据库...');
	$.post("deleteOracle.json",params,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			reloadTableWithID(defaultIp);
			//dispalyEasyUILoad('mainCenter');
			//$.messager.alert('提示','删除成功');
			delMycatDatanode(sid,defaultIp);
		}else{
			//dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
			MaskUtil.unmask(); 
		}
	});
	
}

function impDmpWithSid(){
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	var sid = row.id;
	var params = {sid:sid,ip:defaultIp};
	EasyUILoad('mainCenter');
	
	$.post("impDmpWithSid.json",params,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示','导入成功');	
		}else{
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
		}
	});
}

function impDmpWithSidAndUser(){
	
	if(''==subSid || subSid.length<=0){
		$.messager.alert('提示','sid不能为空');
		return ;
	}
	if(''==subUrl || subUrl.length<=0){
		$.messager.alert('提示','url不能为空');
		return ;
	}
	if(''==subUsername || subUsername.length<=0){
		$.messager.alert('提示','username不能为空');
		return ;
	}
	if(''==subPassword || subPassword.length<=0){
		$.messager.alert('提示','password不能为空');
		return ;
	}
	var params = {sid:subSid,url:subUrl,username:subUsername,password:subPassword,ip:defaultIp};
	alert(subUrl);
	alert(subUsername);
	//EasyUILoad('mainCenter');
	/*
	$.post("impDmpWithSidAndUser.json",params,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示','导入成功');	
		}else{
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
		}
	});*/
}

function deleteWithSidAndUser(){
	if(''==subUsername || subUsername.length<=0){
		$.messager.alert('提示','username不能为空');
		return ;
	}
	
	if(subUsername=='pas'){
		$.messager.alert('提示','pas为默认用户，不能删除');
		return ;
	}
}

function impDmpWithSidW(sid){
	//var row = $('#mainDataGrid').datagrid('getSelected'); 
	//var sid = row.id;
	var params = {sid:sid,ip:defaultIp};
	//EasyUILoad('mainCenter');
	MaskUtil.mask('初始化数据库...');
	$.post("impDmpWithSid.json",params,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			//dispalyEasyUILoad('mainCenter');
			//$.messager.alert('提示','导入成功');	
			//MaskUtil.unmask(); 
			addMycatDB(sid,defaultIp);
		}else{
			//dispalyEasyUILoad('mainCenter');
			//$.messager.alert('提示',data.desc);	
			$.messager.alert('提示',data.desc);	
			MaskUtil.unmask(); 
		}
	});
}

function addMycatDB(sid,ip){
	//alert('设置数据库');
	//var name = $("#name").val();
	var dbType = "oracle";
	var port = "1521";
	var database = sid;
	var ip = ip;
	var user = "pas";
	var password = "pas";
	
	var params = {dbType:dbType,ip:ip,port:port,database:database,username:user,password:password};
	//alert(name.length);
	if(dbType.length<=0 || port.length<=0 || database.length<=0 || ip.length<=0 || user.length<=0 || password.length<=0){
		$.messager.alert('提示','参数没有填写完整');
		MaskUtil.unmask(); 
		return ;
	}
	

	var regDatabase=/^[a-zA-Z0-9]+$/;
	if(!regDatabase.test(database)){
		$.messager.alert('提示','端口的格式必须为4-6位数字');
		MaskUtil.unmask(); 
		return ;
	}
	
	var regIP= /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
	if(!regIP.test(ip)){
		$.messager.alert('提示','ip格式必须为x.x.x.x x为数字');
		MaskUtil.unmask(); 
		return ;
	}
	
	MaskUtil.mask('配置mycat并重启...');
	$.post("/module/mycat/addDatanodeAndRestart.json",params,function(data,status){
		if(data.code == 10000){
			//alert(data.desc);
			//alert("修改成功，请重新启动应用");
			//$('#mainDataGrid').datagrid('reload');//刷新
			//dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示','成功');
			MaskUtil.unmask(); 
		}else{
			//dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);
			MaskUtil.unmask(); 
		}
	});	
	
}

function delMycatDatanode(sid,ip){
	//alert('设置数据库');
	var dbType = "oracle";
	var port = "1521";
	var database = sid;
	var ip = ip;
	var user = "pas";
	var password = "pas";
	var params = {dbType:dbType,ip:ip,port:port,database:database,username:user,password:password};
	//EasyUILoad('mainCenter');
	MaskUtil.mask('配置mycat并重启...');
	$.post("/module/mycat/delDatanodeAndRestart.json",params,function(data,status){
		if(data.code == 10000){
			//alert(data.desc);
			//alert("修改成功，请重新启动应用");
			//$('#mainDataGrid').datagrid('reload');//刷新
			//dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示','成功')
			MaskUtil.unmask(); 
		}else{
			//dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);
			MaskUtil.unmask(); 
		}
	});
	
}