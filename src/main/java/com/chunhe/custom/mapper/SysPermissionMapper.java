package com.chunhe.custom.mapper;

import com.chunhe.custom.entity.SysPermission;
import com.chunhe.custom.mybatis.BaseMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    List<SysPermission> selectBycodes(@Param("permissionCodes") String[] permissionCodes);
}
