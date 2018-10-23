
var dg;
var dg_running;
var d;

function add(){
	d=$("#dlg").dialog({   
	    title: '添加定时任务',    
	    width: 300,    
	    height: 200,    
	    href:'/module/scheduleJob/add.html',
	    modal:true,
	    buttons:[{
			text:'确认',
			handler:function(){
				$("#mainform").submit(); 
			}
		},{
			text:'取消',
			handler:function(){
					d.panel('close');
				}
		}]
	});
}

function stop(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.messager.confirm('提示', '确定要暂停任务？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"/module/scheduleJob/"+row.name+"/"+row.group+"/stop.json",
				success: function(data){
					if(data=='success'){
						dg.datagrid('reload');
						parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
					}else{
						parent.$.messager.alert(data);
					}  
				}
			});
		}
	});
}

function resume(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.messager.confirm('提示', '确定要恢复任务？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"/module/scheduleJob/"+row.name+"/"+row.group+"/resume.json",
				success: function(data){
					if(data=='success'){
						dg.datagrid('reload');
						parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
					}else{
						parent.$.messager.alert(data);
					}  
				}
			});
		}
	});
}

function deleteJob(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"/module/scheduleJob/"+row.name+"/"+row.group+"/delete.json",
				success: function(data){
					if(data=='success'){
						dg.datagrid('reload');
						$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
					}else{
						$.messager.alert(data);
					}  
				}
			});
		}
	});
}

function upd(){
	
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	var rowIndex=dg.datagrid('getRowIndex',row);
	dg.datagrid('beginEdit',rowIndex);
}

//确认修改
function sureUpd(row){
	$.messager.confirm('提示', '确定要修改任务？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"/module/scheduleJob/"+row.name+"/"+row.group+"/update.json",
				data:"cronExpression="+row.cronExpression,
				success: function(data){
					if(data=='success'){
						dg.datagrid('reload');
						$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
					}else{
						$.messager.alert(data);
					}  
				}
			});
			d.panel('close');
		}else{
			dg.datagrid('rejectChanges');
		}
	});
}


function startNow(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	$.messager.confirm('提示', '确定要立即运行一次该任务？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"/module/scheduleJob/"+row.name+"/"+row.group+"/startNow.json",
				success: function(data){
					if(data=='success'){
						//dg.datagrid('reload');
						$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
					}else{
						$.messager.alert(data);
					}  
				}
			});
		}
	});
}

/**
 * 是否选择行数据
 */
function rowIsNull(row){
	if(row){
		return false;
	}else{
		$.messager.show({ title : "提示",msg: "请选择行数据！", position: "bottomRight" });
		return true;
	}
}

function addCron(){
	parent.addTab('cron生成页','/module/scheduleJob/quartzCron.html','icon-add');
}