var url ="/module/redis/redisPageData.json";

function searchForkey(){
    var key = $('#key').val();
    $('#tableDataGrid').datagrid('clearSelections'); 
    $('#tableDataGrid').datagrid('options').url = url+"?redisServerId="+redisServerId+"&index="+index; 
    $('#tableDataGrid').datagrid('load',{'selectKey' : key});
}
       
function deleteForkey(){
    var key = $('#key').val();  
    /*
    var reg = /^app\_\S$/;
    
    if(!reg.test(key)){
		$.messager.alert('提示','只能删除app_开头的！');
		return ;
	}
    */
    var params= {redisServerId:redisServerId,index:index,selectKey:key}; 
    $.messager.confirm('提示框','你确定要删除些节点，会影响到云平台的租户，请再确定？',function(r){
	    if (r){
	    	$.post("deleteRedisBykey.json",params,function(data,status){
	    		if(data.code == 10000){
	    		    //alert(data.desc);
	    			searchForkey()
	    			$.messager.alert('提示','删除成功');	
	    		}else{
	    			$.messager.alert('提示','删除失败');	
	    		}
	    	});
	    }
	 });
    
    
    
    $('#tableDataGrid').datagrid('clearSelections'); 
    $('#tableDataGrid').datagrid('options').url = url+"?redisServerId="+redisServerId+"&index="+index; 
    $('#tableDataGrid').datagrid('load',{'selectKey' : key});
}