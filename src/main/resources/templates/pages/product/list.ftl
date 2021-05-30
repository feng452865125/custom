[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="产品维护" subTitle=""]
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
                        <span class="input-group-addon">产品编号</span>
                        <input name="code" class="form-control code" placeholder="请输入编号查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">客定码</span>
                        <input name="ktCode" class="form-control ktCode" placeholder="请输入客定码查询">
                    </div>
                [/@searchable]
                <div>
                [#--[@authorize ifAnyGranted="product:add"]--]
                [#--<a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/product/add"--]
                [#--redirect>新增</a>--]
                [#--[/@authorize]--]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>产品编码</th>
                        <th>客定码</th>
                        <th>寓意</th>
                        <th>钻石颜色</th>
                        <th>钻石净度</th>
                        <th>价格</th>
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
                $('.form-control.ktCode')[0].value = data.columns[2].search.search;
            },
            'ordering': true,
            'order': [0, 'desc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {data: 'code', name: 'code'},
                {data: 'ktCode', name: 'ktCode'},
                {data: 'exYy', name: 'exYy'},
                {data: 'exZsYs', name: 'exZsYs'},
                {data: 'exZsJd', name: 'exZsJd'},
                {
                    data: 'price', name: 'price', render: function (data, type, full, meta) {
                        return data / 100;
                    }
                },
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                        [@authorize ifAnyGranted="product:view"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/product/view/' + data + '">详情</a>');
                        [/@authorize]
                        [@authorize ifAnyGranted="product:edit"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/product/edit/' + data + '">编辑</a>');
                        [/@authorize]
                    [#--[@authorize ifAnyGranted="product:delete"]--]
                    [#--operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')--]
                    [#--[/@authorize]--]
                        return operations.join(' | ');
                    }
                }
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/product/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/product/" + id;
            $.showRemoveDialog(url, '${_csrf.token}', function () {
                dataTable.draw();
            })
        })


    });
</script>
[/@content]
