[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="样式维护" subTitle=""]
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
                            <span class="input-group-addon">样式编码</span>
                            <input name="code" class="form-control code" placeholder="请输入编码查询">
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">类型</span>
                            <select class="form-control type" name="type">
                                <option value="">请选择</option>
                                [#list typeList as type]
                                    <option value="${type.id}">${type.name}</option>
                                [/#list]
                            </select>
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">所在系列</span>
                            <select class="form-control series" name="series">
                                <option value="">请选择</option>
                                [#list seriesList as series]
                                    <option value="${series.name}">${series.name}</option>
                                [/#list]
                            </select>
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">花头样式</span>
                            <input name="htYs" class="form-control htYs" placeholder="请输入花头编码查询">
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">戒臂样式</span>
                            <input name="jbYs" class="form-control jbYs" placeholder="请输入戒臂编码查询">
                        </div>

                    [/@searchable]
                    <div>
                        [@authorize ifAnyGranted="style:add"]
                            <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/style/add"
                               redirect>新增（仅UNIQUE系列）</a>
                        [/@authorize]
                    </div>
                    <table id="example" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>id</th>
                            <th>id</th>
                            <th>样式编码</th>
                            <th>样式名称</th>
                            <th>类型</th>
                            <th>寓意</th>
                            <th>花头样式</th>
                            <th>戒臂样式</th>
                            <th>系列</th>
                            <th>是否展示</th>
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
                    $('.form-control.code')[0].value = data.columns[2].search.search;
                    $('.form-control.type')[0].value = data.columns[11].search.search;
                    $('.form-control.series')[0].value = data.columns[8].search.search;
                    $('.form-control.htYs')[0].value = data.columns[6].search.search;
                    $('.form-control.jbYs')[0].value = data.columns[7].search.search;
                },
                'ordering': true,
                'order': [1, 'desc'],
                'info': true,
                'autoWidth': false,
                'processing': true,
                'serverSide': true,
                'columns': [
                    {data: 'id', name: 'id', visible: false},
                    {data: 'id', name: 'id', orderable: false},
                    {data: 'code', name: 'code', orderable: false},
                    {data: 'name', name: 'name', orderable: false},
                    {data: 'typeName', name: 'typeName', orderable: false},
                    {data: 'moral', name: 'moral'},
                    {data: 'htYs', name: 'htYs', orderable: false},
                    {data: 'jbYs', name: 'jbYs', orderable: false},
                    {data: 'series', name: 'series', orderable: false},
                    {
                        data: 'enabled', name: 'enabled', render: function (data, type, full, meta) {
                            return data == 1 ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                        }
                    },
                    {
                        data: 'id', orderable: false, render: function (data, type, full, meta) {
                            var operations = [];
                            [@authorize ifAnyGranted="style:view"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/style/view/' + data + '">详情</a>');
                            [/@authorize]
                            [@authorize ifAnyGranted="style:edit"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/style/edit/' + data + '">编辑</a>');
                            [/@authorize]
                            [@authorize ifAnyGranted="style:delete"]
                            operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                            [/@authorize]
                            return operations.join(' | ');
                        }
                    },
                    {data: 'type', name: 'type', orderable: false, visible: false}
                ],
                'ajax': {
                    'contentType': 'application/json',
                    'url': '${springMacroRequestContext.contextPath}/style/pagination',
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
                var url = "${springMacroRequestContext.contextPath}/style/" + id;
                $.showRemoveDialog(url, '${_csrf.token}', function () {
                    dataTable.draw();
                })
            })

            dataTable.on('draw.dt', function () {

                var currentState;
                var currentSwitchTarget;
                var $switch = $("input.enabled");
                var switchable = false;

                var confirmOperation = function () {
                    var extUrl = currentState ? 'enabled' : 'disabled';
                    $.ajax({
                        url: "${springMacroRequestContext.contextPath}/style/" + id + "/" + extUrl,
                        type: 'PATCH',
                        data: {${_csrf.parameterName}: "${_csrf.token}"},
                        success: function (err) {
                            switchable = true;
                            currentSwitchTarget.bootstrapSwitch('toggleState');
                        },
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
                        var message = state ? '是否展示' : '是否隐藏';
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
