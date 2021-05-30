package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.pc.mapper.VersionMapper;
import com.chunhe.custom.pc.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by white 2018年8月20日14:12:30
 */
@Service
public class VersionService extends BaseService<Version> {

    @Autowired
    private VersionMapper versionMapper;

    /**
     * 生成新版本（可以掉接口，也可以进数据库改）
     *
     * @param version
     * @return
     */
    @Transactional
    public void createVersion(Version version) {
        insertNotNull(version);
    }

    /**
     * 查询详情
     *
     * @param version
     * @return
     */
    @Transactional
    public Version getVersion(Version version) {
        Version v = versionMapper.getVersion(version);
        return v;
    }

}
