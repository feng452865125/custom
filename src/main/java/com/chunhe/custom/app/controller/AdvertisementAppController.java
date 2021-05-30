package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.pc.model.Advertisement;
import com.chunhe.custom.pc.model.Style;
import com.chunhe.custom.pc.service.AdvertisementService;
import com.chunhe.custom.pc.service.StyleService;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by white 2018年7月19日09:48:58
 * 广告
 */
@RestController
@RequestMapping("/app/advertisement")
public class AdvertisementAppController extends BaseController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private StyleService styleService;

    /**
     * 广告列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public String findAdvertisementList(HttpServletRequest req) {
        Advertisement advertisement = checkContent(req);
        advertisement.setEnabled(Advertisement.ENABLED_TRUE);
        List<Advertisement> advertisementList = advertisementService.findAdvertisementList(advertisement);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, advertisementList);
    }

    /**
     * 广告详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getAdvertisement(HttpServletRequest req, @PathVariable Long id) {
        Advertisement advertisement = advertisementService.getAdvertisement(id);
        if (advertisement == null) {
            throw new RFException("广告不存在");
        }
        Style style = new Style();
        style.setAdvertisementId(id);
        style.setEnabled(true);
        List<Style> styleList = styleService.findRelAdvertisementStyle(style);
        advertisement.setStyleList(styleList);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, advertisement);
    }

    /**
     * content处理
     *
     * @param req
     * @return
     */
    public Advertisement checkContent(HttpServletRequest req) {
        Advertisement advertisement = new Advertisement();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            advertisement = JSON.parseObject(content, Advertisement.class);
        }
        return advertisement;
    }

}
