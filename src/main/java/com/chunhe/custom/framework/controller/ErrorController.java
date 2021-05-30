package com.chunhe.custom.framework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xuqiang on 2017/6/12.
 */
@Controller
public class ErrorController {

    @RequestMapping("/404")
    public String e404() {
        return "error/404";
    }

    @RequestMapping("/500")
    public String e500() {
        return "error/500";
    }

}
