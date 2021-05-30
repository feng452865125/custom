package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.SysUserDetail;

import java.util.List;

public interface SysUserDetailMapper extends MyMapper<SysUserDetail> {

    List<SysUserDetail> findSysUserDetailList(SysUserDetail sysUserDetail);

}