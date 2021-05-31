<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>XXXXXXX管理系统</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/dist/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/iCheck/square/blue.css">
  <!-- common -->
  <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/dist/css/common.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="javascript:;">
      XXXXXXX管理系统
    </a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    [#if Session["SPRING_SECURITY_LAST_EXCEPTION"]??]
        <div class="alert alert-danger" role="alert">
            账号或密码错误
        </div>
    [/#if]
    <form action="${springMacroRequestContext.contextPath}/login" method="post">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
      <div class="form-group has-feedback login-input">
        <input type="text" name="username" class="form-control" placeholder="登录账号">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback login-input">
        <input type="password" name="password" class="form-control" placeholder="登录密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>

      <div class="row">
        <div class="col-xs-12">
          <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
  </div>
  <!-- /.login-series-body -->
</div>
<!-- /.login-series -->

<!-- jQuery 2.2.3 -->
<script src="${springMacroRequestContext.contextPath}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${springMacroRequestContext.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${springMacroRequestContext.contextPath}/plugins/iCheck/icheck.min.js"></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
</script>
</body>
</html>
