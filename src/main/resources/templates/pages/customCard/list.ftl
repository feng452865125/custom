[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]
[#include "../../component/modal.ftl"]

[@content title="卡片维护" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">
<link rel="stylesheet"
      href="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/css/bootstrap3/bootstrap-switch.css"
      type="text/css"/>

<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">列表</h3>
            </div>
            <!-- /.series-header -->
            <div class="box-body">
                [@searchable id="searchable" targetId="example"]
                    <div class="input-group">
                        <span class="input-group-addon">卡号</span>
                        <input name="cardCode" class="form-control cardCode" placeholder="请输入卡号查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">建卡批次（任务名）</span>
                        <input name="cardTaskName" class="form-control cardTaskName" placeholder="请输入批次查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">合作方</span>
                        <input name="cardCompany" class="form-control cardCompany" placeholder="请输入合作方查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">卡片16位号段</span>
                        <input name="cardCodeStart" class="form-control cardCodeStart" placeholder="请输入16位号段查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">-</span>
                        <input name="cardCodeEnd" class="form-control cardCodeEnd" placeholder="请输入16位号段查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">状态</span>
                        <select class="form-control cardStatus" name="cardStatus">
                            <option value="">全部</option>
                            [#list statusList.keys as status]
                                <option value="${status.key}">${status.label}</option>
                            [/#list]
                        </select>
                    </div>
                [/@searchable]
                <div>
                    [@authorize ifAnyGranted="customCard:edit"]
                        <a class="btn btn-flat bg-olive"
                           href="../../download/importCard.xls" download="">卡片模板下载</a>
                        <a class="btn btn-flat bg-olive btn-customCard-import">导入</a>
                        <a class="btn btn-flat bg-olive btn-customCard-batch-action">批量激活/冻结/解冻</a>
                    [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>卡号</th>
                        <th>合作方</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>卡片面额</th>
                        <th>解冻/冻结</th>
                        <th>卡片状态</th>
                        <th>使用平台</th>
                        <th>平台订单号</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <!-- /.series-body -->
        </div>
        <!-- /.series -->
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->

    [@modal id="modal-customCard-import" title="导入卡片" confirmBtnClass="btn-customCard-import-sure"]
    <div>
        <div style="margin-bottom: 15px;">
            <div style="text-align: left;color: red;">操作说明：</div>
            <div style="text-align: left;color: red; margin: 5px 0;">步骤一、选择卡片状态</div>
            <div style="text-align: left;color: red; margin: 5px 0;">步骤二、选择上传文件（卡片）</div>
            <div style="text-align: left;color: red; margin: 5px 0;">步骤三、点击[确认]按钮</div>
        </div>
        <div class="form-group">
            <span class="input-group-addon">卡片状态</span>
            <select class="select-customCard-status">
                <option value="-1">请选择卡片状态</option>
                <option value="0">未激活</option>
                <option value="1">已激活</option>
                <option value="2">已使用</option>
                <option value="3">已过期</option>
            </select>
        </div>
        <div class="form-group">
            <span class="input-group-addon">文件选择</span>
            <input type="file" class="form-control" id="modalCustomCardFile" name="modalCustomCardFile"
                   accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
        </div>
        <div class="input-group modal-customCard-import-tip" style="color: #dd4b39;"></div>
    </div>
    [/@modal]

    [@modal id="modal-customCard-batch-action" title="批量操作" confirmBtnClass="btn-customCard-batch-action-sure"]
    <div>
        <div style="display: flex">
            <div style="width: 50%;">
                <button style="width: 100%; background: #9C9C9C; font-size: large;" class="btn-choose-hand" onclick="">
                    手填卡号模式
                </button>
            </div>
            <div style="width: 50%;">
                <button style="width: 100%; background: #E8E8E8; font-size: large;" class="btn-choose-file">导入文件模式
                </button>
            </div>
        </div>
        <div style="margin: 15px 0;" class="btn-choose-hand-tip">
            <div style="text-align: left;color: red;">操作说明：</div>
            <div style="text-align: left;color: red; margin: 5px 0;">步骤一、选择批量操作类型</div>
            <div style="text-align: left;color: red; margin: 5px 0;" class="hand-tip">步骤二、填写卡号区间</div>
            <div style="text-align: left;color: red; margin: 5px 0; display: none" class="file-tip">步骤二、选择上传文件（第一列为卡号）
            </div>
            <div style="text-align: left;color: red; margin: 5px 0;">步骤三、点击[确认]按钮</div>
        </div>
        <div class="form-group">
            <span class="input-group-addon">批量操作类型</span>
            <select class="select-customCard-batch-action-type">
                <option value="-1">请选择操作类型</option>
                <option value="0">激活</option>
                <option value="1">冻结</option>
                <option value="2">解冻</option>
            </select>
        </div>
        <div class="hand-input">
            <div class="form-group">
                <span class="input-group-addon">卡片16位号段--区间头</span>
                <input class="form-control modalCardCodeStart" id="modalCardCodeStart" name="modalCardCodeStart"">
            </div>
            <div class="form-group">
                <span class="input-group-addon">卡片16位号段--区间尾</span>
                <input class="form-control modalCardCodeEnd" id="modalCardCodeEnd" name="modalCardCodeEnd"">
            </div>
        </div>
        <div style="display: none" class="file-input">
            <div class="form-group">
                <span class="input-group-addon">文件选择</span>
                <input type="file" class="form-control" id="modalCustomCardBatchActionFile"
                       name="modalCustomCardBatchActionFile"
                       accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
            </div>
        </div>
        <div class="input-group modal-customCard-batch-action-tip" style="color: #dd4b39;"></div>
    </div>
    [/@modal]


    [@modal id="modal-customCard-activate" title="卡片-手动激活" confirmBtnClass="btn-customCard-activate-sure"]
    <div>
        <div class="form-group">
            <span class="input-group-addon">激活人信息</span>
            <input class="form-control modalActivateUserName" id="modalActivateUserName" name="modalActivateUserName"">
        </div>
    [#--<div class="form-group">--]
    [#--<span class="input-group-addon">卡片8位密码</span>--]
    [#--<input class="form-control modalActivateCardPassword" id="modalActivateCardPassword" name="modalActivateCardPassword"">--]
    [#--</div>--]
        <div class="input-group modal-customCard-activate-tip" style="color: #dd4b39;"></div>
    </div>
    [/@modal]

    [@modal id="modal-customCard-use" title="卡片-手动核销" confirmBtnClass="btn-customCard-use-sure"]
    <div>
        <div class="form-group">
            <span class="input-group-addon">使用平台（必填）</span>
            <input class="form-control modalUsePlatform" id="modalUsePlatform" name="modalUsePlatform"
                   placeholder="天猫、京东、微商城等等">
        </div>
        <div class="form-group">
            <span class="input-group-addon">平台订单号（必填）</span>
            <input class="form-control modalUsePlatformOrder" id="modalUsePlatformOrder" name="modalUsePlatformOrder"">
        </div>
        <div class="form-group">
            <span class="input-group-addon">门店信息</span>
            <input class="form-control modalUseStoreName" id="modalUseStoreName" name="modalUseStoreName"">
        </div>
        <div class="form-group">
            <span class="input-group-addon">使用人信息</span>
            <input class="form-control modalUseUserName" id="modalUseUserName" name="modalUseUserName"">
        </div>
    [#--<div class="form-group">--]
    [#--<span class="input-group-addon">使用时间</span>--]
    [#--<input class="form-control modalUseDate" id="modalUseDate" name="modalUseDate" placeholder="不填写默认当前时间">--]
    [#--</div>--]
    [#--<div class="form-group">--]
    [#--<span class="input-group-addon">其他备注</span>--]
    [#--<input class="form-control modalUseRemark" id="modalUseRemark" name="modalUseRemark"">--]
    [#--</div>--]
        <div class="input-group modal-customCard-use-tip" style="color: #dd4b39;"></div>
    </div>
    [/@modal]

    [@modal id="modal-batch-action-result" title="批量操作结果" confirmBtnClass="btn-batch-action-result-sure" confirmBtnLabel = "导出异常卡号"]
    <div class="form-group">
        <span class="input-group-addon">以下卡号，数据异常（数据库中不存在或已失效）</span>
        <textarea class="form-control modalBatchActionResultTextarea" id="modalBatchActionResultTextarea"
                  name="modalBatchActionResultTextarea"></textarea>
    </div>
    [/@modal]

<!-- DataTables -->
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/js/bootstrap-switch.js"></script>
<script>
    $(function () {
        var $form = $('#example');
        var dataTable = $form.DataTable({
            'stateSave': true,
            'stateSaveParams': function (settings, data) {
                data.search.search = "";
                $('.form-control.cardCode')[0].value = data.columns[1].search.search;
                $('.form-control.cardCompany')[0].value = data.columns[2].search.search;
                $('.form-control.cardCodeStart')[0].value = data.columns[11].search.search;
                $('.form-control.cardCodeEnd')[0].value = data.columns[12].search.search;
                $('.form-control.cardStatus')[0].value = data.columns[13].search.search;
                $('.form-control.cardTaskName')[0].value = data.columns[14].search.search;
            },
            'ordering': true,
            'order': [[4, 'desc'], [0, 'asc']],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false, orderable: false},
                {data: 'cardCode', name: 'cardCode', orderable: false},
                {data: 'cardCompany', name: 'cardCompany', orderable: false},
                {data: 'cardCreateUser', name: 'cardCreateUser', orderable: false},
                {data: 'cardCreateDate', name: 'cardCreateDate', orderable: false},
                {data: 'cardPrice', name: 'cardPrice', orderable: false},
                {
                    data: 'cardEnable',
                    name: 'cardEnable',
                    orderable: false,
                    render: function (data, type, full, meta) {
                        return data ?
                        '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                        '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                    }
                },
                {data: 'cardStatusName', name: 'cardStatusName', orderable: false},
                {data: 'cardPlatform', name: 'cardPlatform', orderable: false},
                {data: 'cardPlatformOrder', name: 'cardPlatformOrder', orderable: false},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                    var operations = [];
                    [@authorize ifAnyGranted="customCard:view"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/customCard/view/' + data + '">查看</a>');
                    [/@authorize]
                [#--[@authorize ifAnyGranted="customCard:edit"]--]
                [#--operations.push('<a redirect href="${springMacroRequestContext.contextPath}/customCard/edit/' + data + '">编辑</a>');--]
                [#--[/@authorize]--]
                [#--[@authorize ifAnyGranted="customCard:delete"]--]
                [#--operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')--]
                [#--[/@authorize]--]
                    [@authorize ifAnyGranted="customCard:edit"]
                        if (full.cardStatus == 0) {
                            operations.push('<a class="btn-cardActivate" href="javascript:;" data-id="' + data + '">手动激活</a>');
                            operations.push('<a style="color: grey; cursor:not-allowed;">手动核销</a>');
                        }
                    [/@authorize]
                    [@authorize ifAnyGranted="customCard:edit"]
                        if (full.cardStatus == 1) {
                            operations.push('<a style="color: grey; cursor:not-allowed;">手动激活</a>');
                            operations.push('<a class="btn-cardUse" href="javascript:;" data-id="' + data + '">手动核销</a>');
                        }
                    [/@authorize]
                    return operations.join(' | ');
                }
                },
                {data: 'cardCodeStart', name: 'cardCodeStart', visible: false},
                {data: 'cardCodeEnd', name: 'cardCodeEnd', visible: false},
                {data: 'cardStatus', name: 'cardStatus', visible: false},
                {data: 'cardTaskName', name: 'cardTaskName', visible: false}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/customCard/pagination',
                'type': 'POST',
                'beforeSend': function (request) {
                    request.setRequestHeader('X-CSRF-TOKEN', '${_csrf.token}');
                    return request;
                },
                'data': function (d) {
                    return JSON.stringify(d);
                },
            }
        });

        $form.on('click', '.btn-delete', function () {
            var $this = $(this);
            var id = $this.data('id');
            var url = "${springMacroRequestContext.contextPath}/customCard/" + id;
            $.showRemoveDialog(url, '${_csrf.token}', function () {
                dataTable.draw();
            })
        })

        dataTable.on('draw.dt', function () {
            var currentState;
            var currentSwitchTarget;
            var $switch = $("input.locked, input.enabled");
            var switchable = false;
            var confirmOperation = function () {
                var extUrl;
                if (currentSwitchTarget.attr('class') === 'locked') {
                    extUrl = currentState ? 'lock' : 'unlocked';
                } else {
                    extUrl = currentState ? 'enabled' : 'disabled';
                }
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/customCard/" + id + "/" + extUrl,
                    type: 'PATCH',
                    data: {${_csrf.parameterName}
                :
                "${_csrf.token}"
            },
                success: function () {
                    switchable = true;
                    currentSwitchTarget.bootstrapSwitch('toggleState');
                }
                ,
                error:function (error) {
                    $.notification.error(error.responseText);
                }
            })
            }

            $switch.bootstrapSwitch({
                onSwitchChange: function (e, state) {
                    if (switchable) {
                        switchable = false;
                        return true;
                    }
                    ;
                    var message;
                    $this = $(this);
                    id = $this.data('id');
                    if ($this.attr('class') === 'locked') {
                        message = state ? '是否锁定' : '是否取消锁定';
                    } else {
                        message = state ? '是否解冻' : '是否冻结';
                    }
                    currentState = state;
                    currentSwitchTarget = $this;
                    $.dialog(message, confirmOperation, function () {
                    });
                    return false
                }
            });

        });


        // 导入弹窗
        $('.btn-customCard-import').click(function () {
            $(".select-customCard-status")[0].selectedIndex = 0;
            $('.modal-customCard-import-tip')[0].textContent = '';
            $("#modal-customCard-import").modal("show");
        })

        // 导入弹窗
        var $modalCustomCardImport = $('#modal-customCard-import');
        $modalCustomCardImport.on('click', '.btn-customCard-import-sure', function (e) {
            e.preventDefault();
            var cardStatus = $('.select-customCard-status')[0].value;
            if (cardStatus == null || cardStatus == '' || parseInt(cardStatus) < 0) {
                $('.modal-customCard-import-tip')[0].textContent = '请选择卡片状态';
                return;
            }
            var $modalCustomCardFile = $('#modalCustomCardFile');
            if ($modalCustomCardFile == null || $modalCustomCardFile.val().length == 0) {
                $('.modal-customCard-import-tip')[0].textContent = '请选择文件';
                return;
            }
            var formData = new FormData();//初始化一个FormData对象
            formData.append("file", $modalCustomCardFile[0].files[0]);//将文件塞入FormData
            formData.append("cardStatus", parseInt(cardStatus));//卡片状态
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/customCard/import",
                data: formData,
                async: false,
                dataType: "text",
                cache: false,
                processData: false,  // 告诉jQuery不要去处理发送的数据
                contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
                type: "POST",
                headers: {
                    "X-CSRF-TOKEN": "${_csrf.token}",
                },
                success: function (data) {
                    $modalCustomCardImport.modal("hide");
                    dataTable.draw();
                    $.notification.success(data);
                },
                error: function (data) {
                    $.notification.error(data.responseText);
                }
            })
        })

        // 批量操作模式选---手填卡号区间
        $('.btn-choose-hand').click(function () {
            $('.btn-choose-file')[0].style.background = "#E8E8E8"
            $('.btn-choose-hand')[0].style.background = "#9C9C9C"
            $('.file-tip')[0].style.display = "none";
            $('.hand-tip')[0].style.display = "block";
            $('.file-input')[0].style.display = "none";
            $('.hand-input')[0].style.display = "block";

        })
        // 批量操作模式选---导入文件
        $('.btn-choose-file').click(function () {
            $('.btn-choose-hand')[0].style.background = "#E8E8E8"
            $('.btn-choose-file')[0].style.background = "#9C9C9C"
            $('.hand-tip')[0].style.display = "none";
            $('.file-tip')[0].style.display = "block";
            $('.hand-input')[0].style.display = "none";
            $('.file-input')[0].style.display = "block";
        })


        // 批量操作弹窗（激活、冻结、解冻）
        $('.btn-customCard-batch-action').click(function () {
            $(".select-customCard-batch-action-type")[0].selectedIndex = 0;
            $('.modal-customCard-batch-action-tip')[0].textContent = '';
            $("#modal-customCard-batch-action").modal("show");
        })


        // 批量操作弹窗（激活、冻结、解冻）
        var $modalCustomCardBatchAction = $('#modal-customCard-batch-action');
        $modalCustomCardBatchAction.on('click', '.btn-customCard-batch-action-sure', function (e) {
            e.preventDefault();
            var actionType = $('.select-customCard-batch-action-type')[0].value;
            if (actionType == null || actionType == '' || parseInt(actionType) < 0) {
                $('.modal-customCard-batch-action-tip')[0].textContent = '请选择操作类型';
                return;
            }
            if ($('.file-input')[0].style.display == "block") {
                //导入文件模式
                var $modalCustomCardBatchActionFile = $('#modalCustomCardBatchActionFile');
                if ($modalCustomCardBatchActionFile == null || $modalCustomCardBatchActionFile.val().length == 0) {
                    $('.modal-customCard-batch-action-tip')[0].textContent = '请选择文件';
                    return;
                }
                var formData = new FormData();//初始化一个FormData对象
                formData.append("file", $modalCustomCardBatchActionFile[0].files[0]);//将文件塞入FormData
                formData.append("actionType", parseInt(actionType));//卡片状态
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/customCard/batchActionByFile",
                    data: formData,
                    async: false,
                    dataType: "text",
                    cache: false,
                    processData: false,  // 告诉jQuery不要去处理发送的数据
                    contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
                    type: "POST",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    },
                    success: function (res) {
                        $modalCustomCardBatchAction.modal("hide");
                        dataTable.draw();
                        $.notification.success(res);
                    },
                    error: function (error) {
                        $modalCustomCardBatchAction.modal("hide");
                        dataTable.draw();
                        if (error.responseText.match("##")) {
                            $("#modal-batch-action-result").modal("show");
                            $('.modalBatchActionResultTextarea')[0].value = error.responseText.split("##")[1];
                            $.notification.error(error.responseText.split("##")[0] + "，异常卡号详见页面弹窗");
                        } else {
                            $.notification.error(error.responseText);
                        }

                    }
                })
            } else {
                //手填卡号区间模式
                var modalCardCodeStart = $('.modalCardCodeStart')[0].value;
                var modalCardCodeEnd = $('.modalCardCodeEnd')[0].value;
                if (modalCardCodeStart.length < 16 || modalCardCodeEnd.length < 16) {
                    $('.modal-customCard-batch-action-tip')[0].textContent = '卡片号段位数不足16位';
                    return;
                }
                if (modalCardCodeStart.substring(1, 6) != modalCardCodeEnd.substring(1, 6)) {
                    $('.modal-customCard-batch-action-tip')[0].textContent = '卡号区间不一致';
                    return;
                }
                var data = $form.serializeObject();
                data['actionType'] = parseInt(actionType);
                data['cardCodeStart'] = modalCardCodeStart;
                data['cardCodeEnd'] = modalCardCodeEnd;
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/customCard/batchActionByHand",
                    dataType: "text",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {"X-CSRF-TOKEN": '${_csrf.token}'},
                    success: function (res) {
                        $modalCustomCardBatchAction.modal("hide");
                        dataTable.draw();
                        $.notification.success(res);
                    },
                    error: function (error) {
                        $.notification.error(error.responseText);
                    }
                })
            }

        });


        //导出错误的卡号
        var $modalBatchActionResult = $('#modal-batch-action-result');
        $modalBatchActionResult.on('click', '.btn-batch-action-result-sure', function () {
            var cardCodeArr = $('.modalBatchActionResultTextarea')[0].value
            var href = "${springMacroRequestContext.contextPath}/customCard/exportErrorCardCode/" + cardCodeArr;
            window.location.href = href;
            return null;
        })


        //激活弹窗
        $form.on('click', '.btn-cardActivate', function (e) {
            // $form.activateCardId = $(this).data('id');
            // $('.modalActivateUserName')[0].textContent = '';
            // $('.modalActivateCardPassword')[0].textContent = '';
            // $('.modal-customCard-activate-tip')[0].textContent = '';
            // $("#modal-customCard-activate").modal("show");
            e.preventDefault();
            var data = $form.serializeObject();
            data['id'] = $(this).data('id');
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/customCard/activate",
                dataType: "text",
                data: JSON.stringify(data),
                type: "POST",
                contentType: "application/json",
                headers: {"X-CSRF-TOKEN": '${_csrf.token}'},
                success: function (res) {
                    $modalCustomCardActivate.modal("hide");
                    dataTable.draw(false);
                    $.notification.success(res);
                },
                error: function (error) {
                    $.notification.error(error.responseText);
                }
            })

        })

        //激活弹窗
        var $modalCustomCardActivate = $('#modal-customCard-activate');
        $modalCustomCardActivate.on('click', '.btn-customCard-activate-sure', function (e) {
            e.preventDefault();
            var userName = $('.modalActivateUserName')[0].value;
            var cardPassword = $('.modalActivateCardPassword')[0].value;
            if (userName == null || userName == '') {
                $('.modal-customCard-activate-tip')[0].textContent = '请填写激活用户的信息';
                return;
            }
            if (cardPassword == null || cardPassword == '' || cardPassword.length != 8) {
                $('.modal-customCard-activate-tip')[0].textContent = '请填写8位卡片密码';
                return;
            }
            var data = $form.serializeObject();
            data['id'] = $form.activateCardId;
            data['userName'] = userName;
            data['cardPassword'] = cardPassword;
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/customCard/activate",
                dataType: "text",
                data: JSON.stringify(data),
                type: "POST",
                contentType: "application/json",
                headers: {"X-CSRF-TOKEN": '${_csrf.token}'},
                success: function (res) {
                    $modalCustomCardActivate.modal("hide");
                    dataTable.draw();
                    $.notification.success(res);
                },
                error: function (error) {
                    $.notification.error(error.responseText);
                }
            })
        });

        //核销弹窗
        $form.on('click', '.btn-cardUse', function () {
            $form.useCardId = $(this).data('id');
            $('.modalUseStoreName')[0].textContent = '';
            $('.modal-customCard-use-tip')[0].textContent = '';
            $("#modal-customCard-use").modal("show");
        })

        //核销弹窗
        var $modalCustomCardUse = $('#modal-customCard-use');
        $modalCustomCardUse.on('click', '.btn-customCard-use-sure', function (e) {
            e.preventDefault();
            var cardPlatform = $('.modalUsePlatform')[0].value;
            var cardPlatformOrder = $('.modalUsePlatformOrder')[0].value;
            var storeName = $('.modalUseStoreName')[0].value;
            var cardUser = $('.modalUseUserName')[0].value;
//            var cardUseDate = $('.modalUseDate')[0].value;
//            var cardRemark = $('.modalUseRemark')[0].value;
            if (cardPlatform == null || cardPlatform == '') {
                $('.modal-customCard-use-tip')[0].textContent = '请填写使用平台信息';
                return;
            }
            if (cardPlatformOrder == null || cardPlatformOrder == '') {
                $('.modal-customCard-use-tip')[0].textContent = '请填写平台订单号';
                return;
            }
            var data = $form.serializeObject();
            data['id'] = $form.useCardId;
            data['cardPlatform'] = cardPlatform;
            data['cardPlatformOrder'] = cardPlatformOrder;
            data['storeName'] = storeName;
            data['cardUser'] = cardUser;
//            data['cardUseDate'] = cardUseDate;
//            data['cardRemark'] = cardRemark;
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/customCard/use",
                dataType: "text",
                data: JSON.stringify(data),
                type: "POST",
                contentType: "application/json",
                headers: {"X-CSRF-TOKEN": '${_csrf.token}'},
                success: function (res) {
                    $modalCustomCardUse.modal("hide");
                    dataTable.draw(false);
                    $.notification.success(res);
                },
                error: function (error) {
                    $.notification.error(error.responseText);
                }
            })
        });


    });
</script>
[/@content]
