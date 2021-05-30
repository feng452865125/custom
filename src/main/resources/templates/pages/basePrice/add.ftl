[#include "../../component/content.ftl" /]
[#include "../../component/areaTreeModal.ftl" /]
[@content title="4C标准基价维护" subTitle=""]
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/basePrice" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="klMin">（≥）克拉数下限</label>
                                <input class="form-control" name="klMin" id="klMin" placeholder="请输入克拉数下限"
                                       data-rule="required"
                                       data-msg-required="克拉数不能为空">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="klMax">（≤）克拉数上限</label>
                                <input class="form-control" name="klMax" id="klMax" placeholder="请输入克拉数上限"
                                       data-rule="required"
                                       data-msg-required="克拉数不能为空">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="ys">颜色</label>
                                <select class="form-control" name="ys" id="ys" data-rule="noSelect">
                                    <option value="-1">请选择颜色</option>
                                        [#list ysList.keys as ys]
                                                <option value="${ys.label}"
                                                 [#if ysList.keys?size == 1]
                                                    selected
                                                 [/#if]
                                                >${ys.label}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="jd">净度</label>
                                <select class="form-control" name="jd" id="jd" data-rule="noSelect">
                                    <option value="-1">请选择净度</option>
                                        [#list jdList.keys as jd]
                                                <option value="${jd.label}"
                                                 [#if jdList.keys?size == 1]
                                                    selected
                                                 [/#if]
                                                >${jd.label}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="qg">切工</label>
                                <select class="form-control" name="qg" id="qg" data-rule="noSelect">
                                    <option value="-1">请选择切工</option>
                                        [#list qgList.keys as qg]
                                                <option value="${qg.label}"
                                                 [#if qgList.keys?size == 1]
                                                    selected
                                                 [/#if]
                                                >${qg.label}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="pg">抛光</label>
                                <select class="form-control" name="pg" id="pg" data-rule="noSelect">
                                    <option value="-1">请选择抛光</option>
                                        [#list pgList.keys as pg]
                                                <option value="${pg.label}"
                                                 [#if pgList.keys?size == 1]
                                                    selected
                                                 [/#if]
                                                >${pg.label}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="dc">对称</label>
                                <select class="form-control" name="dc" id="dc" data-rule="noSelect">
                                    <option value="-1">请选择对称</option>
                                        [#list dcList.keys as dc]
                                                <option value="${dc.label}"
                                                 [#if dcList.keys?size == 1]
                                                    selected
                                                 [/#if]
                                                >${dc.label}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="yg">荧光</label>
                                <select class="form-control" name="yg" id="yg" data-rule="noSelect">
                                    <option value="-1">请选择荧光</option>
                                        [#list ygList.keys as yg]
                                                <option value="${yg.label}"
                                                [#if ygList.keys?size == 1]
                                                    selected
                                                [/#if]
                                                >${yg.label}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="zs">证书</label>
                                <select class="form-control" name="zs" id="zs" data-rule="noSelect">
                                    <option value="-1">请选择证书</option>
                                        [#list zsList.keys as zs]
                                                <option value="${zs.label}"
                                                [#if zsList.keys?size == 1]
                                                    selected
                                                [/#if]
                                                >${zs.label}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="name">名称</label>
                                <input class="form-control" name="name" id="name" placeholder="请输入名称">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="price">价格</label>
                                <input class="form-control" name="price" id="price" placeholder="请输入价格"
                                       data-rule="required"
                                       data-msg-required="价格不能为空">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="remark">描述</label>
                                <textarea class="form-control remark_style" id="remark" name="remark"
                                          placeholder="其他描述"></textarea>
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
                                               data-rule="checked" checked>
                                        <label for="enabled">是</label>
                                    </div>
                                    <div class="icheck-item">
                                        <input type="radio" id="disabled" class="minimal" name="isEnabled" value=false
                                               data-rule="checked">
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

<script>
    $(function () {
        $.submitForm("#form1", ".btn-submit", "POST", ["${_csrf.parameterName}", "${_csrf.token}"],
                function (data) {
                    $.redirect("${springMacroRequestContext.contextPath}/basePrice/list", function () {
                    });
                }, function (err) {

                }, function (data) {

                });

    });
</script>
[/@content]
