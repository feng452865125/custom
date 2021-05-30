package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.CustomCardTask;

import java.util.List;

public interface CustomCardTaskMapper extends MyMapper<CustomCardTask> {

    List<CustomCardTask> findCustomCardTaskList(CustomCardTask customCardTask);

    CustomCardTask getCustomCardTask(CustomCardTask customCardTask);
}