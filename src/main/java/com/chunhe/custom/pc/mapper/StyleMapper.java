package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Style;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StyleMapper extends MyMapper<Style> {

    List<Style> findStyleListApp(Style style);

    List<Style> findStyleList(Style style);

    Style getStyle(Style style);

    List<Style> findRelAdvertisementStyle(Style style);

    List<Style> findStyleRecommendList(Style style);

    /**
     * 开启和关闭样式
     * @param style
     * @return
     */
    int openOnOff(Style style);


    List<Style> dropDownBox(@Param("name") String name,@Param("series") String series);

}