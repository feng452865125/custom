package com.chunhe.custom.framework.mapper;

import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.mybatis.MyMapper;

import java.util.List;

public interface SysConfigMapper extends MyMapper<SysConfig> {

    SysConfig getSysConfig(SysConfig sysConfig);

    List<SysConfig> findSysConfigList(SysConfig sysConfig);
}