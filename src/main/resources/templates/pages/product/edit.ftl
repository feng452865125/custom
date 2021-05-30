[#include "../../component/content.ftl" /]

[@content title="产品维护" subTitle=""]
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/product/${product.id}" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="code">产品编码</label>
                                <input class="form-control" name="code" id="code" disabled
                                       value="${product.code}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="name">产品名称</label>
                                <input class="form-control" name="name" id="name" disabled
                                       value="${product.name}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="ktCode">客定码</label>
                                <input class="form-control" name="ktCode" id="ktCode" disabled
                                       value="${product.ktCode}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exZsYs">钻石颜色</label>
                                <input class="form-control" name="exZsYs" id="exZsYs" disabled
                                       value="${product.exZsYs}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exZsJd">钻石净度</label>
                                <input class="form-control" name="exZsJd" id="exZsJd" disabled
                                       value="${product.exZsJd}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="price">价格（单位：元）</label>
                                <input class="form-control" name="price" id="price" placeholder="请输入价格"
                                       data-rule="required"
                                       data-msg-required="价格不能为空"
                                       value="${(product.price / 100)!""}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exFs">钻石分数</label>
                                <input class="form-control" name="exFs" id="exFs" disabled
                                       value="${product.exFs}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exDiamondFsMin">生产要求备注，最小值</label>
                                <input class="form-control" name="exDiamondFsMin" id="exDiamondFsMin" disabled
                                       value="${(product.exDiamondFsMin / 1000)!""}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exDiamondFsMax">生产要求备注，最大值</label>
                                <input class="form-control" name="exDiamondFsMax" id="exDiamondFsMax" disabled
                                       value="${(product.exDiamondFsMax / 1000)!""}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exYy">寓意</label>
                                <input class="form-control" name="exYy" id="exYy" disabled
                                       value="${product.exYy}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="nxbs">内镶宝石</label>
                                <input class="form-control" name="nxbs" id="nxbs" disabled
                                       value="${product.nxbs}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="jsys">金属颜色</label>
                                <input class="form-control" name="jsys" id="jsys" disabled
                                       value="${product.jsys}">
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
                    $.redirect("${springMacroRequestContext.contextPath}/product/list", function () {
                    });
                }, function (err) {

                }, function (data) {

                })
    });
</script>
[/@content]
