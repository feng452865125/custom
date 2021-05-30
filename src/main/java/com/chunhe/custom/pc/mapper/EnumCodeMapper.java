package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.EnumCode;

import java.util.List;

public interface EnumCodeMapper extends MyMapper<EnumCode> {

    List<EnumCode> findEnumCodeList(EnumCode enumCode);

    EnumCode getEnumCode(EnumCode enumCode);
}