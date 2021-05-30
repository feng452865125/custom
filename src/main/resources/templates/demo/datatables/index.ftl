[#include "../../component/content.ftl" /]

[@content title="JQuery.Datatables" subTitle="列表样例代码"]
<!-- DataTables -->
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">

<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">Hover Data Table</h3>
            </div>
            <!-- /.series-header -->
            <div class="box-body">
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>创建日期</th>
                        <th>修改日期</th>
                        <th>名称</th>
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
        $('#example').DataTable({
            'searching': false,
            'ordering': true,
            'info': true,
            'autoWidth': false,
            'serverSide': true,
            'columns': [
                {data: 'id'},
                {data: 'createDate'},
                {data: 'updateDate'},
                {data: 'name'},
                {data: 'id', render: function ( data, type, full, meta ) {
                    return '<a redirect href="${springMacroRequestContext.contextPath}/demo/detail/' + data + '">详情</a>';
                }}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/demo/serverPaginationData',
                'type': 'POST',
                'beforeSend': function (request) {
                    request.setRequestHeader('X-CSRF-TOKEN', '${_csrf.token}');
                    return request;
                },
                'data': function(d) {
                    [#--d.${_csrf.parameterName} = '${_csrf.token}';--]
                    [#--d.${_csrf.parameterName} = '${_csrf.token}';--]
                    return JSON.stringify(d);
                },
            }
         });

    });
</script>
[/@content]
