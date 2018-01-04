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
    <script type="text/javascript" src="/static/js/lib/codemirror/javascript/javascript.js"></script>
    <script type="text/javascript" src="/static/js/lib/codemirror/sql/sql.js"></script>
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
    <script type="text/javascript" src="/app/database/js/databaseEditorToolbar.js"></script>
    <script type="text/javascript" src="/app/database/js/databaseTableTrees.js"></script>
	<script type="text/javascript" src="/app/database/js/databaseTree.js"></script>
	
	<script type="text/javascript">
	    
		$(function(){
		    initDatabaseTree();
		    initDatabaseTableTrees();
		});
		
	</script>
	<style>
	    .CodeMirror {border:#ccc 1px solid; font-size:13px}
	    .datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
	    .formlabel{width:30%;text-align:right;float:left;}
	    .formInput{float:left;margin-left:10px;}
	    .border_right{border-right:#ccc 1px solid;}
	    .border_bottom{border-bottom:#ccc 1px solid;}
	</style>
	
	
</head>
<body id="main" class="easyui-layout" data-options="fit:true" onload="init();"> 
    <div id="mainLeft" class="easyui-tabs" data-options="region:'west',split:true,title:'数据库菜单',iconCls:'icon-databases'" style="width:180px">
		
		<div title="数据库" iconCls="icon-database_link" style="padding:5px;">
		    <ul id="databaseTree" class="easyui-tree" >
		    </ul>
	    </div>
	    <div title="数据表" iconCls="icon-database_table" closable="true" style="padding:5px;">
	        <ul id="databaseTableTrees" class="easyui-tree" >
		    </ul>
	    </div>
		
		<!--树形菜单  开始-->
		<!--
		<ul id="databaseTree" class="easyui-tree" >
		    <div id="mm-batabase" class="easyui-menu" style="width:120px;">
		            
		        <div onclick="removeIt()" data-options="iconCls:'icon-remove'">Remove</div>
		        <div class="menu-sep"></div>
		        <div onclick="collapse()">Collapse</div>
		        <div onclick="expand()">Expand</div>
	        </div>
		</ul>-->
		<!--树形菜单  结束-->
	</div>
	<div id="mainCenter" data-options="region:'center',title:''" style="border:0;">
	    <div id="mainCenterTab" class="easyui-tabs" data-options="region:'center',title:'',fit:true" style="border:0;">
	        <div title="编辑区" iconCls="icon-layout_edit" style="border:0;">
	            <div class="easyui-layout"  data-options="fit:true" style="border:0;">
	                
	                <div data-options="region:'center',title:'',fit:true" >
	                    <div id="tb">
                            <a href="#" class="easyui-linkbutton" iconCls="icon-play_green" plain="true" onclick="execAction()">执行</a>
	                        <a href="#" class="easyui-linkbutton" iconCls="icon-cross" plain="true"  onclick="clearAction()">清空</a>
                        </div>
	                    <textarea id="code" name="code" style="height:250px;"></textarea>
	                </div>
	                <div data-options="region:'south',title:'',split:false,collapsible:false" style="height:280px;border:0;" >
	                    <div class="easyui-tabs" data-options="region:'center',fit:true">
	                        <div title="消息区" iconCls=" icon-2012080407553 " style="padding:5px;">
	                        </div>
	                        <div title="执行结果" iconCls="icon-application_view_detail" closable="true" style="padding:5px;">
	                        </div>
	                    </div>
	                </div>
	            </div>
	        
	        </div>
	        
	    </div>
	</div>
	<div id="toolarea"  data-options="region:'east',title:'工具区',iconCls:' icon-1012333 '" style="width:150px;">
	   toolbar
	</div>
	

</body>
</html>

<script>
var init = function() {
    var mime = 'text/x-mysql';

    editor = CodeMirror.fromTextArea(document.getElementById('code'), {
        mode: mime,
        indentWithTabs: true,
        smartIndent: true,
        lineNumbers: true,
        matchBrackets : true,
        autofocus: true
    });
    editor.setSize('auto','280px');
};
</script>