package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.service.SysUserService;
import com.chunhe.custom.pc.mapper.SysUserDetailMapper;
import com.chunhe.custom.pc.model.SysUserDetail;
import com.chunhe.custom.pc.model.UseLog;
import com.chunhe.custom.pc.service.UseLogService;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.model.SysUser;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by white 2018年7月18日13:39:33
 * 用户
 */
@RestController
@RequestMapping("/app/user")
public class UserAppController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserDetailMapper sysUserDetailMapper;

    @Autowired
    private UseLogService useLogService;

    /**
     * 用户登录（店铺）
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/login/in", method = RequestMethod.POST)
    @ResponseBody
    public String userLoginIn(HttpServletRequest req) {
        SysUser sysUser = checkContent(req);
        if (sysUser.getUsername().equals("admin")) {
            throw new RFException("admin账号暂时无法登录APP");
        }
        SysUser user = sysUserService.userLoginIn(sysUser);
        user.setPassword(null);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, "登录成功", user);
    }


    /**
     * 注销（退出）
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/login/out", method = RequestMethod.POST)
    @ResponseBody
    public String find(@PathVariable Long id) {
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS);
    }


    /**
     * 修改密码
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public String changePasswordApp(HttpServletRequest req) {
        SysUser sysUser = checkContent(req);
        SysUser user = sysUserService.changePasswordApp(sysUser);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, "密码修改成功", user);
    }

    /**
     * 店铺下的所有员工列表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/find/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String userDetailFind(@PathVariable String id) {
        SysUserDetail sysUserDetail = new SysUserDetail();
        sysUserDetail.setStoreId(id);
        List<SysUserDetail> sysUserDetailList = sysUserDetailMapper.findSysUserDetailList(sysUserDetail);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, sysUserDetailList);
    }

    /**
     * 使用日志（转发、试戴、定制）
     *
     * @return
     */
    @RequestMapping(value = "/useLog/create", method = RequestMethod.POST)
    @ResponseBody
    public String createUseLog(HttpServletRequest req) {
        String content = req.getParameter("content");
        if (CheckUtil.checkNull(content)) {
            throw new RFException("数据不合理");
        }
        UseLog useLog = JSON.parseObject(content, UseLog.class);
        useLogService.createUseLog(useLog);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS);
    }

    /**
     * 附近的店铺
     *
     * @return
     */
    @RequestMapping(value = "/nearby/find", method = RequestMethod.GET)
    @ResponseBody
    public String nearbyFind(HttpServletRequest req) {
        SysUser sysUser = checkContent(req);
        List<SysUser> sysUserList = sysUserService.findSysUserNearbyList(sysUser);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, sysUserList);
    }

    /**
     * content处理
     *
     * @param req
     * @return
     */
    public SysUser checkContent(HttpServletRequest req) {
        SysUser sysUser = new SysUser();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            sysUser = JSON.parseObject(content, SysUser.class);
        }
        return sysUser;
    }

}
