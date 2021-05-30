[#include "../../component/content.ftl" /]
[#include "../../component/authorize.ftl"]


[@content title="数据同步" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">

<style>
    input[type="checkbox"] {
        /*-webkit-appearance: none;  !*清除复选框默认样式*!*/
        /*background: #fff url(i/blue.png);   !*复选框的背景图，就是上图*!*/
        height: 20px; /*高度*/
        vertical-align: middle;
        width: 20px;
    }
</style>
<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">列表</h3>
            </div>
            <!-- /.series-header -->
            <div class="box-body">
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>内容</th>
                        <th>来源</th>
                        <th>最新同步时间</th>
                        <th>操作用户</th>
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
<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
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
                {data: 'id', name: 'id', visible: false},
                {data: 'name', name: 'name', orderable: false},
                {data: 'resource', name: 'resource', orderable: false},
                {data: 'createDate', name: 'createDate'},
                {data: 'userName', name: 'userName', orderable: false},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                          [@authorize ifAnyGranted="dataSynch:synch"]
                            operations.push('<a class="btn-submit" href="javascript:;" data-id="' + data + '">同步</a>');
                          [/@authorize]
                          [@authorize ifAnyGranted="dataSynch:view"]
                             operations.push('<a redirect href="${springMacroRequestContext.contextPath}/dataSynch/view/' + data + '">详情</a>');
                          [/@authorize]
                        return operations.join(' | ');
                    }
                }
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/dataSynch/pagination',
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


        $form.on('click', '.btn-submit', function () {
            $this = $(this);
            var id = $this.data('id');
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/dataSynch/" + id,
                type: 'POST',
                data: {${_csrf.parameterName}:"${_csrf.token}"
                },
            success: function (success) {
                dataTable.draw();
                $.notification.success(success);
            },
            error: function (error) {
                $.notification.error(error.responseText);
            }
        })
        })

    });

</script>
[/@content]
