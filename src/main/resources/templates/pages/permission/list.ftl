[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="权限" subTitle=""]
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
                    <input type="text" name="code" class="form-control" placeholder="请输入编码查询">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">名称</span>
                    <input type="text" name="name" class="form-control" placeholder="请输入名称查询">
                </div>
                [/@searchable]
                <div>
                    [@authorize ifAnyGranted="permission:add"]
                        <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/permission/add" redirect>新增</a>
                    [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>创建日期</th>
                        <th>修改日期</th>
                        <th>编码</th>
                        <th>名称</th>
                        <th>分类</th>
                        <th>描述</th>
                        <th>是否系统内置</th>
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
            'ordering': true,
            'info': true,
            'autoWidth': false,
            'processing' : true,
            'serverSide': true,
            'columns': [
                {data: 'createDate', name: 'createDate'},
                {data: 'updateDate', name: 'updateDate'},
                {data: 'code', name: 'code'},
                {data: 'name', name: 'name'},
                {data: 'pname', name: 'pname'},
                {data: 'description', name: 'description'},
                {data: 'system', name: 'isSystem', render: function(data) {
                    return data ? '是' : '否'
                }},
                {data: 'id', orderabled: false, render: function ( data, type, full, meta ) {
                    var operations = [];
                    [@authorize ifAnyGranted="permission:view"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/permission/view/' + data + '">详情</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="permission:edit"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/permission/edit/' + data + '">编辑</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="permission:delete"]
                        operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                    [/@authorize]
                    return operations.join(' | ');
                }}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/permission/pagination',
                'type': 'POST',
                'beforeSend': function (request) {
                    request.setRequestHeader('X-CSRF-TOKEN', '${_csrf.token}');
                    return request;
                },
                'data': function(d) {
                    return JSON.stringify(d);
                },
            }
         });

        $form.on('click', '.btn-delete', function() {
            var id = $(this).data('id');
            var url = '${springMacroRequestContext.contextPath}/permission/' + id;
            $.showRemoveDialog(url, '${_csrf.token}', function() {
                dataTable.draw();
            })
        })
    });
</script>
[/@content]
