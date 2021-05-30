[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]

[@content title="unique类别维护" subTitle=""]
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
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5 col-md-4">
                        <div class="form-group">
                            <label for="code">名称</label>
                            <p>${uniqueGroup.name}</p>
                        </div>
                        <div class="form-group">
                            <label for="name">花头</label>
                            <p>${uniqueGroup.ht}</p>
                        </div>
                        <div class="form-group">
                            <label for="remark">戒臂</label>
                            <p>${uniqueGroup.jb}</p>
                        </div>
                        <div class="form-group">
                            <label for="remark">描述</label>
                            <p>${uniqueGroup.remark}</p>
                        </div>
                        <div class="form-group">
                            <label for="enabled">是否展示</label>
                            <p>
                                    [#if uniqueGroup.enabled]
                                        是
                                    [#else]
                                        否
                                    [/#if]
                            </p>
                        </div>
                    </div>
                    <div class="col-xs-5 col-md-4">
                        <label for="url">配图</label>
                        <div>
                            <img src="${uniqueGroup.url}" class="img_shadow" style="height: 200px; margin: 10px 0;">
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

        </div>
        <!-- /.series -->
    </div>
    <!--/.col (left) -->
</div>
<!-- /.row -->
[/@content]
