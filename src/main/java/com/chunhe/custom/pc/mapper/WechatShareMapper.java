package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.WechatShare;

public interface WechatShareMapper extends MyMapper<WechatShare> {

    public WechatShare getLastWechatShare(WechatShare wechatShare);
}