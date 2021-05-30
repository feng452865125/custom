package com.chunhe.custom.pc.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.security.PlatformUser;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.pc.model.CustomBlack;
import com.chunhe.custom.pc.service.ActionLogService;
import com.chunhe.custom.pc.service.CustomBlackService;
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
 * Created by white 2021年3月22日21:09:02
 * 黑名单管理
 */
@Controller
@RequestMapping("/customBlack")
public class CustomBlackController extends BaseController {

    @Autowired
    private CustomBlackService customBlackService;

    @Autowired
    private ActionLogService actionLogService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('customBlack:page')")
    public String customBlackList(Model model) {
        return "pages/customBlack/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('customBlack:list')")
    public DataTablesResponse<CustomBlack> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<CustomBlack> data = customBlackService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        customBlackService.customBlackList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('customBlack:add')")
    public String add(Model model) {
        return "pages/customBlack/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('customBlack:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map, Authentication authentication, HttpServletRequest request) {
        PlatformUser user = (PlatformUser) authentication.getPrincipal();
        map.put("blackCreateUser", user.getSysUser().getName());
        ServiceResponse result = customBlackService.save(map);
        String blackZsh = ConvertUtil.convert(map.get("blackZsh"), String.class);
        actionLogService.createActionLog(authentication, request, "黑名单管理-添加-" + blackZsh);
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
    @PreAuthorize("hasAuthority('customBlack:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = customBlackService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('customBlack:edit')")
    public String edit(@PathVariable Long id, Model model) {
        CustomBlack customBlack = customBlackService.getCustomBlack(id);
        model.addAttribute("customBlack", customBlack);
        return "pages/customBlack/edit";
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
    @PreAuthorize("hasAuthority('customBlack:edit')")
    public ResponseEntity update(@PathVariable long id, Authentication authentication, @RequestBody Map<String, Object> map, HttpServletRequest request) {
        map.put("id", id);
        PlatformUser user = (PlatformUser) authentication.getPrincipal();
        map.put("blackCreateUser", user.getSysUser().getName());
        ServiceResponse result = customBlackService.update(map);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        String blackZsh = ConvertUtil.convert(map.get("blackZsh"), String.class);
        actionLogService.createActionLog(authentication, request, "黑名单管理-编辑-" + blackZsh);
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
    @PreAuthorize("hasAuthority('customBlack:view')")
    public String view(@PathVariable Long id, Model model) {
        CustomBlack customBlack = customBlackService.getCustomBlack(id);
        model.addAttribute("customBlack", customBlack);
        return "pages/customBlack/view";
    }

    /**
     * 禁用
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('customBlack:edit')")
    @RequestMapping(value = "/{id}/disabled", method = RequestMethod.PATCH)
    public ResponseEntity<String> disabled(@PathVariable long id, Authentication authentication, HttpServletRequest request) {
        if (customBlackService.blackEnabled(id, false)) {
            actionLogService.createActionLog(authentication, request, "黑名单管理-禁用-id:" + id);
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
    @PreAuthorize("hasAuthority('customBlack:edit')")
    @RequestMapping(value = "/{id}/enabled", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<String> enabled(@PathVariable Long id, Authentication authentication, HttpServletRequest request) {
        if (customBlackService.blackEnabled(id, true)) {
            actionLogService.createActionLog(authentication, request, "黑名单管理-启用-id:" + id);
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }


    /**
     * 批量操作，导入文件
     *
     * @param file
     * @param authentication
     * @param request
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('customBlack:add')")
    public ResponseEntity customBlackImport(@RequestParam("file") MultipartFile file,
                                            Authentication authentication, HttpServletRequest request) {
        PlatformUser user = (PlatformUser) authentication.getPrincipal();
        ServiceResponse result = customBlackService.customBlackImport(file, user.getSysUser().getName());
        actionLogService.createActionLog(authentication, request, "黑名单管理-导入文件");
        return responseDeal(result);
    }


    /**
     * 导出
     *
     * @return
     */
    @PreAuthorize("hasAuthority('customBlack:list')")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity customBlackExport(HttpServletResponse response, Authentication authentication, HttpServletRequest request) throws IOException {
        ServiceResponse result = customBlackService.customBlackExport(response);
        actionLogService.createActionLog(authentication, request, "黑名单管理-导出文件");
        return responseDeal(result);
    }


}
