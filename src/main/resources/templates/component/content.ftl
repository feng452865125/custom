[#macro content nobar=false title="Page Header" subTitle="Optional description"]
[#if !nobar]
<script>
    $.ajaxSetup({
        cache:true
    });
</script>
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        ${title}
        <small>${subTitle}</small>
    </h1>
    [#if title!="首页"]
    <ol class="breadcrumb">
        <li><a href="${springMacroRequestContext.contextPath}"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li class="active">${title}</li>
    </ol>
    [/#if]
</section>
[/#if]

<!-- Main content -->
<section class="content">

    <!-- Your Page Content Here -->
    [#nested /]

</section>
<!-- /.content -->
[/#macro]
