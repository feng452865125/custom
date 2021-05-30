package com.chunhe.custom.mapper;

import com.chunhe.custom.entity.SysToken;
import com.chunhe.custom.mybatis.BaseMapper;

import java.util.List;

/**
 * <p>
 * 登录token Mapper 接口
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-14
 */
public interface SysTokenMapper extends BaseMapper<SysToken> {

    List<SysToken> findSysTokenList(SysToken sysToken);

    int setTokenTimeOver(SysToken sysToken);

}
