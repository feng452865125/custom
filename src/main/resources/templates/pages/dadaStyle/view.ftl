[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]

[@content title="dada样式维护" subTitle=""]
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
                <label style="margin-left: 30px;">基本设置</label>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="id">样式编码</label>
                            <input class="form-control" name="code" id="code"
                                   value="${dadaStyle.code}" disabled
                            >
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="name">样式名称</label>
                            <input class="form-control" name="name" id="name"
                                   value="${dadaStyle.name}" disabled
                            >
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="name">系列</label>
                            <input class="form-control" name="series" id="series"
                                   value="${dadaStyle.series}" disabled
                            >
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="name">佩戴类型</label>
                            <input class="form-control" name="typeName" id="typeName"
                                   value="${dadaStyle.typeName}" disabled
                            >
                        </div>
                    </div>
                </div>

                    [#if dadaStyle.imgsUrlList ??]
                        [#list dadaStyle.imgsUrlList as imgsUrl]
                            [#if imgsUrl?index == 0]
                                <div class="row imgsUrlList">
                                    <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                        <div class="form-group">
                                            <label for="imgsUrl">图片文件（推荐尺寸：1000*750）</label>
                                            <input class="form-control" name="imgsUrl${imgsUrl?index}"
                                                   id="imgsUrl${imgsUrl?index}" value="${imgsUrl}">
                                            <label class="bc_upload">上传
                                                <input type="file" style="display: none;"
                                                       accept="image/png, image/jpeg, image/jpg"
                                                       onchange="changePic(this, imgsUrl${imgsUrl?index})"/>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-xs-5 col-md-1">
                                        <div class="form-group">
                                            <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                            <i class="fa fa-check bc_upload_tip tip_success" style="display: block"></i>
                                            <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                                            <label style="height: 40px;"></label>
                                            <button type="button" class="btn btn-info btn-add"
                                                    style="margin-left: -3px;" onclick="addImgUrlList(this)">添加
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            [#else]
                                <div class="row imgsUrlList">
                                    <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                        <div class="form-group">
                                            <input class="form-control" name="imgsUrl${imgsUrl?index}"
                                                   id="imgsUrl${imgsUrl?index}" value="${imgsUrl}">
                                            <label class="bc_upload" style="top: 1px;">上传
                                                <input type="file" style="display: none;"
                                                       accept="image/png, image/jpeg, image/jpg"
                                                       onchange="changePic(this, imgsUrl${imgsUrl?index})"/>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-xs-5 col-md-1">
                                        <div class="form-group">
                                            <i class="fa fa-times bc_upload_tip_top8 tip_fail"></i>
                                            <i class="fa fa-check bc_upload_tip_top8 tip_success"
                                               style="display: block"></i>
                                            <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip_top8 tip_loading"></i>
                                            <button type="button" class="btn btn-info btn-remove"
                                                    onclick="removeImgUrlList(this)">删除
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            [/#if]
                        [/#list]
                    [#else]
                        <div class="row imgsUrlList">
                            <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                <div class="form-group">
                                    <label for="imgsUrl">图片文件（推荐尺寸：1000*750）</label>
                                    <input class="form-control" name="imgsUrl1" id="imgsUrl1">
                                    <label class="bc_upload">上传
                                        <input type="file" style="display: none;"
                                               accept="image/png, image/jpeg, image/jpg"
                                               onchange="changePic(this, imgsUrl1)"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-1">
                                <div class="form-group">
                                    <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                    <i class="fa fa-check bc_upload_tip tip_success"></i>
                                    <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                                    <label style="height: 40px;"></label>
                                    <button type="button" class="btn btn-info btn-add" style="margin-left: -3px;"
                                            onclick="addImgUrlList(this)">添加
                                    </button>
                                </div>
                            </div>
                        </div>
                    [/#if]

                [#--<div class="row">--]
                    [#--<div class="col-xs-offset-1 col-xs-5 col-md-7">--]
                        [#--<div class="form-group">--]
                            [#--<label for="videoUrl">视频文件（推荐尺寸：16:9）</label>--]
                            [#--<input class="form-control" name="videoUrl" id="videoUrl" value="${dadaStyle.videoUrl}">--]
                            [#--<label class="bc_upload">上传--]
                                [#--<input type="file" style="display: none;" accept="video/*"--]
                                       [#--onchange="changePic(this, videoUrl)"/>--]
                            [#--</label>--]
                        [#--</div>--]
                    [#--</div>--]
                    [#--<div class="col-xs-5 col-md-1">--]
                        [#--<div class="form-group">--]
                            [#--<i class="fa fa-times bc_upload_tip tip_fail"></i>--]
                            [#--<i class="fa fa-check bc_upload_tip tip_success"--]
                                 [#--[#if dadaStyle.videoUrl?length > 0]--]
                                    [#--style="display: block"--]
                                 [#--[/#if]--]
                            [#--></i>--]
                            [#--<i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>--]
                        [#--</div>--]
                    [#--</div>--]
                [#--</div>--]
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-7">
                        <div class="form-group">
                            <label for="wearUrl">试戴文件（推荐尺寸：140*140--暂定）</label>
                            <input class="form-control" name="wearUrl" id="wearUrl" value="${dadaStyle.wearUrl}">
                            <label class="bc_upload">上传
                                <input type="file" style="display: none;" accept="image/png, image/jpeg, image/jpg"
                                       onchange="changePic(this, wearUrl)"/>
                            </label>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-1">
                        <div class="form-group">
                            <i class="fa fa-times bc_upload_tip tip_fail"></i>
                            <i class="fa fa-check bc_upload_tip tip_success"
                                 [#if dadaStyle.wearUrl?? && dadaStyle.wearUrl?length > 0]
                                    style="display: block"
                                 [/#if]
                            ></i>
                            <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="moral">寓意</label>
                            <textarea class="form-control remark_style" id="moral"
                                      name="moral" placeholder="寓意">${dadaStyle.moral}</textarea>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="remark">描述</label>
                            <textarea class="form-control remark_style" id="remark"
                                      name="remark" placeholder="描述">${dadaStyle.remark}</textarea>
                        </div>
                    </div>
                </div>

                <label style="margin-left: 30px;">包含组件</label>
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-8">
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
                                <span class="input-group-addon">佩戴类别</span>
                                <select class="form-control" name="type">
                                    <option value="">请选择</option>
                                        [#list typeList as type]
                                            <option value="${type.id}">${type.name}</option>
                                        [/#list]
                                </select>
                            </div>
                            <div class="input-group">
                                <span class="input-group-addon">编码</span>
                                <input name="code" class="form-control" placeholder="请输入编码">
                            </div>
                            <div class="input-group">
                                <span class="input-group-addon">寓意</span>
                                <input name="exYy" class="form-control" placeholder="请输入寓意">
                            </div>
                            [/@searchable]
                        <table id="example" class="table table-bordered table-hover table_style table_img_style dada_style_view">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th class="img_td">配图</th>
                                <th>编码</th>
                                <th>名称</th>
                                <th>系列</th>
                                <th>佩戴类别</th>
                                <th>寓意</th>
                                <th>组合</th>
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
        var $example = $('#example');
        var dataTable = $example.DataTable({
            'ordering': true,
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'id', name: 'id', orderable: false, visible: false},
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
                {data: 'series', name: 'series', orderable: false},
                {data: 'typeName', name: 'typeName', orderable: false},
                {data: 'exYy', name: 'exYy', orderable: false},
                {
                    data: 'enabled', name: 'enabled', orderable: false, render: function (data, type, full, meta) {
                        return data ?
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini" checked>'
                                :
                                '<input type="checkbox" class="enabled" data-id="' + full.id + '" data-size="mini">'
                    }
                },
                {data: 'type', name: 'type', visible: false},
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/dadaStyle/parts/select/' + ${dadaStyle.id},
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
                    var data = $example.serializeObject();
                    $this = $(this);
                    data.partsId = $this.data('id');
                    data.styleId = ${dadaStyle.id};
                    var extUrl;
                    extUrl = state ? 'enabled' : 'disabled';
                    $.ajax({
                        url: "${springMacroRequestContext.contextPath}/dadaStyle/parts/" + extUrl,
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

        $('.box-footer .btn-submit').on('click', function () {
            var data = $('.box-body').serializeObject();
            // data.videoUrl = $('#videoUrl').val();
            data.wearUrl = $('#wearUrl').val();
            data.moral = $('#moral').val();
            data.remark = $('#remark').val();
            for (var item in data) {
                if (data[item] === "" || "undefined" === typeof data[item]) delete data[item];
            }
            var imgsUrlList = [];
            for (var i = 0; i < $('.imgsUrlList').length; i++) {
                var formControl = $('.imgsUrlList')[i].getElementsByClassName('form-control');
                if (formControl[0].value.length > 0) {
                    imgsUrlList.push(formControl[0].value);
                }
            }
            data['imgsUrl'] = imgsUrlList;
            $.ajax({
                url: "${springMacroRequestContext.contextPath}/dadaStyle/detail/" + ${dadaStyle.id},
                data: JSON.stringify(data),
                type: 'PUT',
                contentType: "application/json",
                headers: {"X-CSRF-TOKEN": '${_csrf.token}'}
            }).done(function (data) {
                $.redirect("${springMacroRequestContext.contextPath}/dadaStyle/list", function () {

                });
                $.notification.success(data);
            }).fail(function (error) {
                $.notification.error(error.responseText);
            })
        });
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
            url: "${springMacroRequestContext.contextPath}/api/upload?dir=style",
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
                parent.getElementsByClassName('tip_loading')[0].style.display = 'none';
                parent.getElementsByClassName('tip_success')[0].style.display = 'block';
            },
            error: function (data) {
                parent.getElementsByClassName('tip_loading')[0].style.display = 'none';
                parent.getElementsByClassName('tip_fail')[0].style.display = 'block';
            }
        });

    };

    //添加图片
    this.idx = 1;

    function addImgUrlList(target) {
        this.idx++;
        var src = '<div class="row imgsUrlList">' +
                '<div class="col-xs-offset-1 col-xs-5 col-md-7">' +
                '<div class="form-group">' +
                '<input class="form-control" id="imgsUrl' + this.idx + '" name="imgsUrl' + this.idx + '">' +
                '<label class="bc_upload" style="top: 1px;">上传' +
                '<input type="file" style="display: none;" accept="image/png, image/jpeg, image/jpg" onchange="changePic(this, imgsUrl' + this.idx + ')"/>' +
                '</label>' +
                '</div>' +
                '</div>' +
                '<div class="col-xs-5 col-md-1">' +
                '<div class="form-group">' +
                '<i class="fa fa-times bc_upload_tip_top8 tip_fail"></i>' +
                '<i class="fa fa-check bc_upload_tip_top8 tip_success"></i>' +
                '<i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip_top8 tip_loading"></i>' +
                '<button type="button" class="btn btn-info btn-remove" onclick="removeImgUrlList(this)">删除</button>' +
                '</div>' +
                '</div>' +
                '</div>';

        // 加在备注前
        var div = $('#wearUrl').parent().parent().parent();
        div.before(src);
    }

    //remove图片的位子
    function removeImgUrlList(target) {
        var div = $(target).parent().parent().parent();
        div.remove();
        var fa = $('.btn-remove');
        if (fa.length < 1) {
            this.idx = 1;
        }
    }

</script>
[/@content]
