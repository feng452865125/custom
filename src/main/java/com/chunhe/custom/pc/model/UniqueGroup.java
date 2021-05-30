package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * unique类别维护
 */
@Getter
@Setter
@Table(name = "unique_group")
public class UniqueGroup extends BaseEntity {
    /**
     * 启用状态-启用
     */
    public static final Boolean ENABLED_TRUE = Boolean.TRUE;

    /**
     * * 启用状态-禁用
     */
    public static final Boolean ENABLED_FALSE = Boolean.FALSE;

    /**
     * 类别名字
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 路径图
     */
    @Column(name = "`url`")
    private String url;

    /**
     * 包含花头
     */
    @Column(name = "`ht`")
    private String ht;

    /**
     * 包含戒臂
     */
    @Column(name = "`jb`")
    private String jb;

    /**
     * 其他备注或描述
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 是否启用展示
     */
    @Column(name = "`enabled`")
    private Boolean enabled;

    /**
     * 默认0调用styleList, 状态1调用picList
     */
    @Column(name = "`type`")
    private Integer type;
}