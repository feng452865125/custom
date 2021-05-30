package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * 组信息维护
 */
@Getter
@Setter
public class GroupInfo extends BaseEntity {
    /**
     * 组的类型，1花头，2戒臂
     */
    public static final String GROUP_INFO_TYPE = "groupInfoType";
    public static final int GROUP_INFO_TYPE_FLOWER_HEAD = 1;
    public static final int GROUP_INFO_TYPE_RING_ARM = 2;
    /**
     * 费用编码
     */
    @Column(name = "`code`")
    private String code;

    /**
     * 费用名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 描述
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 类别，1、花头，2、戒臂
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 包含样式
     */
    @Column(name = "`ys`")
    private String ys;

    /******************************************************************/

    @Transient
    private String typeName;
}