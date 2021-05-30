package com.chunhe.custom.pc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "dada_rel_style_parts")
public class DadaRelStyleParts {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * dada样式id
     */
    @Column(name = "`style_id`")
    private Long styleId;

    /**
     * dada组件id
     */
    @Column(name = "`parts_id`")
    private Long partsId;

}