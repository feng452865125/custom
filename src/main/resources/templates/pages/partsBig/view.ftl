[#include "../../component/content.ftl" /]

[@content title="大克拉钻石" subTitle=""]
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
            <form id="form1" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="code">印花编码</label>
                                <p>${partsBig.code}</p>
                            </div>
                            <div class="form-group">
                                <label for="name">印花名称</label>
                                <p>${partsBig.name}</p>
                            </div>
                            <div class="form-group">
                                <label for="remark">印花描述</label>
                                <p>${partsBig.remark}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <label for="url">配图</label>
                            <div>
                                <img src="${partsBig.imgUrl}" class="img_shadow" style="height: 200px; margin: 10px 0;">
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.series-body -->

                <div class="box-footer">
                    <div class="row">
                        <div class="col-xs-offset-1">
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
[/@content]
