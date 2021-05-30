package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Version;

public interface VersionMapper extends MyMapper<Version> {

    Version getVersion(Version version);
}