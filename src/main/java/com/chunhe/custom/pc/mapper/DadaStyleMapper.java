package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.DadaStyle;

import java.util.List;

public interface DadaStyleMapper extends MyMapper<DadaStyle> {

    List<DadaStyle> findDadaStyleList(DadaStyle dadaStyle);

    List<DadaStyle> findDadaStyleTypeList(DadaStyle dadaStyle);

    DadaStyle getDadaStyle(DadaStyle dadaStyle);

    List<DadaStyle> findDadaStyleByPartsList(DadaStyle dadaStyle);
}