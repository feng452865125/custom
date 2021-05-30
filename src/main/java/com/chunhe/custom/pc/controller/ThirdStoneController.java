package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.pc.model.BasePrice;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.service.ActionLogService;
import com.chunhe.custom.pc.service.SynDiamond.ThirdStoneService;
import com.chunhe.custom.utils.DictUtils;
import com.github.pagehelper.ISelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2020年9月13日11:55:53
 * 第三方同步的石头管理
 */
@Controller
@RequestMapping("/thirdStone")
public class ThirdStoneController extends BaseController {

    @Autowired
    private ActionLogService actionLogService;

    @Autowired
    private ThirdStoneService thirdStoneService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('thirdStone:page')")
    public String thirdStoneList(Model model) {
        List companyList = thirdStoneService.findThirdStoneCompanyList();
        model.addAttribute("companyList", companyList);
        JSONObject zsYsList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YS);
        JSONObject zsJdList = DictUtils.findDicByType(BasePrice.BASE_PRICE_JD);
        JSONObject zsQgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_QG);
        JSONObject zsPgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_PG);
        JSONObject zsDcList = DictUtils.findDicByType(BasePrice.BASE_PRICE_DC);
        JSONObject zsYgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YG);
        model.addAttribute("zsYsList", zsYsList);
        model.addAttribute("zsJdList", zsJdList);
        model.addAttribute("zsQgList", zsQgList);
        model.addAttribute("zsPgList", zsPgList);
        model.addAttribute("zsDcList", zsDcList);
        model.addAttribute("zsYgList", zsYgList);
        return "pages/thirdStone/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('thirdStone:list')")
    public DataTablesResponse<Parts> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Parts> data = thirdStoneService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        thirdStoneService.thirdStoneList(dataTablesRequest);
                    }
                });
        return data;
    }

    /**
     * 查询
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('thirdStone:view')")
    public String view(@PathVariable Long id, Model model) {
        Parts thirdStone = thirdStoneService.getThirdStone(id);
        model.addAttribute("thirdStone", thirdStone);
        return "pages/thirdStone/view";
    }

    /**
     * 上架，下架
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/enabled/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<String> thirdStoneEnabled(@PathVariable Long id, Authentication authentication, HttpServletRequest request) {
        actionLogService.createActionLog(authentication, request, "石头上架/下架");
        ServiceResponse result = thirdStoneService.updateEnableStatus(id);
        return responseDeal(result);
    }


    /**
     * 批量导入，上架/下架
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('thirdStone:list')")
    public ResponseEntity importPartsEnable(@RequestParam("file") MultipartFile file,
                                            @RequestParam("enableType") Integer enableType,
                                            Authentication authentication, HttpServletRequest request) {
        actionLogService.createActionLog(authentication, request, "批量导入石头上架/下架，type=" + enableType);
        ServiceResponse result = thirdStoneService.importPartsEnable(file, enableType);
        return responseDeal(result);
    }


    /**
     * 批量导入，默认上架
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/importStone", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('thirdStone:list')")
    public ResponseEntity importPartsStone(@RequestParam("file") MultipartFile file,
                                           Authentication authentication, HttpServletRequest request) {
        actionLogService.createActionLog(authentication, request, "批量导入，默认上架");
        ServiceResponse result = thirdStoneService.importPartsStone(file);
        return responseDeal(result);
    }

    /**
     * 结束操作
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/enableOver", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('thirdStone:list')")
    public ResponseEntity<String> enabledOver(@RequestBody Map<String, Object> map, Authentication authentication, HttpServletRequest request) {
        String enableOverDate = ConvertUtil.convert(map.get("enableOverDate"), String.class);
        ServiceResponse result = thirdStoneService.overPartsEnable(enableOverDate);
        actionLogService.createActionLog(authentication, request, "结束今日操作，" + enableOverDate);
        return responseDeal(result);
    }


    /**
     * 导出未操作的石头
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/outport/{createDate}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('thirdStone:list')")
    public ResponseEntity<String> outportPartsEnable(@PathVariable String createDate, HttpServletResponse response,
                                                     Authentication authentication, HttpServletRequest request) throws IOException {
        actionLogService.createActionLog(authentication, request, "导出未操作的石头，" + createDate);
        thirdStoneService.outportPartsEnable(createDate, response);
        return responseDeal(ServiceResponse.succ("导出成功，准备下载"));
    }


    /**
     * 导出全部已上架的石头
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/outport/allLockEnable", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('thirdStone:list')")
    public ResponseEntity<String> outportAllLockEnable(HttpServletResponse response, Authentication authentication, HttpServletRequest request) throws IOException {
        actionLogService.createActionLog(authentication, request, "导出全部已上架石头");
        thirdStoneService.outportAllLockEnableDiamondList(response);
        return responseDeal(ServiceResponse.succ("导出成功，准备下载"));
    }

}
