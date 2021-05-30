package com.chunhe.custom.pc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Getter
@Setter
@Table(name = "order_repair")
public class OrderRepair extends BaseEntity {
    /**
     * 报修订单的状态：1、未接单，2、已接单，3、待勘察，4、待确认，5、待完成，6、待评价，7、已评价，8、拒绝
     */
    public static final String ORDER_REPAIR_STATUS = "orderRepairStatus";
    public static final int ORDER_REPAIR_STATUS_INIT = 1;
    public static final int ORDER_REPAIR_STATUS_ACCEPT = 2;
    public static final int ORDER_REPAIR_STATUS_FOR_OFFER = 3;
    public static final int ORDER_REPAIR_STATUS_FOR_SURE = 4;
    public static final int ORDER_REPAIR_STATUS_FOR_WORK = 5;
    public static final int ORDER_REPAIR_STATUS_FOR_EVALUATE = 6;
    public static final int ORDER_REPAIR_STATUS_FINISH = 7;
    public static final int ORDER_REPAIR_STATUS_REFUSE = 8;
    /**
     * 用户id
     */
    @Column(name = "`user_id`")
    private Long userId;

    /**
     * 部门主管id
     */
    @Column(name = "`manager_user_id`")
    private Long managerUserId;

    /**
     * 维修工id
     */
    @Column(name = "`repair_user_id`")
    private Long repairUserId;

    /**
     * 报修类型
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 报修地址
     */
    @Column(name = "`address`")
    private String address;

    /**
     * 订单状态
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 用户报修图片路径
     */
    @Column(name = "`create_url1`")
    private String createUrl1;
    /**
     * 用户报修图片路径
     */
    @Column(name = "`create_url2`")
    private String createUrl2;

    /**
     * 用户报修备注
     */
    @Column(name = "`create_remark`")
    private String createRemark;

    /**
     * 勘察报价选择材料
     */
    @Column(name = "`offer_material`")
    private String offerMaterial;

    /**
     * 勘察报价选择材料生成总价格
     */
    @Column(name = "`offer_price`")
    private Integer offerPrice;

    /**
     * 勘察报价备注
     */
    @Column(name = "`offer_remark`")
    private String offerRemark;

    /**
     * 勘察报价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "`offer_date`")
    private Date offerDate;

    /**
     * 完工图片路径
     */
    @Column(name = "`finish_url1`")
    private String finishUrl1;

    /**
     * 完工图片路径
     */
    @Column(name = "`finish_url2`")
    private String finishUrl2;

    /**
     * 完工备注
     */
    @Column(name = "`finish_remark`")
    private String finishRemark;

    /**
     * 完工时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "`finish_date`")
    private Date finishDate;

    /**
     * 评价--质量，打分
     */
    @Column(name = "`evaluate_quality`")
    private Integer evaluateQuality;

    /**
     * 评价--时效，打分
     */
    @Column(name = "`evaluate_timeliness`")
    private Integer evaluateTimeliness;

    /**
     * 评价--态度，打分
     */
    @Column(name = "`evaluate_attitude`")
    private Integer evaluateAttitude;

    /**
     * 评价备注
     */
    @Column(name = "`evaluate_remark`")
    private String evaluateRemark;

    /**
     * 评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "`evaluate_date`")
    private Date evaluateDate;

    /**
     * 拒绝的理由
     */
    @Column(name = "`refuse_remark`")
    private String refuseRemark;

    /*******************************************************************/

    @Transient
    private String userName;

    @Transient
    private String userMobile;

    @Transient
    private String managerUserName;

    @Transient
    private String repairUserName;

    @Transient
    private String repairUserMobile;

    @Transient
    private String typeName;

    @Transient
    private String statusName;

//    @Transient
//    private List<OrderRepairDetail> orderRepairDetailList;

    @Transient
    private int otherPrice;
}