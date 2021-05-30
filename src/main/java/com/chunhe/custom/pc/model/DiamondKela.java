package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 钻石（克拉展）维护
 */
@Getter
@Setter
@Table(name = "diamond_kela")
public class DiamondKela extends BaseEntity {
    /**
     * 钻石类型 1、未销售，2、已销售，3、供应链
     */
    public static final String DIAMOND_KELA_STATUS = "diamondKelaStatus";
    /**
     * 钻石枚举，切工，抛光，对称，荧光
     */
    public static final String DIAMOND_KELA_QG = "zsQg";
    public static final String DIAMOND_KELA_PG = "zsPg";
    public static final String DIAMOND_KELA_DC = "zsDc";
    public static final String DIAMOND_KELA_YG = "zsYg";
    /**
     * 钻石类型 1、未销售，2、已销售，3、供应链
     */
    public static final int DIAMOND_KELA_STATUS_HAS = 1;
    public static final int DIAMOND_KELA_STATUS_SOLD = 2;

    /**
     * 编码
     */
    @Column(name = "`code`")
    private String code;

    @Column(name = "`name`")
    private String name;

    /**
     * 1现货，2已售出
     */
    @Column(name = "`status`")
    private Integer status;

    @Column(name = "`price`")
    private Integer price;

    @Column(name = "`img_url`")
    private String imgUrl;

    /**
     * 描述
     */
    @Column(name = "`remark`")
    private String remark;

    @Column(name = "`note`")
    private String note;

    /**
     * 钻石形状
     */
    @Column(name = "`ex_zs_xz`")
    private String exZsXz;

    /**
     * 钻石证书
     */
    @Column(name = "`ex_zs_zs`")
    private String exZsZs;

    /**
     * 钻石重量
     */
    @Column(name = "`ex_zs_zl`")
    private Integer exZsZl;

    /**
     * 钻石颜色
     */
    @Column(name = "`ex_zs_ys`")
    private String exZsYs;

    /**
     * 钻石净度
     */
    @Column(name = "`ex_zs_jd`")
    private String exZsJd;

    /**
     * 钻石切工
     */
    @Column(name = "`ex_zs_qg`")
    private String exZsQg;

    /**
     * 钻石抛光
     */
    @Column(name = "`ex_zs_pg`")
    private String exZsPg;

    /**
     * 钻石对称
     */
    @Column(name = "`ex_zs_dc`")
    private String exZsDc;

    /**
     * 钻石荧光
     */
    @Column(name = "`ex_zs_yg`")
    private String exZsYg;

    /**
     * 前端展示排序的顺位
     */
    @Column(name = "`level`")
    private Integer level;

    /***********************************/

    @Transient
    private String statusName;

    /**
     * 查组件（钻石）上下限
     */
    @Transient
    private int exZsZlMin;

    @Transient
    private int exZsZlMax;

    @Transient
    private int priceMin;

    @Transient
    private int priceMax;

    /**
     * 查询可以适合该样式id的钻石
     */
    @Transient
    private Long styleId;

    /**
     * 钻石颜色，钻石净度（多选，in)
     */
    @Transient
    private String exZsYsArr;

    @Transient
    private String exZsJdArr;

    /**
     * 前端传进排序类型，后端匹配具体排序
     */
    @Transient
    private Integer orderByType;

    @Transient
    private String orderBy;

    @Transient
    private Boolean hasSku;

    /**
     * 需求变更新逻辑，先选属性，再选钻石
     * 同款样式，手寸全部相同，则此处不做参数
     */
    @Transient
    private String productExSc;

    @Transient
    private String productJbkd;

    @Transient
    private String productJscz;

    @Transient
    private String productJsys;

    @Transient
    private String productKtCode;

    @Transient
    private Integer productPrice;
}