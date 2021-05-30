package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Orders;

import java.util.List;

public interface OrdersMapper extends MyMapper<Orders> {

    List<Orders> findOrdersListApp(Orders orders);

    List<Orders> findOrdersList(Orders orders);

    Orders getOrders(Orders orders);

    Orders getExpireOrders(Orders orders);
}