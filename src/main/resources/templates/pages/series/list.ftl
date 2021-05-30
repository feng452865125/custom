[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="系列维护" subTitle=""]
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
                        <span class="input-group-addon">系列名称</span>
                        <input name="name" class="form-control" placeholder="请输入系列名称查询">
                    </div>
                 <div class="input-group">
                     <span class="input-group-addon">图片描述</span>
                     <input name="remark" class="form-control" placeholder="请输入图片描述查询">
                 </div>
                [/@searchable]
                <div>
                    [@authorize ifAnyGranted="series:add"]
                        <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/series/add"
                           redirect>新增</a>
                    [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover table_style table_img_style">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th class="img_td">配图</th>
                        <th>系列名称</th>
                        <th>图片描述</th>
                        <th>前端展示顺序</th>
                        <th>是否展示</th>
                        <th>创建日期</th>
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
            'ordering': true,
            'order' : [4,'asc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible:false},
                {data: 'imgUrl', name: 'imgUrl', render: function (data, type, full, meta) {
                        return data ?
                                '<img src="' + data + '" data-id="' + full.id + '" data-size="mini" class="img_img">'
                                :
                                '无';
                    }},
                {data: 'name', name: 'name'},
                {data: 'remark', name: 'remark'},
                {data: 'level', name: 'level'},
                {
                    data: 'enabled', name: 'enabled', render: function (data, type, full, meta) {
                        return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                    }
                },
                {data: 'createDate', name: 'createDate'},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [@authorize ifAnyGranted="series:view"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/series/view/' + data + '">详情</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="series:edit"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/series/edit/' + data + '">编辑</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="series:delete"]
                        operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                    [/@authorize]
                        return operations.join(' | ');
                    }
                }
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/series/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/series/" + id;
            $.showRemoveDialog(url, '${_csrf.token}', function () {
                dataTable.draw();
            })
        })

        dataTable.on('draw.dt', function () {

            var currentState;
            var currentSwitchTarget;
            var $switch = $("input.locked, input.enabled");
            var switchable = false;

            var confirmOperation = function () {
                debugger
                var extUrl;
                var cb = function () {
                    switchable = true;
                    currentSwitchTarget.bootstrapSwitch('toggleState');
                };
                if (currentSwitchTarget.attr('class') === 'locked') {
                    extUrl = currentState ? 'lock' : 'unlocked';
                } else {
                    extUrl = currentState ? 'enabled' : 'disabled';
                }
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/series/" + id + "/" + extUrl,
                    type: 'PATCH',
                    data: {${_csrf.parameterName}:"${_csrf.token}"},
                success: cb,
                        error:function (error) {
                    $.notification.error(error.responseText);
                }
            })
            }

            $switch.bootstrapSwitch({
                onSwitchChange: function (e, state) {
                    if (switchable) {
                        switchable = false;
                        return true;
                    }
                    ;
                    var message;
                    $this = $(this);
                    id = $this.data('id');
                    if ($this.attr('class') === 'locked') {
                        message = state ? '是否锁定' : '是否取消锁定';
                    } else {
                        message = state ? '是否展示' : '是否隐藏';
                    }
                    currentState = state;
                    currentSwitchTarget = $this;
                    $.dialog(message, confirmOperation, function () {
                    });
                    return false
                }
            });

        });

    });
</script>
[/@content]
