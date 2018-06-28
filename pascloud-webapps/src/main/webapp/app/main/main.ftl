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
    
    <script type="text/javascript" src="/static/js/lib/jquery.format.js"></script>
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
    
    <script type="text/javascript" src="/app/main/js/mainIndexPage.js"></script>
    
    
	<script type="text/javascript">
		$(function(){
		    init();
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
	
    <div id="mainCenter" data-options="region:'center',border:false"  style="padding:0px;background-color:#eee">
		<!--内容  开始-->
		<table id="mainDataGrid" >
		<div style="width:99.5%;heigth:auto;border:0px solid red;margin-top:10px;">
		    <div id="database_add" style="width:24%;height:150px;float:left;background-color:#fff;margin-left:0.8%;margin-right:0.4%;
		        border-radius:8px;box-shadow: 2px 2px 2px #888888;">
		        <div style="width:100%;line-height:30px;text-align:center;"><h2>新建数据库</h2></div>
		        <div style="width:80%;margin-left:10%;height:50%;">
		            <div style="width:60%;height:64px;background-color:#fff;float:left;line-height:25px;">
		                <div>数据库服务器数量：</div>
		                <div><font color="blue">${dbServers}</font> 台</div>
		                
		            </div>
		            <div style="width:28%;height:64px;background-color:#fff;border-radius:32px;float:right;">
		                <img src="/static/images/enter.png" width="100%" height="100%">
		            </div>
		            <div style="clear:both"></div>
                </div>
		    </div>
		    <div id="tenant_add" style="width:24%;height:150px;float:left;background-color:#fff;margin-left:0.4%;margin-right:0.4%;
		        border-radius:8px;box-shadow: 2px 2px 2px #888888;">
		        <div style="width:100%;line-height:30px;text-align:center;"><h2>一键开户</h2></div>
		        <div style="width:80%;margin-left:10%;height:50%;">
		            <div style="width:60%;height:64px;background-color:#fff;float:left;line-height:25px;">
		                <div>应用服务器数量：<font color="blue">${appServers}</font> 台</div>
		                <div>租户数量：<font color="blue">${dbs}</font> 个</div>
		            </div>
		            <div style="width:28%;height:64px;background-color:#fff;border-radius:32px;float:right;">
		                <img src="/static/images/enter.png" width="100%" height="100%">
		            </div>
		            <div style="clear:both"></div>
                </div>
		    </div>
		    <!--
		    <div style="width:24%;height:150px;float:left;background-color:#fff;margin-left:0.4%;margin-right:0.4%;
		        border-radius:8px;">
		    
		    </div>
		    <div style="width:24%;height:150px;float:left;background-color:#fff;margin-left:0.4%;
		        border-radius:8px;">
		    
		    </div>-->
		    <div style="clear:both"></div>
		</div>
		
		<div id="mainInfoLayout" style="height:auto;width:98%;margin-top:15px;margin-left:0.8%;
		    background-color:#fff;margin-bottom:10px;box-shadow: 2px 2px 2px #888888;
		    border-radius:8px;">
		    
		
		</div>
		</table>
		<!--内容  结束-->
	</div>
</body>
</html>