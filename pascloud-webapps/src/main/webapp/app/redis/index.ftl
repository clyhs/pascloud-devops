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
     
    
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
    <script type="text/javascript" src="/app/redis/js/redisMain.js"></script>
    <script type="text/javascript" src="/app/redis/js/redisDBTree.js"></script>
    <script type="text/javascript" src="/app/redis/js/redisServerDetailDataGrid.js"></script>
	
	<script type="text/javascript">
	    
		$(function(){
		    redisServerId = '${redisServerId}';
		    initRedisTree();
		    initRSDetailDataGrid(redisServerId);
		    changeRedisServer();
		});
		
	</script>
	<style>
	    .CodeMirror {border:#ccc 1px solid; font-size:13px,line-height:20px;}
	</style>
	
	
</head>
<body id="main" class="easyui-layout" data-options="fit:true" > 
    <div id="mainLeft" data-options="region:'west',split:true,title:'reids服务器',iconCls:'icon-databases'" style="width:200px">
        <!--
		<div style="width:94%;line-height:30px;border:#ccc 5px solid;">
		    
		-->
		
		<div id="redisTreeToolbar" style="width:160px;margin:4px 5px 2px 5px;border:0;">
		    <select id="server" class="easyui-combobox" name="server" style="width:160px;">
		        <#list servers as item> 
		        <option value="${item}" <#if item==redisServerId>selected="selected"</#if>  >${item}</option>
		        </#list>  
		    </select>
		</div>
		
		<table id="redisTree" class="easyui-treegrid" >
		</table>
	    
		
		
	</div>
	<div id="mainCenter" data-options="region:'center',title:''" style="border:0;">
	    <div id="mainCenterTab" class="easyui-tabs" data-options="region:'center',title:'',fit:true" style="border:0;">
	        <div title="服务监控与详情" iconCls="icon-monitor" style="border:0;">
                <table id="redisServerDetailDataGrid" >	
		        
	            </table>
	        </div>
	        
	    </div>
	</div>
	
	

</body>
</html>

