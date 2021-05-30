package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.DateUtil;
import com.chunhe.custom.pc.mapper.OrdersStyleMapper;
import com.chunhe.custom.pc.model.OrdersStyle;
import com.chunhe.custom.pc.model.Style;
import com.chunhe.custom.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by white 2018-9-8 16:08:20
 */
@Service
public class OrdersStyleService extends BaseService<OrdersStyle> {

    @Autowired
    private OrdersStyleMapper ordersStyleMapper;

    @Autowired
    private StyleService styleService;


    //全部列表
    public List<OrdersStyle> findOrdersStyleList(OrdersStyle ordersStyle) {
        List<OrdersStyle> ordersStyleList = ordersStyleMapper.findOrdersStyleList(ordersStyle);
        for (int i = 0; i < ordersStyleList.size(); i++) {
            OrdersStyle order = ordersStyleList.get(i);
            order.setStatusName(DictUtils.findValueByTypeAndKey(OrdersStyle.DEAL_STATUS, order.getStatus()));
        }
        return ordersStyleList;
    }

    /**
     * 查询数据
     */
    public List<OrdersStyle> ordersStyleList(DataTablesRequest dataTablesRequest) {
        OrdersStyle ordersStyle = new OrdersStyle();
        //排序
        String orderBy = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orderBy)) {
            ordersStyle.setOrderBy(orderBy);
        }
        //店铺编码
        DataTablesRequest.Column storeCode = dataTablesRequest.getColumn("storeCode");
        if (StringUtils.isNotBlank(storeCode.getSearch().getValue())) {
            ordersStyle.setStoreCode(storeCode.getSearch().getValue());
        }
        //店铺名
        DataTablesRequest.Column storeName = dataTablesRequest.getColumn("storeName");
        if (StringUtils.isNotBlank(storeName.getSearch().getValue())) {
            ordersStyle.setStoreName(storeName.getSearch().getValue());
        }
        //订单开始时间
        DataTablesRequest.Column startDateColumn = dataTablesRequest.getColumn("startDate");
        if (StringUtils.isNotBlank(startDateColumn.getSearch().getValue())) {
            String startDate = ConvertUtil.convert(startDateColumn.getSearch().getValue(), String.class);
            ordersStyle.setStartDate(DateUtil.getDateStart(DateUtil.parseDate(startDate, "yyyy-MM-dd")));
        }
        //订单结束时间
        DataTablesRequest.Column endDateColumn = dataTablesRequest.getColumn("endDate");
        if (StringUtils.isNotBlank(endDateColumn.getSearch().getValue())) {
            String endDate = ConvertUtil.convert(endDateColumn.getSearch().getValue(), String.class);
            ordersStyle.setEndDate(DateUtil.getDateEnd(DateUtil.parseDate(endDate, "yyyy-MM-dd")));
        }
        //状态
        DataTablesRequest.Column status = dataTablesRequest.getColumn("status");
        if (StringUtils.isNotBlank(status.getSearch().getValue())) {
            ordersStyle.setStatus(new Integer(status.getSearch().getValue()));
        }
        //多表关联，不用example
        List<OrdersStyle> ordersStyleList = this.findOrdersStyleList(ordersStyle);
        return ordersStyleList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public OrdersStyle getOrdersStyle(Long id) {
        //预约信息
        OrdersStyle ordersStyle = new OrdersStyle();
        ordersStyle.setId(id);
        OrdersStyle order = ordersStyleMapper.getOrdersStyle(ordersStyle);
        order.setStatusName(DictUtils.findValueByTypeAndKey(OrdersStyle.DEAL_STATUS, order.getStatus()));
        //款式信息
        Style style = styleService.getStyle(order.getStyleId());
        order.setStyle(style);
        return order;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional
    public ServiceResponse deleteById(Long id) {
        OrdersStyle ordersStyle = selectByKey(id);
        if (expireNotNull(ordersStyle) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

    /**
     * 完成联系
     *
     * @param
     */
    @Transactional(readOnly = false)
    public Boolean haveCall(Long id) {
        OrdersStyle ordersStyle = selectByKey(id);
        ordersStyle.setStatus(OrdersStyle.HAVE_CALL_YES);
        return updateNotNull(ordersStyle) == 1;
    }

    /**
     * 完成联系
     *
     * @param
     */
    @Transactional
    public void create(OrdersStyle ordersStyle) {
        if (CheckUtil.checkNull(ordersStyle.getStoreCode())) {
            throw new RFException("店铺编号为空");
        }
        if (CheckUtil.checkNull(ordersStyle.getStyleId())) {
            throw new RFException("款式编号为空");
        }
        if (CheckUtil.checkNull(ordersStyle.getCustomerMobile())) {
            throw new RFException("请留下号码，以便客服联系");
        }
        super.insertNotNull(ordersStyle);
    }

}
