package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.RelAdvertisementStyle;
import com.chunhe.custom.pc.model.Style;

import java.util.List;

public interface RelAdvertisementStyleMapper extends MyMapper<RelAdvertisementStyle> {

    List<Style> findRelAdvertisementStyle(Style style);

    RelAdvertisementStyle getRelAdvertisementStyle(RelAdvertisementStyle relAdvertisementStyle);
}