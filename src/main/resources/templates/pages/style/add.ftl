[#include "../../component/content.ftl" /]

[@content title="样式维护" subTitle=""]
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/iCheck/all.css">
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/css/common.css">

    <div class="row">
        <!-- left column -->
        <div class="col-md-12">
            <!-- general form elements -->
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">添加</h3>
                </div>
                <!-- /.series-header -->
                <!-- form start -->
                <form id="form1" action="${springMacroRequestContext.contextPath}/style" role="form">
                    <div class="box-body">
                        <label style="margin-left: 30px;">基础信息</label>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-3">
                                <div class="form-group">
                                    <label for="id">样式编码（寓意+佩戴类别+金属颜色）</label>
                                    <input class="form-control" name="code" id="code" placeholder="例：天作之合戒指白色"
                                           data-rule="required"
                                           data-msg-required="样式编码不能为空">
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-3">
                                <div class="form-group">
                                    <label for="name">样式名称</label>
                                    <input class="form-control" name="name" id="name" placeholder="例：18K金钻石戒指"
                                           data-rule="required"
                                           data-msg-required="样式名称不能为空">
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-2">
                                <div class="form-group">
                                    <label for="type">类型</label>
                                    <select class="form-control" id="type" name="type" data-rule="noSelect">
                                        <option value="-1">请选择类型</option>
                                        [#list typeList as type]
                                            <option value="${type.id}">${type.name}</option>
                                        [/#list]
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-3">
                                <div class="form-group">
                                    <label for="id">花头样式</label>
                                    <input class="form-control" name="htYs" id="htYs" placeholder="例：A">
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-3">
                                <div class="form-group">
                                    <label for="name">戒臂样式</label>
                                    <input class="form-control" name="jbYs" id="jbYs" placeholder="例：A">
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-2">
                                <div class="form-group">
                                    <label for="name">系列</label>
                                    <input class="form-control" name="series" id="series" value="UNIQUE" disabled>
                                </div>
                            </div>
                        </div>
                        <div class="row imgsUrlList">
                            <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                <div class="form-group">
                                    <label for="imgsUrl">轮播图文件（推荐尺寸：1000*750）</label>
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
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                <div class="form-group">
                                    <label for="imgMaxUrl">详情图（推荐尺寸：1000*750）</label>
                                    <input class="form-control" name="imgMaxUrl" id="imgMaxUrl"
                                           data-rule="required"
                                           data-msg-required="详情大图不能为空">
                                    <label class="bc_upload">上传
                                        <input type="file" style="display: none;"
                                               accept="image/png, image/jpeg, image/jpg"
                                               onchange="changePic(this, imgMaxUrl)"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-1">
                                <div class="form-group">
                                    <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                    <i class="fa fa-check bc_upload_tip tip_success"></i>
                                    <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                <div class="form-group">
                                    <label for="imgUrl">缩略图（推荐尺寸：400*300）</label>
                                    <input class="form-control" name="imgUrl" id="imgUrl"
                                           data-rule="required"
                                           data-msg-required="缩略小图不能为空">
                                    <label class="bc_upload">上传
                                        <input type="file" style="display: none;"
                                               accept="image/png, image/jpeg, image/jpg"
                                               onchange="changePic(this, imgUrl)"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-1">
                                <div class="form-group">
                                    <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                    <i class="fa fa-check bc_upload_tip tip_success"></i>
                                    <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                <div class="form-group">
                                    <label for="rate45imgUrl">45度文件（推荐尺寸：400*300）</label>
                                    <input class="form-control" name="rate45imgUrl" id="rate45imgUrl">
                                    <label class="bc_upload">上传
                                        <input type="file" style="display: none;"
                                               accept="image/png, image/jpeg, image/jpg"
                                               onchange="changePic(this, rate45imgUrl)"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-1">
                                <div class="form-group">
                                    <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                    <i class="fa fa-check bc_upload_tip tip_success"></i>
                                    <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                <div class="form-group">
                                    <label for="fanUrl">侧面图文件（推荐尺寸：400*300）</label>
                                    <input class="form-control" name="fanUrl" id="fanUrl">
                                    <label class="bc_upload">上传
                                        <input type="file" style="display: none;"
                                               accept="image/png, image/jpeg, image/jpg"
                                               onchange="changePic(this, fanUrl)"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-1">
                                <div class="form-group">
                                    <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                    <i class="fa fa-check bc_upload_tip tip_success"></i>
                                    <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                <div class="form-group">
                                    <label for="videoUrl">视频文件（推荐尺寸：16:9）</label>
                                    <input class="form-control" name="videoUrl" id="videoUrl">
                                    <label class="bc_upload">上传
                                        <input type="file" style="display: none;" accept="video/*"
                                               onchange="changePic(this, videoUrl)"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-1">
                                <div class="form-group">
                                    <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                    <i class="fa fa-check bc_upload_tip tip_success"></i>
                                    <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                <div class="form-group">
                                    <label for="wearUrl30">试戴文件（30分）（推荐尺寸：140*140）</label>
                                    <input class="form-control" name="wearUrl30" id="wearUrl30">
                                    <label class="bc_upload">上传
                                        <input type="file" style="display: none;"
                                               accept="image/png, image/jpeg, image/jpg"
                                               onchange="changePic(this, wearUrl30)"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-1">
                                <div class="form-group">
                                    <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                    <i class="fa fa-check bc_upload_tip tip_success"></i>
                                    <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                <div class="form-group">
                                    <label for="wearUrl50">试戴文件（50分）（推荐尺寸：140*140）</label>
                                    <input class="form-control" name="wearUrl50" id="wearUrl50">
                                    <label class="bc_upload">上传
                                        <input type="file" style="display: none;"
                                               accept="image/png, image/jpeg, image/jpg"
                                               onchange="changePic(this, wearUrl50)"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-1">
                                <div class="form-group">
                                    <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                    <i class="fa fa-check bc_upload_tip tip_success"></i>
                                    <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                <div class="form-group">
                                    <label for="wearUrl70">试戴文件（70分）（推荐尺寸：140*140）</label>
                                    <input class="form-control" name="wearUrl70" id="wearUrl70">
                                    <label class="bc_upload">上传
                                        <input type="file" style="display: none;"
                                               accept="image/png, image/jpeg, image/jpg"
                                               onchange="changePic(this, wearUrl70)"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-1">
                                <div class="form-group">
                                    <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                    <i class="fa fa-check bc_upload_tip tip_success"></i>
                                    <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-7">
                                <div class="form-group">
                                    <label for="wearUrl100">试戴文件（100分）（推荐尺寸：140*140）</label>
                                    <input class="form-control" name="wearUrl100" id="wearUrl100">
                                    <label class="bc_upload">上传
                                        <input type="file" style="display: none;"
                                               accept="image/png, image/jpeg, image/jpg"
                                               onchange="changePic(this, wearUrl100)"/>
                                    </label>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-1">
                                <div class="form-group">
                                    <i class="fa fa-times bc_upload_tip tip_fail"></i>
                                    <i class="fa fa-check bc_upload_tip tip_success"></i>
                                    <i class="fa fa-spinner fa-pulse fa-3x fa-fw bc_upload_tip tip_loading"></i>
                                </div>
                            </div>
                        </div>

                        <label style="margin-left: 30px;">其他信息</label>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="moral">寓意</label>
                                    <textarea class="form-control remark_style" id="moral"
                                              name="moral" placeholder="例：天作之合"
                                              data-rule="required"
                                              data-msg-required="寓意不能为空"></textarea>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="features">款式特点</label>
                                    <textarea class="form-control remark_style" id="features"
                                              name="features" placeholder="款式特点"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="story">故事</label>
                                    <textarea class="form-control remark_style" id="story"
                                              name="story" placeholder="情感故事"></textarea>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="meaning">情感含义</label>
                                    <textarea class="form-control remark_style" id="meaning"
                                              name="meaning" placeholder="含义"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-4">
                                <div class="form-group">
                                    <label for="remark">描述</label>
                                    <textarea class="form-control remark_style" id="remark"
                                              name="remark" placeholder="描述"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-2">
                                <div class="form-group">
                                    <label for="enabled">是否展示</label>
                                    <div class="icheck-group">
                                        <div class="icheck-item">
                                            <input type="radio" id="isEnabled" class="minimal" name="enabled" value=true
                                                   data-rule="checked" checked>
                                            <label for="enabled">是</label>
                                        </div>
                                        <div class="icheck-item">
                                            <input type="radio" id="isDisabled" class="minimal" name="enabled"
                                                   value=false
                                                   data-rule="checked">
                                            <label for="disabled">否</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-2">
                                <div class="form-group">
                                    <label for="isRecommend">是否推荐</label>
                                    <div class="icheck-group">
                                        <div class="icheck-item">
                                            <input type="radio" id="isRecommendEnabled" class="minimal"
                                                   name="isRecommend"
                                                   value=true
                                                   data-rule="checked" checked>
                                            <label for="enabled">是</label>
                                        </div>
                                        <div class="icheck-item">
                                            <input type="radio" id="isRecommendDisabled" class="minimal"
                                                   name="isRecommend"
                                                   value=false
                                                   data-rule="checked">
                                            <label for="disabled">否</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-2">
                                <div class="form-group">
                                    <label for="isPrinting">是否印花</label>
                                    <div class="icheck-group">
                                        <div class="icheck-item">
                                            <input type="radio" id="isPrintingEnabled" class="minimal" name="isPrinting"
                                                   value=true
                                                   data-rule="checked" checked>
                                            <label for="enabled">是</label>
                                        </div>
                                        <div class="icheck-item">
                                            <input type="radio" id="isPrintingDisabled" class="minimal"
                                                   name="isPrinting"
                                                   value=false
                                                   data-rule="checked">
                                            <label for="disabled">否</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-2">
                                <div class="form-group">
                                    <label for="isNxbs">是否内镶宝石</label>
                                    <div class="icheck-group">
                                        <div class="icheck-item">
                                            <input type="radio" id="isNxbsEnabled" class="minimal" name="isNxbs"
                                                   value=true
                                                   data-rule="checked" checked>
                                            <label for="enabled">是</label>
                                        </div>
                                        <div class="icheck-item">
                                            <input type="radio" id="isNxbsDisabled" class="minimal" name="isNxbs"
                                                   value=false
                                                   data-rule="checked">
                                            <label for="disabled">否</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-2">
                                <div class="form-group">
                                    <label for="isGq">是否改圈</label>
                                    <div class="icheck-group">
                                        <div class="icheck-item">
                                            <input type="radio" id="isGqEnabled" class="minimal" name="isGq" value=true
                                                   data-rule="checked" checked>
                                            <label for="enabled">是</label>
                                        </div>
                                        <div class="icheck-item">
                                            <input type="radio" id="isGqDisabled" class="minimal" name="isGq"
                                                   value=false
                                                   data-rule="checked">
                                            <label for="disabled">否</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <label style="margin-left: 30px;">主数据</label>
                        <div class="row">
                            <div class="col-xs-offset-1 col-xs-5 col-md-3">
                                <div class="form-group">
                                    <label for="id">选择该款式关联的主数据文件</label>
                                    <input class="form-control" name="productFile" id="productFile" type="file"
                                           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                                           data-rule="required"
                                           data-msg-required="文件不能为空，否则下单异常">
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-3">
                                <div class="form-group">
                                    <a class="btn btn-flat bg-olive btn-product-download"
                                       href="../../download/importProduct.xls" download="">模板下载</a>
                                </div>
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
                </form>
            </div>
            <!-- /.series -->
        </div>
        <!--/.col (left) -->
    </div>
    <!-- /.row -->

    <script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/jquery.validator.js?local=zh-CN"></script>
    <script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/jquery.validator.config.js"></script>
    <script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/local/zh-CN.js"></script>
    <script src="${springMacroRequestContext.contextPath}/plugins/iCheck/icheck.min.js"></script>

    <script>

        $(function () {
            $form = $("#form1");
            $form.on('click', '.btn-submit', function (e) {
                e.preventDefault();
                $form.trigger("validate");
            })
            $form.on('valid.form', function (e, form) {
                var data = $form.serializeObject();
                for (var item in data) {
                    if (data[item] === "" || "undefined" === typeof data[item]) delete data[item];
                }
                var formData = new FormData();//初始化一个FormData对象
                var $productFile = $('#productFile');
                formData.append("file", $productFile[0].files[0]);//将文件塞入FormData
                var imgsUrlList = [];
                for (var i = 0; i < $('.imgsUrlList').length; i++) {
                    var formControl = $('.imgsUrlList')[i].getElementsByClassName('form-control');
                    if (formControl[0].value.length > 0) {
                        imgsUrlList.push(formControl[0].value);
                    }
                }
                data['imgsUrl'] = imgsUrlList;
                formData.append("map", JSON.stringify(data));
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/style",
                    data: formData,
                    async: false,
                    dataType: "text",
                    cache: false,
                    processData: false,  // 告诉jQuery不要去处理发送的数据
                    contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
                    type: "POST",
                    headers: {"X-CSRF-TOKEN": '${_csrf.token}'}
                }).done(function (data) {
                    $.redirect("${springMacroRequestContext.contextPath}/style/list", function () {

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
            var div = $('#imgUrl').parent().parent().parent();
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
