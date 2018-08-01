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
    <script type="text/javascript" src="/app/mversion/js/mVersionTenantTreeDialog.js"></script>
    <script type="text/javascript" src="/app/mversion/js/mVersionMainTreeGridToolbar.js"></script>
    <script type="text/javascript" src="/app/mversion/js/mVersionMainTreeGrid.js"></script>
    <script type="text/javascript" src="/app/mversion/js/pasfileDataGrid.js"></script>
    <script type="text/javascript" src="/app/mversion/js/mVersionMainForm.js"></script>
    
	<script type="text/javascript">
		$(function(){
		    
		    initMVersionMainTreeGrid();
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
    <!--
    <div id="mainLeft" data-options="region:'west',split:true,title:'租户',iconCls:'icon-application_double'" style="width:200px">
		<table id="mVersionTree" class="easyui-treegrid" >
		</table>
	</div>-->

	<div id="mainCenter" data-options="region:'center'" style="padding:0px;">
		<!--内容  开始-->
		
		<table id="mVersionMainTreeGrid" class="easyui-treegrid" style="padding:0px;">	
		    <div id="mm" class="easyui-menu" style="width:120px;display:none;">
		        <div onclick="addMVersionDialog()" data-options="iconCls:'icon-add'">添加</div>
		        <div class="menu-sep"></div>
		        <div onclick="updateMVersion()" data-options="iconCls:'icon-edit'">修改</div>
		        <div onclick="delMVersion()" data-options="iconCls:'icon-delete'">删除</div>
	        </div>
	    </table>
		<!--内容  结束-->
    </div>

</body>
</html>