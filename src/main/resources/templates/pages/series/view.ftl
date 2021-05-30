[#include "../../component/content.ftl" /]

[@content title="系列维护" subTitle=""]
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
                                <label for="name">名称</label>
                                <p>${series.name}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="level">前端展示顺序</label>
                                <p>${series.level}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="remark">描述</label>
                                <p>${series.remark}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="url">配图</label>
                                <div>
                                    <img src="${series.imgUrl}" class="img_shadow"
                                         style="height: 108px; margin: 10px 0;">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isEnabled">是否启用</label>
                                <p>
                                    [#if series.enabled]
                                        是
                                    [#else]
                                        否
                                    [/#if]
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-8">
                            <div class="form-group">
                                <label for="introductionWord">工艺介绍：文字描述</label>
                                <p>${series.introductionWord}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="introductionVideo">工艺介绍：视频文件</label>
                                <div class="img_show_div" style="height: 108px;">
                                      [#if series.introductionVideo?? && series.introductionVideo?length > 0]
                                          <video src="${series.introductionVideo}" controls="controls" class="img_view_show"
                                                 style="height: 100%;">
                                      [/#if]
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="imgsUrl">工艺介绍：图片文件</label>
                                <div class="img_show_div" style="display: flex; height: 108px;">
                                    [#if series.imgsUrlList ??]
                                        [#list series.imgsUrlList as imgsUrl]
                                            <img src="${imgsUrl}" class="img_view_show"
                                                 style="margin-right: 40px; height: 100%;">
                                        [/#list]
                                    [/#if]
                                </div>
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
