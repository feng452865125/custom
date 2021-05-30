package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 账号
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-14
 */

@Table(name = "sys_user")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends BaseEntity implements UserDetails {

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
     * 锁定/解锁
     */
    private Boolean sysUserIsLocked;

    /**
     * 是否最高权限
     */
    private Boolean sysUserIsRoot;

    /**
     * 部门id，person_seciton表
     */
    private Integer sysUserSectionId;

    /**
     * 用户名
     */
    private String sysUserName;

    /**
     * 用户号码
     */
    private String sysUserMobile;

    /******************************************************************/

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysUserPassword;
    }

    @Override
    public String getUsername() {
        return sysUserUsername;
    }

    /**
     * 用户账号是否过期
     * true: 未过期
     * false: 已过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户账号是否被锁定
     * true: 未锁定
     * false: 锁定
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return sysUserIsLocked;
    }

    /**
     * 用户账号凭证(密码)是否过期
     * 简单的说就是可能会因为修改了密码导致凭证过期这样的场景
     * true: 过期
     * false: 无效
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户账号是否被启用
     * true: 启用
     * false: 未启用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return sysUserIsEnable;
    }

    /******************************************************************/

    @Transient
    private String token;

    @Transient
    private String newPassword;

    @Transient
    private List menuList = new ArrayList<>();

}