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
    
    
	<script type="text/javascript">
	
	   var url ="/module/redis/redisPageData.json?redisServerId=${redisServerId}&index=${index}";
	
       function test(){
           var key = $('#key').val();
           
           $('#tableDataGrid').datagrid('clearSelections'); 
           $('#tableDataGrid').datagrid('options').url = url; 
           $('#tableDataGrid').datagrid('load',{'selectKey' : key});
       }
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
		<div id="mainGridLayout" class="easyui-layout" data-options="fit:true,title:'首页',iconCls:'icon-house'">
		    <div data-options="region:'center'">
		        <table id="tableDataGrid" class="easyui-datagrid" 
				    data-options="url:'${url}',fitColumns:true,singleSelect:true,
				    iconCls:'icon-table',pagination:true,fit:true,
				    pageSize:50,pageList:[ 50, 100, 150, 200],border:0,toolbar:'#tb'" >
                    <thead>
		                <tr>
			            <th data-options="field:'key',width:100">属性</th>
			            <th data-options="field:'type',width:20">类型</th>
			            <th data-options="field:'value',width:100">属性值</th>
		                </tr>
                    </thead>
                </table>
                <div id="tb" style="padding:5px;height:auto">
		            <div>
			            关键字: <input id="key" name="key" class="easyui-textbox" style="width:150px">
			            <a href="#" id="btn" class="easyui-linkbutton" onclick="javascript:test()">搜索</a>
		            </div>
	            </div>
		    </div>
		</div>
		<!--内容  结束-->
	</div>

</body>
</html>

