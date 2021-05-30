[#include "../../component/content.ftl" /]
[#include "../../component/authorize.ftl"]
[#include "../../component/searchable.ftl" /]

[@content title="配置维护" subTitle=""]
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
                        <span class="input-group-addon">名称</span>
                        <input name="name" class="form-control name" placeholder="请输入名称查询">
                    </div>
                [/@searchable]
                <div>
            <div class="box-body">
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>id</th>
                        <th>名称</th>
                        <th>key</th>
                        <th>value</th>
                        <th>详情描述</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody style="word-break: break-all;">
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
                $('.form-control.name')[0].value = data.columns[2].search.search;
            },
            'ordering': true,
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', visible:false},
                {
                    data: 'id', name: 'id', render: function (data, type, full, meta) {
                        return meta.row + 1;
                    }
                },
                {data: 'name', name: 'name', orderable:false},
                {data: 'key', name: 'key', orderable:false},
                {data: 'value', name: 'value', orderable:false},
                {data: 'remark', name: 'remark', orderable:false},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [@authorize ifAnyGranted="sysConfig:edit"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/sysConfig/edit/' + data + '">编辑</a>');
                    [/@authorize]
                        return operations.join(' | ');
                    }
                }
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/sysConfig/pagination',
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

    });
</script>
[/@content]
