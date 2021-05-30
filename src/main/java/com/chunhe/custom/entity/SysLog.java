package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-14
 */

@Table(name = "sys_log")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class SysLog extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 用户id，user表
     */
    private Integer sysLogUserId;

    /**
     * 各自触发的对象id
     */
    private Integer sysLogRelId;

    /**
     * 类型
     */
    private String sysLogType;

    /**
     * 描述
     */
    private String sysLogDescribe;

    /**
     * 操作ip
     */
    private String sysLogIp;

    /**
     * request
     */
    private String sysLogRequest;

    /**
     * response
     */
    private String sysLogResponse;

    /**
     * 部分路径
     */
    private String sysLogUrl;


}