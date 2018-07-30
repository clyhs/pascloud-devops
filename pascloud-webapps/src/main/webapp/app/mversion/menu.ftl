<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="/static/easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/IconExtension.css">
    
    <link rel="stylesheet" type="text/css" href="/static/js/rebox/jquery-rebox.css"/>
    <link rel="stylesheet" type="text/css" href="/static/js/webuploader/css/webuploader.css"/>
	<link rel="stylesheet" type="text/css" href="/static/js/webuploader/css/upload.css"/>
    
    <link id="themesUI" href="/static/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet"  type="text/css"/>
   
    
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="/static/js/webuploader/webuploader.js"></script>
	<script type="text/javascript" src="/static/js/rebox/jquery-rebox.js"></script>
	
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    
    <script type="text/javascript" type="text/javascript" src="/static/js/lib/jquery.format.js"></script>
    
    
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
    <script type="text/javascript" src="/app/mversion/js/main.js"></script>
    <script type="text/javascript" src="/app/mversion/js/mVersionTree.js"></script>
    <script type="text/javascript" src="/app/mversion/js/pasfileDataGrid.js"></script>
    <script type="text/javascript" src="/app/mversion/js/menuTreeGridForm.js"></script>
    <script type="text/javascript" src="/app/mversion/js/menuTreeGridToolbar.js"></script>
    <script type="text/javascript" src="/app/mversion/js/menuTreeGrid.js"></script>
    
    
	<script type="text/javascript">
		$(function(){
		    initMVersionTree();
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
<body id="main" class="easyui-layout" data-options="fit:true,border:false"  > 
    
    <div id="mainLeft" data-options="region:'west',split:true,title:'租户',iconCls:'icon-application_double'" style="width:200px">
		<table id="mVersionTree" class="easyui-treegrid" >
		</table>
	</div>

	<div id="mainCenter" data-options="region:'center'" style="padding:0px;">
		<!--内容  开始-->
		
		<table id="menuTreeGrid" class="easyui-treegrid" style="padding:0px;">	
		        
	    </table>
		<!--内容  结束-->
    </div>

</body>
</html>