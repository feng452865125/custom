package com.chunhe.custom.pc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.service.UploadService;
import com.chunhe.custom.pc.model.BasePrice;
import com.chunhe.custom.pc.model.PartsBig;
import com.chunhe.custom.pc.service.ActionLogService;
import com.chunhe.custom.pc.service.PartsBigService;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.response.ServiceResponse;
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
import java.util.List;
import java.util.Map;

/**
 * Created by white 2020年5月22日00:09:33
 * 大克拉的钻石
 */
@Controller
@RequestMapping("/partsBig")
public class PartsBigController extends BaseController {

    @Autowired
    private PartsBigService partsBigService;

    @Autowired
    private ActionLogService actionLogService;

    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('partsBig:page')")
    public String partsBigList(Model model) {
        List companyList = partsBigService.findCompanyList();
        model.addAttribute("companyList", companyList);
        return "pages/partsBig/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('partsBig:list')")
    public DataTablesResponse<PartsBig> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<PartsBig> data = partsBigService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        partsBigService.partsBigList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('partsBig:add')")
    public String add(Model model) {
        JSONObject zsYsList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YS);
        JSONObject zsJdList = DictUtils.findDicByType(BasePrice.BASE_PRICE_JD);
        JSONObject zsQgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_QG);
        JSONObject zsPgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_PG);
        JSONObject zsDcList = DictUtils.findDicByType(BasePrice.BASE_PRICE_DC);
        JSONObject zsYgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YG);
        model.addAttribute("zsYsList", zsYsList);
        model.addAttribute("zsJdList", zsJdList);
        model.addAttribute("zsQgList", zsQgList);
        model.addAttribute("zsPgList", zsPgList);
        model.addAttribute("zsDcList", zsDcList);
        model.addAttribute("zsYgList", zsYgList);
        return "pages/partsBig/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('partsBig:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = partsBigService.save(map);
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
    @PreAuthorize("hasAuthority('partsBig:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = partsBigService.deleteById(id);
        return responseDeal(result);
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('partsBig:edit')")
    public String edit(@PathVariable Long id, Model model) {
        PartsBig partsBig = partsBigService.getPartsBig(id);
        model.addAttribute("partsBig", partsBig);
        JSONObject zsYsList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YS);
        JSONObject zsJdList = DictUtils.findDicByType(BasePrice.BASE_PRICE_JD);
        JSONObject zsQgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_QG);
        JSONObject zsPgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_PG);
        JSONObject zsDcList = DictUtils.findDicByType(BasePrice.BASE_PRICE_DC);
        JSONObject zsYgList = DictUtils.findDicByType(BasePrice.BASE_PRICE_YG);
        model.addAttribute("zsYsList", zsYsList);
        model.addAttribute("zsJdList", zsJdList);
        model.addAttribute("zsQgList", zsQgList);
        model.addAttribute("zsPgList", zsPgList);
        model.addAttribute("zsDcList", zsDcList);
        model.addAttribute("zsYgList", zsYgList);
        return "pages/partsBig/edit";
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
    @PreAuthorize("hasAuthority('partsBig:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = partsBigService.update(map);
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
    @PreAuthorize("hasAuthority('partsBig:view')")
    public String view(@PathVariable Long id, Model model) {
        PartsBig partsBig = partsBigService.getPartsBig(id);
        model.addAttribute("partsBig", partsBig);
        return "pages/partsBig/view";
    }

    /**
     * 禁用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/disabled", method = RequestMethod.PATCH)
    public ResponseEntity<String> disabled(@PathVariable long id) {
        if (partsBigService.enabled(id, SysConfig.ENABLED_FALSE)) {
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
        if (partsBigService.enabled(id, SysConfig.ENABLED_TRUE)) {
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
    @PreAuthorize("hasAuthority('partsBig:add')")
    public ResponseEntity importPartsBig(@RequestParam("file") MultipartFile file,
                                         Authentication authentication, HttpServletRequest request) {
        ServiceResponse result = partsBigService.importPartsBig(file);
        String filePath = uploadService.uploadFile(request, file, "filePartsBig");
        actionLogService.createActionLog(authentication, request, "批量导入大克拉石头，原文件路径=" + filePath);
        return responseDeal(result);
    }


    /**
     * 全部上架
     *
     * @return
     */
    @RequestMapping(value = "/enableOn", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('partsBig:add')")
    public ResponseEntity enableOn() {
        ServiceResponse result = partsBigService.enableOn();
        return responseDeal(result);
    }


    /**
     * 全部下架
     *
     * @return
     */
    @RequestMapping(value = "/enableOff", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('partsBig:add')")
    public ResponseEntity enableOff() {
        ServiceResponse result = partsBigService.enableOff();
        return responseDeal(result);
    }
}
