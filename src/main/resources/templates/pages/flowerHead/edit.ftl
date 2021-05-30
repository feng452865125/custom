[#include "../../component/content.ftl" /]

[@content title="花头维护" subTitle=""]
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/flowerHead/${parts.id}" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="code">花头编码</label>
                                <input class="form-control" name="code" id="code" disabled
                                       value="${parts.code}"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="name">花头名称</label>
                                <input class="form-control" name="name" id="name" disabled
                                       value="${parts.name}"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="ys">样式</label>
                                <input class="form-control" name="ys" id="ys" disabled
                                       value="${parts.ys}"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-1">
                            <div class="form-group">
                                <label for="ys">前端展示排序</label>
                                <input class="form-control" name="level" id="level"
                                       value="${parts.level}" placeholder="填写整数"
                                       data-rule="numberInteger"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="kk">开口</label>
                                <input class="form-control" name="kk" id="kk" disabled
                                       value="${parts.kk}"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="fsKd">分数</label>
                                <input class="form-control" name="fsKd" id="fsKd" disabled
                                       value="${parts.fsKd}"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="isShow">同样式主要展示</label>
                                <input class="form-control" name="isShow" id="isShow" disabled
                                       [#if parts.isShow?? && parts.isShow == true]
                                            value="是"
                                       [#else]
                                            value="否"
                                       [/#if]
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-7">
                            <div class="form-group">
                                <label for="imgUrl">正面图（推荐尺寸：400*300）</label>
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
                        <div class="col-xs-offset-1 col-xs-5 col-md-7">
                            <div class="form-group">
                                <label for="sideImgUrl">侧面图（推荐尺寸：400*300）</label>
                                <input class="form-control" name="sideImgUrl" value="${parts.sideImgUrl}">
                                <label class="bc_upload">上传
                                    <input type="file" style="display: none;"
                                           accept="image/png, image/jpeg, image/jpg"
                                           onchange="changePic(this, sideImgUrl)"/>
                                </label>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-1">
                            <div class="form-group">
                                <i class="fa fa-times bc_upload_tip_right_null tip_fail"></i>
                                    [#if parts.sideImgUrl?? && parts.sideImgUrl?length > 0]
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
                    [#--<div class="col-xs-offset-1 col-xs-5 col-md-4">--]
                    [#--<div class="form-group">--]
                    [#--<label for="threeD30">3D编码（30分）</label>--]
                    [#--<input class="form-control" name="threeD30" id="threeD30"--]
                    [#--value="${parts.threeD30}"--]
                    [#-->--]
                    [#--</div>--]
                    [#--</div>--]
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="groupType">所属组</label>
                                <select class="form-control" id="groupType" name="groupType">
                                    <option value="0">请选择组</option>
                                        [#list groupTypeList as groupType]
                                            [#if parts.groupType??]
                                                [#if groupType.id?number == parts.groupType]
                                                    <option value="${groupType.id}" selected>${groupType.name}</option>
                                                [#else]
                                                    <option value="${groupType.id}">${groupType.name}</option>
                                                [/#if]
                                            [#else]
                                                <option value="${groupType.id}">${groupType.name}</option>
                                            [/#if]
                                        [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="jbYsRecommend">推荐戒臂样式(大写字母且#号隔开）</label>
                                <input class="form-control" name="jbYsRecommend" id="jbYsRecommend" placeholder="例：A#B#C"
                                       value="${parts.jbYsRecommend}"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="threeD50">3D编码（50分）</label>
                                <input class="form-control" name="threeD50" id="threeD50"
                                       value="${parts.threeD50}"
                                >
                            </div>
                        </div>
                    </div>
                [#--<div class="row">--]
                [#--<div class="col-xs-offset-1 col-xs-5 col-md-4">--]
                [#--<div class="form-group">--]
                [#--<label for="threeD70">3D编码（70分）</label>--]
                [#--<input class="form-control" name="threeD70" id="threeD70"--]
                [#--value="${parts.threeD70}"--]
                [#-->--]
                [#--</div>--]
                [#--</div>--]
                [#--<div class="col-xs-5 col-md-4">--]
                [#--<div class="form-group">--]
                [#--<label for="threeD100">3D编码（100分）</label>--]
                [#--<input class="form-control" name="threeD100" id="threeD100"--]
                [#--value="${parts.threeD100}"--]
                [#-->--]
                [#--</div>--]
                [#--</div>--]
                [#--</div>--]
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exYy">寓意</label>
                                <input class="form-control" name="exYy" id="exYy" value="${parts.exYy}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exJszl">金属重量</label>
                                <input class="form-control" name="exJszl" id="exJszl" value="${parts.exJszl / 1000}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exJscz">金属材质</label>
                                <input class="form-control" name="exJscz" id="exJscz" value="${parts.exJscz}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exJsys">金属颜色</label>
                                <input class="form-control" name="exJsys" id="exJsys" value="${parts.exJsys}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exQghy">情感含义</label>
                                <textarea class="form-control remark_style" id="exQghy"
                                          name="exQghy" placeholder="情感含义">${parts.exQghy}</textarea>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="remark">描述</label>
                                <textarea class="form-control remark_style" id="remark"
                                          name="remark" placeholder="描述">${parts.remark}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isRecommend">是否推荐</label>
                                <div class="icheck-group">
                                    <div class="icheck-item">
                                        <input type="radio" id="enabled" class="minimal" name="isRecommend" value=true
                                               data-rule="checked"
                                               [#if parts.isRecommend?? && parts.isRecommend == true]
                                                    checked
                                               [/#if]
                                        >
                                        <label for="enabled">是</label>
                                    </div>
                                    <div class="icheck-item">
                                        <input type="radio" id="disabled" class="minimal" name="isRecommend" value=false
                                               data-rule="checked"
                                               [#if  !parts.isRecommend?? || (parts.isRecommend?? && parts.isRecommend != true)]
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
                    $.redirect("${springMacroRequestContext.contextPath}/flowerHead/list", function () {
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
