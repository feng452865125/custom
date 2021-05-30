package com.chunhe.custom.pc.controller;

import com.chunhe.custom.framework.utils.ConvertUtil;
import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.Orders;
import com.chunhe.custom.pc.service.OrdersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by white 2018年7月23日20:39:00
 * 订单查询
 */
@Controller
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    @Autowired
    private OrdersService ordersService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('orders:page')")
    public String productList(Model model) {
        return "pages/orders/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('orders:list')")
    public DataTablesResponse<Orders> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Orders> data = ordersService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        ordersService.ordersList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('orders:add')")
    public String add(Model model) {
        return "pages/orders/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('orders:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = ordersService.save(map);
        return responseDeal(result);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasAuthority('orders:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = ordersService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('orders:edit')")
    public String edit(@PathVariable Long id, Model model) {
        Orders orders = ordersService.getOrders(id);
        model.addAttribute("orders", orders);
        return "pages/orders/edit";
    }

    /**
     * 修改
     *
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('orders:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);

        String zsBh =  ConvertUtil.convert(map.get("zsBh"), String.class).trim();
        ServiceResponse result = null;
        if(!StringUtils.isEmpty(zsBh)){
            result = ordersService.update2(map);
        } else {
            result = ordersService.update(map);
        }
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return responseDeal(result);
    }



    /**
     * 查询
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('orders:view')")
    public String view(@PathVariable Long id, Model model) {
        Orders orders = ordersService.getOrders(id);
        model.addAttribute("orders", orders);
        return "pages/orders/view";
    }

}
