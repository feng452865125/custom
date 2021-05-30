package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.JewelryType;

import java.util.List;

public interface JewelryTypeMapper extends MyMapper<JewelryType> {

    List<JewelryType> findJewelryTypeList(JewelryType jewelryType);

    JewelryType getJewelryType(JewelryType jewelryType);
}