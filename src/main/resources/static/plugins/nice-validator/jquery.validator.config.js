$(function() {

    // 与bootstrap样式结合，并且自定义消息maker
    $.validator.config({
       validClass: "has-succes",
       invalidClass: "has-error",
       bindClassTo: ".form-group",
       msgClass: "help-block",
       ignore: ".ignore",       // 不验证样式为ignore的dom节点
       msgMaker: function(opt) {
           var html = '';
           if (opt.result) {
               $.each(opt.result, function(i, obj){
                   html += obj.msg + '\r\n';
               });
           } else {
               html += opt.msg;
           }
           return html;
       },
       // 自定义扩展规则
       rules: {
           mobile: [/^1[3-9]\d{9}$/, "请填写有效的手机号"],
           chinese: [/^[\u0391-\uFFE5]+$/, "请填写中文字符"],
           numberAndLetter: [/^[A-Za-z0-9]+$/, "请填写数字或字母"],
           number: [/^[0-9]+$/, "请填写整数"],
           numberInteger: [/^-?[0-9]\d*$/, "请填写整数"],
           noSelect: [/^(?!(-1|0)$)/, "请选择"],
           positiveInteger: [/^\+?[1-9]\d*$/, "请填写正整数"],
       }
    });

});