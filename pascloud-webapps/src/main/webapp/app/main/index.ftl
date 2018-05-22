<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>云平台运维管理中心</title>
	<link rel="stylesheet" type="text/css" href="/static/easyui/themes/bootstrap/easyui.css">
	
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/IconExtension.css">
    
    <link rel="stylesheet" type="text/css" href="/static/css/common.css">
    
    <link id="themesUI" href="/static/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet"  type="text/css"/>
   
    
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    
    <script type="text/javascript" src="/static/js/common/pascloudfunctions.js"></script>
    
    
    <script type="text/javascript" src="/app/main/js/mainDataGridToolbar.js"></script>
    <script type="text/javascript" src="/app/main/js/mainDataGrid.js"></script>
    <script type="text/javascript" src="/app/main/js/mainLeft.js"></script>
    <script type="text/javascript" src="/app/main/js/main.js"></script>
    
	<script type="text/javascript">
		$(function(){
		    initTreeForLeftMenu();
		    initMainDataGrid();

		    $('#mainCenter').tabs({
                onContextMenu:function(e, title,index){
                    e.preventDefault();
                    if(index>0){
                        $('#mm-tab').menu('show', {
                            left: e.pageX,
                            top: e.pageY
                        }).data("tabTitle", title);
                    }
                }
            });
            
            $('#mm-tab').menu({
                onClick : function (item) {
                    closeTab(this, item.name);
                }
            });
            //setInterval('NetPing()',2000);
            
            
		 });
		 
		 
		
	</script>
	
	<style type="text/css">
	    .formlabel{width:30%;text-align:right;float:left;}
	    .formInput{float:left;margin-left:10px;}
	    .border_right{border-right:#ccc 1px solid;}
	    .border_bottom{border-bottom:#ccc 1px solid;}
	</style>
</head>
<body id="main" class="easyui-layout">
    <div data-options="region:'north'" style="height:50px;background-color:#99FFFF;border:0;" class="top">
        <!--
        <div class="logo" style="float:left;width:50px;"></div>-->
        <div class="" style="float:left;width:300px;text-align:left;color:#ffffff;margin-left:20px;"><h2>云平台运维管理中心</h2></div>
        <div class="" style="clear:both;"></div>
    </div>
    <div id="mainLeft" data-options="region:'west',split:true,title:'菜单管理',iconCls:'icon-folder'" style="width:180px">
        <!--树形菜单  开始-->
		<ul id="leftMenu" class="easyui-tree" >
		    <div id="mm" class="easyui-menu" style="width:120px;display:none;">
		            
		        <div onclick="removeIt()" data-options="iconCls:'icon-remove'">Remove</div>
		        <div class="menu-sep"></div>
		        <div onclick="collapse()">Collapse</div>
		        <div onclick="expand()">Expand</div>
	        </div>
		</ul>
		<!--树形菜单  结束-->
    </div>
    <div id="center" data-options="region:'center',border:false" >
        <div id="mainCenter" class="easyui-tabs" data-options="region:'center',fit:true" >
            <div id="mainGridLayout" class="easyui-layout" data-options="title:'首页',fit:true,iconCls:'icon-house'">
                
		        <div data-options="region:'center',fit:true">
		            <iframe scrolling="auto" frameborder="0"  src="/module/main/server.html" style="width:100%;height:100%;"></iframe>
		        </div>
		        
		        
            </div>
        </div>
        
        <div id="mm-tab" class="easyui-menu" style="width: 120px;display:none;">
            <div id="mm-tabrefresh" name="6">刷新</div>
            <div id="mm-tabclose" name="1">关闭</div>
            <div id="mm-tabcloseall" name="2">全部关闭</div>
            <div id="mm-tabcloseother" name="3">除此之外全部关闭</div>
            <div class="menu-sep"></div>
            <div id="mm-tabcloseright" name="4">当前页右侧全部关闭</div>
            <div id="mm-tabcloseleft" name="5">当前页左侧全部关闭</div>
        </div>
    </div>
	
</body>
</html>
<script>
        //删除Tabs
        function closeTab(menu, type) {
            
            var allTabs = $("#mainCenter").tabs('tabs');
            var allTabtitle = [];
            $.each(allTabs, function (i, n) {
                var opt = $(n).panel('options');
                if (opt.closable)
                    allTabtitle.push(opt.title);
            });
            var curTabTitle = $(menu).data("tabTitle");
            
            var curTabIndex = $('#mainCenter').tabs("getTabIndex", $('#mainCenter').tabs("getTab", curTabTitle));
            
            switch (type) {
                case '1':
                    $('#mainCenter').tabs('close', curTabIndex);
                    return false;
                    break;
                case '2':
                    for (var i = 0; i < allTabtitle.length; i++) {
                        $('#mainCenter').tabs('close', allTabtitle[i]);
                    }
                    break;
                case '3':
                    for (var i = 0; i < allTabtitle.length; i++) {
                        if (curTabTitle != allTabtitle[i])
                            $('#mainCenter').tabs('close', allTabtitle[i]);
                    }
                    $('#mainCenter').tabs('select', curTabTitle);
                    break;
                case '4':
                    for (var i = curTabIndex; i < allTabtitle.length; i++) {
                        $('#mainCenter').tabs('close', allTabtitle[i]);
                    }
                    $('#mainCenter').tabs('select', curTabTitle);
                    break;
                case '5': 
                    for (var i = 0; i < curTabIndex - 1; i++) {
                        $('#mainCenter').tabs('close', allTabtitle[i]);
                    }
                    $('#mainCenter').tabs('select', curTabTitle);
                    break;
                case '6': 
                    var panel = $("#mainCenter").tabs("getTab", curTabTitle).panel("refresh");
                    break;
            }

        }

</script>