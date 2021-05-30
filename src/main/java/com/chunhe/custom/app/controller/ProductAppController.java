package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.pc.mapper.StyleMapper;
import com.chunhe.custom.pc.model.Printing;
import com.chunhe.custom.pc.model.Product;
import com.chunhe.custom.pc.service.PrintingService;
import com.chunhe.custom.pc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by white 2018年7月19日09:48:58
 * 产品
 */
@RestController
@RequestMapping("/app/product")
public class ProductAppController extends BaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PrintingService printingService;

    @Autowired
    private StyleMapper styleMapper;

    /**
     * 产品列表(下单时)
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/findByPartsCode", method = RequestMethod.GET)
    @ResponseBody
    public String findProductListByPartsCode(HttpServletRequest req) throws CloneNotSupportedException {
        Product product = checkContent(req);
        List<Product> productList = productService.findProductListByPartsCode(product);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, productList);
    }

    /**
     * 产品列表(下单时)2018年9月28日11:34:10
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/findProductList", method = RequestMethod.GET)
    @ResponseBody
    public String findProductList(HttpServletRequest req) {
        Product product = checkContent(req);
        product.setOrderBy("ex_jb_kd + 0 asc");
        List<Product> productList = productService.findProductListApp(product);
        HashMap<String, Object> map = productService.findProductGroupBy(productList);
        map.put("productList", productList);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, map);
    }

    /**
     * 印花列表(下单时)
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/printing/find", method = RequestMethod.GET)
    @ResponseBody
    public String findProductPrintingList(HttpServletRequest req) {
        Printing printing = new Printing();
        List<Printing> printingList = printingService.findPrintingList(printing);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, printingList);
    }

    /**
     * 数据库检查是否存在sku
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/checkKtCode", method = RequestMethod.GET)
    @ResponseBody
    public String checkKtCode(HttpServletRequest req) {
        Product product = checkContent(req);
        Product pro = productService.checkKtCode(product);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, pro);
    }

    /**
     * content处理
     *
     * @param req
     * @return
     */
    public Product checkContent(HttpServletRequest req) {
        Product product = new Product();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            product = JSON.parseObject(content, Product.class);
        }
        return product;
    }

}
