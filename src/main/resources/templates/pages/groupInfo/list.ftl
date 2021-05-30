[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="组信息维护" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">
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
                        <span class="input-group-addon">组编码</span>
                        <input name="code" class="form-control" placeholder="请输入编码查询">
                    </div>
                 <div class="input-group">
                     <span class="input-group-addon">组名称</span>
                     <input name="name" class="form-control" placeholder="请输入名称查询">
                 </div>
                 <div class="input-group">
                     <span class="input-group-addon">组类型</span>
                     <select class="form-control" name="type">
                         <option value="">请选择类型</option>
                             [#list typeList.keys as type]
                                <option value="${type.key}">${type.label}</option>
                             [/#list]
                     </select>
                 </div>
                [/@searchable]
                <div>
                    [@authorize ifAnyGranted="groupInfo:add"]
                        <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/groupInfo/add"
                           redirect>新增</a>
                    [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>组编码</th>
                        <th>组名称</th>
                        <th>组类型</th>
                        <th>包含样式</th>
                        <th>组描述</th>
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
<script>
    $(function () {
        var $form = $('#example');
        var dataTable = $form.DataTable({
            'ordering': true,
            'order' : [8,'asc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible:false},
                {data: 'code', name: 'code'},
                {data: 'name', name: 'name'},
                {data: 'typeName', name: 'typeName', orderable: false},
                {data: 'ys', name: 'ys'},
                {data: 'remark', name: 'remark'},
                {data: 'createDate', name: 'createDate'},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [@authorize ifAnyGranted="groupInfo:view"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/groupInfo/view/' + data + '">详情</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="groupInfo:edit"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/groupInfo/edit/' + data + '">编辑</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="groupInfo:delete"]
                        operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                    [/@authorize]
                        return operations.join(' | ');
                    }
                },
                {data: 'type', name: 'type', visible:false},
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/groupInfo/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/groupInfo/" + id;
            $.showRemoveDialog(url, '${_csrf.token}', function () {
                dataTable.draw();
            })
        })


    });
</script>
[/@content]
