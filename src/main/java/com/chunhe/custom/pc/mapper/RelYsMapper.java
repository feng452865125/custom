package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.RelYs;

import java.util.List;

public interface RelYsMapper extends MyMapper<RelYs> {

    List<RelYs> findRelYsList();

    RelYs getRelYs(RelYs relYs);
}