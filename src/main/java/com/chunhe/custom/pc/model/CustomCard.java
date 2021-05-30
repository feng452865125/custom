package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Table(name = "custom_card")
public class CustomCard extends BaseEntity {

    public final static String XLS = "xls";
    public final static String XLSX = "xlsx";

    public static final String CUSTOM_CARD_STATUS = "customCardStatus";

    /**
     * 卡任务id
     */
    @Column(name = "`card_task_id`")
    private Integer cardTaskId;

    /**
     * 卡号全部
     */
    @Column(name = "`card_code`")
    private String cardCode;


    /**
     * 卡号后面的数字，中4+后6
     */
    @Column(name = "`card_code_num`")
    private Integer cardCodeNum;

    /**
     * 卡密码
     */
    @Column(name = "`card_password`")
    private String cardPassword;

    /**
     * 合作方
     */
    @Column(name = "`card_company`")
    private String cardCompany;

    /**
     * 卡片面额
     */
    @Column(name = "`card_price`")
    private String cardPrice;

    /**
     * 创建人
     */
    @Column(name = "`card_create_user`")
    private String cardCreateUser;

    /**
     * 创建时间
     */
    @Column(name = "`card_create_date`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cardCreateDate;

    /**
     * 激活人
     */
    @Column(name = "`card_activate_user`")
    private String cardActivateUser;

    /**
     * 激活时间
     */
    @Column(name = "`card_activate_date`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cardActivateDate;

    /**
     * 使用人
     */
    @Column(name = "`card_use_user`")
    private String cardUseUser;

    /**
     * 使用时间
     */
    @Column(name = "`card_use_date`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cardUseDate;


    /**
     * 1解冻(默认)0冻结
     */
    @Column(name = "`card_enable`")
    private Integer cardEnable;

    /**
     * 冻结人/解冻人
     */
    @Column(name = "`card_enable_user`")
    private String cardEnableUser;

    /**
     * 冻结/解冻时间
     */
    @Column(name = "`card_enable_date`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cardEnableDate;


    /**
     * 0未激活，1已激活，2已使用
     */
    @Column(name = "`card_status`")
    private Integer cardStatus;

    /**
     * 使用门店
     */
    @Column(name = "`card_use_store`")
    private String cardUseStore;

    /**
     * 过期时间
     */
    @Column(name = "`card_expire_date`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cardExpireDate;

    @Column(name = "`card_platform`")
    public String cardPlatform;//平台（天猫，京东）

    @Column(name = "`card_platform_order`")
    public String cardPlatformOrder;//平台的订单号

    @Column(name = "`card_remark`")
    public String cardRemark;//备注

    @Column(name = "`card_pos_xml`")
    public String cardPosXml;//pos原始报文

    /**************************************************/

    @Transient
    private String cardCodeBefore;

    @Transient
    private String cardCodeStart;

    @Transient
    private String cardCodeEnd;

    @Transient
    private String cardOrderBy;

    @Transient
    private String cardStatusName;

    @Transient
    private String cardTaskName;

}