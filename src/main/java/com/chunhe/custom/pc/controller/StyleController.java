package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSON;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.UploadService;
import com.chunhe.custom.framework.utils.TransformStringListUtil;
import com.chunhe.custom.pc.model.JewelryType;
import com.chunhe.custom.pc.model.Series;
import com.chunhe.custom.pc.model.Style;
import com.chunhe.custom.pc.model.UniqueGroup;
import com.chunhe.custom.pc.service.ActionLogService;
import com.chunhe.custom.pc.service.JewelryTypeService;
import com.chunhe.custom.pc.service.SeriesService;
import com.chunhe.custom.pc.service.StyleService;
import com.github.pagehelper.ISelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2018-7-16 10:08:48
 * 样式维护
 */
@Controller
@RequestMapping("/style")
public class StyleController extends BaseController {

    @Autowired
    private StyleService styleService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private JewelryTypeService jewelryTypeService;

    @Autowired
    private ActionLogService actionLogService;

    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('style:page')")
    public String styleList(Model model) {
        List<Series> seriesList = seriesService.findSeriesList(new Series());
        model.addAttribute("seriesList", seriesList);
        List<JewelryType> typeList = jewelryTypeService.findJewelryTypeList(new JewelryType());
        model.addAttribute("typeList", typeList);
        return "pages/style/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('style:list')")
    public DataTablesResponse<Style> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Style> data = styleService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        styleService.styleList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('style:add')")
    public String add(Model model) {
        List<JewelryType> typeList = jewelryTypeService.findJewelryTypeList(new JewelryType());
        model.addAttribute("typeList", typeList);
        return "pages/style/add";
    }

    /**
     * 增加
     *
     * @param file
     * @param mapString
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('style:add')")
    public ResponseEntity save(@RequestParam("file") MultipartFile file, @RequestParam("map") String mapString,
                               Authentication authentication, HttpServletRequest request) {
        Map<String, Object> map = JSON.parseObject(mapString, Map.class);
        ServiceResponse result = styleService.save(map, file);
        String filePath = uploadService.uploadFile(request, file, "fileStyleProduct");
        actionLogService.createActionLog(authentication, request, "新增款式，关联主数据原文件路径=" + filePath);
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
    @PreAuthorize("hasAuthority('style:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = styleService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('style:edit')")
    public String edit(@PathVariable Long id, Model model) {
        Style style = styleService.getStyle(id);
        List list = TransformStringListUtil.StringToList(style.getImgsUrl());
        style.setImgsUrlList(list);
        //情侣戒特有的男女戒信息处理
        styleService.dealWithCoupleStyle(style);
        model.addAttribute("style", style);
        List<JewelryType> typeList = jewelryTypeService.findJewelryTypeList(new JewelryType());
        model.addAttribute("typeList", typeList);
        return "pages/style/edit";
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
    @PreAuthorize("hasAuthority('style:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = styleService.update(map);
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
    @PreAuthorize("hasAuthority('style:view')")
    public String view(@PathVariable Long id, Model model) {
        Style style = styleService.getStyle(id);
        List list = TransformStringListUtil.StringToList(style.getImgsUrl());
        style.setImgsUrlList(list);
        //情侣戒特有的男女戒信息处理
        styleService.dealWithCoupleStyle(style);
        model.addAttribute("style", style);
        return "pages/style/view";
    }

    /**
     * 禁用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/disabled", method = RequestMethod.PATCH)
    public ResponseEntity<String> disabled(@PathVariable long id) {
        if (styleService.enabled(id, UniqueGroup.ENABLED_FALSE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }

    /**
     * 启用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/enabled", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<String> enabled(@PathVariable Long id, Model model) {
        if (styleService.enabled(id, UniqueGroup.ENABLED_TRUE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }

    /**
     * 下拉列表
     *
     * @param name
     * @return
     */
    @GetMapping("/dropDownBox")
    @ResponseBody
    public List<Style> dropDownBox(String name, String series) {
        return styleService.dropDownBox(name, series);
    }
}
