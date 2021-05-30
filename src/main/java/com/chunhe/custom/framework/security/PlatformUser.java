package com.chunhe.custom.framework.security;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import com.chunhe.custom.framework.model.SysUser;

public class PlatformUser extends User {

    @Getter
    private final SysUser sysUser;

    public PlatformUser(SysUser sysUser, String[] roles, String[] permissions) {
        super(sysUser.getUsername(), sysUser.getPassword(), sysUser.getEnabled(), true, true, !sysUser.getLocked(), AuthorityUtils.createAuthorityList(ArrayUtils.addAll(roles, permissions)));
        this.sysUser = sysUser;
    }

}
