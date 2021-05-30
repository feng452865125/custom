[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="订单查询" subTitle=""]
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
                        <span class="input-group-addon">订单编号</span>
                        <input name="code" class="form-control code" placeholder="请输入编号查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">店铺</span>
                        <input name="storeCode" class="form-control storeCode" placeholder="请输入店铺名或编号查询">
                    </div>

                    <div class="input-group">
                        <span class="input-group-addon">证书号</span>
                        <input name="zsBh" class="form-control zsBh" placeholder="请输入证书号查询">
                    </div>
                    [#--<div class="input-group">--]
                        [#--<span class="input-group-addon">店铺编号</span>--]
                        [#--<input name="storeCode" class="form-control storeCode" placeholder="请输入店铺编号查询">--]
                    [#--</div>--]
                    [#--<div class="input-group">--]
                        [#--<span class="input-group-addon">店铺名</span>--]
                        [#--<input name="storeName" class="form-control storeName" placeholder="请输入店铺名查询">--]
                    [#--</div>--]
                    [#--<div class="input-group">--]
                        [#--<span class="input-group-addon">包含产品</span>--]
                        [#--<input name="productName" class="form-control productName" placeholder="请输入产品名称查询">--]
                    [#--</div>--]
                    <div class="input-group">
                        <span class="input-group-addon">下单时间</span>
                        <div class="input-group input-daterange">
                            <input class="form-control startDate" name="startDate" placeholder="请选择下单时间">
                        </div>
                    </div>
                    [#--<div class="input-group">--]
                        [#--<span class="input-group-addon">结束时间</span>--]
                        [#--<div class="input-group input-daterange">--]
                            [#--<input class="form-control endDate" name="endDate" placeholder="请选择结束时间">--]
                        [#--</div>--]
                    [#--</div>--]
                [/@searchable]
                <div>
                [#--[@authorize ifAnyGranted="orders:add"]--]
                [#--<a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/orders/add"--]
                [#--redirect>新增</a>--]
                [#--[/@authorize]--]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>订单编码</th>
                        <th>店铺编码</th>
                        <th>店铺名</th>
                        <th>包含产品</th>
                        <th>顾客姓名</th>
                        <th>号码</th>
                        [#--<th>地址</th>--]
                        <th>下单日期</th>
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
                $('.form-control.code')[0].value = data.columns[1].search.search;
                $('.form-control.storeCode')[0].value = data.columns[2].search.search;
                $('.form-control.startDate')[0].value = data.columns[9].search.search;
                $('.form-control.zsBh')[0].value = data.columns[12].search.search;
            },
            'ordering': true,
            'order' : [7,'desc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {data: 'code', name: 'code'},
                {data: 'storeCode', name: 'storeCode', orderable: false},
                {data: 'storeName', name: 'storeName', orderable: false},
                {data: 'productName', name: 'productName', orderable: false},
                {data: 'customerName', name: 'customerName', orderable: false},
                {data: 'customerMobile', name: 'customerMobile', orderable: false},
                {data: 'createDate', name: 'createDate'},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                        [@authorize ifAnyGranted="orders:view"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/orders/view/' + data + '">详情</a>');
                        [/@authorize]
                        [#--[@authorize ifAnyGranted="orders:edit"]--]
                            [#--operations.push('<a redirect href="${springMacroRequestContext.contextPath}/orders/edit/' + data + '">编辑</a>');--]
                        [#--[/@authorize]--]
                        [@authorize ifAnyGranted="orders:delete"]
                            operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                        [/@authorize]
                        // if(full.ktCode == "" || full.ktCode == null){
                        [@authorize ifAnyGranted="orders:edit"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/orders/edit/' + data + '">编辑</a>')
                        [/@authorize]
                        // }

                        return operations.join(' | ');
                    }
                },
                {data: 'startDate', name: 'startDate', visible: false},
                {data: 'endDate', name: 'endDate', visible: false},
                {data: 'ktCode', name: 'ktCode', visible: false},
                {data: 'zsBh', name: 'zsBh', visible: false},
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/orders/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/orders/" + id;
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
