[#include "../../component/content.ftl" /]

[@content title="花头维护" subTitle=""]
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
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="code">花头编码</label>
                                <p>${parts.code}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="name">花头名称</label>
                                <p>${parts.name}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="ys">样式</label>
                                <p>${parts.ys}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-1">
                            <div class="form-group">
                                <label for="level">前端展示排序</label>
                                <p>${parts.level}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="kk">开口</label>
                                <p>${parts.kk}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="fsKd">分数</label>
                                <p>${parts.fsKd}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="isShow">同系列主要展示</label>
                                <p>
                                    [#if parts.isShow?? && parts.isShow == true]
                                        是
                                    [#else]
                                        否
                                    [/#if]
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="imgUrl">正面图</label>
                                <div class="img_show_div" style="height: 108px;">
                                    [#if parts.imgUrl?? && parts.imgUrl?length > 0]
                                        <img src="${parts.imgUrl}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="imgUrl">侧面图</label>
                                <div class="img_show_div" style="height: 108px;">
                                    [#if parts.sideImgUrl?? && parts.sideImgUrl?length > 0]
                                        <img src="${parts.sideImgUrl}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                    [#--<div class="col-xs-offset-1 col-xs-5 col-md-4">--]
                    [#--<div class="form-group">--]
                    [#--<label for="threeD30">3D编码（30分）</label>--]
                    [#--<p>${parts.threeD30}</p>--]
                    [#--</div>--]
                    [#--</div>--]
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <div class="form-group">
                                    <label for="remark">所属组</label>
                                    <p>${parts.groupTypeName!""}</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <div class="form-group">
                                    <label for="jbYsRecommend">推荐戒臂样式</label>
                                    <p>${parts.jbYsRecommend!""}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="threeD50">3D编码（50分）</label>
                                <p>${parts.threeD50}</p>
                            </div>
                        </div>
                    </div>
                [#--<div class="row">--]
                [#--<div class="col-xs-offset-1 col-xs-5 col-md-4">--]
                [#--<div class="form-group">--]
                [#--<label for="threeD70">3D编码（70分）</label>--]
                [#--<p>${parts.threeD70}</p>--]
                [#--</div>--]
                [#--</div>--]
                [#--<div class="col-xs-5 col-md-4">--]
                [#--<div class="form-group">--]
                [#--<label for="threeD100">3D编码（100分）</label>--]
                [#--<p>${parts.threeD100}</p>--]
                [#--</div>--]
                [#--</div>--]
                [#--</div>--]
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exYy">寓意</label>
                                <p>${parts.exYy}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exJszl">金属重量</label>
                                <p>${parts.exJszl / 1000}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exJscz">金属材质</label>
                                <p>${parts.exJscz}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exJsys">金属颜色</label>
                                <p>${parts.exJsys}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="exQghy">情感含义</label>
                                <p>${parts.exQghy}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="remark">描述</label>
                                <p>${parts.remark}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isRecommend">是否推荐</label>
                                <p>
                                    [#if parts.isRecommend?? && parts.isRecommend == true]
                                        是
                                    [#else]
                                        否
                                    [/#if]
                                </p>
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
