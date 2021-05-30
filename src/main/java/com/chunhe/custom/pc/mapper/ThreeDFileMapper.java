package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.ThreeDFile;

import java.util.List;

public interface ThreeDFileMapper extends MyMapper<ThreeDFile> {

    List<ThreeDFile> findThreeDModulesList(ThreeDFile threeDFile);

    List<ThreeDFile> findThreeDFileList(ThreeDFile threeDFile);

    ThreeDFile getThreeDFile(ThreeDFile threeDFile);

}