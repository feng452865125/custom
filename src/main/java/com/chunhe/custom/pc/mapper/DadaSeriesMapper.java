package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.DadaSeries;

import java.util.List;

public interface DadaSeriesMapper extends MyMapper<DadaSeries> {

    List<DadaSeries> findDadaSeriesList(DadaSeries dadaSeries);

    DadaSeries getDadaSeries(DadaSeries dadaSeries);
}