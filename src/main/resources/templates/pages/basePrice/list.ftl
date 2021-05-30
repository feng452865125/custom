[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]
[#include "../../component/modal.ftl"]

[@content title="4C标准基价维护" subTitle=""]
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
                        <span class="input-group-addon">克拉数</span>
                        <input name="klMin" class="form-control klMin" placeholder="请输入克拉数查询">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">颜色</span>
                        <select class="form-control ys" id="ys" name="ys">
                            <option value="">请选择颜色</option>
                                [#list ysList.keys as ys]
                                    <option value="${ys.label}">${ys.label}</option>
                                [/#list]
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">净度</span>
                        <select class="form-control jd" id="jd" name="jd">
                            <option value="">请选择净度</option>
                                [#list jdList.keys as jd]
                                    <option value="${jd.label}">${jd.label}</option>
                                [/#list]
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">切工</span>
                        <select class="form-control qg" id="qg" name="qg">
                            <option value="">请选择切工</option>
                                [#list qgList.keys as qg]
                                    <option value="${qg.label}">${qg.label}</option>
                                [/#list]
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">抛光</span>
                        <select class="form-control pg" id="pg" name="pg">
                            <option value="">请选择抛光</option>
                                [#list pgList.keys as pg]
                                    <option value="${pg.label}">${pg.label}</option>
                                [/#list]
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">对称</span>
                        <select class="form-control dc" id="dc" name="dc">
                            <option value="">请选择对称</option>
                                [#list dcList.keys as dc]
                                    <option value="${dc.label}">${dc.label}</option>
                                [/#list]
                        </select>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">荧光</span>
                        <select class="form-control yg" id="yg" name="yg">
                            <option value="">请选择荧光</option>
                                [#list ygList.keys as yg]
                                    <option value="${yg.label}">${yg.label}</option>
                                [/#list]
                        </select>
                    </div>
                [/@searchable]
                <div>
                    [@authorize ifAnyGranted="basePrice:add"]
                        <a class="btn btn-flat bg-olive" href="${springMacroRequestContext.contextPath}/basePrice/add"
                           redirect>新增</a>
                        <a class="btn btn-flat bg-olive btn-basePrice-import">批量导入</a>
                        <a class="btn btn-flat bg-olive btn-basePrice-download"
                           href="../../download/importBasePrice.xls" download="">模板下载</a>
                    [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>克拉分数段</th>
                        <th>颜色</th>
                        <th>净度</th>
                        <th>切工</th>
                        <th>抛光</th>
                        <th>对称</th>
                        <th>荧光</th>
                        <th>证书</th>
                        <th>价格</th>
                        <th>展示</th>
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
    [@modal id="modal-basePrice-import" title="批量导入4C标准基价" confirmBtnClass="btn-basePrice-import-sure"]
        <div>
            <div class="form-group">
                <span class="input-group-addon" style="text-align: left;">1、请根据模板上传导入文件</span>
            </div>
            <div class="form-group">
                <span class="input-group-addon" style="text-align: left;">2、请保持文件中钻石属性的字段顺序不变</span>
            </div>
            <div class="form-group">
                <span class="input-group-addon">文件选择</span>
                <input type="file" class="form-control" id="modalBasePriceFile" name="modalBasePriceFile"
                       accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
            </div>
            <div class="input-group modal-change-tip" style="color: #dd4b39;"></div>
        </div>
    [/@modal]

<!-- DataTables -->
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/js/bootstrap-switch.js"></script>
<script>
    $(function () {
        var $form = $('#example');
        var dataTable = $form.DataTable({
            'stateSave': true,
            'stateSaveParams': function (settings, data) {
                data.search.search = "";
                $('.form-control.klMin')[0].value = data.columns[2].search.search;
                $('.form-control.ys')[0].value = data.columns[3].search.search;
                $('.form-control.jd')[0].value = data.columns[4].search.search;
                $('.form-control.qg')[0].value = data.columns[5].search.search;
                $('.form-control.pg')[0].value = data.columns[6].search.search;
                $('.form-control.dc')[0].value = data.columns[7].search.search;
                $('.form-control.yg')[0].value = data.columns[8].search.search;
            },
            'ordering': true,
            'order': [10, 'desc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {data: 'name', name: 'name'},
                {
                    data: 'klMin', name: 'klMin', render: function (data, type, full, meta) {
                        if (full.klMin != null && full.klMax != null) {
                            return full.klMin / 1000 + " ~ " + full.klMax / 1000;
                        } else {
                            return "";
                        }
                    }
                },
                {data: 'ys', name: 'ys'},
                {data: 'jd', name: 'jd'},
                {data: 'qg', name: 'qg'},
                {data: 'pg', name: 'pg'},
                {data: 'dc', name: 'dc'},
                {data: 'yg', name: 'yg'},
                {data: 'zs', name: 'zs'},
                {
                    data: 'price', name: 'price', render: function (data, type, full, meta) {
                        return data / 100;
                    }
                },
                {
                    data: 'status', name: 'status', render: function (data, type, full, meta) {
                        return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                    }
                },
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [@authorize ifAnyGranted="basePrice:view"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/basePrice/view/' + data + '">详情</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="basePrice:edit"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/basePrice/edit/' + data + '">编辑</a>');
                    [/@authorize]
                    [@authorize ifAnyGranted="basePrice:delete"]
                        operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')
                    [/@authorize]
                        return operations.join(' | ');
                    }
                }
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/basePrice/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/basePrice/" + id;
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
                    url: "${springMacroRequestContext.contextPath}/basePrice/" + id + "/" + extUrl,
                    type: 'PATCH',
                    data: {${_csrf.parameterName}:
                "${_csrf.token}"
            },
                success: cb,
                        error
            :

                function (error) {
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
                        message = state ? '是否开启合作' : '是否关闭合作';
                    }
                    currentState = state;
                    currentSwitchTarget = $this;
                    $.dialog(message, confirmOperation, function () {
                    });
                    return false
                }
            });

        });




        // 批量上传的弹窗，展示
        $('.btn-basePrice-import').click(function () {
            $('.modal-change-tip')[0].textContent = '';
            $("#modal-basePrice-import").modal("show");
        })

        // 批量上传的弹窗，提交上传
        var $modalCompanyImport = $('#modal-basePrice-import');
        $modalCompanyImport.on('click', '.btn-basePrice-import-sure', function (e) {
            e.preventDefault();
            var $modalBasePriceFile = $('#modalBasePriceFile');
            if ($modalBasePriceFile == null || $modalBasePriceFile.val().length == 0) {
                $('.modal-change-tip')[0].textContent = '请选择文件';
                return;
            }
            var formData = new FormData();//初始化一个FormData对象
            formData.append("file", $modalBasePriceFile[0].files[0]);//将文件塞入FormData
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/basePrice/import",
                data: formData,
                async: false,
                dataType: "text",
                cache: false,
                processData: false,  // 告诉jQuery不要去处理发送的数据
                contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
                type: "POST",
                headers: {
                    "X-CSRF-TOKEN": "${_csrf.token}",
                },
                success: function (data) {
                    $modalCompanyImport.modal("hide");
                    dataTable.draw();
                    $.notification.success(data);
                },
                error: function (data) {
                    $.notification.error(data.responseText);
                }
            })
        })


    });
</script>
[/@content]
