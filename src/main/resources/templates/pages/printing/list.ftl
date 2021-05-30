[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="印花维护" subTitle=""]
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
                        <span class="input-group-addon">印花编码</span>
                        <input name="code" class="form-control" placeholder="请输入编码查询">
                    </div>
                 <div class="input-group">
                     <span class="input-group-addon">印花名称</span>
                     <input name="name" class="form-control" placeholder="请输入名称查询">
                 </div>
                [/@searchable]
                <div>
                    [@authorize ifAnyGranted="printing:add"]
                        <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/printing/add"
                           redirect>新增</a>
                    [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover table_style table_img_style">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th class="img_td">配图</th>
                        <th>印花编码</th>
                        <th>印花名称</th>
                        <th>印花描述</th>
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
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {
                    data: 'imgUrl', name: 'imgUrl', render: function (data, type, full, meta) {
                        return data ?
                                '<img src="' + data + '" data-id="' + full.id + '" data-size="mini" class="img_img">'
                                :
                                '无';
                    }
                },
                {data: 'code', name: 'code'},
                {data: 'name', name: 'name'},
                {data: 'remark', name: 'remark'},
                {data: 'createDate', name: 'createDate'},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [@authorize ifAnyGranted="printing:view"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/printing/view/' + data + '">详情</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="printing:edit"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/printing/edit/' + data + '">编辑</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="printing:delete"]
                        operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                    [/@authorize]
                        return operations.join(' | ');
                    }
                }
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/printing/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/printing/" + id;
            $.showRemoveDialog(url, '${_csrf.token}', function () {
                dataTable.draw();
            })
        })


    });
</script>
[/@content]
