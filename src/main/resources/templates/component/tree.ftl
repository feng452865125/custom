[#macro tree id="treeDemo"]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/zTree/css/metroStyle/metroStyle.css" type="text/css" />
<style>

</style>

<ul id="${id}" class="ztree"></ul>
<!-- /.row -->

<script src="${springMacroRequestContext.contextPath}/plugins/zTree/js/jquery.ztree.core.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/zTree/js/jquery.ztree.excheck.js"></script>
<script>
    $(function(){
        var setting = {
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: { "Y": "", "N": "" }
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentid",
                    rootPId: 0
                }
            },
        };

        $.initTree = function(selector, data, option) {
            delete $.initTree;
            return $.fn.zTree.init($(selector), $.extend(true, setting, option), data);
        }
    });
</script>

[/#macro]