package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.RelProductParts;

import java.util.List;

public interface RelProductPartsMapper extends MyMapper<RelProductParts> {

    List<RelProductParts> findRelProductParts(RelProductParts relProductParts);

    RelProductParts getRelProductParts(RelProductParts relProductParts);
}