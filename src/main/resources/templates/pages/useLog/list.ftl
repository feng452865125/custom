[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="报表查询" subTitle=""]
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
                [@searchable id="searchable" targetId="example"]
                    <div class="input-group">
                        <span class="input-group-addon">开始时间</span>
                        <div class="input-group input-daterange">
                            <input class="form-control startDate" name="startDate" placeholder="请选择开始时间"
                            >
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">结束时间</span>
                        <div class="input-group input-daterange">
                            <input class="form-control endDate" name="endDate" placeholder="请选择结束时间">
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">样式编码</span>
                        <div class="input-group">
                            <input class="form-control styleCode" name="styleCode" placeholder="请输入编码">
                        </div>
                    </div>
                   <div class="input-group">
                       <div class="input-group">
                           <input type="checkbox" name="search" value="0" checked style="display: none;">
                           <label class="input-group-addon"><input type="checkbox" name="search" value="1">转发</label>
                           <label class="input-group-addon"><input type="checkbox" name="search" value="2">试戴</label>
                           <label class="input-group-addon"><input type="checkbox" name="search" value="3">定制</label>
                       </div>
                   </div>
                [/@searchable]
                <div>
                [#--[@authorize ifAnyGranted="orders:add"]--]
                [#--<a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/useLog/add"--]
                [#--redirect>新增</a>--]
                [#--[/@authorize]--]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>样式名称</th>
                        <th>样式编码</th>
                        <th>类型</th>
                        <th>来源</th>
                        <th>时间</th>
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
            'stateSave': true,
            'stateSaveParams': function (settings, data) {
                data.search.search = "";
                $('.form-control.startDate')[0].value = data.columns[7].search.search;
                $('.form-control.endDate')[0].value = data.columns[8].search.search;
                $('.form-control.styleCode')[0].value = data.columns[2].search.search;
                var list = data.columns[10].search.search.split(",");
                var inputList = $('input[type="checkbox"]');
                for (var i = 1; i < inputList.length; i++) {
                    var checkBox = $('input[type="checkbox"]')[i];
                    for (var j = 0; j < list.length; j++){
                        if (list[j] != 0 && list[j] == checkBox.value){
                            checkBox.checked = true;
                            break;
                        }
                    }
                }
            },
            'ordering': true,
            'order' : [5,'desc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false},
                {data: 'styleName', name: 'styleName', orderable: false},
                {data: 'styleCode', name: 'styleCode', orderable: false},
                {data: 'typeName', name: 'typeName', orderable: false},
                {data: 'sourceName', name: 'sourceName', orderable: false},
                {data: 'createDate', name: 'createDate'},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                        [@authorize ifAnyGranted="useLog:view"]
                            operations.push('<a redirect href="${springMacroRequestContext.contextPath}/useLog/view/' + data + '">详情</a>');
                        [/@authorize]
                    [#--[@authorize ifAnyGranted="orders:edit"]--]
                    [#--operations.push('<a redirect href="${springMacroRequestContext.contextPath}/useLog/edit/' + data + '">编辑</a>');--]
                    [#--[/@authorize]--]
                    [#--[@authorize ifAnyGranted="orders:delete"]--]
                    [#--operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')--]
                    [#--[/@authorize]--]
                        return operations.join(' | ');
                    }
                },
                {data: 'startDate', name: 'startDate', orderable: false, visible: false},
                {data: 'endDate', name: 'endDate', orderable: false, visible: false},
                {data: 'search', name: 'search', orderable: false, visible: false},
                {data: 'remark', name: 'remark', orderable: false, visible: false}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/useLog/pagination',
                'type': 'POST',
                'beforeSend': function (request) {
                    request.setRequestHeader('X-CSRF-TOKEN', '${_csrf.token}');
                    return request;
                },
                'data': function (d) {
                    return JSON.stringify(d);
                }
            }
        });

        $form.on('click', '.btn-delete', function () {
            var $this = $(this);
            var id = $this.data('id');
            var url = "${springMacroRequestContext.contextPath}/useLog/" + id;
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
