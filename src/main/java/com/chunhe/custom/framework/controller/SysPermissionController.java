package com.chunhe.custom.framework.controller;

import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.validator.SysPermissionValidator;
import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.chunhe.custom.framework.model.SysPermission;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.SysPermissionService;
import com.chunhe.custom.framework.utils.ConvertUtil;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(path = "/permission")
public class SysPermissionController extends BaseController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('permission:page')")
    public String list() {
        return "pages/permission/list";
    }

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('permission:add')")
    public String add() {
        return "pages/permission/add";
    }


    /**
     * 分页查询
     * @param dataTablesRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('permission:list')")
    public DataTablesResponse<SysPermission> serverPagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {

        DataTablesResponse<SysPermission> data = sysPermissionService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        sysPermissionService.sysPermissionList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('permission:edit')")
    public String editView(@PathVariable Long id, Model model) {
        SysPermission permission = sysPermissionService.selectByKey(id);
        model.addAttribute("permission", permission);
        return "pages/permission/edit";
    }

   @RequestMapping("/view/{id}")
   @PreAuthorize("hasAuthority('permission:view')")
    public String view(@PathVariable Long id, Model model){
       SysPermission permission = sysPermissionService.selectByKey(id);
       model.addAttribute("permission", permission);
       return "pages/permission/view";
   }

    /**
     * 删除
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('permission:delete')")
    public ResponseEntity delete(@PathVariable Long id){
        ServiceResponse result = sysPermissionService.deleteById(id);
       if(!result.isSucc()){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
       }
        return  ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    /**
     * 更新
     * @param permissionMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('permission:edit')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Map<String, Object> permissionMap) {
        permissionMap.put("id", id);
        packageAndValid(null, permissionMap, "sysPermissionValidator", SysPermissionValidator.IDENTITY_PATCH);
        ServiceResponse result = sysPermissionService.update(permissionMap);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('permission:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> permissionMap){
        packageAndValid(null, permissionMap, "sysPermissionValidator",SysPermissionValidator.IDENTITY_CREATE);
        ServiceResponse result = sysPermissionService.save(permissionMap);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("新增成功");
    }

    @ResponseBody
    @RequestMapping(value = "/codeCheck", method = RequestMethod.GET)
    public String codeCheck(@RequestParam Map<String, Object> permissionMap){
        packageAndValid(null, permissionMap, "sysPermissionValidator",SysPermissionValidator.IDENTITY_CHECK);
        boolean isExistCode = sysPermissionService.checkCode(ConvertUtil.convert(permissionMap.get("id"), Long.class), ConvertUtil.convert(permissionMap.get("code"), String.class));
        if(isExistCode){
            return "权限编码重复";
        }
        return null;
    }



}
