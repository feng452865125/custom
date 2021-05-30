package com.chunhe.custom.framework.service;

import com.chunhe.custom.pc.mapper.SysUserDetailMapper;
import com.chunhe.custom.pc.model.SysUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SysUserDetailService extends MyService<SysUserDetail> {

    @Autowired
    private SysUserDetailMapper sysUserDetailMapper;

    /**
     * 同步数据时用到，所有用户信息（门店）
     * @return
     */
    public List<SysUserDetail> findSysUserDetailList(SysUserDetail sysUserDetail) {
        List<SysUserDetail> sysUserDetailList = sysUserDetailMapper.findSysUserDetailList(sysUserDetail);
        return sysUserDetailList;
    }

}
