package com.chunhe.custom.controller.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 账号 前端-控制层
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@RestController
@RequestMapping("/app/SysUser")
public class AppSysUserController {
    /**
     * 广告列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/tttt", method = RequestMethod.GET)
    @ResponseBody
    public String findAdvertisementList(HttpServletRequest req) {
      return "qqqq";
    }
}