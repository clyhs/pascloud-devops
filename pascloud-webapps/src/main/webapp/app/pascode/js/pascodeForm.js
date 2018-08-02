function uploadPascodeAndRestart(){
    var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	if(null==row){
		$.messager.alert('提示','请选择一行');
		return ;
	}
}
