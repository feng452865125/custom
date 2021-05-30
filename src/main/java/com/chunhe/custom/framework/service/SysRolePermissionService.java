package com.chunhe.custom.framework.service;


import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chunhe.custom.framework.mapper.SysRolePermissionMapper;
import com.chunhe.custom.framework.model.SysRolePermission;

import java.util.ArrayList;
import java.util.List;


@Service
public class SysRolePermissionService extends MyService<SysRolePermission> {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    /**
     * 根据角色编号获取角色权限
     */
    public String[] selectByRoleId(long roleId) {
        SysRolePermission sysRolePermission = selectByKey(roleId);
        if(null == sysRolePermission){
            return new String[]{};
        }
        String permissions = sysRolePermission.getPermissions();
        if (StringUtils.isBlank(permissions)) {
            return new String[]{};
        }
        return StringUtils.split(permissions, ",");
    }

    /**
     * 根据角色编号数组获取所有权限
     */
    public String[] selectByRoleIds(long[] roleIds) {
        List<SysRolePermission> sysRolePermissions = selectByKeys(roleIds);

        List<String> result = new ArrayList<>();

        for(SysRolePermission sysRolePermission : sysRolePermissions){
            String permissions = sysRolePermission.getPermissions();
            if (StringUtils.isBlank(permissions)) {
                continue;
            }
            result.addAll(Lists.newArrayList(StringUtils.split(permissions, ",")));
        }

        return result.toArray(new String[]{});
    }

}
