[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]
[#include "../../component/modal.ftl"]

[@content title="黑名单维护" subTitle=""]
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet"
          href="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/css/bootstrap3/bootstrap-switch.css"
          type="text/css"/>
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">

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
                            <span class="input-group-addon">证书号</span>
                            <input name="blackZsh" class="form-control" placeholder="请输入证书号查询">
                        </div>
                    [/@searchable]
                    <div>
                        [@authorize ifAnyGranted="customBlack:add"]
                            <a class="btn btn-flat bg-olive btn-black-template-download"
                               href="../../download/importBlack.xls" download="">批量导入模板下载</a>
                            <a class="btn btn-flat bg-olive btn-black-import">导入</a>
                            <a class="btn btn-flat bg-olive"
                               href="${springMacroRequestContext.contextPath}/customBlack/add"
                               redirect>新增</a>
                        [/@authorize]
                        [@authorize ifAnyGranted="customBlack:list"]
                            <a class="btn btn-flat bg-olive btn-black-export" href="javascript:void(0);"
                               download="" style="margin: 0 20px">导出</a>
                        [/@authorize]
                    </div>
                    <table id="example" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>id</th>
                            <th>证书号</th>
                            <th>数据来源</th>
                            <th>创建者</th>
                            <th>状态</th>
                            <th>原因/备注</th>
                            <th>创建时间</th>
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

    [@modal id="modal-black-import" title="批量导入黑名单（证书号）" confirmBtnClass="btn-black-import-sure"]
        <div>
            <div class="form-group">
                <span class="input-group-addon" style="text-align: left;">1、excel文件，请先下载模板</span>
            </div>
            <div class="form-group">
                <span class="input-group-addon" style="text-align: left;">2、第一列GIA证书号（必填）</span>
            </div>
            <div class="form-group">
                <span class="input-group-addon">文件选择</span>
                <input type="file" class="form-control" id="modalBlackImportFile" name="modalBlackImportFile"
                       accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
            </div>
            <div class="input-group modal-change-tip" style="color: #dd4b39;"></div>
        </div>
    [/@modal]



    <!-- DataTables -->
    <script src="${springMacroRequestContext.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.js"></script>
    <script src="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/js/bootstrap-switch.js"></script>
    <script src="${springMacroRequestContext.contextPath}/plugins/loading/loading.js"></script>
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/loading/loading.css">

    <script>
        $(function () {
            var $form = $('#example');
            var dataTable = $form.DataTable({
                'ordering': true,
                'order' : [6,'desc'],
                'info': true,
                'autoWidth': false,
                'processing': true,
                'serverSide': true,
                'columns': [
                    {data: 'id', name: 'id', orderable: false, visible: false},
                    {data: 'blackZsh', name: 'blackZsh'},
                    {
                        data: 'blackType', name: 'blackType', render: function (data, type, full, meta) {
                            return data == 1 ? "批量文件导入" : "手动添加";
                        }
                    },
                    {data: 'blackCreateUser', name: 'blackCreateUser'},
                    {
                        data: 'blackEnable', name: 'blackEnable', render: function (data, type, full, meta) {
                            return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                        }
                    },
                    {data: 'blackReason', name: 'blackReason'},
                    {data: 'createDate', name: 'createDate'},
                    {
                        data: 'id', orderable: false, render: function (data, type, full, meta) {
                            var operations = [];
                            [@authorize ifAnyGranted="customBlack:edit"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/customBlack/edit/' + data + '">编辑</a>');
                            [/@authorize]
                            [@authorize ifAnyGranted="customBlack:delete"]
                            operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                            [/@authorize]
                            return operations.join(' | ');
                        }
                    }
                ],
                'ajax': {
                    'contentType': 'application/json',
                    'url': '${springMacroRequestContext.contextPath}/customBlack/pagination',
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
                var url = "${springMacroRequestContext.contextPath}/customBlack/" + id;
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
                    var cb = function () {
                        switchable = true;
                        currentSwitchTarget.bootstrapSwitch('toggleState');
                    };
                    if (currentSwitchTarget.attr('class') === 'locked') {
                        extUrl = currentState ? 'lock' : 'unlocked';
                    } else {
                        extUrl = currentState ? 'enabled' : 'disabled';
                    }
                    $.ajax({
                        url: "${springMacroRequestContext.contextPath}/customBlack/" + id + "/" + extUrl,
                        type: 'PATCH',
                        data: {
                            ${_csrf.parameterName}:
                                "${_csrf.token}"
                        },
                        success: cb,
                        error: function (error) {
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
                        $this = $(this);
                        id = $this.data('id');
                        var message = state ? '是否启用' : '是否禁用';
                        currentState = state;
                        currentSwitchTarget = $this;
                        $.dialog(message, confirmOperation, function () {
                        });
                        return false
                    }
                });

            });


            // 批量上传的弹窗，展示
            $('.btn-black-import').click(function () {
                $('.modal-change-tip')[0].textContent = '';
                $("#modal-black-import").modal("show");
            })

            // 批量上传的弹窗，提交上传，上架下架导入
            var $modalPartsEnableImport = $('#modal-black-import');
            $modalPartsEnableImport.on('click', '.btn-black-import-sure', function (e) {
                e.preventDefault();
                var $modalBlackImportFile = $('#modalBlackImportFile');
                if ($modalBlackImportFile == null || $modalBlackImportFile.val().length == 0) {
                    $('.modal-change-tip')[0].textContent = '请选择文件';
                    return;
                }
                $('.btn-black-import-sure').attr("disabled", true);
                initLoading({type: "start"});
                var formData = new FormData();//初始化一个FormData对象
                formData.append("file", $modalBlackImportFile[0].files[0]);//将文件塞入FormData
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/customBlack/import",
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
                        initLoading({type: "stop"});
                        $('.btn-black-import-sure').attr("disabled", false);
                        $modalPartsEnableImport.modal("hide");
                        dataTable.draw();
                        $.notification.success(data);
                    },
                    error: function (data) {
                        initLoading({type: "stop"});
                        $('.btn-black-import-sure').attr("disabled", false);
                        $.notification.error(data.responseText);
                    }
                })
            })


            //导出
            $('.btn-black-export').click(function () {
                var href = "${springMacroRequestContext.contextPath}/customBlack/export";
                window.location.href = href;
                return null;
            })


        });


        function sleep(delay) {
            //delay表示的毫秒数
            var start = (new Date()).getTime();
            while ((new Date()).getTime() - start < delay) {
                continue;
            }
        }

    </script>
[/@content]
