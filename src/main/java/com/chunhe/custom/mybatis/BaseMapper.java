package com.chunhe.custom.mybatis;


import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseMapper<T> extends Mapper<T>, IdsMapper<T> {

    int insert(T var1);

    int deleteById(T var1);

    int delete(T var1);

    int deleteBatchIds(@Param("coll") Collection<? extends Serializable> var1);

    int updateById(T var1);

    T selectById(Serializable var1);

    List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> var1);

    List<T> selectByMap(@Param("cm") Map<String, Object> var1);

}
