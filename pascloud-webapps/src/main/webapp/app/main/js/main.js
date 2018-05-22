
var checkHealthId;
var healthflag = true;

checkHealthId =setInterval( function () {
    $.ajax({
        type: "GET",
        cache: false,
        url: "health.json",
        data: "",
        success: function() {
        	healthflag = true;
        },
        error: function() {
        	if(healthflag){
        		$.messager.alert('提示','服务器已经停止。请重启服务');
        	}
        	healthflag = false;
        }
    });
},2000);