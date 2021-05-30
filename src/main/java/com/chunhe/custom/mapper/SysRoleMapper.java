package com.chunhe.custom.mapper;

import com.chunhe.custom.entity.SysRole;
import com.chunhe.custom.mybatis.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-14
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectListSysRole();

    List<SysRole> findSysRoleList(SysRole sysRole);

    SysRole getSysRole(SysRole sysRole);

}
