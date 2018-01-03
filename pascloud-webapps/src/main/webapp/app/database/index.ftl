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
    
    <link rel="stylesheet" type="text/css" href="/static/js/lib/codemirror/codemirror.css">
    
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/js/lib/jquery.format.js"></script>
    
    <script type="text/javascript" src="/static/js/lib/codemirror/codemirror.js"></script>
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
     
	<script type="text/javascript" src="/app/database/js/databaseTree.js"></script>
    
	<script type="text/javascript">
		$(function(){
		    initDatabaseTree();
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
	
    <div id="mainLeft" data-options="region:'west',split:true,title:'数据库菜单',iconCls:'icon-folder'" style="width:180px">
		<!--树形菜单  开始-->
		<ul id="databaseTree" class="easyui-tree" >
		    <div id="mm-batabase" class="easyui-menu" style="width:120px;">
		            
		        <div onclick="removeIt()" data-options="iconCls:'icon-remove'">Remove</div>
		        <div class="menu-sep"></div>
		        <div onclick="collapse()">Collapse</div>
		        <div onclick="expand()">Expand</div>
	        </div>
		</ul>
		<!--树形菜单  结束-->
	</div>
	<div id="mainCenter" data-options="region:'center',title:''" style="padding:0px;">
	    <div class="easyui-layout" data-options="fit:true" style="border:0;">
	        <div  data-options="region:'center',title:''" style="border:0;">
	            <div id="mainEditor" class="easyui-layout" data-options="fit:true" style="border:0;">
	                <div  data-options="region:'center',title:'center'" style="border:0;">
	                    
	                </div>
	                <div  data-options="region:'south',title:'south'" style="height:150px;border:0;" >
	                    log
	                </div>
	            </div>
	        </div>
	        <div id="toolarea"  data-options="region:'east',title:'east'" style="width:150px;">
	            toolbar
	        </div>
	        
	    </div>
		
	</div>

</body>
</html>