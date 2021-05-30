[#include "../../component/content.ftl" /]

[@content title="权限" subTitle=""]
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/permission" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="code">编码</label>
                                <input class="form-control" id="code" name="code" placeholder="请输入编码"
                                       data-rule="required;length(1~32);remote(get:${springMacroRequestContext.contextPath}/permission/codeCheck)"
                                       data-msg-required="请填写编码"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">名称</label>
                                <input type="text" class="form-control" id="name" name="name" placeholder="请输入名称"
                                       data-rule="required;length(1~32)"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="pname">分类</label>
                                <input type="text" class="form-control" id="pname" name="pname" placeholder="请输入分类"
                                       data-rule="required;length(1~32)"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isSystem">是否内置</label>
                                <div class="icheck-group">
                                    <div class="icheck-item">
                                        <input type="radio" class="minimal" id="system" name="isSystem" value=true data-rule="checked" checked>
                                        <label for="system">是</label>
                                    </div>
                                    <div class="icheck-item">
                                        <input type="radio" class="minimal" id="unsystem" name="isSystem" value=false data-rule="checked">
                                        <label for="unsystem">否</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-10 col-md-8">
                            <div class="form-group">
                                <label for="description">描述</label>
                                <textarea rows="3" class="form-control" id="description" name="description" placeholder="请输入描述"
                                          data-rule="length(0~100)"
                                />
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
<script src="${springMacroRequestContext.contextPath}/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function() {

        $.submitForm("#form1", ".btn-submit", "POST", ["${_csrf.parameterName}", "${_csrf.token}"],
        function(data) {
            $.redirect("${springMacroRequestContext.contextPath}/permission/list", function() {});
        }, function(err) {

        })

        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        });
    });
</script>
[/@content]
