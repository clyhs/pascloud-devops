<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="/static/easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/IconExtension.css">
    
    <link id="themesUI" href="/static/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet"  type="text/css"/>
   
    
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/easyui/detailview/datagrid-detailview.js"></script>
    <script type="text/javascript" src="/static/js/lib/jquery.format.js"></script>
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>

    
    
	<script type="text/javascript">
		$(function(){
		});
		
	</script>
	<style>
	    .datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
	    .formlabel{width:30%;text-align:right;float:left;}
	    .formInput{float:left;margin-left:10px;}
	    .border_right{border-right:#ccc 1px solid;}
	    .border_bottom{border-bottom:#ccc 1px solid;}
	</style>
	
	
</head>
<body> 
    
<div>
	<form id="mainform" action="/module/scheduleJob/add.json" method="post">
		<table class="formTable">
			<tr>
				<td>任务名：</td>
				<td>
					<input type="hidden" name="id" value="${id }"/>
					<input id="name" name="name" class="easyui-validatebox" data-options="width: 150,required:'required'"> 
				</td>
			</tr>
			<tr>
				<td>任务组：</td>
				<td><input id="group" name="group" type="text" class="easyui-validatebox" data-options="width: 150,required:'required'"/></td>
			</tr>
			<tr>
				<td>表达式：</td>
				<td><input id="cronExpression" name="cronExpression" type="text" class="easyui-validatebox" data-options="width: 150,required:'required'"/></td>
			</tr>
			<tr>
				<td>任务类：</td>
				<td><input name="className" type="text" class="easyui-validatebox" data-options="width: 150,required:'required'"/></td>
			</tr>
		</table>
	</form>
</div>    
<script type="text/javascript">
//提交表单
$('#mainform').form({    
    onSubmit: function(){    
    	var isValid = $(this).form('validate');
		return isValid;	// 返回false终止表单提交
    },    
    success:function(data){   
    	if(data=='success'){
	    	dg.datagrid('reload');
			d.panel('close');
			$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
    	}else{
    		alert(data)
    	}
    }    
});    
</script>
</body>
</html>
