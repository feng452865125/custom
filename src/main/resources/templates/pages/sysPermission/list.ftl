[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="权限维护" subTitle=""]
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">

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
                            <span class="input-group-addon">编码</span>
                            <input type="text" name="sysPermissionCode" class="form-control">
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">名称</span>
                            <input type="text" name="sysPermissionName" class="form-control">
                        </div>
                    [/@searchable]
                    <div>
                        [@authorize ifAnyGranted="sysPermission:add"]
                            <a class="btn btn-flat bg-olive"
                               href="${springMacroRequestContext.contextPath}/SysPermission/add" redirect>新增</a>
                        [/@authorize]
                    </div>
                    <table id="example" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>编码</th>
                            <th>名称</th>
                            <th>分类</th>
                            <th>系统内置</th>
                            <th>描述</th>
                            <th>创建日期</th>
                            <th>修改日期</th>
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
    <script>
        $(function () {
            var $form = $('#example');
            var dataTable = $form.DataTable({
                'stateSave': true,
                'stateSaveParams': function (settings, data) {
                    data.search.search = "";
                    $('.form-control.sysPermissionCode')[0].value = data.columns[1].search.search;
                    $('.form-control.sysPermissionName')[0].value = data.columns[2].search.search;
                },
                'ordering': true,
                'order': [0, 'asc'],
                'info': true,
                'autoWidth': false,
                'processing': true,
                'serverSide': true,
                'columns': [
                    {data: 'id', name: 'id', visible: false},
                    {data: 'sysPermissionCode', name: 'sysPermissionCode'},
                    {data: 'sysPermissionName', name: 'sysPermissionName'},
                    {data: 'sysPermissionPname', name: 'sysPermissionPname'},
                    {
                        data: 'sysPermissionIsSystem', name: 'sysPermissionIsSystem', render: function (data) {
                            return data ? '是' : '否'
                        }
                    },
                    {data: 'sysPermissionRemark', name: 'sysPermissionRemark'},
                    {data: 'createDate', name: 'createDate'},
                    {data: 'updateDate', name: 'updateDate'},
                    {
                        data: 'id', orderabled: false, render: function (data, type, full, meta) {
                            var operations = [];
                            [@authorize ifAnyGranted="sysPermission:view"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/SysPermission/view/' + data + '">详情</a>');
                            [/@authorize]
                            [@authorize ifAnyGranted="sysPermission:edit"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/SysPermission/edit/' + data + '">编辑</a>');
                            [/@authorize]
                            [@authorize ifAnyGranted="sysPermission:delete"]
                            operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                            [/@authorize]
                            return operations.join(' | ');
                        }
                    }
                ],
                'ajax': {
                    'contentType': 'application/json',
                    'url': '${springMacroRequestContext.contextPath}/SysPermission/pagination',
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
                var id = $(this).data('id');
                var url = '${springMacroRequestContext.contextPath}/SysPermission/' + id;
                $.showRemoveDialog(url, '${_csrf.token}', function () {
                    dataTable.draw();
                })
            })
        });
    </script>
[/@content]
