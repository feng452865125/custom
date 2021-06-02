[#include "../../component/content.ftl" /]
[#include "../../component/areaTreeModal.ftl" /]
[@content title="账号维护" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/iCheck/all.css">
<div class="row">
    <!-- left column -->
    <div class="col-md-12">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">添加</h3>
            </div>
            <!-- /.series-header -->
            <!-- form start -->
            <form id="form1" action="${springMacroRequestContext.contextPath}/SysUser" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="sysUserUsername">登录账号</label>
                                <input class="form-control" name="sysUserUsername" id="sysUserUsername" placeholder="请输入登录账号"
                                       data-rule="required;length(1~32);remote(get:${springMacroRequestContext.contextPath}/SysUser/check/username)"
                                       data-msg-required="登录账号不能为空"
                                       data-msg-remote="登录账号已存在"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">名称</label>
                                <input class="form-control" name="name" id="name" placeholder="请输入名称"
                                       data-rule="required;length(1~32);"
                                       data-msg-required="名称不能为空"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="password">密码</label>
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="请输入密码"
                                       data-rule="required;length(1~32)"
                                       data-msg-required="密码不能为空"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="password">确认密码</label>
                                <input type="password" class="form-control" id="rePassword" name="rePassword"
                                       placeholder="请输入确认密码"
                                       data-rule="required;length(1~32);match(password)"
                                       data-msg-required="确认密码不能为空"
                                       data-msg-match="确认密码不一致"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="uuid">设备uuid</label>
                                <input class="form-control" id="uuid" name="uuid" placeholder="移动端自动获取">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="uuid">门店系数</label>
                                <input class="form-control" id="priceMultiple" name="priceMultiple" placeholder="不填，默认为1">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isEnabled">是否启用</label>
                                <div class="icheck-group">
                                    <div class="icheck-item">
                                        <input type="radio" id="enabled" class="minimal" name="isEnabled" value=true
                                               data-rule="checked" checked>
                                        <label for="enabled">是</label>
                                    </div>
                                    <div class="icheck-item">
                                        <input type="radio" id="disabled" class="minimal" name="isEnabled" value=false
                                               data-rule="checked">
                                        <label for="disabled">否</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-10 col-md-8">
                            <div class="form-group">
                                <label for="role">角色</label>
                                <div id="role" class="icheck-group">
                                    [#if roles?? && roles?size > 0]
                                        [#list roles as role]
                                            <div class="icheck-item">
                                                <input type="checkbox" id="${role.id}" class="minimal" name="roles"
                                                       value="${role.id}">
                                                <label for="${role.id}">${role.name}</label>
                                            </div>
                                        [/#list]
                                    [#else]
                                        暂无
                                    [/#if]
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.series-body -->

                <div class="box-footer">
                    <div class="row">
                        <div class="col-xs-offset-1 col-md-1">
                            <button type="submit" class="btn btn-primary btn-submit">提交</button>
                        </div>
                        <div class="col-md-1">
                            <button type="button" class="btn btn-default btn-back">返回</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <!-- /.series -->
    </div>
    <!--/.col (left) -->
</div>
<!-- /.row -->

<script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/jquery.validator.js?local=zh-CN"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/jquery.validator.config.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/local/zh-CN.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function () {

        var $userTypeGroup = $('#user-type-group');
        var $userType = $('.user-type');

        $.submitForm("#form1", ".btn-submit", "POST", ["${_csrf.parameterName}", "${_csrf.token}"],
                function (data) {
                    $.redirect("${springMacroRequestContext.contextPath}/SysUser/list", function () {
                    });
                }, function (err) {

                }, function (data) {
//            data.areaIds = JSON.parse(data.areaIds);
                    data.roles = data.roles instanceof Array ? data.roles : [data.roles];
                    $.each($userType, function (index, item) {
                        $this = $(item);
                        if ($this.is(':checked')) {
                            data[$this.attr('name')] = true;
                        } else {
                            data[$this.attr('name')] = false;
                        }
                    })
                })


        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        });
    });
</script>
[/@content]
