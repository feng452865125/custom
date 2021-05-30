package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.utils.DictUtils;
import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.GroupInfo;
import com.chunhe.custom.pc.service.GroupInfoService;
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
 * Created by white 2018年7月13日11:33:17
 * 组信息维护
 */
@Controller
@RequestMapping("/groupInfo")
public class GroupInfoController extends BaseController {

    @Autowired
    private GroupInfoService groupInfoService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('groupInfo:page')")
    public String groupInfoList(Model model) {
        JSONObject typeList = DictUtils.findDicByType(GroupInfo.GROUP_INFO_TYPE);
        model.addAttribute("typeList", typeList);
        return "pages/groupInfo/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('groupInfo:list')")
    public DataTablesResponse<GroupInfo> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<GroupInfo> data = groupInfoService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        groupInfoService.groupInfoList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('groupInfo:add')")
    public String add(Model model) {
        JSONObject typeList = DictUtils.findDicByType(GroupInfo.GROUP_INFO_TYPE);
        model.addAttribute("typeList", typeList);
        return "pages/groupInfo/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('groupInfo:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = groupInfoService.save(map);
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
    @PreAuthorize("hasAuthority('groupInfo:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = groupInfoService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('groupInfo:edit')")
    public String edit(@PathVariable Long id, Model model) {
        GroupInfo groupInfo = groupInfoService.getGroupInfo(id);
        model.addAttribute("groupInfo", groupInfo);
        JSONObject typeList = DictUtils.findDicByType(GroupInfo.GROUP_INFO_TYPE);
        model.addAttribute("typeList", typeList);
        return "pages/groupInfo/edit";
    }

    /**
     * 修改
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('groupInfo:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = groupInfoService.update(map);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
    }

    /**
     * 查询
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('groupInfo:view')")
    public String view(@PathVariable Long id, Model model) {
        GroupInfo groupInfo = groupInfoService.getGroupInfo(id);
        model.addAttribute("groupInfo", groupInfo);
        return "pages/groupInfo/view";
    }



}
