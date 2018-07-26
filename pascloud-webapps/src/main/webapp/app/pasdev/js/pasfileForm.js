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


function initUploadfile(){
	$('#file_upload').uploadify({
        'swf': '/static/js/uploadify/uploadify.swf',  //FLash文件路径
        'buttonText': '浏  览',                                 //按钮文本
        'uploader': '/FileUpload/Upload',                       //处理文件上传Action
        'queueID': 'fileQueue',                        //队列的ID
        'queueSizeLimit': 10,                          //队列最多可上传文件数量，默认为999
        'auto': false,                                 //选择文件后是否自动上传，默认为true
        'multi': true,                                 //是否为多选，默认为true
        'removeCompleted': true,                       //是否完成后移除序列，默认为true
        'fileSizeLimit': '10MB',                       //单个文件大小，0为无限制，可接受KB,MB,GB等单位的字符串值
        'fileTypeDesc': 'Image Files',                 //文件描述
        'fileTypeExts': '*.gif; *.jpg; *.png; *.bmp;*.tif;*.doc;*.xls;*.zip',  //上传的文件后缀过滤器
        'onQueueComplete': function (event, data) {                 //所有队列完成后事件
            
            $.messager.alert("提示", "上传完毕！");                                     //提示完成           
        },
        'onUploadStart' : function(file) {
            $("#file_upload").uploadify("settings", 'formData', { 'folder': '政策法规', 'guid': $("#Attachment_GUID").val() }); //动态传参数
        },
        'onUploadError': function (event, queueId, fileObj, errorObj) {
            //alert(errorObj.type + "：" + errorObj.info);
        }
    });
}






