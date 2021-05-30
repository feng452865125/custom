[#include "../../component/content.ftl" /]

[@content title="第三方石头维护" subTitle=""]
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
                    <label style="margin: 30px;">基本信息</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="exZsBh">证书号</label>
                                <p>${thirdStone.exZsBh}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="exZsZs">证书</label>
                                <p>${thirdStone.exZsZs}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="exZsZl">钻重</label>
                                <p>${(thirdStone.exZsZl / 1000)!""}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="company">第三方公司</label>
                                <p>${thirdStone.company}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="dollarPrice">采购价</label>
                                 [#if thirdStone.company == 'CHINASTAR' || thirdStone.company == 'HB' || thirdStone.company == 'JP'
                                 || thirdStone.company == 'PG' || thirdStone.company == 'DIAMART' || thirdStone.company == 'OPO'
                                 || thirdStone.company == 'DHA' || thirdStone.company == 'KBS' || thirdStone.company == 'HB2'
                                 || thirdStone.company == 'KDL']
                                    <p>${thirdStone.dollarPrice/100!""}（美元）</p>
                                 [#else]
                                    <p>${thirdStone.dollarPrice/100!""}（人民币）</p>
                                 [/#if]
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="createDate">接收时间</label>
                                <p>${(thirdStone.createDate?string('yyyy-MM-dd HH:mm:ss'))!""}</p>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="enableStatus">上架状态</label>
                                [#if thirdStone.enableStatus == 1]
                                    <p>上架</p>
                                [#else]
                                    <p>下架</p>
                                [/#if]
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="enableDate">上架时间</label>
                                <p>${(thirdStone.enableDate?string('yyyy-MM-dd HH:mm:ss'))!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="enableDate">操作时间</label>
                                <p>${(thirdStone.enableOverDate?string('yyyy-MM-dd HH:mm:ss'))!""}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="location">库存地</label>
                                <p>${thirdStone.location}</p>
                            </div>
                        </div>
                    </div>

                    <label style="margin: 30px;">其他属性</label>

                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="exZsYs">颜色</label>
                                <p>${thirdStone.exZsYs}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="exZsJd">净度</label>
                                <p>${thirdStone.exZsJd}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="exZsYg">荧光</label>
                                <p>${thirdStone.exZsYg}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="exZsQg">切工</label>
                                <p>${thirdStone.exZsQg}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="exZsPg">抛光</label>
                                <p>${thirdStone.exZsPg}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="exZsDc">对称</label>
                                <p>${thirdStone.exZsDc}</p>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="saleBack">退点</label>
                                <p>${thirdStone.saleBack}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="bic">中间黑点</label>
                                <p>${thirdStone.bic}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="bis">边缘黑点</label>
                                <p>${thirdStone.bis}</p>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="milky">奶色</label>
                                <p>${thirdStone.milky}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="colsh">咖色</label>
                                <p>${thirdStone.colsh}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="blackInc">黑点</label>
                                <p>${thirdStone.blackInc}</p>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="eyeclean">肉眼干净</label>
                                <p>${thirdStone.eyeclean}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="green">绿色</label>
                                <p>${thirdStone.green}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="table">台宽比</label>
                                <p>${thirdStone.table}</p>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="hues">色调</label>
                                <p>${thirdStone.hues}</p>
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
