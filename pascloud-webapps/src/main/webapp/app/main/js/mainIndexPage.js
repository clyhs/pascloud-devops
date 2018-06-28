function init(){
	$('#database_add').click(function(){
		//addDB();
		parent.addTab('数据库管理','/module/dbserver/index.html','icon-databases');
	});
	$('#tenant_add').click(function(){
		parent.addTab('租户管理','/module/tenant/index.html','icon-application_double');
	});
}