package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.List;

/**
 * 广告栏维护
 */
@Getter
@Setter
public class Advertisement extends BaseEntity {
    /**
     * 启用状态-启用
     */
    public static final Boolean ENABLED_TRUE = Boolean.TRUE;

    /**
     * * 启用状态-禁用
     */
    public static final Boolean ENABLED_FALSE = Boolean.FALSE;
    /**
     * 名称
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
     * 跳转的标题展示区的图的描述
     */
    @Column(name = "`jump_title_remark`")
    private String jumpTitleRemark;

    /**
     * 跳转后的标题展示区的图片的路径
     */
    @Column(name = "`jump_title_img_url`")
    private String jumpTitleImgUrl;

    /**
     * 跳转的图的内容展示区的描述
     */
    @Column(name = "`jump_content_remark`")
    private String jumpContentRemark;

    /**
     * 跳转后的内容展示区的图片的路径
     */
    @Column(name = "`jump_content_img_url`")
    private String jumpContentImgUrl;

    /**
     * 跳转的图的视频展示区的描述
     */
    @Column(name = "`jump_video_remark`")
    private String jumpVideoRemark;

    /**
     * 跳转后的视频展示区的图片的路径
     */
    @Column(name = "`jump_video_url`")
    private String jumpVideoUrl;

    /**
     * 是否启用展示
     */
    @Column(name = "`enabled`")
    private Boolean enabled;

    /***************************************************/

    @Transient
    private List<Style> styleList;

    @Transient
    private Long styleId;
}