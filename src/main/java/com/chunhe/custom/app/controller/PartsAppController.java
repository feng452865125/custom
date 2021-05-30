package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.service.SysUserService;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.framework.utils.RequestUtils;
import com.chunhe.custom.pc.model.BasePrice;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.model.RelYs;
import com.chunhe.custom.pc.model.ThirdSupplier;
import com.chunhe.custom.pc.service.BasePriceService;
import com.chunhe.custom.pc.service.PartsService;
import com.chunhe.custom.utils.DictUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by white 2018年7月18日13:39:33
 * 组件（钻石、戒指、戒臂、戒托）
 */
@RestController
@RequestMapping("/app/parts")
public class PartsAppController extends BaseController {

    @Autowired
    private PartsService partsService;

    @Autowired
    private BasePriceService basePriceService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysUserService sysUserService;

    @Value("${storePriceMultiple}")
    private String storePriceMultiple;

    @Value("${basePriceMultiple1}")
    private String basePriceMultiple1;

    @Value("${basePriceMultiple2}")
    private String basePriceMultiple2;

    private static final String LOCATION_INDIA = "locationIndia";

    /**
     * 组件列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<Parts> findParts(HttpServletRequest req) {
        Parts parts = checkContent(req);
        parts.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
        List<Parts> partsList = partsService.findPartsList(parts);
        if (parts.getStyleId() != null || (parts.getType() != null && parts.getType() == Parts.TYPE_DIAMOND)) {
            //查钻石，styleId，要分页
            PageInfo<Parts> pageInfo = RequestUtils.logicalPage(partsList);
            if (parts.getStyleId() != null) {
                //选择钻石，有主数据和没主数据，有区分
                partsService.checkHasSkuByStyleIdAndDiamond(parts.getStyleId(), pageInfo.getList());
            }
            throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, pageInfo);
        }
        //花头列表，戒臂列表，不分页
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, partsList);
    }

    /**
     * 组件关联
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/relStyleParts/find", method = RequestMethod.GET)
    @ResponseBody
    public String findRelStyleParts(HttpServletRequest req) {
        Parts parts = checkContent(req);
        List<RelYs> relYsList = partsService.findRelPartsList(parts);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, relYsList);
    }

    /**
     * 钻石列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/diamond/find", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<BasePrice> findDiamond(HttpServletRequest req) {
        Parts parts = checkContent(req);
        parts.setType(Parts.TYPE_DIAMOND);
        parts.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
        parts.setCompany(ThirdSupplier.COMPANY_KEER);//千叶自己的过滤，company != KEER
        parts.setOrderBy(DictUtils.findValueByTypeAndKey(BasePrice.BASE_ORDER_BY, parts.getOrderByType()));
        //获取门店系数，要是没有用户id 默认1，要是门店系数为Null默认1
        //用户（店铺）销售系数（默认1.8） 2020年12月18日19:29:15改为2.3
        String priceStoreMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
        String priceMultiple = priceStoreMultiple;
        if(parts.getUserId() != null && !"".equals(parts.getUserId())) {
            priceMultiple = sysUserService.storeCoefficient(Long.valueOf(parts.getUserId()));
            if(StringUtils.isEmpty(priceMultiple) || "".equals(parts.getUserId())){
                priceMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
            }
        }
        parts.setPriceMultiple1(priceMultiple);
        parts.setPriceStoreMultiple(priceStoreMultiple);
        String locationStatus = sysConfigService.getSysConfigByKey(LOCATION_INDIA, String.valueOf(SysConfig.LOCATION_INDIA));
        if("1".equals(locationStatus)){
            parts.setLocationIndia(true);
        }
        RequestUtils.startPageHelp(req);
        List<BasePrice> basePriceList = basePriceService.findDiamondGroupList(parts);
        PageInfo<BasePrice> pageInfo = new PageInfo<>(basePriceList);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, pageInfo);
    }

    /**
     * content处理
     *
     * @param req
     * @return
     */
    public Parts checkContent(HttpServletRequest req) {
        Parts parts = new Parts();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            parts = JSON.parseObject(content, Parts.class);
        }
        return parts;
    }



}


