$(function () {
    var $document = $(document);

    window.Log = {
        IS_DEBUG: true,
        d: function (data) {
            if (this.IS_DEBUG)
                if (typeof data === "object")
                    console.log(JSON.stringify(data));
                else
                    console.log(data);
        }
    };

    // 序列化
    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    // 定义通用跳转
    $.redirect = function (url, callback) {
        var $contentWrapper = $("#content-wrapper");

        if (!callback || 'function' !== typeof callback) {
            callback = function () {
            };
        }

        // push state to history
        locationBar.update(url);
        // load page
        $contentWrapper.load(url, function (response, status, xhr) {
            if (status == 'error') {
                $contentWrapper.html(response);
            }
            callback(response, status, xhr);
        });
    }

    // 初始化LocationBar
    var locationBar = new LocationBar();
    locationBar.onChange(function (path) {
        // 监听url变化
        if (path == '' || path == 'index') {
            path = 'home';
        }
        //方法一、jar启动（二选一）
        $.redirect(window.root + "/" + path);
        //方法二、tomcat启动（二选一）
        //$.redirect(path);
    });

    locationBar.start({
        // pushState: true,
        hashChange: true,
    });

    //方法一、jar启动需要，保留
    //方法二、tomcat启动不需要，下行$document.on（）整段注释
    // bind redirect href to $.redirect
    $document.on('click', 'a[redirect]', function (e) {
        e.preventDefault();
        var $this = $(this),
            target = $this.attr('href');

        $.redirect(target);
    })

    //设置返回按钮
    $document.on('click', '.btn-back', function () {
        history.back()
    })

    $.showSureDialog = function (url, csrf, success, fail) {
        $.dialog('确认此次操作？', function (n) {
            $.ajax({
                url: url,
                type: 'POST',
                'beforeSend': function (request) {
                    request.setRequestHeader('X-CSRF-TOKEN', csrf);
                    return request;
                },
            }).done(function (data) {
                $.notification.success('操作成功！');
                if (success && 'function' === typeof success) success(n, data);
            }).fail(function (err) {
                $.notification.error(err.responseText);
                if (fail && 'function' === typeof fail) fail(n, err);
            })
        })
    }

    $.showRemoveDialog = function (url, csrf, success, fail) {
        $.dialog('是否确认删除？', function (n) {
            $.ajax({
                url: url,
                type: 'DELETE',
                'beforeSend': function (request) {
                    request.setRequestHeader('X-CSRF-TOKEN', csrf);
                    return request;
                },
            }).done(function (data) {
                $.notification.success('删除成功！');
                if (success && 'function' === typeof success) success(n, data);
            }).fail(function (err) {
                $.notification.error(err.responseText);
                if (fail && 'function' === typeof fail) fail(n, err);
            })
        })
    }

    $.removeModal = function () {
        $(".modal-backdrop, .fade, .in").remove();
    }

    //表单提交
    $.submitForm = function (formId, submitBtnSelector, type, csrf, success, fail, handleData) {
        $form = $(formId);
        $form.on('click', submitBtnSelector, function (e) {
            e.preventDefault();

            $form.trigger("validate");
        })
        $form.on('valid.form', function (e, form) {
            var action = $form.attr("action");
            var data = $form.serializeObject();

            for (var item in data) {
                if (data[item] === "" || "undefined" === typeof data[item]) delete data[item];
            }

            if (handleData && 'function' === typeof handleData) handleData(data);
            $.ajax({
                url: action,
                data: JSON.stringify(data),
                type: type,
                contentType: "application/json",
                headers: {
                    "X-CSRF-TOKEN": csrf[1],
                }
            }).done(function (data) {
                if (type === 'PUT' || type === 'put') {
                    $.notification.success('更新成功');
                } else if (type === 'POST' || type === 'post') {
                    $.notification.success('添加成功');
                }

                if (success && 'function' === typeof success) success(data);
            }).fail(function (error) {
                var result;
                var message;
                try {
                    result = JSON.parse(error.responseText);
                    message = result.message;
                } catch (e) {
                    message = error.responseText
                }
                $.notification.error(message);
                if (fail && 'function' === typeof fail) fail(error);
            })
        });
    }

    $.fn.setFormValues = function (jsonValue) {
        if (!jsonValue) {
            return;
        }
        var obj = this;
        foreach(jsonValue, "");

        function foreach(json, prefix) {
            $.each(json, function (name, ival) {
                name = prefix + name;
                if (typeof ival == "object") {
                    foreach(ival, name + ".");
                    return;
                }
                var $oinput = obj.find("input[name='" + name + "']");
                if ($oinput.attr("type") == "checkbox") {
                    if (ival !== null) {
                        var checkboxObj = obj.find("[name='" + name + "']");
                        var checkArray = ival.split(";");
                        for (var i = 0; i < checkboxObj.length; i++) {
                            for (var j = 0; j < checkArray.length; j++) {
                                if (checkboxObj[i].value == checkArray[j]) {
                                    checkboxObj[i].click();
                                }
                            }
                        }
                    }
                } else if ($oinput.attr("type") == "radio") {
                    $oinput.each(function () {
                        var radioObj = obj.find("[name='" + name + "']");
                        for (var i = 0; i < radioObj.length; i++) {
                            if (radioObj[i].value == ival) {
                                // radioObj[i].click();
                                $(radioObj[i]).iCheck('check')
                            }
                        }
                    });
                } else if ($oinput.attr("type") == "textarea") {
                    obj.find("[name='" + name + "']").html(ival);
                } else {
                    var text = obj.find("[name='" + name + "']");
                    if (text.length > 0) {
                        text.val(ival);
                    }
                    var $select = obj.find("select[name='" + name + "']");
                    if ($select.length > 0) {

                    }
                    var $textarea = obj.find("textarea[name='" + name + "']");
                    if ($textarea.length > 0) {
                        $textarea.val(ival);
                    }
                    var $img = obj.find("img[name='" + name + "']");
                    if ($img.length > 0) {
                        $img.attr("src", ival);
                    }
                }
            })
        }
    }

    $.fn.resetForm = function () {
        this[0].reset();
        this.validator("cleanUp");
    }

    $.format = function (date) {
        /*
         * 使用例子:format="yyyy-MM-dd hh:mm:ss";
         */
        format = "yyyy-MM-dd hh:mm:ss";

        var o = {
            "M+": date.getMonth() + 1, // month
            "d+": date.getDate(), // day
            "h+": date.getHours(), // hour
            "H+": date.getHours(), // hour
            "m+": date.getMinutes(), // minute
            "s+": date.getSeconds(), // second
            "q+": Math.floor((date.getMonth() + 3) / 3), // quarter
            "S": date.getMilliseconds()
            // millisecond
        }

        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4
                - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1
                    ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }

});