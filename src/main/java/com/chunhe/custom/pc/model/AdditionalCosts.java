package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 附加费用维护
 */
@Getter
@Setter
@Table(name = "additional_costs")
public class AdditionalCosts extends BaseEntity {

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
}