package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.service.SysUserService;
import com.chunhe.custom.pc.model.BasePrice;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.service.CertificateInquiryService;
import com.chunhe.custom.pc.service.ProductService;
import com.chunhe.custom.utils.DictUtils;
import com.github.pagehelper.ISelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


/**
 * 证书号查询
 */
@Controller
@RequestMapping("/certificateInquiry")
public class CertificateInquiryController {


    @Autowired
    private CertificateInquiryService certificateInquiryService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('certificateInquiry:page')")
    public String productList(Model model) {
        JSONObject zsYsList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YS);
        JSONObject zsJdList = DictUtils.findDicByType(BasePrice.BASE_PRICE_JD);
        JSONObject zsQgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_QG);
        JSONObject zsPgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_PG);
        JSONObject zsDcList = DictUtils.findDicByType(BasePrice.BASE_PRICE_DC);
        JSONObject zsYgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YG);
        model.addAttribute("zsYsList", zsYsList);
        model.addAttribute("zsJdList", zsJdList);
        model.addAttribute("zsQgList", zsQgList);
        model.addAttribute("zsPgList", zsPgList);
        model.addAttribute("zsDcList", zsDcList);
        model.addAttribute("zsYgList", zsYgList);
        return "pages/certificateInquiry/list";
    }

    /**
     * 查询列表
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('certificateInquiry:list')")
    public DataTablesResponse<BasePrice> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest
            , HttpSession session) {
        //获取门店系数，要是没有用户id 默认1，要是门店系数为Null默认1
        final Parts parts = parts(dataTablesRequest,session);
        DataTablesResponse<BasePrice> data = certificateInquiryService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        certificateInquiryService.certificateInquiryList(parts);
                    }
                });
        return data;
    }


    /**
     * 获取Parts对象
     * 如果放到doSelect()方法里查询，会导致分页失败，和查询结果不对
     * @return
     */
    public Parts parts(DataTablesRequest dataTablesRequest,HttpSession session){
        return certificateInquiryService.parts(dataTablesRequest,session);
    }

}
