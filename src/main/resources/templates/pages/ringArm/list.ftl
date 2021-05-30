[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="戒臂维护" subTitle=""]
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
                        <span class="input-group-addon">戒臂编号</span>
                        <input name="code" class="form-control code" placeholder="请输入编号查询">
                    </div>
                 <div class="input-group">
                     <span class="input-group-addon">戒臂样式</span>
                     <input name="ys" class="form-control ys" placeholder="请输入样式查询">
                 </div>
                    <div class="input-group">
                        <span class="input-group-addon">所属组</span>
                        <select class="form-control groupType" name="groupType">
                            <option value="">请选择组</option>
                             [#list groupTypeList as groupType]
                                <option value="${groupType.id}">${groupType.name}</option>
                             [/#list]
                        </select>
                    </div>
                [/@searchable]
                <div>
                [#--[@authorize ifAnyGranted="ringArm:add"]--]
                [#--<a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/ringArm/add"--]
                [#--redirect>新增</a>--]
                [#--[/@authorize]--]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>戒臂编码</th>
                        <th>戒臂名称</th>
                        <th>手寸范围</th>
                        <th>样式</th>
                        <th>宽度</th>
                        <th>同样式主要展示</th>
                        <th>所属组</th>
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
                $('.form-control.ys')[0].value = data.columns[4].search.search;
                $('.form-control.groupType')[0].value = data.columns[9].search.search;
            },
            'ordering': true,
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {data: 'code', name: 'code'},
                {data: 'name', name: 'name'},
                {data: 'exSc', name: 'exSc', orderable: false},
                {data: 'ys', name: 'ys'},
                {data: 'fsKd', name: 'fsKd', orderable: false},
                {
                    data: 'isShow', name: 'isShow', render: function (data, type, full, meta) {
                        return data ? "是":"否";
                    }
                },
                {data: 'groupTypeName', name: 'groupTypeName', orderable: false},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [@authorize ifAnyGranted="ringArm:view"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/ringArm/view/' + data + '">详情</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="ringArm:edit"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/ringArm/edit/' + data + '">编辑</a>');
                    [/@authorize]
                    [#--[@authorize ifAnyGranted="ringArm:delete"]--]
                        [#--operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')--]
                    [#--[/@authorize]--]
                        return operations.join(' | ');
                    }
                },
                {data: 'groupType', name: 'groupType', visible:false},
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/ringArm/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/ringArm/" + id;
            $.showRemoveDialog(url, '${_csrf.token}', function () {
                dataTable.draw();
            })
        })


    });
</script>
[/@content]
