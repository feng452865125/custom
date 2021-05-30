[#include "../../component/content.ftl" /]

[@content title="大克拉钻石" subTitle=""]
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/partsBig/${partsBig.id}" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsKh">款号（公司代码-序号，如：A-1）</label>
                                <input class="form-control" name="zsKh" id="zsKh" value="${partsBig.zsKh}"
                                       placeholder="请输入款号"
                                       data-rule="required"
                                       data-msg-required="款号不能为空"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="zsZsh">GIA证书号</label>
                                <input class="form-control" name="zsZsh" id="zsZsh" value="${partsBig.zsZsh}"
                                       placeholder="请输入GIA证书号"
                                       data-rule="required"
                                       data-msg-required="GIA证书号不能为空"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="zsZl">质量</label>
                                <input class="form-control" type="number" name="zsZl" id="zsZl"
                                       value="${((partsBig.zsZl / 1000)!"")?string('0.000')}" placeholder="请输入质量"
                                       data-rule="required"
                                       data-msg-required="质量不能为空"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsYs">颜色</label>
                                <select class="form-control" id="zsYs" name="zsYs" data-rule="noSelect">
                                    <option value="-1">请选择颜色</option>
                                    [#list zsYsList.keys as zsYs]
                                        <option value="${zsYs.label}"
                                            [#if zsYs.label?string == partsBig.zsYs]
                                                selected
                                            [/#if]
                                        >${zsYs.label}</option>
                                    [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsJd">净度</label>
                                <select class="form-control" id="zsJd" name="zsJd" data-rule="noSelect">
                                    <option value="-1">请选择净度</option>
                                    [#list zsJdList.keys as zsJd]
                                        <option value="${zsJd.label}"
                                            [#if partsBig.zsJd?? && zsJd.label?string == partsBig.zsJd]
                                                selected
                                            [/#if]
                                        >${zsJd.label}</option>
                                    [/#list]
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsQg">切工</label>
                                <select class="form-control" id="zsQg" name="zsQg" data-rule="noSelect">
                                    <option value="-1">请选择切工</option>
                                    [#list zsQgList.keys as zsQg]
                                        <option value="${zsQg.label}"
                                            [#if partsBig.zsQg?? && zsQg.label?string == partsBig.zsQg]
                                                selected
                                            [/#if]
                                        >${zsQg.label}</option>
                                    [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsPg">抛光</label>
                                <select class="form-control" id="zsPg" name="zsPg" data-rule="noSelect">
                                    <option value="-1">请选择抛光</option>
                                    [#list zsPgList.keys as zsPg]
                                        <option value="${zsPg.label}"
                                            [#if partsBig.zsPg?? && zsPg.label?string == partsBig.zsPg]
                                                selected
                                            [/#if]
                                        >${zsPg.label}</option>
                                    [/#list]
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsDc">对称</label>
                                <select class="form-control" id="zsDc" name="zsDc" data-rule="noSelect">
                                    <option value="-1">请选择对称</option>
                                    [#list zsDcList.keys as zsDc]
                                        <option value="${zsDc.label}"
                                            [#if partsBig.zsDc?? && zsDc.label?string == partsBig.zsDc]
                                                selected
                                            [/#if]
                                        >${zsDc.label}</option>
                                    [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsYg">荧光</label>
                                <select class="form-control" id="zsYg" name="zsYg" data-rule="noSelect">
                                    <option value="-1">请选择荧光</option>
                                    [#list zsYgList.keys as zsYg]
                                        <option value="${zsYg.label}"
                                            [#if partsBig.zsYg?? && zsYg.label?string == partsBig.zsYg]
                                                selected
                                            [/#if]
                                        >${zsYg.label}</option>
                                    [/#list]
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsKd">扣点</label>
                                <input class="form-control" name="zsKd" id="zsKd" value="${partsBig.zsKd}"
                                       placeholder="请输入扣点">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsGjbj">国际报价</label>
                                <input class="form-control" type="number" name="zsGjbj" id="zsGjbj"
                                       value="${((partsBig.zsGjbj / 100)!"")?string('0.00')}" placeholder="请输入国际报价">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsCtjj">基价报价</label>
                                <input class="form-control" type="number" name="zsCtjj" id="zsCtjj"
                                       value="${((partsBig.zsCtjj / 100)!"")?string('0.00')}" placeholder="请输入基价报价">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsMj">美金</label>
                                <input class="form-control" type="number" name="zsMj" id="zsMj"
                                       value="${((partsBig.zsMj / 100)!"")?string('0.00')}"
                                       placeholder="请输入美金报价">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsRmb">人民币</label>
                                <input class="form-control" type="number" name="zsRmb" id="zsRmb"
                                       value="${((partsBig.zsRmb / 100)!"")?string('0.00')}"
                                       placeholder="请输入人民币报价">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="zsPrice">销售价</label>
                                <input class="form-control" type="number" name="zsPrice" id="zsPrice"
                                       value="${((partsBig.zsPrice / 100)!"")?string('0.00')}" placeholder="请输入销售价"
                                       data-rule="required"
                                       data-msg-required="款号不能为空">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="lockStatus">状态</label>
                                <select class="form-control lockStatus" name="lockStatus" data-rule="noSelect">
                                    <option value="-1">请选择状态</option>
                                    <option value="1"
                                        [#if partsBig.lockStatus == 1]
                                            selected
                                        [/#if]
                                    >在库
                                    </option>
                                    <option value="3"
                                        [#if partsBig.lockStatus == 3]
                                            selected
                                        [/#if]
                                    >锁库
                                    </option>
                                </select>
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
                                            [#if partsBig.enabled]
                                               checked
                                            [/#if]
                                        >
                                        <label for="enabled">是</label>
                                    </div>
                                    <div class="icheck-item">
                                        <input type="radio" id="disabled" class="minimal" name="isEnabled" value=false
                                               data-rule="checked"
                                            [#if !partsBig.enabled]
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
                    $.redirect("${springMacroRequestContext.contextPath}/partsBig/list", function () {
                    });
                }, function (err) {

                }, function (data) {

                })

    });
</script>
[/@content]
