package com.chunhe.custom.mapper;

import com.chunhe.custom.entity.SysUser;
import com.chunhe.custom.mybatis.BaseMapper;

import java.util.List;

/**
 * <p>
 * 账号 Mapper 接口
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getSysUser(SysUser sysUser);

    List<SysUser> findSysUserList();

    List<SysUser> findSysUserNearbyList(SysUser sysUser);

    public int setNewPassword(SysUser sysUser);

    SysUser selectByUsername(SysUser sysUser);
}
