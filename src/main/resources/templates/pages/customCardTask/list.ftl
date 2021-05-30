[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="制卡任务维护" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">
<link rel="stylesheet"
      href="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/css/bootstrap3/bootstrap-switch.css"
      type="text/css"/>

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
                        <span class="input-group-addon">任务名称</span>
                        <input name="cardTaskName" class="form-control" placeholder="请输入任务名称查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">创建人</span>
                        <input name="cardTaskCreateUser" class="form-control" placeholder="请输入创建人查询">
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
                        <select class="form-control cardTaskStatus" name="cardTaskStatus">
                            <option value="">全部</option>
                             [#list statusList.keys as status]
                                    <option value="${status.key}">${status.label}</option>
                             [/#list]
                        </select>
                    </div>

                [/@searchable]
                <div>
                    [@authorize ifAnyGranted="customCardTask:add"]
                        <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/customCardTask/add"
                           redirect>新建制卡任务</a>
                    [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>任务名称</th>
                        <th>卡片数量</th>
                        <th>合作方</th>
                        <th>卡号段</th>
                        <th>卡片面额</th>
                        <th>启用/禁用</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>任务状态</th>
                        <th>截止使用日期</th>
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
<script src="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/js/bootstrap-switch.js"></script>
<script>
    $(function () {
        var $form = $('#example');
        var dataTable = $form.DataTable({
            'ordering': true,
            'order' : [8,'desc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {data: 'cardTaskName', name: 'cardTaskName'},
                {data: 'cardTaskCount', name: 'cardTaskCount'},
                {data: 'cardTaskCompany', name: 'cardTaskCompany'},
                {data: 'cardTaskCode', name: 'cardTaskCode'},
                {data: 'cardTaskPrice', name: 'cardTaskPrice'},
                {
                    data: 'cardTaskEnable', name: 'cardTaskEnable', render: function (data, type, full, meta) {
                        return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                    }
                },
                {data: 'cardTaskCreateUser', name: 'cardTaskCreateUser'},
                {data: 'createDate', name: 'createDate'},
                {data: 'cardTaskStatusName', name: 'cardTaskStatusName'},
                {data: 'cardTaskLastDate', name: 'cardTaskLastDate'},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [#--[@authorize ifAnyGranted="customCardTask:view"]--]
                    [#--operations.push('<a redirect href="${springMacroRequestContext.contextPath}/customCardTask/view/' + data + '">详情</a>');--]
                    [#--[/@authorize]--]
                    [#--[@authorize ifAnyGranted="customCardTask:edit"]--]
                    [#--operations.push('<a redirect href="${springMacroRequestContext.contextPath}/customCardTask/edit/' + data + '">编辑</a>');--]
                    [#--[/@authorize]--]
                    [@authorize ifAnyGranted="customCardTask:edit"]
                        operations.push('<a class="btn-exportCard" href="javascript:;" data-id="' + data + '">导出卡片</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="customCardTask:edit"]
                        if (full.cardTaskStatus == 0) {
                            operations.push('<a class="btn-finishCard" href="javascript:;" data-id="' + data + '">完成制卡</a>');
                        }
                    [/@authorize]
                    [#--[@authorize ifAnyGranted="customCardTask:delete"]--]
                    [#--operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')--]
                    [#--[/@authorize]--]
                        return operations.join(' | ');
                    }
                },
                {data: 'startDate', name: 'startDate', visible: false},
                {data: 'endDate', name: 'endDate', visible: false},
                {data: 'cardTaskStatus', name: 'cardTaskStatus', visible: false}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/customCardTask/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/customCardTask/" + id;
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
                var extUrl;
                if (currentSwitchTarget.attr('class') === 'locked') {
                    extUrl = currentState ? 'lock' : 'unlocked';
                } else {
                    extUrl = currentState ? 'enabled' : 'disabled';
                }
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/customCardTask/" + id + "/" + extUrl,
                    type: 'PATCH',
                    data: {${_csrf.parameterName}:
                "${_csrf.token}"
            },
                success: function () {
                    switchable = true;
                    currentSwitchTarget.bootstrapSwitch('toggleState');
                }
            ,
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
                        message = state ? '是否启用' : '是否禁用';
                    }
                    currentState = state;
                    currentSwitchTarget = $this;
                    $.dialog(message, confirmOperation, function () {
                    });
                    return false
                }
            });

        });


        $('.input-daterange').datepicker({
            language: "zh-CN",
            autoclose: true,
            clearBtn: true,
            format: "yyyy-mm-dd"
        });


        //导出卡片
        $form.on('click', '.btn-exportCard', function () {
            var $this = $(this);
            var id = $this.data('id');
            var href = "${springMacroRequestContext.contextPath}/customCardTask/exportCard/" + id;
            window.location.href = href;
            return null;
        })


        //完成制卡
        $form.on('click', '.btn-finishCard', function () {
            var $this = $(this);
            var id = $this.data('id');
            var url = "${springMacroRequestContext.contextPath}/customCardTask/finishCardTask/" + id;
            $.showSureDialog(url, '${_csrf.token}', function () {
                dataTable.draw(false);
            })
        })


    });
</script>
[/@content]
