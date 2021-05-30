[#include "../../component/content.ftl" /]

[@content title="订单查询" subTitle=""]
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
            <form id="form1" role="form">
                <div class="box-body">
                    <label style="margin-left: 30px;">基本信息</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>订单编号</label>
                                <input class="form-control" value="${orders.code}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>总价（单位：元）</label>
                                <input class="form-control" value="${orders.price}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>客定码</label>
                                <input class="form-control" value="${orders.product.ktCode!""}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>钻石编码</label>
                                <input class="form-control" value="${(orders.parts.code)!"无"}" disabled>
                            </div>
                        </div>
                    </div>

                    <label style="margin-left: 30px;">戒托信息</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>戒臂宽度</label>
                                <input class="form-control" value="${(orders.product.exJbKd)!(orders.exJbKd)!}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>手寸</label>
                                <input class="form-control" value="${orders.hand}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>材质</label>
                                <input class="form-control" value="${orders.material}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>颜色</label>
                                <input class="form-control" value="${orders.color}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>寓意</label>
                                <input class="form-control" value="${orders.styleMoral!""}" disabled>
                            </div>
                        </div>
                    </div>

                    <label style="margin-left: 30px;">钻石信息</label>
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
                                <input class="form-control" value="${(orders.parts.exZsZs+orders.parts.exZsBh)!"无"}" disabled>
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
                    [#--<div class="row">--]
                        [#--<div class="col-xs-5 col-md-4">--]
                            [#--<div class="form-group">--]
                                [#--<label for="story">地址</label>--]
                                [#--<input class="form-control" value="${orders.customerAddress}" disabled>--]
                            [#--</div>--]
                        [#--</div>--]
                    [#--</div>--]

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
                </div>
                <!-- /.series-body -->

                <div class="box-footer">
                    <div class="row">
                        <div class="col-xs-offset-1">
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
[/@content]
