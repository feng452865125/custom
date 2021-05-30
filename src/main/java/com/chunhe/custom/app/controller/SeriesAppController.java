package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.framework.utils.RequestUtils;
import com.chunhe.custom.pc.model.Series;
import com.chunhe.custom.pc.model.Style;
import com.chunhe.custom.pc.service.SeriesService;
import com.chunhe.custom.pc.service.StyleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by white 2018年7月19日09:48:58
 * 系列
 */
@RestController
@RequestMapping("/app/series")
public class SeriesAppController extends BaseController {

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private StyleService styleService;

    /**
     * 系列列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public String findSeriesList(HttpServletRequest req) {
        Series series = checkContent(req);
        series.setEnabled(Series.ENABLED_TRUE);
        List<Series> seriesList = seriesService.findSeriesList(series);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, seriesList);
    }

    /**
     * 系列下的产品列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/style/find/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String findSeriesStyleList(HttpServletRequest req, @PathVariable Long id) {
        Series series = seriesService.getSeries(id);
        Style style = new Style();
        style.setSeries(series.getName());
        RequestUtils.startPageHelp(req);
        List<Style> styleList = styleService.findStyleListApp(style);
        PageInfo<Style> pageInfo = new PageInfo<>(styleList);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, pageInfo);
    }

    /**
     * content处理
     * @param req
     * @return
     */
    public Series checkContent(HttpServletRequest req) {
        Series series = new Series();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            series = JSON.parseObject(content, Series.class);
        }
        return series;
    }

}
