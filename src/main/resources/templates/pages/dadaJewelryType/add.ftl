[#include "../../component/content.ftl" /]
[@content title="dada类别维护" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/iCheck/all.css">
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">

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
            <form id="form1" action="${springMacroRequestContext.contextPath}/dadaJewelryType" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="code">类别编码</label>
                                <input class="form-control" name="code" id="code" placeholder="请输入编码"
                                       data-rule="required"
                                       data-msg-required="编码不能为空"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">类别名称</label>
                                <input class="form-control" name="name" id="name" placeholder="请输入名称"
                                       data-rule="required"
                                       data-msg-required="名称不能为空"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="remark">类别描述</label>
                                <textarea class="form-control remark_style" id="remark" name="remark" placeholder="类别描述"></textarea>
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
    $(function() {

        $.submitForm("#form1", ".btn-submit", "POST", ["${_csrf.parameterName}", "${_csrf.token}"],
        function(data) {
            $.redirect("${springMacroRequestContext.contextPath}/dadaJewelryType/list", function() {});
        }, function(err) {

        }, function(data) {

        })


    });





</script>
[/@content]
