package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Task;

import java.util.List;

public interface TaskMapper extends MyMapper<Task> {

    public List<Task> findTaskList(Task task);

    public Task getTask(Task task);
}