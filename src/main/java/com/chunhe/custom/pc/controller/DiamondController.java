package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.service.DiamondService;
import com.chunhe.custom.pc.service.GroupInfoService;
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
 * Created by white 2018-8-7 11:44:58
 * 钻石维护
 */
@Controller
@RequestMapping("/diamond")
public class DiamondController extends BaseController {

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private GroupInfoService groupInfoService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('diamond:page')")
    public String diamondList(Model model) {
        JSONObject statusList = DictUtils.findDicByType(Parts.DIAMOND_STATUS);
        model.addAttribute("statusList", statusList);
        return "pages/diamond/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('diamond:list')")
    public DataTablesResponse<Parts> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Parts> data = diamondService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        diamondService.diamondList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('diamond:add')")
    public String add(Model model) {
        return "pages/diamond/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('diamond:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = diamondService.save(map);
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
    @PreAuthorize("hasAuthority('diamond:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = diamondService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('diamond:edit')")
    public String edit(@PathVariable Long id, Model model) {
        Parts parts = diamondService.getDiamond(id);
        model.addAttribute("parts", parts);
        JSONObject statusList = DictUtils.findDicByType(Parts.DIAMOND_STATUS);
        model.addAttribute("statusList", statusList);
        return "pages/diamond/edit";
    }

    /**
     * 修改
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('diamond:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = diamondService.update(map);
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
    @PreAuthorize("hasAuthority('diamond:view')")
    public String view(@PathVariable Long id, Model model) {
        Parts parts = diamondService.getDiamond(id);
        model.addAttribute("parts", parts);
        return "pages/diamond/view";
    }


}
