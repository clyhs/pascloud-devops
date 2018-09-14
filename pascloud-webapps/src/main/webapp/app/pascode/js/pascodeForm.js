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
	
	EasyUILoad('mainCenter');
	$.post("uploadPascodeAndRestart.json",params,function(data,status){
		if(data.code == 10000){
			reloadTableWithID();
			$.messager.alert('提示',data.desc);
			dispalyEasyUILoad( 'mainCenter' );
		}else{
			$.messager.alert('提示',data.desc);
			dispalyEasyUILoad( 'mainCenter' );
		}
	});
}
