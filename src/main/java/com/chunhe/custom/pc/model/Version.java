package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * 版本管理
 */
@Getter
@Setter
public class Version extends BaseEntity {

    @Column(name = "`name`")
    private String name;

    /**
     * 版本地址
     */
    @Column(name = "`url`")
    private String url;

    /**
     * 版本号/名
     */
    @Column(name = "`version`")
    private String version;

    /**
     * 版本详情
     */
    @Column(name = "`content`")
    private String content;

    /**
     * 更新文件大小
     */
    @Column(name = "`size`")
    private String size;


}