package com.chunhe.custom.controller.fm;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by LW on 2018/5/19
 * 后台重要管理，测试等
 */
@Controller
@RequestMapping(path = "/admin")
@PreAuthorize("hasAuthority('admin')")
public class AdminController {

    @RequestMapping(value = "/upload")
    public String sysUserList() {
        return "demo/jqupload/index";
    }
}
