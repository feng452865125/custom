package com.chunhe.custom.pc.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.pc.model.DadaJewelryType;
import com.chunhe.custom.pc.model.DadaParts;
import com.chunhe.custom.pc.model.DadaSeries;
import com.chunhe.custom.pc.model.DadaStyle;
import com.chunhe.custom.pc.service.*;
import com.chunhe.custom.pc.service.*;
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
 * dada系列管理
 */
@Controller
@RequestMapping("/dadaStyle")
public class DadaStyleController extends BaseController {

    @Autowired
    private DadaStyleService dadaStyleService;

    @Autowired
    private DadaSeriesService dadaSeriesService;

    @Autowired
    private DadaJewelryTypeService dadaJewelryTypeService;

    @Autowired
    private DadaRelStylePartsService dadaRelStylePartsService;

    @Autowired
    private DadaPartsService dadaPartsService;


    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('dadaStyle:page')")
    public String styleList(Model model) {
        List<DadaSeries> seriesList =  dadaSeriesService.findDadaSeriesList(new DadaSeries());
        model.addAttribute("seriesList", seriesList);
        List<DadaJewelryType> typeList =  dadaJewelryTypeService.findDadaJewelryTypeList(new DadaJewelryType());
        model.addAttribute("typeList", typeList);
        return "pages/dadaStyle/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaStyle:list')")
    public DataTablesResponse<DadaStyle> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<DadaStyle> data = dadaStyleService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        dadaStyleService.dadaStyleList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('dadaStyle:add')")
    public String add(Model model) {
        List<DadaSeries> seriesList =  dadaSeriesService.findDadaSeriesList(new DadaSeries());
        model.addAttribute("seriesList", seriesList);
        List<DadaJewelryType> typeList =  dadaJewelryTypeService.findDadaJewelryTypeList(new DadaJewelryType());
        model.addAttribute("typeList", typeList);
        return "pages/dadaStyle/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaStyle:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = dadaStyleService.save(map);
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
    @PreAuthorize("hasAuthority('dadaStyle:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = dadaStyleService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('dadaStyle:edit')")
    public String edit(@PathVariable Long id, Model model) {
        DadaStyle dadaStyle = dadaStyleService.getDadaStyle(id);
        model.addAttribute("dadaStyle", dadaStyle);
        List<DadaSeries> seriesList =  dadaSeriesService.findDadaSeriesList(new DadaSeries());
        model.addAttribute("seriesList", seriesList);
        List<DadaJewelryType> typeList =  dadaJewelryTypeService.findDadaJewelryTypeList(new DadaJewelryType());
        model.addAttribute("typeList", typeList);
        return "pages/dadaStyle/edit";
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
    @PreAuthorize("hasAuthority('dadaStyle:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = dadaStyleService.update(map);
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
    @PreAuthorize("hasAuthority('dadaStyle:view')")
    public String view(@PathVariable Long id, Model model) {
        DadaStyle dadaStyle = dadaStyleService.getDadaStyle(id);
        model.addAttribute("dadaStyle", dadaStyle);
        List<DadaSeries> seriesList =  dadaSeriesService.findDadaSeriesList(new DadaSeries());
        model.addAttribute("seriesList", seriesList);
        List<DadaJewelryType> typeList =  dadaJewelryTypeService.findDadaJewelryTypeList(new DadaJewelryType());
        model.addAttribute("typeList", typeList);
        return "pages/dadaStyle/view";
    }

    /**
     * dadaStyle
     * view，维护详情的数据（轮播图，视频，试戴图，绑定的组件）
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('dadaStyle:edit')")
    public ResponseEntity detailUpdate(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = dadaStyleService.detailUpdate(map);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
    }

    /**
     * 检查编码是否被使用
     */
    @RequestMapping(value = "/checkCode", method = RequestMethod.GET)
    @ResponseBody
    public String checkCode(@RequestParam Map<String, Object> map){
        String code = ConvertUtil.convert(map.get("code"), String.class);
        if(code != null){
            boolean isExistCode = dadaStyleService.isExistByParam("code", code);
            if(isExistCode){
                return "编码已存在";
            }
        }
        return null;
    }


    /**
     * dada定制style-parts组装时的查询
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping("/parts/select/{id}")
    @ResponseBody
    public DataTablesResponse<DadaParts> select(@PathVariable final Long id, @Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<DadaParts> data = dadaPartsService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        dadaRelStylePartsService.findDadaRelStyleParts(dataTablesRequest, id);
                    }
                });
        return data;
    }

    /**
     * 关联
     * @param map
     * @return
     */
    @RequestMapping(value= "/parts/enabled", method = RequestMethod.PATCH)
    @ResponseBody
    public String enabled(@RequestBody Map<String, Object> map) {
        dadaStyleService.createDadaRelStyleParts(map);
        return "";
    }

    /**
     * 取消关联
     * @param map
     * @return
     */
    @RequestMapping(value= "/parts/disabled", method = RequestMethod.PATCH)
    @ResponseBody
    public String disabled(@RequestBody Map<String, Object> map) {
        dadaStyleService.deleteDadaRelStyleParts(map);
        return "";
    }
}
