[#include "../../component/content.ftl" /]

[@content title="ZTree" subTitle="树结构演示"]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/zTree/css/metroStyle/metroStyle.css" type="text/css" />
<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">Hover Data Table</h3>
            </div>
            <!-- /.series-header -->
            <div class="box-body">
                <p><a href="http://www.treejs.cn/v3/api.php" target="_blank">其他配置参考文档</a></p>
                <ul id="treeDemo" class="ztree"></ul>
            </div>
            <!-- /.series-body -->
        </div>
        <!-- /.series -->
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->

<script src="${springMacroRequestContext.contextPath}/plugins/zTree/js/jquery.ztree.core.js"></script>
<script>
    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {};
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    var zNodes = [
        {name:"test1", open:true, children:[
            {name:"test1_1"}, {name:"test1_2"}]},
        {name:"test2", open:true, children:[
            {name:"test2_1"}, {name:"test2_2"}]}
    ];
    $(document).ready(function(){
        zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });
</script>
[/@content]
