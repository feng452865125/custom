from 白小菜
start.sh
nohup java -DXms1G -DXmx2500M -DXX:PermSize=800M -DXX:MaxPermSize=800M -jar custom.jar > catalina.out 2>&1 & echo $! > custom.pid
shutdown.sh
kill -9 `cat custom.pid`


spring boot 默认启动打包jar
命令：mvn clean package
配置文件：1、/src/main/java/com/dingjust/custom/Application.java，方法一
        2、/pom.xml，将pom-jar.xml内容复制进去
        3、/src/main/resources/static/dist/js/common.js，
            第59行（方法一）
                    //方法一、jar启动（二选一）
                    $.redirect(window.root + "/" + path);
                    //方法二、tomcat启动（二选一）
                    //$.redirect(path);
            第70行（方法一）
                    //方法一、jar启动需要，保留
                    $document.on('click', 'a[redirect]', function (e) {
                            e.preventDefault();
                            var $this = $(this),
                            target = $this.attr('href');
                            $.redirect(target);
                    })
                    //方法二、tomcat启动不需要，下行$document.on（）整段注释
                    //$document.on('click', 'a[redirect]', function (e) {
                    //      e.preventDefault();
                    //      var $this = $(this),
                    //      target = $this.attr('href');
                    //      $.redirect(target);
                    //})
        4、/src/main/resources/static/dist/js/component/menu.js，
            第59行（方法一）
                    //方法一、jar启动需要，保留
                    $("#menu").proxyClick();
                    //方法二、tomcat启动不需要，下行注释
                    //$("#menu").proxyClick();
        5、/src/main/resources/templates/component/menu.ftl 
            跳转路径格式：<a href="${springMacroRequestContext.contextPath}/user/list">
        6、/src/main/resources/templates/pages/*/list.ftl 
            跳转路径格式：<a redirect href="${springMacroRequestContext.contextPath}/user/view/' + data + '">详情</a>
        
tomcat 启动打包war
命令：mvn clean package
配置文件：1、/src/main/java/com/dingjust/custom/Application.java，方法二
        2、/pom.xml，将pom-war.xml内容复制进去
        3、/src/main/resources/static/dist/js/common.js，
            第59行（方法二）
                    //方法一、jar启动（二选一）
                    $.redirect(window.root + "/" + path);
                    //方法二、tomcat启动（二选一）
                    //$.redirect(path);
            第70行（方法二）
                    //方法一、jar启动需要，保留
                    $document.on('click', 'a[redirect]', function (e) {
                            e.preventDefault();
                            var $this = $(this),
                            target = $this.attr('href');
                            $.redirect(target);
                    })
                    //方法二、tomcat启动不需要，下行$document.on（）整段注释
                    //$document.on('click', 'a[redirect]', function (e) {
                    //      e.preventDefault();
                    //      var $this = $(this),
                    //      target = $this.attr('href');
                    //      $.redirect(target);
                    //})
        4、/src/main/resources/static/dist/js/component/menu.js，
            第59行（方法二）
                    //方法一、jar启动需要，保留
                    $("#menu").proxyClick();
                    //方法二、tomcat启动不需要，下行注释
                    //$("#menu").proxyClick();
        5、/src/main/resources/templates/component/menu.ftl 
             跳转路径格式：<a href="#user/list">
        6、/src/main/resources/templates/pages/*/list.ftl 
             跳转路径格式：<a redirect href="#user/view/' + data + '">详情</a>
                   
主要区别
第一点、jar包与tomcat的war包启动方式不一样
第二点、构建web项目模块 包括了Tomcat和spring-webmvc
       spring-boot-starter-web 默认依赖了tomcat的starter 所以使得项目可以直接运行而不需要部署到tomcat中
第三四点、部署成功后，页面跳转返回路径异常（比如进入edit，再返回，路径中会出现多余的项目名，导致异常）
        修改跳转机制，采用方法一或者方法二。
第五六点、menu.ftl，<a href="
        list.ftl，<a redirect href="
        ${springMacroRequestContext.contextPath}/   改为   #