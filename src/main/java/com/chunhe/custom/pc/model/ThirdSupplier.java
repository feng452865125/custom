package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * 第3方钻石维护
 */
@Getter
@Setter
@Table(name = "third_supplier")
public class ThirdSupplier extends BaseEntity {
    /**
     * 合作开关 0、关闭，1、开启
     */
    public static final String THIRD_SUPPLIER_STATUS = "thirdSupplierStatus";

    /**
     * 公司名
     */
    public static final String COMPANY_KEER = "KEER";
    public static final String COMPANY_JP = "JP";
    public static final String COMPANY_CHINASTAR = "CHINASTAR";
    public static final String COMPANY_HB = "HB";
    public static final String COMPANY_HB2 = "HB2";
    public static final String COMPANY_PG = "PG";
    public static final String COMPANY_DIAMART = "DIAMART";
    public static final String COMPANY_ONEPLUSONE = "OPO";
    public static final String COMPANY_KEERCOM= "keercom";
    public static final String COMPANY_DHA = "DHA";
    public static final String COMPANY_KBS = "KBS";
    public static final String COMPANY_HAO = "HAO";
    public static final String COMPANY_YBL = "YBL";
    public static final String COMPANY_EX3 = "EX3";
    public static final String COMPANY_KEERSAP = "KEERSAP";
    public static final String COMPANY_HST = "HST";
    public static final String COMPANY_YZ = "YZ";
    public static final String COMPANY_JP2 = "JP2";
    public static final String COMPANY_KDL = "KDL";

    /**
     * 返回状态
     */
    public static final int STATUS_FAIL = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_NET_ERROR = -404;
    public static final int STATUS_DATA_ERROR = -1;

    public static final String JP_LOGIN = "viplogin";//登录
    public static final String JP_QUERY_DIAMONDS = "querydiamondstock";//库存查询
    public static final String JP_QUERY_PREFERENTIAL_DIAMONDS = "QueryPreferentialDiamondStock";//新库存查询2020年4月21日21:57:16
    public static final String JP_LOCK = "lockdiamond";//加锁，解锁
    public static final String JP_CREATE_ORDER = "confirmorder";//下单

    /**
     * 加锁islock=1
     * 解锁islock=0
     */
    public static final String STONE_ISLOCK_LOCK = "1";
    public static final String STONE_ISLOCK_UNLOCK = "0";

    /**
     * 钻石状态1正常可用2已锁可用（自己锁定的货）3已锁不可用
     */
    public static final int STONE_STATUS_UNLOCK = 1;
    public static final int STONE_STATUS_MYLOCK = 2;
    public static final int STONE_STATUS_LOCK = 3;

    /**
     * 加锁解锁，失败0，成功1
     */
    public static final int OPERATE_STATUS_FAIL = 0;
    public static final int OPERATE_STATUS_SUCCESS = 1;

    /**
     * 启用状态-启用
     */
    public static final Boolean ENABLED_TRUE = Boolean.TRUE;

    /**
     * * 启用状态-禁用
     */
    public static final Boolean ENABLED_FALSE = Boolean.FALSE;

    /**
     * 公司名
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 公司名英文简称
     */
    @Column(name = "`short_name`")
    private String shortName;

    /**
     * 评分，作为优先级用
     */
    @Column(name = "`score`")
    private Integer score;

    /**
     * 公司地址
     */
    @Column(name = "`address`")
    private String address;

    /**
     * 千叶设置的登录账号
     */
    @Column(name = "`keer_username`")
    private String keerUsername;

    /**
     * 千叶设置的登录密码
     */
    @Column(name = "`keer_password`")
    private String keerPassword;

    /**
     * 负责人姓名
     */
    @Column(name = "`man_name`")
    private String manName;

    /**
     * 负责人号码
     */
    @Column(name = "`man_mobile`")
    private String manMobile;

    /**
     * 负责人邮箱
     */
    @Column(name = "`man_email`")
    private String manEmail;

    /**
     * 接口地址
     */
    @Column(name = "`api_url`")
    private String apiUrl;

    /**
     * 接口的登录账号（需要token）
     */
    @Column(name = "`api_username`")
    private String apiUsername;

    /**
     * 接口的登录密码（需要token）
     */
    @Column(name = "`api_password`")
    private String apiPassword;

    /**
     * 合作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workDate;

    /**
     * 是否启用
     */
    @Column(name = "`status`")
    private Boolean status;

    /**
     * 是否启用
     */
    @Column(name = "`sap_code`")
    private String sapCode;

    /**
     * 备注说明
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 库存地点过滤（HK、SZ、INDIA）
     */
    @Column(name = "`filter_address`")
    private String filterAddress;

    /**
     * 证书号白名单
     */
    @Column(name = "`white_zsbh_list`")
    private String whiteZsbhList;

    /**
     * 库存地，香港
     */
    @Column(name = "`location_in_hk`")
    private String locationInHk;

    /**
     * 库存地，深圳
     */
    @Column(name = "`location_in_sz`")
    private String locationInSz;

    /**
     * 默认上架1/下架0
     */
    @Column(name = "`enable_in_hk`")
    private Integer enableInHk;

    /**
     * 默认上架1/下架0
     */
    @Column(name = "`enable_in_sz`")
    private Integer enableInSz;

    /*********************************************/

    @Transient
    private String orderBy;

    @Transient
    private String token;

    @Transient
    private Map<String, String> blackMap;
}