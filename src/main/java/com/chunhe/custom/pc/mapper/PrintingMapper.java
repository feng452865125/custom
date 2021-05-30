package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Printing;

import java.util.List;

public interface PrintingMapper extends MyMapper<Printing> {

    List<Printing> findPrintingList(Printing printing);

    Printing getPrinting(Printing printing);
}