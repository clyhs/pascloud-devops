var treeRow = [];

function initTreeForLeftMenu(){
    EasyUILoad('leftMenu');
    $('#leftMenu').tree({
	    url:'/module/main/trees.json',
		method:'get',
		border:false,
		loadFilter:function(data){
		    dispalyEasyUILoad( 'leftMenu' );	
		    //alert(data);
		    treeRow = data;
		    return data;
		},
		onContextMenu: onContextMenu,
		onClick: onClick
	});
}
/**右键菜单**/
function onContextMenu(e,node){
    e.preventDefault();
	$(this).tree('select',node.target);
	if($(this).tree('isLeaf', node.target)){
	    $('#mm').menu('show',{
		    left: e.pageX,
		    top: e.pageY
	    });
	}
}
/**单击事件**/
function onClick(node){
    if($(this).tree('isLeaf', node.target)){
        var title = node.text;
        var url = node.url;
        //console.log(node);
        var icon = node.iconCls;
        //alert(node.url);
        addTab(title,url,icon);
    }
}



function addTab(title, url, icon){
	if ($('#mainCenter').tabs('exists', title)){
		$('#mainCenter').tabs('select', title);
	} else {
		var content = '<iframe scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:99.2%;"></iframe>';
		$('#mainCenter').tabs('add',{
			title:title,
			content:content,
			iconCls:icon,
			closable:true
		});
	}
}


function initAccordion(){
	$.ajax({
        type: "GET",
        async: false,
        cache: false,
        url: "parentTrees.json",
        data: "",
        success: function(data) {
        	treeRow = data;
        	$.each(data, function(i, n) {
        		var id = n.id;
                var text = n.text;
                var childUrl = '/module/main/childTrees.json?pid='+id;
                
                if(i == 0) { //显示第一个一级菜单下的二级菜单  
                    $('#leftMenu_accor').accordion('add', {
                        title: n.text,
                        iconCls: n.iconCls,
                        selected: true,
                        
                        //可在这加HTML代码，改变布局
                        content: '<div style="padding:0px;background-color:#eee"><ul class="easyui-tree" id="tree' + id + '" ></ul></div>',
                    });
                } else {
                    $('#leftMenu_accor').accordion('add', {
                        title: n.text,
                        iconCls: n.iconCls,
                        selected: false,
                        content: '<div style="padding:0px;background-color:#eee"><ul class="easyui-tree" id="tree' + id + '" ></ul></div>',
                    });
                }
                /*
                $.ajax({
                    type: 'GET',
                    async: false,
                    cache: false,
                    data: "",
                    url: 'childTrees.json?pid='+id,
                    success: function(data) {
                    	//alert($('#tree' + id))
                    	
                    	var treev = $('#tree' + id);
                    	treev.tree({
                            data: data,
                            animate: true,
                            formatter:function(node){
                        		return node.text;
                        	},
                            lines: true

                        });
                    },
                    error:function(){
                    	
                    }
                });*/
                /*
                $.parser.onComplete = function () { 
                	$.ajax({
                        type: 'GET',
                        async: false,
                        cache: false,
                        data: "",
                        url: 'childTrees.json?pid='+id,
                        success: function(data) {
                        	//alert($('#tree' + id))
                        	alert(data)
                        	var treev = $('#tree' + id);
                        	treev.tree({
                                data: data,
                                animate: true,
                                formatter:function(node){
                            		return node.text;
                            	},
                                lines: true,
                                onContextMenu: onContextMenu,
                        		onClick: onClick

                            });
                        },
                        error:function(){
                        	
                        }
                    });
                }*/
        	});
        	
        	
        },
        error: function() {
        }
    });
}



$.parser.onComplete = function () { 
	if(treeRow.length>0){
		$.each(treeRow, function(i, n) {
			var id = n.id;
			$.ajax({
                type: 'GET',
                async: false,
                data: "",
                url: 'childTrees.json?pid='+id,
                success: function(data) {
                	var treev = $('#tree' + id);
                	treev.tree({
                        data: data,
                        animate: true,
                        formatter:function(node){
                    		return node.text;
                    	},
                        lines: true,
                		onClick: onClick

                    });
                },
                error:function(){
                	
                }
            });
		});
	}
}

