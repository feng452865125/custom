[#include "../../component/content.ftl" /]

[@content title="钻石（克拉展）维护" subTitle=""]
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/diamondKela/${diamondKela.id}"
                  role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="code">钻石编码</label>
                                <input class="form-control" name="code" id="code" placeholder="请输入编码"
                                       value="${diamondKela.code}"
                                       data-rule="required"
                                       data-msg-required="编码不能为空"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">钻石名称</label>
                                <input class="form-control" name="name" id="name" placeholder="请输入名称"
                                       value="${diamondKela.name}"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsZl">质量（单位：克拉）</label>
                                <input class="form-control" name="exZsZl" id="exZsZl"
                                       value="${diamondKela.exZsZl / 1000}" placeholder="请输入质量"
                                       data-rule="required"
                                       data-msg-required="价格不能为空">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="statusName">状态</label>
                                <select class="form-control" id="status" name="status" data-rule="noSelect">
                                    <option value="-1">请选择状态</option>
                                        [#list statusList.keys as status]
                                                <option value="${status.key}"
                                                [#if diamondKela.status?? && status.key?number == diamondKela.status]
                                                    selected
                                                [/#if]
                                                >${status.label}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="price">价格（单位：元）</label>
                                <input class="form-control" name="price" id="price"
                                       value="${diamondKela.price / 100}" placeholder="请输入价格"
                                       data-rule="required"
                                       data-msg-required="价格不能为空"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-7">
                            <div class="form-group">
                                <label for="imgUrl">缩略图（推荐尺寸：72*72）</label>
                                <input class="form-control imgUrl" name="imgUrl" id="imgUrl"
                                       value="${diamondKela.imgUrl}">
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
                                    [#if diamondKela.imgUrl?? && diamondKela.imgUrl?length > 0]
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
                                          placeholder="图片描述">${diamondKela.remark}</textarea>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="from-group">
                                <label for="imgUrl">配图</label>
                                <div style="height: 108px;">
                                    [#if diamondKela.imgUrl?? && diamondKela.imgUrl?length > 0]
                                        <img src="${diamondKela.imgUrl}" class="img_shadow imgUrl"
                                             style="height: 100%;">
                                    [#else]
                                        <img src="${diamondKela.imgUrl}" class="imgUrl">
                                    [/#if]
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsXz">形状</label>
                                <input class="form-control" name="exZsXz" id="exZsXz" value="${diamondKela.exZsXz}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsZs">证书</label>
                                <input class="form-control" name="exZsZs" id="exZsZs" value="${diamondKela.exZsZs}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsYs">颜色（D、E、F、G、H、I、J、K、L）</label>
                                <input class="form-control" name="exZsYs" id="exZsYs" placeholder="请输入颜色"
                                       value="${diamondKela.exZsYs}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsJd">净度（FL、IF、VVS1、VVS2、VS1、VS2、SI1、SI2）</label>
                                <input class="form-control" name="exZsJd" id="exZsJd" placeholder="请输入净度"
                                       value="${diamondKela.exZsJd}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsQg">切工</label>
                                <select class="form-control" name="exZsQg" id="exZsQg" data-rule="noSelect">
                                    <option value="-1">请选择切工</option>
                                        [#list qgList.keys as qg]
                                                <option value="${qg.label}"
                                                 [#if diamondKela.exZsQg?? && qg.label?string == diamondKela.exZsQg]
                                                    selected
                                                 [/#if]
                                                >${qg.label}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsPg">抛光</label>
                                <select class="form-control" name="exZsPg" id="exZsPg" data-rule="noSelect">
                                    <option value="-1">请选择抛光</option>
                                        [#list pgList.keys as pg]
                                                <option value="${pg.label}"
                                                [#if diamondKela.exZsPg?? && pg.label?string == diamondKela.exZsPg]
                                                    selected
                                                [/#if]
                                                >${pg.label}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsDc">对称</label>
                                <select class="form-control" name="exZsDc" id="exZsDc" data-rule="noSelect">
                                    <option value="-1">请选择对称</option>
                                        [#list dcList.keys as dc]
                                                <option value="${dc.label}"
                                                [#if diamondKela.exZsDc?? && dc.label?string == diamondKela.exZsDc]
                                                    selected
                                                [/#if]
                                                >${dc.label}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsYg">荧光</label>
                                <select class="form-control" name="exZsYg" id="exZsYg" data-rule="noSelect">
                                    <option value="-1">请选择荧光</option>
                                        [#list ygList.keys as yg]
                                                <option value="${yg.label}"
                                                [#if diamondKela.exZsYg?? && yg.label?string == diamondKela.exZsYg]
                                                    selected
                                                [/#if]
                                                >${yg.label}</option>
                                        [/#list]
                                </select>
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
                    $.redirect("${springMacroRequestContext.contextPath}/diamondKela/list", function () {
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
            url: "${springMacroRequestContext.contextPath}/api/upload?dir=diamondKela",
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
