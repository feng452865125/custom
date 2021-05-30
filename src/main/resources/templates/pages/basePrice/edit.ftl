[#include "../../component/content.ftl" /]
[#include "../../component/areaTreeModal.ftl" /]

[@content title="4C标准基价维护" subTitle=""]
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/basePrice/${basePrice.id}"
                  role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="klMin">（≥）克拉数下限</label>
                                <input class="form-control" name="klMin" id="klMin" placeholder="请输入克拉数下限"
                                       data-rule="required" value="${((basePrice.klMin / 1000)!"")?string('0.000')}"
                                       data-msg-required="克拉数不能为空" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="klMax">（≤）克拉数上限</label>
                                <input class="form-control" name="klMax" id="klMax" placeholder="请输入克拉数上限"
                                       data-rule="required" value="${((basePrice.klMax / 1000)!"")?string('0.000')}"
                                       data-msg-required="克拉数不能为空" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="ys">颜色</label>
                                <input class="form-control" name="ys" id="ys" value="${basePrice.ys}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="jd">净度</label>
                                <input class="form-control" name="jd" id="jd" value="${basePrice.jd}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="qg">切工</label>
                                <input class="form-control" name="qg" id="qg" value="${basePrice.qg}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="pg">抛光</label>
                                <input class="form-control" name="pg" id="pg" value="${basePrice.pg}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="dc">对称</label>
                                <input class="form-control" name="dc" id="dc" value="${basePrice.dc}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="yg">荧光</label>
                                <input class="form-control" name="yg" id="yg" value="${basePrice.yg}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="zs">证书</label>
                                <input class="form-control" name="zs" id="zs" value="${basePrice.zs}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="name">名称</label>
                                <input class="form-control" name="name" id="name" placeholder="请输入名称"
                                       value="${basePrice.name}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="price">价格</label>
                                <input class="form-control" name="price" id="price" placeholder="请输入价格"
                                       data-rule="required" value="${(basePrice.price / 100)!""}"
                                       data-msg-required="价格不能为空">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="remark">描述</label>
                                <textarea class="form-control remark_style" id="remark" name="remark"
                                          placeholder="其他描述">${basePrice.remark}</textarea>
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
                                        [#if basePrice.status]
                                               checked
                                        [/#if]
                                        >
                                        <label for="enabled">是</label>
                                    </div>
                                    <div class="icheck-item">
                                        <input type="radio" id="disabled" class="minimal" name="isEnabled" value=false
                                               data-rule="checked"
                                         [#if !basePrice.status]
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
                    $.redirect("${springMacroRequestContext.contextPath}/basePrice/list", function () {
                    });
                }, function (err) {

                }, function (data) {

                });


    })
</script>
[/@content]
