var init = function() {
    var mime = 'text/x-mysql';
    sqlEditor = CodeMirror.fromTextArea(document.getElementById('sqlTextarea'), {
        mode: mime,
        indentWithTabs: true,
        smartIndent: true,
        lineNumbers: true,
        matchBrackets : true,
        autofocus: true
    });
    sqlEditor.setSize('auto','230px');
    
    msgEditor = CodeMirror.fromTextArea(document.getElementById('msgTextarea'), {
        mode: mime,
        indentWithTabs: true,
        smartIndent: true,
        lineNumbers: true,
        matchBrackets : true,
        autofocus: true
    });
    msgEditor.setSize('auto','240px');
};

function execAction(){
	//alert("执行");
	
	var codeVal = sqlEditor.getValue().trim();
	//alert(editor.getValue()+codeVal);
	
	if(codeVal == ""){
		$.messager.alert('提示框','编辑区不能为空！','error');
		return ;
	}
	
	if(dsId == ""){
		$.messager.alert('提示框','请在右边选择一个数据库！','error');
		return ;
	}
	
	var param = {sql:codeVal,dsId:dsId};
	var url = 'exec.json';
	$.post("execHeader.json",param,function(data,status){
		if(data.code = 10000){
			//alert(data.desc);
			//alert(data.headers);
			var columns = createColumns(data);
			//alert(columns);
			createOutputDataGrid(columns,url,param);
		}
		
	});
}
function clearAction(){
	alert("清空");
}