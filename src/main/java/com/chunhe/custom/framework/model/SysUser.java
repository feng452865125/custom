package com.chunhe.custom.framework.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@Table(name = "sys_user")
@Entity
public class SysUser extends BaseEntity {
    /**
     * 店铺状态
     */
    public static String USER_STATUS_NAME = "userStatusName";
    public static String USER_STATUS_OPEN = "1";
    public static String USER_STATUS_CLOSE = "0";
    /**
     * 锁定状态-锁定
     */
    public static final Boolean LOCKED_TRUE = Boolean.TRUE;

    /**
     * * 锁定状态-未锁定
     */
    public static final Boolean LOCKED_FALSE = Boolean.FALSE;

    /**
     * 启用状态-启用
     */
    public static final Boolean ENABLED_TRUE = Boolean.TRUE;

    /**
     * * 启用状态-禁用
     */
    public static final Boolean ENABLED_FALSE = Boolean.FALSE;

    /**
     * 是否系统内置-是
     */
    public static final Boolean IS_SYSTEM_TRUE = Boolean.TRUE;

    /**
     * * 是否系统内置-否
     */
    public static final Boolean IS_SYSTEM_FALSE = Boolean.FALSE;

    private String username;

    private String password;

    private String name;

    /**
     * 是否系统内置（用于管理平台新增用户账号）
     */
    @Column(name = "`is_system`")
    private Boolean isSystem;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    private Integer loginFailureCount;

    private String loginIp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lockedDate;

    private Boolean locked;

    private Boolean enabled;

    private String address;

    private String pos;

    private Integer status;

    /**
     * 经度
     */
    @Column(name = "`longitude`")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @Column(name = "`latitude`")
    private BigDecimal latitude;

    /**
     * 设备uuid
     */
    @Column(name = "`uuid`")
    private String uuid;

    /**
     * 门店系数
     */
    @Column(name = "`price_multiple`")
    private String priceMultiple;

    /***************************************/

    @Transient
    private String statusName;

    @Transient
    private Integer nearbyDistance;

    @Transient
    private String token;

    @Transient
    private String newPassword;
}
