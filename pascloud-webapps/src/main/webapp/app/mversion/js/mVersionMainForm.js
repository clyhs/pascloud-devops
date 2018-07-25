function addMVersionDialog(){
	
	
	var row = $('#mVersionMainTreeGrid').treegrid('getSelected');
	if(null == row){
		$.messager.alert('提示','请先选择一个节点!');
		return ;
	}
	
	
	
	
	var div = '';
	
	div +='<div style="margin:10px 0;width:100%;">&nbsp;';  
	div +='</div>';
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="name" class="formlabel">菜单名称:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="name" name="name" data-options="required:true" >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	/*
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="type" class="formlabel">类型:</label>';
	div +=    '<select id="type" class="easyui-validatebox formInput" name="type" >';
	div +=      '<option value="1">目录</option>';
	div +=      '<option value="2">连接</option>';
	div +=      '<option value="3">按钮</option>';
	div +=    '</select>';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	*/
	
	if(pId!="" && level=='1'){
		div +='<div style="margin:5px 0;width:100%;">';  
		div +=    '<label for="url" class="formlabel">连接地址:</label>';
		div +=    '<input class="easyui-validatebox formInput" id="url" name="url" data-options="required:true" size=35 >';
		div +=    '<div style="clear:both;"></div>';
		div +='</div>';
		
		div +='<div style="margin:5px 0;width:100%;">';  
		div +=    '<label for="version" class="formlabel">版本号:</label>';
		div +=    '<input class="easyui-validatebox formInput" id="version" name="version" data-options="required:true" size=20 >';
		div +=    '<div style="clear:both;"></div>';
		div +='</div>';
	}
	
	if(pId!="" && level=='2'){
		div +='<div style="margin:5px 0;width:100%;">';  
		div +=    '<label for="url" class="formlabel">连接地址:</label>';
		div +=    '<input class="easyui-validatebox formInput" id="url" name="url" data-options="required:true" size=35 >';
		div +=    '<div style="clear:both;"></div>';
		div +='</div>';
	}
	
	
	div +='<div style="margin:5px 0;width:100%;">';  
	div +=    '<label for="classid" class="formlabel">排序:</label>';
	div +=    '<input class="easyui-validatebox formInput" id="classid" name="classid" data-options="required:true" size=20 >';
	div +=    '<div style="clear:both;"></div>';
	div +='</div>';
	
	
	
	
	div += createFormFooter(); 
	
	if(pId!="" && level=='1'){
		createDialogDivWithSize('mVersionMainTreeGrid', 'addMVersion','添加连接菜单', '',550,250,div);
	}else if(pId!="" && level=='2'){
		createDialogDivWithSize('mVersionMainTreeGrid', 'addMVersion','添加按钮', '',550,220,div);
	}
	else{
		createDialogDivWithSize('mVersionMainTreeGrid', 'addMVersion','添加目录', '',550,200,div);
	}
	
}

function createFormFooter(){

	var html ="";
    html += '<div style="border:#ccc 0px solid;margin-bottom:25px;width:95%;line-height:24px;margin-top:20px;">';
	
    html +=   '<div style="float:left;width:25%;">';
	html +=   '&nbsp;'; 
	html +=   '</div>';
	html +=   '<div style="float:left;width:35%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-database_save\'" onclick="addMV()" >确定</a>'; 
	html +=   '</div>';
	html +=   '<div style="border:#ccc 0px solid;float:left;width:30%;">';
	html +=   '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-2013040601125064_easyicon_net_16\'">重置</a>'; 
	html +=   '</div>';
	html +=   '<div style="clear:both;"></div>';
	html += '</div>';
	
	return html;
}

function addMV(){
	var name = $('#name').val();
	if(name.length <= 0){
		$.messager.alert('提示','name不能为空');
		return ;
	}
	var cdjb = 0;
	if(pId == '1'){
		cdjb = 0;
	}
	if(pId != '1'){
		cdjb = parseInt(level)+1;
	}
	
	var version="";
	if(level == '1'){
		version = $('#version').val();
	}else{
		version = "#";
	}
	if(version.length <= 0){
		$.messager.alert('提示','版本号不能为空');
		return ;
	}
	var url = "";
	if(level == '1' && level == '1'){
		url = $('#url').val();
	}else{
		url= "#";
	}
	
	if(url.length <= 0){
		$.messager.alert('提示','url不能为空');
		return ;
	}
	
	var classid= $('#classid').val();
	
	if(classid.length <= 0){
		$.messager.alert('提示','排序不能为空');
		return ;
	}
	
	var param = {Id:'dn0',name:name,cdjb:cdjb,url:url,version:version,pId:pId,classid:classid};
	
	$('#addMVersion').dialog('close');
	EasyUILoad('mainCenter');
	$.post("addXtcd.json",param,function(data,status){
		
		if(data.code == 10000){
			
			reloadTree();
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示','成功');
		}else{
			dispalyEasyUILoad( 'mainCenter' );
			$.messager.alert('提示',data.desc);
		}
		
	});
	
}

function delMVersion(){
	var node = $('#mVersionMainTreeGrid').treegrid('getSelected');
	if(null == node){
		$.messager.alert('提示','请先选择一个节点!');
		return ;
	}
	
	var xmdh = node.id;
	
	if(xmdh.length <=0 ){
		$.messager.alert('提示','请先选择一个节点!');
		return ;
	}
	
	var param = {Id:'dn0',xmdh:xmdh};
	$.messager.confirm('提示框','你确定要删除些节点，会影响到云平台的租户的菜单资源，请再确定？',function(r){
	    if (r){
	    	EasyUILoad('mainCenter');
	    	$.post("delXtcd.json",param,function(data,status){
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

function changeMVersionSfxs(status){
	var node = $('#mVersionMainTreeGrid').treegrid('getSelected');
	if(null == node){
		$.messager.alert('提示','请先选择一个节点!');
		return ;
	}
	
	var xmdh = node.id;
	
	if(xmdh.length <=0 ){
		$.messager.alert('提示','请先选择一个节点!');
		return ;
	}
	
	var param = {Id:'dn0',xmdh:xmdh,sfxs:status};
	
	$.messager.confirm('提示框','你确定要你要改变菜单状态，请再确定？',function(r){
	    if (r){
	    	EasyUILoad('mainCenter');
	    	$.post("changeXtcdSfxs.json",param,function(data,status){
	    		if(data.code == 10000){
	    		    //alert(data.desc);
	    			reloadTree();
	    			dispalyEasyUILoad('mainCenter');
	    			$.messager.alert('提示','成功');	
	    		}else{
	    			$.messager.alert('提示','失败');	
	    		}
	    	});
	    }
	});
}
