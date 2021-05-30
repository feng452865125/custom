package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.model.PartsMiddle;

import java.util.List;

public interface PartsMiddleMapper extends MyMapper<PartsMiddle> {

    /**
     * 根据要求获取 列表
     * @param partsMiddle
     * @return
     */
    List<PartsMiddle> findPartsMiddleList(PartsMiddle partsMiddle);

    /**
     * 获取创建时间在24小时内的列表
     * @param parts
     * @return
     */
    List<Parts> findCreatePartsList(Parts parts);

    /**
     * 获取更新时间在24小时内的列表
     * @param parts
     * @return
     */
    List<PartsMiddle> findUpdatePartsList(Parts parts);

    /**
     * 获取失效时间在24小时内的列表
     * @param parts
     * @return
     */
    List<PartsMiddle> findExpirePartsList(Parts parts);
}