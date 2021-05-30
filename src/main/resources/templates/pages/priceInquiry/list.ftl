[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="价格查询" subTitle=""]
<link rel="stylesheet" href="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.css">

<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">列表</h3>
            </div>
            <!-- /.series-header -->
            <div class="box-body">
                [@searchable id="searchable" targetId="example"]
                <div class="input-group">
                    <span class="input-group-addon">系列</span>
                    <select id="serice" name="serice" class="form-control">
                        <option value="UNIQUE">UNIQUE</option>
                        [#--<option value="MORE LOVE">MORE LOVE</option>--]
                    </select>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">寓意</span>
                    <input type="hidden" id="styleId" name="styleId" >
                    <input id="moral" name="moral" class="form-control moral" placeholder="请输入寓意">
                    <ul class="dropdown2">
                    </ul>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">戒臂宽度</span>
                    <input id="jbKd" name="jbKd" class="form-control jbKd" placeholder="请输入戒臂宽度">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">颜色</span>
                    <input id="zsYs" name="zsYs" class="form-control zsYs" placeholder="颜色 如:J 或 J,K">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">净度</span>
                    <input id="zsJd" name="zsJd" class="form-control zsJd" placeholder="净度 如:SI 或 SI1,SI2">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">钻重</span>
                    <input id="zl" name="zl" class="form-control" placeholder="请输入钻重">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">切工</span>
                    <input id="zsQg" name="zsQg" class="form-control" placeholder="请输入切工">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">抛光</span>
                    <input id="zsPg" name="zsPg" class="form-control" placeholder="请输入抛光">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">对称</span>
                    <input id="zsDc" name="zsDc" class="form-control" placeholder="请输入对称">
                </div>
                [/@searchable]
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>证书</th>
                        <th>钻重</th>
                        <th>颜色</th>
                        <th>净度</th>
                        <th>切工</th>
                        <th>抛光</th>
                        <th>对称</th>
                        <th>荧光</th>
                        <th>销售价</th>
                        <th>上柜价</th>
                        <th>结算价</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tfoot>
                </table>
            </div>
            <!-- /.series-body -->
        </div>
        <!-- /.series -->
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->

<!-- DataTables -->
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${springMacroRequestContext.contextPath}/plugins/datatables/dataTables.bootstrap.js"></script>
<script>
    $(function () {

        var $form = $('#example');
        var dataTable = $form.DataTable({
            'stateSave': true,
            'stateSaveParams': function (settings, data) {
                console.log("data",data)
                data.search.search = "";
                $('.form-control.moral')[0].value = data.columns[15].search.search;
                $('.form-control.jbKd')[0].value = data.columns[16].search.search;
                $('.form-control.zsYs')[0].value = data.columns[17].search.search;
                $('.form-control.zsJd')[0].value = data.columns[18].search.search;
                $('#zl')[0].value = data.columns[1].search.search;
                // $('#moral').val(data.columns[15].search.search);
                // $('#moral').value = data.columns[15].search.search;

            },
            'ordering': true,
            'info': true,
            'autoWidth': false,
            'processing' : true,
            'serverSide': true,
            'columns': [
                {data: 'zs', name: 'zs'},
                {data: 'zl', name: 'zl', render: function(data) {
                        return data ? data/1000 : "";
                }},
                {data: 'ys', name: 'ys'},
                {data: 'jd', name: 'jd'},
                {data: 'qg', name: 'qg'},
                {data: 'pg', name: 'pg'},
                {data: 'dc', name: 'dc'},
                {data: 'yg', name: 'yg'},
                {data: 'price', name: 'price',render: function ( data, type, full, meta ) {
                    return data ? parseInt(data/100):"";
                    }
                },
                {data: 'sgPrice', name: 'sgPrice',render: function ( data, type, full, meta ) {
                        return data ? parseInt(data/100):"";
                    }
                },
                {data: 'jsPrice', name: 'jsPrice',render: function ( data, type, full, meta ) {
                        return data ? parseInt(data/100):"";
                    }
                },
                [#--{data: 'id', orderabled: false, render: function ( data, type, full, meta ) {--]
                    [#--var operations = [];--]
                    [#--[@authorize ifAnyGranted="permission:view"]--]
                        [#--operations.push('<a redirect href="${springMacroRequestContext.contextPath}/priceInquiry/view/' + data + '/'+full.diamondId+'/'+full.productId+'">详情</a>');--]
                    [#--[/@authorize]--]
                    [#--[@authorize ifAnyGranted="permission:edit"]--]
                        [#--operations.push('<a redirect href="${springMacroRequestContext.contextPath}/permission/edit/' + data + '">编辑</a>');--]
                    [#--[/@authorize]--]
                    [#--[@authorize ifAnyGranted="permission:delete"]--]
                        [#--operations.push('<a class="btn-delete" href="javascript:;" data-id="' + data + '">删除</a>')--]
                    [#--[/@authorize]--]
                    [#--return operations.join(' | ');--]
                [#--}},--]

                {data: 'diamondId', name: 'diamondId',visible:false},
                {data: 'productId', name: 'productId',visible:false},
                {data: 'styleId', name: 'styleId',visible:false},
                {data: 'serice', name: 'serice',visible:false},
                {data: 'moral', name: 'moral',visible: false},
                {data: 'jbKd', name: 'jbKd',visible: false},
                {data: 'zsYs', name: 'zsYs',visible: false},
                {data: 'zsJd', name: 'zsJd',visible: false},
                {data: 'zsQg', name: 'zsQg',visible: false},
                {data: 'zsPg', name: 'zsPg',visible: false},
                {data: 'zsDc', name: 'zsDc',visible: false},
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/priceInquiry/pagination',
                'type': 'POST',
                'beforeSend': function (request) {
                    request.setRequestHeader('X-CSRF-TOKEN', '${_csrf.token}');
                    return request;
                },
                'data': function(d) {
                    return JSON.stringify(d);
                },
            }
         });

        $form.on('click', '.btn-delete', function() {
            var id = $(this).data('id');
            var url = '${springMacroRequestContext.contextPath}/priceInquiry/' + id;
            $.showRemoveDialog(url, '${_csrf.token}', function() {
                dataTable.draw();
            })
        })
    });


    $("#moral").blur("input", function () {
        setTimeout(function() {
            $(".dropdown2").hide();
        },300)
    })


    var timer;
    $("#moral").on("input",function(e){
        if(timer) {
           // $("#searchText").attr("zidingyi", "");
            clearTimeout(timer);
        }
        // console.log("ffff",e.target.value);
        timer = setTimeout(function() {
            var name = e.target.value;
            var series = $("#serice").val();
            $.ajax({
                type: "GET",//请求类型
                url: "${springMacroRequestContext.contextPath}/style/dropDownBox",//请求的url
                data: "name="+name+"&series=" + series,
                dataType: "json",//ajax接口（请求url）返回的数据类型
                success: function (data) {

                    var open = "";
                    $(".dropdown2").html("")
                    for(var i =0; i < data.length; i++){
                        open += "<li id='"+data[i].id+"'>"+data[i].moral+"</li>"
                    }
                    $(".dropdown2").html(open)
                    $(".dropdown2").show();
                    $(".dropdown2").find('li').each(function () { // 传值给input，同时关闭焦点开关
                        $(this).on("click", function () {
                            isBox = false;
                            var text = $(this).text();
                            $("#moral").val(text);
                            $("#styleId").val($(this).attr("id"));
                            $(".dropdown2").hide();
                        })
                    })

                }
            })
        },600);
    })

</script>
<style>
    .dropdown2 {
        position: absolute;
        top: 32px;
        left: 53px;
        width: 177px;
        height: 200px;
        overflow:auto;
        background-color: #FFF;
        border: 1px solid #23a8ce;
        border-top: 0;
        box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
        z-index: 999;
        padding: 0;
        margin: 0;
        display: none;
    }

    .dropdown2 li {
        display: block;
        line-height: 1.42857;
        padding: 0 6px;
        min-height: 1.2em;
        cursor: pointer;
    }

    .dropdown2 li:hover {
        background-color: #23a8ce;
        color: #FFF;
    }
</style>
[/@content]
