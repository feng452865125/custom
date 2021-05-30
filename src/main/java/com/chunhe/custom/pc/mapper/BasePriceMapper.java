package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.BasePrice;
import com.chunhe.custom.pc.model.Parts;

import java.util.List;

public interface BasePriceMapper extends MyMapper<BasePrice> {

    public List<BasePrice> findBasePriceList(BasePrice basePrice);

    public BasePrice getBasePrice(BasePrice basePrice);

    public List<BasePrice> findDiamondGroupList(Parts parts);

    List<BasePrice> findDiamondGroupListPC(Parts parts);

    //查出基准价格，用于同步钻石时候过滤价格过高的
    public BasePrice getDiamondFilter(BasePrice basePrice);

    public BasePrice getBasePriceByProperty(BasePrice basePrice);
}