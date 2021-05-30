package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.ThirdSupplier;

import java.util.List;

public interface ThirdSupplierMapper extends MyMapper<ThirdSupplier> {

    public List<ThirdSupplier> findThirdSupplierList(ThirdSupplier thirdSupplier);

    public ThirdSupplier getThirdSupplier(ThirdSupplier thirdSupplier);

    //2020年9月13日12:01:42 第三方同步石头的开关
    public List findThirdStoneCompanyList();
}