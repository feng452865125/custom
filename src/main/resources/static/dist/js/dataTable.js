var $checkboxs;
var $checkAll = $('.check-all');
var notTriggerCheckAll = false;
var $domTable;
var $dataTable;

$tableInit = function () {
    $domTable.on('ifChanged', '.check-all', function () {
        if (!$checkboxs) return false;
        if (notTriggerCheckAll) return false;
        var $this = $(this);
        if ($this.is(':checked')) {
            $.each($checkboxs, function (index, item) {
                var $this = $(item);
                if (!$this.attr('disabled')) {
                    $this.iCheck('check')
                }
            })
        } else {
            $.each($checkboxs, function (index, item) {
                var $this = $(item);
                if (!$this.attr('disabled')) {
                    $this.iCheck('uncheck')
                }
            })
        }
    });

    $dataTable.on('draw.dt', function () {
        $checkboxs = $("input[name='ids']");
        $checkboxs.on('ifUnchecked', function () {
            var flag = checkAllcheckbox(true);
            if (!flag) {
                $checkAll.iCheck('uncheck');
                notTriggerCheckAll = false;
            }
        });

        $checkboxs.on('ifChecked', function () {
            var flag = checkAllcheckbox(true);
            if (flag) {
                $checkAll.iCheck('check');
            }
        });

        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        });
    });

    $dataTable.on('preXhr', function () {
        $checkAll.iCheck('uncheck');
    })
}

var checkAllcheckbox = function (checked) {
    if (!$checkboxs) return false;
    var flag = true;
    $.each($checkboxs, function (index, item) {
        var $this = $(item);
        if ($this.attr('disabled')) return true;
        if (checked ? !$(item).is(':checked') : $(item).is(':checked')) {
            flag = false;
            return false;
        }
    });
    return flag;
};
