<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="/static/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/IconExtension.css">
    
    <link id="themesUI" href="/static/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet"  type="text/css"/>
    <link id="themesUI" href="/static/css/buttons.css" rel="stylesheet"  type="text/css"/>
    <link href="http://cdn.bootcss.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    
    <link href="/static/css/common.css" rel="stylesheet"  type="text/css"/>
    
    
    <link rel="stylesheet" type="text/css" href="/static/js/lib/codemirror/codemirror.css">
    <link type="text/css" rel="stylesheet" href="/static/js/lib/codemirror/addon/hint/show-hint.css" />
    
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/js/lib/jquery.format.js"></script>
     
    
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
    <script type="text/javascript" src="/app/database/js/redisMain.js"></script>
    <script type="text/javascript" src="/app/database/js/redisDBTree.js"></script>
	
	<script type="text/javascript">
	    
		$(function(){
		});
		
	</script>
	<style>
	    .CodeMirror {border:#ccc 1px solid; font-size:13px,line-height:20px;}
	</style>
	
	
</head>
<body id="main" class="easyui-layout" data-options="fit:true" onload="init();"> 
    <div id="mainLeft" data-options="region:'west',split:true,title:'reids服务器',iconCls:'icon-databases'" style="width:200px">
		<div style="width:95%;line-height:30px;border:#ccc 5px solid;">
		    <select id="server" name="server" style="width:80%;margin:5px 5px;">
		        <#list servers as item> 
		        <option value="${item}" >${item}</option>
		        </#list>  
		    </select>
		</div>
		
		<ul id="redisTree" class="easyui-tree" >
		    <div id="mm-redis" class="easyui-menu" style="width:120px;">
		        <div data-options="iconCls:'icon-database_add'">添加库</div>
		        <div data-options="iconCls:'icon-database_delete'">删除库</div>
		        <div data-options="iconCls:'icon-database_go'">备份库</div>
	        </div>
		</ul>
	    
		
		
	</div>
	<div id="mainCenter" data-options="region:'center',title:''" style="border:0;">
	    center
	</div>
	
	

</body>
</html>

