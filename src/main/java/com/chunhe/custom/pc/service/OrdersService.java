package com.chunhe.custom.pc.service;

import com.chunhe.custom.pc.mapper.*;
import com.chunhe.custom.pc.model.*;
import com.chunhe.custom.pc.service.mail.MailService;
import com.chunhe.custom.pc.xml.*;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.model.SysUser;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.service.SysUserService;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.DateUtil;
import com.chunhe.custom.pc.thirdSupplier.rows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by white 2018年7月14日15:00:28
 */
@Service
public class OrdersService extends BaseService<Orders> {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private PartsService partsService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private StyleService styleService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserDetailMapper sysUserDetailMapper;

    @Autowired
    private EnumCodeService enumCodeService;

    @Autowired
    private PrintingService printingService;

    @Autowired
    private AdditionalCostsService additionalCostsService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private ThirdSupplierCacheService thirdSupplierCacheService;

    @Autowired
    private BasePriceService basePriceService;

    @Autowired
    private PartsBigService partsBigService;

    @Autowired
    private PartsBigMapper partsBigMapper;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    TransactionDefinition transactionDefinition;


    @Autowired
    private BasePriceMapper basePriceMapper;
    @Autowired
    private ProductService productService;

    @Value("${storePriceMultiple}")
    private static String storePriceMultiple;

    @Value("${basePriceMultiple}")
    private static String basePriceMultiple;

    @Value("${basePriceMultiple1}")
    private static String basePriceMultiple1;

    @Value("${basePriceMultiple2}")
    private static String basePriceMultiple2;

    @Value("${keerokUrl}")
    private static String keerokUrl = "http://192.168.58.73:9080/TBAPApplicationService/SoapBoxer/";

    @Value("${recipientAddress}")
    public static String recipientAddress;

    @Value("${senderAddress}")
    public static String senderAddress;

    @Value("${senderAccount}")
    public static String senderAccount;

    @Value("${senderPassword}")
    public static String senderPassword;

    @Value("${mailSmtp}")
    public static String mailSmtp;

    @Value("${orderTitle}")
    public static String orderTitle;

    @Value("${cancelTitle}")
    public static String cancelTitle;

    //全部列表APP
    public List<Orders> findOrdersListApp(Orders orders) {
        List<Orders> ordersList = ordersMapper.findOrdersListApp(orders);
        //样式
        for (int i = 0; i < ordersList.size(); i++) {
            Orders ord = ordersList.get(i);

            //把价格四舍五入
            BigDecimal p1 = new BigDecimal(ord.getPrice());
            BigDecimal p2 = new BigDecimal(100);
            int price = p1.divide(p2, 2, BigDecimal.ROUND_HALF_UP).intValue();
            ord.setPrice(price);
            //人员姓名
            SysUserDetail sales = sysUserDetailMapper.selectByPrimaryKey(ord.getUserSalesId().longValue());
            if (sales != null) {
                ord.setUserSalesName(sales.getName());
            }
            if (ord.getStyleId() != null) {
                Style style = styleService.getStyle(ord.getStyleId());
                ord.setStyle(style);
            }
        }
        return ordersList;
    }

    //全部列表
    public List<Orders> findOrdersList(Orders orders) {
        List<Orders> ordersList = ordersMapper.findOrdersList(orders);
        return ordersList;
    }

    /**
     * 查询数据
     */
    public List<Orders> ordersList(DataTablesRequest dataTablesRequest) {
        Orders orders = new Orders();
        //排序
        String orderBy = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orderBy)) {
            orders.setOrderBy(orderBy);
        }
        //订单号
        DataTablesRequest.Column code = dataTablesRequest.getColumn("code");
        if (StringUtils.isNotBlank(code.getSearch().getValue())) {
            orders.setCode(code.getSearch().getValue());
        }
        //店铺名、店铺编号
        DataTablesRequest.Column storeCode = dataTablesRequest.getColumn("storeCode");
        if (StringUtils.isNotBlank(storeCode.getSearch().getValue())) {
            orders.setStoreCode(storeCode.getSearch().getValue());
        }
        //包含产品
        DataTablesRequest.Column productName = dataTablesRequest.getColumn("productName");
        if (StringUtils.isNotBlank(productName.getSearch().getValue())) {
            orders.setProductName(productName.getSearch().getValue());
        }
        //证书号
        DataTablesRequest.Column zsBh = dataTablesRequest.getColumn("zsBh");
        if (StringUtils.isNotBlank(zsBh.getSearch().getValue())) {
            orders.setZsBh(zsBh.getSearch().getValue());
        }
        //新，下单时间
        DataTablesRequest.Column startDateColumn = dataTablesRequest.getColumn("startDate");
        if (StringUtils.isNotBlank(startDateColumn.getSearch().getValue())) {
            String startDate = ConvertUtil.convert(startDateColumn.getSearch().getValue(), String.class);
            orders.setStartDate(DateUtil.getDateStart(DateUtil.parseDate(startDate, "yyyy-MM-dd")));
        }
        //多表关联，不用example
        List<Orders> ordersList = this.findOrdersList(orders);
        return ordersList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public Orders getOrders(Long id) {
        //订单信息
        Orders orders = new Orders();
        orders.setId(id);
        Orders or = ordersMapper.getOrders(orders);
        //成品数据
        or.setPrice(or.getPrice() / 100);
        Product product = new Product();
        Product pro = new Product();
        if (or.getProductId() != null && !"".equals(or.getProductId())) {
            product.setId(Long.valueOf(or.getProductId()));
            pro = productMapper.getProduct(product);
        }
        if (or.getProductCode() != null && !"".equals(or.getProductCode())) {
            product.setCode(or.getProductCode());
        }


        or.setProduct(pro);
        //钻石数据
        or.setParts(this.getOrderParts(or));
        //戒托寓意（styleId)
        Style style = styleService.selectByKey(or.getStyleId());
        if (style == null) {
            style = new Style();
        }
        or.setStyleMoral(style.getMoral());
        //人员姓名
        SysUser user = sysUserService.selectByKey(or.getUserId().longValue());
        SysUserDetail operator = sysUserDetailMapper.selectByPrimaryKey(or.getUserOperatorId().longValue());
        SysUserDetail sales = sysUserDetailMapper.selectByPrimaryKey(or.getUserSalesId().longValue());
        if (user != null) {
            or.setUserName(user.getName());
        }
        if (operator != null) {
            or.setUserOperatorName(operator.getName());
        }
        if (sales != null) {
            or.setUserSalesName(sales.getName());
        }

        if (or.getProduct().getCode() != null && "200-100".equals(or.getProduct().getCode()))
            or.getProduct().setKtCode(null);
        return or;
    }


    /**
     * 查询详情app
     *
     * @param id
     * @return
     */
    @Transactional
    public Orders getOrdersApp(Long id) {
        Orders orders = new Orders();
        orders.setId(id);
        Orders or = ordersMapper.getOrders(orders);

        BigDecimal p1 = new BigDecimal(or.getPrice());
        BigDecimal p2 = new BigDecimal(100);
        int price = p1.divide(p2, 2, BigDecimal.ROUND_HALF_UP).intValue();
        or.setPrice(price);

        if (or.getAddCostsId() != null) {
            AdditionalCosts additionalCosts = additionalCostsService.getAdditionalCosts(or.getAddCostsId());
            or.setAddCostsName(additionalCosts.getName());
        }
        //样式详情
        if (or.getStyleId() != null) {
            Style style = styleService.getStyle(or.getStyleId());
            or.setStyle(style);
        }
        //戒托数据
        if (or.getKtCode() != null && !or.getKtCode().equals("")) {
            Product product = new Product();
            product.setKtCode(or.getKtCode());
            Product pro = productMapper.getProduct(product);
            or.setProduct(pro);
        }
        //钻石数据
        or.setParts(this.getOrderParts(or));
        return or;
    }

    /**
     * getOrder时的钻石信息
     *
     * @param orders
     * @return
     */
    public Parts getOrderParts(Orders orders) {
        Parts parts = new Parts();
        if (!StringUtils.isEmpty(orders.getDiamondCode())) {
            if (!orders.getStoneType().equals("big")) {
                Parts pa = new Parts();
                pa.setType(Parts.TYPE_DIAMOND);
                if (!StringUtils.isEmpty(orders.getStoneBatch())) {
                    //批次不为空，KEERSAP
                    pa.setBatch(orders.getStoneBatch());
                }
                if (!StringUtils.isEmpty(orders.getStoneZsbh())) {
                    //证书号不为空
                    pa.setExZsBh(orders.getStoneZsbh());
                }
                pa.setCode(orders.getDiamondCode());//物料编码（其他家唯一，KEERSAP家不唯一）
                parts = partsMapper.getParts(pa);
            } else {
                PartsBig partsBig = new PartsBig();
                partsBig.setZsKh(orders.getDiamondCode());
                PartsBig big = partsBigMapper.getPartsBig(partsBig);
                parts.setExZsZl(big.getZsZl());
                parts.setExZsYs(big.getZsYs());
                parts.setExZsJd(big.getZsJd());
                parts.setExZsQg(big.getZsQg());
                parts.setExZsZs("GIA");
                parts.setExZsBh(big.getZsZsh());
            }
            if (parts == null) {
                return new Parts();
            }
            if (parts.getExZsQg().equals("EX")
                    && parts.getExZsPg().equals("EX")
                    && parts.getExZsDc().equals("EX")) {

            } else {
                parts.setExZsQg("VG");
                parts.setExZsPg("VG");
                parts.setExZsDc("VG");
            }
        }
        return parts;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> styleMap) {

        return ServiceResponse.succ("添加成功");
    }


    public ServiceResponse update2(Map<String, Object> ordersMap) {
        Long id = ConvertUtil.convert(ordersMap.get("id"), Long.class);
        Orders orders = this.selectByKey(id);
        String zsBh = ConvertUtil.convert(ordersMap.get("zsBh"), String.class).trim();
        if (StringUtils.isEmpty(zsBh)) {
            return ServiceResponse.error("证书编号不能为空");
        }
        int result = 0;
        if (!orders.getStoneType().equals("big")) {
            //先找当前的，再找改动证书编号的
            Parts orderPart = new Parts();
            orderPart.setCode(orders.getDiamondCode());
            Parts local = partsMapper.getParts(orderPart);
            Parts target = partsService.getInfoWithZsbh(zsBh);
            if (target != null) {
                target.setLockStatus(ThirdSupplier.STONE_STATUS_LOCK);
                partsService.updateNotNull(target);
                local.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
                partsService.updateNotNull(local);
                orders.setDiamondCode(target.getCode());
                result = this.updateNotNull(orders);
            } else {
                local.setExZsBh(zsBh);
                result = partsService.updateNotNull(local);
            }
        } else {
            PartsBig orderPartsBig = new PartsBig();
            orderPartsBig.setZsKh(orders.getDiamondCode());
            PartsBig local = partsBigMapper.getPartsBig(orderPartsBig);
            PartsBig target = partsBigService.getInfoWithZsbh(zsBh);
            if (target != null) {
                target.setLockStatus(ThirdSupplier.STONE_STATUS_LOCK);
                partsBigService.updateNotNull(target);
                local.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
                partsBigService.updateNotNull(local);
                orders.setDiamondCode(target.getZsKh());
                result = this.updateNotNull(orders);
            } else {
                local.setZsZsh(zsBh);
                result = partsBigService.updateNotNull(local);
            }
        }
        if (result > 0) {
            return ServiceResponse.succ("更新成功");
        }
        return ServiceResponse.error("更新失败");
    }


    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> ordersMap) {
        Long id = ConvertUtil.convert(ordersMap.get("id"), Long.class);
        String code = ConvertUtil.convert(ordersMap.get("code"), String.class).trim();
        String ktCode = ConvertUtil.convert(ordersMap.get("ktCode"), String.class).trim();
        Orders orders = this.selectByKey(id);

        if (code == null || "".equals(code) || ktCode == null || "".equals(ktCode)) {
            return ServiceResponse.error("请同时输入,SKU和客定码数据");
        }
        Product product = productMapper.getProductId(code, ktCode, orders.getHand());
        if (product == null) {
            return ServiceResponse.error("无匹配的SKU和客定码数据");
        }
        orders.setKtCode(ktCode);
        orders.setProductCode(code);
        orders.setProductId(product.getId().toString());

        String data = up(orders);
        if (data != null) {
            return ServiceResponse.error(data);
        }

        int result = updateNotNull(orders);

        if (result > 0) {
            return ServiceResponse.succ("更新成功");
        }
        return ServiceResponse.error("更新失败");
    }


    public String up(Orders orders) {
//        String certificate = "国检证书不分级";
//        if (orders.isCertificate())
//            certificate = "国检证书分级";
        //店铺信息，制单人信息
        SysUser user = sysUserService.selectByKey(orders.getUserId().longValue());
        SysUserDetail operator = sysUserDetailMapper.selectByPrimaryKey(orders.getUserOperatorId().longValue());

        int diamondPrice = 0;//钻石价格
        int diamondOriginalPrice = 0;//钻石原价
        double diamondZl = 0;//钻石质量
        String diamondQg = "";//钻石切工
        String FJG = "";//供应商采购价格，拿的dollarPrice字段
        String FGYSBM = "";//供应商编码
        int productPrice = 0;
        Product product = new Product();
        Product pr = new Product();
        if (orders.getProductCode() != null) {
            product.setCode(orders.getProductCode());
            pr = productMapper.getProduct(product);
            orders.setKtCode(pr.getKtCode());
            productPrice = pr.getPrice();
        }
        if (orders.getProductId() != null && !"".equals(orders.getProductId())) {
            product.setId(Long.valueOf(orders.getProductId()));
            pr = productMapper.getProduct(product);
            orders.setKtCode(pr.getKtCode());
            productPrice = pr.getPrice();
        }
        if (!orders.getStoneType().equals("big")) {
            //钻石--供应商优先级（一），价格优先级（二）
            Parts pa = new Parts();
            pa.setType(Parts.TYPE_DIAMOND);
            pa.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
            //钻石的属性
            BasePrice basePrice = basePriceService.selectByKey(orders.getBaseId().longValue());
            if (basePrice != null) {
                pa.setExZsQg(basePrice.getQg());
                pa.setExZsPg(basePrice.getPg());
                pa.setExZsDc(basePrice.getDc());
                pa.setExZsYs(basePrice.getYs());
                pa.setExZsJd(basePrice.getJd());
                pa.setExZsYg(basePrice.getYg());
                pa.setExZsZs(basePrice.getZs());
                pa.setExZsZlMin(basePrice.getKlMin());
                pa.setExZsZlMax(basePrice.getKlMax());
            }
            if (!StringUtils.isEmpty(orders.getPartsId())) {
                Parts pp = partsService.selectByKey(Long.valueOf(orders.getPartsId()));
                if (pp != null) {
                    pa.setExZsZl(pp.getExZsZl());
                }
            }
            Parts diamond = partsMapper.getDiamondByBaseScore(pa);
            if (diamond == null) {
                throw new RFException("钻石数据异常，请重新选择");
            }
            orders.setDiamondCode(diamond.getCode());
            orders.setExYs(diamond.getExZsYs());
            orders.setExDc(diamond.getExZsDc());
            orders.setExJd(diamond.getExZsJd());
            orders.setExPg(diamond.getExZsPg());
            orders.setExYg(diamond.getExZsYg());
            diamondQg = diamond.getExZsQg();
            //店铺信息里有门店销售系数
            String priceMultipleStore = user.getPriceMultiple();
            String storeMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
            if ("".equals(user.getPriceMultiple()) || user.getPriceMultiple() == null) {
                //门店系数没有设置或者不存在，再从系统配置里拿系数
                priceMultipleStore = storeMultiple;
            }
            //钻石价格=基价*门店系数*基价系数（人民币）
            diamondZl = new BigDecimal(diamond.getExZsZl())
                    .divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            diamondPrice = new BigDecimal(new Double(basePrice.getPrice()))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(priceMultipleStore, 1))))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(diamondZl + "", 1))))
                    .intValue();
            //原价
            diamondOriginalPrice = new BigDecimal(new Double(basePrice.getPrice()))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(storeMultiple, 1))))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(diamondZl + "", 1))))
                    .intValue();
            //钻石的采购价格（供应商报价--美元）//个别家报人民币
            Float thirdDollarPrice = diamond.getDollarPrice().floatValue() / 100;
            FJG = thirdDollarPrice.toString();
            //供应商信息，供应商编码
            ThirdSupplier thirdSupplier = thirdSupplierCacheService.getCacheThirdSupplierManager(diamond.getCompany());
            if (thirdSupplier == null
                    || thirdSupplier.getId() == null
                    || thirdSupplier.getStatus() == Boolean.FALSE) {
                throw new RFException("供应商信息异常，或供应商管理中未开启合作开关");
            }
            FGYSBM = thirdSupplier != null ? thirdSupplier.getSapCode() : "";
        } else {
            //大克拉无主数据
            PartsBig partsBig = partsBigService.selectByKey(orders.getBaseId().longValue());
            orders.setDiamondCode(partsBig.getZsKh());
            orders.setExYs(partsBig.getZsYs());
            orders.setExJd(partsBig.getZsJd());
            orders.setExDc(partsBig.getZsDc());
            orders.setExPg(partsBig.getZsPg());
            orders.setExYg(partsBig.getZsYg());
            diamondQg = partsBig.getZsQg();
            diamondPrice = new BigDecimal(new Double(partsBig.getZsPrice())).intValue();
            diamondOriginalPrice = diamondPrice;
            diamondZl = new BigDecimal(partsBig.getZsZl())
                    .divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            FJG = new BigDecimal(partsBig.getZsMj())
                    .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).toString();//采购价
            FGYSBM = "";//供应商编码
            //主数据(大克拉无主数据，所以客户选择主数据，价格采用product表里设置的100-200的戒托价格)
            Product bigP = new Product();
            bigP.setZl(partsBig.getZsZl());
            Product pp = productMapper.getProductListByPartBig(bigP);
            if (pp != null) {
                productPrice = pp.getPrice();
            }
        }

        //钻石价格--细表2
        int zsPrice = new BigDecimal(diamondPrice).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).intValue();
        String FAMOUNT_SASE_zs = zsPrice + "";
        Float skuPrice = new Integer(productPrice).floatValue() / 100;
        String FAMOUNT_SASE_sku = skuPrice.toString();
        int zsPriceOriginal = new BigDecimal(diamondOriginalPrice).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).intValue();
        String FAMOUNT_SASE_zs_origin = zsPriceOriginal + "";
        //总价格（约定金额/定制金额）--主表
        Float allPrice = skuPrice + zsPrice;
        String FAMOUNT_SASE = allPrice.toString();
        orders.setPrice(diamondPrice + productPrice);
        //原价
        Float allPriceOriginal = skuPrice + zsPriceOriginal;
        String FAMOUNT_SASE_ORIGINAL = allPriceOriginal.toString();

        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(orders.getCreateDate());
        String hasFs = pr.getExFsCount() != null && pr.getExFsCount() != 0 ? "是" : "否";
        //附加费用+附加描述
        String FADD_FEE = "0";
        String FEXPLANE = "0";
        if (orders.getAddCostsId() != null) {
            Float aPrice = orders.getAddCostsPrice().floatValue() / 100;
            FADD_FEE = aPrice.toString();
            AdditionalCosts additionalCosts = additionalCostsService.getAdditionalCosts(orders.getAddCostsId());
            FEXPLANE = additionalCosts.getName();
        }


        //钻石重量
        String ZZSZ = String.valueOf(diamondZl);
        String hasZs = orders.getBaseId() != null ? "K10" : "K20";

        XMLRequest xmlRequest = new XMLRequest("2.0", "{1506DC71-E410-4610-BE04-E7766C2F8E50}");
        XMLParams xmlParams = new XMLParams();
        XMLMetaData xmlMetaData = new XMLMetaData();
        SQLBuilderItem sqlBuilderItem = new SQLBuilderItem("{607A5BD4-ECC4-4597-AB35-65E869528FDE}", "", "", "true");
        Save save = new Save();
        ROWDATA rowdata = new ROWDATA();
        ROW[] row = new ROW[1];
        row[0] = new ROW("4", orders.getCode(), user.getUsername() + "0001", "1", FAMOUNT_SASE,
                FAMOUNT_SASE_ORIGINAL, orders.getCustomerName(), orders.getCustomerMobile(),
                time, DateUtil.formatDate(orders.getPlanTime()), hasFs, FADD_FEE, FEXPLANE,
                "", operator.getName(), operator.getCode(), hasZs, orders.getLettering());
        //POS系统转换编码
        String zyy = enumCodeService.getEnumCodeByName(pr.getExYy(), "ZYY");//寓意
        String zlsYs = enumCodeService.getEnumCodeByName(orders.getExYs(), "ZDMFJYSFW");//钻石颜色范围
        String zjsys = enumCodeService.getEnumCodeByName(orders.getColor(), "ZDMJSYS");//金属颜色
        String zjsca = enumCodeService.getEnumCodeByName(orders.getMaterial(), "ZDMJL");//金属材质
        String zjd = enumCodeService.getEnumCodeByName(orders.getExJd(), "ZDMFJJDFW");//钻石净度范围
        //2021年4月21日12:16:58 pos传参和展示一样，3EX和3VG
        String zqg = "";//切工
        if ("EX".equals(diamondQg)
                && "EX".equals(orders.getExPg())
                && "EX".equals(orders.getExDc())) {
            zqg = enumCodeService.getEnumCodeByName("EX", "ZQG");//切工
        } else {
            zqg = enumCodeService.getEnumCodeByName("VG", "ZQG");//切工
        }
        String zpg = zqg;//抛光
        String zdc = zqg;//对称
//        String zpg = enumCodeService.getEnumCodeByName(orders.getExPg(), "ZPG");//抛光
//        String zdc = enumCodeService.getEnumCodeByName(orders.getExDc(), "ZDC");//对称
        String zyg = "10";//枚举表里荧光none, 基价表里是N
        String zzsxz = "圆形";//主石形状
        String zym = "01";//腰码无
        String zzssl = "02";//钻石（主石石类）

        SQLBuilderItem sqlBuilderItem1 = new SQLBuilderItem("{D272A064-04BF-46FF-B043-8115F20E7CFF}", "", "", "true");
        Save save1 = new Save();
        ROWDATA rowdata1 = new ROWDATA();
        ROW[] row1 = new ROW[2];
        row1[0] = new ROW("4", orders.getCode(), orders.getKtCode(), pr.getCode(), "1",
                FAMOUNT_SASE_sku, FAMOUNT_SASE_sku, zyy, zlsYs, zjsys, zjsca, zjd,
                zpg, zdc, zyg, orders.getHand(), ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, "",
                "", "", "", orders.getBornStone(), orders.getStoneZsbh(), orders.getStoneNxbs());
        row1[1] = new ROW("4", orders.getCode(), "DZ" + orders.getDiamondCode(), pr.getCode(), "1",
                FAMOUNT_SASE_zs, FAMOUNT_SASE_zs_origin, zyy, zlsYs, zjsys, zjsca, zjd,
                zpg, zdc, zyg, "0", ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, "",
                FGYSBM, "DZ" + orders.getDiamondCode(), FJG, "", orders.getStoneZsbh(), orders.getStoneNxbs());
        rowdata1.setROW(row1);

        rowdata.setROW(row);
        save.setROWDATA(rowdata);
        sqlBuilderItem.setSave(save);
        save1.setROWDATA(rowdata1);
        sqlBuilderItem1.setSave(save1);
        sqlBuilderItem.setSQLBuilderItem(sqlBuilderItem1);
        xmlMetaData.setSQLBuilderItem(sqlBuilderItem);
        xmlRequest.setXMLParams(xmlParams);
        xmlRequest.setXMLMetaData(xmlMetaData);

        System.out.println(xmlRequest);
        XMLUtil xmlUtil = new XMLUtil();
        String posXml = xmlUtil.beanToXML(xmlRequest);
        //先拿数据库配置里的pos下单地址
        String posUrl = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_ORDER_POS, keerokUrl);

        String result2 = null;
        try {
            result2 = xmlUtil.toPost(posXml, posUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("result||传POS接口=" + result2);
        XMLResponse response = xmlUtil.XMLToBean(result2);
        if (response == null || response.getXMLResult() != true) {
            throw new RFException("pos发送失败");
        }
        return null;
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional
    public ServiceResponse deleteById(Long id) {
        Orders orders = selectByKey(id);
        if (expireNotNull(orders) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

    /**
     * 生成订单（普通款）
     *
     * @param orders
     */
    @Transactional
    public void createOrders(Orders orders) throws Exception {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
//        String certificate = "国检证书不分级";
//        if (orders.isCertificate())
//            certificate = "国检证书分级";
        //验证手机号
        if (!CheckUtil.isMobileNO(orders.getCustomerMobile())) {
            throw new RFException("手机号码未知");
        }
        //验证约定交货日期
        if (CheckUtil.checkNull(orders.getPlanTime())) {
            throw new RFException("约定交货日期未知");
        }
        //店铺信息，制单人信息
        SysUser user = sysUserService.selectByKey(orders.getUserId().longValue());
        SysUserDetail operator = sysUserDetailMapper.selectByPrimaryKey(orders.getUserOperatorId().longValue());
        if (!user.getEnabled() || user.getExpireDate() != null) {
            throw new RFException(405, "店铺账号未启用!");
        }
        //订单号规则再定
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int randomNum = (int) (new Random().nextDouble() * (9999 - 1000 + 1)) + 1000;// 获取4位随机数
        orders.setCode(time + randomNum);
        int diamondPrice = 0;//钻石价格
        int diamondOriginalPrice = 0;//钻石原价
        double diamondZl = 0;//钻石质量
        String diamondQg = "";//钻石切工
        String diamondCompany = "";//钻石公司
        String diamondZsbh = "";//钻石证书编号
        String FJG = "";//供应商采购价格，拿的dollarPrice字段
        String FGYSBM = "";//供应商编码
        String diamondSaleBack = "";//HB2下单用到返点
        String diamondBatch = "";//KEERSAP下单用到批次号
        int productPrice = 0;
        Product product = new Product();
        //成品，拿ktCode
        if (!StringUtils.isEmpty(orders.getProductCode())) {
            product.setCode(orders.getProductCode());
        }
        if (!StringUtils.isEmpty(orders.getProductId())) {
            product.setId(Long.valueOf(orders.getProductId()));
        }
        Product pr = productMapper.getProduct(product);
        if (pr != null && pr.getId() != null) {
            orders.setKtCode(pr.getKtCode());
            productPrice = pr.getPrice();
        } else {
            pr = new Product();
            orders.setKtCode(pr.getKtCode());
        }
        if (!orders.getStoneType().equals("big")) {
            //钻石--供应商优先级（一），价格优先级（二）
            Parts pa = new Parts();
            String locationStatus = sysConfigService.getSysConfigByKey("locationIndia", String.valueOf(SysConfig.LOCATION_INDIA));
            if ("1".equals(locationStatus)) {
                pa.setLocationIndia(true);
            }
            //钻石的属性
            BasePrice basePrice = basePriceService.selectByKey(orders.getBaseId().longValue());
            if (basePrice != null) {
                pa.setExZsQg(basePrice.getQg());
                pa.setExZsPg(basePrice.getPg());
                pa.setExZsDc(basePrice.getDc());
                pa.setExZsYs(basePrice.getYs());
                pa.setExZsJd(basePrice.getJd());
                pa.setExZsYg(basePrice.getYg());
                pa.setExZsZs(basePrice.getZs());
                pa.setExZsZlMin(basePrice.getKlMin());
                pa.setExZsZlMax(basePrice.getKlMax());
            }
            pa.setType(Parts.TYPE_DIAMOND);
            pa.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
            pa.setBaseId(orders.getBaseId());
            if (!StringUtils.isEmpty(orders.getPartsId())) {
                Parts pp = partsService.selectByKey(Long.valueOf(orders.getPartsId()));
                if (pp != null) {
                    pa.setExZsZl(pp.getExZsZl());
                }
            }
            Parts diamond = partsMapper.getDiamondByBaseScore(pa);
            if (diamond == null) {
                diamond = new Parts();
                diamond.setDollarPrice(0);
                diamond.setCompany(ThirdSupplier.COMPANY_KEER);
                throw new RFException("钻石数据异常，请重新选择");
            }
            diamondSaleBack = diamond.getSaleBack();
            diamondCompany = diamond.getCompany().toUpperCase();
            diamondZsbh = diamond.getExZsBh();
            diamondBatch = diamond.getBatch();
            orders.setDiamondCode(diamond.getCode());
            orders.setExYs(diamond.getExZsYs());
            orders.setExDc(diamond.getExZsDc());
            orders.setExJd(diamond.getExZsJd());
            orders.setExPg(diamond.getExZsPg());
            orders.setExYg(diamond.getExZsYg());
            diamondQg = diamond.getExZsQg();
            //店铺信息里有门店销售系数
            String priceMultipleStore = user.getPriceMultiple();
            //系统配置中的门店系数（原价）
            String storeMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
            if ("".equals(user.getPriceMultiple()) || user.getPriceMultiple() == null) {
                //门店系数没有设置或者不存在，再从系统配置里拿系数
                priceMultipleStore = storeMultiple;
            }
            //钻石价格=基价*门店系数*基价系数（人民币）
            diamondZl = new BigDecimal(diamond.getExZsZl())
                    .divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            diamondPrice = new BigDecimal(new Double(basePrice.getPrice()))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(priceMultipleStore, 1))))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(diamondZl + "", 1))))
                    .intValue();
            //原价
            diamondOriginalPrice = new BigDecimal(new Double(basePrice.getPrice()))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(storeMultiple, 1))))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(diamondZl + "", 1))))
                    .intValue();
            //钻石的采购价格（供应商报价--美元）//个别家报人民币
            Float thirdDollarPrice = diamond.getDollarPrice().floatValue() / 100;
            FJG = thirdDollarPrice.toString();
            //供应商信息，供应商编码
            ThirdSupplier thirdSupplier = thirdSupplierCacheService.getCacheThirdSupplierManager(diamond.getCompany());
            if (thirdSupplier == null
                    || thirdSupplier.getId() == null
                    || thirdSupplier.getStatus() == Boolean.FALSE) {
                throw new RFException("供应商信息异常，或供应商管理中未开启合作开关");
            }
            FGYSBM = thirdSupplier != null ? thirdSupplier.getSapCode() : "";
        } else {
            //大克拉无主数据
            PartsBig partsBig = partsBigService.selectByKey(orders.getBaseId().longValue());
            diamondCompany = partsBig.getCompany().toUpperCase();
            diamondZsbh = partsBig.getZsZsh();
            orders.setDiamondCode(partsBig.getZsKh());
            orders.setExYs(partsBig.getZsYs());
            orders.setExJd(partsBig.getZsJd());
            orders.setExDc(partsBig.getZsDc());
            orders.setExPg(partsBig.getZsPg());
            orders.setExYg(partsBig.getZsYg());
            diamondQg = partsBig.getZsQg();
            //店铺信息里有门店销售系数
            String priceMultipleStore = user.getPriceMultiple();
            //系统配置中的门店系数（原价）
            String storeMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
            if ("".equals(user.getPriceMultiple()) || user.getPriceMultiple() == null) {
                //门店系数没有设置或者不存在，再从系统配置里拿系数
                priceMultipleStore = storeMultiple;
            }
            diamondPrice = new BigDecimal(new Double(partsBig.getZsPrice()))
                    .multiply(new BigDecimal(new Double(priceMultipleStore)))
                    .divide(new BigDecimal(new Double(storeMultiple))).intValue();
            diamondOriginalPrice = new BigDecimal(new Double(partsBig.getZsPrice())).intValue();
            diamondZl = new BigDecimal(partsBig.getZsZl())
                    .divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            FJG = new BigDecimal(partsBig.getZsMj())
                    .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).toString();//采购价
            FGYSBM = "";//供应商编码
            //主数据(大克拉无主数据，所以客户选择主数据，价格采用product表里设置的100-200的戒托价格)
            Product bigP = new Product();
            bigP.setZl(partsBig.getZsZl());
            Product pp = productMapper.getProductListByPartBig(bigP);
            if (pp != null) {
                productPrice = pp.getPrice();
            }
        }
        //约定取货时间格式处理
        orders.setPlanTime(DateUtil.getDateStart(orders.getPlanTime()));
        //POS系统转换编码
        String zyy = enumCodeService.getEnumCodeByName(pr.getExYy(), "ZYY");//寓意
        String zlsYs = enumCodeService.getEnumCodeByName(orders.getExYs(), "ZDMFJYSFW");//钻石颜色范围
        String zjsys = enumCodeService.getEnumCodeByName(orders.getColor(), "ZDMJSYS");//金属颜色
        String zjsca = enumCodeService.getEnumCodeByName(orders.getMaterial(), "ZDMJL");//金属材质
        String zjd = enumCodeService.getEnumCodeByName(orders.getExJd(), "ZDMFJJDFW");//钻石净度范围
        //2021年4月21日12:16:58 pos传参和展示一样，3EX和3VG
        String zqg = "";//切工
        if ("EX".equals(diamondQg)
                && "EX".equals(orders.getExPg())
                && "EX".equals(orders.getExDc())) {
            zqg = enumCodeService.getEnumCodeByName("EX", "ZQG");//切工
        } else {
            zqg = enumCodeService.getEnumCodeByName("VG", "ZQG");//切工
        }
        String zpg = zqg;//抛光
        String zdc = zqg;//对称
//        String zpg = enumCodeService.getEnumCodeByName(orders.getExPg(), "ZPG");//抛光
//        String zdc = enumCodeService.getEnumCodeByName(orders.getExDc(), "ZDC");//对称
        String zyg = "10";//枚举表里荧光none, 基价表里是N
        String zzsxz = "圆形";//主石形状
        String zym = "01";//腰码无
        String zzssl = "02";//钻石（主石石类）
        //钻石价格--细表2
        int zsPrice = new BigDecimal(diamondPrice).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).intValue();
        String FAMOUNT_SASE_zs = zsPrice + "";
        String FAMOUNT_SASE = "";
        String FAMOUNT_SASE_sku = "";
        int zsPriceOriginal = new BigDecimal(diamondOriginalPrice).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).intValue();
        String FAMOUNT_SASE_zs_origin = zsPriceOriginal + "";
        String FAMOUNT_SASE_ORIGINAL = "";
        if (orders.getProductCode() != null) {
            //sku价格--细表1
            Float skuPrice = new Integer(productPrice).floatValue() / 100;
            FAMOUNT_SASE_sku = skuPrice.toString();
            //总价格（约定金额/定制金额）--主表
            Float allPrice = skuPrice + zsPrice;
            FAMOUNT_SASE = allPrice.toString();
            orders.setPrice(diamondPrice + productPrice);
            //原价
            Float allPriceOriginal = skuPrice + zsPriceOriginal;
            FAMOUNT_SASE_ORIGINAL = allPriceOriginal.toString();
        } else {
            orders.setPrice(orders.getPrice() * 100);
        }


        //附加费用+附加描述
        String FADD_FEE = "0";
        String FEXPLANE = "0";
        if (orders.getAddCostsId() != null) {
            Float aPrice = orders.getAddCostsPrice().floatValue() / 100;
            FADD_FEE = aPrice.toString();
            AdditionalCosts additionalCosts = additionalCostsService.getAdditionalCosts(orders.getAddCostsId());
            FEXPLANE = additionalCosts.getName();
        }
        String ZZSZ = diamondZl + "";

        String hasFs = "未知";//是否有辅石，`辅石总数`不为0
        if (orders.getProductCode() != null) {
            hasFs = pr.getExFsCount() != null && pr.getExFsCount() != 0 ? "是" : "否";
        }
        //三个值，有裸石的订单，K10;无裸石的订单，K20;黄金定制，K21
        String hasZs = orders.getBaseId() != null ? "K10" : "K20";
        XMLRequest xmlRequest = new XMLRequest("2.0", "{1506DC71-E410-4610-BE04-E7766C2F8E50}");
        XMLParams xmlParams = new XMLParams();
        XMLMetaData xmlMetaData = new XMLMetaData();
        SQLBuilderItem sqlBuilderItem = new SQLBuilderItem("{607A5BD4-ECC4-4597-AB35-65E869528FDE}", "", "", "true");
        Save save = new Save();
        ROWDATA rowdata = new ROWDATA();
        ROW[] row = new ROW[1];
        row[0] = new ROW("4", orders.getCode(), user.getUsername() + "0001", "1", FAMOUNT_SASE,
                FAMOUNT_SASE_ORIGINAL, orders.getCustomerName(), orders.getCustomerMobile(),
                time, DateUtil.formatDate(orders.getPlanTime()), hasFs, FADD_FEE, FEXPLANE,
                "", operator.getName(), operator.getCode(), hasZs, orders.getLettering());

        SQLBuilderItem sqlBuilderItem1 = new SQLBuilderItem("{D272A064-04BF-46FF-B043-8115F20E7CFF}", "", "", "true");
        Save save1 = new Save();
        ROWDATA rowdata1 = new ROWDATA();
        if (orders.getBaseId() != null) {
            ROW[] row1 = new ROW[2];
            row1[0] = new ROW("4", orders.getCode(), orders.getKtCode(), pr.getCode(), "1",
                    FAMOUNT_SASE_sku, FAMOUNT_SASE_sku, zyy, zlsYs, zjsys, zjsca, zjd,
                    zpg, zdc, zyg, orders.getHand(), ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, "",
                    "", "", "", orders.getBornStone(), diamondZsbh, orders.getStoneNxbs());
            row1[1] = new ROW("4", orders.getCode(), "DZ" + orders.getDiamondCode(), pr.getCode(), "1",
                    FAMOUNT_SASE_zs, FAMOUNT_SASE_zs_origin, zyy, zlsYs, zjsys, zjsca, zjd,
                    zpg, zdc, zyg, "0", ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, "",
                    FGYSBM, "DZ" + orders.getDiamondCode(), FJG, "", diamondZsbh, orders.getStoneNxbs());
            rowdata1.setROW(row1);
        } else {
            //不选钻石，无钻石码
            ROW[] row1 = new ROW[1];
            row1[0] = new ROW("4", orders.getCode(), orders.getKtCode(), pr.getCode(), "1",
                    FAMOUNT_SASE_sku, FAMOUNT_SASE_sku, zyy, zlsYs, zjsys, zjsca, zjd,
                    zpg, zdc, zyg, orders.getHand(), ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, "",
                    "", "", "", orders.getBornStone(), diamondZsbh, orders.getStoneNxbs());
            rowdata1.setROW(row1);
        }
        rowdata.setROW(row);
        save.setROWDATA(rowdata);
        sqlBuilderItem.setSave(save);
        save1.setROWDATA(rowdata1);
        sqlBuilderItem1.setSave(save1);
        sqlBuilderItem.setSQLBuilderItem(sqlBuilderItem1);
        xmlMetaData.setSQLBuilderItem(sqlBuilderItem);
        xmlRequest.setXMLParams(xmlParams);
        xmlRequest.setXMLMetaData(xmlMetaData);

        System.out.println(xmlRequest);
        XMLUtil xmlUtil = new XMLUtil();
        String posXml = xmlUtil.beanToXML(xmlRequest);
        //先拿数据库配置里的pos下单地址
        String posUrl = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_ORDER_POS, keerokUrl);

        //本地order
        orders.setPosUrl(posUrl);
        orders.setPosXml(posXml);
        orders.setCompany(diamondCompany);
        orders.setStoneZsbh(diamondZsbh);
        orders.setStoneBatch(diamondBatch);
        orders.setStoneZl(String.valueOf(diamondZl));
        orders.setStoneCgj(FJG);
        orders.setProductYy(pr.getExYy());
        super.insertNotNull(orders);

        rows rows = new rows();
        rows.setProductid(orders.getDiamondCode());
        rows.setOrderId(orders.getId());
        rows.setCompany(orders.getCompany());
        rows.setProductYy(orders.getProductYy());
        rows.setStoneZsh(orders.getStoneZsbh());
        rows.setStoneYs(orders.getExYs());
        rows.setStoneJd(orders.getExJd());
        rows.setStoneZl(orders.getStoneZl());
        rows.setStoneCgj(orders.getStoneCgj());
        rows.setStoneType(orders.getStoneType());
        rows.setSaleback(diamondSaleBack);
        rows.setStoneOrderCode(orders.getCode());
        rows.setStoneOrderCharg(orders.getStoneBatch());
        //邮件
        if (rows.getCompany().equals(ThirdSupplier.COMPANY_KEER) && rows.getProductid().equals("")) {
            mailService.createNoDiamond(rows);
        } else {
            mailService.createOrder(rows);
        }
        if (pr.getCode() != null && !"200-100".equals(pr.getCode())) {
            //无空托的订单下单时只保存订单， 不调用传pos接口 
            if (!StringUtils.isEmpty(orders.getProductId())
                    || !StringUtils.isEmpty(orders.getProductCode())) {
                String result = null;
                try {
                    result = xmlUtil.toPost(posXml, posUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("result||传POS接口=" + result);
                XMLResponse response = xmlUtil.XMLToBean(result);
                if (response == null || response.getXMLResult() != true) {
                    throw new RFException("pos发送失败");
                }
            }
        }
        dataSourceTransactionManager.commit(transactionStatus);//提交
    }


    /**
     * 取消订单（jp）
     *
     * @param orders
     */
    @Transactional
    public void cancelOrders(Orders orders) throws Exception {
        Orders local = selectByKey(orders.getId());
        if (local != null) {
            if (DateUtil.compareGreater(new Date(), DateUtil.getDate(local.getCreateDate(), 30 * 60 * 1000))) {
                //超过30分钟，不能取消
                return;
            }
            //调用供应商unlock接口
            rows rows = new rows();
            rows.setProductid(local.getDiamondCode());
            rows.setOrderId(local.getId());
            rows.setCompany(local.getCompany());
            rows.setProductYy(local.getProductYy());
            rows.setStoneZsh(local.getStoneZsbh());
            rows.setStoneYs(local.getExYs());
            rows.setStoneJd(local.getExJd());
            rows.setStoneZl(local.getStoneZl());
            rows.setStoneCgj(local.getStoneCgj());
            rows.setStoneType(local.getStoneType());
            rows.setStoneOrderCode(local.getCode());
            rows.setStoneOrderCharg(local.getStoneBatch());
            //本地订单失效
            super.expireNotNull(local);
            //邮件
            try {
                mailService.cancelOrder(rows);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成订单（情侣戒）
     *
     * @param orders
     */
    @Transactional
    public void createCoupleOrders(Orders orders) throws IOException {
        if (orders.getContentList() == null || orders.getContentList().size() < 1) {
            throw new RFException("信息不完整");
        }
        //男女戒，生成两个订单
        for (int i = 0; i < orders.getContentList().size(); i++) {
            Orders ord = orders.getContentList().get(i);
            if (ord.getProductCode() == null) {
                throw new RFException("产品编码不能为空");
            }
            //验证手机号
            if (!CheckUtil.isMobileNO(ord.getCustomerMobile())) {
                throw new RFException("手机号码未知");
            }
            //验证约定交货日期
            if (CheckUtil.checkNull(ord.getPlanTime())) {
                throw new RFException("约定交货日期未知");
            }
            //找成品
            Product product = new Product();
            product.setCode(ord.getProductCode());
            Product pro = productMapper.getProduct(product);
            //订单号规则再定
            String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            int randomNum = (int) (new Random().nextDouble() * (9999 - 1000 + 1)) + 1000;// 获取4位随机数
            ord.setCode(time + randomNum);
            ord.setKtCode(pro.getKtCode());
            ord.setAddCostsId(orders.getAddCostsId());
            ord.setAddCostsPrice(orders.getAddCostsPrice());
            ord.setPlanTime(DateUtil.getDateStart(ord.getPlanTime()));
            int price = pro.getPrice();
            if (orders.getAddCostsPrice() != null) {
                //附加费用对半分
                int costsPrice = orders.getAddCostsPrice() / orders.getContentList().size();
                ord.setAddCostsPrice(costsPrice);
                price = price + costsPrice;
            }
            ord.setPrice(price);
            //pos接口
            //店铺信息
            SysUser user = sysUserService.selectByKey(ord.getUserId().longValue());
            //制单人信息
            SysUserDetail operator = sysUserDetailMapper.selectByPrimaryKey(ord.getUserOperatorId().longValue());
            //一些编码(情侣戒不选钻石)
            String zyy = enumCodeService.getEnumCodeByName(pro.getExYy(), "ZYY");//寓意
            String zlsYs = "";//裸石颜色
            String zjsys = enumCodeService.getEnumCodeByName(ord.getColor(), "ZDMJSYS");//金属颜色
            String zjsca = enumCodeService.getEnumCodeByName(ord.getMaterial(), "ZDMJL");//金属材质
            String zjd = "";//净度
            String zpg = "";//抛光
            String zdc = "";//对称
            String zyg = "";//荧光
            String zym = "";//腰码有
            String zqg = "";//切工
            String zzsxz = "";//主石形状
            String zzssl = "";//钻石（主石石类）
//            zzssl = enumCodeService.getEnumCodeByName(ord.getGems(), "ZDMSL");//主石石类
            String ZZSZ = "";//钻石重量
            String zyh = "";//印花
            if (ord.getPrintingId() != null) {
                Printing printing = printingService.selectByKey(ord.getPrintingId());
                zyh = printing.getName();
            }
            //印花和刻字放一起
            String printAndLetter = "";
            if (ord.getLettering() != null && !ord.getLettering().equals("")) {
                printAndLetter = ord.getLettering();
            } else if (!zyh.equals("")) {
                printAndLetter = "印花：" + zyh;
            }

            //附加费用+附加描述
            String FADD_FEE = "";
            String FEXPLANE = "";
            if (ord.getAddCostsId() != null) {
                Float aPrice = ord.getAddCostsPrice().floatValue() / 100;
                FADD_FEE = aPrice.toString();
                AdditionalCosts additionalCosts = additionalCostsService.getAdditionalCosts(ord.getAddCostsId());
                FEXPLANE = additionalCosts.getName();
            }

            Float skuPrice = pro.getPrice().floatValue() / 100;
            String FAMOUNT_SASE = skuPrice.toString();

            //是否有辅石，`辅石总数`不为0
            String hasFs = pro.getExFsCount() != null && pro.getExFsCount() != 0 ? "是" : "否";
            //三个值，有裸石的订单，K10;无裸石的订单，K20;黄金定制，K21
            String hasZs = "K20";
            XMLRequest xmlRequest = new XMLRequest("2.0", "{1506DC71-E410-4610-BE04-E7766C2F8E50}");
            XMLParams xmlParams = new XMLParams();
            XMLMetaData xmlMetaData = new XMLMetaData();
            SQLBuilderItem sqlBuilderItem = new SQLBuilderItem("{607A5BD4-ECC4-4597-AB35-65E869528FDE}", "", "", "true");
            Save save = new Save();
            ROWDATA rowdata = new ROWDATA();
            ROW[] row = new ROW[1];
            row[0] = new ROW("4", ord.getCode(), user.getUsername() + "0001", "1", FAMOUNT_SASE,
                    FAMOUNT_SASE, ord.getCustomerName(), ord.getCustomerMobile(),
                    time, DateUtil.formatDate(ord.getPlanTime()), hasFs, FADD_FEE, FEXPLANE,
                    "", operator.getName(), operator.getCode(), hasZs, printAndLetter);

            SQLBuilderItem sqlBuilderItem1 = new SQLBuilderItem("{D272A064-04BF-46FF-B043-8115F20E7CFF}", "", "", "true");
            Save save1 = new Save();
            ROWDATA rowdata1 = new ROWDATA();
            ROW[] row1 = new ROW[1];
            row1[0] = new ROW("4", ord.getCode(), ord.getKtCode(), pro.getCode(), "1",
                    FAMOUNT_SASE, FAMOUNT_SASE, zyy, zlsYs, zjsys, zjsca, zjd,
                    zpg, zdc, zyg, ord.getHand(), ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, zyh,
                    "", "", "", ord.getBornStone(), ord.getStoneZsbh(), ord.getStoneNxbs());

            rowdata.setROW(row);
            save.setROWDATA(rowdata);
            sqlBuilderItem.setSave(save);
            rowdata1.setROW(row1);
            save1.setROWDATA(rowdata1);
            sqlBuilderItem1.setSave(save1);
            sqlBuilderItem.setSQLBuilderItem(sqlBuilderItem1);
            xmlMetaData.setSQLBuilderItem(sqlBuilderItem);
            xmlRequest.setXMLParams(xmlParams);
            xmlRequest.setXMLMetaData(xmlMetaData);

            System.out.println(xmlRequest);
            XMLUtil xmlUtil = new XMLUtil();
            String posXml = xmlUtil.beanToXML(xmlRequest);
            //先拿数据库配置里的pos下单地址
            String posUrl = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_ORDER_POS, keerokUrl);
            String result = xmlUtil.toPost(posXml, posUrl);
            System.out.println("result||传POS接口=" + result);
//        String result = testXML;
            XMLResponse response = xmlUtil.XMLToBean(result);
            if (response == null || response.getXMLResult() != true) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw new RFException("下单失败，请重试");
            } else {
                ord.setPosUrl(posUrl);
                ord.setPosXml(posXml);
                ord.setCompany(ThirdSupplier.COMPANY_KEER);
                super.insertNotNull(ord);
            }
        }
    }


    /**
     * 拿订单中的数据，重新生成报文
     *
     * @param orderCode
     * @return
     */
    public ServiceResponse stoneOrderPosRenew(String orderCode) {
        Orders oo = new Orders();
        oo.setCode(orderCode);
        Orders localOrder = ordersMapper.getOrders(oo);
        if (localOrder == null) {
            return ServiceResponse.error("订单不存在");
        }
        //店铺信息
        SysUser user = sysUserService.selectByKey(localOrder.getUserId().longValue());
        //制单人信息
        SysUserDetail operator = sysUserDetailMapper.selectByPrimaryKey(localOrder.getUserOperatorId().longValue());
        //店铺信息里有门店销售系数
        String priceMultipleStore = user.getPriceMultiple();
        //系统配置中的门店系数（原价）
        String storeMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
        if ("".equals(user.getPriceMultiple()) || user.getPriceMultiple() == null) {
            //门店系数没有设置或者不存在，再从系统配置里拿系数
            priceMultipleStore = storeMultiple;
        }
        double diamondZl = 0;//钻石质量
        int diamondPrice = 0;//钻石价格
        int diamondOriginalPrice = 0;//钻石原价
        String FJG = "";//供应商采购价格，拿的dollarPrice字段
        String FGYSBM = "";//供应商编码
        String diamondQg = "";//钻石切工

        int productPrice = 0;
        Product product = new Product();
        //成品，拿ktCode
        if (!StringUtils.isEmpty(localOrder.getProductCode())) {
            product.setCode(localOrder.getProductCode());
        }
        if (!StringUtils.isEmpty(localOrder.getProductId())) {
            product.setId(Long.valueOf(localOrder.getProductId()));
        }
        Product pr = productMapper.getProduct(product);
        if (pr != null && pr.getId() != null) {
            productPrice = pr.getPrice();
        }

        if (!localOrder.getStoneType().equals("big")) {
            //订单里的钻石的属性
            BasePrice basePrice = basePriceService.selectByKey(localOrder.getBaseId().longValue());
            Parts pa = new Parts();
            pa.setType(Parts.TYPE_DIAMOND);
            pa.setExZsYs(basePrice.getYs());
            pa.setCode(localOrder.getDiamondCode());
            pa.setCompany(localOrder.getCompany());
            pa.setLockStatus(ThirdSupplier.STONE_STATUS_LOCK);
            pa.setExZsBh(localOrder.getStoneZsbh());
            pa.setExZsZl(new BigDecimal(localOrder.getStoneZl()).multiply(new BigDecimal(1000)).intValue());
            Parts diamond = partsMapper.getParts(pa);
            diamondQg = diamond.getExZsQg();
            //钻石价格=基价*门店系数*基价系数（人民币）
            diamondZl = new BigDecimal(diamond.getExZsZl())
                    .divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            diamondPrice = new BigDecimal(new Double(basePrice.getPrice()))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(priceMultipleStore, 1))))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(diamondZl + "", 1))))
                    .intValue();
            //原价
            diamondOriginalPrice = new BigDecimal(new Double(basePrice.getPrice()))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(storeMultiple, 1))))
                    .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(diamondZl + "", 1))))
                    .intValue();
            //钻石的采购价格（供应商报价--美元）//个别家报人民币
            Float thirdDollarPrice = diamond.getDollarPrice().floatValue() / 100;
            FJG = thirdDollarPrice.toString();
            //供应商信息，供应商编码
            ThirdSupplier thirdSupplier = thirdSupplierCacheService.getCacheThirdSupplierManager(diamond.getCompany());
            FGYSBM = thirdSupplier != null ? thirdSupplier.getSapCode() : "";
        } else {
            //大克拉无主数据
            PartsBig partsBig = partsBigService.selectByKey(localOrder.getBaseId().longValue());
            diamondQg = partsBig.getZsQg();
            diamondPrice = new BigDecimal(partsBig.getZsPrice())
                    .multiply(new BigDecimal(priceMultipleStore))
                    .divide(new BigDecimal(storeMultiple)).intValue();
            diamondOriginalPrice = new BigDecimal(partsBig.getZsPrice()).intValue();
            diamondZl = new BigDecimal(partsBig.getZsZl())
                    .divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
            FJG = new BigDecimal(partsBig.getZsMj())
                    .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).toString();//采购价
            FGYSBM = "";//供应商编码

            //主数据(大克拉无主数据，所以客户选择主数据，价格采用product表里设置的100-200的戒托价格)
            Product bigP = new Product();
            bigP.setZl(partsBig.getZsZl());
            Product pp = productMapper.getProductListByPartBig(bigP);
            if (pp != null) {
                productPrice = pp.getPrice();
            }
        }
        //POS系统转换编码
        String zyy = enumCodeService.getEnumCodeByName(localOrder.getProductYy(), "ZYY");//寓意
        String zlsYs = enumCodeService.getEnumCodeByName(localOrder.getExYs(), "ZDMFJYSFW");//钻石颜色范围
        String zjsys = enumCodeService.getEnumCodeByName(localOrder.getColor(), "ZDMJSYS");//金属颜色
        String zjsca = enumCodeService.getEnumCodeByName(localOrder.getMaterial(), "ZDMJL");//金属材质
        String zjd = enumCodeService.getEnumCodeByName(localOrder.getExJd(), "ZDMFJJDFW");//钻石净度范围
        //2021年4月21日12:16:58 pos传参和展示一样，3EX和3VG
        String zqg = "";//切工
        if ("EX".equals(diamondQg)
                && "EX".equals(localOrder.getExPg())
                && "EX".equals(localOrder.getExDc())) {
            zqg = enumCodeService.getEnumCodeByName("EX", "ZQG");//切工
        } else {
            zqg = enumCodeService.getEnumCodeByName("VG", "ZQG");//切工
        }
        String zpg = zqg;//抛光
        String zdc = zqg;//对称
        String zyg = "10";//枚举表里荧光none, 基价表里是N
        String zzsxz = "圆形";//主石形状
        String zym = "01";//腰码无
        String zzssl = "02";//钻石（主石石类）
        //钻石价格--细表2
        int zsPrice = new BigDecimal(diamondPrice).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).intValue();
        String FAMOUNT_SASE_zs = zsPrice + "";
        String FAMOUNT_SASE = "";
        String FAMOUNT_SASE_sku = "";
        int zsPriceOriginal = new BigDecimal(diamondOriginalPrice).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).intValue();
        String FAMOUNT_SASE_zs_origin = zsPriceOriginal + "";
        String FAMOUNT_SASE_ORIGINAL = "";
        if (localOrder.getProductCode() != null) {
            //sku价格--细表1
            Float skuPrice = new Integer(productPrice).floatValue() / 100;
            FAMOUNT_SASE_sku = skuPrice.toString();
            //总价格（约定金额/定制金额）--主表
            Float allPrice = skuPrice + zsPrice;
            FAMOUNT_SASE = allPrice.toString();
            localOrder.setPrice(diamondPrice + productPrice);
            //原价
            Float allPriceOriginal = skuPrice + zsPriceOriginal;
            FAMOUNT_SASE_ORIGINAL = allPriceOriginal.toString();
        } else {
            localOrder.setPrice(localOrder.getPrice() * 100);
        }


        //附加费用+附加描述
        String FADD_FEE = "0";
        String FEXPLANE = "0";
        if (localOrder.getAddCostsId() != null) {
            Float aPrice = localOrder.getAddCostsPrice().floatValue() / 100;
            FADD_FEE = aPrice.toString();
            AdditionalCosts additionalCosts = additionalCostsService.getAdditionalCosts(localOrder.getAddCostsId());
            FEXPLANE = additionalCosts.getName();
        }
        String ZZSZ = diamondZl + "";

        String hasFs = "未知";//是否有辅石，`辅石总数`不为0
        if (localOrder.getProductCode() != null) {
            hasFs = pr.getExFsCount() != null && pr.getExFsCount() != 0 ? "是" : "否";
        }
        //三个值，有裸石的订单，K10;无裸石的订单，K20;黄金定制，K21
        String hasZs = localOrder.getBaseId() != null ? "K10" : "K20";
        XMLRequest xmlRequest = new XMLRequest("2.0", "{1506DC71-E410-4610-BE04-E7766C2F8E50}");
        XMLParams xmlParams = new XMLParams();
        XMLMetaData xmlMetaData = new XMLMetaData();
        SQLBuilderItem sqlBuilderItem = new SQLBuilderItem("{607A5BD4-ECC4-4597-AB35-65E869528FDE}", "", "", "true");
        Save save = new Save();
        ROWDATA rowdata = new ROWDATA();
        ROW[] row = new ROW[1];
        row[0] = new ROW("4", localOrder.getCode(), user.getUsername() + "0001", "1", FAMOUNT_SASE,
                FAMOUNT_SASE_ORIGINAL, localOrder.getCustomerName(), localOrder.getCustomerMobile(),
                localOrder.getCode().substring(0, 14), DateUtil.formatDate(localOrder.getPlanTime()), hasFs, FADD_FEE, FEXPLANE,
                "", operator.getName(), operator.getCode(), hasZs, localOrder.getLettering());

        SQLBuilderItem sqlBuilderItem1 = new SQLBuilderItem("{D272A064-04BF-46FF-B043-8115F20E7CFF}", "", "", "true");
        Save save1 = new Save();
        ROWDATA rowdata1 = new ROWDATA();
        if (localOrder.getBaseId() != null) {
            ROW[] row1 = new ROW[2];
            row1[0] = new ROW("4", localOrder.getCode(), localOrder.getKtCode(), localOrder.getProductCode(), "1",
                    FAMOUNT_SASE_sku, FAMOUNT_SASE_sku, zyy, zlsYs, zjsys, zjsca, zjd,
                    zpg, zdc, zyg, localOrder.getHand(), ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, "",
                    "", "", "", localOrder.getBornStone(), localOrder.getStoneZsbh(), localOrder.getStoneNxbs());
            row1[1] = new ROW("4", localOrder.getCode(), "DZ" + localOrder.getDiamondCode(), localOrder.getProductCode(), "1",
                    FAMOUNT_SASE_zs, FAMOUNT_SASE_zs_origin, zyy, zlsYs, zjsys, zjsca, zjd,
                    zpg, zdc, zyg, "0", ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, "",
                    FGYSBM, "DZ" + localOrder.getDiamondCode(), FJG, "", localOrder.getStoneZsbh(), localOrder.getStoneNxbs());
            rowdata1.setROW(row1);
        } else {
            //不选钻石，无钻石码
            ROW[] row1 = new ROW[1];
            row1[0] = new ROW("4", localOrder.getCode(), localOrder.getKtCode(), localOrder.getProductCode(), "1",
                    FAMOUNT_SASE_sku, FAMOUNT_SASE_sku, zyy, zlsYs, zjsys, zjsca, zjd,
                    zpg, zdc, zyg, localOrder.getHand(), ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, "",
                    "", "", "", localOrder.getBornStone(), localOrder.getStoneZsbh(), localOrder.getStoneNxbs());
            rowdata1.setROW(row1);
        }
        rowdata.setROW(row);
        save.setROWDATA(rowdata);
        sqlBuilderItem.setSave(save);
        save1.setROWDATA(rowdata1);
        sqlBuilderItem1.setSave(save1);
        sqlBuilderItem.setSQLBuilderItem(sqlBuilderItem1);
        xmlMetaData.setSQLBuilderItem(sqlBuilderItem);
        xmlRequest.setXMLParams(xmlParams);
        xmlRequest.setXMLMetaData(xmlMetaData);

        System.out.println(xmlRequest);
        XMLUtil xmlUtil = new XMLUtil();
        String posXml = xmlUtil.beanToXML(xmlRequest);
        //先拿数据库配置里的pos下单地址
        String posUrl = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_ORDER_POS, keerokUrl);
        //本地更新报文
        localOrder.setPosUrl(posUrl);
        localOrder.setPosXml(posXml);
        super.updateNotNull(localOrder);
        return ServiceResponse.succ("重新生成POS报文成功，" + posXml);
    }

    /**
     * 拿订单中的报文，手动重新调用pos
     *
     * @param orderCode
     * @return
     */
    public ServiceResponse stoneOrderPosResend(String orderCode) {
        Orders orders = new Orders();
        orders.setCode(orderCode);
        Orders localOrder = ordersMapper.getOrders(orders);
        if (localOrder == null) {
            return ServiceResponse.error("订单不存在");
        }
        String result = "";
        XMLUtil xmlUtil = new XMLUtil();
        try {
            result = xmlUtil.toPost(localOrder.getPosXml(), localOrder.getPosUrl());
            XMLResponse response = xmlUtil.XMLToBean(result);
            if (response == null || response.getXMLResult() != true) {
                return ServiceResponse.error("下单失败，提交报文详见数据库orders表"
                        + ", pos地址=" + localOrder.getPosUrl()
                        + ", pos返回=" + result);
            }
            return ServiceResponse.succ("下单成功，pos返回=" + response.toString());
        } catch (Exception e) {
            return ServiceResponse.error("下单失败，超时");
        }
    }


    /**
     * 重新下单，查的是已退单的定的那
     *
     * @param map
     * @return
     */
    @Transactional
    public ServiceResponse stoneOrderReOrder(Map<String, Object> map) throws Exception {
        String orderCode = ConvertUtil.convert(map.get("orderCode"), String.class);
        String orderZsh = ConvertUtil.convert(map.get("orderZsh"), String.class);
        String orderKtCode = ConvertUtil.convert(map.get("orderKtCode"), String.class);
        String orderSc = ConvertUtil.convert(map.get("orderSc"), String.class);

        Orders orders = new Orders();
        orders.setCode(orderCode);
        orders.setStoneZsbh(orderZsh);
        Orders localOrder = ordersMapper.getExpireOrders(orders);
        if (localOrder == null) {
            return ServiceResponse.error("[orders表]订单不存在，或者此订单当前并未取消");
        }
        if (!localOrder.getStoneType().equals("small")) {
            return ServiceResponse.error("[orders表]大克拉石头，暂不支持此功能");
        }
        //校验石头，拿价格
        Parts parts = new Parts();
        parts.setType(Parts.TYPE_DIAMOND);
        parts.setCompany(localOrder.getCompany());
        parts.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
        parts.setExZsBh(localOrder.getStoneZsbh());
        Parts localDiamond = partsMapper.getParts(parts);
        if (localDiamond == null) {
            return ServiceResponse.error("[parts表]石头不存在，请检查或者手动添加");
        }
        //检查基价，拿价格
        BasePrice localBasePrice = basePriceMapper.selectByPrimaryKey(localOrder.getBaseId().longValue());
        if (localBasePrice == null) {
            return ServiceResponse.error("[base_price表]基价不存在，请检查或者手动添加");
        }
        //店铺信息，制单人信息，拿门店系数
        SysUser user = sysUserService.selectByKey(localOrder.getUserId().longValue());
        SysUserDetail operator = sysUserDetailMapper.selectByPrimaryKey(localOrder.getUserOperatorId().longValue());
        if (!user.getEnabled() || user.getExpireDate() != null) {
            return ServiceResponse.error("[sys_user表]店铺账号未启用");
        }
        //店铺信息里有门店销售系数
        String priceMultipleStore = user.getPriceMultiple();
        //系统配置中的门店系数（原价）
        String storeMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
        if ("".equals(user.getPriceMultiple()) || user.getPriceMultiple() == null) {
            //门店系数没有设置或者不存在，再从系统配置里拿系数
            priceMultipleStore = storeMultiple;
        }

        //供应商信息，供应商编码
        ThirdSupplier thirdSupplier = thirdSupplierCacheService.getCacheThirdSupplierManager(localDiamond.getCompany());
//        if (thirdSupplier == null
//                || thirdSupplier.getId() == null
//                || thirdSupplier.getStatus() == Boolean.FALSE) {
//            return ServiceResponse.error("[third_supplier表]供应商信息异常，或供应商管理中未开启合作开关");
//        }
        //供应商编码
        String FGYSBM = thirdSupplier != null ? thirdSupplier.getSapCode() : "";
        //检查主数据客定码，拿价格
        Product localProduct = null;
        Product product = new Product();
        product.setKtCode(orderKtCode);
        product.setExSc(orderSc);
        List<Product> productList = productMapper.findProductList(product);
        for (Product pp : productList) {
            if (productService.checkDiamondYs(localOrder.getExYs(), pp.getExZsYs())
                    && productService.checkDiamondJd(localOrder.getExJd(), pp.getExZsJd())) {
                localProduct = pp;
                break;
            }
        }
        if (localProduct == null) {
            return ServiceResponse.error("[product表]主数据不存在，请检查或者手动添加");
        }

        double diamondZl = new BigDecimal(localDiamond.getExZsZl())
                .divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //钻石门店价格=基价*门店系数*基价系数（人民币）* 石重
        int diamondPrice = new BigDecimal(new Double(localBasePrice.getPrice()))
                .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(priceMultipleStore, 1))))
                .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(diamondZl + "", 1))))
                .intValue();
        //钻石原价
        int diamondOriginalPrice = new BigDecimal(new Double(localBasePrice.getPrice()))
                .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(storeMultiple, 1))))
                .multiply(new BigDecimal(Double.toString(NumberUtils.toDouble(diamondZl + "", 1))))
                .intValue();
        //钻石的采购价格（供应商报价--美元）//个别家报人民币
        //供应商采购价格，拿的dollarPrice字段
        String FJG = String.valueOf(localDiamond.getDollarPrice().floatValue() / 100);

        //POS系统转换编码
        String zyy = enumCodeService.getEnumCodeByName(localProduct.getExYy(), "ZYY");//寓意
        String zjsys = enumCodeService.getEnumCodeByName(localProduct.getJsys(), "ZDMJSYS");//金属颜色
        String zjsca = enumCodeService.getEnumCodeByName(localProduct.getJscz(), "ZDMJL");//金属材质
        String zlsYs = enumCodeService.getEnumCodeByName(localOrder.getExYs(), "ZDMFJYSFW");//钻石颜色范围
        String zjd = enumCodeService.getEnumCodeByName(localOrder.getExJd(), "ZDMFJJDFW");//钻石净度范围
        //2021年4月21日12:16:58 pos传参和展示一样，3EX和3VG
        String zqg = "";//切工
        if ("EX".equals(localDiamond.getExZsQg())
                && "EX".equals(localOrder.getExPg())
                && "EX".equals(localOrder.getExDc())) {
            zqg = enumCodeService.getEnumCodeByName("EX", "ZQG");//切工
        } else {
            zqg = enumCodeService.getEnumCodeByName("VG", "ZQG");//切工
        }
        String zpg = zqg;//抛光
        String zdc = zqg;//对称
//        String zpg = enumCodeService.getEnumCodeByName(localOrder.getExPg(), "ZPG");//抛光
//        String zdc = enumCodeService.getEnumCodeByName(localOrder.getExDc(), "ZDC");//对称
        String zyg = "10";//枚举表里荧光none, 基价表里是N
        String zzsxz = "圆形";//主石形状
        String zym = "01";//腰码无
        String zzssl = "02";//钻石（主石石类）

        //钻石门店价格--细表2
        int zsPrice = new BigDecimal(diamondPrice).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).intValue();
        String FAMOUNT_SASE_zs = String.valueOf(zsPrice);
        //钻石原价--细表2
        int zsPriceOriginal = new BigDecimal(diamondOriginalPrice).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).intValue();
        String FAMOUNT_SASE_zs_origin = String.valueOf(zsPriceOriginal);
        //sku价格--细表1
        Float skuPrice = localProduct.getPrice().floatValue() / 100;
        String FAMOUNT_SASE_sku = String.valueOf(skuPrice);
        //总价格（约定金额/定制金额）--主表
        Float allPrice = skuPrice + zsPrice;
        String FAMOUNT_SASE = String.valueOf(allPrice);
        //原价
        Float allPriceOriginal = skuPrice + zsPriceOriginal;
        String FAMOUNT_SASE_ORIGINAL = String.valueOf(allPriceOriginal);
        //附加费用+附加描述
        String FADD_FEE = "0";
        String FEXPLANE = "0";
        int costPrice = 0;
        if (localOrder.getAddCostsId() != null) {
            Float aPrice = localOrder.getAddCostsPrice().floatValue() / 100;
            FADD_FEE = aPrice.toString();
            costPrice = localOrder.getAddCostsPrice();
            AdditionalCosts additionalCosts = additionalCostsService.getAdditionalCosts(localOrder.getAddCostsId());
            FEXPLANE = additionalCosts.getName();
        }

        String ZZSZ = String.valueOf(diamondZl);
        String hasFs = "未知";//是否有辅石，`辅石总数`不为0
        if (localProduct != null) {
            hasFs = localProduct.getExFsCount() != null && localProduct.getExFsCount() != 0 ? "是" : "否";
        }
        //三个值，有裸石的订单，K10;无裸石的订单，K20;黄金定制，K21
        String hasZs = localOrder.getBaseId() != null ? "K10" : "K20";
        XMLRequest xmlRequest = new XMLRequest("2.0", "{1506DC71-E410-4610-BE04-E7766C2F8E50}");
        XMLParams xmlParams = new XMLParams();
        XMLMetaData xmlMetaData = new XMLMetaData();
        SQLBuilderItem sqlBuilderItem = new SQLBuilderItem("{607A5BD4-ECC4-4597-AB35-65E869528FDE}", "", "", "true");
        Save save = new Save();
        ROWDATA rowdata = new ROWDATA();
        ROW[] row = new ROW[1];
        row[0] = new ROW("4", localOrder.getCode(), user.getUsername() + "0001", "1", FAMOUNT_SASE,
                FAMOUNT_SASE_ORIGINAL, localOrder.getCustomerName(), localOrder.getCustomerMobile(),
                localOrder.getCode().substring(0, 14), DateUtil.formatDate(localOrder.getPlanTime()), hasFs, FADD_FEE, FEXPLANE,
                "", operator.getName(), operator.getCode(), hasZs, localOrder.getLettering());

        SQLBuilderItem sqlBuilderItem1 = new SQLBuilderItem("{D272A064-04BF-46FF-B043-8115F20E7CFF}", "", "", "true");
        Save save1 = new Save();
        ROWDATA rowdata1 = new ROWDATA();
        if (localOrder.getBaseId() != null) {
            ROW[] row1 = new ROW[2];
            row1[0] = new ROW("4", localOrder.getCode(), localOrder.getKtCode(), localProduct.getCode(), "1",
                    FAMOUNT_SASE_sku, FAMOUNT_SASE_sku, zyy, zlsYs, zjsys, zjsca, zjd,
                    zpg, zdc, zyg, orderSc, ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, "",
                    "", "", "", localOrder.getBornStone(), localOrder.getStoneZsbh(), localOrder.getStoneNxbs());
            row1[1] = new ROW("4", localOrder.getCode(), "DZ" + localOrder.getDiamondCode(), localProduct.getCode(), "1",
                    FAMOUNT_SASE_zs, FAMOUNT_SASE_zs_origin, zyy, zlsYs, zjsys, zjsca, zjd,
                    zpg, zdc, zyg, "0", ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, "",
                    FGYSBM, "DZ" + localOrder.getDiamondCode(), FJG, "", localOrder.getStoneZsbh(), localOrder.getStoneNxbs());
            rowdata1.setROW(row1);
        } else {
            //不选钻石，无钻石码
            ROW[] row1 = new ROW[1];
            row1[0] = new ROW("4", localOrder.getCode(), localOrder.getKtCode(), localProduct.getCode(), "1",
                    FAMOUNT_SASE_sku, FAMOUNT_SASE_sku, zyy, zlsYs, zjsys, zjsca, zjd,
                    zpg, zdc, zyg, localOrder.getHand(), ZZSZ, zym, zqg, "", "01", zzsxz, zzssl, "",
                    "", "", "", localOrder.getBornStone(), localOrder.getStoneZsbh(), localOrder.getStoneNxbs());
            rowdata1.setROW(row1);
        }
        rowdata.setROW(row);
        save.setROWDATA(rowdata);
        sqlBuilderItem.setSave(save);
        save1.setROWDATA(rowdata1);
        sqlBuilderItem1.setSave(save1);
        sqlBuilderItem.setSQLBuilderItem(sqlBuilderItem1);
        xmlMetaData.setSQLBuilderItem(sqlBuilderItem);
        xmlRequest.setXMLParams(xmlParams);
        xmlRequest.setXMLMetaData(xmlMetaData);

        System.out.println(xmlRequest);
        XMLUtil xmlUtil = new XMLUtil();
        String posXml = xmlUtil.beanToXML(xmlRequest);
        //先拿数据库配置里的pos下单地址
        String posUrl = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_ORDER_POS, keerokUrl);

        //本地order 更新主数据的属性
        localOrder.setKtCode(localProduct.getKtCode());
        localOrder.setHand(orderSc);
        localOrder.setMaterial(localProduct.getJscz());
        localOrder.setColor(localProduct.getJsys());
        localOrder.setStyleId(localProduct.getStyleId());
        localOrder.setProductCode(localProduct.getCode());
        localOrder.setProductId(String.valueOf(localProduct.getId()));
        localOrder.setPrice(diamondPrice + localProduct.getPrice() + costPrice);
        localOrder.setPosUrl(posUrl);
        localOrder.setPosXml(posXml);
        localOrder.setProductYy(localProduct.getExYy());
        localOrder.setExpireDate(null);
        super.updateAll(localOrder);

        rows rows = new rows();
        rows.setCompany(localOrder.getCompany());
        rows.setOrderId(localOrder.getId());
        rows.setProductid(localOrder.getDiamondCode());
        rows.setStoneZsh(localOrder.getStoneZsbh());
        rows.setProductYy(localOrder.getProductYy());
        rows.setStoneYs(localOrder.getExYs());
        rows.setStoneType(localOrder.getStoneType());
        rows.setStoneOrderCode(localOrder.getCode());
        String result = "";
        try {
            result = xmlUtil.toPost(localOrder.getPosXml(), localOrder.getPosUrl());
            XMLResponse response = xmlUtil.XMLToBean(result);
            if (response == null || response.getXMLResult() != true) {
                return ServiceResponse.error("下单失败，提交报文详见数据库orders表"
                        + ", pos地址=" + localOrder.getPosUrl()
                        + ", pos返回=" + result);
            }
            //邮件
            mailService.createOrder(rows);
            return ServiceResponse.succ("下单成功，pos返回=" + response.toString());
        } catch (Exception e) {
            return ServiceResponse.error("下单失败，超时");
        }
    }


}
