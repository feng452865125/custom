[#include "../component/modal.ftl" /]

[#macro passwordModal id="password-modal" formId="password-form"]
<style>

</style>
    [@modal id="${id}" title="修改密码" width="450px" closeBtnClass="password-close-btn" confirmBtnClass="password-submit-btn" ]
    <form id="${formId}" action="${springMacroRequestContext.contextPath}/user/changePwd" role="form">
        <div class="row">
            <div class="col-xs-offset-1 col-md-10">
                <div class="form-group">
                    <label for="password">旧密码</label>
                    <input type="password" class="form-control" id="oldPassword" name="oldPassword" placeholder="请输入密码"
                           data-rule="required;length(0~32)"
                           data-msg-required="旧密码不能为空"
                    >
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-offset-1 col-md-10">
                <div class="form-group">
                    <label for="password">新密码</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="请输入确认密码"
                           data-rule="required;length(0~32);"
                           data-msg-required="新密码不能为空"
                    >
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-offset-1 col-md-10">
                <div class="form-group">
                    <label for="password">重复新密码</label>
                    <input type="password" class="form-control" id="rePassword" name="rePassword" placeholder="请输入确认密码"
                           data-rule="required;length(0~32);match(password)"
                           data-msg-match="与新密码不一致"
                    >
                </div>
            </div>
        </div>
    </form>

    [/@modal]
<script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/jquery.validator.js?local=zh-CN"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/nice-validator/jquery.validator.config.js"></script>
<script>
    $(function(){
        var $form = $('#${formId}');
        var $modal = $('#${id}');
        var $passwordCloseBtn = $('.password-close-btn');
        var $passwordSubmitBtn = $('.password-submit-btn');

        $passwordSubmitBtn.on('click', function() {
            $form.trigger("validate");
        })

        $modal.on('hidden.bs.modal', function() {
            $form[0].reset();
            $form.validator("cleanUp");
        })

        $form.on('valid.form', function(e, form){
            var data = $form.serializeObject();
            var url = $form.attr('action');

            $.ajax({
                url: url,
                type: 'PATCH',
                data: JSON.stringify(data),
                headers: {
                    "Content-Type": "application/json; charset=utf-8",
                    "X-CSRF-TOKEN": "${_csrf.token}",
                }
            }).done(function(result) {
                $.notification.success('密码修改成功！');
                $modal.modal('hide');
            }).fail(function(error) {
                $.notification.error(error.responseText);
            })
        })
    });
</script>

[/#macro]