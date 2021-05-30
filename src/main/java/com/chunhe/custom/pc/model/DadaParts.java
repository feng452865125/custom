package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * dada系列的组件管理
 */
@Getter
@Setter
@Table(name = "dada_parts")
public class DadaParts extends BaseEntity {

    /**
     * 编码
     */
    @Column(name = "`code`")
    private String code;

    @Column(name = "`name`")
    private String name;

    /**
     * 佩戴类型
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * dada底下的小系列
     */
    @Column(name = "`series`")
    private String series;

    @Column(name = "`price`")
    private Integer price;

    /**
     * 寓意
     */
    @Column(name = "`ex_yy`")
    private String exYy;

    /**
     * 情感含义
     */
    @Column(name = "`ex_qghy`")
    private String exQghy;

    /**
     * 金属颜色
     */
    @Column(name = "`ex_jsys`")
    private String exJsys;

    /**
     * 手寸
     */
    @Column(name = "`ex_sc`")
    private String exSc;

    @Column(name = "`imgs`")
    private String imgs;

    @Column(name = "`img_url`")
    private String imgUrl;

    @Column(name = "`video`")
    private String video;

    /**
     * 描述
     */
    @Column(name = "`remark`")
    private String remark;

    @Column(name = "`note`")
    private String note;

    /**********************************/

    @Transient
    private String orderBy;

    @Transient
    private String typeName;

    /**
     * 款式绑定组件用到，已绑enabled=true
     */
    @Transient
    private Long styleId;

    @Transient
    private Long partsId;

    @Transient
    private Boolean enabled;
}