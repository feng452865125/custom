package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Series;

import java.util.List;

public interface SeriesMapper extends MyMapper<Series> {

    List<Series> findSeriesList(Series series);

    Series getSeries(Series series);
}