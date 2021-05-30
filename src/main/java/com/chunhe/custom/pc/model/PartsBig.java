package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "parts_big")
public class PartsBig extends BaseEntity {

    public final static String XLS = "xls";
    public final static String XLSX = "xlsx";

    /**
     * 公司
     */
    @Column(name = "`company`")
    private String company;

    /**
     * 款号
     */
    @Column(name = "`zs_kh`")
    private String zsKh;

    /**
     * 质量*1000
     */
    @Column(name = "`zs_zl`")
    private Integer zsZl;

    /**
     * 证书号
     */
    @Column(name = "`zs_zsh`")
    private String zsZsh;

    /**
     * 颜色
     */
    @Column(name = "`zs_ys`")
    private String zsYs;

    /**
     * 净度
     */
    @Column(name = "`zs_jd`")
    private String zsJd;

    /**
     * 切工
     */
    @Column(name = "`zs_qg`")
    private String zsQg;

    /**
     * 抛光
     */
    @Column(name = "`zs_pg`")
    private String zsPg;

    /**
     * 对称
     */
    @Column(name = "`zs_dc`")
    private String zsDc;

    /**
     * 荧光
     */
    @Column(name = "`zs_yg`")
    private String zsYg;

    /**
     * 扣点
     */
    @Column(name = "`zs_kd`")
    private String zsKd;

    /**
     * 国际报价*100
     */
    @Column(name = "`zs_gjbj`")
    private Integer zsGjbj;

    /**
     * 分数均价*100
     */
    @Column(name = "`zs_ctjj`")
    private Integer zsCtjj;

    /**
     * 美金*100
     */
    @Column(name = "`zs_mj`")
    private Integer zsMj;

    /**
     * 人民币*100
     */
    @Column(name = "`zs_rmb`")
    private Integer zsRmb;

    /**
     * 销售价
     */
    @Column(name = "`zs_price`")
    private Integer zsPrice;

    /**
     * 未锁定（JP系统）
     */
    @Column(name = "`lock_status`")
    private Integer lockStatus;

    /**
     * 是否启用
     */
    @Column(name = "`enabled`")
    private Boolean enabled;
}