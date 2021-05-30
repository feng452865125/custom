package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.service.SysUserService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.pc.mapper.BasePriceMapper;
import com.chunhe.custom.pc.mapper.PartsMapper;
import com.chunhe.custom.pc.mapper.ProductMapper;
import com.chunhe.custom.pc.model.BasePrice;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.model.Style;
import com.chunhe.custom.pc.model.ThirdSupplier;
import com.chunhe.custom.pc.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CertificateInquiryService extends BaseService<BasePrice> {

    @Autowired
    private BasePriceMapper basePriceMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StyleService styleService;

    @Autowired
    private BasePriceService basePriceService;

    @Autowired
    private PartsBigService partsBigService;

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private PartsService partsService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysConfigService sysConfigService;

    @Value("${storePriceMultiple}")
    private static String storePriceMultiple;

    /**
     * 获取列表
     *
     * @param parts
     * @return
     */
    public List<BasePrice> certificateInquiryList(Parts parts) {
        if (parts == null) {
            return null;
        }
        long startTime = System.currentTimeMillis() / 1000;
        parts.setVg3(false);
        String type = "";
        String qg = parts.getExZsQg();
        String pg = parts.getExZsPg();
        String dc = parts.getExZsDc();
        if ((!StringUtils.isEmpty(qg) && qg.equals("EX"))
                && (!StringUtils.isEmpty(pg) && pg.equals("EX"))
                && (!StringUtils.isEmpty(dc) && dc.equals("EX"))) {
            //三个都是EX
            type = "3EX";
        } else if ((!StringUtils.isEmpty(qg) && qg.equals("VG"))
                || (!StringUtils.isEmpty(pg) && pg.equals("VG"))
                || (!StringUtils.isEmpty(dc) && dc.equals("VG"))) {
            //任意一个VG，处于3VG
            parts.setVg3(true);
            type = "3VG";
        } else {
            //其他查全部
            parts.setExZsQg(null);
            parts.setExZsPg(null);
            parts.setExZsDc(null);
            type = "all";
        }
        System.out.println("切工=" + qg + "，抛光=" + pg + "，对称=" + dc + "|| PC=" + type);
        List<BasePrice> basePriceList = basePriceMapper.findDiamondGroupListPC(parts);
        for (int i = 0; i < basePriceList.size(); i++) {
            BasePrice basePrice = basePriceList.get(i);
            if (parts.getVg3()) {
                basePrice.setQg("VG");
                basePrice.setPg("VG");
                basePrice.setDc("VG");
            }
        }
        long endTime = System.currentTimeMillis() / 1000;
        System.out.println("证书号查询PC：" + (endTime - startTime));
        return basePriceList;
    }


    /**
     * 获取参数对象
     *
     * @param dataTablesRequest
     * @param session
     * @return
     */
    public Parts parts(DataTablesRequest dataTablesRequest, HttpSession session) {
        Parts parts = new Parts();
        DataTablesRequest.Column styleId = dataTablesRequest.getColumn("styleId");
        if (StringUtils.isNotBlank(styleId.getSearch().getValue())) {
            Long styleIdColumn = ConvertUtil.convert(styleId.getSearch().getValue(), Long.class);
            parts.setStyleId(styleIdColumn);
        } else {
            DataTablesRequest.Column serice = dataTablesRequest.getColumn("serice");
            DataTablesRequest.Column moral = dataTablesRequest.getColumn("moral");
            if (StringUtils.isNotBlank(serice.getSearch().getValue())
                    && StringUtils.isNotBlank(moral.getSearch().getValue())) {
                List<Style> style = styleService.dropDownBox(moral.getSearch().getValue(), serice.getSearch().getValue());
                if (style.size() > 0) {
                    parts.setStyleId(style.get(0).getId());
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        DataTablesRequest.Column jbkd = dataTablesRequest.getColumn("jbKd");
        if (StringUtils.isNotBlank(jbkd.getSearch().getValue())) {
            parts.setProductJbkd(jbkd.getSearch().getValue());
        } else {
            return null;
        }

        DataTablesRequest.Column ys = dataTablesRequest.getColumn("zsYs");
        if (StringUtils.isNotBlank(ys.getSearch().getValue())) {
            parts.setExZsYs(ys.getSearch().getValue());
        } else {
            return null;
        }

        DataTablesRequest.Column jd = dataTablesRequest.getColumn("zsJd");
        if (StringUtils.isNotBlank(jd.getSearch().getValue())) {
            parts.setExZsJd(jd.getSearch().getValue());
        } else {
            return null;
        }

        DataTablesRequest.Column exZsQg = dataTablesRequest.getColumn("zsQg");
        if (StringUtils.isNotBlank(exZsQg.getSearch().getValue())) {
            parts.setExZsQg(exZsQg.getSearch().getValue());
        } else {
            return null;
        }

        DataTablesRequest.Column exZsPg = dataTablesRequest.getColumn("zsPg");
        if (StringUtils.isNotBlank(exZsPg.getSearch().getValue())) {
            parts.setExZsPg(exZsPg.getSearch().getValue());
        } else {
            return null;
        }

        DataTablesRequest.Column exZsDc = dataTablesRequest.getColumn("zsDc");
        if (StringUtils.isNotBlank(exZsDc.getSearch().getValue())) {
            parts.setExZsDc(exZsDc.getSearch().getValue());
        } else {
            return null;
        }

        DataTablesRequest.Column zl = dataTablesRequest.getColumn("zl");
        if (StringUtils.isNotBlank(zl.getSearch().getValue())) {
            BigDecimal p1 = new BigDecimal(zl.getSearch().getValue());
            BigDecimal p2 = new BigDecimal(1000);
            int zsZl = p1.multiply(p2).intValue();
            parts.setZsZlMin(Integer.valueOf(zsZl));
            parts.setExZsZlMin(Integer.valueOf(zsZl));
        } else {
            return null;
        }

        parts.setType(Parts.TYPE_DIAMOND);
        parts.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
        parts.setCompany(ThirdSupplier.COMPANY_KEER);//千叶自己的过滤，company != KEER
//        parts.setOrderBy("bp.kl_min,np.ex_zs_zl,price asc");
        parts.setOrderBy("price asc, zl desc");
        //用户（店铺）销售系数（默认1.8） 2020年12月18日19:29:15改为2.3
        String priceStoreMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
        String priceMultiple = priceStoreMultiple;
        if (session.getAttribute("userId") != null) {
            Long userid = ConvertUtil.convert(session.getAttribute("userId"), Long.class);
            priceMultiple = sysUserService.storeCoefficient(userid);
            if (StringUtils.isEmpty(priceMultiple)) {
                priceMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
            }
        }
        session.setAttribute("priceMultiple", Double.valueOf(priceMultiple));
        parts.setPriceMultiple1(priceMultiple);
        parts.setPriceStoreMultiple(priceStoreMultiple);
        return parts;
    }


}
