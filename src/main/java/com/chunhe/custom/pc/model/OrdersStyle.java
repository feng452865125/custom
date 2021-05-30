package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 预约查询
 */
@Getter
@Setter
@Table(name = "orders_style")
public class OrdersStyle extends BaseEntity {
    /**
     * 处理状态 1、未处理，2、已处理
     */
    public static final String DEAL_STATUS = "ordersStyleStatus";
    public static final int HAVE_CALL_NO = 1;
    public static final int HAVE_CALL_YES = 2;
    /**
     * 店铺id
     */
    @Column(name = "`store_code`")
    private String storeCode;

    /**
     * 样式id
     */
    @Column(name = "`style_id`")
    private Long styleId;

    /**
     * 顾客名字
     */
    @Column(name = "`customer_name`")
    private String customerName;

    /**
     * 顾客手机号
     */
    @Column(name = "`customer_mobile`")
    private String customerMobile;

    /**
     * 是否处理，1未处理，2已处理
     */
    @Column(name = "`status`")
    private Integer status;

    /************************************/

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @Transient
    private String orderBy;

    @Transient
    private String statusName;

    @Transient
    private String storeName;

    @Transient
    private String styleName;

    @Transient
    private Style style;
}