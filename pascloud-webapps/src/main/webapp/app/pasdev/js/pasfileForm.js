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

function publishPasfileDialog(){
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="title" class="formlabel">名称:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="title" name="title" data-options="required:true" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="newfunId" class="formlabel">ID:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="newfunId" name="newfunId" data-options="required:true" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="desc" class="formlabel">描述:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="desc" name="desc" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	
	div += createPublishFormFooter(); 
	createDialogDivWithSize('mainDataGrid', 'publishPanel','发布为公共版本', '',500,260,div);
}


function createPublishFormFooter(){

	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:10px;">';
	
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:20%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="publish()" >确定</a>'; 
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
	
	
	if(name == 'dn0'){
		$.messager.alert('提示','不能填写');	
	}else{
		$('#addPasfileDir').dialog('close');
		//alert(addrVal+''+nameVal );
		EasyUILoad('mainCenter');
		$.post("copyPasfileWithName.json",param,function(data,status){
			if(data.code == 10000){
			    //alert(data.desc);
				reloadTree();
				reloadTableWithID(name);
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
		
		if(name == 'dn0'){
			$.messager.alert('提示','dn0目录不能删除');	
		}else{
			$.messager.confirm('提示框','你确定要删除些节点，会影响到云平台的租户，请再确定？',function(r){
			    if (r){
			    	EasyUILoad('mainCenter');
			    	$.post("delPasfileWithName.json",param,function(data,status){
			    		if(data.code == 10000){
			    		    //alert(data.desc);
			    			reloadTree();
			    			reloadTableWithID('dn0');
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
		if(name == ''){
			$.messager.alert('提示','name不能为空');	
		}else{
			EasyUILoad('mainCenter');
			$.post("uploadPasfile.json",param,function(data,status){
	    		if(data.code == 10000){
	    		    //alert(data.desc);
	    			//putPasfileToRedis.json
	    			/*
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
	    	    	});*/
	    			dispalyEasyUILoad('mainCenter');
	    			$.messager.alert('提示',data.desc);	
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

function sysPasfileToDB(){
	var node = $('#pasfileTree').treegrid('getSelected');
	//alert(node);
	if(node!=null){
        var name = node.id;
		var param = {dirId:name};
		EasyUILoad('mainCenter');
		$.post("sysPasfileToDB.json",param,function(data,status){
    		if(data.code == 10000){
    			dispalyEasyUILoad('mainCenter');
    			$.messager.alert('提示',data.desc);
    		}else{
    			dispalyEasyUILoad('mainCenter');
    			$.messager.alert('提示',data.desc);	
    		}
    	});
		
	}else{
		$.messager.alert('提示','请选择要上传的目录');
	}
}


function deletePasfile(){
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	if(null == row){
		$.messager.alert('错误',"请选择一行");
		return ;
	}
	
	var funId = row.funId;
	
	var params = {funId:funId,dirId:dirId};
	//alert(funId);
	
	$.messager.confirm('提示框','你确定要删除，会影响到云平台的租户资源，请再确定？',function(r){
	    if (r){
	    	EasyUILoad('mainCenter');
	    	$.post("deletePasfileByFunId.json",params,function(data,status){
	    		if(data.code == 10000){
	    		    //alert(data.desc);
	    			reloadTableWithID(dirId);
	    			dispalyEasyUILoad('mainCenter');
	    			$.messager.alert('提示','删除成功');	
	    		}else{
	    			$.messager.alert('提示','删除失败');	
	    		}
	    	});
	    }
	});
	
	
}

function publish(){
	
	var title = $("#title").val();
	var newfunId = $("#newfunId").val();
	var desc = $("#desc").val();
	
	var regCn=/^[\u4E00-\u9FA5]{2,15}$/;
	if(!regCn.test(title)){
		$.messager.alert('提示','中文名称只能2位到15位的汉字组成');
		return ;
	}
	var regDesc=/^[A-Za-z0-9\u4E00-\u9FA5]{0,20}$/;
	if(!regDesc.test(desc)){
		$.messager.alert('提示','描述只能包括中英文数字组合，且不能超过20个字符');
		return ;
	}
	
	var regNewfunId=/^[A-Za-z0-9]{0,15}$/;
	if(!regNewfunId.test(newfunId)){
		$.messager.alert('提示','描述只能包括英文数字组合，且不能超过15个字符');
		return ;
	}
	
    var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	if(null == row){
		$.messager.alert('错误',"请选择一行");
		return ;
	}
	
	var funId = row.funId;
	var version = row.version;
	
	var vers = version.split(".");
	if(vers.length!=4){
		$.messager.alert('错误',"该文件版本号不规范，版本号应该是x.x.x.x");
		return ;
	}else{
		if(vers[1]=='0'){
			$.messager.alert('错误',"该文件为公共版本，不需要发布。");
			return ;
		}
	}
	
	var params = {funId:funId,dirId:dirId,desc:desc,title:title,newfunId:newfunId};
	$('#publishPanel').dialog('close');
	EasyUILoad('mainCenter');
	$.post("publish.json",params,function(data,status){
		if(data.code == 10000){
		    //alert(data.desc);
			reloadTableWithID(dirId);
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
		}else{
			dispalyEasyUILoad('mainCenter');
			$.messager.alert('提示',data.desc);	
		}
	});
}





