<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="/static/easyui/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/IconExtension.css">
    
    <link id="themesUI" href="/static/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet"  type="text/css"/>
   
    
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
    <script type="text/javascript" src="/app/pas/js/pasDataGrid.js"></script>
    
	<script type="text/javascript">
		$(function(){
		
		    initMainDataGrid();
		});
		
	</script>
	
</head>
<body id="main"> 
	
	<div class="easyui-layout" data-options="fit:true">

		<div id="mainCenter" data-options="region:'center'" style="padding:0px;">
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