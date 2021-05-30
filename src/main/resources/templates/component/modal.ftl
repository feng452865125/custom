[#macro modal id="modal" title="title" width="400px" top="25%" closeBtnClass="btn-close" confirmBtnClass="btn-submit" closeBtnLabel="取消" confirmBtnLabel="确认" hideCloseBtn=false]
<style>
</style>
<div class="message-modal">
    <div class="modal" id="${id}">
        <div class="modal-dialog" style="width: ${width}; top: ${top};">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true" style="color: red; font-size: 25px;">&times;</span></button>
                    <h4 class="modal-title">${title}</h4>
                </div>
                <div class="modal-body">
                    [#nested /]
                </div>
                <div class="modal-footer">
                    [#if hideCloseBtn?string == "false"]
                        <button type="button" class="btn btn-default pull-left ${closeBtnClass}" data-dismiss="modal">${closeBtnLabel}</button>
                    [/#if]
                    <button type="button" class="btn btn-primary ${confirmBtnClass}">${confirmBtnLabel}</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
        <!-- /.modal -->
</div>
[/#macro]
