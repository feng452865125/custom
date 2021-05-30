package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.CustomBlack;

import java.util.List;

public interface CustomBlackMapper extends MyMapper<CustomBlack> {

    List<CustomBlack> findCustomBlackList(CustomBlack customBlack);

    CustomBlack getCustomBlack(CustomBlack customBlack);
}