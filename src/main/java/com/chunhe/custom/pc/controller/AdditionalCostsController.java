package com.chunhe.custom.pc.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.pc.model.AdditionalCosts;
import com.chunhe.custom.pc.service.AdditionalCostsService;
import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
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
@RequestMapping("/additionalCosts")
public class AdditionalCostsController extends BaseController {

    @Autowired
    private AdditionalCostsService additionalCostsService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('additionalCosts:page')")
    public String additionalCostsList(Model model) {
        return "pages/additionalCosts/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('additionalCosts:list')")
    public DataTablesResponse<AdditionalCosts> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<AdditionalCosts> data = additionalCostsService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        additionalCostsService.additionalCostsList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('additionalCosts:add')")
    public String add(Model model) {
        return "pages/additionalCosts/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('additionalCosts:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = additionalCostsService.save(map);
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
    @PreAuthorize("hasAuthority('additionalCosts:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = additionalCostsService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('additionalCosts:edit')")
    public String edit(@PathVariable Long id, Model model) {
        AdditionalCosts additionalCosts = additionalCostsService.getAdditionalCosts(id);
        model.addAttribute("additionalCosts", additionalCosts);
        return "pages/additionalCosts/edit";
    }

    /**
     * 修改
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('additionalCosts:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = additionalCostsService.update(map);
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
    @PreAuthorize("hasAuthority('additionalCosts:view')")
    public String view(@PathVariable Long id, Model model) {
        AdditionalCosts additionalCosts = additionalCostsService.getAdditionalCosts(id);
        model.addAttribute("additionalCosts", additionalCosts);
        return "pages/additionalCosts/view";
    }


}
