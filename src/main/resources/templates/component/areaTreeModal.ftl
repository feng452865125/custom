[#include "./modal.ftl" /]
[#include "./tree.ftl" /]

[#macro areaTreeModal id="modal" treeId="areaTree" title="区域选择" name="areaId" value="[]" text="选择区域" multiselect=true]
<style>
    .container {
        padding: 0 10px;
    }

    .area-names {
        display: inline-block;
        margin-bottom: 10px;
    }
</style>

<div class="form-group">
    <a id="areaName" class="area-names" href="#" data-toggle="modal" data-target="#${id}">${text}</a>
    <input type="hidden" name="${name}" value="${value}"
       data-rule="required;"
       data-msg-required="区域不能为空"
    >
</div>
[@modal id="${id}" title="${title}" confirmBtnClass="area-submit-btn" ]
<div class="container">
    [@tree id="${treeId}"]
    [/@tree]
</div>
[/@modal]

<script>
    $(function(){
        var $submitBtn = $('.area-submit-btn');
        var $areaName = $('#areaName');
        var $areaId = $("#areaName + input[name='${name}']");
        var $areaTreeModal = $('#${id}');

        var treeObj;

        var getFullNodeName = function(node) {
            var name = node.name;
            var getParentNode = function(node) {
                var parentNode = treeObj.getNodesByParam('id', node.parentid, null)[0];
                if (parentNode) {
                    name = parentNode.name + '/' + name;
                    getParentNode(parentNode);
                }
            }
            getParentNode(node);
            return name;
        }

        var uncheckedAll = function(treeId, treeNode) {
            treeObj.checkAllNodes(false);
            return true;
        }

        $.ajax({
            url: '${springMacroRequestContext.contextPath}/area/tree',
            type: 'POST',
            data: { ${_csrf.parameterName}: "${_csrf.token}"}
        }).done(function(data) {
            var value = $areaId.val();
            if ($areaId.val().indexOf('[') !== 0) {
                value = "[" + value + "]";
            }
            var ids = JSON.parse(value);
            var names = [];
            treeObj = $.initTree('#${treeId}', data);
            [#if !multiselect]
                treeObj.setting.callback.beforeCheck = uncheckedAll;
            [/#if]
            $.each(ids, function(index, item) {
                var node = treeObj.getNodesByParam('id', item, null)[0];
                if (node) {
                    treeObj.checkNode(node, true, true);
                    names.push(getFullNodeName(node));
                }

            })
            if (names.length > 0) $areaName.html(names.join('</br>'));
        })

        $submitBtn.on('click', function() {
            var nodes = treeObj.getCheckedNodes(true);
            var names = [];
            var ids = [];
            $.each(nodes, function(index, item) {
                names.push(getFullNodeName(item));
                ids.push(item.id + '');
            })
            $areaName.html(names.join('</br>'));
            [#if multiselect]
                $areaId.val("[" + ids.join(', ') + "]");
            [#else]
                $areaId.val(ids.join(', '));
            [/#if]
            if (ids.length === 0) {
                $areaName.text("${text}");
            }
            $areaTreeModal.modal('hide');
        })
    });
</script>

[/#macro]