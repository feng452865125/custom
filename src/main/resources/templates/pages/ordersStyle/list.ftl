[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="预约查询" subTitle=""]
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
                        <span class="input-group-addon">店铺编号</span>
                        <input name="storeCode" class="form-control storeCode" placeholder="请输入店铺编号查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">店铺名</span>
                        <input name="storeName" class="form-control storeName" placeholder="请输入店铺名查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">开始时间</span>
                        <div class="input-group input-daterange">
                            <input class="form-control startDate" name="startDate" placeholder="请选择开始时间">
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">结束时间</span>
                        <div class="input-group input-daterange">
                            <input class="form-control endDate" name="endDate" placeholder="请选择结束时间">
                        </div>
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
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>店铺编码</th>
                        <th>店铺名</th>
                        <th>预约产品</th>
                        <th>顾客姓名</th>
                        <th>号码</th>
                        <th>预约时间</th>
                        <th>状态</th>
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
<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script>
    $(function () {
        var $form = $('#example');
        var dataTable = $form.DataTable({
            'stateSave': true,
            'stateSaveParams': function (settings, data) {
                data.search.search = "";
                $('.form-control.storeCode')[0].value = data.columns[1].search.search;
                $('.form-control.storeName')[0].value = data.columns[2].search.search;
                $('.form-control.startDate')[0].value = data.columns[9].search.search;
                $('.form-control.endDate')[0].value = data.columns[10].search.search;
                $('.form-control.status')[0].value = data.columns[11].search.search;
            },
            'ordering': true,
            'order': [[11, 'asc'], [6, 'desc']],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {data: 'storeCode', name: 'storeCode', orderable: false},
                {data: 'storeName', name: 'storeName', orderable: false},
                {data: 'styleName', name: 'styleName', orderable: false},
                {data: 'customerName', name: 'customerName', orderable: false},
                {data: 'customerMobile', name: 'customerMobile', orderable: false},
                {data: 'createDate', name: 'createDate'},
                {data: 'statusName', name: 'statusName'},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                        [@authorize ifAnyGranted="ordersStyle:edit"]
                            if (full.status == 2) {
                                operations.push('<a style="color:#dadada">完成联系</a>');
                            } else {
                                operations.push('<a class="btn-haveCall" href="javascript:;" data-id="' + data + '">完成联系</a>');
                            }
                        [/@authorize]
                        [@authorize ifAnyGranted="ordersStyle:view"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/ordersStyle/view/' + data + '">详情</a>');
                        [/@authorize]
                        [@authorize ifAnyGranted="ordersStyle:delete"]
                            operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                        [/@authorize]
                        return operations.join(' | ');
                    }
                },
                {data: 'startDate', name: 'startDate', visible: false},
                {data: 'endDate', name: 'endDate', visible: false},
                {data: 'status', name: 'status', visible: false}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/ordersStyle/pagination',
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

        $form.on('click', '.btn-haveCall', function () {
            var $this = $(this);
            var id = $this.data('id');
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/ordersStyle/haveCall/" + id,
                type: 'POST',
                contentType: "application/json",
                headers: {"X-CSRF-TOKEN": '${_csrf.token}'}
            }).done(function (data) {
                dataTable.draw();
                $.notification.success(data);
            }).fail(function (error) {
                $.notification.error(error.responseText);
            })
        });

        $form.on('click', '.btn-delete', function () {
            var $this = $(this);
            var id = $this.data('id');
            var url = "${springMacroRequestContext.contextPath}/ordersStyle/" + id;
            $.showRemoveDialog(url, '${_csrf.token}', function () {
                dataTable.draw();
            })
        })


        $('.input-daterange').datepicker({
            language: "zh-CN",
            autoclose: true,
            clearBtn: true,
            format: "yyyy-mm-dd"
        });
    });
</script>
[/@content]
