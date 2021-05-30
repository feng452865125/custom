[#include "../../component/content.ftl" /]

[@content title="钻石（克拉展）维护" subTitle=""]
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
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="code">钻石编码</label>
                                <p>${diamondKela.code}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">钻石名称</label>
                                <p>${diamondKela.name}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsZl">质量（单位：克拉）</label>
                                <p>${diamondKela.exZsZl / 1000}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="statusName">状态</label>
                                <p>${diamondKela.statusName!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="price">价格（单位：元）</label>
                                <p>${diamondKela.price / 100}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="imgUrl">缩略图</label>
                                <div class="img_show_div" style="height: 108px;">
                                    [#if diamondKela.imgUrl?? && diamondKela.imgUrl?length > 0]
                                        <img src="${diamondKela.imgUrl}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="remark">描述</label>
                                <p>${diamondKela.remark}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsXz">形状</label>
                                <p>${diamondKela.exZsXz}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <div class="form-group">
                                    <label for="exZsZs">证书</label>
                                    <p>${diamondKela.exZsZs}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsYs">颜色</label>
                                <p>${diamondKela.exZsYs}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsJd">净度</label>
                                <p>${diamondKela.exZsJd}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsQg">切工</label>
                                <p>${diamondKela.exZsQg}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsPg">抛光</label>
                                <p>${diamondKela.exZsPg}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsDc">对称</label>
                                <p>${diamondKela.exZsDc}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exZsYg">荧光</label>
                                <p>${diamondKela.exZsYg}</p>
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
