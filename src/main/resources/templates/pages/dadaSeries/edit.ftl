[#include "../../component/content.ftl" /]
[#include "../../component/areaTreeModal.ftl" /]

[@content title="dada系列维护" subTitle=""]
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/dadaSeries/${dadaSeries.id}" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">名称</label>
                                <input class="form-control" name="name" id="name" placeholder="请输入系列名称"
                                       value="${dadaSeries.name}"
                                       data-rule="required"
                                       data-msg-required="系列名称不能为空" disabled
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="level">前端展示顺序</label>
                                <input type="number" class="form-control" name="level" id="level" placeholder="请输入顺序整数"
                                       value="${dadaSeries.level}"
                                       data-rule="number"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-7">
                            <div class="form-group">
                                <label for="introductionVideo">图片（推荐尺寸：1800*600）</label>
                                <input class="form-control seriesImg" name="seriesImg" id="seriesImg"
                                       value="${dadaSeries.imgUrl}">
                                <label class="bc_upload">上传
                                    <input type="file" style="display: none;" accept="image/png, image/jpeg, image/jpg"
                                           onchange="changePic(this, seriesImg)"/>
                                </label>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-1">
                            <div class="form-group">
                                <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                <i class="fa fa-check bc_upload_tip tip_success"
                                    [#if dadaSeries.imgUrl?? && dadaSeries.imgUrl?length > 0]
                                        style="display: block"
                                    [/#if]
                                ></i>
                                <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="remark">文件描述</label>
                                <textarea class="form-control remark_style" id="remark" name="remark"
                                          placeholder="文件描述">${dadaSeries.remark}</textarea>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="from-group">
                                <label for="seriesImg">配图</label>
                                <div style="height: 108px;">
                                    [#if dadaSeries.imgUrl?? && dadaSeries.imgUrl?length > 0]
                                        <img src="${dadaSeries.imgUrl}" class="img_shadow seriesImg"
                                             style="height: 100%;">
                                    [#else]
                                        <img src="${dadaSeries.imgUrl}" class="seriesImg">
                                    [/#if]
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isEnabled">是否展示</label>
                                <div class="icheck-group">
                                    <div class="icheck-item">
                                        <input type="radio" id="enabled" class="minimal" name="isEnabled" value=true
                                               data-rule="checked"
                                                    [#if dadaSeries.enabled]
                                                        checked
                                                    [/#if]
                                        >
                                        <label for="enabled">是</label>
                                    </div>
                                    <div class="icheck-item">
                                        <input type="radio" id="disabled" class="minimal" name="isEnabled" value=false
                                               data-rule="checked"
                                                    [#if !dadaSeries.enabled]
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
                function(data) {
                    $.redirect("${springMacroRequestContext.contextPath}/dadaSeries/list", function() {});
                }, function(err) {

                }, function(data) {

                })

    });

    //选择图片
    function changePic(target, id) {
        var parent = $(target).parent().parent().parent().parent()[0];
        parent.getElementsByClassName('tip_success')[0].style.display = 'none';
        parent.getElementsByClassName('tip_fail')[0].style.display = 'none';
        parent.getElementsByClassName('tip_loading')[0].style.display = 'block';
        var formData = new FormData();//初始化一个FormData对象
        formData.append("file", target.files[0]);//将文件塞入FormData
        $.ajax({
            url: "${springMacroRequestContext.contextPath}/api/upload?dir=dadaSeries",
            type: "POST",
            data: formData,
            processData: false,  // 告诉jQuery不要去处理发送的数据
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            headers: {"X-CSRF-TOKEN": '${_csrf.token}'},
            success: function (data) {
                if (data.length == 0) {
                    $.notification.error("上传失败，请重试");
                    return
                }
                $(id).val(data);
                parent.getElementsByClassName('tip_loading')[0].style.display = 'none';
                parent.getElementsByClassName('tip_success')[0].style.display = 'block';
                if ($(id).attr('class') === 'form-control seriesImg'){
                    $($('.seriesImg')[1]).addClass('img_shadow');
                    $('.seriesImg')[1].style.height = '100%';
                    $('.seriesImg')[1].src = data;
                }
            },
            error: function (data) {
                parent.getElementsByClassName('tip_loading')[0].style.display = 'none';
                parent.getElementsByClassName('tip_fail')[0].style.display = 'block';
            }
        });

    };


</script>
[/@content]
