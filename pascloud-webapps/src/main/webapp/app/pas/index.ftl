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
    
    <script type="text/javascript" src="/static/js/common/jquery.format.js"></script>
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
    <script type="text/javascript" src="/app/pas/js/pasForm.js"></script>
    <script type="text/javascript" src="/app/pas/js/pasDataGridToolbar.js"></script>
    <script type="text/javascript" src="/app/pas/js/pasDBDataGridToolbar.js"></script>
    <script type="text/javascript" src="/app/pas/js/pasDBDataGrid.js"></script>
    <script type="text/javascript" src="/app/pas/js/pasDataGrid.js"></script>
    
	<script type="text/javascript">
		$(function(){
		    initMainDataGrid();
		    
		    initEastDataGrid();
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
<body id="main" class="easyui-layout" data-options="fit:true,border:false" > 
	
	<!--<div class="easyui-layout" data-options="fit:true" style="padding:0px;">-->

		<div id="mainCenter" data-options="region:'center'" style="padding:0px;">
		    <!--内容  开始-->
		    <div id="mainGridLayout" class="easyui-layout" data-options="fit:true,title:'首页',iconCls:'icon-house'">
		        <div data-options="region:'center'">
		            <table id="mainDataGrid" >	
		        
	                </table>
		        </div>
		        <div id="infoLayout" data-options="region:'east',split:true,
				    collapsed:true,title:'数据库详情',iconCls:'icon-cog_edit'" style="width:300px">
				    <table id="eastDataGrid" >	
		        
	                </table>
				</div>
		    </div>
		    <!--内容  结束-->
		</div>
		
	<!--</div>-->
	
	<div id="pasSpringXml" class="easyui-dialog" title="XML配置文件" style="width:700px;height:400px;"
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">
        <textarea id='pasSpringXml_text' style="width:97%;height:97%;" ></textarea>
    </div>
	
</body>
</html>