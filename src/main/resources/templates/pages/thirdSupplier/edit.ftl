[#include "../../component/content.ftl" /]
[#include "../../component/areaTreeModal.ftl" /]

[@content title="第三方供应商维护" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/iCheck/all.css">
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">

<div class="row">
    <!-- left column -->
    <div class="col-md-12">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">编辑</h3>
            </div>
            <!-- /.series-header -->
            <!-- form start -->
            <form id="form1" action="${springMacroRequestContext.contextPath}/thirdSupplier/${thirdSupplier.id}"
                  role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="name">公司名</label>
                                <input class="form-control" name="name" id="name" placeholder="请输入公司名"
                                       data-rule="required" value="${thirdSupplier.name}"
                                       data-msg-required="公司名不能为空">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="shortName">英文简称</label>
                                <input class="form-control" name="shortName" id="shortName"
                                       value="${thirdSupplier.shortName}" disabled>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="address">公司地址</label>
                                <input class="form-control" name="address" id="address" placeholder="请输入地址"
                                       value="${thirdSupplier.address!""}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="manName">负责人姓名</label>
                                <input class="form-control" name="manName" id="manName" placeholder="请输入负责人姓名"
                                       value="${thirdSupplier.manName!""}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="manMobile">号码</label>
                                <input class="form-control" name="manMobile" id="manMobile" placeholder="请输入负责人号码"
                                       value="${thirdSupplier.manMobile!""}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="manEmail">邮箱</label>
                                <input class="form-control" name="manEmail" id="manEmail" placeholder="请输入负责人邮箱"
                                       value="${thirdSupplier.manEmail!""}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="apiUrl">接口地址</label>
                                <input class="form-control" name="apiUrl" id="apiUrl" placeholder="请输入接口地址"
                                       value="${thirdSupplier.apiUrl!""}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="apiUsername">接口登录账号</label>
                                <input class="form-control" name="apiUsername" id="apiUsername" placeholder="请输入接口登录账号"
                                       value="${thirdSupplier.apiUsername!""}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="apiPassword">接口登录密码</label>
                                <input class="form-control" name="apiPassword" id="apiPassword" placeholder="请输入接口登录密码"
                                       value="${thirdSupplier.apiPassword!""}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="keerUsername">千叶登录账号</label>
                                <input class="form-control" name="keerUsername" id="keerUsername"
                                       value="${thirdSupplier.keerUsername!""}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="keerPassword">千叶登录密码</label>
                                <input class="form-control" name="keerPassword" id="keerPassword"
                                       placeholder="请输入千叶登录密码">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="score">千叶评分（默认满分100，用作下单优先级）</label>
                                <input class="form-control" name="score" id="score" placeholder="请输入评分"
                                       value="${thirdSupplier.score!""}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="workDate">开始合作时间</label>
                                <div class="input-daterange">
                                    <input class="form-control workDate" style="text-align: left;" name="workDate" placeholder="请选择开始合作时间" value="${(thirdSupplier.workDate?string('yyyy-MM-dd'))!""}">
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="sapCode">sap供应商编码</label>
                                <input class="form-control" name="sapCode" id="sapCode" placeholder="请输入sap供应商编码" value="${thirdSupplier.sapCode!""}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="filterAddress">接收库存区域（用/隔开，例:SZ/深圳/HK/HONGKONG）</label>
                                <input class="form-control" name="filterAddress" id="filterAddress" placeholder="哪些库存地允许接收" value="${thirdSupplier.filterAddress!""}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="whiteZsbhList">证书号过滤白名单（用/隔开，例:XXX001/XXXX002）</label>
                                <input class="form-control" name="whiteZsbhList" id="whiteZsbhList" placeholder="白名单跳过过滤规则，直接接收" value="${thirdSupplier.whiteZsbhList!""}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="locationInHk">库存地统一改为HK（用/隔开，例:香港A/HONGKONG/H-K）</label>
                                <input class="form-control" name="locationInHk" id="locationInHk" placeholder="设置的库存地地名改为'HK'" value="${thirdSupplier.locationInHk!""}">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="locationInSz">库存地统一改为SZ（用/隔开，例:深圳/SZ(在库)）</label>
                                <input class="form-control" name="locationInSz" id="locationInSz" placeholder="设置的库存地地名改为'SZ'" value="${thirdSupplier.locationInSz!""}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="enableInHk">默认香港</label>
                                <select class="form-control" id="enableInHk" name="enableInHk">
                                    <option value="0"
                                        [#if thirdSupplier.enableInHk?? && thirdSupplier.enableInHk == 1]
                                            selected
                                        [/#if]>下架
                                    </option>
                                    <option value="1"
                                        [#if thirdSupplier.enableInHk?? && thirdSupplier.enableInHk == 1]
                                            selected
                                        [/#if]>上架
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="enableInSz">默认深圳</label>
                                <select class="form-control" id="enableInSz" name="enableInSz">
                                    <option value="0"
                                        [#if thirdSupplier.enableInSz?? && thirdSupplier.enableInSz == 1]
                                            selected
                                        [/#if]>下架
                                    </option>
                                    <option value="1"
                                        [#if thirdSupplier.enableInSz?? && thirdSupplier.enableInSz == 1]
                                            selected
                                        [/#if]>上架
                                    </option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-3">
                            <div class="form-group">
                                <label for="isEnabled">是否合作</label>
                                <div class="icheck-group">
                                    <div class="icheck-item">
                                        <input type="radio" id="enabled" class="minimal" name="isEnabled" value=true
                                               data-rule="checked"
                                        [#if thirdSupplier.status]
                                               checked
                                        [/#if]
                                        >
                                        <label for="enabled">是</label>
                                    </div>
                                    <div class="icheck-item">
                                        <input type="radio" id="disabled" class="minimal" name="isEnabled" value=false
                                               data-rule="checked"
                                         [#if !thirdSupplier.status]
                                               checked
                                         [/#if]
                                        >
                                        <label for="disabled">否</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-6">
                            <div class="form-group">
                                <label for="remark">描述</label>
                                <textarea class="form-control remark_style" id="remark" name="remark"
                                          placeholder="其他描述">${thirdSupplier.remark!""}</textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.series-body -->

                <div class="box-footer">
                    <div class="row">
                        <div class="col-xs-offset-1 col-md-1">
                            <button type="submit" class="btn btn-primary btn-submit">提交</button>
                        </div>
                        <div class="col-md-1">
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

<script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/jquery.validator.js?local=zh-CN"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/jquery.validator.config.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/local/zh-CN.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/iCheck/icheck.min.js"></script>
[#--时间选择--]
<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datepicker/datepicker3.css">
<script>
    $(function () {
        $.submitForm("#form1", ".btn-submit", "PUT", ["${_csrf.parameterName}", "${_csrf.token}"],
                function (data) {
                    $.redirect("${springMacroRequestContext.contextPath}/thirdSupplier/list", function () {
                    });
                }, function (err) {

                }, function (data) {

                });

        $('.input-daterange').datepicker({
            language: "zh-CN",
            autoclose: true,
            clearBtn: true,
            format: "yyyy-mm-dd"
        });

    })
</script>
[/@content]
