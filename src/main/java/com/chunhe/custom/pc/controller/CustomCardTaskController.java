package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.security.PlatformUser;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.utils.DateUtil;
import com.chunhe.custom.pc.model.CustomCardTask;
import com.chunhe.custom.pc.service.ActionLogService;
import com.chunhe.custom.pc.service.CustomCardTaskService;
import com.chunhe.custom.utils.DictUtils;
import com.github.pagehelper.ISelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2020年10月28日21:27:37
 * 卡片任务管理
 */
@Controller
@RequestMapping("/customCardTask")
public class CustomCardTaskController extends BaseController {

    @Autowired
    private CustomCardTaskService customCardTaskService;

    @Autowired
    private SysConfigService sysConfigService;
    
    @Autowired
    private ActionLogService actionLogService;

    @Value("${cardTaskPrice}")
    private String cardTaskPrice;

    @Value("${cardTaskEnableMonth}")
    private String cardTaskEnableMonth;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('customCardTask:page')")
    public String customCardTaskList(Model model) {
        JSONObject statusList = DictUtils.findDicByType(CustomCardTask.CUSTOM_CARD_TASK_STATUS);
        model.addAttribute("statusList", statusList);
        return "pages/customCardTask/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('customCardTask:list')")
    public DataTablesResponse<CustomCardTask> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<CustomCardTask> data = customCardTaskService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        customCardTaskService.customCardTaskList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('customCardTask:add')")
    public String add(Model model) {
        String priceArr = sysConfigService.getSysConfigByKey(CustomCardTask.CUSTOM_CARD_TASK_PRICE, cardTaskPrice);
        List priceList = Arrays.asList(priceArr.split("/"));
        model.addAttribute("priceList", priceList);
        String enableMonth = sysConfigService.getSysConfigByKey(CustomCardTask.CUSTOM_CARD_TASK_ENABLE_MONTH, cardTaskEnableMonth);
        String cardTaskLastDate = DateUtil.formatDate(DateUtil.getLastMonth(new Date(), Integer.parseInt(enableMonth)), DateUtil.FORMAT_DATE);
        model.addAttribute("cardTaskLastDate", cardTaskLastDate);
        return "pages/customCardTask/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('customCardTask:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map, Authentication authentication, HttpServletRequest request) {
        PlatformUser user = (PlatformUser) authentication.getPrincipal();
        map.put("cardTaskCreateUser", user.getSysUser().getName());
        ServiceResponse result = customCardTaskService.save(map);
        actionLogService.createActionLog(authentication, request, "卡片任务管理-添加制卡任务");
        return responseDeal(result);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasAuthority('customCardTask:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = customCardTaskService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('customCardTask:edit')")
    public String edit(@PathVariable Long id, Model model) {
        CustomCardTask customCardTask = customCardTaskService.getCustomCardTask(id);
        model.addAttribute("customCardTask", customCardTask);
        return "pages/customCardTask/edit";
    }

    /**
     * 修改
     *
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('customCardTask:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = customCardTaskService.update(map);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
    }

    /**
     * 查询
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('customCardTask:view')")
    public String view(@PathVariable Long id, Model model) {
        CustomCardTask customCardTask = customCardTaskService.getCustomCardTask(id);
        model.addAttribute("customCardTask", customCardTask);
        return "pages/customCardTask/view";
    }

    /**
     * 禁用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/disabled", method = RequestMethod.PATCH)
    public ResponseEntity<String> disabled(@PathVariable long id, Authentication authentication, HttpServletRequest request) {
        PlatformUser user = (PlatformUser) authentication.getPrincipal();
        if (customCardTaskService.cardTaskEnabled(id, 0, user.getSysUser().getName())) {
            actionLogService.createActionLog(authentication, request, "卡片任务管理-任务禁用");
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
        PlatformUser user = (PlatformUser) authentication.getPrincipal();
        if (customCardTaskService.cardTaskEnabled(id, 1, user.getSysUser().getName())) {
            actionLogService.createActionLog(authentication, request, "卡片任务管理-任务启用");
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }


    /**
     * 导出卡片
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/exportCard/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity exportCard(@PathVariable Long id, HttpServletResponse response, Authentication authentication, HttpServletRequest request) throws IOException {
        ServiceResponse result = customCardTaskService.exportCard(id, response);
        actionLogService.createActionLog(authentication, request, "卡片任务管理-导出卡片");
        return responseDeal(result);
    }


    /**
     * 完成制卡
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/finishCardTask/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity finishCardTask(@PathVariable Long id, Authentication authentication, HttpServletRequest request) {
        ServiceResponse result = customCardTaskService.finishCardTask(id);
        actionLogService.createActionLog(authentication, request, "卡片任务管理-完成制卡");
        return responseDeal(result);
    }

}
