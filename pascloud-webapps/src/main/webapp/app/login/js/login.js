function loginAction(){
	var username = $('#username').val();
	var password = $('#password').val();
	
	if(username.length==0 || password.length == 0){
		$("#msg").css('display','block'); 
		$('#msg').html('请输入用户名和密码');
		return ;
	}
	var params = {username:username,password:password};
	
	$.post("/module/login/login.json",params,function(data,status){
		if(data.code == 10000){
			window.location.href="/module/main/index.html";
		}else{
			//alert('提示',data.desc);	
			$("#msg").css('display','block'); 
			$('#msg').html(data.desc);
		}
	});
}