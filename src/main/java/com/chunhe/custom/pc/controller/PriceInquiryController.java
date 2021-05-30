package com.chunhe.custom.pc.controller;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.service.SysUserService;
import com.chunhe.custom.pc.model.BasePrice;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.service.PriceInquiryService;
import com.github.pagehelper.ISelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


/**
 * 价格查询
 */
@Controller
@RequestMapping("/priceInquiry")
public class PriceInquiryController  {


    @Autowired
    private PriceInquiryService priceInquiryService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('priceInquiry:page')")
    public String productList(Model model) {
        return "pages/priceInquiry/list";
    }

    /**
     * 查询列表
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('priceInquiry:list')")
    public DataTablesResponse<BasePrice> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest
            , HttpSession session) {
        //获取门店系数，要是没有用户id 默认1，要是门店系数为Null默认1
        final Parts parts = parts(dataTablesRequest,session);
        DataTablesResponse<BasePrice> data = priceInquiryService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        priceInquiryService.priceList(parts);
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
        return priceInquiryService.parts(dataTablesRequest,session);
    }

    @GetMapping("/view/{id}/{diamondId}/{productId}")
    public String getBasePrice(@PathVariable Long id,@PathVariable Long diamondId, @PathVariable Long productId,Model model){
        BasePrice basePrice = priceInquiryService.getBasePrice(id,diamondId,productId);
        model.addAttribute("basePrice",basePrice);
        return "pages/priceInquiry/view";
    }
}
