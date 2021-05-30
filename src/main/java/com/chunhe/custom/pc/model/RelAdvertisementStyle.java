package com.chunhe.custom.pc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 广告栏管理
 */
@Getter
@Setter
@Table(name = "rel_advertisement_style")
public class RelAdvertisementStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 广告栏id
     */
    @Column(name = "`advertisement_id`")
    private Long advertisementId;

    /**
     * 样式id
     */
    @Column(name = "`style_id`")
    private Long styleId;

}