package com.chunhe.custom.framework.security;

import com.chunhe.custom.framework.service.SysPermissionService;
import com.chunhe.custom.framework.service.SysRolePermissionService;
import com.chunhe.custom.framework.service.SysUserRoleService;
import com.chunhe.custom.framework.validator.SystemConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.chunhe.custom.framework.model.SysPermission;
import com.chunhe.custom.framework.model.SysRole;
import com.chunhe.custom.framework.service.SysRoleService;
import com.chunhe.custom.framework.model.SysUser;
import com.chunhe.custom.framework.service.SysUserService;

import java.util.List;

@Component
public class SystemUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SystemUserDetailsService.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("未找到用户");
        }

        List<SysRole> roleArray;
        String[] permissions;
        String[] roles;

        if (SystemConstant.USER_ADMIN.equals(sysUser.getUsername())) {
            roleArray = sysRoleService.sysRoleAllList();
            roles = new String[roleArray.size()];
            for (int i = 0; i < roleArray.size(); i++) {
                SysRole sysRole = roleArray.get(i);
                roles[i] = sysRole.getName();
            }
            List<SysPermission> permissionArray = sysPermissionService.sysPermissionAllList();
            permissions = new String[permissionArray.size() + 1];
            for (int i = 0; i < permissionArray.size(); i++) {
                SysPermission sysPermission = permissionArray.get(i);
                permissions[i] = sysPermission.getCode();
            }
            permissions[permissionArray.size()] = "admin";
        } else {
            long[] roleIds = sysUserRoleService.selectRoleIdsByUserId(sysUser.getId());
            roleArray = sysRoleService.selectByKeys(roleIds);
            roles = new String[roleArray.size()];
            for (int i = 0; i < roleArray.size(); i++) {
                SysRole sysRole = roleArray.get(i);
                roles[i] = sysRole.getName();
            }
            permissions = sysRolePermissionService.selectByRoleIds(roleIds);
        }
        return new PlatformUser(sysUser, roles, permissions);
    }
}
