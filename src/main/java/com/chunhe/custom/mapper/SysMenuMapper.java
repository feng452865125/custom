package com.chunhe.custom.mapper;

import com.chunhe.custom.entity.SysMenu;
import com.chunhe.custom.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单控制 Mapper 接口
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-14
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findSysMenuList(@Param("roleId") Integer roleId,
                                  @Param("filterType") Integer filterType);

}
