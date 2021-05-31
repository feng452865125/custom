package com.chunhe.custom.controller.fm;

import com.chunhe.custom.datatables.DataTablesRequest;
import com.chunhe.custom.datatables.DataTablesResponse;
import com.chunhe.custom.entity.SysPermission;
import com.chunhe.custom.mybatis.BaseController;
import com.chunhe.custom.response.ServiceResponse;
import com.chunhe.custom.service.fm.FmSysPermissionService;
import com.chunhe.custom.utils.ConvertUtils;
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
 * <p>
 * 权限表 freemarker-控制层
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Controller
@RequestMapping("/SysPermission")
public class FmSysPermissionController extends BaseController {

    @Autowired
    private FmSysPermissionService fmSysPermissionService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('sysPermission:page')")
    public String list() {
        return "pages/sysPermission/list";
    }

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('sysPermission:add')")
    public String add() {
        return "pages/sysPermission/add";
    }


    /**
     * 分页查询
     * @param dataTablesRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('sysPermission:list')")
    public DataTablesResponse<SysPermission> serverPagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {

        DataTablesResponse<SysPermission> data = fmSysPermissionService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        fmSysPermissionService.sysPermissionList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('sysPermission:edit')")
    public String editView(@PathVariable Integer id, Model model) {
        SysPermission permission = fmSysPermissionService.selectByKey(id);
        model.addAttribute("permission", permission);
        return "pages/sysPermission/edit";
    }

    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('sysPermission:view')")
    public String view(@PathVariable Integer id, Model model){
        SysPermission permission = fmSysPermissionService.selectByKey(id);
        model.addAttribute("permission", permission);
        return "pages/sysPermission/view";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('sysPermission:delete')")
    public ResponseEntity delete(@PathVariable Integer id){
        ServiceResponse result = fmSysPermissionService.deleteById(id);
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
    @PreAuthorize("hasAuthority('sysPermission:edit')")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Map<String, Object> permissionMap) {
        permissionMap.put("id", id);
        ServiceResponse result = fmSysPermissionService.update(permissionMap);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('sysPermission:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> permissionMap){
        ServiceResponse result = fmSysPermissionService.save(permissionMap);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("新增成功");
    }

    @ResponseBody
    @RequestMapping(value = "/codeCheck", method = RequestMethod.GET)
    public String codeCheck(@RequestParam Map<String, Object> permissionMap){
        boolean isExistCode = fmSysPermissionService.checkCode(ConvertUtils.convert(permissionMap.get("id"), Integer.class), ConvertUtils.convert(permissionMap.get("code"), String.class));
        if(isExistCode){
            return "权限编码重复";
        }
        return null;
    }


}