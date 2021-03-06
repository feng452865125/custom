package com.chunhe.custom.controller.fm;

import com.chunhe.custom.entity.PlatformUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping(value = {"/", "/index"})
    public String index(ModelMap model, Authentication authentication, HttpSession session) {
        PlatformUser user = (PlatformUser) authentication.getPrincipal();
        model.put("username", user.getSysUser().getSysUserName());
        session.setAttribute("userId",user.getSysUser().getId());
        return "index";
    }

    /**
     * 主页跳转
     */
    @RequestMapping(value = "/home")
    public String home(Model model) {
        return "component/indexContent";
    }

}
