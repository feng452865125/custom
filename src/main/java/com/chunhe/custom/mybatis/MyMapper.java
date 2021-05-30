package com.chunhe.custom.mybatis;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

public interface MyMapper<T> extends Mapper<T>, IdsMapper<T> {
}
