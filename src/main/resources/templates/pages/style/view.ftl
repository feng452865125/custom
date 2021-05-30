[#include "../../component/content.ftl" /]

[@content title="样式维护" subTitle=""]
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
                    <label style="margin-left: 30px;">基础信息</label>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="code">样式编码</label>
                                <p>${style.code}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="name">样式名称</label>
                                <p>${style.name}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="typeName">类型</label>
                                <p>${style.typeName}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="htYs">花头样式</label>
                                <p>${style.htYs!"无"}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="jbYs">戒臂样式</label>
                                <p>${style.jbYs!"无"}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="series">系列</label>
                                <p>${style.series!"无"}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="imgsUrl">轮播图文件</label>
                                <div class="img_show_div" style="display: flex; height: 108px;">
                                    [#if style.imgsUrlList ??]
                                        [#list style.imgsUrlList as imgsUrl]
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
                                <label for="imgUrl">详情图</label>
                                <div class="img_show_div" style="height: 108px;">
                                    [#if style.imgMaxUrl?? && style.imgMaxUrl?length > 0]
                                        <img src="${style.imgMaxUrl}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="imgUrl">缩略图</label>
                                <div class="img_show_div" style="height: 108px;">
                                    [#if style.imgUrl?? && style.imgUrl?length > 0]
                                        <img src="${style.imgUrl}" class="img_view_show" style="height: 100%;">
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
                                     [#if style.rate45imgUrl?? && style.rate45imgUrl?length > 0]
                                         <img src="${style.rate45imgUrl}" class="img_view_show" style="height: 100%;">
                                     [/#if]
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="fanUrl">侧面图</label>
                                <div class="img_show_div" style="height: 108px;">
                                     [#if style.fanUrl?? && style.fanUrl?length > 0]
                                         <img src="${style.fanUrl}" class="img_view_show" style="height: 100%;">
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
                                      [#if style.videoUrl?? && style.videoUrl?length > 0]
                                          <video src="${style.videoUrl}" controls="controls" class="img_view_show"
                                                 style="height: 100%;">
                                      [/#if]
                                </div>
                            </div>
                        </div>
                    </div>

                    [#if style.type?? && style.type == 3]
                        <label style="margin-left: 30px;">男戒信息</label>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="imgUrlBoy">详情图</label>
                                    <div class="img_show_div" style="height: 108px;">
                                    [#if style.imgUrlBoy?? && style.imgUrlBoy?length > 0]
                                        <img src="${style.imgUrlBoy}" class="img_view_show" style="height: 100%;">
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
                                    [#if style.wearUrlBoy30?? && style.wearUrlBoy30?length > 0]
                                        <img src="${style.wearUrlBoy30}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlBoy50">试戴（50分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if style.wearUrlBoy50?? && style.wearUrlBoy50?length > 0]
                                         <img src="${style.wearUrlBoy50}" class="img_view_show" style="height: 100%;">
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
                                    [#if style.wearUrlBoy70?? && style.wearUrlBoy70?length > 0]
                                        <img src="${style.wearUrlBoy70}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlBoy100">试戴（100分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if style.wearUrlBoy100?? && style.wearUrlBoy100?length > 0]
                                         <img src="${style.wearUrlBoy100}" class="img_view_show" style="height: 100%;">
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
                                    [#if style.imgUrlGirl?? && style.imgUrlGirl?length > 0]
                                        <img src="${style.imgUrlGirl}" class="img_view_show" style="height: 100%;">
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
                                    [#if style.wearUrlGirl30?? && style.wearUrlGirl30?length > 0]
                                        <img src="${style.wearUrlGirl30}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlGirl50">试戴（50分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if style.wearUrlGirl50?? && style.wearUrlGirl50?length > 0]
                                         <img src="${style.wearUrlGirl50}" class="img_view_show" style="height: 100%;">
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
                                    [#if style.wearUrlGirl70?? && style.wearUrlGirl70?length > 0]
                                        <img src="${style.wearUrlGirl70}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrlGirl100">试戴（100分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if style.wearUrlGirl100?? && style.wearUrlGirl100?length > 0]
                                         <img src="${style.wearUrlGirl100}" class="img_view_show" style="height: 100%;">
                                     [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                    [#elseif style.series == '大克重黄金手镯' || style.series == '大分数钻石']
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="gdSc">手寸或长度</label>
                                    <p>${style.gdSc}</p>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="gdJsys">金属颜色</label>
                                    <p>${style.gdJsys}</p>
                                </div>
                            </div>
                        </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="gdYsfw">分级颜色范围</label>
                                <p>${style.gdYsfw}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="gdJdfw">分级净度范围</label>
                                <p>${style.gdJdfw}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="gdPriceMin">直营最低上柜价（单位：元）</label>
                                <p>${style.gdPriceMin}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="gdPriceMax">直营最高上柜价（单位：元）</label>
                                <p>${style.gdPriceMax}</p>
                            </div>
                        </div>
                    </div>
                     <div class="row">
                         <div class="col-xs-offset-1 col-xs-5 col-md-4">
                             <div class="form-group">
                                 <label for="gdZlAll">总重</label>
                                 <p>${style.gdZlAll}</p>
                             </div>
                         </div>
                         <div class="col-xs-5 col-md-2">
                             <div class="form-group">
                                 <label for="gdZlMin">直营最低克重</label>
                                 <p>${style.gdZlMin}</p>
                             </div>
                         </div>
                         <div class="col-xs-5 col-md-2">
                             <div class="form-group">
                                 <label for="gdZlMax">直营最高克重</label>
                                 <p>${style.gdZlMax}</p>
                             </div>
                         </div>
                     </div>
                    [#else]
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrl30">试戴（30分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                    [#if style.wearUrl30?? && style.wearUrl30?length > 0]
                                        <img src="${style.wearUrl30}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrl50">试戴（50分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if style.wearUrl50?? && style.wearUrl50?length > 0]
                                         <img src="${style.wearUrl50}" class="img_view_show" style="height: 100%;">
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
                                    [#if style.wearUrl70?? && style.wearUrl70?length > 0]
                                        <img src="${style.wearUrl70}" class="img_view_show" style="height: 100%;">
                                    [/#if]
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="wearUrl100">试戴（100分）</label>
                                    <div class="img_show_div" style="height: 108px;">
                                     [#if style.wearUrl100?? && style.wearUrl100?length > 0]
                                         <img src="${style.wearUrl100}" class="img_view_show" style="height: 100%;">
                                     [/#if]
                                    </div>
                                </div>
                            </div>
                        </div>
                    [/#if]

                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="moral">寓意</label>
                                <p>${style.moral}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="features">款式特点</label>
                                <p>${style.features}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="story">故事</label>
                                <p>${style.story}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="meaning">情感含义</label>
                                <p>${style.meaning}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="remark">图片描述</label>
                                <p>${style.remark}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="enabled">是否展示</label>
                                <p>
                                    [#if style.enabled]
                                        是
                                    [#else]
                                        否
                                    [/#if]
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="isRecommend">是否推荐</label>
                                <p>
                                    [#if style.isRecommend]
                                        是
                                    [#else]
                                        否
                                    [/#if]
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="isPrinting">是否印花</label>
                                <p>
                                    [#if style.isPrinting]
                                        是
                                    [#else]
                                        否
                                    [/#if]
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="isNxbs">是否内镶宝石</label>
                                <p>
                                    [#if style.isNxbs]
                                        是
                                    [#else]
                                        否
                                    [/#if]
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-2">
                            <div class="form-group">
                                <label for="isGq">是否改圈</label>
                                <p>
                                    [#if style.isGq]
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
