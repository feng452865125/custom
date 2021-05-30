[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]
[#include "../../component/modal.ftl"]

[@content title="第三方石头维护" subTitle=""]
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
                     <span class="input-group-addon">第三方公司</span>
                     <select class="form-control" name="company">
                         <option value="">请选择公司</option>
                             [#list companyList as company]
                                <option value="${company.shortName}">${company.shortName}</option>
                             [/#list]
                     </select>
                 </div>
                  <div class="input-group">
                      <span class="input-group-addon">证书号</span>
                      <input name="exZsBh" class="form-control" placeholder="请输入证书号查询">
                  </div>
                  <div class="input-group">
                      <span class="input-group-addon">克拉数(min)</span>
                      <input name="thirdStoneZlMin" class="form-control" placeholder="请输入克拉数查询">
                  </div>
                 <div class="input-group">
                     <span class="input-group-addon">克拉数(max)</span>
                     <input name="thirdStoneZlMax" class="form-control" placeholder="请输入克拉数查询">
                 </div>
                <div class="input-group">
                    <span class="input-group-addon">颜色</span>
                    <select class="form-control exZsYs" id="exZsYs" name="exZsYs">
                        <option value="">请选择颜色</option>
                                [#list zsYsList.keys as exZsYs]
                                    <option value="${exZsYs.label}">${exZsYs.label}</option>
                                [/#list]
                    </select>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">净度</span>
                    <select class="form-control exZsJd" id="exZsJd" name="exZsJd">
                        <option value="">请选择净度</option>
                                [#list zsJdList.keys as exZsJd]
                                    <option value="${exZsJd.label}">${exZsJd.label}</option>
                                [/#list]
                    </select>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">切工</span>
                    <select class="form-control exZsQg" id="exZsQg" name="exZsQg">
                        <option value="">请选择切工</option>
                                [#list zsQgList.keys as exZsQg]
                                    <option value="${exZsQg.label}">${exZsQg.label}</option>
                                [/#list]
                    </select>
                </div>
                 <div class="input-group">
                     <span class="input-group-addon">抛光</span>
                     <select class="form-control exZsPg" id="exZsPg" name="exZsPg">
                         <option value="">请选择抛光</option>
                                [#list zsPgList.keys as exZsPg]
                                    <option value="${exZsPg.label}">${exZsPg.label}</option>
                                [/#list]
                     </select>
                 </div>
                 <div class="input-group">
                     <span class="input-group-addon">对称</span>
                     <select class="form-control exZsDc" id="exZsDc" name="exZsDc">
                         <option value="">请选择对称</option>
                                [#list zsDcList.keys as exZsDc]
                                    <option value="${exZsDc.label}">${exZsDc.label}</option>
                                [/#list]
                     </select>
                 </div>
                <div class="input-group">
                    <span class="input-group-addon">荧光</span>
                    <select class="form-control exZsYg" id="exZsYg" name="exZsYg">
                        <option value="">请选择荧光</option>
                                [#list zsYgList.keys as exZsYg]
                                    <option value="${exZsYg.label}">${exZsYg.label}</option>
                                [/#list]
                    </select>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">同步日期</span>
                    <div class="input-group input-daterange">
                        <input class="form-control createDate" name="createDate" placeholder="请选择同步日期"
                               onchange="changeCreateDate()">
                    </div>
                </div>
                [/@searchable]
                <div>
            [@authorize ifAnyGranted="thirdStone:list"]
                <a class="btn btn-flat bg-red btn-parts-enable-over">结束今日操作</a>
                <a class="btn btn-flat bg-olive btn-parts-enable-outport" href="javascript:void(0);" download="">导出今日未操作的石头</a>
                <a class="btn btn-flat bg-olive btn-parts-enable-import">批量导入上架/下架（证书号）</a>
                <a class="btn btn-flat bg-olive btn-parts-enable-outport-all" href="javascript:void(0);" download="">导出所有已上架石头</a>
                <a class="btn btn-flat bg-olive btn-parts-keercom-import">根据模板批量导入石头（默认上架）</a>
                <a class="btn btn-flat bg-olive btn-parts-enable-outport-keercom-download"
                   href="../../download/importStone.xls" download="">石头导入模板下载</a>
            [/@authorize]
                </div>
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>证书号</th>
                        <th>证书</th>
                        <th>钻重</th>
                        <th>颜色</th>
                        <th>净度</th>
                        <th>切工</th>
                        <th>抛光</th>
                        <th>对称</th>
                        <th>荧光</th>
                        <th>公司简称</th>
                        <th>库存地</th>
                        <th>采购价</th>
                        <th>同步时间</th>
                        <th>上架状态</th>
                        <th>操作时间</th>
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

    [@modal id="modal-parts-enable-import" title="批量导入上架/下架（证书号）" confirmBtnClass="btn-parts-enable-import-sure"]
    <div>
        <div class="form-group">
            <span class="input-group-addon" style="text-align: left;">1、excel文件，证书号保证在第一列</span>
        </div>
        <div class="form-group">
            <span class="input-group-addon" style="text-align: left;">2、针对此次操作，选择上架/下架（文件内的所有石头）</span>
        </div>
        <div class="form-group">
            <span class="input-group-addon" style="text-align: left;">3、新同步石头默认下架，所以只需一次上架操作即可</span>
        </div>
        <div class="form-group">
            <select class="selecte-parts-enable">
                <option value="-1">请选择操作类型</option>
                <option value="1" selected>上架</option>
                <option value="0">下架</option>
            </select>
        </div>
        <div class="form-group">
            <span class="input-group-addon">文件选择</span>
            <input type="file" class="form-control" id="modalPartsEnableFile" name="modalPartsEnableFile"
                   accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
        </div>
        <div class="input-group modal-change-tip" style="color: #dd4b39;"></div>
    </div>
    [/@modal]


[#--批量导入keercom--]
    [@modal id="modal-keercom-import" title="批量导入裸石" confirmBtnClass="btn-parts-keercom-import-sure"]
    <div>
        <div class="form-group">
            <span class="input-group-addon" style="text-align: left;">1、请先下载石头导入模板文件</span>
        </div>
        <div class="form-group">
            <span class="input-group-addon" style="text-align: left;">2、请确保供应商缩写正确（KEERSAP、keercom等等）</span>
        </div>
        <div class="form-group">
            <span class="input-group-addon" style="text-align: left;">3、请保持上传文件中钻石属性的字段顺序不变</span>
        </div>
        <div class="form-group">
            <span class="input-group-addon">文件选择</span>
            <input type="file" class="form-control" id="modalPartsKeercomFile" name="modalPartsKeercomFile"
                   accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
        </div>
        <div class="input-group modal-change-keercom-tip" style="color: #dd4b39;"></div>
    </div>
    [/@modal]

<!-- DataTables -->
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/js/bootstrap-switch.js"></script>
[#--<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>--]
[#--<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>--]
<script src="${springMacroRequestContext.contextPath}/plugins/loading/loading.js"></script>
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/loading/loading.css">

<script>
    $(function () {
        var $form = $('#example');
        var dataTable = $form.DataTable({
            'stateSave': true,
            'stateSaveParams': function (settings, data) {
                data.search.search = "";
                if (data.columns[13].search.search != '') {
                    $('.form-control.createDate')[0].value = data.columns[13].search.search;
                }
                var date = $('.form-control.createDate')[0].value;
                if (date == null || date == '') {
                    date = "今日";
                }
                $('.btn-parts-enable-over')[0].text = "结束" + date + "操作";
                $('.btn-parts-enable-outport')[0].text = "导出" + date + "未操作的石头";
            },
            'ordering': true,
            'order': [12, 'desc'],
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {data: 'exZsBh', name: 'exZsBh'},
                {data: 'exZsZs', name: 'exZsZs'},
                {
                    data: 'exZsZl', name: 'exZsZl', render: function (data, type, full, meta) {
                        return data / 1000;
                    }
                },
                {data: 'exZsYs', name: 'exZsYs'},
                {data: 'exZsJd', name: 'exZsJd'},
                {data: 'exZsQg', name: 'exZsQg'},
                {data: 'exZsPg', name: 'exZsPg'},
                {data: 'exZsDc', name: 'exZsDc'},
                {data: 'exZsYg', name: 'exZsYg'},
                {data: 'company', name: 'company'},
                {data: 'location', name: 'location'},
                {
                    data: 'dollarPrice', name: 'dollarPrice', render: function (data, type, full, meta) {
                        if (full.company == 'CHINASTAR' || full.company == 'HB' || full.company == 'JP'
                                || full.company == 'PG' || full.company == 'DIAMART' || full.company == 'OPO'
                                || full.company == 'DHA' || full.company == 'KBS' || full.company == 'HB2'
                                || full.company == 'KDL') {
                            return data / 100 + "（美元）";
                        } else {
                            return data / 100 + "（人民币）";
                        }
                    }
                },
                {data: 'createDate', name: 'createDate'},
                {
                    data: 'enableStatus', name: 'enableStatus', render: function (data, type, full, meta) {
                        return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                    }
                },
                {data: 'enableOverDate', name: 'enableOverDate'},
                {
                    data: 'id', orderable: false, render: function (data, type, full, meta) {
                        var operations = [];
                    [@authorize ifAnyGranted="thirdStone:view"]
                        operations.push('<a redirect href="${springMacroRequestContext.contextPath}/thirdStone/view/' + data + '">详情</a>');
                    [/@authorize]
                        return operations.join(' | ');
                    }
                },
                {data: 'thirdStoneZlMin', name: 'thirdStoneZlMin', visible: false},
                {data: 'thirdStoneZlMax', name: 'thirdStoneZlMax', visible: false}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/thirdStone/pagination',
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
            var url = "${springMacroRequestContext.contextPath}/groupInfo/" + id;
            $.showRemoveDialog(url, '${_csrf.token}', function () {
                dataTable.draw();
            })
        })


        dataTable.on('draw.dt', function () {
            var currentState;
            var currentSwitchTarget;
            var $switch = $("input.enabled");
            var switchable = false;
            var confirmOperation = function () {
                var cb = function () {
                    switchable = true;
                    currentSwitchTarget.bootstrapSwitch('toggleState');
                    dataTable.draw();
                };
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/thirdStone/enabled/" + id,
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
                    $this = $(this);
                    id = $this.data('id');
                    var message = state ? '是否上架' : '是否下架';
                    currentState = state;
                    currentSwitchTarget = $this;
                    $.dialog(message, confirmOperation, function () {
                    });
                    return false
                }
            });

        });


        // 批量上传的弹窗，展示，上架下架导入
        $('.btn-parts-enable-import').click(function () {
            $('.modal-change-tip')[0].textContent = '';
            $("#modal-parts-enable-import").modal("show");
        })

        // 批量上传的弹窗，提交上传，上架下架导入
        var $modalPartsEnableImport = $('#modal-parts-enable-import');
        $modalPartsEnableImport.on('click', '.btn-parts-enable-import-sure', function (e) {
            e.preventDefault();
            var enableType = $('.selecte-parts-enable')[0].value;
            if (enableType == null || enableType < 0) {
                $('.modal-change-tip')[0].textContent = '请选择上架，或者下架';
                return;
            }
            var $modalPartsEnableFile = $('#modalPartsEnableFile');
            if ($modalPartsEnableFile == null || $modalPartsEnableFile.val().length == 0) {
                $('.modal-change-tip')[0].textContent = '请选择文件';
                return;
            }
            $('.btn-parts-enable-import-sure').attr("disabled", true);
            initLoading({type: "start"});
            var formData = new FormData();//初始化一个FormData对象
            formData.append("file", $modalPartsEnableFile[0].files[0]);//将文件塞入FormData
            formData.append("enableType", enableType);//上架下架类型
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/thirdStone/import",
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
                    initLoading({type: "stop"});
                    $('.btn-parts-enable-import-sure').attr("disabled", false);
                    $modalPartsEnableImport.modal("hide");
                    dataTable.draw();
                    $.notification.success(data);
                },
                error: function (data) {
                    initLoading({type: "stop"});
                    $('.btn-parts-enable-import-sure').attr("disabled", false);
                    $.notification.error(data.responseText);
                }
            })
        })


        //结束操作
        $('.btn-parts-enable-over').click(function () {
            var data = $form.serializeObject();
            data.enableOverDate = $('.createDate').val();
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/thirdStone/enableOver",
                dataType: "text",
                data: JSON.stringify(data),
                type: "POST",
                contentType: "application/json",
                headers: {
                    "X-CSRF-TOKEN": "${_csrf.token}",
                }
            }).done(function (data) {
                dataTable.draw();
                $.notification.success(data);
            }).fail(function (error) {
                dataTable.draw();
                $.notification.error(error.responseText);
            });
        })


        //导出未操作的石头
        $('.btn-parts-enable-outport').click(function () {
            var date = $('.form-control.createDate')[0].value;
            if (date == null || date == '') {
                var myDate = new Date();
                var year = myDate.getFullYear(); //获取当前年
                var mon = myDate.getMonth() + 1; //获取当前月
                var day = myDate.getDate(); //获取当前日
                date = year + "-" + mon + "-" + day;
            }
            var href = "${springMacroRequestContext.contextPath}/thirdStone/outport/" + date;
            window.location.href = href;
            return null;
        })

        //导出全部已上架的
        $('.btn-parts-enable-outport-all').click(function () {
            var href = "${springMacroRequestContext.contextPath}/thirdStone/outport/allLockEnable";
            window.location.href = href;
            return null;
        })

        // 时间选择初始化
        $('.input-daterange').find('input').each(function () {
            $(this).datepicker("setDate", new Date());
        });


        // 批量上传的弹窗，展示，批量导入keercom
        $('.btn-parts-keercom-import').click(function () {
            $('.modal-change-keercom-tip')[0].textContent = '';
            $("#modal-keercom-import").modal("show");
        })

        // 批量上传的弹窗，提交上传，批量导入keercom
        var $modalKeercomImport = $('#modal-keercom-import');
        $modalKeercomImport.on('click', '.btn-parts-keercom-import-sure', function (e) {
            e.preventDefault();
            var $modalPartsKeercomFile = $('#modalPartsKeercomFile');
            if ($modalPartsKeercomFile == null || $modalPartsKeercomFile.val().length == 0) {
                $('.modal-change-keercom-tip')[0].textContent = '请选择文件';
                return;
            }
            $('.btn-parts-keercom-import-sure').attr("disabled", true);
            initLoading({type: "start"});
            var formData = new FormData();//初始化一个FormData对象
            formData.append("file", $modalPartsKeercomFile[0].files[0]);//将文件塞入FormData
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/thirdStone/importStone",
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
                    initLoading({type: "stop"});
                    $('.btn-parts-keercom-import-sure').attr("disabled", false);
                    $modalKeercomImport.modal("hide");
                    dataTable.draw();
                    $.notification.success(data);
                },
                error: function (data) {
                    initLoading({type: "stop"});
                    $('.btn-parts-keercom-import-sure').attr("disabled", false);
                    $.notification.error(data.responseText);
                }
            })
        })


    });

    // 时间选择
    function changeCreateDate() {
        var date = $('.form-control.createDate')[0].value;
        if (date == null || date == '') {
            date = "今日";
        }
        $('.btn-parts-enable-over')[0].text = "结束" + date + "操作";
        $('.btn-parts-enable-outport')[0].text = "导出" + date + "未操作的石头";
    }


    function sleep(delay) {
        //delay表示的毫秒数
        var start = (new Date()).getTime();
        while ((new Date()).getTime() - start < delay) {
            continue;
        }
    }

</script>
[/@content]
