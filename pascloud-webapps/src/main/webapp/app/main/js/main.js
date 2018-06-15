
var checkHealthId;
var healthflag = true;

checkHealthId =setInterval( function () {
    $.ajax({
        type: "GET",
        cache: false,
        url: "health.json",
        data: "",
        success: function(data) {
        	console.log(data);
        	healthflag = true;
        	if(data.code!=10000){
        		window.location.href="/module/login/index.html";
        	}
        },
        error: function() {
        	if(healthflag){
        		$.messager.alert('提示','服务器已经停止。请重启服务');
        	}
        	healthflag = false;
        }
    });
},2000);

function exitAction(){
	$.post("/module/login/exit.json",{},function(data,status){
		if(data.code == 10000){
			window.location.href="/module/login/index.html";
		}
	});
}




function init(){
	$('#database_add').click(function(){
		addDB();
	});
	$('#tenant_add').click(function(){
		addSelectDbDialog();
	});
}