[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="账号维护" subTitle=""]
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
                            <span class="input-group-addon">账号/姓名/号码</span>
                            <input name="sysUsername" class="form-control sysUsername">
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">禁用状态</span>
                            <select class="form-control sysUserIsEnable" name="sysUserIsEnable">
                                <option value="">请选择状态</option>
                                [#list isEnable.keys as isEnable]
                                    <option value="${isEnable.key}">${isEnable.value}</option>
                                [/#list]
                            </select>
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">锁定状态</span>
                            <select class="form-control sysUserIsLocked" name="sysUserIsLocked">
                                <option value="">请选择状态</option>
                                [#list isLock.keys as isLock]
                                    <option value="${isLock.key}">${isLock.value}</option>
                                [/#list]
                            </select>
                        </div>
                    [/@searchable]
                    <div>
                        [@authorize ifAnyGranted="sysUser:add"]
                            <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/SysUser/add"
                               redirect>新增</a>
                        [/@authorize]
                    </div>
                    <table id="example" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>登录账号</th>
                            <th>姓名</th>
                            <th>联系号码</th>
                            <th>启用</th>
                            <th>锁住</th>
                            <th>创建日期</th>
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
                    $('.form-control.sysUsername')[0].value = data.columns[0].search.search;
                    $('.form-control.sysUserIsEnable')[0].value = data.columns[3].search.search;
                    $('.form-control.sysUserIsLocked')[0].value = data.columns[4].search.search;
                },
                'ordering': true,
                'info': true,
                'autoWidth': false,
                'processing': true,
                'serverSide': true,
                'columns': [
                    {data: 'sysUserUsername', name: 'sysUserUsername'},
                    {data: 'sysUserName', name: 'sysUserName'},
                    {data: 'sysUserMobile', name: 'sysUserMobile'},
                    {
                        data: 'sysUserIsEnable', name: 'sysUserIsEnable', render: function (data, type, full, meta) {
                            return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                        }
                    },
                    {
                        data: 'sysUserIsLocked', name: 'sysUserIsLocked', render: function (data, type, full, meta) {
                            return data ?
                                '<input type="checkbox" class="locked" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="locked" data-id="' + full.id + '" data-size="mini">'
                        }
                    },
                    {data: 'createDate', name: 'createDate'},
                    {
                        data: 'id', orderable: false, render: function (data, type, full, meta) {
                            var operations = [];
                            [@authorize ifAnyGranted="sysUser:view"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/SysUser/view/' + data + '">详情</a>');
                            [/@authorize]
                            [@authorize ifAnyGranted="sysUser:edit"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/SysUser/edit/' + data + '">编辑</a>');
                            [/@authorize]
                            [@authorize ifAnyGranted="sysUser:delete"]
                            operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                            [/@authorize]
                            return operations.join(' | ');
                        }
                    }
                ],
                'ajax': {
                    'contentType': 'application/json',
                    'url': '${springMacroRequestContext.contextPath}/SysUser/pagination',
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
                var url = '${springMacroRequestContext.contextPath}/SysUser/' + id;
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
                        url: "${springMacroRequestContext.contextPath}/SysUser/" + urlType + "/" + id,
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
