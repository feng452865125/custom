package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.BasePrice;
import com.chunhe.custom.pc.service.ActionLogService;
import com.chunhe.custom.pc.service.BasePriceService;
import com.chunhe.custom.utils.DictUtils;
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
import java.util.Map;


/**
 * Created by white 2019年4月18日17:04:37
 * 4C标准基价
 */
@Controller
@RequestMapping("/basePrice")
public class BasePriceController extends BaseController {

    @Autowired
    private BasePriceService basePriceService;

    @Autowired
    private ActionLogService actionLogService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('basePrice:page')")
    public String basePriceList(Model model) {
        JSONObject ysList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YS);
        model.addAttribute("ysList", ysList);
        JSONObject jdList = DictUtils.findDicByType(BasePrice.BASE_PRICE_JD);
        model.addAttribute("jdList", jdList);
        JSONObject qgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_QG);
        model.addAttribute("qgList", qgList);
        JSONObject pgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_PG);
        model.addAttribute("pgList", pgList);
        JSONObject dcList = DictUtils.findDicByType(BasePrice.BASE_PRICE_DC);
        model.addAttribute("dcList", dcList);
        JSONObject ygList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YG);
        model.addAttribute("ygList", ygList);
        return "pages/basePrice/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('basePrice:list')")
    public DataTablesResponse<BasePrice> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<BasePrice> data = basePriceService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        basePriceService.basePriceList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('basePrice:add')")
    public String add(Model model) {
        JSONObject ysList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YS);
        model.addAttribute("ysList", ysList);
        JSONObject jdList = DictUtils.findDicByType(BasePrice.BASE_PRICE_JD);
        model.addAttribute("jdList", jdList);
        JSONObject qgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_QG);
        model.addAttribute("qgList", qgList);
        JSONObject pgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_PG);
        model.addAttribute("pgList", pgList);
        JSONObject dcList = DictUtils.findDicByType(BasePrice.BASE_PRICE_DC);
        model.addAttribute("dcList", dcList);
        JSONObject ygList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YG);
        model.addAttribute("ygList", ygList);
        JSONObject zsList = DictUtils.findDicByType(BasePrice.BASE_PRICE_ZS);
        model.addAttribute("zsList", zsList);
        return "pages/basePrice/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('basePrice:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = basePriceService.save(map);
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
    @PreAuthorize("hasAuthority('basePrice:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = basePriceService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('basePrice:edit')")
    public String edit(@PathVariable Long id, Model model) {
        BasePrice basePrice = basePriceService.getBasePrice(id);
        model.addAttribute("basePrice", basePrice);
        return "pages/basePrice/edit";
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
    @PreAuthorize("hasAuthority('basePrice:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = basePriceService.update(map);
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
    @PreAuthorize("hasAuthority('basePrice:view')")
    public String view(@PathVariable Long id, Model model) {
        BasePrice basePrice = basePriceService.getBasePrice(id);
        model.addAttribute("basePrice", basePrice);
        return "pages/basePrice/view";
    }

    /**
     * 禁用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/disabled", method = RequestMethod.PATCH)
    public ResponseEntity<String> disabled(@PathVariable long id) {
        if (basePriceService.enabled(id, BasePrice.ENABLED_FALSE)) {
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
        if (basePriceService.enabled(id, BasePrice.ENABLED_TRUE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }


    /**
     * 批量导入
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('basePrice:add')")
    public ResponseEntity importPartsBig(@RequestParam("file") MultipartFile file, Authentication authentication, HttpServletRequest request) {
        ServiceResponse result = basePriceService.importBasePrice(file);
        actionLogService.createActionLog(authentication, request, "批量导入修改4C标准基价");
        return responseDeal(result);
    }

}
