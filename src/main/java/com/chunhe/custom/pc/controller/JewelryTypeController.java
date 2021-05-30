package com.chunhe.custom.pc.controller;

import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.JewelryType;
import com.chunhe.custom.pc.service.JewelryTypeService;
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
 * Created by white 2018年7月12日11:19:19
 * 系列管理
 */
@Controller
@RequestMapping("/jewelryType")
public class JewelryTypeController extends BaseController {

    @Autowired
    private JewelryTypeService jewelryTypeService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('jewelryType:page')")
    public String jewelryTypeList(Model model) {
        return "pages/jewelryType/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('jewelryType:list')")
    public DataTablesResponse<JewelryType> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<JewelryType> data = jewelryTypeService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        jewelryTypeService.jewelryTypeList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('jewelryType:add')")
    public String add(Model model) {
        return "pages/jewelryType/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('jewelryType:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = jewelryTypeService.save(map);
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
    @PreAuthorize("hasAuthority('jewelryType:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = jewelryTypeService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('jewelryType:edit')")
    public String edit(@PathVariable Long id, Model model) {
        JewelryType jewelryType = jewelryTypeService.getJewelryType(id);
        model.addAttribute("jewelryType", jewelryType);
        return "pages/jewelryType/edit";
    }

    /**
     * 修改
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('jewelryType:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = jewelryTypeService.update(map);
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
    @PreAuthorize("hasAuthority('jewelryType:view')")
    public String view(@PathVariable Long id, Model model) {
        JewelryType jewelryType = jewelryTypeService.getJewelryType(id);
        model.addAttribute("jewelryType", jewelryType);
        return "pages/jewelryType/view";
    }


}
