package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * dada样式维护
 */
@Getter
@Setter
@Table(name = "dada_style")
public class DadaStyle extends BaseEntity {
    /**
     * dada组件数量
     */
    public static final int countInit = 0;
    /**
     * 款式总价格
     */
    public static final int priceInit = 0;
    /**
     * 编号
     */
    @Column(name = "`code`")
    private String code;

    @Column(name = "`name`")
    private String name;

    /**
     * 1项链，2戒指（jewelry_type表）
     */
    @Column(name = "`type`")
    private Integer type;

    @Column(name = "`price`")
    private Integer price;

    /**
     * 详情轮播图文件路径
     */
    @Column(name = "`imgs_url`")
    private String imgsUrl;

    /**
     * 缩略图文件路径
     */
    @Column(name = "`img_url`")
    private String imgUrl;

    /**
     * 视频文件路径
     */
    @Column(name = "`video_url`")
    private String videoUrl;

    /**
     * 描述
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 寓意
     */
    @Column(name = "`moral`")
    private String moral;

    @Column(name = "`story`")
    private String story;

    /**
     * 系列id
     */
    @Column(name = "`series`")
    private String series;

    /**
     * 情感含义
     */
    @Column(name = "`meaning`")
    private String meaning;

    /**
     * 试戴（30分）
     */
    @Column(name = "`wear_url`")
    private String wearUrl;

    @Column(name = "`count`")
    private Integer count;

    /**********************************/

    /**
     * dada_rel_style_parts
     */
    @Transient
    private List<DadaParts> dadaPartsList;

    @Transient
    private String orderBy;

    @Transient
    private String typeName;

    @Transient
    private List imgsUrlList;

    /**
     * 定制中，查询所有产品
     */
    @Transient
    private String search;

    @Transient
    private Integer searchLength;

}