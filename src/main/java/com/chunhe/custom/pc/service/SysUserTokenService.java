package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.pc.mapper.SysUserTokenMapper;
import com.chunhe.custom.pc.model.SysUserToken;
import com.chunhe.custom.pc.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zcw
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2019/7/2919:02
 */
@Service
public class SysUserTokenService extends BaseService<SysUserToken> {

    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;

    @Autowired
    private SysConfigService sysConfigService;

    @Value("${tokenTime}")
    private String tokenTime = "30";

    @Value("${tokenLogin}")
    private String tokenLogin = "1";

    /**
     * 查询token 是否过去
     *
     * @param token
     * @return
     */
    public Boolean isToken(String token) {
        SysUserToken sysUserToken = sysUserTokenMapper.isToken(token);
        if (sysUserToken == null || sysUserToken.getId() == null) {
            return false;
        }
        String tk = sysConfigService.getSysConfigByKey(SysConfig.TOKEN_TIME, tokenTime);
        Date date = DateUtil.getAfterMinuteTime(tk);
        sysUserToken.setEffectiveTime(date);
        sysUserTokenMapper.setTimeUpdate(sysUserToken);
//        sysUserTokenMapper.updateByPrimaryKey(sysUserToken);
        return true;
    }

    /**
     * 超时退出的开关
     *
     * @return
     */
    public Boolean isTokenLogin() {
        String isTokenLogin = sysConfigService.getSysConfigByKey(SysConfig.IS_TOKEN_LOGIN, tokenLogin);
        if (isTokenLogin.equals("1")) {
            return true;
        }
        return false;
    }
}
