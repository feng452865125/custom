package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.pc.model.EnumCode;
import com.chunhe.custom.pc.service.EnumCodeService;
import com.chunhe.custom.pc.service.EnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by white 2018年7月24日14:18:17
 * 订单
 */
@RestController
@RequestMapping("/app/enum")
public class EnumAppController extends BaseController {

    @Autowired
    private EnumService enumService;

    @Autowired
    private EnumCodeService enumCodeService;

    /**
     * 配置枚举查询
     * @param req
     * @return
     */
    @RequestMapping(value = "/find/{typeName}", method = RequestMethod.GET)
    @ResponseBody
    public String findKeyEnum(HttpServletRequest req, @PathVariable String typeName) {
        JSONObject jsonObject = enumService.findKeyEnum(typeName);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, jsonObject);
    }

    /**
     * 配置枚举查询
     * @param req
     * @return
     */
    @RequestMapping(value = "/get/{typeName}/{key}", method = RequestMethod.GET)
    @ResponseBody
    public String getKeyEnum(HttpServletRequest req, @PathVariable String typeName, @PathVariable Integer key) {
        String name = enumService.getKeyEnum(typeName, key);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, name);
    }

    /**
     * enum_code数据list
     * @param req
     * @return
     */
    @RequestMapping(value = "/code/find", method = RequestMethod.GET)
    @ResponseBody
    public String findEnumCodeList(HttpServletRequest req) {
        EnumCode enumCode = this.checkContent(req);
        List<EnumCode> enumCodeList = enumCodeService.findEnumCodeList(enumCode);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, enumCodeList);
    }

    /**
     * enum_code数据list
     * @param req
     * @return
     */
    @RequestMapping(value = "/code/get", method = RequestMethod.GET)
    @ResponseBody
    public String getEnumCode(HttpServletRequest req) {
        EnumCode enumCode = this.checkContent(req);
        String code = enumCodeService.getEnumCodeByName(enumCode.getName(), enumCode.getType());
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, code);
    }



    /**
     * content处理
     * @param req
     * @return
     */
    public EnumCode checkContent(HttpServletRequest req) {
        EnumCode enumCode = new EnumCode();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            enumCode = JSON.parseObject(content, EnumCode.class);
        }
        return enumCode;
    }
}
