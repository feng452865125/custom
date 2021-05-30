[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="dada样式维护" subTitle=""]
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
                        <span class="input-group-addon">样式编码</span>
                        <input name="code" class="form-control code" placeholder="请输入编码查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">类型</span>
                        <select class="form-control type" name="type">
                            <option value="">请选择</option>
                             [#list typeList as type]
                                <option value="${type.id}">${type.name}</option>
                             [/#list]
                        </select>
                    </div>
                 <div class="input-group">
                     <span class="input-group-addon">所在系列</span>
                     <select class="form-control series" name="series">
                         <option value="">请选择</option>
                             [#list seriesList as series]
                                <option value="${series.name}">${series.name}</option>
                             [/#list]
                     </select>
                 </div>
                [/@searchable]
                <div>
                [@authorize ifAnyGranted="dadaStyle:add"]
                    <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/dadaStyle/add"
                       redirect>新增</a>
                [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover table_style table_img_style dada_style_view">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th class="img_td">配图</th>
                        <th>编码</th>
                        <th>名称</th>
                        <th>类型</th>
                        <th>系列</th>
                        <th>描述</th>
                        <th>创建时间</th>
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
                $('.form-control.code')[0].value = data.columns[2].search.search;
                $('.form-control.type')[0].value = data.columns[9].search.search;
                $('.form-control.series')[0].value = data.columns[5].search.search;
            },
            'ordering': true,
            'order' : [7,'desc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', visible: false},
                {
                    data: 'imgUrl', name: 'imgUrl', orderable: false, render: function (data, type, full, meta) {
                        return data ?
                                '<img src="' + data + '" data-id="' + full.id + '" data-size="mini" class="img_img">'
                                :
                                '无';
                    }
                },
                {data: 'code', name: 'code', orderable: false},
                {data: 'name', name: 'name', orderable: false},
                {data: 'typeName', name: 'typeName', orderable: false},
                {data: 'series', name: 'series', orderable: false},
                {data: 'remark', name: 'remark', orderable: false},
                {data: 'createDate', name: 'createDate'},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                        [@authorize ifAnyGranted="dadaStyle:edit"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/dadaStyle/edit/' + data + '">编辑</a>');
                        [/@authorize]
                        [@authorize ifAnyGranted="dadaStyle:view"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/dadaStyle/view/' + data + '">详情维护</a>');
                        [/@authorize]
                        [@authorize ifAnyGranted="dadaStyle:delete"]
                            operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                        [/@authorize]
                        return operations.join(' | ');
                    }
                },
                {data: 'type', name: 'type', orderable: false, visible: false}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/dadaStyle/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/dadaStyle/" + id;
            $.showRemoveDialog(url, '${_csrf.token}', function () {
                dataTable.draw();
            })
        })


    });
</script>
[/@content]
