package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.OrdersStyle;

import java.util.List;

public interface OrdersStyleMapper extends MyMapper<OrdersStyle> {

    List<OrdersStyle> findOrdersStyleList(OrdersStyle ordersStyle);

    OrdersStyle getOrdersStyle(OrdersStyle ordersStyle);
}