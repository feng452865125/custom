package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * dada系列维护
 */
@Getter
@Setter
@Table(name = "dada_series")
public class DadaSeries extends BaseEntity {
    /**
     * 启用状态-启用
     */
    public static final Boolean ENABLED_TRUE = Boolean.TRUE;

    /**
     * * 启用状态-禁用
     */
    public static final Boolean ENABLED_FALSE = Boolean.FALSE;

    /**
     * 系列名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 描述
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 图片的路径
     */
    @Column(name = "`img_url`")
    private String imgUrl;

    /**
     * 是否启用展示
     */
    @Column(name = "`enabled`")
    private Boolean enabled;

    /**
     * 前端展示排序的顺位
     */
    @Column(name = "`level`")
    private Integer level;

}