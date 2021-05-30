[#include "../../component/content.ftl" /]

[@content title="上传" subTitle="" nobar=true]
<script src="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/js/vendor/jquery.ui.widget.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/js/jquery.iframe-transport.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/js/jquery.fileupload.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/js/upload.js"></script>

<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/css/jquery.fileupload.css">
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/css/upload.css">
<!-- jQuery 2.2.3 -->
[#--<script src="${springMacroRequestContext.contextPath}/plugins/jQuery/jquery-3.3.1.min.js"></script>--]
<script src="${springMacroRequestContext.contextPath}/plugins/jQuery-upload/js/ajaxfileupload.js"></script>

<head>
    <style>
        .ui-upload {
            background-color: #3c8dbc;
            border-color: #367fa9;
            cursor: pointer;
            color: #fff;
            border-radius: 3px;
            box-shadow: none;
            border: 1px solid transparent;
            display: inline-block;
            padding: 6px 12px;
            margin-bottom: 0;
            font-size: 14px;
            font-weight: 400;
            line-height: 1.42857143;
            text-align: center;
            white-space: nowrap;
            vertical-align: middle;
            position: absolute;
            top: 25px;
            right: 15px;
        }
        .upload_tip {
            position: absolute;
            top: 25px;
            font-size: 30px;
            display: none;
        }
        .tip_success {
            color: green;
        }
        .tip_fail {
            color: red;
        }
    </style>
</head>

<div class="row">
    <div class="col-md-12">
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">编辑</h3>
            </div>
            <form id="form1" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="username">图片1</label>
                                <input type="hidden" name="pic1" class="relFile">
                                <span class="btn btn-success fileinput-button">
                <i class="fa fa-plus"></i>
                <span>添加</span>
                <input class="fileupload" type="file" name="file" data-url="/api/upload?dir=test">
                </span>
                                <button class="btn btn-primary startUpload">
                                    <i class="fa fa-upload"></i>
                                    <span>上传</span>
                                </button>
                                <button class="btn btn-warning cancelUpload">
                                    <i class="fa fa-reply"></i>
                                    <span>取消</span>
                                </button>
                                <div class="preview">
                                    <span class="loading"></span>
                                    <span class="success"></span>
                                    <img class="uploadimg" src=""/>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">图片2</label>
                                <input type="hidden" name="pic2" class="relFile">
                                <span class="btn btn-success fileinput-button">
                <i class="fa fa-plus"></i>
                <span>添加</span>
                <input class="fileupload" type="file" name="file" data-url="/api/upload?dir=test">
                </span>
                                <button class="btn btn-primary startUpload">
                                    <i class="fa fa-upload"></i>
                                    <span>上传</span>
                                </button>
                                <button class="btn btn-warning cancelUpload">
                                    <i class="fa fa-reply"></i>
                                    <span>取消</span>
                                </button>
                                <div class="preview">
                                    <span class="success"></span>
                                    <img class="uploadimg" src=""/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isEnabled">帐号</label>
                                <input name="name" value="admin">
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isLocked">密码</label>
                                <input name="password" value="123456">
                            </div>
                        </div>
                    </div>


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
            </form>

            <div class="row">
            [#--data-url="/api/upload?dir=test"--]
                <div class="col-xs-offset-1 col-xs-5 col-md-4">
                    <div class="form-group">
                        <form action="${springMacroRequestContext.contextPath}/api/upload?dir=testForm"
                              enctype="multipart/form-data" METHOD="post">
                            <label for="username">测试一，form</label>
                            <div style="display: flex;">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="file" id="fileForm" name="file" class="fileForm" accept="image/png">
                                <button type="submit">submit</button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
            <div class="row">
            [#--data-url="/api/upload?dir=test"--]
                [#--<div class="col-xs-offset-1 col-xs-5 col-md-4">--]
                    [#--<div class="form-group">--]
                        [#--<label for="username">测试二，input+ajax</label>--]
                        [#--<input type="hidden" name="file" class="relFile">--]
                        [#--<input type="file" id="fileInput" class="fileInput" accept="image/png" name="file"--]
                               [#--onchange="changePic(this)"><br>--]
                    [#--</div>--]
                [#--</div>--]

            </div>

            <div class="row">
                <div class="col-xs-offset-1 col-xs-5 col-md-7">
                    <div class="form-group">
                        <label for="imgUrl">缩略图</label>
                        <input class="form-control" name="imgUrl" id="imgUrl">
                        <label class="ui-upload">上传
                            <input type="file" style="display: none;" accept="image/png, image/jpeg, image/gif, image/jpg"
                                   onchange="changePic(this, 'imgUrl')"/>
                        </label>
                    </div>
                </div>
                <div class="col-xs-5 col-md-1">
                    <div class="form-group">
                        <i class="fa fa-times upload_tip tip_fail"></i>
                        <i class="fa fa-check upload_tip tip_success"></i>
                        <i class="fa fa-spinner fa-pulse fa-3x fa-fw upload_tip tip_loading"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function () {
    [#--$('.fileupload').fileupload({--]
    [#--headers: {--]
    [#--"X-CSRF-TOKEN": "${_csrf.token}"--]
    [#--}--]
    [#--});--]

    [#--$(".btn-submit").on("click", function(e){--]
    [#--e.preventDefault();--]
    [#--console.log("pic1路径:" + $("input[name='pic1']").val());--]
    [#--console.log("pic2路径:" + $("input[name='pic2']").val());--]
    [#--console.log("from表单序列化:" + $("#form1").serialize());--]
    [#--});--]
    });

    function changePic(target, id) {
        debugger
        $('.tip_success')[0].style.display='none';
        $('.tip_fail')[0].style.display='none';
        $('.tip_loading')[0].style.display='block';
        var formData = new FormData();//初始化一个FormData对象
        formData.append("file", target.files[0]);//将文件塞入FormData
        $.ajax({
            url: "${springMacroRequestContext.contextPath}/api/upload?dir=testInput",
            type: "POST",
            data: formData,
            processData: false,  // 告诉jQuery不要去处理发送的数据
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            headers: {"X-CSRF-TOKEN": '${_csrf.token}'},
            success: function (data) {
                debugger
                $.notification.success(data);
                $('#' + id + '').val(data);
                $('.tip_loading')[0].style.display='none';
                $('.tip_success')[0].style.display='block';
            },
            error: function (data) {
                debugger
                $.notification.error(data.responseText);
                console.log(data.responseText);
                $('.tip_loading')[0].style.display='none';
                $('.tip_fail')[0].style.display='block';
            }
        });
        // }


    };
</script>
[/@content]