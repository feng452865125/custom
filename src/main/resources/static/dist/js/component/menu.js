(function($, AdminLTE) {

    $.fn.proxyClick = function() {
        var $this = $(this),
            $menus = $this.find("li"),
            $as = $this.find("a[href!='javascript:;'][href!='#']");

        $as.on("click", function(e) {
            e.preventDefault();
            var $a = $(this),
                $menu=$a.closest("li"),
                target = $a.attr("href");

            // is current?
            if (target === $this.data("current")) {
                return false;
            }

            // load target html page
            $.redirect(target, function(response, status, xhr) {
                $menus.removeClass("active");
                $menu.addClass("active");
                $this.data("current", target);
            });
        });
    }

    //1、jar启动需要，保留
    //2、tomcat启动不需要，下行注释
    $("#menu").proxyClick();

} (jQuery, $.AdminLTE))