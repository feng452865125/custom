package com.chunhe.custom.pc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 签名
 */
@Getter
@Setter
@Table(name = "wechat_share")
public class WechatShare {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`timestamp`")
    private String timestamp;

    @Column(name = "`accessToken`")
    private String accesstoken;

    @Column(name = "`ticket`")
    private String ticket;

    @Column(name = "`noncestr`")
    private String noncestr;

    @Column(name = "`signature`")
    private String signature;

    @Column(name = "`create_date`")
    private Date createDate;

    @Column(name = "`last_date`")
    private Date lastDate;

    @Column(name = "`url`")
    private String url;

    @Column(name = "`appId`")
    private String appId;
}