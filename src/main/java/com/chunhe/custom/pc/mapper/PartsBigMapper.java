package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.PartsBig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartsBigMapper extends MyMapper<PartsBig> {

    List<PartsBig> findPartsBigList(PartsBig partsBig);

    PartsBig getPartsBig(PartsBig partsBig);

    List findCompanyList();

    public int setExpire();

    public int setEnable(@Param("enable") Integer enable);
}