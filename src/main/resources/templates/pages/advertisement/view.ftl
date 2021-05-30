[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]

[@content title="广告栏维护" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet"
      href="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/css/bootstrap3/bootstrap-switch.css"
      type="text/css"/>
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">

<div class="row">
    <!-- left column -->
    <div class="col-md-12">
        <!-- general form elements -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">详情</h3>
            </div>
            <!-- /.series-header -->
            <!-- form start -->
            <div class="box-body">
                <label style="margin-left: 30px;">标题展示区</label>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-7">
                        <div class="form-group">
                            <label for="jumpTitleImgUrl">图片文件（推荐尺寸：2048*600）</label>
                            <input class="form-control jumpTitleImg" name="jumpTitleImgUrl"
                                   id="jumpTitleImgUrl" value="${advertisement.jumpTitleImgUrl}">
                            <label class="bc_upload">上传
                                <input type="file" style="display: none;"
                                       accept="image/png, image/jpeg, image/jpg"
                                       onchange="changePic(this, jumpTitleImgUrl)"/>
                            </label>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-1">
                        <div class="form-group">
                            <i class="fa fa-times bc_upload_tip_right_null tip_fail"></i>
                                    [#if advertisement.jumpTitleImgUrl?? && advertisement.jumpTitleImgUrl?length > 0]
                                        <i class="fa fa-check bc_upload_tip_right_null tip_success"
                                           style="display: block"></i>
                                    [#else]
                                        <i class="fa fa-check bc_upload_tip_right_null tip_success"></i>
                                    [/#if]
                            <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="jumpTitleRemark">文件描述</label>
                            <textarea class="form-control remark_style" id="jumpTitleRemark" name="jumpTitleRemark"
                                      placeholder="文件描述">${advertisement.jumpTitleRemark}</textarea>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-4">
                        <div class="from-group">
                            <label for="jumpTitleImgUrl">配图</label>
                            <div style="height: 108px;">
                                     [#if advertisement.jumpTitleImgUrl?? && advertisement.jumpTitleImgUrl?length > 0]
                                         <img src="${advertisement.jumpTitleImgUrl}" class="img_shadow jumpTitleImg"
                                              style="height: 100%;">
                                     [#else]
                                        <img src="${advertisement.jumpTitleImgUrl}" class="jumpTitleImg">
                                     [/#if]
                            </div>
                        </div>
                    </div>
                </div>

                <label style="margin-left: 30px;">内容展示区</label>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-7">
                        <div class="form-group">
                            <label for="jumpImgUrl">图片文件（推荐尺寸：1800*480）</label>
                            <input class="form-control jumpContentImg" name="jumpContentImgUrl"
                                   id="jumpContentImgUrl" value="${advertisement.jumpContentImgUrl}">
                            <label class="bc_upload">上传
                                <input type="file" style="display: none;"
                                       accept="image/png, image/jpeg, image/jpg"
                                       onchange="changePic(this, jumpContentImgUrl)"/>
                            </label>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-1">
                        <div class="form-group">
                            <i class="fa fa-times bc_upload_tip_right_null tip_fail"></i>
                                    [#if advertisement.jumpContentImgUrl?? && advertisement.jumpContentImgUrl?length > 0]
                                        <i class="fa fa-check bc_upload_tip_right_null tip_success"
                                           style="display: block"></i>
                                    [#else]
                                        <i class="fa fa-check bc_upload_tip_right_null tip_success"></i>
                                    [/#if]
                            <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="jumpContentRemark">文件描述</label>
                            <textarea class="form-control remark_style" id="jumpContentRemark" name="jumpContentRemark"
                                      placeholder="文件描述">${advertisement.jumpContentRemark}</textarea>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-4">
                        <div class="from-group">
                            <label for="jumpContentImgUrl">配图</label>
                            <div style="height: 108px;">
                                    [#if advertisement.jumpContentImgUrl?? && advertisement.jumpContentImgUrl?length > 0]
                                        <img src="${advertisement.jumpContentImgUrl}" class="img_shadow jumpContentImg"
                                             style="height: 100%;">
                                    [#else]
                                        <img src="${advertisement.jumpContentImgUrl}" class="jumpContentImg">
                                    [/#if]
                            </div>
                        </div>
                    </div>
                </div>

                <label style="margin-left: 30px;">视频展示区</label>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-7">
                        <div class="form-group">
                            <label for="jumpVideoUrl">视频文件（推荐尺寸：16:9）</label>
                            <input class="form-control jumpVideo" name="jumpVideoUrl"
                                   id="jumpVideoUrl" value="${advertisement.jumpVideoUrl}">
                            <label class="bc_upload">上传
                                <input type="file" style="display: none;" accept="video/*"
                                       onchange="changePic(this, jumpVideoUrl)"/>
                            </label>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-1">
                        <div class="form-group">
                            <i class="fa fa-times bc_upload_tip_right_null tip_fail"></i>
                                    [#if advertisement.jumpVideoUrl?? && advertisement.jumpVideoUrl?length > 0]
                                        <i class="fa fa-check bc_upload_tip_right_null tip_success"
                                           style="display: block"></i>
                                    [#else]
                                        <i class="fa fa-check bc_upload_tip_right_null tip_success"></i>
                                    [/#if]
                            <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="jumpVideoRemark">文件描述</label>
                            <textarea class="form-control remark_style" id="jumpVideoRemark" name="jumpVideoRemark"
                                      placeholder="文件描述">${advertisement.jumpVideoRemark}</textarea>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-4">
                        <div class="from-group">
                            <label for="jumpVideoUrl">配图</label>
                            <div style="height: 108px;">
                                    [#if advertisement.jumpVideoUrl?? && advertisement.jumpVideoUrl?length > 0]
                                    <video src="${advertisement.jumpVideoUrl}" controls="controls"
                                           class="img_shadow jumpVideo" style="height: 100%;">
                                    [#else]
                                        <video src="${advertisement.jumpVideoUrl}" class="jumpVideo">
                                    [/#if]
                            </div>
                        </div>
                    </div>
                </div>

                <label style="margin-left: 30px;">样式展示区</label>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-7">
                            [@searchable id="searchable" targetId="example"]
                            <div class="input-group">
                                <span class="input-group-addon">系列</span>
                                <select class="form-control" name="series">
                                    <option value="">请选择</option>
                                        [#list seriesList as series]
                                        <option value="${series.name}">${series.name}</option>
                                        [/#list]
                                </select>
                            </div>
                            <div class="input-group">
                                <span class="input-group-addon">样式名称</span>
                                <input name="name" class="form-control" placeholder="请输入名称查询">
                            </div>
                            [/@searchable]
                        <table id="example" class="table table-bordered table-hover ad_view">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>样式编码</th>
                                <th>样式名称</th>
                                <th>所属系列</th>
                                <th>产品描述</th>
                                <th>是否绑定</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- /.series-body -->

            <div class="box-footer">
                <div class="row">
                    <div class="col-xs-offset-1 col-md-1">
                        <button type="submit" class="btn btn-primary btn-submit">提交</button>
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-default btn-back">返回</button>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.series -->
    </div>
    <!--/.col (left) -->
</div>
<!-- /.row -->
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/js/bootstrap-switch.js"></script>
<script>
    $(function () {
        var $form = $('#example');
        var dataTable = $form.DataTable({
            'ordering': true,
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
                {data: 'id', name: 'id', orderable: false},
                {data: 'name', name: 'name', orderable: false},
                {data: 'series', name: 'series', orderable: false},
                {data: 'remark', name: 'remark', orderable: false},
                {
                    data: 'enabled', name: 'enabled', orderable: false, render: function (data, type, full, meta) {
                        return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                    }
                }
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/advertisement/style/select/' + ${advertisement.id},
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

        dataTable.on('draw.dt', function () {
            var $switch = $("input.enabled");
            var switchable = false;
            $switch.bootstrapSwitch({
                onSwitchChange: function (e, state) {
                    if (switchable) {
                        switchable = false;
                        return true;
                    }
                    ;
                    var data = $form.serializeObject();
                    $this = $(this);
                    data.styleId = $this.data('id');
                    data.advertisementId = ${advertisement.id};
                    var extUrl;
                    extUrl = state ? 'enabled' : 'disabled';
                    $.ajax({
                        url: "${springMacroRequestContext.contextPath}/advertisement/style/" + extUrl,
                        type: 'PATCH',
                        data: JSON.stringify(data),
                        contentType: "application/json",
                        headers: {
                            "X-CSRF-TOKEN": "${_csrf.token}",
                        },
                        success: function () {
                            switchable = true;
                            dataTable.draw();
                        },
                        error: function (error) {
                            $.notification.error(error.responseText);
                        }
                    })
                    return false
                }
            });

        });

        //编辑修改
        $('.box-footer .btn-submit').on('click', function () {
            var data = $('.box-body').serializeObject();
            data.jumpTitleImgUrl = $('.jumpTitleImg').val();
            data.jumpTitleRemark = $('#jumpTitleRemark').val();
            data.jumpContentImgUrl = $('.jumpContentImg').val();
            data.jumpContentRemark = $('#jumpContentRemark').val();
            data.jumpVideoUrl = $('.jumpVideo').val();
            data.jumpVideoRemark = $('#jumpVideoRemark').val();
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/advertisement/detail/${advertisement.id}",
                type: 'PUT',
                data: JSON.stringify(data),
                contentType: "application/json",
                headers: {
                    "X-CSRF-TOKEN": "${_csrf.token}",
                },
                success: function () {
                    $.redirect("${springMacroRequestContext.contextPath}/advertisement/list", function () {

                    });
                },
                error: function (error) {
                    $.notification.error(error.responseText);
                }
            })
        })

    });

    //选择图片
    function changePic(target, id) {
        var parent = $(target).parent().parent().parent().parent()[0];
        parent.getElementsByClassName('tip_success')[0].style.display = 'none';
        parent.getElementsByClassName('tip_fail')[0].style.display = 'none';
        parent.getElementsByClassName('tip_loading')[0].style.display = 'block';
        var formData = new FormData();//初始化一个FormData对象
        formData.append("file", target.files[0]);//将文件塞入FormData
        $.ajax({
            url: "${springMacroRequestContext.contextPath}/api/upload?dir=advertisement",
            type: "POST",
            data: formData,
            processData: false,  // 告诉jQuery不要去处理发送的数据
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            headers: {"X-CSRF-TOKEN": '${_csrf.token}'},
            success: function (data) {
                if (data.length == 0) {
                    $.notification.error("上传失败，请重试");
                    return
                }
                $(id).val(data);
                if ($(id).attr('class') === 'form-control jumpTitleImg') {
                    $($('.jumpTitleImg')[1]).addClass('img_shadow');
                    $('.jumpTitleImg')[1].style.height = '100%';
                    $('.jumpTitleImg')[1].src = data;
                } else if ($(id).attr('class') === 'form-control jumpContentImg'){
                    $($('.jumpContentImg')[1]).addClass('img_shadow');
                    $('.jumpContentImg')[1].style.height = '100%';
                    $('.jumpContentImg')[1].src = data;
                } else if ($(id).attr('class') === 'form-control jumpVideo') {
                    $($('.jumpVideo')[1]).addClass('img_shadow');
                    $('.jumpVideo')[1].style.height = '100%';
                    $('.jumpVideo')[1].src = data;
                    $('.jumpVideo')[1].controls= 'controls';
                }
                parent.getElementsByClassName('tip_loading')[0].style.display = 'none';
                parent.getElementsByClassName('tip_success')[0].style.display = 'block';
            },
            error: function (data) {
                parent.getElementsByClassName('tip_loading')[0].style.display = 'none';
                parent.getElementsByClassName('tip_fail')[0].style.display = 'block';
            }
        });

    };

</script>


<script type="text/template" id="relStyleTemplate">
    {{#each this}}
    <tr>
        <td>{{code}}</td>
        <td>{{name}}</td>
        <td>{{series}}</td>
        <td>{{remark}}</td>
        <td style="text-align: center;">
            {{#if enabled}}
            <input type="checkbox" class="enabled" data-id="{{id}}" data-size="mini" checked>
            {{else}}
            <input type="checkbox" class="enabled" data-id="{{id}}" data-size="mini">
            {{/if}}
        </td>
    </tr>
    {{/each}}
</script>
[/@content]
