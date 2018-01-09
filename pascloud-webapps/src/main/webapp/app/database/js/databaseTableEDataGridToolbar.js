var editTableToolbar = function(){
	return [{
		text : '添加',  
        iconCls : 'icon-add',  
        handler : function(){
        	if (editRow != undefined) {
                $("#tableEditDataGrid").datagrid('endEdit', editRow);
            }
            if (editRow == undefined) {
                $("#tableEditDataGrid").datagrid('insertRow', {
                    index: 0,
                    row: {}
                });

                $("#tableEditDataGrid").datagrid('beginEdit', 0);
                editRow = 0;
            }
        }
	},'-',{
		text: '保存', 
		iconCls: 'icon-save', 
		handler: function () {
            $("#tableEditDataGrid").datagrid('endEdit', editRow);
            var rows = $("#tableEditDataGrid").datagrid('getChanges');
            var rowstr = JSON.stringify(rows);
            $.post('', rowstr, function (data) {
                 
            });
        }
	},'-',{
		text: '撤销', 
		iconCls: 'icon-redo', 
		handler: function () {
            editRow = undefined;
            $("#tableEditDataGrid").datagrid('rejectChanges');
            $("#tableEditDataGrid").datagrid('unselectAll');
        }
	},'-',{
		text: '删除', iconCls: 'icon-remove', handler: function () {
            var row = $("#tableEditDataGrid").datagrid('getSelections');
             
        }
	},'-',{
		text: '修改', 
		iconCls: 'icon-edit', 
		handler: function () {
            var row = $("#tableEditDataGrid").datagrid('getSelected');
            if (row !=null) {
                if (editRow != undefined) {
                    $("#tableEditDataGrid").datagrid('endEdit', editRow);
                }

                if (editRow == undefined) {
                    var index = $("#tableEditDataGrid").datagrid('getRowIndex', row);
                    $("#tableEditDataGrid").datagrid('beginEdit', index);
                    editRow = index;
                    $("#tableEditDataGrid").datagrid('unselectAll');
                }
            } else {
                 
            }
        }
	}, '-', {
        text: '上移', iconCls: 'icon-arrow_up', handler: function () {
            MoveUp();
        }
    }, '-', {
        text: '下移', iconCls: 'icon-arrow_down', handler: function () {
            MoveDown();
        }
    }];
}();


function MoveUp() {
    var row = $("#tableEditDataGrid").datagrid('getSelected');
    var index = $("#tableEditDataGrid").datagrid('getRowIndex', row);
    mysort(index, 'up', 'tableEditDataGrid');
     
}
//下移
function MoveDown() {
    var row = $("#tableEditDataGrid").datagrid('getSelected');
    var index = $("#tableEditDataGrid").datagrid('getRowIndex', row);
    mysort(index, 'down', 'tableEditDataGrid');
     
}



function mysort(index, type, gridname) {
    if ("up" == type) {
        if (index != 0) {
            var toup = $('#' + gridname).datagrid('getData').rows[index];
            var todown = $('#' + gridname).datagrid('getData').rows[index - 1];
            $('#' + gridname).datagrid('getData').rows[index] = todown;
            $('#' + gridname).datagrid('getData').rows[index - 1] = toup;
            $('#' + gridname).datagrid('refreshRow', index);
            $('#' + gridname).datagrid('refreshRow', index - 1);
            $('#' + gridname).datagrid('selectRow', index - 1);
        }
    } else if ("down" == type) {
        var rows = $('#' + gridname).datagrid('getRows').length;
        if (index != rows - 1) {
            var todown = $('#' + gridname).datagrid('getData').rows[index];
            var toup = $('#' + gridname).datagrid('getData').rows[index + 1];
            $('#' + gridname).datagrid('getData').rows[index + 1] = todown;
            $('#' + gridname).datagrid('getData').rows[index] = toup;
            $('#' + gridname).datagrid('refreshRow', index);
            $('#' + gridname).datagrid('refreshRow', index + 1);
            $('#' + gridname).datagrid('selectRow', index + 1);
        }
    }
 
}
