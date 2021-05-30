[#include "../../component/content.ftl" /]

[@content title="产品维护" subTitle=""]
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
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="code">产品编码</label>
                                <p>${product.code}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="name">产品名称</label>
                                <p>${product.name}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="ktCode">客定码</label>
                                <p>${product.ktCode}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exZsYs">钻石颜色</label>
                                <p>${product.exZsYs}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exZsJd">钻石净度</label>
                                <p>${product.exZsJd}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="price">价格（单位：元）</label>
                                <p>${product.price / 100}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exFs">钻石分数</label>
                                <p>${product.exFs}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exDiamondFsMin">生产要求备注，最小值</label>
                                <p>${(product.exDiamondFsMin / 1000)!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exDiamondFsMax">生产要求备注，最大值</label>
                                <p>${(product.exDiamondFsMax / 1000)!""}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="exYy">寓意</label>
                                <p>${product.exYy}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="nxbs">内镶宝石</label>
                                <p>${product.nxbs}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="jsys">金属颜色</label>
                                <p>${product.jsys}</p>
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
