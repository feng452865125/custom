package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.DadaParts;
import com.chunhe.custom.pc.model.DadaRelStyleParts;

import java.util.List;

public interface DadaRelStylePartsMapper extends MyMapper<DadaRelStyleParts> {

    List<DadaParts> findDadaRelStyleParts(DadaParts dadaParts);

    DadaRelStyleParts getDadaRelStyleParts(DadaRelStyleParts dadaRelStyleParts);
}