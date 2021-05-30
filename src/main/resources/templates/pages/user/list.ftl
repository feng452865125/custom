[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]
[#include "../../component/modal.ftl"]

[@content title="店铺维护" subTitle=""]
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
                        <span class="input-group-addon">店铺编码</span>
                        <input name="username" class="form-control username" placeholder="请输入编码查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">店铺名称</span>
                        <input name="name" class="form-control name" placeholder="请输入名称查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">营业状态</span>
                        <select class="form-control status" name="status">
                            <option value="">请选择状态</option>
                             [#list statusList.keys as status]
                                <option value="${status.key}">${status.label}</option>
                             [/#list]
                        </select>
                    </div>
                [/@searchable]
                <div>
                    [@authorize ifAnyGranted="user:add"]
                        <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/user/add"
                           redirect>新增</a>
                    [/@authorize]
                    [@authorize ifAnyGranted="user:edit"]
                        <a class="btn btn-flat bg-olive all-password" href="javascript:;">批量修改密码</a>
                    [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>店铺编码</th>
                        <th>店铺名称</th>
                        <th>地址</th>
                        <th>pos终端号</th>
                        <th>门店状态</th>
                        <th>是否启用</th>
                        <th>更新日期</th>
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
    [@modal id="modal-all-password" title="批量修改店铺密码" confirmBtnClass="all-password-sure"]
    <div>
        <div class="form-group">
            <span class="input-group-addon">密码</span>
            <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码"
                   data-rule="required;length(0~32)"
            >
        </div>
        <div class="form-group">
            <span class="input-group-addon">确认密码</span>
            <input type="password" class="form-control" id="rePassword" name="rePassword" placeholder="请输入确认密码"
                   data-rule="required;length(0~32);match(password)"
                   data-msg-match="确认密码不一致"
            >
        </div>
        <div class="input-group change-tip" style="color: #dd4b39;"></div>
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
                $('.form-control.username')[0].value = data.columns[0].search.search;
                $('.form-control.name')[0].value = data.columns[1].search.search;
                $('.form-control.status')[0].value = data.columns[8].search.search;
            },
            'ordering': true,
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'username', name: 'username'},
                {data: 'name', name: 'name'},
                {data: 'address', name: 'address'},
                {data: 'pos', name: 'pos'},
                {data: 'statusName', name: 'statusName', orderable: false},
                {
                    data: 'enabled', name: 'enabled', render: function (data, type, full, meta) {
                        return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                    }
                },
                {data: 'updateDate', name: 'updateDate'},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [@authorize ifAnyGranted="user:view"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/user/view/' + data + '">详情</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="user:edit"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/user/edit/' + data + '">编辑</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="user:delete"]
                        operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                    [/@authorize]
                        return operations.join(' | ');
                    }
                },
                {data: 'status', name: 'status', visible: false}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/user/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/user/" + id;
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
                    url: "${springMacroRequestContext.contextPath}/user/" + id + "/" + extUrl,
                    type: 'PATCH',
                    data: {${_csrf.parameterName}:
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
                        message = state ? '是否启用' : '是否取消启用';
                    }
                    currentState = state;
                    currentSwitchTarget = $this;
                    $.dialog(message, confirmOperation, function () {
                    });
                    return false
                }
            });

        });

        // 批量修改密码的弹窗，展示
        $('.all-password').click(function () {
            $('.change-tip')[0].textContent = '';
            $("#modal-all-password").modal("show");
        })

        // 批量修改密码的弹窗，修改功能
        var $modalPassword = $('#modal-all-password');
        $modalPassword.on('click', '.all-password-sure', function (e) {
            e.preventDefault();
            var data = $modalPassword.serializeObject();
            for (var item in data) {
                if (data[item] === "" || "undefined" === typeof data[item]) delete data[item];
            }
            var password = $('#password').val();
            var rePassword = $('#rePassword').val();
            if (password.length == 0 || rePassword.length == 0) {
                $('.change-tip')[0].textContent = '密码不能为空';
                return;
            }
            if (password != rePassword) {
                $('.change-tip')[0].textContent = '两个密码不一致';
                return;
            }
            data.password = password;
            data.rePassword = rePassword;
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/user/changeAllPwd",
                data: JSON.stringify(data),
                type: "POST",
                contentType: "application/json",
                headers: {
                    "X-CSRF-TOKEN": "${_csrf.token}",
                }
            }).done(function (data) {
                $modalPassword.modal("hide");
                dataTable.draw();
                $.notification.success(data);
            }).fail(function (error) {
                $.notification.error(error.responseText);
            })
        })
    });
</script>
[/@content]
