
function initMainDataGrid(){
	EasyUILoad('mainCenter');
	dg=$('#mainDataGrid').datagrid({
        height: 'auto',
        url: '/module/scheduleJob/jobs.json',
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
        autoEditing: true,          //该属性启用双击行时自定开启该行的编辑状态
        singleEditing: true,
        columns:[[    
	        {field:'name',title:'任务名',sortable:true,width:30},    
	        {field:'group',title:'任务组',sortable:true,width:30},
	        {field:'cronExpression',title:'cron表达式',sortable:true,width:70,editor: "text"},
	        {field:'status',title:'状态',sortable:true,width:30},
	        {field:'className',title:'任务类',sortable:true,width:100},
	        {field:'description',title:'描述',sortable:true,width:80}
	    ]],
        toolbar:toolbar,
        onBeforeLoad: function (param) {
        },
        onLoadSuccess: function (data) {
        	dispalyEasyUILoad( 'mainCenter' );	
        },
        onLoadError: function () {
        
        },
        onAfterEdit: function (rowIndex, rowData, changes) {
            //endEdit该方法触发此事件，保存代码是否这里写？？？
            console.info(rowData);
            sureUpd(rowData)
            editIndex = undefined;
        },
        onDblClickRow: function (rowIndex, rowData) {
            //双击开启编辑行
            if (editIndex != undefined) {
                dg.datagrid("endEdit", editRow);
            }
            if (editIndex == undefined) {
                dg.datagrid("beginEdit", rowIndex);
                editIndex = rowIndex;
            }
        }
    });
}