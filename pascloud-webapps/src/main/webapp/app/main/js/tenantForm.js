function addSelectDbDialog(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="dbname" class="formlabel">选择代号: </label>';
	div +=    '<input class="easyui-combobox" id="dbname" name="dbname" data-options="valueField:\'name\',textField:\'name\',url:\'/module/mycat/datanodes.json\'" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="en" class="formlabel">英文名称: </label>';
	div +=    '<input class="easyui-validatebox" id="en" name="en" data-options="required:true" value="" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="cn" class="formlabel">中文名称: </label>';
	div +=    '<input class="easyui-validatebox" id="cn" name="cn" data-options="required:true" value="" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div += createSelectDBFormFooter(); 
	createDialogDivWithSize('mainDataGrid', 'tenantAdd','添加数据库节点', '',450,250,div);
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