package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.List;

/**
 * 系列维护,
 */
@Getter
@Setter
public class Series extends BaseEntity {
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
     * 是否启用展示，1展示，0隐藏
     */
    @Column(name = "`enabled`")
    private Boolean enabled;

    @Column(name = "`level`")
    private Integer level;

    /**
     * 工艺介绍文字
     */
    @Column(name = "`introduction_word`")
    private String introductionWord;

    /**
     * 工艺介绍视频
     */
    @Column(name = "`introduction_video`")
    private String introductionVideo;

    /**
     * 工艺介绍配图
     */
    @Column(name = "`introduction_imgs`")
    private String introductionImgs;

    /****************************************/

    @Transient
    private List imgsUrlList;
}