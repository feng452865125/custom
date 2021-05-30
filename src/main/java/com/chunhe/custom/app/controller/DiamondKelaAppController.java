package com.chunhe.custom.app.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.framework.utils.RequestUtils;
import com.chunhe.custom.pc.model.DiamondKela;
import com.chunhe.custom.pc.service.DiamondKelaService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by white 2018-11-21 14:47:21
 * 新系列--克拉展
 */
@RestController
@RequestMapping("/app/diamondKela")
public class DiamondKelaAppController extends BaseController {

    @Autowired
    private DiamondKelaService diamondKelaService;

    /**
     * 列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public String findDiamondKelaList(HttpServletRequest req) {
        DiamondKela diamondKela = new DiamondKela();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            diamondKela = JSON.parseObject(content, DiamondKela.class);
        }
        RequestUtils.startPageHelp(req);
        diamondKela.setStatus(DiamondKela.DIAMOND_KELA_STATUS_SOLD);
        List<DiamondKela> diamondKelaList = diamondKelaService.findDiamondKelaList(diamondKela);
        PageInfo<DiamondKela> pageInfo = new PageInfo<>(diamondKelaList);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, pageInfo);
    }

    /**
     * 样式详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String getDiamondKela(HttpServletRequest req) {
        DiamondKela diamondKela = new DiamondKela();
        String content = req.getParameter("content");
        if (!CheckUtil.checkNull(content)) {
            diamondKela = JSON.parseObject(content, DiamondKela.class);
        }
        DiamondKela dk = diamondKelaService.getDiamondKelaDetail(diamondKela);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, dk);
    }

}
