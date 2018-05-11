function addDialog(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="name" class="formlabel">分行名称:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="name" name="name" data-options="required:true" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="node" class="formlabel">服务器节点:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="node" name="node" data-options="required:true" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="port" class="formlabel">映射端口:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="port" name="port" data-options="required:true" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="imageName" class="formlabel">镜像名称:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="imageName" name="imageName" data-options="required:true" value="tomcat:7.0.82-jre8" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	div += createFormFooter(); 
	createDialogDivWithSize('mainDataGrid', 'pasAddContainer','添加分行', '',500,250,div);
}

function createFormFooter(){

	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:10px;">';
	
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:35%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="addContainer()" >确定</a>'; 
	html +=   '</div>';
	html +=   '<div style="border:#ccc 0px solid;float:left;width:35%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'">重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
}


function addDbDialog(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="driverClass" class="formlabel">driverClass:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="driverClass" name="driverClass" data-options="required:true" value="com.ibm.db2.jcc.DB2Driver" size=30 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="url" class="formlabel">数据库地址:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="url" name="url" data-options="required:true" size=30 >';
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
	createDialogDivWithSize('mainDataGrid', 'pasAddDb','绑定数据库', '',500,250,div);
}

function createDBFormFooter(){

	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:10px;">';
	
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:35%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="addDB()" >确定</a>'; 
	html +=   '</div>';
	html +=   '<div style="border:#ccc 0px solid;float:left;width:35%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'">重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
}



function readSpringXml(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	var addrVal = row.ip;
	var nameVal = row.name;
	var param = {name:nameVal,addr:addrVal};
	//alert(addrVal+''+nameVal );
	$.post("readContainerSpringXml.json",param,function(data,status){
		if(data.code = 10000){
		    //alert(data.desc);
			$('#pasSpringXml_text').val(data.desc);
			$('#pasSpringXml_text').format({method: 'xml'});
			$('#pasSpringXml').dialog('open');
		}
		
	});
	
}

function addContainer(){
	
	var nameVal = $("#name").val();
	var ipVal = $("#node").val();
	var portVal = $("#port").val();
	var imageNameVal = $("#imageName").val();
	
	var param = {name:nameVal,ip:ipVal,imageName:imageNameVal,port:portVal};
	
	$.post("addContainerForPB.json",param,function(data,status){
		if(data.code = 10000){
			$('#pasAddContainer').dialog('close');
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}

function addDB(){
	//alert('设置数据库');
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	var addrVal = row.ip;
	var nameVal = row.name;
	var driverClassVal = $("#driverClass").val();
	var urlVal = $("#url").val();
	var usernameVal = $("#username").val();
	var passwordVal = $("#password").val();
	
	
	var param = {name:nameVal,addr:addrVal,driverClass:driverClassVal,url:urlVal,username:usernameVal,password:passwordVal};
	//alert(driverClassVal);
	$.post("modifyContainerSpringXml.json",param,function(data,status){
		if(data.code = 10000){
			//alert(data.desc);
			alert("修改成功，请重新启动应用");
			$('#pasAddDb').dialog('close');
		}
		
	});
}

function startContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	if(row.state != 'running'){
		$.post("/module/container/startContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
			alert(data.code);
			$('#mainDataGrid').datagrid('reload');//刷新
		});
	}else{
		alert('已经运行');
	}
}

function stopContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	if(row.state == 'running'){
		$.post("/module/container/stopContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
			alert(data.code);
			$('#mainDataGrid').datagrid('reload');//刷新
		});
	}else{
		alert('已经运行');
	}
}

function restartContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	if(row.state != 'running'){
		$.post("/module/container/restartContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
			alert(data.code);
			$('#mainDataGrid').datagrid('reload');//刷新
		});
	}else{
		alert('已经运行');
	}
}


function pauseContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	if(row.state != 'paused'){
		$.post("/module/container/pauseContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
			alert(data.code);
			$('#mainDataGrid').datagrid('reload');//刷新
		});
	}else{
		alert('已经暂停');
	}
}

function unpauseContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	if(row.state != 'running'){
		$.post("/module/container/unpauseContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
			alert(data.code);
			$('#mainDataGrid').datagrid('reload');//刷新
		});
	}else{
		alert('已经恢复');
	}
}

function getContainerLog(){
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	$.post("/module/container/getContainerLog.json",{name:row.name,ip:row.ip},function(data,status){
		//alert(data.desc);
		//$('#mainDataGrid').datagrid('reload');//刷新
		if(data.code = 10000){
		    //alert(data.desc);
			$('#pasSpringlog_text').val(data.desc);
			//$('#pasSpringlog_text').format({method: 'json'});
			$('#pasSpringlog').dialog('open');
		}
	});
}