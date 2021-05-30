package com.chunhe.custom.pc.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.DadaJewelryType;
import com.chunhe.custom.pc.service.DadaJewelryTypeService;
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
 * Created by white 2018-9-5 14:49:47
 * dada系列的佩戴类别管理
 */
@Controller
@RequestMapping("/dadaJewelryType")
public class DadaJewelryTypeController extends BaseController {

    @Autowired
    private DadaJewelryTypeService dadaJewelryTypeService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('dadaJewelryType:page')")
    public String dadaJewelryTypeList(Model model) {
        return "pages/dadaJewelryType/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaJewelryType:list')")
    public DataTablesResponse<DadaJewelryType> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<DadaJewelryType> data = dadaJewelryTypeService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        dadaJewelryTypeService.dadaJewelryTypeList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('dadaJewelryType:add')")
    public String add(Model model) {
        return "pages/dadaJewelryType/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaJewelryType:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = dadaJewelryTypeService.save(map);
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
    @PreAuthorize("hasAuthority('dadaJewelryType:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = dadaJewelryTypeService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('dadaJewelryType:edit')")
    public String edit(@PathVariable Long id, Model model) {
        DadaJewelryType dadaJewelryType = dadaJewelryTypeService.getDadaJewelryType(id);
        model.addAttribute("dadaJewelryType", dadaJewelryType);
        return "pages/dadaJewelryType/edit";
    }

    /**
     * 修改
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaJewelryType:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = dadaJewelryTypeService.update(map);
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
    @PreAuthorize("hasAuthority('dadaJewelryType:view')")
    public String view(@PathVariable Long id, Model model) {
        DadaJewelryType dadaJewelryType = dadaJewelryTypeService.getDadaJewelryType(id);
        model.addAttribute("dadaJewelryType", dadaJewelryType);
        return "pages/dadaJewelryType/view";
    }


}
