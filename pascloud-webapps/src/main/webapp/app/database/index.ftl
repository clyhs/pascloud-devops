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
    
    <link href="/static/css/common.css" rel="stylesheet"  type="text/css"/>
    
    
    <link rel="stylesheet" type="text/css" href="/static/js/lib/codemirror/codemirror.css">
    <link type="text/css" rel="stylesheet" href="/static/js/lib/codemirror/addon/hint/show-hint.css" />
    
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/js/lib/jquery.format.js"></script>
    
    <script type="text/javascript" src="/static/js/lib/codemirror/codemirror.js"></script>
    <script type="text/javascript" src="/static/js/lib/codemirror/mode/javascript/javascript.js"></script>
    <script type="text/javascript" src="/static/js/lib/codemirror/mode/sql/sql.js"></script>
    <script type="text/javascript" src="/static/js/lib/codemirror/addon/hint/show-hint.js"></script> 
    <script type="text/javascript" src="/static/js/lib/codemirror/addon/hint/sql-hint.js"></script>   
    <script type="text/javascript" src="/static/js/lib/codemirror/addon/display/placeholder.js"></script>  
    
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
    <script type="text/javascript" src="/app/database/js/databaseMain.js"></script>
    <script type="text/javascript" src="/app/database/js/databaseOutputDataGrid.js"></script>
    <script type="text/javascript" src="/app/database/js/databaseSqlEditor.js"></script>
    <script type="text/javascript" src="/app/database/js/databaseTableTrees.js"></script>
    <script type="text/javascript" src="/app/database/js/databaseTreeToolbar.js"></script>
	<script type="text/javascript" src="/app/database/js/databaseTree.js"></script>
	<script type="text/javascript" src="/app/database/js/databaseGrammarTree.js"></script>
	
	<script type="text/javascript">
	    
		$(function(){
		    initDatabaseTree();
		    initDatabaseTableTrees();
		    initDatabaseGrammarTrees();
		});
		
	</script>
	<style>
	    .CodeMirror {border:#ccc 1px solid; font-size:13px,line-height:20px;}
	</style>
	
	
</head>
<body id="main" class="easyui-layout" data-options="fit:true" onload="init();"> 
    <div id="mainLeft" class="easyui-tabs" data-options="region:'west',split:true,title:'数据库菜单',iconCls:'icon-databases'" style="width:200px">
		
		<div title="数据库" iconCls="icon-database_link" style="padding:5px;width:100%;" >
		    <ul id="databaseTree" class="easyui-tree" >
		    </ul>
	    </div>
	    <div id="databaseTableDiv" title="数据表" iconCls="icon-database_table" closable="true" style="padding:5px;width:100%;" >
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
	                
	                <div data-options="region:'center',title:'',fit:true" style="padding:5px;"  >
	                    <div id="tb" style="padding-bottom:5px;">
                            <a href="#" class="easyui-linkbutton" iconCls="icon-database_yellow_start" plain="true" onclick="execAction()">执行</a>
	                        <a href="#" class="easyui-linkbutton" iconCls="icon-cross" plain="true"  onclick="clearAction()">清空</a>
	                        <a href="#" class="easyui-linkbutton" iconCls="icon-database_save" plain="true"  onclick="saveAction()">保存</a>
                        </div>
	                    <textarea id="sqlTextarea" name="sqlTextarea" ></textarea>
	                </div>
	                <div data-options="region:'south',title:'',split:false,collapsible:false" style="height:280px;border:0;" >
	                    <div class="easyui-tabs" data-options="region:'center',fit:true">
	                        <div title="消息区" iconCls=" icon-2012080407553 " style="padding:5px;">
	                            <textarea id="msgTextarea" name="msgTextarea" ></textarea>
	                        </div>
	                        <div title="执行结果" iconCls="icon-application_view_detail" closable="true" style="padding:5px;">
	                            <table id="outputDataGrid">
	                            </table>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        
	        </div>
	        
	    </div>
	</div>
	<div id="toolaccor" class="easyui-accordion"  data-options="region:'east',title:'工具区',iconCls:' icon-1012333 '" style="width:180px;">
	   <div title="commonTool" data-options="iconCls:'icon-help',title:'常用语法'" style="overflow:auto;padding:5px;">
	       <ul id="sqlGrammarTree" class="easyui-tree" >
	       </ul>
	   </div>
	   <div title="querySql" data-options="iconCls:'icon-database_save',title:'我的SQL'" style="overflow:auto;padding:5px;">
	       <table id="mySqlDataGrid" class="easyui-datagrid" 
				data-options="url:'',fitColumns:true,singleSelect:true,
				iconCls:'icon-table',pagination:false,fit:true,border:0" >
                <thead>
		            <tr>
		            <th data-options="field:'id',width:40">编号</th>
			        <th data-options="field:'name',width:100">名称</th>
			        <th data-options="field:'opt',width:40">操作</th>
		            </tr>
                </thead>
            </table>
	   </div>
	</div>
	

</body>
</html>

