package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Table(name = "task")
public class Task extends BaseEntity {
    /**
     * 任务状态 初始化，已完成
     */
    public static final int STATUS_INIT = 0;
    public static final int STATUS_FINISH = 1;

    /**
     * 供应商名字
     */
    @Column(name = "`company`")
    private String company;

    /**
     * 订单号（本地）
     */
    @Column(name = "`order_id`")
    private String orderId;

    /**
     * jp系统的钻石编码
     */
    @Column(name = "`productid`")
    private String productid;

    /**
     * 状态
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 订单号（第三方系统）
     */
    @Column(name = "`third_order_id`")
    private String thirdOrderId;

    /**
     * 系统期限下单截止时间
     */
    @Column(name = "`last_date`")
    private Date lastDate;

    /**
     * 调用次数
     */
    @Column(name = "`num`")
    private Integer num;

    /**
     * 返回信息
     */
    @Column(name = "`content`")
    private String content;

    @Transient
    private String stoneZsbh;
}