[#include "../../component/content.ftl" /]
[#include "../../component/searchable.ftl" /]
[#include "../../component/authorize.ftl"]

[@content title="证书查询" subTitle=""]
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
                    </select>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">寓意</span>
                    <input type="hidden" id="styleId" name="styleId">
                    <input id="moral" name="moral" class="form-control moral" placeholder="请输入寓意">
                    <ul class="dropdown2">
                    </ul>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">戒臂宽度</span>
                    <select class="form-control jbKd" id="jbKd" name="jbKd">
                        <option value="">请选择戒臂宽度</option>
                        <option value="1.8">1.8</option>
                        <option value="2.1">2.1</option>
                        <option value="2.4">2.4</option>
                        <option value="2.7">2.7</option>
                        <option value="3.0">3.0</option>
                    </select>
                </div>
               <div class="input-group">
                   <span class="input-group-addon">克拉数</span>
                   <input id="zl" name="zl" class="form-control" placeholder="请输入克拉数查询">
               </div>
                <div class="input-group">
                    <span class="input-group-addon">颜色</span>
                    <select class="form-control zsYs" id="zsYs" name="zsYs">
                        <option value="">请选择颜色</option>
                                [#list zsYsList.keys as zsYs]
                                    <option value="${zsYs.label}">${zsYs.label}</option>
                                [/#list]
                    </select>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">净度</span>
                    <select class="form-control zsJd" id="zsJd" name="zsJd">
                        <option value="">请选择净度</option>
                                [#list zsJdList.keys as zsJd]
                                    <option value="${zsJd.label}">${zsJd.label}</option>
                                [/#list]
                    </select>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">切工</span>
                    <select class="form-control zsQg" id="zsQg" name="zsQg">
                        <option value="">请选择切工</option>
                                [#list zsQgList.keys as zsQg]
                                    <option value="${zsQg.label}">${zsQg.label}</option>
                                [/#list]
                    </select>
                </div>
                 <div class="input-group">
                     <span class="input-group-addon">抛光</span>
                     <select class="form-control zsPg" id="zsPg" name="zsPg">
                         <option value="">请选择抛光</option>
                                [#list zsPgList.keys as zsPg]
                                    <option value="${zsPg.label}">${zsPg.label}</option>
                                [/#list]
                     </select>
                 </div>
                 <div class="input-group">
                     <span class="input-group-addon">对称</span>
                     <select class="form-control zsDc" id="zsDc" name="zsDc">
                         <option value="">请选择对称</option>
                                [#list zsDcList.keys as zsDc]
                                    <option value="${zsDc.label}">${zsDc.label}</option>
                                [/#list]
                     </select>
                 </div>
                [/@searchable]
                <table id="example" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>证书号</th>
                        <th>钻重</th>
                        <th>颜色</th>
                        <th>净度</th>
                        <th>切工</th>
                        <th>抛光</th>
                        <th>对称</th>
                        <th>荧光</th>
                        <th>价格</th>
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
                data.search.search = "";
                $('.form-control.moral')[0].value = data.columns[13].search.search;
                $('.form-control.jbKd')[0].value = data.columns[14].search.search;
                $('.form-control.zsYs')[0].value = data.columns[15].search.search;
                $('.form-control.zsJd')[0].value = data.columns[16].search.search;
                // $('.form-control.zsQg')[0].value = data.columns[18].search.search;
                // $('.form-control.zsPg')[0].value = data.columns[19].search.search;
                // $('.form-control.zsDc')[0].value = data.columns[20].search.search;
                $('#zl')[0].value = data.columns[1].search.search;
            },
            'ordering': true,
            'info': true,
            'autoWidth': false,
            'processing': true,
            'serverSide': true,
            'columns': [
                {data: 'stoneZsh', name: 'stoneZsh'},
                {
                    data: 'zl', name: 'zl', render: function (data) {
                        return data ? data / 1000 : "";
                    }
                },
                {data: 'ys', name: 'ys'},
                {data: 'jd', name: 'jd'},
                {data: 'qg', name: 'qg'},
                {data: 'pg', name: 'pg'},
                {data: 'dc', name: 'dc'},
                {data: 'yg', name: 'yg'},
                {
                    data: 'price', name: 'price', render: function (data, type, full, meta) {
                        return data ? parseInt(data / 100) : "";
                    }
                },
                {data: 'diamondId', name: 'diamondId', visible: false},
                {data: 'productId', name: 'productId', visible: false},
                {data: 'styleId', name: 'styleId', visible: false},
                {data: 'serice', name: 'serice', visible: false},
                {data: 'moral', name: 'moral', visible: false},
                {data: 'jbKd', name: 'jbKd', visible: false},
                {data: 'zsYs', name: 'zsYs', visible: false},
                {data: 'zsJd', name: 'zsJd', visible: false},
                {data: 'zsQg', name: 'zsQg', visible: false},
                {data: 'zsPg', name: 'zsPg', visible: false},
                {data: 'zsDc', name: 'zsDc', visible: false}
            ],
            'ajax': {
                'contentType': 'application/json',
                'url': '${springMacroRequestContext.contextPath}/certificateInquiry/pagination',
                'type': 'POST',
                'beforeSend': function (request) {
                    request.setRequestHeader('X-CSRF-TOKEN', '${_csrf.token}');
                    return request;
                },
                'data': function (d) {
                    return JSON.stringify(d);
                },
            }
        });

    });


    $("#moral").blur("input", function () {
        setTimeout(function () {
            $(".dropdown2").hide();
        }, 300)
    })


    var timer;
    $("#moral").on("input", function (e) {
        if (timer) {
            // $("#searchText").attr("zidingyi", "");
            clearTimeout(timer);
        }
        // console.log("ffff",e.target.value);
        timer = setTimeout(function () {
            var name = e.target.value;
            var series = $("#serice").val();
            $.ajax({
                type: "GET",//请求类型
                url: "${springMacroRequestContext.contextPath}/style/dropDownBox",//请求的url
                data: "name=" + name + "&series=" + series,
                dataType: "json",//ajax接口（请求url）返回的数据类型
                success: function (data) {

                    var open = "";
                    $(".dropdown2").html("")
                    for (var i = 0; i < data.length; i++) {
                        open += "<li id='" + data[i].id + "'>" + data[i].moral + "</li>"
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
        }, 600);
    })

</script>
<style>
    .dropdown2 {
        position: absolute;
        top: 32px;
        left: 53px;
        width: 177px;
        height: 200px;
        overflow: auto;
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
