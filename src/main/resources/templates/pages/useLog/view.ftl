[#include "../../component/content.ftl" /]

[@content title="报表查询" subTitle=""]
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
                    <label style="margin-left: 30px;">基本信息（编号：${useLog.id}）</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label>店铺</label>
                                <input class="form-control" value="${useLog.userName!""}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label>来源</label>
                                <input class="form-control" value="${useLog.sourceName!""}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label>类型</label>
                                <input class="form-control" value="${useLog.typeName!""}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label>样式名称</label>
                                <input class="form-control" value="${useLog.styleName!""}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label>样式编码</label>
                                <input class="form-control" value="${useLog.styleCode!""}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label>样式类型</label>
                                <input class="form-control" value="${useLog.style.typeName!""}" disabled>
                            </div>
                        </div>
                    </div>

                    <label style="margin-left: 30px;">样式详情</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="htYs">花头样式</label>
                                <p>${useLog.style.htYs!"无"}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="jbYs">戒臂样式</label>
                                <p>${useLog.style.jbYs!"无"}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="series">系列</label>
                                <p>${useLog.style.series!"无"}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="imgsUrl">轮播图文件</label>
                                <div class="img_show_div" style="display: flex; height: 108px;">
                                    [#if useLog.style.imgsUrlList ??]
                                        [#list useLog.style.imgsUrlList as imgsUrl]
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
                                <label for="imgMaxUrl">详情图</label>
                                <div class="img_show_div" style="height: 108px;">
                                    [#if useLog.style.imgMaxUrl?? && useLog.style.imgMaxUrl?length > 0]
                                        <img src="${useLog.style.imgMaxUrl}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="imgUrl">缩略图</label>
                                <div class="img_show_div" style="height: 108px;">
                                    [#if useLog.style.imgUrl?? && useLog.style.imgUrl?length > 0]
                                        <img src="${useLog.style.imgUrl}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="rate45imgUrl">45度图</label>
                                <div class="img_show_div" style="height: 108px;">
                                     [#if useLog.style.rate45imgUrl?? && useLog.style.rate45imgUrl?length > 0]
                                         <img src="${useLog.style.rate45imgUrl}" class="img_view_show"
                                              style="height: 100%;">
                                     [/#if]
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="fanUrl">侧面图</label>
                                <div class="img_show_div" style="height: 108px;">
                                     [#if useLog.style.fanUrl?? && useLog.style.fanUrl?length > 0]
                                         <img src="${useLog.style.fanUrl}" class="img_view_show" style="height: 100%;">
                                     [/#if]
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="videoUrl">视频文件</label>
                                <div class="img_show_div" style="height: 108px;">
                                      [#if useLog.style.videoUrl?? && useLog.style.videoUrl?length > 0]
                                          <video src="${useLog.style.videoUrl}" controls="controls"
                                                 class="img_view_show" style="height: 100%;">
                                      [/#if]
                                </div>
                            </div>
                        </div>
                    </div>

                    [#if useLog.style.type?? && useLog.style.type == 3]
                        <label style="margin-left: 30px;">男戒信息</label>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="imgUrlBoy">详情图</label>
                                    <div class="img_show_div" style="height: 108px;">
                                    [#if useLog.style.imgUrlBoy?? && useLog.style.imgUrlBoy?length > 0]
                                        <img src="${useLog.style.imgUrlBoy}" class="img_view_show"
                                             style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlBoy30">试戴（30分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                    [#if useLog.style.wearUrlBoy30?? && useLog.style.wearUrlBoy30?length > 0]
                                        <img src="${useLog.style.wearUrlBoy30}" class="img_view_show"
                                             style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlBoy50">试戴（50分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if useLog.style.wearUrlBoy50?? && useLog.style.wearUrlBoy50?length > 0]
                                         <img src="${useLog.style.wearUrlBoy50}" class="img_view_show"
                                              style="height: 100%;">
                                     [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlBoy70">试戴（70分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                    [#if useLog.style.wearUrlBoy70?? && useLog.style.wearUrlBoy70?length > 0]
                                        <img src="${useLog.style.wearUrlBoy70}" class="img_view_show"
                                             style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlBoy100">试戴（100分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if useLog.style.wearUrlBoy100?? && useLog.style.wearUrlBoy100?length > 0]
                                         <img src="${useLog.style.wearUrlBoy100}" class="img_view_show"
                                              style="height: 100%;">
                                     [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                        <label style="margin-left: 30px;">女戒信息</label>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="imgUrlGirl">详情图</label>
                                    <div class="img_show_div" style="height: 108px;">
                                    [#if useLog.style.imgUrlGirl?? && useLog.style.imgUrlGirl?length > 0]
                                        <img src="${useLog.style.imgUrlGirl}" class="img_view_show"
                                             style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlGirl30">试戴（30分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                    [#if useLog.style.wearUrlGirl30?? && useLog.style.wearUrlGirl30?length > 0]
                                        <img src="${useLog.style.wearUrlGirl30}" class="img_view_show"
                                             style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlGirl50">试戴（50分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if useLog.style.wearUrlGirl50?? && useLog.style.wearUrlGirl50?length > 0]
                                         <img src="${useLog.style.wearUrlGirl50}" class="img_view_show"
                                              style="height: 100%;">
                                     [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlGirl70">试戴（70分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                    [#if useLog.style.wearUrlGirl70?? && useLog.style.wearUrlGirl70?length > 0]
                                        <img src="${useLog.style.wearUrlGirl70}" class="img_view_show"
                                             style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlGirl100">试戴（100分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if useLog.style.wearUrlGirl100?? && useLog.style.wearUrlGirl100?length > 0]
                                         <img src="${useLog.style.wearUrlGirl100}" class="img_view_show"
                                              style="height: 100%;">
                                     [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                    [#else]
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrl30">试戴（30分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                    [#if useLog.style.wearUrl30?? && useLog.style.wearUrl30?length > 0]
                                        <img src="${useLog.style.wearUrl30}" class="img_view_show"
                                             style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrl50">试戴（50分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if useLog.style.wearUrl50?? && useLog.style.wearUrl50?length > 0]
                                         <img src="${useLog.style.wearUrl50}" class="img_view_show"
                                              style="height: 100%;">
                                     [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrl70">试戴（70分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                    [#if useLog.style.wearUrl70?? && useLog.style.wearUrl70?length > 0]
                                        <img src="${useLog.style.wearUrl70}" class="img_view_show"
                                             style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrl100">试戴（100分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if useLog.style.wearUrl100?? && useLog.style.wearUrl100?length > 0]
                                         <img src="${useLog.style.wearUrl100}" class="img_view_show"
                                              style="height: 100%;">
                                     [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                    [/#if]

                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="story">故事</label>
                                <p>${useLog.style.story}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="meaning">情感含义</label>
                                <p>${useLog.style.meaning}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="moral">寓意</label>
                                <p>${useLog.style.moral}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="features">款式特点</label>
                                <p>${useLog.style.features}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="remark">图片描述</label>
                                <p>${useLog.style.remark}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isRecommend">是否推荐</label>
                                <p>
                                    [#if useLog.style.isRecommend]
                                        是
                                    [#else]
                                        否
                                    [/#if]
                                </p>
                            </div>
                        </div>
                    </div>

                    <label style="margin-left: 30px;">工艺介绍详情</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-8">
                            <div class="form-group">
                                <label for="introductionWord">文字描述</label>
                                <p>${useLog.style.introductionWord}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="introductionVideo">视频文件</label>
                                <div class="img_show_div" style="height: 108px;">
                                      [#if useLog.style.introductionVideo?? && useLog.style.introductionVideo?length > 0]
                                          <video src="${useLog.style.introductionVideo}" controls="controls"
                                                 class="img_view_show"
                                                 style="height: 100%;">
                                      [/#if]
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="imgsUrl">图片文件</label>
                                <div class="img_show_div" style="display: flex; height: 108px;">
                                    [#if useLog.style.imgsUrlList ??]
                                        [#list useLog.style.imgsUrlList as imgsUrl]
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
