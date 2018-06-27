<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="/static/easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/IconExtension.css">
    <link rel="stylesheet" type="text/css" href="/static/js/uploadify/uploadify.css">
    
    <link id="themesUI" href="/static/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet"  type="text/css"/>
   
    
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="/static/js/uploadify/jquery.uploadify.min.js"></script>
	
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    
    <script type="text/javascript" src="/static/js/lib/jquery.format.js"></script>
    
    
    
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
    <script type="text/javascript" src="/app/pasdev/js/pasfileForm.js"></script>
    <script type="text/javascript" src="/app/pasdev/js/pasfileDataGridToolbar.js"></script>
    <script type="text/javascript" src="/app/pasdev/js/pasfileDataGrid.js"></script>
    <script type="text/javascript" src="/app/pasdev/js/pasfileTreeToolbar.js"></script>
    <script type="text/javascript" src="/app/pasdev/js/pasfileTree.js"></script>
    
	<script type="text/javascript">
		$(function(){
		    initMainDataGrid();
		    
		    initPasfileTree();
		    
		    initUploadfile();
		    
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
    <div id="mainLeft" data-options="region:'west',split:true,title:'PAS+目录',iconCls:'icon-databases'" style="width:200px">
		<table id="pasfileTree" class="easyui-treegrid" >
		</table>
	</div>

	<div id="mainCenter" data-options="region:'center'" style="padding:0px;">
		<!--内容  开始-->
		<!--
		<div>
           <input class="easyui-validatebox" type="hidden" id="Attachment_GUID" name="Attachment_GUID" />
           <input id="file_upload" name="file_upload" type="file" multiple="multiple">
           <a href="javascript:void(0)" class="easyui-linkbutton" id="btnUpload" data-options="plain:true,iconCls:'icon-save'"
                                    onclick="javascript: $('#file_upload').uploadify('upload', '*')">上传</a>
           <a href="javascript:void(0)" class="easyui-linkbutton" id="btnCancelUpload" data-options="plain:true,iconCls:'icon-cancel'"
                                    onclick="javascript: $('#file_upload').uploadify('cancel', '*')">取消</a>
           <div id="fileQueue" class="fileQueue"></div>
           <div id="div_files"></div>
           <br />
        </div>-->
		<table id="mainDataGrid" style="padding:0px;">	
		        
	    </table>
		    <!--内容  结束-->
    </div>

</body>
</html>