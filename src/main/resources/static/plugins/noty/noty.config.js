$(function() {
    //theme = 'mint' | 'sunset' | 'relax' | 'metroui' | 'bootstrap-v3' | 'bootstrap-v4' | 'semanticui' | 'nest'
    var notice = function(type) {
        return function(text, timeout, option) {
            var o = {
                type: type,
                layout: 'topRight',
                theme: 'metroui',
                text: text,
                timeout: timeout || 2000,
                progressBar: true,
                closeWith: ['click', 'button'],
                animation: {
                    open: 'noty_effects_open',
                    close: 'noty_effects_close'
                },
                id: false,
                force: false,
                killer: false,
                queue: 'global',
                container: false,
                buttons: [],
                sounds: {
                    sources: [],
                    volume: 1,
                    conditions: []
                },
                titleCount: {
                    conditions: []
                },
                modal: false
            }
            if (option) {
                o = Object.assgin(o, option);
            }
            return new Noty(o);
        }
    }


    var dialogOption = {
        confirmBtn: {
            className: 'btn btn-primary btn-sm',
            label: '确定',
            callback: function(n) {
            },
            attributes: {'data-status': 'ok'},
        },
        cancelBtn: {
            className: 'btn btn-error btn-sm',
            label: '取消',
            callback: function(n) {
            },
            attributes: {'data-status': 'error'},
        }
    }
    var dialog = function(text, confirmBtnCb, cancelBtnCb, onClose, option) {
        if ('function' !== typeof confirmBtnCb) {
            option = confirmBtnCb;
            option = $.extend(true, dialogOption, option);
            confirmBtnCb = option.confirmBtn.callback;
            cancelBtnCb = option.cancelBtn.callback;
            onClose = function() {};
        } else {
            if ('function' !== typeof cancelBtnCb) {
                option = cancelBtnCb;
                option = $.extend(true, dialogOption, option);
                cancelBtnCb = option.cancelBtn.callback;
                onClose = function() {};
            } else {
                if ('function' !== typeof onClose) {
                    option = onClose;
                    option = $.extend(true, dialogOption, option);
                    onClose = function() {};
                } else {
                    option = $.extend(true, dialogOption, option);
                }
            }
        }

        var confirmBtn = option.confirmBtn;
        var cancelBtn = option.cancelBtn;
        var n = new Noty($.extend({
            text: text,
            layout: 'center',
            theme: 'metroui',
            modal: true,
            callbacks: {
                onClose: onClose
            },
            buttons: [
                Noty.button(cancelBtn.label, cancelBtn.className, function() {
                    cancelBtnCb(n)
                    n.close();
                }, cancelBtn.attributes),
                Noty.button(confirmBtn.label, confirmBtn.className, function() {
                    confirmBtnCb(n)
                    n.close();
                }, confirmBtn.attributes),
            ]
        }, option.config || {}))
        return n;
    }

    //确认对话框
    $.dialog = function(text, confirmBtnCb, cancelBtnCb, option) {
        return dialog(text, confirmBtnCb, cancelBtnCb, option || {}).show();
    }

    //消息提示框
    $.notification = {
        success: function(text, timeout, option) {
            notice('success')(text, timeout, option).show();
            return true;
        },
        error: function(text, timeout, option) {
            notice('error')(text, timeout, option).show();
            return false;
        },
        warn: function(text, timeout, option) {
            notice('warning')(text, timeout, option).show();
            return false;
        },
        warning: function(text, timeout, option) {
            notice('warning')(text, timeout, option).show();
            return false;
        },
        alert: function(text, timeout, option) {
            notice('alert')(text, timeout, option).show();
            return false;
        },
        information: function(text, timeout, option) {
            notice('information')(text, timeout, option).show();
            return true;
        }
    }
})