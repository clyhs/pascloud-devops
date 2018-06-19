
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
		//addDB();
		parent.addTab('数据库管理','/module/dbserver/index.html','icon-databases');
	});
	$('#tenant_add').click(function(){
		parent.addTab('租户管理','/module/tenant/index.html','icon-application_double');
	});
}