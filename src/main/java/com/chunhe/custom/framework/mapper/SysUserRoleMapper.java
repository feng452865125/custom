package com.chunhe.custom.framework.mapper;


import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.framework.model.SysUserRole;

public interface SysUserRoleMapper extends MyMapper<SysUserRole> {

    public SysUserRole selectRoleIdsByUserId(Long userId);
}
