[#include "../../component/content.ftl" /]
[@content title="制卡任务维护" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/iCheck/all.css">
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">

<div class="row">
    <!-- left column -->
    <div class="col-md-12">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">添加</h3>
            </div>
            <!-- /.series-header -->
            <!-- form start -->
            <form id="form1">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="cardTaskName">任务名称</label>
                                <input class="form-control" name="cardTaskName" id="cardTaskName" placeholder="请输入任务名称"
                                       data-rule="required"
                                       data-msg-required="任务名称不能为空"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="cardTaskCompany">合作方名称</label>
                                <input class="form-control" name="cardTaskCompany" id="cardTaskCompany" placeholder="请输入合作方名称"
                                       data-rule="required"
                                       data-msg-required="合作方名称不能为空"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="cardTaskCount">卡片数量（1~999999）</label>
                                <input class="form-control" name="cardTaskCount" id="cardTaskCount" placeholder="请输入卡片数量"
                                       type="number"
                                       data-rule="required;positiveInteger"
                                       data-msg-required="卡片数量不能为空"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="cardTaskPrice">卡片面额（单位：元）</label>
                                <select class="form-control" name="cardTaskPrice" id="cardTaskPrice" data-rule="noSelect">
                                    <option value="-1">请选择面额</option>
                                        [#list priceList as price]
                                                <option value="${price}">${price}</option>
                                        [/#list]
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="cardTaskLastDate">卡片有效期</label>
                                <div class="input-daterange">
                                    <input class="form-control cardTaskLastDate" style="text-align: left;"
                                           value="${cardTaskLastDate}"
                                           name="cardTaskLastDate" id="cardTaskLastDate"
                                           placeholder="请选择卡片有效期" data-rule="required"
                                           data-msg-required="卡片有效期不能为空">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="cardTaskCodeBefore">自定义卡号前六位</label>
                                <input class="form-control" name="cardTaskCodeBefore" id="cardTaskCodeBefore" placeholder="请输入自定义六位"
                                       data-rule="required;length(6)"
                                       data-msg-required="卡号前六位不能为空"
                                >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="cardTaskCodeMiddle">自定义卡号中四位（当前年份后两位+月份）</label>
                                <input class="form-control" name="cardTaskCodeMiddle" id="cardTaskCodeMiddle" placeholder="请输入自定义四位"
                                       type="number"
                                       data-rule="required;length(4);positiveInteger"
                                       data-msg-required="卡号中四位不能为空"
                                >
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.series-body -->

                <div class="box-footer">
                    <div class="row">
                        <div class="col-xs-offset-1 col-md-1">
                            <button type="button" class="btn btn-primary btn-submit">提交</button>
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
<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datepicker/datepicker3.css">

<script src="${springMacroRequestContext.contextPath}/plugins/loading/loading.js"></script>
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/loading/loading.css">

<script>
    $(function () {

    [#--$.submitForm("#form1", ".btn-submit", "POST", ["${_csrf.parameterName}", "${_csrf.token}"],--]
    [#--function (data) {--]
    [#--$.redirect("${springMacroRequestContext.contextPath}/customCardTask/list", function () {--]
    [#--});--]
    [#--}, function (err) {--]

    [#--}, function (data) {--]

    [#--})--]
        var $form = $('#form1');
        $form.on('click', '.btn-submit', function (e) {
            e.preventDefault();
            $form.trigger("validate");
        })

        $form.on('valid.form', function (e, form) {
            $('.btn-submit').attr("disabled", true);
            initLoading({type: "start"});
            e.preventDefault();
            var data = $form.serializeObject();
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/customCardTask",
                dataType: "text",
                data: JSON.stringify(data),
                type: "POST",
                contentType: "application/json",
                headers: {"X-CSRF-TOKEN": '${_csrf.token}'},
                success: function (res) {
                    initLoading({type: "stop"});
                    $('.btn-submit').attr("disabled", false);
                    $.redirect("${springMacroRequestContext.contextPath}/customCardTask/list", function () {

                    });
                    $.notification.success(res);
                },
                error: function (error) {
                    initLoading({type: "stop"});
                    $('.btn-submit').attr("disabled", false);
                    $.notification.error(error.responseText);
                }
            })
        });


        $('.input-daterange').datepicker({
            language: "zh-CN",
            autoclose: true,
            clearBtn: true,
            format: "yyyy-mm-dd"
        });

    });


</script>
[/@content]
