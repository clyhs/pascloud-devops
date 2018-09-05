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
    
    <script>
        var defaultIp='${defaultIp}';
    </script>
    
    
    <script type="text/javascript" src="/app/dbserver/js/dbserverForm.js"></script>
    <script type="text/javascript" src="/app/dbserver/js/dbserverTree.js"></script>
    <script type="text/javascript" src="/app/dbserver/js/dbserverDataGridToolbar.js"></script>
    <script type="text/javascript" src="/app/dbserver/js/dbserverDataGrid.js"></script>
    
	<script type="text/javascript">
		$(function(){
		    initMainDataGrid();
		    initDbserverTree();
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
    <div id="mainLeft" data-options="region:'west',split:true,title:'数据库服务器',iconCls:'icon-databases'" style="width:180px">
		<table id="dbserverTree" class="easyui-treegrid" >
		</table>
	</div>

	<div id="mainCenter" data-options="region:'center'" style="padding:0px;">
		<!--内容  开始-->
		<table id="mainDataGrid" style="padding:0px;">	
		        
	    </table>
		    <!--内容  结束-->
    </div>
    
    <div id="menu" class="easyui-menu" style="width: 50px; display: none;">
        <div data-options="iconCls:'icon-database_go'" onclick="impDmpWithSidAndUser()">初始化</div>
        <div data-options="iconCls:'icon-delete'" onclick="deleteWithSidAndUser()">删除</div>
    </div>

</body>
</html>