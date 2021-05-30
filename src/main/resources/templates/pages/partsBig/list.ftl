 [#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]
[#include "../../component/modal.ftl"]

[@content title="大克拉钻石" subTitle=""]
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
                        <span class="input-group-addon">款号</span>
                        <input name="zsKh" class="form-control" placeholder="请输入款号查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">公司代码</span>
                        <select class="form-control company" name="company">
                            <option value="">请选择公司代码</option>
                            [#list companyList as partsBig]
                                <option value="${partsBig.company}">${partsBig.company}</option>
                            [/#list]
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">状态</span>
                        <select class="form-control lockStatus" name="lockStatus">
                            <option value="">请选择状态</option>
                            <option value="1">在库</option>
                            <option value="3">锁库</option>
                        </select>
                    </div>
                [/@searchable]
                <div>
                    [@authorize ifAnyGranted="partsBig:add"]
                        <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/partsBig/add"
                           redirect>新增</a>
                        <a class="btn btn-flat bg-olive btn-partsBig-import">批量导入</a>
                        <a class="btn btn-flat bg-olive btn-partsBig-download"
                           href="../../download/importPartsBig.xls" download="">模板下载</a>
                        <a class="btn btn-flat bg-red btn-partsBig-enable-on">当前全部上架</a>
                        <a class="btn btn-flat bg-red btn-partsBig-enable-off">当前全部下架</a>
                    [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>款号</th>
                        <th>质量</th>
                        <th>GIA证书号</th>
                        <th>颜色</th>
                        <th>净度</th>
                        <th>切工</th>
                        <th>抛光</th>
                        <th>对称</th>
                        <th>荧光</th>
                        <th>销售价（人民币）</th>
                        <th>库存状态</th>
                        <th>是否上架</th>
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

    [@modal id="modal-partsBig-import" title="批量导入大克拉钻石清单" confirmBtnClass="btn-partsBig-import-sure"]
    <div>
        <div class="form-group">
            <span class="input-group-addon" style="text-align: left;">1、请根据模板上传导入文件</span>
        </div>
        <div class="form-group">
            <span class="input-group-addon" style="text-align: left;">2、请保持文件中钻石属性的字段顺序不变</span>
        </div>
        <div class="form-group">
            <span class="input-group-addon">文件选择</span>
            <input type="file" class="form-control" id="modalPartsBigFile" name="modalPartsBigFile"
                   accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
        </div>
        <div class="input-group modal-change-tip" style="color: #dd4b39;"></div>
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
            'ordering': true,
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {data: 'zsKh', name: 'zsKh'},
                {
                    data: 'zsZl', name: 'zsZl', render: function (data, type, full, meta) {
                        return data / 1000;
                    }
                },
                {data: 'zsZsh', name: 'zsZsh'},
                {data: 'zsYs', name: 'zsYs'},
                {data: 'zsJd', name: 'zsJd'},
                {data: 'zsQg', name: 'zsQg'},
                {data: 'zsPg', name: 'zsPg'},
                {data: 'zsDc', name: 'zsDc'},
                {data: 'zsYg', name: 'zsYg'},
                {
                    data: 'zsPrice', name: 'zsPrice', render: function (data, type, full, meta) {
                        return data / 100;
                    }
                },
                {
                    data: 'lockStatus', name: 'lockStatus', render: function (data, type, full, meta) {
                        if (data == 3) {
                            return "锁库";
                        } else {
                            return "在库";
                        }
                    }
                },
                {
                    data: 'enabled', name: 'enabled', render: function (data, type, full, meta) {
                        return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                    }
                },
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [#--[@authorize ifAnyGranted="partsBig:view"]--]
                    [#--operations.push('<a redirect href="${springMacroRequestContext.contextPath}/partsBig/view/' + data + '">详情</a>');--]
                    [#--[/@authorize]--]
                    [@authorize ifAnyGranted="partsBig:edit"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/partsBig/edit/' + data + '">编辑</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="partsBig:delete"]
                        operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                    [/@authorize]
                        return operations.join(' | ');
                    }
                },
                {data: 'company', name: 'company', visible: false}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/partsBig/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/partsBig/" + id;
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
                debugger
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
                    url: "${springMacroRequestContext.contextPath}/partsBig/" + id + "/" + extUrl,
                    type: 'PATCH',
                    data: {${_csrf.parameterName}
            :
                "${_csrf.token}"
            },
                success: cb,
                        error
            :

                function (error) {
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
                        message = state ? '是否展示' : '是否隐藏';
                    }
                    currentState = state;
                    currentSwitchTarget = $this;
                    $.dialog(message, confirmOperation, function () {
                    });
                    return false
                }
            });

        });

        // 批量上传的弹窗，展示
        $('.btn-partsBig-import').click(function () {
            $('.modal-change-tip')[0].textContent = '';
            $("#modal-partsBig-import").modal("show");
        })

        // 批量上传的弹窗，提交上传
        var $modalCompanyImport = $('#modal-partsBig-import');
        $modalCompanyImport.on('click', '.btn-partsBig-import-sure', function (e) {
            e.preventDefault();
            var $modalPartsBigFile = $('#modalPartsBigFile');
            if ($modalPartsBigFile == null || $modalPartsBigFile.val().length == 0) {
                $('.modal-change-tip')[0].textContent = '请选择文件';
                return;
            }
            var formData = new FormData();//初始化一个FormData对象
            formData.append("file", $modalPartsBigFile[0].files[0]);//将文件塞入FormData
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/partsBig/import",
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
                    $modalCompanyImport.modal("hide");
                    dataTable.draw();
                    $.notification.success(data);
                },
                error: function (data) {
                    $.notification.error(data.responseText);
                }
            })
        })


        //全部上架
        $('.btn-partsBig-enable-on').click(function () {
            var data = $form.serializeObject();
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/partsBig/enableOn",
                dataType: "json",
                data: JSON.stringify(data),
                type: "POST",
                contentType: "application/json",
                headers: {
                    "X-CSRF-TOKEN": "${_csrf.token}",
                }
            }).done(function (data) {
                dataTable.draw();
                $.notification.success(data);
            }).fail(function (error) {
                dataTable.draw();
                $.notification.error(error.responseText);
            });
        })


        //全部下架
        $('.btn-partsBig-enable-off').click(function () {
            var data = $form.serializeObject();
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/partsBig/enableOff",
                dataType: "json",
                data: JSON.stringify(data),
                type: "POST",
                contentType: "application/json",
                headers: {
                    "X-CSRF-TOKEN": "${_csrf.token}",
                }
            }).done(function (data) {
                dataTable.draw();
                $.notification.success(data);
            }).fail(function (error) {
                dataTable.draw();
                $.notification.error(error.responseText);
            });
        })

    });
</script>
[/@content]
