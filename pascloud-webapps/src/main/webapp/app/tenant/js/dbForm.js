function addDbDialog(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="name" class="formlabel">节点代号:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="name" name="name" data-options="required:true" value="" onBlur="changeName(this.value)" size=30 >';
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
	createDialogDivWithSize('mainDataGrid', 'tenantAddDb','添加数据库节点', '',500,360,div);
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


function addSelectDbDialog(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="dbname" class="formlabel">选择代号:</label>';
	div +=    '<input class="easyui-combobox" id="dbname" name="dbname" data-options="valueField:\'name\',textField:\'name\',url:\'/module/mycat/datanodes.json\'" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div += createSelectDBFormFooter(); 
	createDialogDivWithSize('mainDataGrid', 'tenantAddSelectDb','添加数据库节点', '',450,180,div);
}

function createSelectDBFormFooter(){

	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:10px;">';
	
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:20%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="selectDBTest()" >测试</a>'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:20%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="addSelectDB()" >确定</a>'; 
	html +=   '</div>';
	html +=   '<div style="border:#ccc 0px solid;float:left;width:35%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'">重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
}



function changeName(name){
	var name = $("#name").val();
	$.get('/module/mycat/datanode.json',{name:name},function(data,status){
		if(data.code == 10000){
			//alert("成功"+data.bean);
			$("#dbType").val(data.bean.dbType);
			$("#port").val(data.bean.port);
			$("#database").val(data.bean.database);
			$("#ip").val(data.bean.ip);
			$("#username").val(data.bean.user);
			$("#password").val(data.bean.password);
		}else{
			$("#port").val("");
			$("#database").val("");
			$("#ip").val("");
			$("#username").val("");
			$("#password").val("");
		}
	});
}

function selectDBTest(){
	var name =  $('#dbname').combobox('getValue');
	//alert(name);
	$.get('/module/mycat/datanode.json',{name:name},function(data,status){
		if(data.code == 10000){
			$.messager.alert('提示','连接成功');
		}else{
			$.messager.alert('提示','连接失败');
		}
	});
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
	}else{
		$.post("addTenantDB.json",params,function(data,status){
			if(data.code = 10000){
				//alert(data.desc);
				//alert("修改成功，请重新启动应用");
				$('#tenantAddDb').dialog('close');
				$('#mainDataGrid').datagrid('reload');//刷新
			}
		});
	}
}

function addSelectDB(){
	var name =  $('#dbname').combobox('getValue');
	
	if(''==name || 'dn0'==name){
		$.messager.alert('提示','请选择非dn0的节点');
		return ;
	}
	var params = {name:name};
	EasyUILoad('mainCenter');
	$.get('checkDBConfigByName.json',params,function(data,status){
		if(data.code == 10000){
			//$.messager.alert('提示','节点还没被使用');
			$.post("addTenantDBByName.json",params,function(data,status){
				if(data.code = 10000){
					//alert(data.desc);
					//alert("修改成功，请重新启动应用");
					$('#tenantAddSelectDb').dialog('close');
					$('#mainDataGrid').datagrid('reload');//刷新
					dispalyEasyUILoad('mainCenter');
					sysHyByDBName(name);
					uploadCofingBySelectDB();
					addPasfile(name);
				}else{
					$('#tenantAddSelectDb').dialog('close');
					$('#mainDataGrid').datagrid('reload');//刷新
					dispalyEasyUILoad('mainCenter');
					$.messager.alert('提示','addTenantDBByName'+data.desc);
				}
			});
		}else{
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示','此节点已经被使用，请换其它节点');
		}
	});
}

function sysHyByDBName(name){

	if(null == name || '' == name){
		$.messager.alert('提示','名字不能为空');
		return ;
	}
	
	if(name == 'dn0'){
		$.messager.alert('提示','不能选择公共库，请重新选择');
	}else{
		var params = {name:name};
		//alert(driverClassVal + passwordVal);
		EasyUILoadForMsg('mainCenter','行员同步中，请耐心等待！');
		//alert(EasyUILoad('mainDataGrid'));
		$.get('syscHyByName.json',params,function(data,status){
			if(data.code == 10000){
				
				$('#mainDataGrid').datagrid('reload');//刷新
				dispalyEasyUILoad( 'mainCenter' );
			}else{
				dispalyEasyUILoad( 'mainCenter' );
				$.messager.alert('提示','sysHyByDBName'+data.desc);
			}
		});
	}
}

function delDB(){
	//alert('设置数据库');
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	var name = row.name;
	var params = {name:name};
	
	if(name.length>0){
		if(name == 'dn0'){
			$.messager.alert('提示','不能选择公共库，请重新选择');
		}else{
			$.messager.confirm('提示框','你确定要删除些节点，会影响到云平台的租户，请再确定？',function(r){
			    if (r){
			    	EasyUILoad('mainCenter');
			    	$.post("delTenantDB.json",params,function(data,status){
						if(data.code = 10000){
							//alert(data.desc);
							//alert("修改成功，请重新启动应用");
							uploadCofingBySelectDB();
							$('#mainDataGrid').datagrid('reload');//刷新
							dispalyEasyUILoad( 'mainCenter' );
			    			$.messager.alert('提示','上传完毕，请重启服务');
							
						}else{
							$.messager.alert('提示','delTenantDB'+data.desc);
						}
					});
			    }
			});
		}
		
		
	}
	
}



function uploadConfig(){
	$.post("uploadConfig.json",{},function(data,status){
		if(data.code = 10000){
			//alert(data.desc);
			$.messager.alert('提示','成功');
		}
	});
}

function uploadCofingBySelectDB(){
	$.post("uploadConfig.json",{},function(data,status){
		if(data.code = 10000){
			
		}else{
			$.messager.alert('提示','上传配置文件失败');
		}
	});
}

function addPasfile(name){
	
	var param = {name:name};
	
	if(''==name || name.length<=0){
		$.messager.alert('提示','name不能为空');
		return ;
	}
	if(name == 'pasdev'){
		$.messager.alert('提示','不能填写');	
	}else{
		EasyUILoadForMsg('mainCenter','上传完成，正在复制开发文件！');
		$.post("/module/pasdev/copyPasfileWithName.json",param,function(data,status){
			if(data.code == 10000){
				dispalyEasyUILoad('mainCenter');
				uploadPasfile(name);
			}else{
				dispalyEasyUILoad('mainCenter');
				$.messager.alert('提示','复制文件'+data.desc);	
			}
		});
	}
	
	
}

function uploadPasfile(name){
	//alert(node);
	if(name!=null || ''!=name){
		var param = {name:name};
		if(name == 'pasdev'){
			$.messager.alert('提示','pasdev目录不能好传');	
		}else{
			EasyUILoadForMsg('mainCenter','正在上传开发文件，请耐心等待！');
			$.post("/module/pasdev/uploadPasfile.json",param,function(data,status){
	    		if(data.code == 10000){
	    			dispalyEasyUILoad( 'mainCenter' );
	    			$.messager.alert('提示','上传完毕，请重启服务');
	    		}else{
	    			dispalyEasyUILoad('mainCenter');
	    			$.messager.alert('提示',data.desc);	
	    		}
	    	});
		}
		
	}else{
		$.messager.alert('提示','请选择要上传的目录');
	}
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
			
			$.messager.alert('提示','连接成功');
		}else{
			$.messager.alert('提示','连接失败');
		}
	});
}

function delDatanode(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	var name = row.name;
	alert(name);
	
}