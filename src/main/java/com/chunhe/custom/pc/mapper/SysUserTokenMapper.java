package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.SysUserToken;
import org.apache.ibatis.annotations.Param;;

/**
 * @author zcw
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2019/7/2919:02
 */
public interface SysUserTokenMapper extends MyMapper<SysUserToken> {

    SysUserToken isToken(@Param("token") String token);

    public int setTimeOver(SysUserToken sysUserToken);

    public int setTimeUpdate(SysUserToken sysUserToken);
}
