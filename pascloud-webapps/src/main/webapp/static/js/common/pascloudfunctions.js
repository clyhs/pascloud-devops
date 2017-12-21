/**
 * 加载LOADING UI
 * @param obj
 * @returns
 */
function EasyUILoad(obj) {
	$("<div class=\"datagrid-mask\"></div>")
	    .css({ display: "block", width:"100%",height:"auto !important"})
	    .appendTo($('#'+obj));
	$("<div class=\"datagrid-mask-msg\"></div>")
	    .html("正在运行，请稍候。。。").appendTo($('#'+obj))
	    .css({ display: "block", left: ($('#'+obj).outerWidth(true) - 190) / 2, top: ($('#'+obj).height - 45) / 2 });
}
/**
 * 隐藏LOADING UI
 * @param obj
 * @returns
 */  
function dispalyEasyUILoad(obj) {
	
	$('#'+obj+" .datagrid-mask").remove();
	$('#'+obj+" .datagrid-mask-msg").remove();
}


function createDialog(dialogFrame, dialogId, dialogTitle, dialogUrl)  
{  
    var div = $('#'+dialogFrame);  
    var html = '<div id="'+dialogId+'"></div>';  
    div.empty();  
    div.append(html);  
    $.parser.parse(div);  
    var url = dialogUrl;  
      
    //var url = 'allpage/page/assistSystem/cashManager/cashSettlementManager/cashSettlementManagerLC/tvmCashSettlementDialog.jsp';  
    $("#"+dialogId).dialog(  
    {  
        title : dialogTitle,  
        width : '70%',  
        height : '70%',  
        closed : false,  
        cache : false,  
        draggable : false,  
        href : url,  
        modal : true,  
        onClose : function()  
        {  
            $(this).dialog('destroy');  
        }  
    });  
} 


function createDialogWithSize(dialogFrame, dialogId,dialogTitle, dialogUrl,width,height)  
{  
    var div = $('#'+dialogFrame);  
    var html = '<div id="'+dialogId+'"></div>';  
    div.empty();  
    div.append(html);  
    $.parser.parse(div);  
    var url = dialogUrl;  
        
    $("#"+dialogId).dialog(  
    {  
        title : dialogTitle,  
        width : width,  
        height : height,  
        closed : false,  
        cache : false,  
        draggable : false,  
        href : url,  
        modal : true,  
        onClose : function()  
        {  
            $(this).dialog('destroy');  
        }  
    });  
} 

/**
 * 创建表单对话框的方法（htmlDiv）
 * @param dialogFrame
 * @param dialogId
 * @param dialogTitle
 * @param dialogUrl
 * @param htmlDiv
 * @param width
 * @param height
 * @returns
 */
function createDialogWithSizeForCsz(dialogFrame, dialogId,dialogTitle, dialogUrl,htmlDiv,width,height)  
{  
    var div = $('#'+dialogFrame);  
    
    var html = '<div id="'+dialogId+'" data-options="iconCls:\'icon-cog_edit\'""><form id="'+dialogId+'Form" method="post" style="width:100%;height:100%;">'+htmlDiv+'</form></div>';  
    div.empty();  
    div.append(html);  
    $.parser.parse(div);  
    var url = dialogUrl;  
    
    $("#"+dialogId).dialog(  
    {  
        title : dialogTitle,  
        width : width,  
        height : height,  
        minWidth : 500,
        minimizable : true,
        maximizable : true,
        resizable : true,
        closed : false,  
        cache : false,  
        draggable : false,  
        href : url,  
        modal : true,  
        onClose : function()  
        {  
            $(this).dialog('destroy');  
        }  
    });  
} 

function createDialogWithSizeForQj(dialogFrame, dialogId,dialogTitle, dialogUrl,htmlDiv,width,height)  
{  
    var div = $('#'+dialogFrame);  
    
    var html = '<div id="'+dialogId+'" data-options="iconCls:\'icon-cog_edit\'"">'+htmlDiv+'</div>';  
    div.empty();  
    div.append(html);  
    $.parser.parse(div);  
    var url = dialogUrl;  
        
    $("#"+dialogId).dialog(  
    {  
        title : dialogTitle,  
        width : width,  
        height : height, 
        minWidth : 500,
        minimizable : true,
        maximizable : true,
        resizable : true,
        closed : false,  
        cache : false,  
        draggable : false,  
        href : url,  
        modal : true,  
        onClose : function()  
        {  
            $(this).dialog('destroy');  
        }  
    });
} 