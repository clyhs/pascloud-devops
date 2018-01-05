var init = function() {
    var mime = 'text/x-mysql';
    sqlEditor = CodeMirror.fromTextArea(document.getElementById('sqlTextarea'), {
        mode: mime,
        indentWithTabs: true,
        smartIndent: true,
        //extraKeys: {"Ctrl": "autocomplete"},
        lineNumbers: true,
        matchBrackets : true,
        autofocus: true,
        extraKeys: {
             "'a'": completeAfter,
             "'b'": completeAfter,
             "'c'": completeAfter,
             "'d'": completeAfter,
             "'e'": completeAfter,
             "'f'": completeAfter,
             "'g'": completeAfter,
             "'h'": completeAfter,
             "'i'": completeAfter,
             "'j'": completeAfter,
             "'k'": completeAfter,
             "'l'": completeAfter,
             "'m'": completeAfter,
             "'n'": completeAfter,
             "'o'": completeAfter,
             "'p'": completeAfter,
             "'q'": completeAfter,
             "'r'": completeAfter,
             "'s'": completeAfter,
             "'t'": completeAfter,
             "'u'": completeAfter,
             "'v'": completeAfter,
             "'w'": completeAfter,
             "'x'": completeAfter,
             "'y'": completeAfter,
             "'z'": completeAfter,
             "'.'": completeAfter,
             "'='": completeIfInTag,
             // ,
             // "Ctrl-Space": "autocomplete",
            "Ctrl-Enter": "autocomplete",

            Tab: function(cm) {
                var spaces = Array(cm.getOption("indentUnit") + 1).join(" ");
                cm.replaceSelection(spaces);
            }
        }
    });
    sqlEditor.setSize('auto','230px');
    
    sqlEditor.on('change', function() {

    });
    sqlEditor.refresh();//动态设置或浏览器变动后保证editor的正确显示
    
    msgEditor = CodeMirror.fromTextArea(document.getElementById('msgTextarea'), {
        mode: mime,
        indentWithTabs: true,
        smartIndent: true,
        lineNumbers: true,
        matchBrackets : true,
        autofocus: false
    });
    msgEditor.setSize('auto','240px');
};

function completeIfInTag(cm) {
    return completeAfter(cm, function() {
        var tok = cm.getTokenAt(cm.getCursor());
        if (tok.type == "string" && (!/['"]/.test(tok.string.charAt(tok.string.length - 1)) || tok.string.length == 1)) return false;
        var inner = CodeMirror.innerMode(cm.getMode(), tok.state).state;
        return inner.tagName;
    });
}
function completeAfter(cm, pred) {
    var cur = cm.getCursor();
    if (!pred || pred()) setTimeout(function() {
        if (!cm.state.completionActive)
            cm.showHint({
                completeSingle: false
            });
    }, 100);
    return CodeMirror.Pass;
}

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