function initEDataGrid(url){
	
	$('#tableEditDataGrid').datagrid({
	    height: 'auto',
	    url: url,
	    method: 'get',
	    fit:true ,
	    border:false,
	    striped: true,
	    fitColumns: true,
	    singleSelect: true,
	    rownumbers: true,
	    nowrap: false,
	    showFooter: true,
	    toolbar:editTableToolbar,
	    columns: [[
	        { field: 'columnName', title: '字段名', width: 120, align: 'left',editor: { type: 'text', options: { required: true } }  },
	        { field: 'dataType', title: '字段类型', width: 80, align: 'left' ,editor: { type: 'text', options: { required: true } } },
	        { field: 'columnType', title: '长度', width: 80, align: 'left' ,editor: { type: 'text', options: { required: true } }, 
	        	formatter: function(value) {
                    var len = value.length;
                    var startIndex = value.indexOf("(");
                    var val = value.substring(startIndex+1,len-1);
	        		return val;
                }
	        
	        },
	        { field: 'columnComment', title: '字段注释', width: 80, align: 'left' ,editor: { type: 'text', options: { required: true } } },
	        { field: 'isNullable', title: '是否为空', width: 80, align: 'left',
	        	editor: { 
	        		type: 'checkbox', 
	        		options: { 
	        			required: true ,
	        			on: "YES",    
	                    off: "NO"  
	        		} 
	            }, 
	            formatter: function(value) {
                    if (value == 'YES') {
                        return '<input type="checkbox" checked=checked />';
                    } else {
                    	return '<input type="checkbox" />';
                    }
                }
	        	
	        },
	        { field: 'columnKey', title: '主键', width: 80, align: 'left' ,
	        	editor: { 
	        		type: 'checkbox', 
	        		options: { 
	        			required: true 
	        		} 
	            }, 
	            formatter: function(value) {
                    if (value == 'PRI') {
                        return '<input type="checkbox" checked=checked />';
                    } else {
                    	return '<input type="checkbox" />';
                    }
                }
	        
	        }
		       
	        
	     
	    ]],
	    onBeforeLoad: function (param) {
	    },
	    onLoadSuccess: function (data) {
	            
	    },
	    onAfterEdit: function (rowIndex, rowData, changes) {
            editRow = undefined;
        },
        onDblClickRow:function (rowIndex, rowData) {
            if (editRow != undefined) {
                $("#tableEditDataGrid").datagrid('endEdit', editRow);
            }
 
            if (editRow == undefined) {
                $("#tableEditDataGrid").datagrid('beginEdit', rowIndex);
                editRow = rowIndex;
            }
        },
        onClickRow:function(rowIndex,rowData){
            if (editRow != undefined) {
                $("#tableEditDataGrid").datagrid('endEdit', editRow);
 
            }
            
        }
	});
}