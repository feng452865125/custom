package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * dada类别维护
 */
@Getter
@Setter
@Table(name = "dada_jewelry_type")
public class DadaJewelryType extends BaseEntity {

    /**
     * 类别编码
     */
    @Column(name = "`code`")
    private String code;

    /**
     * 类别名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 描述
     */
    @Column(name = "`remark`")
    private String remark;
}