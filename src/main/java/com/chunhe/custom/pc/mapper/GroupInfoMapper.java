package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.GroupInfo;

import java.util.List;

public interface GroupInfoMapper extends MyMapper<GroupInfo> {

    List<GroupInfo> findGroupInfoList(GroupInfo group);

    GroupInfo getGroupInfo(GroupInfo group);

    GroupInfo getGroupInfoYs(GroupInfo group);
}