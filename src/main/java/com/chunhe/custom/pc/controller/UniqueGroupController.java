package com.chunhe.custom.pc.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.UniqueGroup;
import com.chunhe.custom.pc.service.UniqueGroupService;
import com.github.pagehelper.ISelect;
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
 * Created by white 2018-8-24 13:58:51
 * unique类别管理
 */
@Controller
@RequestMapping("/uniqueGroup")
public class UniqueGroupController extends BaseController {

    @Autowired
    private UniqueGroupService uniqueGroupService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('uniqueGroup:page')")
    public String uniqueGroupList(Model model) {
        return "pages/uniqueGroup/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('uniqueGroup:list')")
    public DataTablesResponse<UniqueGroup> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<UniqueGroup> data = uniqueGroupService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        uniqueGroupService.uniqueGroupList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('uniqueGroup:add')")
    public String add(Model model) {
        return "pages/uniqueGroup/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('uniqueGroup:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = uniqueGroupService.save(map);
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
    @PreAuthorize("hasAuthority('uniqueGroup:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = uniqueGroupService.deleteById(id);
        return responseDeal(result);
    }


    /**
     * 编辑自身数据
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('uniqueGroup:edit')")
    public String edit(@PathVariable Long id, Model model) {
        UniqueGroup uniqueGroup = uniqueGroupService.getUniqueGroup(id);
        model.addAttribute("uniqueGroup", uniqueGroup);
        return "pages/uniqueGroup/edit";
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
    @PreAuthorize("hasAuthority('uniqueGroup:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = uniqueGroupService.update(map);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
    }

    /**
     * 编辑详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('uniqueGroup:view')")
    public String view(@PathVariable Long id, Model model) {
        UniqueGroup uniqueGroup = uniqueGroupService.getUniqueGroup(id);
        model.addAttribute("uniqueGroup", uniqueGroup);
        return "pages/uniqueGroup/view";
    }

    /**
     * 禁用
     * @param id
     * @return
     */
    @RequestMapping(value= "/{id}/disabled", method = RequestMethod.PATCH)
    public ResponseEntity<String> disabled(@PathVariable long id) {
        if(uniqueGroupService.enabled(id, UniqueGroup.ENABLED_FALSE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }

    /**
     * 启用
     * @param id
     * @return
     */
    @RequestMapping(value= "/{id}/enabled", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<String> enabled(@PathVariable Long id, Model model) {
        if(uniqueGroupService.enabled(id, UniqueGroup.ENABLED_TRUE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }


}
