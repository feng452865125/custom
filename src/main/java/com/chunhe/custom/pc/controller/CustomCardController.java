package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.security.PlatformUser;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.pc.model.CustomCard;
import com.chunhe.custom.pc.service.ActionLogService;
import com.chunhe.custom.pc.service.CustomCardService;
import com.chunhe.custom.utils.DictUtils;
import com.github.pagehelper.ISelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.Map;

/**
 * Created by white 2020年10月28日21:27:37
 * 卡片管理
 */
@Controller
@RequestMapping("/customCard")
public class CustomCardController extends BaseController {

    @Autowired
    private CustomCardService customCardService;

    @Autowired
    private ActionLogService actionLogService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('customCard:page')")
    public String customCardList(Model model) {
        JSONObject statusList = DictUtils.findDicByType(CustomCard.CUSTOM_CARD_STATUS);
        model.addAttribute("statusList", statusList);
        return "pages/customCard/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('customCard:list')")
    public DataTablesResponse<CustomCard> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<CustomCard> data = customCardService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        customCardService.customCardList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('customCard:add')")
    public String add(Model model) {
        return "pages/customCard/add";
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasAuthority('customCard:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = customCardService.deleteById(id);
        return responseDeal(result);
    }

    /**
     * 查询
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('customCard:view')")
    public String view(@PathVariable Long id, Model model) {
        CustomCard customCard = customCardService.getCustomCard(id);
        model.addAttribute("customCard", customCard);
        return "pages/customCard/view";
    }


    /**
     * 禁用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/disabled", method = RequestMethod.PATCH)
    public ResponseEntity<String> disabled(@PathVariable long id, Authentication authentication, HttpServletRequest request) {
        if (customCardService.cardEnabled(id, 0)) {
            actionLogService.createActionLog(authentication, request, "卡片管理-卡片冻结");
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }

    /**
     * 启用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/enabled", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<String> enabled(@PathVariable Long id, Authentication authentication, HttpServletRequest request) {
        if (customCardService.cardEnabled(id, 1)) {
            actionLogService.createActionLog(authentication, request, "卡片管理-卡片解冻");
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }


    /**
     * 导入
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('customCard:add')")
    public ResponseEntity customCardImport(@RequestParam("file") MultipartFile file,
                                           @RequestParam("cardStatus") Integer cardStatus,
                                           Authentication authentication, HttpServletRequest request) {
        ServiceResponse result = customCardService.customCardImport(file, cardStatus);
        actionLogService.createActionLog(authentication, request, "卡片管理-卡片导入");
        return responseDeal(result);
    }


    /**
     * 批量操作，手填区间号
     *
     * @param map actionType 0激活/1冻结/2解冻
     * @param map cardCodeStart 16位卡号
     * @param map cardCodeEnd 16位卡号
     * @return
     */
    @RequestMapping(value = "/batchActionByHand", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('customCard:add')")
    public ResponseEntity customCardBatchActionByHand(@RequestBody Map<String, Object> map, Authentication authentication, HttpServletRequest request) {
        Integer actionType = ConvertUtil.convert(map.get("actionType"), Integer.class);
        String cardCodeStart = ConvertUtil.convert(map.get("cardCodeStart"), String.class);
        String cardCodeEnd = ConvertUtil.convert(map.get("cardCodeEnd"), String.class);
        PlatformUser user = (PlatformUser) authentication.getPrincipal();
        ServiceResponse result = customCardService.customCardBatchActionByHand(actionType, cardCodeStart, cardCodeEnd, user.getSysUser().getName());
        actionLogService.createActionLog(authentication, request, "卡片管理-批量操作-手填区间，actionType=" + actionType + "|start=" + cardCodeStart + "|end=" + cardCodeEnd);
        return responseDeal(result);
    }


    /**
     * 批量操作，导入文件
     *
     * @param file
     * @param actionType
     * @param authentication
     * @param request
     * @return
     */
    @RequestMapping(value = "/batchActionByFile", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('customCard:add')")
    public ResponseEntity customCardBatchActionByFile(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("actionType") Integer actionType,
                                                      Authentication authentication, HttpServletRequest request) {
        PlatformUser user = (PlatformUser) authentication.getPrincipal();
        ServiceResponse result = customCardService.customCardBatchActionByFile(file, actionType, user.getSysUser().getName());
        actionLogService.createActionLog(authentication, request, "卡片管理-批量操作-导入文件，actionType=" + actionType);
        return responseDeal(result);
    }


    /**
     * 激活
     *
     * @return
     */
    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('customCard:edit')")
    public ResponseEntity customCardActivate(@RequestBody Map<String, Object> map, Authentication authentication, HttpServletRequest request) {
        Long id = ConvertUtil.convert(map.get("id"), Long.class);
        ServiceResponse result = customCardService.customCardActivate(id);
        actionLogService.createActionLog(authentication, request, "卡片管理-卡片激活-cardId=" + id);
        return responseDeal(result);
    }


    /**
     * 使用核销
     *
     * @return
     */
    @RequestMapping(value = "/use", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('customCard:edit')")
    public ResponseEntity customCardUse(@RequestBody Map<String, Object> map, Authentication authentication, HttpServletRequest request) {
        ServiceResponse result = customCardService.customCardUse(map);
        actionLogService.createActionLog(authentication, request, "卡片管理-卡片核销");
        return responseDeal(result);
    }


    /**
     * 导出卡片
     *
     * @param cardCodeArr
     * @return
     */
    @RequestMapping(value = "/exportErrorCardCode/{cardCodeArr}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity exportErrorCardCode(@PathVariable String cardCodeArr, HttpServletResponse response, Authentication authentication, HttpServletRequest request) throws IOException {
        ServiceResponse result = customCardService.exportErrorCardCode(cardCodeArr, response);
        actionLogService.createActionLog(authentication, request, "卡片管理-导出异常卡号");
        return responseDeal(result);
    }

}
