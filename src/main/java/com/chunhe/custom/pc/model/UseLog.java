package com.chunhe.custom.pc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 报表查询
 */
@Getter
@Setter
@Table(name = "use_log")
public class UseLog extends BaseEntity {

    public final static String ACTION_TYPE = "actionType";

    public final static String SOURCE_TYPE = "sourceType";

    /**
     * 店铺id
     */
    @Column(name = "`user_id`")
    private Integer userId;

    /**
     * 样式id
     */
    @Column(name = "`style_id`")
    private Long styleId;

    /**
     * 类型，1、转发 2、试戴 3、定制
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 来源类型 1、app 2、h5
     */
    @Column(name = "`source_type`")
    private Integer sourceType;

    /**
     * 描述备注
     */
    @Column(name = "`remark`")
    private String remark;

    /**************************************************/

    @Transient
    private String orderBy;

    /**
     * 搜索条件
     */
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @Transient
    private String search;

    /**
     * list展示
     */
    @Transient
    private String styleName;

    @Transient
    private String styleCode;

    /**
     * int转换
     */
    @Transient
    private String typeName;

    @Transient
    private String sourceName;

    @Transient
    private String userName;

    @Transient
    private Style style = new Style();
}