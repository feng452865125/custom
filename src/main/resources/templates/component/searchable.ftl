[#macro searchable id="search" targetId="dataTable"]
<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datepicker/datepicker3.css">
<style>
    .searchable {
        margin-bottom: 10px;
    }

    .searchable > .form-inline > div,
    .searchable > .form-inline > button {
        margin-top: 5px;
    }
</style>
<div class="searchable">
    <form id="${id}" class="form-inline">
        [#nested /]
        <button type="button" class="btn btn-flat btn-primary btn-submit">搜索</button>
        <button type="reset" class="btn btn-flat btn-default">重置</button>
    </form>
</div>
<script>
    $(function () {
        $("#${id} button.btn-submit").on("click", function (e) {
            e.preventDefault();

            // 调用datatables搜索
            var $table = $("#${targetId}").DataTable(),
                    $this = $(this),
                    $form = $this.closest("form");

            // 初始化列映射信息
            var map = {};
            var columns = $table.settings().init().columns;
            for (var i = 0; i < columns.length; i++) {
                map[columns[i].name || columns[i].data] = i;
            }

            var data = $form.serializeObject();
            if (data && Object.keys(data).length > 0) {
                var keys = Object.keys(data);
                for (var i = 0; i < keys.length; i++) {
                    var key = keys[i];
                    var value = data[key];
                    $table.column(map[key]).search(value);
                }

                $table.draw();
            }

        });

        //初始化datetimepicker
        if ($('.input-daterange').length > 0) {
            $('.input-daterange').each(function () {
                $(this).datepicker({
                    language: "zh-CN",
                    autoclose: true,
                    clearBtn: true,
                    format: "yyyy-mm-dd"
                });
                // $('.input-daterange').find('input').each(function(){
                //     $(this).datepicker("setDate", new Date());
                // });
            });
        }

    });
</script>
[/#macro]
