package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 操作日志
 */
@Getter
@Setter
@Table(name = "action_log")
public class ActionLog extends BaseEntity {

    /**
     * 操作用户
     */
    @Column(name = "`log_username`")
    private String logUsername;

    /**
     * 操作request
     */
    @Column(name = "`log_request_url`")
    private String logRequestUrl;

    /**
     * 操作内容
     */
    @Column(name = "`log_content`")
    private String logContent;

}