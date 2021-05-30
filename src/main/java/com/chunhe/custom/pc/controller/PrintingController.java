package com.chunhe.custom.pc.controller;

import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.Printing;
import com.chunhe.custom.pc.service.PrintingService;
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
 * Created by white 2018年7月12日14:48:10
 * 系列管理
 */
@Controller
@RequestMapping("/printing")
public class PrintingController extends BaseController {

    @Autowired
    private PrintingService printingService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('printing:page')")
    public String printingList(Model model) {
        return "pages/printing/list";
    }

    /**
     * 列表
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('printing:list')")
    public DataTablesResponse<Printing> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Printing> data = printingService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        printingService.printingList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('printing:add')")
    public String add(Model model) {
        return "pages/printing/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('printing:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = printingService.save(map);
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
    @PreAuthorize("hasAuthority('printing:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = printingService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('printing:edit')")
    public String edit(@PathVariable Long id, Model model) {
        Printing printing = printingService.getPrinting(id);
        model.addAttribute("printing", printing);
        return "pages/printing/edit";
    }

    /**
     * 修改
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('printing:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = printingService.update(map);
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
    @PreAuthorize("hasAuthority('printing:view')")
    public String view(@PathVariable Long id, Model model) {
        Printing printing = printingService.getPrinting(id);
        model.addAttribute("printing", printing);
        return "pages/printing/view";
    }



}
