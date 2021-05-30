package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.DiamondKela;
import com.chunhe.custom.pc.service.DiamondKelaService;
import com.chunhe.custom.utils.DictUtils;
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
 * Created by white 2018-11-21 14:44:58
 * 新系列--克拉展
 */
@Controller
@RequestMapping("/diamondKela")
public class DiamondKelaController extends BaseController {

    @Autowired
    private DiamondKelaService diamondKelaService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('diamondKela:page')")
    public String diamondKelaList(Model model) {
        JSONObject statusList = DictUtils.findDicByType(DiamondKela.DIAMOND_KELA_STATUS);
        model.addAttribute("statusList", statusList);
        return "pages/diamondKela/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('diamondKela:list')")
    public DataTablesResponse<DiamondKela> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<DiamondKela> data = diamondKelaService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        diamondKelaService.diamondKelaList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('diamondKela:add')")
    public String add(Model model) {
        JSONObject statusList = DictUtils.findDicByType(DiamondKela.DIAMOND_KELA_STATUS);
        model.addAttribute("statusList", statusList);
        JSONObject qgList = DictUtils.findDicByType(DiamondKela.DIAMOND_KELA_QG);
        model.addAttribute("qgList", qgList);
        JSONObject pgList = DictUtils.findDicByType(DiamondKela.DIAMOND_KELA_PG);
        model.addAttribute("pgList", pgList);
        JSONObject dcList = DictUtils.findDicByType(DiamondKela.DIAMOND_KELA_DC);
        model.addAttribute("dcList", dcList);
        JSONObject ygList = DictUtils.findDicByType(DiamondKela.DIAMOND_KELA_YG);
        model.addAttribute("ygList", ygList);
        return "pages/diamondKela/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('diamondKela:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = diamondKelaService.save(map);
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
    @PreAuthorize("hasAuthority('diamondKela:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = diamondKelaService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('diamondKela:edit')")
    public String edit(@PathVariable Long id, Model model) {
        JSONObject statusList = DictUtils.findDicByType(DiamondKela.DIAMOND_KELA_STATUS);
        model.addAttribute("statusList", statusList);
        JSONObject qgList = DictUtils.findDicByType(DiamondKela.DIAMOND_KELA_QG);
        model.addAttribute("qgList", qgList);
        JSONObject pgList = DictUtils.findDicByType(DiamondKela.DIAMOND_KELA_PG);
        model.addAttribute("pgList", pgList);
        JSONObject dcList = DictUtils.findDicByType(DiamondKela.DIAMOND_KELA_DC);
        model.addAttribute("dcList", dcList);
        JSONObject ygList = DictUtils.findDicByType(DiamondKela.DIAMOND_KELA_YG);
        model.addAttribute("ygList", ygList);
        DiamondKela diamondKela = diamondKelaService.getDiamondKela(id);
        model.addAttribute("diamondKela", diamondKela);
        return "pages/diamondKela/edit";
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
    @PreAuthorize("hasAuthority('diamondKela:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = diamondKelaService.update(map);
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
    @PreAuthorize("hasAuthority('diamondKela:view')")
    public String view(@PathVariable Long id, Model model) {
        DiamondKela diamondKela = diamondKelaService.getDiamondKela(id);
        model.addAttribute("diamondKela", diamondKela);
        return "pages/diamondKela/view";
    }

}
