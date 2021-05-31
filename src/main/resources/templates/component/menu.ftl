[#include "./authorize.ftl"]
<link rel="stylesheet" href="dist/css/menu.css">
<ul id="menu" class="sidebar-menu">
    <li class="header" style="color:#8aa4af"><i class="fa fa-star"></i> 功能列表</li>
    [@authorize ifAnyGranted="sysPermission:page,sysUser:page,sysRole:page,sysConfig:page"]
        <li class="treeview">
            <a href="#"><i class="fa fa-users"></i> <span>系统管理</span>
                <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
            </a>
            <ul class="treeview-menu">
                [@authorize ifAnyGranted="sysUser:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/SysUser/list"><i class="fa fa-circle"></i> 账号</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="sysRole:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/SysRole/list"><i class="fa fa-circle"></i> 角色</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="sysPermission:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/SysPermission/list"><i
                                    class="fa fa-circle"></i> 权限</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="sysConfig:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/SysConfig/list"><i
                                    class="fa fa-circle"></i> 系统配置</a>
                    </li>
                [/@authorize]
            </ul>
        </li>
    [/@authorize]

    [@authorize ifAnyGranted="series:page, advertisement:page, flowerHead:page, ringArm:page, diamond:page, partsBig:page, groupInfo:page, jewelryType:page,
                            additionalCosts:page, printing:page, sysConfig:page, attachment:page"]
        <li class="treeview">
            <a href="#"><i class="fa fa-cog"></i> <span>系统管理</span>
                <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
            </a>
            <ul class="treeview-menu">
                [@authorize ifAnyGranted="series:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/series/list"><i class="fa fa-circle"></i> 系列维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="advertisement:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/advertisement/list"><i
                                    class="fa fa-circle"></i>
                            广告栏维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="flowerHead:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/flowerHead/list"><i class="fa fa-circle"></i>
                            花头维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="ringArm:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/ringArm/list"><i class="fa fa-circle"></i>
                            戒臂维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="diamond:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/diamond/list"><i class="fa fa-circle"></i>
                            钻石维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="partsBig:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/partsBig/list"><i class="fa fa-circle"></i>
                            大克拉钻石维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="groupInfo:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/groupInfo/list"><i class="fa fa-circle"></i>
                            组信息维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="jewelryType:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/jewelryType/list"><i class="fa fa-circle"></i>
                            类型维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="uniqueGroup:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/uniqueGroup/list"><i class="fa fa-circle"></i>
                            unique类别维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="attachment:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/attachment/list"><i class="fa fa-circle"></i>
                            图片附件维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="additionalCosts:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/additionalCosts/list"><i
                                    class="fa fa-circle"></i>
                            附加费用维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="printing:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/printing/list"><i class="fa fa-circle"></i>
                            印花维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="dataSynch:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/dataSynch/list"><i class="fa fa-circle"></i>
                            数据同步</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="sysConfig:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/sysConfig/list"><i class="fa fa-circle"></i>
                            系统配置</a>
                    </li>
                [/@authorize]
            </ul>
        </li>
    [/@authorize]

    [@authorize ifAnyGranted="style:page, product:page, customBlack:page, thirdStone:page"]
        <li class="treeview">
            <a href="#"><i class="fa fa-list"></i> <span>产品管理</span>
                <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
            </a>
            <ul class="treeview-menu">
                [@authorize ifAnyGranted="style:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/style/list"><i class="fa fa-circle"></i> 样式维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="product:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/product/list"><i class="fa fa-circle"></i>
                            产品维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="customBlack:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/customBlack/list"><i class="fa fa-circle"></i>
                            石头黑名单维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="thirdStone:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/thirdStone/list"><i class="fa fa-circle"></i>
                            第三方石头维护</a>
                    </li>
                [/@authorize]
            </ul>
        </li>
    [/@authorize]

    [@authorize ifAnyGranted="dadaJewelryType:page, dadaSeries:page, dadaStyle:page"]
        <li class="treeview">
            <a href="#"><i class="fa fa-list"></i> <span>dada管理</span>
                <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
            </a>
            <ul class="treeview-menu">
                [@authorize ifAnyGranted="dadaJewelryType:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/dadaJewelryType/list"><i
                                    class="fa fa-circle"></i>
                            类别维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="dadaSeries:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/dadaSeries/list"><i class="fa fa-circle"></i>
                            系列维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="dadaStyle:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/dadaStyle/list"><i class="fa fa-circle"></i>
                            样式维护</a>
                    </li>
                [/@authorize]
            </ul>
        </li>
    [/@authorize]

    [@authorize ifAnyGranted="ordersStyle:page, orders:page"]
        <li class="treeview">
            <a href="#"><i class="fa fa-list-ul"></i> <span>订单管理</span>
                <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
            </a>
            <ul class="treeview-menu">
                [@authorize ifAnyGranted="ordersStyle:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/ordersStyle/list"><i class="fa fa-circle"></i>
                            预约查询</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="orders:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/orders/list"><i class="fa fa-circle"></i> 订单查询</a>
                    </li>
                [/@authorize]
            </ul>
        </li>
    [/@authorize]

    [@authorize ifAnyGranted="priceInquiry:page, certificateInquiry:page"]
        <li class="treeview">
            <a href="#"><i class="fa fa-list-ul"></i> <span>查询（价格、证书）</span>
                <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
            </a>
            <ul class="treeview-menu">
                [@authorize ifAnyGranted="priceInquiry:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/priceInquiry/list"><i
                                    class="fa fa-circle"></i>
                            价格查询</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="certificateInquiry:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/certificateInquiry/list"><i
                                    class="fa fa-circle"></i>
                            证书查询</a>
                    </li>
                [/@authorize]
            </ul>
        </li>
    [/@authorize]

    [@authorize ifAnyGranted="basePrice:page"]
        <li class="treeview">
            <a href="#"><i class="fa fa-th-list"></i> <span>4C标准管理</span>
                <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
            </a>
            <ul class="treeview-menu">
                [@authorize ifAnyGranted="basePrice:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/basePrice/list"><i class="fa fa-circle"></i>
                            基价维护</a>
                    </li>
                [/@authorize]
            </ul>
        </li>
    [/@authorize]

    [@authorize ifAnyGranted="customCardTask:page, customCard:page"]
        <li class="treeview">
            <a href="#"><i class="fa fa-th-list"></i> <span>实体卡业务管理</span>
                <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
            </a>
            <ul class="treeview-menu">
                [@authorize ifAnyGranted="customCardTask:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/customCardTask/list"><i
                                    class="fa fa-circle"></i>
                            制卡任务维护</a>
                    </li>
                [/@authorize]
                [@authorize ifAnyGranted="customCard:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/customCard/list"><i class="fa fa-circle"></i>
                            卡片维护</a>
                    </li>
                [/@authorize]
            </ul>
        </li>
    [/@authorize]

    [@authorize ifAnyGranted="thirdSupplier:page"]
        <li class="treeview">
            <a href="#"><i class="fa fa-th-list"></i> <span>第三方管理</span>
                <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
            </a>
            <ul class="treeview-menu">
                [@authorize ifAnyGranted="thirdSupplier:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/thirdSupplier/list"><i
                                    class="fa fa-circle"></i>
                            钻石供应商维护</a>
                    </li>
                [/@authorize]
            </ul>
        </li>
    [/@authorize]

    [@authorize ifAnyGranted="useLog:page"]
        <li class="treeview">
            <a href="#"><i class="fa fa-th-list"></i> <span>报表管理</span>
                <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
            </a>
            <ul class="treeview-menu">
                [@authorize ifAnyGranted="useLog:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/useLog/list"><i class="fa fa-circle"></i> 报表查询</a>
                    </li>
                [/@authorize]
            </ul>
        </li>
    [/@authorize]
    [@authorize ifAnyGranted="diamondKela:page"]
        <li class="treeview">
            <a href="#"><i class="fa fa-th-list"></i> <span>克拉展管理</span>
                <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
            </a>
            <ul class="treeview-menu">
                [@authorize ifAnyGranted="diamondKela:page"]
                    <li><a href="${springMacroRequestContext.contextPath}/diamondKela/list"><i class="fa fa-circle"></i>
                            钻石管理</a>
                    </li>
                [/@authorize]
            </ul>
        </li>
    [/@authorize]

    [@authorize ifAnyGranted="admin"]
        <li class="treeview">
            <a href="#"><i class="fas fa-user-shield"></i> <span>开发者测试</span>
                <span class="pull-right-container">
<i class="fa fa-angle-left pull-right"></i>
</span>
            </a>
            <ul class="treeview-menu">
                <li><a href="${springMacroRequestContext.contextPath}/developer/list"><i class="fa fa-circle"></i> 开发者测试</a>
                </li>
            </ul>
        </li>
    [/@authorize]
</ul>

