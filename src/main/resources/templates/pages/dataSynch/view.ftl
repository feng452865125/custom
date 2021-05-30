[#include "../../component/content.ftl" /]

[@content title="同步维护" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">

<div class="row">
    <!-- left column -->
    <div class="col-md-12">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">详情</h3>
            </div>
            <!-- /.series-header -->
            <!-- form start -->
            <div class="box-body">
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="name">名称</label>
                            <p>${dataSynch.name}</p>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="resource">来源</label>
                            <p>${dataSynch.resource}</p>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-8">
                        <table id="example" class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>id</th>
                                <th>名称</th>
                                <th>来源</th>
                                <th>创建时间</th>
                                <th>操作用户</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- /.series-body -->

            <div class="box-footer">
                <div class="row">
                    <div class="col-xs-offset-1">
                        <button type="button" class="btn btn-default btn-back">返回</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- /.series -->
    </div>
    <!--/.col (left) -->
</div>
<!-- /.row -->
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/js/bootstrap-switch.js"></script>
<script>
    $(function () {
        var $form = $('#example');
        var dataTable = $form.DataTable({
            'ordering': true,
            'order' : [4,'desc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', visible: false},
                {data: 'id', name: 'id', orderable: false},
                {data: 'name', name: 'name', orderable: false},
                {data: 'resource', name: 'resource', orderable: false},
                {data: 'createDate', name: 'createDate'},
                {
                    data: 'userCode', name: 'userCode', render: function (data, type, full, meta) {
                        return data ? data : 'Auto'
                    }
                }
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/dataSynch/select/' + ${dataSynch.id},
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
