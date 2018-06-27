
var toolbar = function(){
	return [{
		text : '同步上传',  
        iconCls : 'icon-20130406125647919_easyicon_net_16',  
        handler : function(){
        	alert('upload file');
        }
	},{
		text : '上传',  
        iconCls : 'icon-20130406125647919_easyicon_net_16',  
        handler : function(){
        	$('#file').uploadify('upload', '*');
        }
	}];
}();