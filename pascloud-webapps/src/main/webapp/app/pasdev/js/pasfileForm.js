function addTenantPasfile(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="name" class="formlabel">租户代号:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="name" name="name" data-options="required:true" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	
	div += createFormFooter(); 
	createDialogDivWithSize('mainDataGrid', 'addPasfileDir','添加租户目录', '',500,180,div);
}

function createFormFooter(){

	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:10px;">';
	
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:20%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="addPasfile()" >确定</a>'; 
	html +=   '</div>';
	html +=   '<div style="border:#ccc 0px solid;float:left;width:30%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'">重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
}

function addPasfile(){
	
	var name = $('#name').val();
	var param = {name:name};
	
	if(''==name || name.length<=0){
		$.messager.alert('提示','name不能为空');
		return ;
	}
	
	var regName=/^dn[1-9]{1}[0-9]{0,2}$/;
	if(!regName.test(name)){
		$.messager.alert('提示','节点代号的格式必须为dn1,dn2,dn10,dn99...等');
		return ;
	}
	
	
	if(name == 'pasdev'){
		$.messager.alert('提示','不能填写');	
	}else{
		$('#addPasfileDir').dialog('close');
		//alert(addrVal+''+nameVal );
		EasyUILoad('mainCenter');
		$.post("copyPasfileWithName.json",param,function(data,status){
			if(data.code == 10000){
			    //alert(data.desc);
				reloadTree();
				dispalyEasyUILoad('mainCenter');
				$.messager.alert('提示','复制成功');	
			}else{
				dispalyEasyUILoad('mainCenter');
				$.messager.alert('提示',data.desc);	
			}
		});
	}
	
	
}

function delPasfile(){
	
	var node = $('#pasfileTree').treegrid('getSelected');
	//alert(node);
	if(node!=null){
		var name = node.id;
		
		var param = {name:name};
		
		if(name == 'pasdev'){
			$.messager.alert('提示','pasdev目录不能删除');	
		}else{
			$.messager.confirm('提示框','你确定要删除些节点，会影响到云平台的租户，请再确定？',function(r){
			    if (r){
			    	EasyUILoad('mainCenter');
			    	$.post("delPasfileWithName.json",param,function(data,status){
			    		if(data.code == 10000){
			    		    //alert(data.desc);
			    			reloadTree();
			    			dispalyEasyUILoad('mainCenter');
			    			$.messager.alert('提示','删除成功');	
			    		}else{
			    			$.messager.alert('提示','删除失败');	
			    		}
			    	});
			    }
			});
		}
	}else{
		$.messager.alert('提示','请选择要删除的目录');	
	}
	
}

function uploadPasfile(){
	var node = $('#pasfileTree').treegrid('getSelected');
	//alert(node);
	if(node!=null){
        var name = node.id;
		var param = {name:name};
		if(name == 'pasdev'){
			$.messager.alert('提示','pasdev目录不能好传');	
		}else{
			EasyUILoad('mainCenter');
			$.post("uploadPasfile.json",param,function(data,status){
	    		if(data.code == 10000){
	    		    //alert(data.desc);
	    			//putPasfileToRedis.json
	    			EasyUILoadForMsg('mainCenter','上传完成，正在初始化到缓存中，请耐心等待！');
	    			$.post("putPasfileToRedis.json",param,function(data,status){
	    	    		if(data.code == 10000){
	    	    		    //alert(data.desc);
	    	    			dispalyEasyUILoad('mainCenter');
	    	    			$.messager.alert('提示',data.desc);	
	    	    		}else{
	    	    			dispalyEasyUILoad('mainCenter');
	    	    			$.messager.alert('提示',data.desc);	
	    	    		}
	    	    	});
	    		}else{
	    			dispalyEasyUILoad('mainCenter');
	    			$.messager.alert('提示',data.desc);	
	    		}
	    	});
		}
		
	}else{
		$.messager.alert('提示','请选择要上传的目录');
	}
}


