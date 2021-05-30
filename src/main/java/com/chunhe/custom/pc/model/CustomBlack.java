package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Setter
@Getter
@Table(name = "custom_black")
public class CustomBlack extends BaseEntity {

    public static String CUSTOM_BLACK_CACHE_KEY = "blackList";

    /**
     * 证书号
     */
    @Column(name = "`black_zsh`")
    private String blackZsh;

    /**
     * 类型，0单个设置，1文件导入
     */
    @Column(name = "`black_type`")
    private Integer blackType;


    /**
     * 状态，是否禁用（黑名单禁用，表示可用）
     */
    @Column(name = "`black_enable`")
    private Boolean blackEnable;

    /**
     * 创建人
     */
    @Column(name = "`black_create_user`")
    private String blackCreateUser;

    /**
     * 原因/备注
     */
    @Column(name = "`black_reason`")
    private String blackReason;


    /**************************************************/

}