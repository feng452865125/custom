[#include "./pages/passwordModal.ftl" /]
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>XXXXXXX管理系统</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.12/css/all.css" integrity="sha384-G0fIWCsCzJIMAVNQPfjH08cyYaUtMwjJwqiRKxxE/rx96Uroj1BtIQ6MLJuheaO9" crossorigin="anonymous">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="dist/css/AdminLTE.css">
    <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
          page. However, you can choose any other skin. Make sure you
          apply the skin class to the body tag so the changes take effect.
    -->
    <link rel="stylesheet" href="dist/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="plugins/noty/noty.css">
    <link rel="stylesheet" href="dist/css/common.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- handlebars -->
    <script src="plugins/handlebars/handlebars-v4.0.11.js"></script>
</head>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <header class="main-header">

        <!-- Logo -->
        <a href="javascript:;" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini">菜单</span>
            <!-- logo for regular state and mobile devices -->
        [#--<span class="logo-lg">物业管理系统</span>--]
            <img src="" class="logo-lg" style="height: 50px; width: 95px;" alt="">
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <i class="fas fa-bars"></i>
                <span class="sr-only">Toggle navigation</span>
            </a>

            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <!-- User Account Menu -->
                    <li class="dropdown user user-menu">
                        <!-- Menu Toggle Button -->
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <!-- The user image in the navbar-->
                            <img src="dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
                            <!-- hidden-xs hides the username on small devices so only the image appears. -->
                            <span class="hidden-xs">${username}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- The user image in the menu -->
                            <li class="user-header">
                                <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat" data-toggle="modal"
                                       data-target="#password-modal">修改密码</a>
                                </div>
                                <div class="pull-right">
                                    <a href="${springMacroRequestContext.contextPath}/logout"
                                       class="btn btn-default btn-flat">登出</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">

        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

            <!-- Sidebar user panel (optional) -->
        [#--<div class="user-panel">--]
        [#--<div class="pull-left image">--]
        [#--<img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">--]
        [#--</div>--]
        [#--<div class="pull-left info">--]
        [#--<p>${username}</p>--]
        [#--</div>--]
        [#--</div>--]

            <!-- Sidebar Menu -->
            [#include "component/menu.ftl" /]
            <!-- /.sidebar-menu -->
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div id="content-wrapper" class="content-wrapper">
        <!-- Content Header (Page header) -->
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <footer class="main-footer">
        <!-- To the right -->
        <div class="pull-right hidden-xs">

        </div>
        <!-- Default to the left -->
        <strong></strong>
    </footer>
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.2.3 -->
<script src="${springMacroRequestContext.contextPath}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${springMacroRequestContext.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="${springMacroRequestContext.contextPath}/dist/js/app.min.js"></script>
<!-- Noty -->
<script src="${springMacroRequestContext.contextPath}/plugins/noty/noty.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/noty/noty.config.js"></script>

<script>
    window.root = "${springMacroRequestContext.contextPath}";
</script>
<!-- Location Bar -->
<script src="${springMacroRequestContext.contextPath}/plugins/location-bar/location-bar.js"></script>
<script src="${springMacroRequestContext.contextPath}/dist/js/common.js"></script>
<script src="${springMacroRequestContext.contextPath}/dist/js/component/menu.js"></script>

<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
[@passwordModal id="password-modal"]
[/@passwordModal]
</body>
</html>
