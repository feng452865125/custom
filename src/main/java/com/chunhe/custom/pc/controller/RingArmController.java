package com.chunhe.custom.pc.controller;

import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.GroupInfo;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.service.GroupInfoService;
import com.chunhe.custom.pc.service.RingArmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2018年7月14日11:39:34
 * 戒臂管理
 */
@Controller
@RequestMapping("/ringArm")
public class RingArmController extends BaseController {

    @Autowired
    private RingArmService ringArmService;

    @Autowired
    private GroupInfoService groupInfoService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('ringArm:page')")
    public String ringArmList(Model model) {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setType(GroupInfo.GROUP_INFO_TYPE_RING_ARM);
        List<GroupInfo> groupTypeList = groupInfoService.findGroupInfoList(groupInfo);
        model.addAttribute("groupTypeList", groupTypeList);
        return "pages/ringArm/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('ringArm:list')")
    public DataTablesResponse<Parts> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Parts> data = ringArmService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        ringArmService.ringArmList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('ringArm:add')")
    public String add(Model model) {
        return "pages/ringArm/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('ringArm:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = ringArmService.save(map);
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
    @PreAuthorize("hasAuthority('ringArm:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = ringArmService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ringArm:edit')")
    public String edit(@PathVariable Long id, Model model) {
        Parts parts = ringArmService.getRingArm(id);
        model.addAttribute("parts", parts);
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setType(GroupInfo.GROUP_INFO_TYPE_RING_ARM);
        List<GroupInfo> groupTypeList = groupInfoService.findGroupInfoList(groupInfo);
        model.addAttribute("groupTypeList", groupTypeList);
        return "pages/ringArm/edit";
    }

    /**
     * 修改
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('ringArm:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = ringArmService.update(map);
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
    @PreAuthorize("hasAuthority('ringArm:view')")
    public String view(@PathVariable Long id, Model model) {
        Parts parts = ringArmService.getRingArm(id);
        model.addAttribute("parts", parts);
        return "pages/ringArm/view";
    }


}
