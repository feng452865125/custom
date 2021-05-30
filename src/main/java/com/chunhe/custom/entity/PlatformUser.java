package com.chunhe.custom.entity;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class PlatformUser extends User {

    @Getter
    private final SysUser sysUser;

    public PlatformUser(SysUser sysUser, String[] roles, String[] permissions) {
        super(sysUser.getSysUserUsername(), sysUser.getSysUserPassword(), sysUser.getSysUserIsEnable(), true, true, !sysUser.getSysUserIsLocked(), AuthorityUtils.createAuthorityList(ArrayUtils.addAll(roles, permissions)));
        this.sysUser = sysUser;
    }

}
