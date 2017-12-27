<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>云平台管理</title>
	<link rel="stylesheet" type="text/css" href="/static/easyui/themes/bootstrap/easyui.css">
	
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/IconExtension.css">
    
    <link id="themesUI" href="/static/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet"  type="text/css"/>
   
    
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
    
    <script type="text/javascript" src="/app/main/js/mainDataGridToolbar.js"></script>
    <script type="text/javascript" src="/app/main/js/mainDataGrid.js"></script>
    <script type="text/javascript" src="/app/main/js/mainLeft.js"></script>
    
	<script type="text/javascript">
		$(function(){
		    initTreeForLeftMenu();
		    
		    initMainDataGrid();
		    
		    //initServerDataGrid();
		});
		
	</script>
	
	<style type="text/css">
	    .formlabel{width:30%;text-align:right;float:left;}
	    .formInput{float:left;margin-left:10px;}
	    .border_right{border-right:#ccc 1px solid;}
	    .border_bottom{border-bottom:#ccc 1px solid;}
	
	</style>
</head>
<body id="main"> 
	
	<div class="easyui-layout" data-options="fit:true">
		<div id="mainLeft" data-options="region:'west',split:true,title:'菜单管理',iconCls:'icon-folder'" style="width:280px">
		    <!--树形菜单  开始-->
		    <ul id="leftMenu" class="easyui-tree" >
		        <div id="mm" class="easyui-menu" style="width:120px;">
		            
		            <div onclick="removeIt()" data-options="iconCls:'icon-remove'">Remove</div>
		            <div class="menu-sep"></div>
		            <div onclick="collapse()">Collapse</div>
		            <div onclick="expand()">Expand</div>
	            </div>
		    </ul>
		    <!--树形菜单  结束-->
		</div>
		
		
		<div id="mainCenter" class="easyui-tabs" data-options="region:'center'" style="padding:0px;">
		    <!--内容  开始-->
		    <div id="mainGridLayout" class="easyui-layout" data-options="fit:true,title:'首页',iconCls:'icon-house'">
		        <div data-options="region:'center'">
		            <table id="mainDataGrid" >	
		        
	                </table>
		        </div>
		        
		        <div id="mainInfoLayout" data-options="region:'south',split:true,
				    collapsed:false,title:'服务器详情',iconCls:'icon-cog_edit'" style="height:400px">
				    
				    <div style="width:100%;line-height:30px;">
				        	
				        <#list nodes as node> 
				        
				        <div style="width:30%;margin:20px 10px;border:#99FFFF 1px solid;height:auto;float:left;">
				            <div style="width:100%;line-height:30px;height:auto;border:#ccc 0px solid;
				                background-color:#99FFFF;">&nbsp;&nbsp;${node.hostname}</div>
				            <table class="easyui-datagrid" style="height:250px;" 
				                data-options="url:'/module/docker/getServerInfo.json?index='+${node_index},
				                fitColumns:true,singleSelect:true">
                                <thead>
		                            <tr>
			                        <th data-options="field:'key',width:60">属性名称</th>
			                        <th data-options="field:'value',width:100">属性值</th>
		                            </tr>
                                </thead>
                            </table>
	                    </div>
	                    </#list>
	                    
	                    
	                    <div style="clear:both;"></div>
	                </div>
	                
				    
				    <!--
				    <div style="width:100%;line-height:30px;">	
		                <#list servers as server> 
		                <div style="width:30%;margin:20px 10px;border:#ccc 1px solid;height:auto;float:left;">
		                    <div style="width:100%;border:#ccc 0px solid;">
		                        <span style="float:left;width:30%;text-align:right;">服务器名称:&nbsp;&nbsp;</span>
		                        <span style="float:left;width:70%;">&nbsp;&nbsp;${server.hostname}</span>
		                        <div style="clear:both;"></div>
		                    </div>
		                    <div style="width:100%;">
		                        <span style="float:left;width:30%;text-align:right;">服务器IP:&nbsp;&nbsp;</span>
		                        <span style="float:left;width:70%;">&nbsp;&nbsp;${server.ip}</span>
		                        <div style="clear:both;"></div>
		                    </div>
		                    <div style="width:100%;">
		                        <span style="float:left;width:30%;text-align:right;">CPU空闲率:&nbsp;&nbsp;</span>
		                        <span style="float:left;width:70%;">&nbsp;&nbsp;${server.cpu_idle}</span>
		                        <div style="clear:both;"></div>
		                    </div>
		                    <div style="width:100%;">
		                        <span style="float:left;width:30%;text-align:right;">总内存:&nbsp;&nbsp;</span>
		                        <span style="float:left;width:70%;">&nbsp;&nbsp;${server.memory_total}</span>
		                        <div style="clear:both;"></div>
		                    </div>
		                    <div style="width:100%;">
		                        <span style="float:left;width:30%;text-align:right;">剩余内存:&nbsp;&nbsp;</span>
		                        <span style="float:left;width:70%;">&nbsp;&nbsp;${server.memory_free}</span>
		                        <div style="clear:both;"></div>
		                    </div>
		                    <div style="width:100%;">
		                        <span style="float:left;width:30%;text-align:right;">服务器OS:&nbsp;&nbsp;</span>
		                        <span style="float:left;width:70%;">&nbsp;&nbsp;${server.os}</span>
		                        <div style="clear:both;"></div>
		                    </div>
		                </div>
		                </#list>
		                <div style="clear:both;"></div>
	                </div>
	                
				</div>-->
		    </div>
		    <!--内容  结束-->
		</div>
		
	</div>
	
</body>
</html>