function execAction(){
	//alert("执行");
	var codeVal = editor.getValue();
	//alert(editor.getValue()+codeVal);
	var param = {sql:codeVal,dsId:dsId};
	$.post("exec.json",param,function(data,status){
		if(data.code = 10000){
			//alert(data.desc);
			alert("成功");
		}
		
	});
}
function clearAction(){
	alert("清空");
}