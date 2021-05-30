package com.chunhe.custom.framework.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import org.springframework.data.repository.query.Param;
import com.chunhe.custom.framework.model.SysPermission;

import java.util.List;

public interface SysPermissionMapper extends MyMapper<SysPermission> {

    List<SysPermission> selectBycodes(@Param("permissionCodes") String[] permissionCodes);
}
