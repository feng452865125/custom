package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 * 钻石基价表
 */
@Getter
@Setter
@Table(name = "base_price")
public class BasePrice extends BaseEntity {
    /**
     * 展示开关 0、关闭，1、开启
     */
    public static final String BASE_PRICE_STATUS = "basePriceStatus";
    public static final Boolean ENABLED_TRUE = Boolean.TRUE;
    public static final Boolean ENABLED_FALSE = Boolean.FALSE;
    /**
     * 颜色，净度，切工，抛光，对称，荧光，证书
     */
    public static final String BASE_PRICE_YS = "basePriceYs";
    public static final String BASE_PRICE_JD = "basePriceJd";
    public static final String BASE_PRICE_QG = "basePriceQg";
    public static final String BASE_PRICE_PG = "basePricePg";
    public static final String BASE_PRICE_DC = "basePriceDc";
    public static final String BASE_PRICE_YG = "basePriceYg";
    public static final String BASE_PRICE_ZS = "basePriceZs";
    /**
     * 基价钻石排序
     */
    public static final String BASE_ORDER_BY = "baseOrderBy";

    public BasePrice(){

    }

    public BasePrice(String ys, String jd, String qg, Integer zl){
        this.ys = ys;
        this.jd = jd;
        this.qg = qg;
        this.zl = zl;
    }
    /**
     * 4C标准名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 克拉最小值（*1000）
     */
    @Column(name = "`kl_min`")
    private Integer klMin;

    /**
     * 克拉最大值（*1000）
     */
    @Column(name = "`kl_max`")
    private Integer klMax;

    /**
     * 钻石颜色
     */
    @Column(name = "`ys`")
    private String ys;

    /**
     * 钻石净度
     */
    @Column(name = "`jd`")
    private String jd;

    /**
     * 钻石切工
     */
    @Column(name = "`qg`")
    private String qg;

    /**
     * 钻石抛光
     */
    @Column(name = "`pg`")
    private String pg;

    /**
     * 钻石对称
     */
    @Column(name = "`dc`")
    private String dc;

    /**
     * 钻石荧光
     */
    @Column(name = "`yg`")
    private String yg;

    /**
     * 钻石证书
     */
    @Column(name = "`zs`")
    private String zs;

    /**
     * 价格，*100
     */
    @Column(name = "`price`")
    private Integer price;

    /**
     * 备注说明
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 是否启用
     */
    @Column(name = "`status`")
    private Boolean status;

    /*********************************************/

    @Transient
    private String orderBy;

    @Transient
    private Boolean hasSku;

    @Transient
    private Boolean hasDiamond;

    @Transient
    private String productCode;

    @Transient
    private String productKtCode;

    @Transient
    private Integer productPrice;

    @Transient
    private Integer diamondId;

    @Transient
    private Integer productId;

    @Transient
    private Integer zl;

    @Transient
    private Long styleId;

    @Transient
    private String serice;

    @Transient
    private  String moral;

    @Transient
    private String jbKd;

    @Transient
    private String zsYs;

    @Transient
    private String zsJd;

    @Transient
    private  Parts parts;

    @Transient
    private Product product;

    @Transient
    private String zsQg;

    @Transient
    private String zsDc;

    @Transient
    private String zsPg;

    /**
     * 区分石头来源（parts表和parts_big表）
     */
    @Transient
    private String stoneType;

    @Transient
    private String stoneZsh;

    @Transient
    private String stoneLocation;

    @Transient
    private String stoneCompany;

    @Transient
    private String stoneLocationDay;

    @Transient
    private Boolean stoneRecommend;

    //上柜价（基价*石重*系数2.3+戒托价）
    @Transient
    private Integer sgPrice;
    //结算价：（基价*石重*系数2.3+戒托价）*0.45
    @Transient
    private Integer jsPrice;
}