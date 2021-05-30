[#include "../../component/content.ftl" /]

[@content title="dada样式维护" subTitle=""]
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/dadaStyle/${dadaStyle.id}" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="id">样式编码</label>
                                <input class="form-control" name="code" id="code"
                                       value="${dadaStyle.code}"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">样式名称</label>
                                <input class="form-control" name="name" id="name"
                                       value="${dadaStyle.name}"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="series">系列</label>
                                <select class="form-control" id="series" name="series">
                                    <option>请选择系列</option>
                                        [#list seriesList as series]
                                            [#if dadaStyle.series??]
                                                [#if dadaStyle.series == series.name]
                                                    <option value="${series.name}" selected>${series.name}</option>
                                                [#else]
                                                    <option value="${series.name}">${series.name}</option>
                                                [/#if]
                                            [#else]
                                                <option value="${series.name}">${series.name}</option>
                                            [/#if]
                                        [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="type">类型</label>
                                <select class="form-control" id="type" name="type">
                                    <option>请选择类型</option>
                                        [#list typeList as type]
                                            [#if dadaStyle.type??]
                                                [#if dadaStyle.type?number == type.id]
                                                    <option value="${type.id}" selected>${type.name}</option>
                                                [#else]
                                                    <option value="${type.id}">${type.name}</option>
                                                [/#if]
                                            [#else]
                                                <option value="${type.id}">${type.name}</option>
                                            [/#if]
                                        [/#list]
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-7">
                            <div class="form-group">
                                <label for="imgUrl">缩略图（推荐尺寸：400*300）</label>
                                <input class="form-control imgUrl" name="imgUrl" id="imgUrl"
                                       value="${dadaStyle.imgUrl}">
                                <label class="bc_upload">上传
                                    <input type="file" style="display: none;" accept="image/png, image/jpeg, image/jpg"
                                           onchange="changePic(this, imgUrl)"/>
                                </label>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-1">
                            <div class="form-group">
                                <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                <i class="fa fa-check bc_upload_tip tip_success"
                                    [#if dadaStyle.imgUrl?? && dadaStyle.imgUrl?length > 0]
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
                                <label for="remark">描述</label>
                                <textarea class="form-control remark_style" id="remark" name="remark"
                                          placeholder="描述">${dadaStyle.remark}</textarea>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="col-xs-5 col-md-4">
                                <div class="from-group">
                                    <label for="imgUrl">配图</label>
                                    <div style="height: 108px;">
                                    [#if dadaStyle.imgUrl?? && dadaStyle.imgUrl?length > 0]
                                        <img src="${dadaStyle.imgUrl}" class="img_shadow imgUrl"
                                             style="height: 100%;">
                                    [#else]
                                        <img src="${dadaStyle.imgUrl}" class="imgUrl">
                                    [/#if]
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
                    $.redirect("${springMacroRequestContext.contextPath}/dadaStyle/list", function () {
                    });
                }, function (err) {

                }, function (data) {

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
            url: "${springMacroRequestContext.contextPath}/api/upload?dir=dadaStyle",
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
                if ($(id).attr('class') === 'form-control imgUrl') {
                    $($('.imgUrl')[1]).addClass('img_shadow');
                    $('.imgUrl')[1].style.height = '100%';
                    $('.imgUrl')[1].src = data;
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
