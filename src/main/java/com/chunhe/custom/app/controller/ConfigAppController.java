package com.chunhe.custom.app.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by white 2019-4-27 12:56:50
 * 系统信息
 */
@RestController
@RequestMapping("/app/config")
public class ConfigAppController extends BaseController {

    @Autowired
    private SysConfigService sysConfigService;


    /**
     * 系统配置--查询
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String getVersion(HttpServletRequest req) {
        SysConfig sysConfig = BeanUtil.checkContent(req, SysConfig.class);
        SysConfig config = sysConfigService.getSysConfig(sysConfig);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, config);
    }


}
