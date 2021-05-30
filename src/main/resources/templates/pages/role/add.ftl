[#include "../../component/content.ftl" /]
[#include "../../component/tree.ftl" /]

[@content title="角色" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/iCheck/all.css">
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
            <form id="form1" action="${springMacroRequestContext.contextPath}/role" role="form">
                <div class="box-body">
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="name">名称</label>
                                <input type="text" class="form-control" id="name" name="name" placeholder="请输入名称"
                                   data-rule="required;length(1~32);remote(get:${springMacroRequestContext.contextPath}/role/nameCheck)"
                                   data-msg-required="名称不能为空"
                                   data-msg-remote="名称已存在"
                                >
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-4">
                            
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-10 col-md-8">
                            <div class="form-group">
                                <label for="description">描述</label>
                                <textarea rows="3" class="form-control" id="description" name="description" placeholder="请输入描述"
                                     data-rule="length(1~255)"
                                />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-5 col-md-4">
                            <div class="form-group">
                                <label for="isSystem">是否系统内置</label>
                                <div class="icheck-group">
                                    <div class="icheck-item">
                                        <input type="radio" id="system" class="minimal" name="isSystem" value=true data-rule="checked" checked>
                                        <label for="system">是</label>
                                    </div>
                                    <div class="icheck-item">
                                        <input type="radio" id="unsystem" class="minimal" name="isSystem" value=false data-rule="checked">
                                        <label for="unsystem">否</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-offset-1 col-xs-10 col-md-8">
                            <div class="form-group">
                                <div class="icheck-group-title">
                                    <label for="permission">权限</label><input type="checkbox" id="permission" class="minimal check-all">
                                </div>
                                [#list permissionsMap as key, value ]
                                <div class="icheck-group">
                                    <b class="col-xs-12 icheck-group-header">
                                        <span class="icheck-group-title">
                                            <label for="${key}">${key}</label>
                                            <input type="checkbox" id="${key}" data-index="${key_index}" class="minimal check-group" name="permissionGroup">
                                        </span>
                                    </b>
                                    <div class="col-xs-12 icheck-group-body">
                                        [#list permissionsMap[key] as item]
                                            <span class="icheck-item">
                                                <input type="checkbox" id="${item.id}" data-index="${key_index}" class="minimal check-item" name="permissionArray" value="${item.code}">
                                                <label for="${item.id}">${item.name}</label>
                                            </span>
                                        [/#list]
                                    </div>
                                </div>
                                [/#list]
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
<script src="${springMacroRequestContext.contextPath}/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function() {
        $.submitForm("#form1", ".btn-submit", "POST", ["${_csrf.parameterName}", "${_csrf.token}"],
        function(data) {
            $.redirect("${springMacroRequestContext.contextPath}/role/list", function() {});
        }, function(err) {

        }, function(data) {
            data.permissionArray = JSON.stringify(data.permissionArray)
        })

        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        });


        var $checkAll = $('.check-all');
        var $checkGroup = $('.check-group');
        var $checkItem = $(".check-item");
        var state = {};
        var triggerCheckedEvent = {
            all: true,
            group: true,
            item: true,
        }

        var setChecked = function(target, checked, options) {
            if ('boolean' === typeof checked) {
                checked = checked ? 'check' : 'uncheck';
            }
            $.extend(triggerCheckedEvent, options);
            target.iCheck(checked);
            triggerCheckedEvent = {
                all: true,
                group: true,
                item: true,
            };
        }
        var checkedAll = function(checks, isCheckedAll, options) {
            var isCheck = isCheckedAll ? 'check' : 'uncheck';
            $.extend(triggerCheckedEvent, options);
            checks.each(function(index, item) {
                var $this = $(item);
                $this.iCheck(isCheck);
            })
            triggerCheckedEvent = {
                all: true,
                group: true,
                item: true,
            };
        }
        var isGroupCheck = function($checks) {
            var flag = false;
            $checks.each(function(index, item) {
                var $this = $(item);
                var checked = $this.is(':checked') ? true : false;
                if (index === 0) {
                    flag = checked;
                } else {
                    flag = flag !== checked ? false : flag
                    return flag;
                }
            })
            return flag;
        }
        var initCheckboxs = function() {
            $checkGroup.each(function(index, item) {
                var $this = $(item);
                var $checks = $this.parents('.icheck-group-header').next().find("input[type='checkbox']");
                state[index] = {
                    current: $this,
                    children: $checks,
                }
            })
        }
        initCheckboxs();
        $checkAll.on('ifChecked', function() {
            var $this = $(this);
            if (!triggerCheckedEvent.all) return false;
            checkedAll($checkGroup, true, {
                group: false
            });
            checkedAll($checkItem, true, {
                item: false
            });
        })
        $checkAll.on('ifUnchecked', function() {
            var $this = $(this);
            if (!triggerCheckedEvent.all) return false;
            checkedAll($checkGroup, false, {
                group: false
            });
            checkedAll($checkItem, false, {
                item: false
            });
        })
        $checkGroup.on('ifChecked', function() {
            var $this = $(this);
            if (!triggerCheckedEvent.group) return false;
            checkedAll(state[$this.data('index')].children, true, {
                item: false
            });
            setChecked($checkAll, isGroupCheck($checkGroup), {
                all: false
            });
        })
        $checkGroup.on('ifUnchecked', function() {
            var $this = $(this);
            if (!triggerCheckedEvent.group) return false;
            checkedAll(state[$this.data('index')].children, false, {
                item: false
            });
            setChecked($checkAll, isGroupCheck($checkGroup), {
                all: false
            });
        })

        $checkItem.on('ifChecked', function() {
            var $this = $(this);
            if (!triggerCheckedEvent.item) return false;
            var group = state[$this.data('index')];
            setChecked(group.current, isGroupCheck(group.children), {
                group: false
            });
            setChecked($checkAll, isGroupCheck($checkGroup), {
                all: false
            });
        })
        $checkItem.on('ifUnchecked', function() {
            var $this = $(this);
            if (!triggerCheckedEvent.item) return false;
            var group = state[$this.data('index')];
            setChecked(group.current, isGroupCheck(group.children), {
                group: false
            });
            setChecked($checkAll, isGroupCheck($checkGroup), {
                all: false
            });
        })
    });
</script>
[/@content]
