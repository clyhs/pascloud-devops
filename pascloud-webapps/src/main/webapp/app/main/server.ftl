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
    
    <script type="text/javascript" src="/app/main/js/mainDataGridToolbar.js"></script>
    <script type="text/javascript" src="/app/main/js/mainDataGrid.js"></script>
    
	<script type="text/javascript">
		$(function(){
			initMainDataGrid();
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
<body id="main" class="easyui-layout" data-options="fit:true" > 
	

		<div id="mainCenter" data-options="region:'center',border:false" style="padding:0px;">
		    <!--内容  开始-->
		    <div id="mainGridLayout" class="easyui-layout" data-options="fit:true,iconCls:'icon-house'">
		        <div data-options="region:'center'">
		            <table id="mainDataGrid" >	
		        
	                </table>
		        </div>
		        <div id="mainInfoLayout" data-options="region:'south',split:true,
				    collapsed:false,title:'服务器监控',iconCls:'icon-cog_edit'" style="height:400px">
				    
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
			                        <th data-options="field:'value',width:100" formatter="formatStyle">属性值</th>
		                            </tr>
                                </thead>
                            </table>
	                    </div>
	                    </#list>
	                    
	                    
	                    <div style="clear:both;"></div>
	                </div>
	            </div>
		    </div>
		    <!--内容  结束-->
		</div>
		
	
	
</body>
</html>