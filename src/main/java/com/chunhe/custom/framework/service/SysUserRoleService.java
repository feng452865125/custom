package com.chunhe.custom.framework.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import com.chunhe.custom.framework.mapper.SysUserRoleMapper;
import com.chunhe.custom.framework.model.SysUserRole;
import com.chunhe.custom.framework.utils.ConvertUtil;

@Service
public class SysUserRoleService extends MyService<SysUserRole> {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 通过用户编号获取用户对应角色编号数组
     */
    public long[] selectRoleIdsByUserId(long userId) {
        SysUserRole sysUserRole = selectByKey(userId);
        if (sysUserRole == null) {
            return new long[]{};
        }
        String roles = sysUserRole.getRoles();
        if (StringUtils.isBlank(roles)) {
            return new long[]{};
        }

        String[] roleArray = StringUtils.split(roles, ",");

        return (long[]) ConvertUtil.convert(roleArray, long[].class);
    }

    /**
     * 用户是否关联了角色
     */
    public boolean hasRoles(String roleId) {
        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria =   example.createCriteria();
        criteria.andLike("roles", roleId);
        return getMapper().selectCountByExample(example) > 0;
    }
}
