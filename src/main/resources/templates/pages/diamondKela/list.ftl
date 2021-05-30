[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="钻石（克拉展）维护" subTitle=""]
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
                        <span class="input-group-addon">钻石编号</span>
                        <input name="code" class="form-control code" placeholder="请输入编号查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">状态</span>
                        <select class="form-control status" id="status" name="status">
                            <option value="">请选择状态</option>
                                [#list statusList.keys as status]
                                    <option value="${status.key}">${status.label}</option>
                                [/#list]
                        </select>
                    </div>
                [/@searchable]
                <div>
                [@authorize ifAnyGranted="diamondKela:add"]
                    <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/diamondKela/add"
                       redirect>新增</a>
                [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>钻石编码</th>
                        <th>钻石名称</th>
                        <th>质量（单位：克拉）</th>
                        <th>状态</th>
                        <th>价格（单位：元）</th>
                        <th>创建日期</th>
                        <th>销售日期</th>
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
            'stateSave': true,
            'stateSaveParams': function (settings, data) {
                data.search.search = "";
                $('.form-control.code')[0].value = data.columns[1].search.search;
                $('.form-control.status')[0].value = data.columns[9].search.search;
            },
            'ordering': true,
            'order': [6, 'desc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {data: 'code', name: 'code', orderable: false},
                {data: 'name', name: 'name', orderable: false},
                {
                    data: 'exZsZl', name: 'exZsZl', render: function (data, type, full, meta) {
                        return data / 1000;
                    }
                },
                {data: 'statusName', name: 'statusName', orderable: false},
                {
                    data: 'price', name: 'price', render: function (data, type, full, meta) {
                        return data / 100;
                    }
                },
                {data: 'createDate', name: 'createDate'},
                {
                    data: 'updateDate', name: 'updateDate', render: function (data, type, full, meta) {
                        if (full.status != null && full.status != 2) {
                            return '';
                        } else {
                            return data;
                        }
                    }
                },
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [@authorize ifAnyGranted="diamondKela:view"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/diamondKela/view/' + data + '">详情</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="diamondKela:edit"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/diamondKela/edit/' + data + '">编辑</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="diamondKela:delete"]
                    operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                    [/@authorize]
                        return operations.join(' | ');
                    }
                },
                {data: 'status', name: 'status', visible: false}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/diamondKela/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/diamondKela/" + id;
            $.showRemoveDialog(url, '${_csrf.token}', function () {
                dataTable.draw();
            })
        })


    });
</script>
[/@content]
