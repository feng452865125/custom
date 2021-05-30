package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.AdditionalCosts;

import java.util.List;

public interface AdditionalCostsMapper extends MyMapper<AdditionalCosts> {

    List<AdditionalCosts> findAdditionalCostsList(AdditionalCosts additionalCosts);

    AdditionalCosts getAdditionalCosts(AdditionalCosts additionalCosts);
}