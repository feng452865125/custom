[#include "../../component/content.ftl" /]

[@content title="价格查询" subTitle=""]
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
                    <label style="margin-left: 30px;">4C${(basePrice.id)!}</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>切工</label>
                                <input class="form-control" value="${(basePrice.qg)!}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>抛光</label>
                                <input class="form-control" value="${(basePrice.pg)!}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>对称</label>
                                <input class="form-control" value="${(basePrice.dc)}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>价格（单位：元）</label>
                                <input class="form-control" value="${basePrice.price/100}" disabled>
                            </div>
                        </div>
                    </div>
                    <label style="margin-left: 30px;">戒托信息</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>戒臂宽度</label>
                                <input class="form-control" value="${(basePrice.product.exJbKd)!}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>手寸</label>
                                <input class="form-control" value="${(basePrice.product.exSc)!}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>材质</label>
                                <input class="form-control" value="${(basePrice.product.jscz)!}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>颜色</label>
                                <input class="form-control" value="${(basePrice.product.jsys)!}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>寓意</label>
                                <input class="form-control" value="${(basePrice.product.exYy)!}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>价格（单位：元）</label>
                                <input class="form-control" value="${(basePrice.product.price/100)!}" disabled>
                            </div>
                        </div>
                    </div>

                    <label style="margin-left: 30px;">钻石信息${(basePrice.parts.id)}</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>颜色</label>
                                <input class="form-control" value="${(basePrice.parts.exZsYs)!"无"}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>净度</label>
                                <input class="form-control" value="${(basePrice.parts.exZsJd)!"无"}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>切工</label>
                                <input class="form-control" value="${(basePrice.parts.exZsQg)!"无"}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>重量</label>
                                <input class="form-control" value="${(basePrice.parts.exZsZl / 1000)!"无"}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>证书</label>
                                <input class="form-control" value="${(basePrice.parts.exZsZs)!"无"}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>荧光</label>
                                <input class="form-control" value="${(basePrice.parts.exZsYg)!"无"}" disabled>
                            </div>
                        </div>
                    </div>

                    <label style="margin-left: 30px;">价格</label>
                    [#--<div class="row">--]
                        [#--<div class="col-xs-offset-1 col-xs-5 col-md-4">--]
                            [#--<div class="form-group">--]
                                [#--<label>门店系数</label>--]
                                [#--<input class="form-control" value="${(priceMultiple)!"1.8"}" disabled>--]
                            [#--</div>--]
                        [#--</div>--]
                        [#--<div class="col-xs-5 col-md-4">--]
                            [#--<div class="form-group">--]
                                [#--<label>4C*钻石重量*门店系数+戒托价格（单位：元）</label>--]
                                [#--<input class="form-control" value="${basePrice.price/100}*${(priceMultiple)!}*${(basePrice.parts.exZsZl / 1000)!}+${(basePrice.product.price/100)!}=${(basePrice.price/100)*(basePrice.parts.exZsZl / 1000)*(priceMultiple)+(basePrice.product.price/100)}" disabled>--]
                            [#--</div>--]
                        [#--</div>--]
                    [#--</div>--]
                    <div class="row">

                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label>总价格（单位：元）</label>
                                <input id="number" class="form-control" value="${(basePrice.price/100)*(basePrice.parts.exZsZl / 1000)*(priceMultiple)+(basePrice.product.price/100)}" disabled>
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
<script>
    $(function(){
        $("#number").val(parseInt($("#number").val()))
    })
</script>
[/@content]
