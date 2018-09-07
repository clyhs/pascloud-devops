function uploadPascodeAndRestart(){
    var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	if(null==row){
		$.messager.alert('提示','请选择一行');
		return ;
	}
	
	var name = row.name;
	var id = row.id;
	var type = row.type;
	var params = {name:name,id:id,type:type};
	
	MaskUtil.mask();
	MaskUtil.mask('部署升级代码...'); 
	$.post("uploadPascodeAndRestart.json",params,function(data,status){
		if(data.code == 10000){
			reloadTableWithID();
			$.messager.alert('提示',data.desc);
			MaskUtil.unmask(); 
		}else{
			$.messager.alert('提示',data.desc);
			MaskUtil.unmask(); 
		}
	});
}
