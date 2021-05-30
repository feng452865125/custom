package com.chunhe.custom.pc.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.DadaJewelryType;
import com.chunhe.custom.pc.model.DadaParts;
import com.chunhe.custom.pc.service.DadaJewelryTypeService;
import com.chunhe.custom.pc.service.DadaPartsService;
import com.github.pagehelper.ISelect;
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
 * Created by white 2018-9-5 14:49:47
 * dada系列的组件管理
 */
@Controller
@RequestMapping("/dadaParts")
public class DadaPartsController extends BaseController {

    @Autowired
    private DadaJewelryTypeService dadaJewelryTypeService;
    
    @Autowired
    private DadaPartsService dadaPartsService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('dadaParts:page')")
    public String dadaPartsList(Model model) {
        List<DadaJewelryType> dadaJewelryTypeList = dadaJewelryTypeService.findDadaJewelryTypeList(new DadaJewelryType());
        model.addAttribute("dadaJewelryTypeList", dadaJewelryTypeList);
        return "pages/dadaParts/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaParts:list')")
    public DataTablesResponse<DadaParts> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<DadaParts> data = dadaPartsService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        dadaPartsService.dadaPartsList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('dadaParts:add')")
    public String add(Model model) {
        return "pages/dadaParts/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaParts:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = dadaPartsService.save(map);
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
    @PreAuthorize("hasAuthority('dadaParts:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = dadaPartsService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('dadaParts:edit')")
    public String edit(@PathVariable Long id, Model model) {
        DadaParts dadaParts = dadaPartsService.getDadaParts(id);
        model.addAttribute("dadaParts", dadaParts);
        return "pages/dadaParts/edit";
    }

    /**
     * 修改
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaParts:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = dadaPartsService.update(map);
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
    @PreAuthorize("hasAuthority('dadaParts:view')")
    public String view(@PathVariable Long id, Model model) {
        DadaParts dadaParts = dadaPartsService.getDadaParts(id);
        model.addAttribute("dadaParts", dadaParts);
        return "pages/dadaParts/view";
    }


}
