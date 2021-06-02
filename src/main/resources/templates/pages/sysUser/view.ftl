[#include "../../component/content.ftl" /]

[@content title="账号维护" subTitle=""]
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
                                <label for="username">店铺编码</label>
                                <p>${user.username}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">店铺名称</label>
                                <p>${user.name}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="address">店铺地址</label>
                                <p>${user.address}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="pos">pos终端号</label>
                                <p>${user.pos}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="statusName">门店状态</label>
                                <p>${user.statusName!""}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="loLa">经纬度</label>
                                <p>
                                    [#if user.longitude??]
                                        ${user.longitude?string["0.######"]},${user.latitude?string["0.######"]}
                                    [/#if]
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="uuid">设备uuid</label>
                                <p>${user.uuid}</p>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="uuid">门店系数</label>
                                <p>${user.priceMultiple}</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isEnabled">是否启用</label>
                                <p>
                                    [#if user.enabled]
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
                                <label for="role">角色</label>
                                <p>
                                    [#if roles?? && roles?size>0]
                                        [#list roles as role]
                                            ${role.name}&nbsp;&nbsp;
                                        [/#list]
                                    [#else]
                                        暂无
                                    [/#if]
                                </p>
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
