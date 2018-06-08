<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>绩效运维管理中心</title>
	<link rel="stylesheet" type="text/css" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/login.css">
	<script type="text/javascript" src="/static/easyui/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    
    <script type="text/javascript" src="/app/login/js/login.js"></script>
    
    <script type="text/javascript">
        
    </script>
</head>
<body>
    <div class="wrapper">
    <form class="form-signin">       
      <h2 class="form-signin-heading">登录</h2>
      <input type="text" class="form-control" name="username" id="username" placeholder="Email Address" required="" autofocus="" />
      <input type="password" class="form-control" name="password" id="password" placeholder="Password" required=""/>      
      <label class="checkbox">
        <input type="checkbox" value="remember-me" id="rememberMe" name="rememberMe"> 是否记住
      </label>
      <button id="login" class="btn btn-lg btn-primary btn-block" type="button" onClick="loginAction()">登录</button>  
      <div id="msg" style="margin-top:5px;color:red;display:none;"></div>
    </form>
    
    </div>
</body>
</html>
