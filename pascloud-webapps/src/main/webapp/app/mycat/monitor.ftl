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
    
    <script type="text/javascript" src="/app/mycat/js/monitor.js"></script>
    
	<script type="text/javascript">
		$(function(){
		    
		    var intervalId;
		    clearInterval(intervalId); 
            intervalId = setInterval(function() {
		        var url = $('#contentGraph').attr('src');
		        $('#contentGraph').attr('src', url+"&timestamp="+(new Date()).getTime());
	        }, 20000)
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
        

		<div id="mainCenter" data-options="region:'center'" style="padding:0px;">
		    <!--内容  开始-->
		    <div style="padding:5px;background:#fafafa;width:100%;border:0px solid #ccc">
		        <a href="#" class="easyui-linkbutton" iconCls="" onClick="change('1')">活动线程分析</a>
		        <a href="#" class="easyui-linkbutton" iconCls="" onClick="change('2')">缓冲队列分析</a>
		        <a href="#" class="easyui-linkbutton" iconCls="" onClick="change('3')">tps分析</a>
		        <a href="#" class="easyui-linkbutton" iconCls="" onClick="change('4')">内存分析</a>
		        <a href="#" class="easyui-linkbutton" onClick="change('5')">浏量分析</a>
		        <a href="#" class="easyui-linkbutton" iconCls="" onClick="change('6')">连接分析</a>
            </div>
		    <div style="width:100%;">	
		        <img id="contentGraph" src="/graph/D_192.168.0.7_9066/MycatActiveThreadGraph?probe=MycatThreadPool" height="380" width="100%">
	        </div>
		    <!--内容  结束-->
		</div>

</body>
</html>