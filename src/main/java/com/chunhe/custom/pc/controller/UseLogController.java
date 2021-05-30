package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.utils.DictUtils;
import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.UseLog;
import com.chunhe.custom.pc.service.UseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by white 2018年7月12日14:48:10
 * 日志管理
 */
@Controller
@RequestMapping("/useLog")
public class UseLogController extends BaseController {

    @Autowired
    private UseLogService useLogService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('useLog:page')")
    public String useLogList(Model model) {
        JSONObject useType = DictUtils.findDicByType(UseLog.ACTION_TYPE);
        model.addAttribute("useType", useType);
        return "pages/useLog/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('useLog:list')")
    public DataTablesResponse<UseLog> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<UseLog> data = useLogService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        useLogService.useLogList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('useLog:add')")
    public String add(Model model) {
        return "pages/useLog/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('useLog:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = useLogService.save(map);
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
    @PreAuthorize("hasAuthority('useLog:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = useLogService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('useLog:edit')")
    public String edit(@PathVariable Long id, Model model) {
        UseLog useLog = useLogService.getUseLog(id);
        model.addAttribute("useLog", useLog);
        return "pages/useLog/edit";
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
    @PreAuthorize("hasAuthority('useLog:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = useLogService.update(map);
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
    @PreAuthorize("hasAuthority('useLog:view')")
    public String view(@PathVariable Long id, Model model) {
        UseLog useLog = useLogService.getUseLog(id);
        model.addAttribute("useLog", useLog);
        return "pages/useLog/view";
    }


}
