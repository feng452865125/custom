package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "rel_style_printing")
public class RelStylePrinting extends BaseEntity {

    /**
     * 样式id
     */
    @Column(name = "`style_id`")
    private Integer styleId;

    /**
     * 印花id
     */
    @Column(name = "`printing_id`")
    private Integer printingId;
}