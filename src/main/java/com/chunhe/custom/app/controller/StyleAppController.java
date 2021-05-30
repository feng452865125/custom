package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.framework.utils.RequestUtils;
import com.chunhe.custom.pc.model.JewelryType;
import com.chunhe.custom.pc.model.Product;
import com.chunhe.custom.pc.model.Style;
import com.chunhe.custom.pc.model.UniqueGroup;
import com.chunhe.custom.pc.service.JewelryTypeService;
import com.chunhe.custom.pc.service.ProductService;
import com.chunhe.custom.pc.service.StyleService;
import com.chunhe.custom.pc.service.UniqueGroupService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by white 2018年7月19日09:48:58
 * 样式
 */
@RestController
@RequestMapping("/app/style")
public class StyleAppController extends BaseController {

    @Autowired
    private StyleService styleService;

    @Autowired
    private JewelryTypeService jewelryTypeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UniqueGroupService uniqueGroupService;

    /**
     * 样式列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public String findStyleList(HttpServletRequest req) {
        Style style = checkContent(req);
        //后台维护是否展示
        style.setEnabled(Style.ENABLED_TRUE);
        if (style.getIsRecommend() != null && style.getIsRecommend() == Style.RECOMMEND_TRUE) {
            List<Style> styleList = styleService.findStyleListApp(style);
            throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, styleList);
        } else {
            RequestUtils.startPageHelp(req);
            List<Style> styleList = styleService.findStyleListApp(style);
            PageInfo<Style> pageInfo = new PageInfo<>(styleList);
            throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, pageInfo);
        }
    }

    /**
     * 样式详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String getStyle(HttpServletRequest req) throws CloneNotSupportedException {
        Style st = checkContent(req);
        Style style = styleService.getStyleDetail(st);
        JewelryType jewelryType = jewelryTypeService.getJewelryType(style.getType().longValue());
        if (jewelryType.getName().equals(Style.STYLE_TYPE_COUPLE)) {
            //情侣戒--查男女戒，返回list
            Product product = new Product();
            product.setStyleId(style.getId());
            product.setGroupBy("type");
            List<Product> productList = productService.findProductList(product);
            for (int i = 0; i < productList.size(); i++) {
                Product pro = productList.get(i);
                List scList = new ArrayList();
                List handList = new ArrayList();
                Product sex = new Product();
                sex.setType(pro.getType());
                sex.setStyleId(pro.getStyleId());
                sex.setGroupBy("ex_sc");
                sex.setOrderBy("ex_sc + 0 asc");
                List<Product> sexList = productService.findProductList(sex);
                //重新group by手寸
                for (int j = 0; j < sexList.size(); j++) {
                    productService.addToList(scList, sexList.get(j).getExSc());
                }
                handList = productService.dealList(handList, scList);
                style.setHandList(handList);
                if (pro.getType() == Product.TYPE_MALE) {
                    style.setImgUrlBoy(pro.getImgMaxUrl());
                } else {
                    style.setImgUrlGirl(pro.getImgMaxUrl());
                }
                style.setWearUrl30(pro.getWearUrl30());
                style.setWearUrl50(pro.getWearUrl50());
                style.setWearUrl70(pro.getWearUrl70());
                style.setWearUrl100(pro.getWearUrl100());
                pro.setStyle((Style) style.clone());
            }
            throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, productList);
        }
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, style);
    }

    /**
     * 样式类型列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/jewelryType/find", method = RequestMethod.GET)
    @ResponseBody
    public String findJewelryTypeList(HttpServletRequest req) {
        List<JewelryType> jewelryTypeList = jewelryTypeService.findJewelryTypeList(new JewelryType());
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, jewelryTypeList);
    }

    /**
     * unique类型列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/uniqueGroup/find", method = RequestMethod.GET)
    @ResponseBody
    public String findUniqueGroupList(HttpServletRequest req) {
        List<UniqueGroup> uniqueGroupList = uniqueGroupService.findUniqueGroupList(new UniqueGroup());
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, uniqueGroupList);
    }


    /**
     * content处理
     *
     * @param req
     * @return
     */
    public Style checkContent(HttpServletRequest req) {
        Style style = new Style();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            style = JSON.parseObject(content, Style.class);
        }
        return style;
    }

}
