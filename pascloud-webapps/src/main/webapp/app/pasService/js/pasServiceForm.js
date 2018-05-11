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

function addBaseContainer(){
	
	var param = {};
	
	$.post("addBaseContainer.json",param,function(data,status){
		if(data.code = 10000){
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}

function addMainServiceContainer(){
	
	var param = {};
	
	$.post("addMainServiceContainer.json",param,function(data,status){
		if(data.code = 10000){
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}

function addPaspmServiceContainer(){
	
	var param = {};
	
	$.post("addPaspmServiceContainer.json",param,function(data,status){
		if(data.code = 10000){
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}

function addTomcatContainer(){
	
	var param = {};
	
	$.post("addTomcatContainer.json",param,function(data,status){
		if(data.code = 10000){
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}


function copyMainServiceContainer(){
	
	var param = {};
	
	$.post("copyMainServiceContainer.json",param,function(data,status){
		if(data.code = 10000){
			$('#mainDataGrid').datagrid('reload');//刷新
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