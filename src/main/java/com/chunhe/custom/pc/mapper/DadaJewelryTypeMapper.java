package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.DadaJewelryType;

import java.util.List;

public interface DadaJewelryTypeMapper extends MyMapper<DadaJewelryType> {

    List<DadaJewelryType> findDadaJewelryTypeList(DadaJewelryType dadaJewelryType);

    DadaJewelryType getDadaJewelryType(DadaJewelryType dadaJewelryType);
}