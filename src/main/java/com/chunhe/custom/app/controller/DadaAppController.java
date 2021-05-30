package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.framework.utils.RequestUtils;
import com.chunhe.custom.pc.model.DadaJewelryType;
import com.chunhe.custom.pc.model.DadaParts;
import com.chunhe.custom.pc.model.DadaSeries;
import com.chunhe.custom.pc.model.DadaStyle;
import com.chunhe.custom.pc.service.DadaJewelryTypeService;
import com.chunhe.custom.pc.service.DadaPartsService;
import com.chunhe.custom.pc.service.DadaSeriesService;
import com.chunhe.custom.pc.service.DadaStyleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2018-9-5 19:40:26
 * dada系列维护
 */
@RestController
@RequestMapping("/app/dada")
public class DadaAppController extends BaseController {

    @Autowired
    private DadaJewelryTypeService dadaJewelryTypeService;

    @Autowired
    private DadaStyleService dadaStyleService;

    @Autowired
    private DadaSeriesService dadaSeriesService;

    @Autowired
    private DadaPartsService dadaPartsService;


    /**
     * 佩戴类型列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/jewelryType/find", method = RequestMethod.GET)
    @ResponseBody
    public String findDadaJewelryTypeList(HttpServletRequest req) {
        List<DadaJewelryType> dadaJewelryTypeList = dadaJewelryTypeService.findDadaJewelryTypeList(new DadaJewelryType());
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, dadaJewelryTypeList);
    }

    /**
     * 系列列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/series/find", method = RequestMethod.GET)
    @ResponseBody
    public String findDadaSeriesList(HttpServletRequest req) {
        DadaSeries dadaSeries = new DadaSeries();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            dadaSeries = JSON.parseObject(content, DadaSeries.class);
        }
        dadaSeries.setEnabled(DadaSeries.ENABLED_TRUE);
        List<DadaSeries> dadaSeriesList = dadaSeriesService.findDadaSeriesList(dadaSeries);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, dadaSeriesList);
    }

    /**
     * 样式列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/style/find", method = RequestMethod.GET)
    @ResponseBody
    public String findDadaStyleList(HttpServletRequest req) {
        DadaStyle dadaStyle = new DadaStyle();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            dadaStyle = JSON.parseObject(content, DadaStyle.class);
        }
        dadaStyle.setCount(1);
        List<DadaStyle> dadaStyleTypeList = dadaStyleService.findDadaStyleTypeList(dadaStyle);
        List<DadaStyle> dadaStyleList = dadaStyleService.findDadaStyleList(dadaStyle);
        Map<String, Object> map = new HashMap<>();
        map.put("dadaStyleTypeList", dadaStyleTypeList);
        map.put("dadaStyleList", dadaStyleList);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, map);
    }

    /**
     * 样式详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/style/get", method = RequestMethod.GET)
    @ResponseBody
    public String getDadaStyle(HttpServletRequest req) {
        DadaStyle dadaStyle = new DadaStyle();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            dadaStyle = JSON.parseObject(content, DadaStyle.class);
        }
        DadaStyle dada = dadaStyleService.getDadaStyleDetail(dadaStyle);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, dada);
    }

    /**
     * 更多组合（两个以上SKU）
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/style/more/find", method = RequestMethod.GET)
    @ResponseBody
    public String findDadaStyleMoreList(HttpServletRequest req) {
        DadaStyle dadaStyle = new DadaStyle();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            dadaStyle = JSON.parseObject(content, DadaStyle.class);
        }
        List<DadaStyle> dadaStyleList = dadaStyleService.findDadaStyleMoreList(dadaStyle);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, dadaStyleList);
    }

    /**
     * 组件列表（定制）
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/parts/find", method = RequestMethod.GET)
    @ResponseBody
    public String findDadaPartsList(HttpServletRequest req) {
        DadaParts dadaParts = new DadaParts();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            dadaParts = JSON.parseObject(content, DadaParts.class);
        }
        dadaParts.setPrice(0);
        RequestUtils.startPageHelp(req);
        List<DadaParts> dadaPartsList = dadaPartsService.findDadaPartsList(dadaParts);
        PageInfo<DadaParts> pageInfo = new PageInfo<>(dadaPartsList);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, pageInfo);
    }

    /**
     * 查询款式（定制）
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/styleByParts/find", method = RequestMethod.GET)
    @ResponseBody
    public String findDadaStyleByPartsList(HttpServletRequest req) {
        DadaStyle dadaStyle = new DadaStyle();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            dadaStyle = JSON.parseObject(content, DadaStyle.class);
        }
        RequestUtils.startPageHelp(req);
        List<DadaStyle> dadaStyleList = dadaStyleService.findDadaStyleByPartsList(dadaStyle);
        PageInfo<DadaStyle> pageInfo = new PageInfo<>(dadaStyleList);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, pageInfo);
    }

}
