package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.DiamondKela;

import java.util.List;

public interface DiamondKelaMapper extends MyMapper<DiamondKela> {

    List<DiamondKela> findDiamondKelaList(DiamondKela diamondKela);

    DiamondKela getDiamondKela(DiamondKela diamondKela);
}