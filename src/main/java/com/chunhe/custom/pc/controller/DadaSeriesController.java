package com.chunhe.custom.pc.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.DadaSeries;
import com.chunhe.custom.pc.model.Series;
import com.chunhe.custom.pc.service.DadaSeriesService;
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
 * dada系列的系列管理
 */
@Controller
@RequestMapping("/dadaSeries")
public class DadaSeriesController extends BaseController {

    @Autowired
    private DadaSeriesService dadaSeriesService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('dadaSeries:page')")
    public String seriesList(Model model) {
        return "pages/dadaSeries/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaSeries:list')")
    public DataTablesResponse<DadaSeries> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<DadaSeries> data = dadaSeriesService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        dadaSeriesService.dadaSeriesList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('dadaSeries:add')")
    public String add(Model model) {
        return "pages/dadaSeries/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaSeries:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = dadaSeriesService.save(map);
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
    @PreAuthorize("hasAuthority('dadaSeries:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = dadaSeriesService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('dadaSeries:edit')")
    public String edit(@PathVariable Long id, Model model) {
        DadaSeries dadaSeries = dadaSeriesService.getDadaSeries(id);
        model.addAttribute("dadaSeries", dadaSeries);
        return "pages/dadaSeries/edit";
    }

    /**
     * 修改
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaSeries:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = dadaSeriesService.update(map);
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
    @PreAuthorize("hasAuthority('dadaSeries:view')")
    public String view(@PathVariable Long id, Model model) {
        DadaSeries dadaSeries = dadaSeriesService.getDadaSeries(id);
        model.addAttribute("dadaSeries", dadaSeries);
        return "pages/dadaSeries/view";
    }

    /**
     * 禁用
     * @param id
     * @return
     */
    @RequestMapping(value= "/{id}/disabled", method = RequestMethod.PATCH)
    public ResponseEntity<String> disabled(@PathVariable long id) {
        if(dadaSeriesService.enabled(id, Series.ENABLED_FALSE)) {
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
        if(dadaSeriesService.enabled(id, Series.ENABLED_TRUE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }


}
