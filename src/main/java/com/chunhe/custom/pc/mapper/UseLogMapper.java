package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.UseLog;

import java.util.List;

public interface UseLogMapper extends MyMapper<UseLog> {

    List<UseLog> findUseLogList(UseLog useLog);

    UseLog getUseLog(UseLog useLog);
}