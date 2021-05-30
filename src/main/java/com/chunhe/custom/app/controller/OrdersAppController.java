package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.framework.utils.RequestUtils;
import com.chunhe.custom.pc.model.AdditionalCosts;
import com.chunhe.custom.pc.model.Orders;
import com.chunhe.custom.pc.model.OrdersStyle;
import com.chunhe.custom.pc.service.AdditionalCostsService;
import com.chunhe.custom.pc.service.OrdersService;
import com.chunhe.custom.pc.service.OrdersStyleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by white 2018年7月24日14:18:17
 * 订单
 */
@RestController
@RequestMapping("/app/orders")
public class OrdersAppController extends BaseController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersStyleService ordersStyleService;

    @Autowired
    private AdditionalCostsService additionalCostsService;

    /**
     * 订单列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public String findOrdersList(HttpServletRequest req) {
        Orders orders = checkContent(req);
        RequestUtils.startPageHelp(req);
        List<Orders> ordersList = ordersService.findOrdersListApp(orders);
        PageInfo<Orders> pageInfo = new PageInfo<>(ordersList);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, pageInfo);
    }

    /**
     * 订单详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getOrders(HttpServletRequest req, @PathVariable Long id) {
        Orders orders = ordersService.getOrdersApp(id);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, orders);
    }

    /**
     * 创建订单（普通款式）
     * @param req
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public String createOrders(HttpServletRequest req) throws Exception {
        Orders orders = checkContent(req);
        ordersService.createOrders(orders);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, "下单成功");
    }

    /**
     * 取消订单（jp）
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public String cancelOrders(HttpServletRequest req) throws Exception {
        Orders orders = checkContent(req);
        ordersService.cancelOrders(orders);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, "取消成功");
    }

    /**
     * 创建订单（情侣戒）
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/couple/create", method = RequestMethod.POST)
    @ResponseBody
    public String createCoupleOrders(HttpServletRequest req) throws IOException {
        Orders orders = checkContent(req);
        ordersService.createCoupleOrders(orders);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, "下单成功");
    }

    /**
     * 提交预约
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/style/create", method = RequestMethod.POST)
    @ResponseBody
    public String ordersStyleCreate(HttpServletRequest req) {
        OrdersStyle ordersStyle = new OrdersStyle();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            ordersStyle = JSON.parseObject(content, OrdersStyle.class);
        }
        ordersStyleService.create(ordersStyle);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, "提交预约成功");
    }

    /**
     * 附加费用列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/addCosts/find", method = RequestMethod.GET)
    @ResponseBody
    public String findAddCostsList(HttpServletRequest req) {
        List<AdditionalCosts> additionalCostsList = additionalCostsService.findAdditionalCostsList(new AdditionalCosts());
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, additionalCostsList);
    }

    /**
     * 配置枚举查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/find/enum", method = RequestMethod.GET)
    @ResponseBody
    public String findEnum(HttpServletRequest req) {
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS);
    }

    /**
     * 配置枚举查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/get/enum", method = RequestMethod.GET)
    @ResponseBody
    public String getEnum(HttpServletRequest req) {
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS);
    }


    /**
     * content处理
     *
     * @param req
     * @return
     */
    public Orders checkContent(HttpServletRequest req) {
        Orders orders = new Orders();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            orders = JSON.parseObject(content, Orders.class);
        }
        return orders;
    }
}
