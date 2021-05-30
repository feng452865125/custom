[#include "../../component/content.ftl" /]
[@content title="订单手动添加" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/iCheck/all.css">
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/orders" role="form">
                <div class="box-body">
                    <label style="margin-left: 30px;">订单信息</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="code">订单编号</label>
                                <input class="form-control" name="code" id="code" placeholder="请输入订单编号"
                                       data-rule="required"
                                       data-msg-required="订单编号不能为空"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>GIA证书号</label>
                                <input class="form-control" name="stoneZsbh" id="stoneZsbh" placeholder="请输入GIA证书编号"
                                       data-rule="required"
                                       data-msg-required="证书编号不能为空"
                                >
                            </div>
                        </div>
                    </div>

                    <label style="margin-left: 30px;">戒托信息</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="ktCode">客定码</label>
                                <input class="form-control" name="ktCode" id="ktCode" placeholder="请输入客定码"
                                       data-rule="required"
                                       data-msg-required="客定码不能为空"
                                >
                            </div>
                        </div>
                    </div>


                    [#if order != null]
                        <label style="margin-left: 30px;">石头信息</label>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label>颜色</label>
                                    <input class="form-control" value="${(orders.parts.exZsYs)!"无"}" disabled>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label>净度</label>
                                    <input class="form-control" value="${(orders.parts.exZsJd)!"无"}" disabled>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label>切工</label>
                                    <input class="form-control" value="${(orders.parts.exZsQg)!"无"}" disabled>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label>重量</label>
                                    <input class="form-control" value="${(orders.parts.exZsZl / 1000)!"无"}" disabled>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">

                                    <label>证书</label>
                                    [#if !orders.product.ktCode??]
                                        <input class="form-control" value="${(orders.parts.exZsZs+orders.parts.exZsBh)!"无"}" disabled>
                                    [#else]
                                        <input class="form-control" value="${(orders.parts.exZsZs)!"无"}" disabled>
                                    [/#if]
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label>荧光</label>
                                    <input class="form-control" value="${(orders.parts.exZsYg)!"无"}" disabled>
                                </div>
                            </div>
                        </div>

                        <label style="margin-left: 30px;">顾客信息</label>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="story">顾客姓名</label>
                                    <input class="form-control" value="${orders.customerName}" disabled>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="story">号码</label>
                                    <input class="form-control" value="${orders.customerMobile}" disabled>
                                </div>
                            </div>
                        </div>

                        <label style="margin-left: 30px;">导购信息</label>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label>店铺</label>
                                    <input class="form-control" value="${orders.userName!""}" disabled>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label>制单人</label>
                                    <input class="form-control" value="${orders.userOperatorName!""}" disabled>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label>导购</label>
                                    <input class="form-control" value="${orders.userSalesName!""}" disabled>
                                </div>
                            </div>
                        </div>
                    [/#if]


















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
    $(function() {

        $.submitForm("#form1", ".btn-submit", "POST", ["${_csrf.parameterName}", "${_csrf.token}"],
        function(data) {
            $.redirect("${springMacroRequestContext.contextPath}/flowerHead/list", function() {});
        }, function(err) {

        }, function(data) {

        })


    });





</script>
[/@content]
