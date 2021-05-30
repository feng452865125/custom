[#include "../../component/content.ftl" /]

[@content title="第三方供应商维护" subTitle=""]
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
                                <label for="name">名称</label>
                                <p>${thirdSupplier.name}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="shortName">英文简称</label>
                                <p>${thirdSupplier.shortName}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="address">公司地址</label>
                                <p>${thirdSupplier.address!""}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="manName">负责人姓名</label>
                                <p>${thirdSupplier.manName!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="manMobile">号码</label>
                                <p>${thirdSupplier.manMobile!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="manEmail">邮箱</label>
                                <p>${thirdSupplier.manEmail!""}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="apiUrl">接口地址</label>
                                <p>${thirdSupplier.apiUrl!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="apiUsername">接口登录账号</label>
                                <p>${thirdSupplier.apiUsername!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="apiPassword">接口登录密码</label>
                                <p>${thirdSupplier.apiPassword!""}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="keerUsername">千叶登录账号</label>
                                <p>${thirdSupplier.keerUsername!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="keerPassword">千叶登录密码</label>
                                <p>***</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="score">千叶评分（默认满分100，用作下单优先级）</label>
                                <p>${thirdSupplier.score!""}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="workDate">开始合作时间</label>
                                <p>${(thirdSupplier.workDate?string('yyyy-MM-dd'))!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="sapCode">sap供应商编码</label>
                                <p>${thirdSupplier.sapCode!""}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="filterAddress">接收库存区域（用/隔开，例:SZ/深圳/HK/HONGKONG）</label>
                                <p>${thirdSupplier.filterAddress!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="whiteZsbhList">证书号过滤白名单（用/隔开，例:XXX001/XXXX002）</label>
                                <p>${thirdSupplier.whiteZsbhList!""}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="filterAddress">库存地统一改为HK（用/隔开，例:香港A/HONGKONG/H-K）</label>
                                <p>${thirdSupplier.locationInHk!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="locationInSz">库存地统一改为SZ（用/隔开，例:深圳/SZ(在库)）</label>
                                <p>${thirdSupplier.locationInSz!""}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="enableInHk">默认香港</label>
                                <p>[#if thirdSupplier.enableInHk?? && thirdSupplier.enableInHk == 1]
                                    上架
                                [#else]
                                    下架
                                [/#if]</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="enableInSz">默认深圳</label>
                                <p>[#if thirdSupplier.enableInSz?? && thirdSupplier.enableInSz == 1]
                                    上架
                                [#else]
                                    下架
                                [/#if]</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="isEnabled">是否合作</label>
                                <p>
                                    [#if thirdSupplier.status]
                                        是
                                    [#else]
                                        否
                                    [/#if]
                                </p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-6">
                            <div class="form-group">
                                <label for="remark">描述</label>
                                <p>${thirdSupplier.remark!""}</p>
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
