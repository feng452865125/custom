package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.DadaParts;
import com.chunhe.custom.pc.model.DadaRelStyleParts;

import java.util.List;

public interface DadaPartsMapper extends MyMapper<DadaParts> {

    List<DadaParts> findDadaPartsList(DadaParts dadaParts);

    DadaParts getDadaParts(DadaParts dadaParts);

    List<DadaParts> findDadaRelStyleParts(DadaRelStyleParts dadaRelStyleParts);
}