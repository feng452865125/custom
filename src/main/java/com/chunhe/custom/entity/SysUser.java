package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.Data;
import javax.persistence.Table;

/**
 * <p>
 * 账号
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Table(name = "sys_user")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登录账号
     */
    private String sysUserUsername;

    /**
     * 登录密码
     */
    private String sysUserPassword;

    /**
     * 禁用/启用
     */
    private Boolean sysUserIsEnable;

    /**
     * 解锁/锁定
     */
    private Boolean sysUserIsLocked;

    /**
     * 是否最高权限
     */
    private Boolean sysUserIsRoot;

    /**
     * 用户名
     */
    private String sysUserName;

    /**
     * 用户号码
     */
    private String sysUserMobile;


}