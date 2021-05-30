[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]
[#include "../../component/modal.ftl"]

[@content title="开发者测试页面" subTitle=""]
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet"
          href="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/css/bootstrap3/bootstrap-switch.css"
          type="text/css"/>

    <div class="row">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">开发者测试页面，admin账号以外不展示</h3>
                </div>
                <!-- /.series-header -->
                <div class="box-body">
                </div>


                <div class="row">
                    <div class="col-xs-5 col-md-3" style="margin-left: 2%; border:#0c0c0c 1px solid;">
                        <div>
                            <div style="text-align: center; margin: 10px auto;">
                                <label>手动同步</label>
                            </div>
                            <div class="form-group">
                                <select class="select-company">
                                    <option value="">请选择公司</option>
                                    <option value="JP">JP</option>
                                    <option value="CHINASTAR">CHINASTAR</option>
                                    <option value="DHA">DHA</option>
                                    <option value="PG">PG</option>
                                    <option value="HB">HB</option>
                                    <option value="HB2">HB2</option>
                                    <option value="HAO">HAO</option>
                                    <option value="YBL">YBL</option>
                                    <option value="EX3">EX3</option>
                                    <option value="KEERSAP">KEERSAP</option>
                                    [#--<option value="HST">HST</option>--]
                                    <option value="YZ">YZ</option>
                                    <option value="JP2">JP2</option>
                                    <option value="KDL">KDL</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <a class="btn btn-flat bg-olive company-diamond-scheduled" href="javascript:;">同步库存</a>
                            </div>
                        </div>


                        [#--<div>--]
                        [#--<div style="text-align: center; margin: 10px auto;">--]
                        [#--<label>手动锁定/解锁/下单</label>--]
                        [#--</div>--]
                        [#--<div class="form-group">--]
                        [#--<label for="thirdCode">chinastar, HB2, EX3用证书号, KEERSAP用批次号</label>--]
                        [#--<input class="form-control company-diamond-input-code" placeholder="石头编码，锁定/解锁/下单，必填">--]
                        [#--<input class="form-control company-diamond-input-orderCode" placeholder="订单号，锁定/解锁/下单，KEERSAP必填">--]
                        [#--</div>--]
                        [#--<div class="form-group">--]
                        [#--<a class="btn btn-flat bg-olive company-diamond-lock" href="javascript:;">手动-锁定</a>--]
                        [#--<a class="btn btn-flat bg-olive company-diamond-unlock" href="javascript:;">手动-解锁</a>--]
                        [#--<a class="btn btn-flat bg-olive company-diamond-order" href="javascript:;">手动-下单</a>--]
                        [#--</div>--]
                        [#--</div>--]

                        [#--<div class="form-group">--]
                        [#--<label for="thirdCode">钻石编码</label>--]
                        [#--<input class="form-control local-input-third-code" placeholder="请输入编码"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="编码不能为空"--]
                        [#-->--]
                        [#--<a class="btn btn-flat bg-olive local-btn-third-lock" href="javascript:;">对外接口-锁定</a>--]
                        [#--<a class="btn btn-flat bg-olive local-btn-third-unlock" href="javascript:;">对外接口-解锁</a>--]
                        [#--<a class="btn btn-flat bg-olive local-btn-third-order" href="javascript:;">对外接口-下单</a>--]
                        [#--</div>--]
                        [#--<div class="form-group">--]
                        [#--<label for="username">对外接口-账号</label>--]
                        [#--<input class="form-control local-input-login-username" placeholder="请输入账号"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="账号不能为空"--]
                        [#-->--]
                        [#--<label for="password">对外接口-密码</label>--]
                        [#--<input class="form-control local-input-login-password" placeholder="请输入密码"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="密码不能为空"--]
                        [#-->--]
                        [#--<a class="btn btn-flat bg-blue local-btn-login" href="javascript:;">对外接口-登录</a>--]
                        [#--</div>--]
                        [#--<div class="form-group">--]
                        [#--<label for="token">token--通用</label>--]
                        [#--<input class="form-control local-input-token" placeholder="请输入token"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="token不能为空"--]
                        [#-->--]
                        [#--<label for="company">公司简称--通用</label>--]
                        [#--<input class="form-control local-input-company" placeholder="请输入公司简称（建议英文首字母）"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="公司简称不能为空"--]
                        [#-->--]

                        [#--<label for="updateDiamond">库存信息更新（二选一）</label>--]
                        [#--<input class="form-control local-input-update-diamond" placeholder="请输入库存信息"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="库存信息不能为空"--]
                        [#-->--]
                        [#--<a class="btn btn-flat bg-blue local-btn-update-diamond" href="javascript:;">本地测试-库存更新</a>--]

                        [#--<label for="updateDiamond">配置信息更新（二选一）</label>--]
                        [#--<input class="form-control local-input-update-sysconfig" placeholder="请输入配置信息"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="配置信息不能为空"--]
                        [#-->--]
                        [#--<a class="btn btn-flat bg-blue local-btn-update-sysconfig" href="javascript:;">本地测试-配置更新</a>--]
                        [#--</div>--]
                        [#--<label style="margin-left: 30px;">=========================分割线=========================</label>--]

                        [#--<div class="form-group">--]
                        [#--<label for="code">测试APP登录token</label>--]
                        [#--<input class="form-control token-login-username" placeholder="请输入账号"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="账号不能为空"--]
                        [#-->--]
                        [#--<input class="form-control token-login-password" placeholder="请输入密码"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="密码不能为空"--]
                        [#-->--]
                        [#--<a class="btn btn-flat bg-olive token-login" href="javascript:;">登录</a>--]
                        [#--</div>--]
                        [#--<div class="form-group">--]
                        [#--<label>测试APP更新token有效时间(客定码查主数据)</label>--]
                        [#--<input class="form-control input-token-update" placeholder="请输入token"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="token不能为空"--]
                        [#-->--]
                        [#--<input class="form-control input-product-ktCode" placeholder="请输入客定码"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="客定码不能为空"--]
                        [#-->--]
                        [#--<a class="btn btn-flat bg-olive button-token-update" href="javascript:;">查询</a>--]
                        [#--</div>--]
                        [#--<label style="margin-left: 30px;">=========================分割线=========================</label>--]

                        [#--<div class="form-group">--]
                        [#--<label>测试KBS登录</label>--]
                        [#--<a class="btn btn-flat bg-olive button-kbs-login" href="javascript:;">KBS登录</a>--]
                        [#--<input class="form-control input-kbs-stoneId" placeholder="请输入KBS钻石码"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="KBS钻石码不能为空"--]
                        [#-->--]
                        [#--<label>测试KBS锁定</label>--]
                        [#--<a class="btn btn-flat bg-olive button-kbs-lock" href="javascript:;">KBS锁定</a>--]
                        [#--<label>测试KBS解锁</label>--]
                        [#--<a class="btn btn-flat bg-olive button-kbs-unlock" href="javascript:;">KBS解锁</a>--]
                        [#--<label>测试KBS下单</label>--]
                        [#--<a class="btn btn-flat bg-olive button-kbs-order" href="javascript:;">KBS下单</a>--]
                        [#--</div>--]
                        [#--<label style="margin-left: 30px;">=========================分割线=========================</label>--]

                        [#--<div class="form-group">--]
                        [#--<label>测试缓存</label>--]
                        [#--<a class="btn btn-flat bg-olive button-config-get" href="javascript:;">getConfig</a>--]
                        [#--<input class="form-control input-config-key" placeholder="请输入key"--]
                        [#--data-rule="required"--]
                        [#--data-msg-required="key不能为空"--]
                        [#-->--]
                        [#--</div>--]
                        <div style="height: 100px"></div>
                        <div>
                            <div style="text-align: center; margin: 10px auto;">
                                <label>加锁/解锁，上架/下架</label>
                                <label>pad端查询显示规则：解锁状态 且 上架状态</label>
                            </div>
                            <div class="form-group">
                                <label>钻石证书号</label>
                                <input class="form-control input-status-stoneZsh" placeholder="证书号">
                            </div>
                            <div class="form-group">
                                <div class="icheck-item">
                                    <input type="radio" id="statusLockOn" class="input-status-lock" name="isLockStatus"
                                           value=1 checked>
                                    <label for="statusLockOn">解锁</label>
                                </div>
                                <div class="icheck-item">
                                    <input type="radio" id="statusLockOff" class="input-status-lock" name="isLockStatus"
                                           value=3>
                                    <label for="statusLockOff">加锁</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="icheck-item">
                                    <input type="radio" id="statusEnableOn" class="input-status-enable"
                                           name="isEnableStatus" value=1 checked>
                                    <label for="statusEnableOn">上架</label>
                                </div>
                                <div class="icheck-item">
                                    <input type="radio" id="statusEnableOff" class="input-status-enable"
                                           name="isEnableStatus" value=0>
                                    <label for="statusEnableOff">下架</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-group">
                                    <a class="btn btn-flat bg-olive button-status-update" href="javascript:;">保存</a>
                                </div>
                            </div>
                        </div>

                        <div style="height: 100px"></div>
                        <div>
                            <div style="text-align: center; margin: 10px auto;">
                                <label>重新生成pos报文 || 手动调用pos下单</label>
                            </div>
                            <div class="form-group">
                                <label>订单编号</label>
                                <input class="form-control input-orderCode" placeholder="订单编号">
                            </div>
                            <div class="form-group">
                                <a class="btn btn-flat bg-olive button-pos-renew" href="javascript:;">重新生成报文</a>
                                <a class="btn btn-flat bg-olive button-pos-resend" href="javascript:;">发送报文到pos</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-5 col-md-3" style="border:#0c0c0c 1px solid">
                        <div style="text-align: center; margin: 10px auto;">
                            <label>手动添加石头（默认上架）</label>
                        </div>
                        <div class="form-group">
                            <label>证书号</label>
                            <input class="form-control input-stone-zsh" placeholder="证书号">
                        </div>
                        <div class="form-group">
                            <label>证书</label>
                            <input class="form-control input-stone-zs" placeholder="证书" value="GIA">
                        </div>
                        <div class="form-group">
                            <label>克拉数</label>
                            <input class="form-control input-stone-zl" placeholder="克拉数">
                        </div>
                        <div class="form-group">
                            <label>颜色(D、E、F、G、H、I、J、K、L、M)</label>
                            <input class="form-control input-stone-ys" placeholder="颜色">
                        </div>
                        <div class="form-group">
                            <label>净度(IF、SI1、SI2、VS1、VS2、VVS1、VVS2)</label>
                            <input class="form-control input-stone-jd" placeholder="净度">
                        </div>
                        <div class="form-group">
                            <label>切工(EX、VG)</label>
                            <input class="form-control input-stone-qg" placeholder="切工">
                        </div>
                        <div class="form-group">
                            <label>抛光(EX、VG)</label>
                            <input class="form-control input-stone-pg" placeholder="抛光">
                        </div>
                        <div class="form-group">
                            <label>对称(EX、VG)</label>
                            <input class="form-control input-stone-dc" placeholder="对称">
                        </div>
                        <div class="form-group">
                            <label>荧光</label>
                            <input class="form-control input-stone-yg" placeholder="荧光" value="N">
                        </div>
                        <div class="form-group">
                            <label>库存地</label>
                            <input class="form-control input-stone-location" placeholder="库存地" value="SZ">
                        </div>
                        <div class="form-group">
                            <label>所属公司</label>
                            <input class="form-control input-stone-company" placeholder="所属公司" value="keercom">
                        </div>
                        <div class="form-group">
                            <a class="btn btn-flat bg-olive button-stone-add" href="javascript:;">添加</a>
                        </div>
                    </div>

                    <div class="col-xs-5 col-md-4" style="border:#0c0c0c 1px solid">
                        <div class="form-group">
                            <label for="result">返回结果</label>
                            <textarea class="form-control back-result" style="height: 400px"></textarea>
                        </div>
                    </div>

                    <div class="col-xs-5 col-md-4" style="border:#0c0c0c 1px solid">
                        <div style="text-align: center; margin: 10px auto;">
                            <label>已退订单修改戒托(定制系统+POS系统)</label>
                        </div>
                        <div class="row">
                            <div class="col-xs-5 col-md-6">
                                <div class="form-group">
                                    <label>订单编号</label>
                                    <input class="form-control input-reOrder-code" placeholder="订单编号">
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-6">
                                <div class="form-group">
                                    <label>GIA证书号</label>
                                    <input class="form-control input-reOrder-zsh" placeholder="GIA证书号">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-5 col-md-6">
                                <div class="form-group">
                                    <label>客定码</label>
                                    <input class="form-control input-reOrder-ktCode" placeholder="客定码">
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-6">
                                <div class="form-group">
                                    <label>手寸</label>
                                    <input class="form-control input-reOrder-sc" placeholder="手寸">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-5 col-md-6">
                                <div class="form-group">
                                    <a class="btn btn-flat bg-olive button-reOrder" href="javascript:;">提交</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <table id="example">

                </table>
            </div>
            <!-- /.series-body -->
        </div>
        <!-- /.series -->
    </div>
    <!-- /.col -->
    </div>
    <!-- /.row -->

    <!-- DataTables -->
    <script src="${springMacroRequestContext.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.js"></script>
    <script src="${springMacroRequestContext.contextPath}/plugins/bootstrap-switch/js/bootstrap-switch.js"></script>
    <script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/jquery.validator.js?local=zh-CN"></script>
    <script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/jquery.validator.config.js"></script>
    <script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/local/zh-CN.js"></script>
    <script>
        $(function () {
            var $form = $('#example');
            //同步
            $('.company-diamond-scheduled').click(function () {
                var data = $form.serializeObject();
                data.company = $('.select-company').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/diamond/scheduled",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.status == 1) {
                        //成功
                    } else {
                        //失败
                    }
                    $.notification.success(data);
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                    $('.back-result')[0].value = JSON.stringify(error);
                });
            })
            $('.company-diamond-lock').click(function () {
                var data = $form.serializeObject();
                data.company = $('.select-company').val();
                data.code = $('.company-diamond-input-code').val();
                data.orderCode = $('.company-diamond-input-orderCode').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/diamond/lock",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                    $('.back-result')[0].value = JSON.stringify(error);
                });
            })
            $('.company-diamond-unlock').click(function () {
                var data = $form.serializeObject();
                data.company = $('.select-company').val();
                data.code = $('.company-diamond-input-code').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/diamond/unlock",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                    $('.back-result')[0].value = JSON.stringify(error);
                });
            })
            $('.company-diamond-order').click(function () {
                var data = $form.serializeObject();
                data.company = $('.select-company').val();
                data.code = $('.company-diamond-input-code').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/diamond/order",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                    $('.back-result')[0].value = JSON.stringify(error);
                });
            })


            $('.local-btn-login').click(function () {
                debugger
                var data = $form.serializeObject();
                data.action = "login";
                data.username = $('.local-input-login-username').val();
                data.password = $('.local-input-login-password').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/api",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })

            $('.local-btn-update-diamond').click(function () {
                debugger
                var data = $form.serializeObject();
                data.action = "diamond";
                data.token = $('.local-input-token').val();
                data.company = $('.local-input-company').val();
                data.rows = $('.local-input-update-diamond').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/api",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })

            $('.local-btn-update-sysconfig').click(function () {
                debugger
                var data = $form.serializeObject();
                data.action = "sysconfig";
                data.token = $('.local-input-token').val();
                data.company = $('.local-input-company').val();
                data.rows = $('.local-input-update-sysconfig').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/api",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })

            $('.local-btn-third-login').click(function () {
                debugger
                var data = $form.serializeObject();
                data.action = "thirdLogin";
                data.company = $('.local-login-third-company').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/api",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })

            $('.local-btn-third-lock').click(function () {
                debugger
                var data = $form.serializeObject();
                data.action = "thirdLock";
                data.code = $('.local-input-third-code').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/api",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })

            $('.local-btn-third-unlock').click(function () {
                debugger
                var data = $form.serializeObject();
                data.action = "thirdUnlock";
                data.code = $('.local-input-third-code').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/api",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })

            $('.local-btn-third-order').click(function () {
                debugger
                var data = $form.serializeObject();
                data.action = "thirdOrder";
                data.code = $('.local-input-third-code').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/api",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })

            // token 登录测试APP
            $('.token-login').click(function () {
                debugger
                var data = $form.serializeObject();
                data.username = $('.token-login-username').val();
                data.password = $('.token-login-password').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/app/loginToken",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                        "token": "222"
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })


            // 测试更新token的有效时间
            $('.button-token-update').click(function () {
                debugger
                var data = $form.serializeObject();
                data.ktCode = $('.input-product-ktCode').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/app/tokenUpdate",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                        "token": $('.input-token-update').val()
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })


            //KBS登录
            $('.button-kbs-login').click(function () {
                debugger
                var data = $form.serializeObject();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/kbsLogin",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })

            //KBS锁定
            $('.button-kbs-lock').click(function () {
                debugger
                var data = $form.serializeObject();
                data.stoneId = $('.input-kbs-stoneId').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/kbsLock",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })

            //KBS解锁
            $('.button-kbs-unlock').click(function () {
                debugger
                var data = $form.serializeObject();
                data.stoneId = $('.input-kbs-stoneId').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/kbsUnlock",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })

            //KBS下单
            $('.button-kbs-order').click(function () {
                debugger
                var data = $form.serializeObject();
                data.stoneId = $('.input-kbs-stoneId').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/thirdSupplier/kbsOrder",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })


            //缓存测试
            $('.button-config-get').click(function () {
                debugger
                var data = $form.serializeObject();
                data.key = $('.input-config-key').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/config/get",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.code == 0) {
                        //成功
                        $.notification.success(data.message);
                    } else {
                        //失败
                        $.notification.error(data.message);
                    }
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                });
            })


            //石头手动加锁/解锁
            //石头手动上架/下架
            $('.button-status-update').click(function () {
                var data = $form.serializeObject();
                data.stoneZsh = $('.input-status-stoneZsh').val();
                data.stoneLockStatus = $("input[name='isLockStatus']:checked").val();
                data.stoneEnableStatus = $("input[name='isEnableStatus']:checked").val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/stone/status/update",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.status == 1) {
                        //成功
                    } else {
                        //失败
                    }
                    $.notification.success(data);
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                    $('.back-result')[0].value = JSON.stringify(error);
                });
            })


            //石头手动添加
            $('.button-stone-add').click(function () {
                var data = $form.serializeObject();
                data.stoneZsh = $('.input-stone-zsh').val();
                data.stoneZs = $('.input-stone-zs').val();
                data.stoneZl = $('.input-stone-zl').val();
                data.stoneYs = $('.input-stone-ys').val();
                data.stoneJd = $('.input-stone-jd').val();
                data.stoneQg = $('.input-stone-qg').val();
                data.stonePg = $('.input-stone-pg').val();
                data.stoneDc = $('.input-stone-dc').val();
                data.stoneYg = $('.input-stone-yg').val();
                data.stoneLocation = $('.input-stone-location').val();
                data.stoneCompany = $('.input-stone-company').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/stone/create",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.status == 1) {
                        //成功
                    } else {
                        //失败
                    }
                    $.notification.success(data);
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                    $('.back-result')[0].value = JSON.stringify(error);
                });
            })


            //订单重新生成报文
            $('.button-pos-renew').click(function () {
                var data = $form.serializeObject();
                data.orderCode = $('.input-orderCode').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/stoneOrder/pos/renew",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.status == 1) {
                        //成功
                    } else {
                        //失败
                    }
                    $.notification.success(data);
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                    $('.back-result')[0].value = JSON.stringify(error);
                });
            })

            //订单手动再次调用pos
            $('.button-pos-resend').click(function () {
                var data = $form.serializeObject();
                data.orderCode = $('.input-orderCode').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/stoneOrder/pos/resend",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.status == 1) {
                        //成功
                    } else {
                        //失败
                    }
                    $.notification.success(data);
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                    $('.back-result')[0].value = JSON.stringify(error);
                });
            })



            //订单取消重新下单（修改客定码）
            $('.button-reOrder').click(function () {
                var data = $form.serializeObject();
                data.orderCode = $('.input-reOrder-code').val();
                data.orderZsh = $('.input-reOrder-zsh').val();
                data.orderKtCode = $('.input-reOrder-ktCode').val();
                data.orderSc = $('.input-reOrder-sc').val();
                $.ajax({
                    url: "${springMacroRequestContext.contextPath}/developer/stoneOrder/reOrder",
                    dataType: "json",
                    data: JSON.stringify(data),
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                        "X-CSRF-TOKEN": "${_csrf.token}",
                    }
                }).done(function (data) {
                    debugger
                    if (data.status == 1) {
                        //成功
                    } else {
                        //失败
                    }
                    $.notification.success(data);
                    $('.back-result')[0].value = JSON.stringify(data);
                }).fail(function (error) {
                    $.notification.error(error.responseText);
                    $('.back-result')[0].value = JSON.stringify(error);
                });
            })




        });
    </script>
[/@content]
