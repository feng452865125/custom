package com.chunhe.custom.pc.service;

import com.chunhe.custom.pc.enumCode.XMLEnum;
import com.chunhe.custom.pc.enumCode.XMLEnumParam;
import com.chunhe.custom.pc.model.CustomCard;
import com.chunhe.custom.pc.model.EnumCode;
import com.chunhe.custom.pc.model.Product;
import com.chunhe.custom.pc.model.SapSku;
import com.chunhe.custom.pc.posCard.XMLPosCPCardCode;
import com.chunhe.custom.pc.posCard.XMLPosCPCardPassword;
import com.chunhe.custom.pc.posCard.XMLPosCard;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.utils.BeanUtil;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.framework.utils.DateUtil;
import com.chunhe.custom.pc.mapper.ProductMapper;
import com.chunhe.custom.pc.posCard.XMLPosCardParam;
import com.chunhe.custom.pc.model.*;
import com.chunhe.custom.pc.posCard.*;
import com.chunhe.custom.pc.sku.XMLSku;
import com.chunhe.custom.pc.sku.XMLSkuParam;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by white 2018-8-14 09:30:54
 */
@Service
public class SapSkuService extends BaseService<SapSku> {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private EnumCodeService enumCodeService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private CustomCardService customCardService;

    @Value("${isAllowSap}")
    private String isAllowSap = "0";

//    @Transactional
//    public void dataSynchSapSku(List<SapSku> list) {
//        for (int i = 0; i < list.size(); i++) {
//            SapSku sapSku = list.get(i);
//            if (sapSku.getType() == null || sapSku.getType().equals("")) {
//                throw new RFException("类型为空");
//            }
//            //物料编码必填，唯一查询依据
//            if (sapSku.getCode().equals("")) {
//                throw new RFException("物料编码为空");
//            }
//            //可以客定，则必填客定码
//            if (sapSku.getIsKt().equals("是") && sapSku.getKtCode().equals("")) {
//                throw new RFException("客定码为空");
//            }
//            //存在手寸，需要按格式 1、8# size = 1     2、8-10# size = 2
//            if (!sapSku.getSc().equals("")) {
//                String sc = this.dealWithScList(sapSku.getSc());
//                List<String> list2 = Arrays.asList(sc.split("##"));
//                if (Boolean.valueOf(list2.get(0)) == Boolean.FALSE) {
//                    throw new RFException(list.get(1));
//                }
//                sapSku.setSc(list2.get(1));
//            }
//            insertNotNull(sapSku);
//            //先自己sapSku表备份，再数据处理
//            if (sapSku.getType().equals(String.valueOf(SapSku.TYPE_FLOWER_HEAD))
//                    || sapSku.getType().equals(String.valueOf(SapSku.TYPE_RING_ARM))) {
//                partsService.createPartsBySap(sapSku);
//            } else if (sapSku.getType().equals(String.valueOf(SapSku.TYPE_PRODUCT))) {
//                //本地有，更新，本地无，新建（带relProductParts表的关联数据）
//                productService.createProductBySap(sapSku);
//            }
//        }
//    }

    /**
     * xml调用转sapSku
     *
     * @param xml
     */
    @Transactional
    public String toBeanSapSku(String xml) {
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.processAnnotations(XMLSku.class);
        xStream.processAnnotations(XMLSkuParam.class);
        Object object = xStream.fromXML(xml);
        XMLSku result = (XMLSku) object;
        XMLSkuParam[] xmlSkuParamArr = result.getXMLSkuParam();
        if (xmlSkuParamArr == null || xmlSkuParamArr.length < 1) {
            return Boolean.FALSE + "##<null>空数据";
        }
        String isAllow = sysConfigService.getSysConfigByKey(SysConfig.IS_ALLOW_SAP, isAllowSap);
        for (int i = 0; i < xmlSkuParamArr.length; i++) {
            XMLSkuParam xmlSkuParam = xmlSkuParamArr[i];
//            //2019年11月5日22:17:23，说sap只更新价格
//            int fsCount = new BigDecimal(Double.toString(NumberUtils.toDouble(xmlSkuParam.getFsCount(), 0))).intValue();
//            String isZs = fsCount != 0 ? "是" : "否";
//            //转换数据字典
//            String sJscz = enumCodeService.getEnumCodeByCode(xmlSkuParam.getJsCz(), "ZDMJL");//金属材质ZDMJL
//            String sJsys = enumCodeService.getEnumCodeByCode(xmlSkuParam.getJsYs(), "ZDMJSYS");//金属颜色ZDMJSYS
//            String sSc = enumCodeService.getEnumCodeByCode(xmlSkuParam.getSc(), "ZSC");//手寸ZSC
//            String sYy = enumCodeService.getEnumCodeByCode(xmlSkuParam.getYy(), "ZYY");//寓意ZYY
//            String sSeries = enumCodeService.getEnumCodeByCode(xmlSkuParam.getSeries(), "ZDMXL");//系列ZDMXL
//            String sZsLx = enumCodeService.getEnumCodeByCode(xmlSkuParam.getZsLx(), "ZDMSL");//主石石类ZDMSL
//            String sPdlx = enumCodeService.getEnumCodeByCode(xmlSkuParam.getPdLx(), "ZDMLB");//佩戴类别ZDMLB
//            String sZsys = enumCodeService.getEnumCodeByCode(xmlSkuParam.getZsYs(), "ZDMFJYSFW");//钻石颜色范围ZDMFJYSFW
//            String sZsjd = enumCodeService.getEnumCodeByCode(xmlSkuParam.getZsJd(), "ZDMFJJDFW");//钻石净度范围ZDMFJJDFW
//            String sIskt = enumCodeService.getEnumCodeByCode(xmlSkuParam.getIsKt(), "ZDMNFKD");//能否客订ZDMNFKD
//            System.out.println("报文：" + xmlSkuParam.toString());
//            SapSku sapSku = new SapSku(xmlSkuParam.toString(), xmlSkuParam.getCode(), xmlSkuParam.getKtCode(), xmlSkuParam.getName(),
//                    sJscz, sZsLx, sPdlx, sJsys, sZsys, sZsjd,
//                    String.valueOf(fsCount), sSc, sYy, xmlSkuParam.getFs(), xmlSkuParam.getKd(),
//                    xmlSkuParam.getYs(), xmlSkuParam.getKk(), xmlSkuParam.getPrice(), sSeries, sIskt,
//                    xmlSkuParam.getType(), xmlSkuParam.getGs(), xmlSkuParam.getQghy(), xmlSkuParam.getKstd(), xmlSkuParam.getSex(),
//                    isZs, xmlSkuParam.getJszl());
//            System.out.println("sapSku：" + String.valueOf(sapSku));
//            if (sapSku.getType() != null && sapSku.getType().equals("0")) {
//                //对接，对方过滤的数据
//                continue;
//            }
//            //物料编码必填，唯一查询依据
//            if (sapSku.getCode() == null || sapSku.getCode().equals("")) {
//                return Boolean.FALSE + "##<null>物料编码code为空";
//            }
//            if (sapSku.getType() == null || sapSku.getType().equals("")) {
//                return Boolean.FALSE + "##<" + xmlSkuParam.getCode() + ">类型type为空";
//            }
//            if (!sapSku.getType().equals(String.valueOf(Parts.TYPE_RING_ARM))
//                    && !sapSku.getType().equals(String.valueOf(Parts.TYPE_FLOWER_HEAD))
//                    && !sapSku.getType().equals(String.valueOf(Parts.TYPE_SKU))
//                    && !sapSku.getType().equals("0")) {
//                return Boolean.FALSE + "##<" + xmlSkuParam.getCode() + ">类型type错误";
//            }
//            if ((sPdlx.contains("戒臂") && !sapSku.getType().equals(String.valueOf(Parts.TYPE_RING_ARM)))
//                    || (sPdlx.contains("花头") && !sapSku.getType().equals(String.valueOf(Parts.TYPE_FLOWER_HEAD)))) {
//                return Boolean.FALSE + "##<" + xmlSkuParam.getCode() + ">类型type错误";
//            }
////            //可以客定，则必填客定码
////            if (sapSku.getIsKt().equals("是") && (sapSku.getKtCode() == null || sapSku.getKtCode().equals(""))) {
////                return Boolean.FALSE + "##<" + xmlSkuParam.getCode() + ">客定码ktCode为空";
////            }
//            //先自己sapSku表备份，再数据处理
//            insertNotNull(sapSku);
//            //存在手寸，需要按格式 1、8# size = 1     2、8-10# size = 2
//            if (sapSku.getSc() != null && !sapSku.getSc().equals("") && !sapSku.getSc().equals("无")) {
//                String sc = this.dealWithScList(sapSku.getSc());
//                List<String> list = Arrays.asList(sc.split("##"));
//                if (Boolean.valueOf(list.get(0)) == Boolean.FALSE) {
//                    return Boolean.FALSE + "##<" + xmlSkuParam.getCode() + ">" + list.get(1);
//                }
//                sapSku.setSc(list.get(1));
//            }
//            if (sapSku.getType().equals(String.valueOf(SapSku.TYPE_FLOWER_HEAD))
//                    || sapSku.getType().equals(String.valueOf(SapSku.TYPE_RING_ARM))) {
//                //只更新，不新增，2018年10月31日17:19:45
//                partsService.createPartsBySap(sapSku);
//            } else if (sapSku.getType().equals(String.valueOf(SapSku.TYPE_PRODUCT))) {
//                //只更新，不新增，2018年10月31日17:19:45
//                productService.createProductBySap(sapSku);
//            }
            System.out.println("sap报文：" + xmlSkuParam.toString());
            SapSku sapSku = new SapSku(xmlSkuParam.toString(), xmlSkuParam.getType(), xmlSkuParam.getCode(),
                    xmlSkuParam.getKtPrice(), xmlSkuParam.getDzPrice(), xmlSkuParam.getZF1());
            //物料编码必填，唯一查询依据
            if (sapSku.getCode() == null || sapSku.getCode().equals("")) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Boolean.FALSE + "##<null>物料编码存在空值 ，此次同步失败";
            }
            int price = 0;
            int ktPrice = new BigDecimal(Double.toString(NumberUtils.toDouble(sapSku.getKtPrice(), 0))).multiply(new BigDecimal(100)).intValue();
            int dzPrice = new BigDecimal(Double.toString(NumberUtils.toDouble(sapSku.getDzPrice(), 0))).multiply(new BigDecimal(100)).intValue();
            //空托价格优先，空托价格=0，则采用定制成品价格
            if (ktPrice != 0) {
                price = ktPrice;
            } else {
                if (dzPrice != 0) {
                    price = dzPrice;
                } else {
                    //价格=0，不做更新
                    continue;
                }
            }
            sapSku.setPrice(String.valueOf(price));
            sapSku.setNxs(xmlSkuParam.getZF1());
            //先自己sapSku表备份，再数据处理
            insertNotNull(sapSku);
            //检查系统配置，是否允许同步
            if (!isAllow.equals("1")) {
                //不同步，只做存储sap数据
                continue;
            }
            Product product = new Product();
            product.setCode(xmlSkuParam.getCode());
            Product pro = productMapper.getProduct(product);
            if (pro == null || pro.getId() == null) {
                //不做新增，参数不全
                continue;
            }
            //镶嵌方式，不是"是"的，都为否（否，null）
            String nxbs = "否";
            if (!StringUtils.isEmpty(xmlSkuParam.getZF1()) && xmlSkuParam.getZF1().equals("是")) {
                nxbs = "是";
            }
            if ((!StringUtils.isEmpty(pro.getPrice()) && pro.getPrice().equals(price))
                    && (!StringUtils.isEmpty(pro.getIsNxbs()) && pro.getIsNxbs().equals(xmlSkuParam.getZF1()))) {
                //sap同步的价格，与本地相同，&& 同步的镶嵌方式也与本地相同   不更新
                continue;
            }
            pro.setPrice(price);
            pro.setIsNxbs(nxbs);
            productMapper.updateByPrimaryKey(pro);
            //镶嵌方式，同款式的统一更新
            if (!StringUtils.isEmpty(pro.getStyleId())) {
                productMapper.updateProductNxbs(pro.getStyleId(), nxbs);
            }
        }
        return Boolean.TRUE + "##success";
    }

    /**
     * 字典数据处理
     *
     * @param xml
     */
    @Transactional
    public String insertEnumCode(String xml) {
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.processAnnotations(XMLEnum.class);
        xStream.processAnnotations(XMLEnumParam.class);
        Object object = xStream.fromXML(xml);
        XMLEnum result = (XMLEnum) object;
        XMLEnumParam[] xmlEnumParamArr = result.getXMLEnumParam();

        for (int i = 0; i < xmlEnumParamArr.length; i++) {
            XMLEnumParam xmlEnumParam = xmlEnumParamArr[i];
            EnumCode enumCode = enumCodeService.getEnumCode(xmlEnumParam.getZSXBM(), xmlEnumParam.getZSXLX());
            if (enumCode == null) {
                //不存在，insert
                EnumCode code = new EnumCode(xmlEnumParam.getZSXBM(), xmlEnumParam.getZSXMC(), xmlEnumParam.getZSXLX(), xmlEnumParam.getZSXLXMC());
                if (enumCodeService.insertNotNull(code) != 1) {
                    return Boolean.FALSE + "##insert failed";
                }
            } else {
                EnumCode code = new EnumCode(xmlEnumParam.getZSXBM(), xmlEnumParam.getZSXMC(), xmlEnumParam.getZSXLX(), xmlEnumParam.getZSXLXMC());
                BeanUtil.copyObject(code, enumCode, true);
                if (enumCodeService.updateNotNull(enumCode) != 1) {
                    return Boolean.FALSE + "##update failed";
                }
            }
        }

        return Boolean.TRUE + "##ok";
    }

    /**
     * 处理手寸1，8-10#，转换8#9#10#
     * 处理手寸2，8#
     *
     * @param sapSc
     * @return
     */
    public String dealWithScList(String sapSc) {
        String sc = "";
        List<String> scList = Arrays.asList(sapSc.split("-"));
        if (scList.size() == 1) {
            sc = sapSc;
        } else if (scList.size() == 2) {
            int min = 0;
            int max = 0;
            for (int i = 0; i < scList.size(); i++) {
                List<String> list = Arrays.asList(scList.get(i).split("#"));
                String value = scList.get(0);
                if (!CheckUtil.isNumber(value)) {
                    return Boolean.FALSE + "##手寸值sc不正确";
                }
                if (i != scList.size() - 1) {
                    min = new Integer(list.get(0));
                } else {
                    max = new Integer(list.get(0));
                }
            }
            if (min >= max) {
                return Boolean.FALSE + "##手寸值sc不正确";
            }
            for (int j = min; j <= max; j++) {
                sc = sc + j + "#";
            }
        } else {
            return Boolean.FALSE + "##手寸sc格式错误";
        }
        return Boolean.TRUE + "##" + sc;
    }


    /**
     * xml调用转PosCard
     *
     * @param xml
     */
    @Transactional
    public String toBeanPosCard(String xml) {
        System.out.println("posCard原始报文：" + xml);
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.processAnnotations(XMLPosCard.class);
        xStream.processAnnotations(XMLPosCardParam.class);
        Object object = xStream.fromXML(xml);
        XMLPosCard obj = (XMLPosCard) object;
        XMLPosCardParam[] xmlPosCardParamsArr = obj.getXMLPosCardParam();
        if (StringUtils.isEmpty(xmlPosCardParamsArr)) {
            return Boolean.FALSE + "##空数据";
        }
        for (int i = 0; i < xmlPosCardParamsArr.length; i++) {
            XMLPosCardParam xmlPosCardParam = xmlPosCardParamsArr[i];
            XMLPosCPCardCode xmlPosCPCardCode = xmlPosCardParam.getXMLPosCPCardCode();
            XMLPosCPCardPassword xmlPosCPCardPassword = xmlPosCardParam.getXMLPosCPCardPassword();
            //物料编码必填，唯一查询依据
            if (StringUtils.isEmpty(xmlPosCPCardCode.getText())
                    || StringUtils.isEmpty(xmlPosCPCardPassword.getText())) {
                return Boolean.FALSE + "##卡号或者密码为空";
            }
            //通过卡号+密码，查询卡
            CustomCard card = customCardService.getCardByCodeAndPassword(xmlPosCPCardCode.getText(), xmlPosCPCardPassword.getText());
            if (card == null) {
                return Boolean.FALSE + "##卡号或者密码错误";
            }
            if (!StringUtils.isEmpty(card.getCardStatus())
                    && card.getCardStatus().equals(2)) {
                return Boolean.TRUE + "##状态已使用";
            }
            card.setCardUseStore(xmlPosCardParam.getXMLPosCPCardStore().getText());
            card.setCardUseUser(xmlPosCardParam.getXMLPosCPCardUser().getText());
            card.setCardUseDate(DateUtil.parseDate(xmlPosCardParam.getXMLPosCPCardUseDate().getText()));
            card.setCardPlatform(xmlPosCardParam.getXMLPosCPCardPlatform().getText());
            card.setCardPlatformOrder(xmlPosCardParam.getXMLPosCPCardPlatformOrder().getText());
            card.setCardRemark(xmlPosCardParam.getXMLPosCPCardRemark().getText());
            card.setCardPosXml(xml);
            card.setCardStatus(2);
            int result = customCardService.updateNotNull(card);
            if (result != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Boolean.FALSE + "##系统存储异常，此次不生效";
            }
        }
        return Boolean.TRUE + "##使用状态更新成功";
    }


}
