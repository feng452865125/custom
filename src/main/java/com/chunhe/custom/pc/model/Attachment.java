package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * 图集维护
 * white 2020年4月25日14:46:13
 */
@Getter
@Setter
public class Attachment extends BaseEntity {
    /**
     * 启用状态-启用
     */
    public static final Boolean ENABLED_TRUE = Boolean.TRUE;

    /**
     * * 启用状态-禁用
     */
    public static final Boolean ENABLED_FALSE = Boolean.FALSE;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`img_url`")
    private String imgUrl;

    /**
     * 是否启用
     */
    @Column(name = "`enabled`")
    private Boolean enabled;

    /**
     * 前端展示顺序
     */
    @Column(name = "`level`")
    private Integer level;

    /**
     * 描述
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 标题（产品列表中图片下展示的文字）
     */
    @Column(name = "`title`")
    private String title;

    /**
     * 内容（产品列表中图片下展示的文字）
     */
    @Column(name = "`content`")
    private String content;
}