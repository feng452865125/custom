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
                <h3 class="box-title">编辑</h3>
            </div>
            <!-- /.series-header -->
            <!-- form start -->
            <form id="form1" action="${springMacroRequestContext.contextPath}/SysUser/${user.id}" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="username">店铺编码</label>
                                <input class="form-control" id="username" name="username" value="${user.username}"
                                       disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">店铺名称</label>
                                <input class="form-control" id="name" name="name" value="${user.name}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="password">登录密码</label>
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="请输入密码"
                                       data-rule="length(0~32)"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="password">确认密码</label>
                                <input type="password" class="form-control" id="rePassword" name="rePassword"
                                       placeholder="请输入确认密码"
                                       data-rule="length(0~32);match(password)"
                                       data-msg-match="确认密码不一致"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="address">店铺地址</label>
                                <input class="form-control" id="address" name="address" value="${user.address}"
                                       disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="pos">pos终端号</label>
                                <input class="form-control" id="pos" name="pos" value="${user.pos}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="statusName">门店状态</label>
                                <input class="form-control" id="statusName" name="statusName" value="${user.statusName!""}"
                                       disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="loLa">经纬度(半角逗号","隔开)</label>
                                <input class="form-control" id="loLa" name="loLa" placeholder="经度,纬度"
                                       [#if user.longitude??]
                                            value="${user.longitude?string["0.######"]},${user.latitude?string["0.######"]}"
                                       [#else]
                                            value=""
                                       [/#if]
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-1">
                            <label style="height: 40px;"></label>
                            <button type="button" class="btn btn-info btn-add" style="margin-left: -3px;">
                                <a href="http://api.map.baidu.com/lbsapi/getpoint/index.html" target=_blank
                                   style="color: white;">百度地图</a>
                            </button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="uuid">设备uuid</label>
                                <input class="form-control" id="uuid" name="uuid" value="${user.uuid}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="uuid">门店系数</label>
                                <input class="form-control" id="priceMultiple" name="priceMultiple" value="${user.priceMultiple}">
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
                                               data-rule="checked"
                                            [#if user.enabled]
                                               checked
                                            [/#if]
                                        >
                                        <label for="enabled">是</label>
                                    </div>
                                    <div class="icheck-item">
                                        <input type="radio" id="disabled" class="minimal" name="isEnabled" value=false
                                               data-rule="checked"
                                            [#if !user.enabled]
                                                checked
                                            [/#if]
                                        >
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
                                <div class="icheck-group">
                                    [#if roles?? && roles?size > 0]
                                        [#list roles as role]
                                            <div class="icheck-item">
                                                <input type="checkbox" id="${role.id}" class="minimal" name="roles"
                                                       value="${role.id}"
                                                       [#if sysUserRole??]
                                                           [#if sysUserRole.roles?contains(role.id?string)]
                                                         checked
                                                           [/#if]
                                                       [/#if]
                                                >
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
        var state = {
            cardId: '',
            phone: '',
        };
        [#if cmReceiver??]
        state = {
            cardId: "${cmReceiver.cardId!''}",
            phone: "${cmReceiver.phone!''}",
        }
        [/#if]

        var cardIdEditable = true;

        $.submitForm("#form1", ".btn-submit", "PUT", ["${_csrf.parameterName}", "${_csrf.token}"],
                function (data) {
                    $.redirect("${springMacroRequestContext.contextPath}/SysUser/list", function () {
                    });
                }, function (err) {

                }, function (data) {
                    data.roles = data.roles && !data.roles instanceof Array ? [data.roles] : data.roles;
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
