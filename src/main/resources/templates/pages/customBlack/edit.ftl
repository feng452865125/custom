[#include "../../component/content.ftl" /]

[@content title="黑名单维护" subTitle=""]
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/iCheck/all.css">
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">

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
                <form id="form1" action="${springMacroRequestContext.contextPath}/customBlack/${customBlack.id}"
                      role="form">
                    <div class="box-body">
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="blackZsh">GIA证书号</label>
                                    <input class="form-control" name="blackZsh" id="blackZsh" placeholder="请输入GIA证书号"
                                           data-rule="required"
                                           data-msg-required="证书号不能为空" value="${customBlack.blackZsh}"
                                    >
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="blackReason">原因/备注</label>
                                    <textarea class="form-control remark_style" id="blackReason" name="blackReason"
                                              placeholder="原因/备注">${customBlack.blackReason}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="blackEnable">是否启用黑名单</label>
                                    <div class="icheck-group">
                                        <div class="icheck-item">
                                            <input type="radio" id="enabled" class="minimal" name="blackEnable"
                                                   value=true
                                                   data-rule="checked"
                                                    [#if customBlack.blackEnable]
                                                        checked
                                                    [/#if]
                                            >
                                            <label for="enabled">是</label>
                                        </div>
                                        <div class="icheck-item">
                                            <input type="radio" id="disabled" class="minimal" name="blackEnable"
                                                   value=false
                                                   data-rule="checked"
                                                    [#if !customBlack.blackEnable]
                                                        checked
                                                    [/#if]
                                            >
                                            <label for="disabled">否</label>
                                        </div>
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

            $.submitForm("#form1", ".btn-submit", "PUT", ["${_csrf.parameterName}", "${_csrf.token}"],
                function (data) {
                    $.redirect("${springMacroRequestContext.contextPath}/customBlack/list", function () {
                    });
                }, function (err) {

                }, function (data) {

                })

        });
    </script>
[/@content]
