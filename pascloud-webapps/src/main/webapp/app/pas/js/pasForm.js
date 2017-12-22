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

function addContainer(){
	
	var nameVal = $("#name").val();
	var ipVal = $("#node").val();
	var portVal = $("#port").val();
	var imageNameVal = $("#imageName").val();
	
	var param = {name:nameVal,ip:ipVal,imageName:imageNameVal,port:portVal};
	
	$.post("addContainer.json",param,function(data,status){
		if(data.code = 10000){
			$('#pasAddContainer').dialog('close');
			$('#mainDataGrid').datagrid('reload');//刷新
		}
		
	});
}

function startContainer(){
	
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	if(row.state != 'running'){
		$.post("startContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
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
		$.post("pauseContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
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
		$.post("unpauseContainer.json",{containerId:row.id,ip:row.ip},function(data,status){
			alert(data.code);
			$('#mainDataGrid').datagrid('reload');//刷新
		});
	}else{
		alert('已经恢复');
	}
}