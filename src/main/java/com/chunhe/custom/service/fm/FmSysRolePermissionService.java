package com.chunhe.custom.service.fm;

import com.chunhe.custom.mapper.SysRolePermissionMapper;
import com.chunhe.custom.mybatis.BaseService;
import com.chunhe.custom.entity.SysRolePermission;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色权限关联表 freemarker-服务层
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Service
public class FmSysRolePermissionService extends BaseService<SysRolePermission> {

    private Logger logger = LogManager.getLogger(getClass());


    @Autowired
    private FmSysPermissionService fmSysPermissionService;

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
        String permissions = sysRolePermission.getSysRolePermissionPermissions();
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
            String permissions = sysRolePermission.getSysRolePermissionPermissions();
            if (StringUtils.isBlank(permissions)) {
                continue;
            }
            result.addAll(Lists.newArrayList(StringUtils.split(permissions, ",")));
        }

        return result.toArray(new String[]{});
    }
    
}