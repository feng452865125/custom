package com.chunhe.custom.pc.controller;

import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.GroupInfo;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.service.FlowerHeadService;
import com.chunhe.custom.pc.service.GroupInfoService;
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
 * 花头管理
 */
@Controller
@RequestMapping("/flowerHead")
public class FlowerHeadController extends BaseController {

    @Autowired
    private FlowerHeadService flowerHeadService;

    @Autowired
    private GroupInfoService groupInfoService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('flowerHead:page')")
    public String flowerHeadList(Model model) {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setType(GroupInfo.GROUP_INFO_TYPE_FLOWER_HEAD);
        List<GroupInfo> groupTypeList = groupInfoService.findGroupInfoList(groupInfo);
        model.addAttribute("groupTypeList", groupTypeList);
        return "pages/flowerHead/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('flowerHead:list')")
    public DataTablesResponse<Parts> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Parts> data = flowerHeadService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        flowerHeadService.flowerHeadList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('flowerHead:add')")
    public String add(Model model) {
        return "pages/flowerHead/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('flowerHead:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = flowerHeadService.save(map);
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
    @PreAuthorize("hasAuthority('flowerHead:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = flowerHeadService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('flowerHead:edit')")
    public String edit(@PathVariable Long id, Model model) {
        Parts parts = flowerHeadService.getFlowerHead(id);
        model.addAttribute("parts", parts);
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setType(GroupInfo.GROUP_INFO_TYPE_FLOWER_HEAD);
        List<GroupInfo> groupTypeList = groupInfoService.findGroupInfoList(groupInfo);
        model.addAttribute("groupTypeList", groupTypeList);
        return "pages/flowerHead/edit";
    }

    /**
     * 修改
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('flowerHead:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = flowerHeadService.update(map);
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
    @PreAuthorize("hasAuthority('flowerHead:view')")
    public String view(@PathVariable Long id, Model model) {
        Parts parts = flowerHeadService.getFlowerHead(id);
        model.addAttribute("parts", parts);
        return "pages/flowerHead/view";
    }


}
