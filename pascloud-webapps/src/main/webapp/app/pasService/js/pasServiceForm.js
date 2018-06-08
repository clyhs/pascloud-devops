function addPasService(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="ip" class="formlabel">选择服务器IP:</label>';
	div +=    '<input class="easyui-combobox" id="ip" name="ip" data-options="valueField:\'ip\',textField:\'ip\',url:\'/module/server/appservers.json\'" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="type" class="formlabel">选择服务类别:</label>';
	div +=    '<input class="easyui-combobox" id="type" name="type" data-options="valueField:\'key\',textField:\'value\',url:\'/module/pasService/getPasCloudServiceType.json\'" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	
	div += createFormFooter(); 
	createDialogDivWithSize('mainDataGrid', 'addPasService','添加云服务', '',500,200,div);
}

function createFormFooter(){
	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:10px;">';
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:30%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="submit()" >确定</a>'; 
	html +=   '</div>';
	html +=   '<div style="border:#ccc 0px solid;float:left;width:30%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'">重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
}


function submit(){
	var ip = $("#ip").combobox('getValue');
	var type = $("#type").combobox('getValue');
	var param = {ip:ip,type:type};
	if("" == ip || ip.length == 0 || "" == type || type.length == 0){
		$.messager.alert('提示','IP和类型不能为空');
		return ;
	}else{
		EasyUILoad('mainCenter');
		$.post("addPasService.json",param,function(data,status){
			$('#addPasService').dialog('close');
			if(data.code == 10000){
				
				$('#mainDataGrid').datagrid('reload');//刷新
				dispalyEasyUILoad( 'mainCenter' );
				$.messager.alert('提示','成功');
			}else{
				dispalyEasyUILoad( 'mainCenter' );
				$.messager.alert('提示',data.desc);
			}
			
		});
	}
}

function addContainer(){
	
	var nameVal = $("#name").val();
	var ipVal = $("#node").val();
	var portVal = $("#port").val();
	var imageNameVal = $("#imageName").val();
	
	var param = {name:nameVal,ip:ipVal,imageName:imageNameVal,port:portVal};
	EasyUILoad('mainCenter');
	$.post("addContainerForPB.json",param,function(data,status){
		if(data.code == 10000){
			$('#pasAddContainer').dialog('close');
			$('#mainDataGrid').datagrid('reload');//刷新
			dispalyEasyUILoad( 'mainCenter' );
		}
		
	});
}

function addBaseContainer(){
	
	var param = {};
	
	$.post("addBaseContainer.json",param,function(data,status){
		if(data.code == 10000){
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}

function addMainServiceContainer(){
	
	var param = {};
	
	$.post("addMainServiceContainer.json",param,function(data,status){
		if(data.code == 10000){
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}

function addPaspmServiceContainer(){
	
	var param = {};
	
	$.post("addPaspmServiceContainer.json",param,function(data,status){
		if(data.code == 10000){
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}

function addTomcatContainer(){
	
	var param = {};
	
	$.post("addTomcatContainer.json",param,function(data,status){
		if(data.code == 10000){
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}


function copyMainServiceContainer(){
	
	var param = {};
	
	$.post("copyMainServiceContainer.json",param,function(data,status){
		if(data.code == 10000){
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}

function copyPaspmServiceContainer(){
	
	var param = {};
	
	$.post("copyPaspmServiceContainer.json",param,function(data,status){
		if(data.code == 10000){
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}


function startContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	if(row.state != 'running'){
		EasyUILoad('mainCenter');
		$.post("/module/container/startContainer.json",{containerId:row.id,ip:row.ip},function(data,status){

			$('#mainDataGrid').datagrid('reload');//刷新
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示','成功');
		});
	}else{
		$.messager.alert('提示','已经运行');
	}
}

function stopContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	if(null!=row){
		if(row.name == "shipyard-proxy"){
			$.messager.alert('提示','基础服务不能停止');
			return ;
		}
	}else{
		$.messager.alert('提示','请先选择一行');
		return ;
	}
	if(row.state == 'running'){
		EasyUILoad('mainCenter');
		$.post("/module/container/stopContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
			if(data.code == 10000){
				
				
				$('#mainDataGrid').datagrid('reload');//刷新
				dispalyEasyUILoad( 'mainCenter' );
				$.messager.alert('提示','成功');
			}else{
				$.messager.alert('提示','失败');
			}
			
		});
	}else{
		$.messager.alert('提示','已经运行');
	}
}

function restartContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	if(row.state == 'exited'){
		EasyUILoad('mainCenter');
		$.post("/module/container/restartContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
            if(data.code == 10000){
				
				$('#mainDataGrid').datagrid('reload');//刷新
				dispalyEasyUILoad( 'mainCenter' );
				$.messager.alert('提示','成功');
			}else{
				$.messager.alert('提示','失败');
			}
		});
	}else{
		$.messager.alert('提示','先停止掉容器');
	}
}


function pauseContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	if(null!=row){
		if(row.name.indexOf("shipyard")){
			$.messager.alert('提示','基础服务不能停止');
			return ;
		}
	}else{
		$.messager.alert('提示','请先选择一行');
		return ;
	}
	if(row.state != 'paused'){
		EasyUILoad('mainCenter');
		$.post("/module/container/pauseContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
            
			$('#mainDataGrid').datagrid('reload');//刷新
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示','成功');
		});
	}else{
		$.messager.alert('提示','已经暂停');
	}
}

function unpauseContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	if(row.state != 'running'){
		EasyUILoad('mainCenter');
		$.post("/module/container/unpauseContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
			$('#mainDataGrid').datagrid('reload');//刷新
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示','成功');
		});
	}else{
		$.messager.alert('提示','已经恢复');
	}
}

function removeContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	if(null!=row){
		if(row.name.indexOf("shipyard")){
			$.messager.alert('提示','基础服务不能销毁');
			return ;
		}
	}else{
		$.messager.alert('提示','请先选择一行');
		return ;
	}
	
	if(row.state == 'exited'|| row.state == 'created'){
		EasyUILoad('mainCenter');
		
		
		
		$.post("/module/container/removeContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
			$('#mainDataGrid').datagrid('reload');//刷新
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示','成功');
		});
	}else{
		$.messager.alert('提示','先停止掉容器');
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