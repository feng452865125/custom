[#include "../../component/content.ftl" /]

[@content title="角色" subTitle=""]
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
                                <label for="name">名称</label>
                                <p>${role.name!'暂无'}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-10 col-md-8">
                            <div class="form-group">
                                <label for="description">描述</label>
                                <p>${role.description!'暂无'}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isSystem">是否系统内置</label>
                                <p>
                                [#if role.isSystem]
                                    是
                                [#else]
                                    否
                                [/#if]
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-10 col-md-8">
                            <div class="form-group">
                                <label for="role">权限</label>
                                [#if rolePermissionsMap?? && rolePermissionsMap?size>0]
                                    [#list rolePermissionsMap as key, value ]
                                        <div class="icheck-group">
                                            <b class="col-xs-12">
                                            ${key}
                                            </b>
                                            <div class="col-xs-12">
                                                <span class="icheck-item">
                                                </span>
                                                [#list value as item]
                                                    <span class="icheck-item">
                                                        <p>${item.name}</p>
                                                    </span>
                                                [/#list]
                                            </div>
                                        </div>
                                    [/#list]
                                [#else]
                                    <p>暂无</p>
                                [/#if]
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
