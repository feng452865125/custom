package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.pc.model.Version;
import com.chunhe.custom.pc.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by white 2018年8月20日14:46:55
 * 版本信息
 */
@RestController
@RequestMapping("/app/version")
public class VersionAppController extends BaseController {

    @Autowired
    private VersionService versionService;

    /**
     * app新版本--创建
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public String createVersion(HttpServletRequest req) {
        Version version = checkContent(req);
        versionService.createVersion(version);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CREATE_SUCCESS);
    }

    /**
     * app新版本--查询
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String getVersion(HttpServletRequest req) {
        Version version = checkContent(req);
        Version v = versionService.getVersion(version);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, v);
    }

    /**
     * content处理
     * @param req
     * @return
     */
    public Version checkContent(HttpServletRequest req) {
        Version version = new Version();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            version = JSON.parseObject(content, Version.class);
        }
        return version;
    }

}
