package com.chunhe.custom.mapper;

import com.chunhe.custom.entity.SysAccess;
import com.chunhe.custom.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 权限控制 Mapper 接口
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-14
 */
public interface SysAccessMapper extends BaseMapper<SysAccess> {

    int deleteSysAccessByRoleId(@Param("roleId") Integer roleId);

}
