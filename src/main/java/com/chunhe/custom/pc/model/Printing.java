package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * 印花维护
 */
@Getter
@Setter
public class Printing extends BaseEntity {

    /**
     * 印花编码
     */
    @Column(name = "`code`")
    private String code;

    /**
     * 印花名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 印花图片地址
     */
    @Column(name = "`img_url`")
    private String imgUrl;

    /**
     * 描述
     */
    @Column(name = "`remark`")
    private String remark;
}