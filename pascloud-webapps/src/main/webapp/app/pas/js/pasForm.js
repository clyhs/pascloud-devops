function addDialog(){
	var div = '';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="name" class="formlabel">分行名称:</label>';
	div +=    '<input class="easyui-validatebox formInput" name="name" data-options="required:true" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	createDialogDivWithSize('mainDataGrid', 'pasAdd','添加分行', '',500,400,div);
}