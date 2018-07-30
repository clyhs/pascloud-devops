
String.prototype.endWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
     return false;
  if(this.substring(this.length-s.length)==s)
     return true;
  else
     return false;
  return true;
 }

 String.prototype.startWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
   return false;
  if(this.substr(0,s.length)==s)
     return true;
  else
     return false;
  return true;
 }

/**
 * 加载LOADING UI
 * 
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

function EasyUILoadForMsg(obj,msg) {
	$("<div class=\"datagrid-mask\"></div>")
	    .css({ display: "block", width:"100%",height:"auto !important"})
	    .appendTo($('#'+obj));
	$("<div class=\"datagrid-mask-msg\"></div>")
	    .html(msg+"。。。").appendTo($('#'+obj))
	    .css({ display: "block", left: ($('#'+obj).outerWidth(true) - 190) / 2, top: ($('#'+obj).height - 45) / 2 });
}

function EasyUILoadForMsgWithId(obj,msg,maskId) {
	$("<div class=\"datagrid-mask\"></div>")
	    .css({ display: "block", width:"100%",height:"auto !important"})
	    .appendTo($('#'+obj));
	$("<div id="+maskId+" class=\"datagrid-mask-msg\"></div>")
	    .html(msg+"。。。").appendTo($('#'+obj))
	    .css({ display: "block", left: ($('#'+obj).outerWidth(true) - 190) / 2, top: ($('#'+obj).height - 45) / 2 });
}

function EasyUILoadForMsgUpdate(msg,maskId){
	$("<div id="+maskId+" class=\"datagrid-mask-msg\"></div>")
    .html(msg+"。。。");
}


function EasyUILoadSimple(obj) {
	$("<div class=\"datagrid-mask\"></div>")
	    .css({ display: "block", width:"100%",height:"auto !important"})
	    .appendTo($('#'+obj));
	$("<div class=\"datagrid-mask-msg\"></div>")
	    .html("请稍候。。。").appendTo($('#'+obj))
	    .css({ display: "block", left: ($('#'+obj).outerWidth(true) - 190) / 2, top: ($('#'+obj).height - 45) / 2 });
}
/**
 * 隐藏LOADING UI
 * 
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
      
    // var url =
	// 'allpage/page/assistSystem/cashManager/cashSettlementManager/cashSettlementManagerLC/tvmCashSettlementDialog.jsp';
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

function createDialogDivWithSize(dialogFrame, dialogId,dialogTitle, dialogUrl,width,height,htmldiv)  
{  
	
    var div = $('#'+dialogFrame);  
    var html = '<div id="'+dialogId+'" style="overflow:hidden;"><form id="'+dialogId+'Form" method="post" style="width:100%;height:100%;">'+htmldiv+'</form></div>';  
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
        draggable : true,  
        href : url,  
        modal : true,  
        onClose : function()  
        {  
            $(this).dialog('destroy');  
        }  
    });  
} 



function createDialogSourceWithSize(dialogFrame, dialogId,dialogTitle, dialogUrl,width,height,htmldiv)  
{  
    var div = $('#'+dialogFrame);  
    var html = '<div id="'+dialogId+'">'+htmldiv+'</div>';  
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


var MaskUtil = (function(){  
    
    var $mask,$maskMsg;  
       
    var defMsg = '正在处理，请稍待。。。';  
       
    function init(){  
        if(!$mask){  
            $mask = $("<div class=\"datagrid-mask mymask\"></div>").appendTo($('#mainCenter'));  
        }  
        if(!$maskMsg){  
            $maskMsg = $("<div class=\"datagrid-mask-msg mymask\">"+defMsg+"</div>")  
                .appendTo($('#mainCenter')).css({'font-size':'12px'});  
        }  
           
        $mask.css({width:"100%",height:$('#mainCenter').height()});  
           
        var scrollTop = $('#mainCenter').scrollTop();  
           
        $maskMsg.css({ 
            left:( $('#mainCenter').outerWidth(true) - 190 ) / 2  
            ,top:( ($('#mainCenter').height() - 45) / 2 ) + scrollTop  
        });   
                   
    }  
       
    return {  
        mask:function(msg){  
            init();  
            $mask.show();  
            $maskMsg.html(msg||defMsg).show();  
        }  
        ,unmask:function(){  
            $mask.hide();  
            $maskMsg.hide();  
        }  
    }  
       
}());


