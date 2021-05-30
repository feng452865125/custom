[#include "../../component/content.ftl" /]

[@content title="卡片维护" subTitle=""]
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
                                <label for="cardCode">卡号</label>
                                <input class="form-control" value="${customCard.cardCode}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardCompany">合作方</label>
                                <input class="form-control" value="${customCard.cardCompany}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardStatusName">卡片状态</label>
                                <input class="form-control" value="${customCard.cardStatusName}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardCreateUser">创建人</label>
                                <input class="form-control" value="${customCard.cardCreateUser}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardCreateDate">创建时间</label>
                                <input class="form-control"
                                       value="${(customCard.cardCreateDate?string('yyyy-MM-dd HH:mm:ss'))!""}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardExpireDate">有效时间截止</label>
                                <input class="form-control"
                                       value="${(customCard.cardExpireDate?string('yyyy-MM-dd HH:mm:ss'))!""}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardActivateUser">激活客户信息</label>
                                <input class="form-control" value="${customCard.cardActivateUser}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardActivateDate">激活时间</label>
                                <input class="form-control"
                                       value="${(customCard.cardActivateDate?string('yyyy-MM-dd HH:mm:ss'))!""}"
                                       disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardUseStore">核销门店信息</label>
                                <input class="form-control" value="${customCard.cardUseStore}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardUseDate">核销时间</label>
                                <input class="form-control"
                                       value="${(customCard.cardUseDate?string('yyyy-MM-dd HH:mm:ss'))!""}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardPlatform">使用平台</label>
                                <input class="form-control" value="${customCard.cardPlatform}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardPlatformOrder">平台订单号</label>
                                <input class="form-control" value="${customCard.cardPlatformOrder}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardUseUser">使用人信息</label>
                                <input class="form-control" value="${customCard.cardUseUser}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardRemark">其他备注</label>
                                <input class="form-control" value="${customCard.cardRemark}" disabled>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardEnableUser">
                                    [#if customCard.cardEnable == 0 ]
                                        冻结人
                                    [#else]
                                        解冻人
                                    [/#if]
                                </label>
                                <input class="form-control" value="${customCard.cardEnableUser}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardUseDate">
                                    [#if customCard.cardEnable == 0 ]
                                        冻结时间
                                    [#else]
                                        解冻时间
                                    [/#if]
                                </label>
                                <input class="form-control"
                                       value="${(customCard.cardEnableDate?string('yyyy-MM-dd HH:mm:ss'))!""}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="cardUseStore">卡片冻结状态</label>
                                [#if customCard.cardEnable == 0 ]
                                    <input class="form-control" value="已冻结" disabled>
                                [#else]
                                    <input class="form-control" value="已解冻" disabled>
                                [/#if]
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
