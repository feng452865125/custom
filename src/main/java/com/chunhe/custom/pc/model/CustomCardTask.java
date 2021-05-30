package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Table(name = "custom_card_task")
public class CustomCardTask extends BaseEntity {

    /**
     * 处理状态 0、初始，1、已完成
     */
    public static final String CUSTOM_CARD_TASK_STATUS = "customCardTaskStatus";
    public static final int CUSTOM_CARD_TASK_STATUS_INIT = 0;
    public static final int CUSTOM_CARD_TASK_STATUS_FINISH = 1;

    public static final String CUSTOM_CARD_TASK_PRICE = "cardTaskPrice";//制卡任务卡片面额
    public static final String CUSTOM_CARD_TASK_ENABLE_MONTH = "cardTaskEnableMonth";//制卡任务卡片有效期限，月

    /**
     * 制卡任务名称
     */
    @Column(name = "`card_task_name`")
    private String cardTaskName;

    /**
     * 合作方
     */
    @Column(name = "`card_task_company`")
    private String cardTaskCompany;

    /**
     * 卡片数量
     */
    @Column(name = "`card_task_count`")
    private Integer cardTaskCount;

    /**
     * 卡片面额
     */
    @Column(name = "`card_task_price`")
    private String cardTaskPrice;

    /**
     * 有效期
     */
    @Column(name = "`card_task_last_date`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cardTaskLastDate;

    /**
     * 卡号自定义前六位
     */
    @Column(name = "`card_task_code_before`")
    private String cardTaskCodeBefore;

    /**
     * 卡号自定义中四位
     */
    @Column(name = "`card_task_code_middle`")
    private String cardTaskCodeMiddle;

    /**
     * 卡号 后6位
     */
    @Column(name = "`card_task_code_after`")
    private String cardTaskCodeAfter;

    /**
     * 0禁用，1启用（默认）
     */
    @Column(name = "`card_task_enable`")
    private Integer cardTaskEnable;

    /**
     * 0创建中（默认），1已完成
     */
    @Column(name = "`card_task_status`")
    private Integer cardTaskStatus;

    /**
     * 创建人
     */
    @Column(name = "`card_task_create_user`")
    private String cardTaskCreateUser;

    /**************************************************/


    /**
     * 搜索条件
     */
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @Transient
    private String cardTaskCode;

    @Transient
    private String cardTaskStatusName;
}