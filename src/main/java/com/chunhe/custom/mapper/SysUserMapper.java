package com.chunhe.custom.mapper;

import com.chunhe.custom.entity.SysUser;
import com.chunhe.custom.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 账号 Mapper 接口
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-14
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser findSysUserByUsername(@Param("username") String username);

    List<SysUser> findSysUserList(SysUser sysUser);

    SysUser getSysUser(SysUser sysUser);

    List<SysUser> findSysUserListByDeviceCode(SysUser sysUser);

}
