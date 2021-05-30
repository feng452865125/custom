package com.chunhe.custom.framework.mapper;


import com.chunhe.custom.framework.model.SysUser;
import com.chunhe.custom.framework.mybatis.MyMapper;

import java.util.List;

public interface SysUserMapper extends MyMapper<SysUser> {

    SysUser getSysUser(SysUser sysUser);

    List<SysUser> findSysUserList();

    List<SysUser> findSysUserNearbyList(SysUser sysUser);

    public int setNewPassword(SysUser sysUser);

    SysUser selectByUsername(SysUser sysUser);
}
