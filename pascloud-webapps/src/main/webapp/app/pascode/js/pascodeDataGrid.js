
function initMainDataGrid(){
	EasyUILoad('mainCenter');
    $('#mainDataGrid').datagrid({
        height: 'auto',
        url: '/module/pascode/getpascode.json',
        method: 'get',
        fit:true ,
        idField: 'id',
        //queryParams:{'dirId':Id},
        border:false,
        striped: true,
        fitColumns: true,
        singleSelect: true,
        rownumbers: false,
        pagination: true,
        nowrap: false,
        pageSize: 20,
        pageList: [ 20, 50, 100],
        showFooter: true,
        columns: [[
            //{ field: 'ck', checkbox: true },
        	{ field: 'id', title: '编号', width: 10, align: 'center' },
        	{ field: 'name', title: '名称', width: 160, align: 'left' },
        	{ field: 'type', title: '类型', width: 40, align: 'left' ,formatter:formatOper },
        	{ field: 'createTime', title: '创建时间', width: 60, align: 'left',formatter: DateTimeFormatter},
        	{ field: 'size', title: '大小', width: 40, align: 'left',formatter:sizeFormatter},
        	{ field: 'selected', title: '状态', width: 30, align: 'center',formatter:selectFormatter}
        ]],
        toolbar:toolbar,
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
        	dispalyEasyUILoad( 'mainCenter' );	
        },
        onLoadError: function () {
        
        },
        onClickCell: function (rowIndex, field, value) {

        },onDblClickCell: function (rowIndex, field, value) {
           
        }
    });
}

function reloadTableWithID(){
	$('#mainDataGrid').datagrid('reload');
}

function formatOper(val,row,index){ 
	if (val == '1'){
		return '调度管家';
	} else if(val == '2'){
		return '公共服务';
	} else if(val == '4'){
		return '数据库中间件';
	} else if(val == '6'){
		return '前端服务';
	}else{
		return '其它服务';
	}
} 

function DateTimeFormatter(val,row,index) {
    if (val == undefined) {
        return "";
    }
    
    //var obj = eval('(' + "{Date: new Date(" + val + ")}" + ')');
    var dateValue = new Date();
    dateValue.setTime(val);
    if (dateValue.getFullYear() < 1900) {
        return "";
    }

    return dateValue.format("yyyy-MM-dd hh:mm:ss");
}

function sizeFormatter(val,row,index){
	if(val > 0){
		val = val / (1024 * 1024)
	}
	return Math.round(val*100)/100+'M';
}

function selectFormatter(val,row,index){
	if(val > 0){
		return "<font color=\"blue\" >&radic;</font>";
	}
	return "";
}

function deletePascode(){
	var row = $('#mainDataGrid').datagrid('getSelected'); 
	
	if(null==row){
		$.messager.alert('提示','请选择一行');
		return ;
	}
	
	var name = row.name;
	var id = row.id;
	var params = {name:name,id:id};
	
	$.messager.confirm('提示框','你确定要删除，请再确定？',function(r){
	    if (r){
	    	MaskUtil.mask();
	    	MaskUtil.mask('删除代码...'); 
	    	$.post("deletePascode.json",params,function(data,status){
				if(data.code == 10000){
					$('#mainDataGrid').datagrid('reload');//刷新
					$.messager.alert('提示',data.desc);
					MaskUtil.unmask(); 
				}else{
					$.messager.alert('提示',data.desc);
					MaskUtil.unmask(); 
				}
			});
	    }
	});
}


















Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}
