package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.pc.model.ThreeDFile;
import com.chunhe.custom.pc.service.ThreeDFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by white 2018-8-3 16:59:11
 * 用户
 */
@RestController
@RequestMapping("/app/threeD")
public class ThreeDFileAppController extends BaseController {

    @Autowired
    private ThreeDFileService threeDFileService;

    /**
     * 查询3d文件list
     * @param req
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public String findThreeDFileList(HttpServletRequest req) {
        ThreeDFile threeDFile = checkContent(req);
        List<ThreeDFile> threeDFileList = threeDFileService.findThreeDFileList(threeDFile);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, threeDFileList);
    }

    /**
     * 查询3d文件
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getThreeDFile(@PathVariable Long id) {
        ThreeDFile threeDFile = threeDFileService.getThreeDFile(id);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, threeDFile);
    }


    /**
     * content处理
     * @param req
     * @return
     */
    public ThreeDFile checkContent(HttpServletRequest req) {
        ThreeDFile threeDFile = new ThreeDFile();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            threeDFile = JSON.parseObject(content, ThreeDFile.class);
        }
        return threeDFile;
    }

}
