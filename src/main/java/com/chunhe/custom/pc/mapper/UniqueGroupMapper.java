package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.UniqueGroup;

import java.util.List;

public interface UniqueGroupMapper extends MyMapper<UniqueGroup> {

    List<UniqueGroup> findUniqueGroupList(UniqueGroup uniqueGroup);

    UniqueGroup getUniqueGroup(UniqueGroup uniqueGroup);
}