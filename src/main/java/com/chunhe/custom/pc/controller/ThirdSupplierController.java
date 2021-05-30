//package com.chunhe.custom.pc.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.chunhe.custom.framework.controller.BaseController;
//import com.chunhe.custom.framework.datatables.DataTablesRequest;
//import com.chunhe.custom.framework.datatables.DataTablesResponse;
//import com.chunhe.custom.framework.exception.RFException;
//import com.chunhe.custom.framework.response.ServiceResponse;
//import com.chunhe.custom.framework.utils.APIUtils;
//import com.chunhe.custom.framework.utils.CheckUtil;
//import com.chunhe.custom.framework.utils.ConvertUtil;
//import com.chunhe.custom.pc.model.ThirdSupplier;
//import com.github.pagehelper.ISelect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * Created by white 2019年3月18日19:52:53
// * 第三方供应商（钻石）
// */
//@Controller
//@RequestMapping("/thirdSupplier")
//public class ThirdSupplierController extends BaseController {
//
//    @Autowired
//    private ThirdSupplierService thirdSupplierService;
//
//    @RequestMapping(value = "/list")
//    @PreAuthorize("hasAuthority('thirdSupplier:page')")
//    public String thirdSupplierList(Model model) {
//        JSONObject statusList = DictUtils.findDicByType(ThirdSupplier.THIRD_SUPPLIER_STATUS);
//        model.addAttribute("statusList", statusList);
//        return "pages/thirdSupplier/list";
//    }
//
//    /**
//     * 列表
//     * @param dataTablesRequest
//     * @return
//     */
//    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
//    @ResponseBody
//    @PreAuthorize("hasAuthority('thirdSupplier:list')")
//    public DataTablesResponse<ThirdSupplier> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
//        DataTablesResponse<ThirdSupplier> data = thirdSupplierService.selectPage(dataTablesRequest,
//                new ISelect() {
//                    @Override
//                    public void doSelect() {
//                        thirdSupplierService.thirdSupplierList(dataTablesRequest);
//                    }
//                });
//        return data;
//    }
//
//    @RequestMapping(value = "/add")
//    @PreAuthorize("hasAuthority('thirdSupplier:add')")
//    public String add(Model model) {
//        return "pages/thirdSupplier/add";
//    }
//
//    /**
//     * 增加
//     *
//     * @param map
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.POST)
//    @ResponseBody
//    @PreAuthorize("hasAuthority('thirdSupplier:add')")
//    public ResponseEntity save(@RequestBody Map<String, Object> map) {
//        ServiceResponse result = thirdSupplierService.save(map);
//        return responseDeal(result);
//    }
//
//    /**
//     * 删除
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    @PreAuthorize("hasAuthority('thirdSupplier:delete')")
//    public ResponseEntity delete(@PathVariable long id) {
//        ServiceResponse result = thirdSupplierService.deleteById(id);
//        return responseDeal(result);
//    }
//
//    @RequestMapping("/edit/{id}")
//    @PreAuthorize("hasAuthority('thirdSupplier:edit')")
//    public String edit(@PathVariable Long id, Model model) {
//        ThirdSupplier thirdSupplier = thirdSupplierService.getThirdSupplier(id);
//        model.addAttribute("thirdSupplier", thirdSupplier);
//        return "pages/thirdSupplier/edit";
//    }
//
//    /**
//     * 修改
//     *
//     * @param id
//     * @param map
//     * @return
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    @ResponseBody
//    @PreAuthorize("hasAuthority('thirdSupplier:edit')")
//    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
//        map.put("id", id);
//        ServiceResponse result = thirdSupplierService.update(map);
//        if (!result.isSucc()) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
//        }
//        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
//    }
//
//    /**
//     * 查询
//     *
//     * @param id
//     * @param model
//     * @return
//     */
//    @RequestMapping("/view/{id}")
//    @PreAuthorize("hasAuthority('thirdSupplier:view')")
//    public String view(@PathVariable Long id, Model model) {
//        ThirdSupplier thirdSupplier = thirdSupplierService.getThirdSupplier(id);
//        model.addAttribute("thirdSupplier", thirdSupplier);
//        return "pages/thirdSupplier/view";
//    }
//
//    /**
//     * 禁用
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/{id}/disabled", method = RequestMethod.PATCH)
//    public ResponseEntity<String> disabled(@PathVariable long id) {
//        if (thirdSupplierService.enabled(id, ThirdSupplier.ENABLED_FALSE)) {
//            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
//        }
//    }
//
//    /**
//     * 启用
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/{id}/enabled", method = RequestMethod.PATCH)
//    @ResponseBody
//    public ResponseEntity<String> enabled(@PathVariable Long id, Model model) {
//        if (thirdSupplierService.enabled(id, ThirdSupplier.ENABLED_TRUE)) {
//            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
//        }
//    }
//
//    /**
//     * 检查英文简称和千叶登录账号是否被使用
//     */
//    @RequestMapping(value = "/check/{type}", method = RequestMethod.GET)
//    @ResponseBody
//    public String codeCheck(@RequestParam Map<String, Object> map, @PathVariable String type) {
//        Long id = ConvertUtil.convert(map.get("id"), Long.class);
//        if (type.equals("shortName")) {
//            String shortName = ConvertUtil.convert(map.get("shortName"), String.class);
//            if (shortName != null) {
//                boolean isExistCode = thirdSupplierService.isExistByParam(id, "shortName", shortName);
//                if (isExistCode) {
//                    return "英文简称已存在";
//                }
//            }
//        } else if (type.equals("keerUsername")) {
//            String keerUsername = ConvertUtil.convert(map.get("keerUsername"), String.class);
//            if (keerUsername != null) {
//                boolean isExistUserName = thirdSupplierService.isExistByParam(id, "keerUsername", keerUsername);
//                if (isExistUserName) {
//                    return "千叶登录账号已存在";
//                }
//            }
//        }
//        return null;
//    }
//
//    /*******对外接口，多家钻石供应商***************************************/
//
//    @RequestMapping(value = "/api", method = RequestMethod.POST)
//    public void thirdSupplierApi(HttpServletRequest req, @RequestBody Map<String, Object> map) {
//        if (CheckUtil.checkNull(map)) {
//            throw new RFException("param error");
//        }
//        String action = ConvertUtil.convert(map.get("action"), String.class);
//        if (action == null || action.equals("")) {
//            throw new RFException("action error");
//        }
//        if (action.equals("login")) {
//            this.createLogin(map);
//        } else if (action.equals("diamond")) {
//            this.updateDiamond(map);
//        }
//    }
//
//    public String createLogin(Map<String, Object> map) {
//        String token = thirdSupplierService.thirdCreateLogin(map);
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("token", token);
//        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, "登录成功", tokenMap);
//    }
//
//    public String updateDiamond(Map<String, Object> map) {
//        thirdSupplierService.thirdUpdateDiamond(map);
//        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, "接收成功，正在处理中...");
//    }
//
//
//}
