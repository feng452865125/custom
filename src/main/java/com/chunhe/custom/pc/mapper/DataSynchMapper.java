package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.DataSynch;

import java.util.List;

public interface DataSynchMapper extends MyMapper<DataSynch> {

    List<DataSynch> findDataSynchList (DataSynch dataSynch);

    List<DataSynch> findDataSynchOrderByTypeList (DataSynch dataSynch);

    DataSynch getDataSynch (DataSynch dataSynch);

}