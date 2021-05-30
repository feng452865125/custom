[#include "../../component/content.ftl" /]
[#include "../../component/areaTreeModal.ftl" /]

[@content title="unique类别维护" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/iCheck/all.css">
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">
[#--上传图片--]
<script src="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/js/vendor/jquery.ui.widget.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/js/jquery.iframe-transport.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/js/jquery.fileupload.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/js/upload.js"></script>
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/css/jquery.fileupload.css">
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/css/upload.css">

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
            <form id="form1" action="${springMacroRequestContext.contextPath}/uniqueGroup/${uniqueGroup.id}"
                  role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">名称</label>
                                <input class="form-control" name="name" id="name" placeholder="请输入类别名称"
                                       value="${uniqueGroup.name}"
                                       data-rule="required"
                                       data-msg-required="类别名称不能为空"
                                >
                            </div>
                            <div class="form-group">
                                <label for="ht">花头样式(大写字母且#号隔开）</label>
                                <input class="form-control" name="ht" id="ht" placeholder="例：A#B#C"
                                       value="${uniqueGroup.ht}"
                                       [#--data-rule="required"--]
                                       [#--data-msg-required="花头样式不能为空"--]
                                >
                            </div>
                            <div class="form-group">
                                <label for="jb">戒臂样式(大写字母且#号隔开）</label>
                                <input class="form-control" name="jb" id="jb" placeholder="例：A#B#C"
                                       value="${uniqueGroup.jb}"
                                       [#--data-rule="required"--]
                                       [#--data-msg-required="戒臂样式不能为空"--]
                                >
                            </div>
                            <div class="form-group">
                                <label for="remark">描述</label>
                                <textarea class="form-control remark_style" id="remark" name="remark"
                                          placeholder="描述">${uniqueGroup.remark}</textarea>
                            </div>
                            <div class="form-group">
                                <label for="isEnabled">是否展示</label>
                                <div class="icheck-group">
                                    <div class="icheck-item">
                                        <input type="radio" id="enabled" class="minimal" name="isEnabled" value=true
                                               data-rule="checked"
                                                    [#if uniqueGroup.enabled]
                                                        checked
                                                    [/#if]
                                        >
                                        <label for="enabled">是</label>
                                    </div>
                                    <div class="icheck-item">
                                        <input type="radio" id="disabled" class="minimal" name="isEnabled" value=false
                                               data-rule="checked"
                                                    [#if !uniqueGroup.enabled]
                                                        checked
                                                    [/#if]
                                        >
                                        <label for="disabled">否</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="url">配图（推荐尺寸：400*300）</label>
                                <input type="hidden" name="url" class="relFile" value="${uniqueGroup.url}">
                                <span class="btn btn-success fileinput-button">
                                    <i class="fa fa-plus"></i>
                                    <span>替换</span>
                                    <input class="fileupload" type="file" name="file"
                                           data-url="/api/upload?dir=uniqueGroup">
                                </span>
                                <div class="preview img_div" style="height: 164px;">
                                    <img class="uploadimg" style="height: 100%;" src="${uniqueGroup.url}"/>
                                    <span class="success"></span>
                                    <span class="loading"></span>
                                </div>
                                <div class="btn_div">
                                    <button class="btn btn-primary startUpload">
                                        <i class="fa fa-upload"></i>
                                        <span>上传</span>
                                    </button>
                                    <button class="btn btn-warning cancelUpload">
                                        <i class="fa fa-reply"></i>
                                        <span>取消</span>
                                    </button>
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
        $('.fileupload').fileupload({
            headers: {
                "X-CSRF-TOKEN": "${_csrf.token}"
            }
        });

        $.submitForm("#form1", ".btn-submit", "PUT", ["${_csrf.parameterName}", "${_csrf.token}"],
                function (data) {
                    $.redirect("${springMacroRequestContext.contextPath}/uniqueGroup/list", function () {
                    });
                }, function (err) {

                }, function (data) {

                })

    });
</script>
[/@content]
