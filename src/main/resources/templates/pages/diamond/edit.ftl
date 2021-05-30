[#include "../../component/content.ftl" /]

[@content title="钻石维护" subTitle=""]
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/diamond/${parts.id}" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="code">钻石编码</label>
                                <input class="form-control" name="code" id="code" disabled
                                       value="${parts.code}"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">钻石名称</label>
                                <input class="form-control" name="name" id="name" disabled
                                       value="${parts.name}"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="statusName">状态</label>
                                <select class="form-control" id="status" name="status">
                                    <option>请选择状态</option>
                                        [#list statusList.keys as status]
                                            [#if parts.status??]
                                                [#if status.key?number == parts.status]
                                                    <option value="${status.key}" selected>${status.label}</option>
                                                [#else]
                                                    <option value="${status.key}">${status.label}</option>
                                                [/#if]
                                            [#else]
                                                <option value="${status.key}">${status.label}</option>
                                            [/#if]
                                        [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="price">价格（单位：元）</label>
                                <input class="form-control" name="price" id="price" disabled
                                       value="${parts.price / 100}"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-7">
                            <div class="form-group">
                                <label for="imgUrl">缩略图（推荐尺寸：72*72）</label>
                                <input class="form-control" name="imgUrl" value="${parts.imgUrl}">
                                <label class="bc_upload">上传
                                    <input type="file" style="display: none;"
                                           accept="image/png, image/jpeg, image/jpg"
                                           onchange="changePic(this, imgUrl)"/>
                                </label>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-1">
                            <div class="form-group">
                                <i class="fa fa-times bc_upload_tip_right_null tip_fail"></i>
                                    [#if parts.imgUrl?? && parts.imgUrl?length > 0]
                                        <i class="fa fa-check bc_upload_tip_right_null tip_success"
                                           style="display: block"></i>
                                    [#else]
                                        <i class="fa fa-check bc_upload_tip_right_null tip_success"></i>
                                    [/#if]
                                <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsXz">形状</label>
                                <input class="form-control" name="exZsXz" id="exZsXz" value="${parts.exZsXz}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsZs">证书</label>
                                <input class="form-control" name="exZsZs" id="exZsZs" value="${parts.exZsZs}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsYs">颜色</label>
                                <input class="form-control" name="exZsYs" id="exZsYs" value="${parts.exZsYs}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsZl">质量</label>
                                <input class="form-control" name="exZsZl" id="exZsZl" value="${parts.exZsZl / 1000}"
                                       disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsJd">净度</label>
                                <input class="form-control" name="exZsJd" id="exZsJd" value="${parts.exZsJd}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsQg">切工</label>
                                <input class="form-control" name="exZsQg" id="exZsQg" value="${parts.exZsQg}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsPg">抛光</label>
                                <input class="form-control" name="exZsPg" id="exZsPg" value="${parts.exZsPg}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsDc">对称</label>
                                <input class="form-control" name="exZsDc" id="exZsDc" value="${parts.exZsDc}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsYg">荧光</label>
                                <input class="form-control" name="exZsYg" id="exZsYg" value="${parts.exZsYg}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="remark">描述</label>
                                <textarea class="form-control remark_style" id="remark"
                                          name="remark" placeholder="描述">${parts.remark}</textarea>
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
                    $.redirect("${springMacroRequestContext.contextPath}/diamond/list", function () {
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
            url: "${springMacroRequestContext.contextPath}/api/upload?dir=parts",
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
            },
            error: function (data) {
                parent.getElementsByClassName('tip_loading')[0].style.display = 'none';
                parent.getElementsByClassName('tip_fail')[0].style.display = 'block';
            }
        });

    };
</script>
[/@content]
