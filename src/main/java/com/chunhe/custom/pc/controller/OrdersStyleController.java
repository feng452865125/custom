package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.OrdersStyle;
import com.chunhe.custom.pc.service.OrdersStyleService;
import com.chunhe.custom.utils.DictUtils;
import com.github.pagehelper.ISelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by white 2018-9-8 16:07:40
 * 预约查询
 */
@Controller
@RequestMapping("/ordersStyle")
public class OrdersStyleController extends BaseController {

    @Autowired
    private OrdersStyleService ordersStyleService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('ordersStyle:page')")
    public String productList(Model model) {
        JSONObject statusList = DictUtils.findDicByType(OrdersStyle.DEAL_STATUS);
        model.addAttribute("statusList", statusList);
        return "pages/ordersStyle/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('ordersStyle:list')")
    public DataTablesResponse<OrdersStyle> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<OrdersStyle> data = ordersStyleService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        ordersStyleService.ordersStyleList(dataTablesRequest);
                    }
                });
        return data;
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasAuthority('ordersStyle:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = ordersStyleService.deleteById(id);
        return responseDeal(result);
    }

    /**
     * 详情（view），完成联系（edit）
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('ordersStyle:view')")
    public String view(@PathVariable Long id, Model model) {
        OrdersStyle ordersStyle = ordersStyleService.getOrdersStyle(id);
        model.addAttribute("ordersStyle", ordersStyle);
        return "pages/ordersStyle/view";
    }

    /**
     * 完成联系
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('ordersStyle:edit')")
    @RequestMapping(value = "/haveCall/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> haveCall(@PathVariable Long id) {
        if (ordersStyleService.haveCall(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }
}
