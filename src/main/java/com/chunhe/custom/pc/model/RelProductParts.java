package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "rel_product_parts")
public class RelProductParts extends BaseEntity {

    /**
     * 成品编号
     */
    @Column(name = "`product_code`")
    private String productCode;

    /**
     * 组件编号
     */
    @Column(name = "`parts_code`")
    private String partsCode;

}