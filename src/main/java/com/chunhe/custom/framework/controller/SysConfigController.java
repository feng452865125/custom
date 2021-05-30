package com.chunhe.custom.framework.controller;

import com.chunhe.custom.pc.service.ActionLogService;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.service.SysConfigService;
import com.github.pagehelper.ISelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(path = "/sysConfig")
public class SysConfigController extends BaseController {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private ActionLogService actionLogService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('sysConfig:page')")
    public String sysConfigList(Model model) {
        return "pages/sysConfig/list";
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('sysConfig:edit')")
    public String edit(@PathVariable Long id, Model model) {
        SysConfig sysConfig = sysConfigService.selectByKey(id);
        model.addAttribute("sysConfig", sysConfig);
        return "pages/sysConfig/edit";
    }


    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('sysConfig:list')")
    public DataTablesResponse<SysConfig> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<SysConfig> data = sysConfigService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        sysConfigService.sysConfigList(dataTablesRequest);
                    }
                });
        return data;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('sysConfig:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map, Authentication authentication, HttpServletRequest request) {
        map.put("id", id);
        SysConfig sysConfig = sysConfigService.selectByKey(id);
        String result = sysConfigService.update(sysConfig.getKey(), map, sysConfig);
        if (result.equals("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败");
        }
        actionLogService.createActionLog(authentication, request, "原：key=" + sysConfig.getKey() + "|value=" + sysConfig.getValue() + ", 新：" + map.toString());
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
    }
}
