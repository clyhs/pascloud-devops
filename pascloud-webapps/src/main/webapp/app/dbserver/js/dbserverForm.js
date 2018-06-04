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
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'">重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
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
	var param = {sid:sid,ip:ip};
	$('#addDB').dialog('close');
	//alert(addrVal+''+nameVal );
	EasyUILoad('mainCenter');
	$.post("createOracle.json",param,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			reloadTableWithID(defaultIp);
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示','创建成功');	
		}else{
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
		}
	});
}

function delDB(){
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	var sid = row.id;
	var params = {sid:sid,ip:defaultIp};
	EasyUILoad('mainCenter');
	$.post("deleteOracle.json",params,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			reloadTableWithID(defaultIp);
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示','删除成功');	
		}else{
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
		}
	});
	
}