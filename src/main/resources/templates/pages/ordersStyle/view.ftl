[#include "../../component/content.ftl" /]

[@content title="预约查询" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">

<div class="row">
    <!-- left column -->
    <div class="col-md-12">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">详情</h3>
            </div>
            <!-- /.series-header -->
            <!-- form start -->
            <div class="box-body">
                <label style="margin-left: 30px;">基本信息</label>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label>店铺编号</label>
                            <input class="form-control" value="${ordersStyle.storeCode}" disabled>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-4">
                        <div class="form-group">
                            <label>店铺名称</label>
                            <input class="form-control" value="${ordersStyle.storeName}" disabled>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-3">
                        <div class="form-group">
                            <label>用户名</label>
                            <input class="form-control" value="${ordersStyle.customerName}" disabled>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-3">
                        <div class="form-group">
                            <label>联系号码</label>
                            <input class="form-control" value="${ordersStyle.customerMobile}" disabled>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-2">
                        <div class="form-group">
                            <label>状态</label>
                            <input class="form-control" value="${ordersStyle.statusName}" disabled>
                        </div>
                    </div>
                </div>

                <label style="margin-left: 30px;">款式信息</label>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-3">
                        <div class="form-group">
                            <label>名称</label>
                            <input class="form-control" value="${ordersStyle.style.name!""}" disabled>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-3">
                        <div class="form-group">
                            <label>系列</label>
                            <input class="form-control" value="${ordersStyle.style.series!""}" disabled>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-2">
                        <div class="form-group">
                            <label>样式</label>
                            <input class="form-control"
                                   value="${ordersStyle.style.htYs!""}${ordersStyle.style.jbYs!""}" disabled>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="imgsUrl">图片文件</label>
                            <div class="img_show_div" style="display: flex; height: 108px;">
                                    [#if ordersStyle.style.imgsUrlList ??]
                                        [#list ordersStyle.style.imgsUrlList as imgsUrl]
                                            <img src="${imgsUrl}" class="img_view_show"
                                                 style="margin-right: 40px; height: 100%;">
                                        [/#list]
                                    [/#if]
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="imgUrl">缩略图</label>
                            <div class="img_show_div" style="height: 108px;">
                                    [#if ordersStyle.style.imgUrl?? && ordersStyle.style.imgUrl?length > 0]
                                        <img src="${ordersStyle.style.imgUrl}" class="img_view_show"
                                             style="height: 100%;">
                                    [/#if]
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="moral">寓意</label>
                            <p>${ordersStyle.style.moral!""}</p>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="features">款式特点</label>
                            <p>${ordersStyle.style.features!""}</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="story">故事</label>
                            <p>${ordersStyle.style.story!""}</p>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="meaning">情感含义</label>
                            <p>${ordersStyle.style.meaning!""}</p>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.series-body -->

            <div class="box-footer">
                <div class="row">
                    <div class="col-xs-offset-1 col-md-1">
                            [#if ordersStyle.status == 1]
                                <button type="submit" class="btn btn-primary btn-submit">完成联系</button>
                            [#else]
                                <button type="submit" class="btn btn-primary btn-submit" disabled>完成联系</button>
                            [/#if]
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-default btn-back">返回</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.series -->
    </div>
    <!--/.col (left) -->
</div>
<!-- /.row -->

<script>
    $(function () {
        $('.box-footer .btn-submit').on('click', function () {
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/ordersStyle/haveCall/${ordersStyle.id}",
                type: 'POST',
                contentType: "application/json",
                headers: {"X-CSRF-TOKEN": '${_csrf.token}'}
            }).done(function (data) {
                $.redirect("${springMacroRequestContext.contextPath}/ordersStyle/list", function () {

                });
                $.notification.success(data);
            }).fail(function (error) {
                $.notification.error(error.responseText);
            })
        })
    });
</script>
[/@content]
