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
    
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    
    <script type="text/javascript" src="/static/js/common/jquery.format.js"></script>
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
     
	<script type="text/javascript" src="/app/tool/js/toolContainerTree.js"></script>
    <script type="text/javascript" src="/app/tool/js/toolfunctions.js"></script>
    
	<script type="text/javascript">
		$(function(){
		    initContainerTree();
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
<body id="main" class="easyui-layout" data-options="fit:true"> 
	
    <div id="mainLeft" data-options="region:'west',split:true,title:'应用管理',iconCls:'icon-folder'" style="width:180px">
		<!--树形菜单  开始-->
		<ul id="containerTree" class="easyui-tree" >
		    <div id="mm-container" class="easyui-menu" style="width:120px;">
		            
		        <div onclick="removeIt()" data-options="iconCls:'icon-remove'">Remove</div>
		        <div class="menu-sep"></div>
		        <div onclick="collapse()">Collapse</div>
		        <div onclick="expand()">Expand</div>
	        </div>
		</ul>
		<!--树形菜单  结束-->
	</div>
	<div id="mainCenter" data-options="region:'center',title:'工具区'" style="padding:5px;">
		<button class="button button-primary button-box button-giant button-longshadow-right" onClick="javascript:onFileClick()">
            <i class="fa fa-file"></i>
        </button>
	</div>

		

	
	
</body>
</html>