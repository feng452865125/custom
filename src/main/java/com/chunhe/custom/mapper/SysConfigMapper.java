package com.chunhe.custom.mapper;

import com.chunhe.custom.entity.SysConfig;
import com.chunhe.custom.mybatis.BaseMapper;

import java.util.List;

/**
 * <p>
 * 常用系统配置 Mapper 接口
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-14
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    List<SysConfig> findSysConfigList(SysConfig sysConfig);

    SysConfig getSysConfig(SysConfig sysConfig);

}
