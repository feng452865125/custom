[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="角色维护" subTitle=""]
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">
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
                            <span class="input-group-addon">名称</span>
                            <input name="sysRoleName" class="form-control sysRoleName">
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">禁用状态</span>
                            <select class="form-control sysRoleIsEnable" name="sysRoleIsEnable">
                                <option value="">请选择状态</option>
                                [#list isEnable.keys as isEnable]
                                    <option value="${isEnable.key}">${isEnable.value}</option>
                                [/#list]
                            </select>
                        </div>
                    [/@searchable]
                    <div>
                        [@authorize ifAnyGranted="sysRole:add"]
                            <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/SysRole/add"
                               redirect>新增</a>
                        [/@authorize]
                    </div>
                    <table id="example" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>描述</th>
                            <th>启用</th>
                            <th>创建日期</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tfoot>
                    </table>
                </div>
                <!-- /.series-body -->
            </div>
            <!-- /.series -->
        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->

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
                    $('.form-control.sysRoleName')[0].value = data.columns[0].search.search;
                    $('.form-control.sysRoleIsEnable')[0].value = data.columns[2].search.search;
                },
                'ordering': true,
                'info': true,
                'autoWidth': false,
                'processing': true,
                'serverSide': true,
                'columns': [
                    {data: 'sysRoleName', name: 'sysRoleName'},
                    {data: 'sysRoleRemark', name: 'sysRoleRemark'},
                    {
                        data: 'sysRoleIsEnable', name: 'sysRoleIsEnable', render: function (data, type, full, meta) {
                            return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                        }
                    },
                    {data: 'createDate', name: 'createDate'},
                    {
                        data: 'id', orderable: false, render: function (data, type, full, meta) {
                            var operations = [];
                            [@authorize ifAnyGranted="sysRole:view"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/SysRole/view/' + data + '">详情</a>');
                            [/@authorize]
                            [@authorize ifAnyGranted="sysRole:edit"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/SysRole/edit/' + data + '">编辑</a>');
                            [/@authorize]
                            [@authorize ifAnyGranted="sysRole:delete"]
                            operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                            [/@authorize]
                            return operations.join(' | ');
                        }
                    }
                ],
                'ajax': {
                    'contentType': 'application/json',
                    'url': '${springMacroRequestContext.contextPath}/SysRole/pagination',
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
                var url = '${springMacroRequestContext.contextPath}/SysRole/' + id;
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
                    var urlType
                    if (currentSwitchTarget.attr('class') === 'enabled') {
                        urlType = 'enable';
                    } else {
                        urlType = 'lock';
                    }
                    $.ajax({
                        url: "${springMacroRequestContext.contextPath}/SysRole/" + urlType + "/" + id,
                        type: 'PATCH',
                        data: {
                            ${_csrf.parameterName}:
                                "${_csrf.token}"
                        },
                        success: function () {
                            debugger
                            switchable = true;
                            currentSwitchTarget.bootstrapSwitch('toggleState');
                        },
                        error: function (error) {
                            debugger
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
                        if ($this.attr('class') === 'enabled') {
                            message = state ? '是否启用' : '是否禁用';
                        } else {
                            message = state ? '是否锁住' : '是否解锁';
                        }
                        currentState = state;
                        currentSwitchTarget = $this;
                        $.dialog(message, confirmOperation, function () {
                        });
                        return false
                    }
                });

            });


        });
    </script>
[/@content]
