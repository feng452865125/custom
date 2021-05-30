[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="第三方供应商维护" subTitle=""]
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
                        <span class="input-group-addon">供应商名称</span>
                        <input name="name" class="form-control name" placeholder="请输入供应商名称查询">
                    </div>
                 <div class="input-group">
                     <span class="input-group-addon">英文缩写</span>
                     <input name="shortName" class="form-control shortName" placeholder="请输入英文缩写查询">
                 </div>
                <div class="input-group">
                    <span class="input-group-addon">合作状态</span>
                    <select class="form-control status" id="status" name="status">
                        <option value="">请选择合作状态</option>
                                [#list statusList.keys as status]
                                    <option value="${status.key}">${status.label}</option>
                                [/#list]
                    </select>
                </div>
                [/@searchable]
                <div>
                    [@authorize ifAnyGranted="thirdSupplier:add"]
                        <a class="btn btn-flat bg-olive"
                           href="${springMacroRequestContext.contextPath}/thirdSupplier/add"
                           redirect>新增</a>
                    [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>公司名</th>
                        <th>缩写</th>
                        [#--<th>负责人姓名</th>--]
                        [#--<th>号码</th>--]
                        <th>评分优先级</th>
                        <th>合作状态</th>
                        <th>接收库存区域</th>
                        <th>库存地统一为HK</th>
                        <th>库存地统一为SZ</th>
                        <th>默认香港</th>
                        <th>默认深圳</th>
                        [#--<th>开始合作时间</th>--]
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
                $('.form-control.name')[0].value = data.columns[1].search.search;
                $('.form-control.shortName')[0].value = data.columns[2].search.search;
                $('.form-control.status')[0].value = data.columns[7].search.search;
            },
            'ordering': true,
            'order': [6, 'desc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {data: 'name', name: 'name'},
                {data: 'shortName', name: 'shortName'},
                // {data: 'manName', name: 'manName'},
                // {data: 'manMobile', name: 'manMobile'},
                {data: 'score', name: 'score'},
                {
                    data: 'status', name: 'status', render: function (data, type, full, meta) {
                        return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                    }
                },
                {data: 'filterAddress', name: 'filterAddress'},
                {data: 'locationInHk', name: 'locationInHk'},
                {data: 'locationInSz', name: 'locationInSz'},
                {
                    data: 'enableInHk', name: 'enableInHk', render: function (data, type, full, meta) {
                        return data == 1 ? "上架" : "下架";
                    }
                },
                {
                    data: 'enableInSz', name: 'enableInSz', render: function (data, type, full, meta) {
                        return data == 1 ? "上架" : "下架";
                    }
                },
                // {data: 'workDate', name: 'workDate'},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [@authorize ifAnyGranted="thirdSupplier:view"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/thirdSupplier/view/' + data + '">详情</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="thirdSupplier:edit"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/thirdSupplier/edit/' + data + '">编辑</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="thirdSupplier:delete"]
                        operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                    [/@authorize]
                        return operations.join(' | ');
                    }
                }
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/thirdSupplier/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/thirdSupplier/" + id;
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
                    url: "${springMacroRequestContext.contextPath}/thirdSupplier/" + id + "/" + extUrl,
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
                        message = state ? '是否开启合作' : '是否关闭合作';
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
