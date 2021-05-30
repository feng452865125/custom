$(function () {
  'use strict';

  $('.fileupload').fileupload({
    dataType: "text",
    autoUpload: false,
    add: function (e, data) {
      let $formGroup = $(this).parent().parent();
      let $startUpload = $formGroup.find(".startUpload");//上传按钮
      let $cancelUpload = $formGroup.find(".cancelUpload");//取消按钮
      let $previewSuccess = $formGroup.find(".preview .uploadimg");//成功标志
      let $loading = $formGroup.find(".preview .loading");//上传标志
      let url = getUrl(data.files[0]);
      //上传预览
      $previewSuccess.attr("src", url);
      $previewSuccess.css('height','100%');
      $previewSuccess.css('max-width','100%');
      $startUpload.show();
      $cancelUpload.show();
      //上传事件
      $startUpload.on("click", function (e) {
        e.preventDefault();
        data.submit();
        $loading.show();
      });

      //取消上传
      $cancelUpload.on("click", function (e) {
        e.preventDefault();
        $startUpload.hide();
        $cancelUpload.hide();
        $loading.hide();
        $formGroup.find(".preview .success").hide();
        $previewSuccess.attr("src", null);//取消预览值
        $previewSuccess.css('height','');
        $previewSuccess.css('max-width','');
        $formGroup.find(".relFile").val("");//取消表单图片的值
      })
    },
    done: function (e, data) {
      let $formGroup = $(this).parent().parent();
      $formGroup.find(".preview .success").show();
      $formGroup.find(".preview .loading").hide();
      let filepath = data.result;
      $formGroup.find(".relFile").val(filepath);
    },
    fail: function (e, data) {
      let $formGroup = $(this).parent().parent();
      let $loading = $formGroup.find(".preview .loading");//上传标志
      $loading.hide();
      $.notification.error(data);
    }
  });

  //获取图片地址
  function getUrl(file) {
    var url = null;
    if (window.createObjectURL != undefined) {
      url = window.createObjectURL(file);
    } else if (window.URL != undefined) {
      url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) {
      url = window.webkitURL.createObjectURL(file);
    }
    return url;
  }

});
