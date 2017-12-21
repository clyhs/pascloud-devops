<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>云平台管理</title>
	<link rel="stylesheet" type="text/css" href="/static/easyui/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/IconExtension.css">
    
    <link id="themesUI" href="/static/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet"  type="text/css"/>
   
    
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    <script type="text/javascript" src="/app/main/js/mainDataGrid.js"></script>
    <script type="text/javascript" src="/app/main/js/mainLeft.js"></script>
    
	<script type="text/javascript">
		$(function(){
		    initTreeForLeftMenu();
		    
		    initMainDataGrid();
		});
		
	</script>
	
	<style type="text/css">
	    .formlabel{width:30%;text-align:right;float:left;}
	    .formInput{float:left;margin-left:10px;}
	    .border_right{border-right:#ccc 1px solid;}
	    .border_bottom{border-bottom:#ccc 1px solid;}
	
	</style>
</head>
<body id="main"> 
	
	<div class="easyui-layout" data-options="fit:true">
		<div id="mainLeft" data-options="region:'west',split:true,title:'菜单管理',iconCls:'icon-folder'" style="width:280px">
		    <!--树形菜单  开始-->
		    <ul id="leftMenu" class="easyui-tree" >
		        <div id="mm" class="easyui-menu" style="width:120px;">
		            
		            <div onclick="removeIt()" data-options="iconCls:'icon-remove'">Remove</div>
		            <div class="menu-sep"></div>
		            <div onclick="collapse()">Collapse</div>
		            <div onclick="expand()">Expand</div>
	            </div>
		    </ul>
		    <!--树形菜单  结束-->
		</div>
		
		
		<div id="mainCenter" class="easyui-tabs" data-options="region:'center'" style="padding:0px;">
		    <!--内容  开始-->
		    <div id="mainGridLayout" class="easyui-layout" data-options="fit:true,title:'首页',iconCls:'icon-house'">
		        <div data-options="region:'center'">
		            <table id="mainDataGrid" >	
		        
	                </table>
		        </div>
		    </div>
		    <!--内容  结束-->
		</div>
		
	</div>
	
</body>
</html>