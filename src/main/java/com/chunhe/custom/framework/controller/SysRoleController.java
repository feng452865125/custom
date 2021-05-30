package com.chunhe.custom.framework.controller;

import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.model.SysPermission;
import com.chunhe.custom.framework.model.SysRole;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.SysPermissionService;
import com.chunhe.custom.framework.service.SysRolePermissionService;
import com.chunhe.custom.framework.service.SysRoleService;
import com.chunhe.custom.framework.service.SysUserRoleService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.validator.SysPermissionValidator;
import com.chunhe.custom.framework.validator.SysRoleValidator;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(path = "/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('role:page')")
    public String list() {
        return "pages/role/list";
    }


    /**
     * 分页查询
     * @param dataTablesRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('role:list')")
    public DataTablesResponse<SysRole> serverPagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<SysRole> data = sysRoleService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        sysRoleService.sysRoleList(dataTablesRequest);
                    }
                });
        return data;
    }


    /**
     * 增加页面
     * @param model
     * @return
     */
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('role:add')")
    public String add(Model model) {
        Map<String, List<SysPermission>> permissionsMap = sysPermissionService.findAllPermissions();
        model.addAttribute("permissionsMap", permissionsMap);
        return "pages/role/add";
    }

    /**
     * 编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('role:edit')")
    public String editView(@PathVariable Long id, Model model) {
        SysRole sysRole = sysRoleService.selectByKey(id);
        List<String> rolePermissions = Lists.newArrayList(sysRolePermissionService.selectByRoleId(id));
        Map<String, List<SysPermission>> permissionsMap = sysPermissionService.findAllPermissions();
        model.addAttribute("role", sysRole);
        model.addAttribute("rolePermissions", rolePermissions);
        model.addAttribute("permissionsMap", permissionsMap);
        return "pages/role/edit";
    }

    /**
     * 查看页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('role:view')")
    public String view(@PathVariable Long id, Model model){
        SysRole sysRole = sysRoleService.selectByKey(id);
        Map<String, List<SysPermission>> rolePermissionsMap = sysPermissionService.getRolePermissionsMap(id);
        model.addAttribute("role", sysRole);
        model.addAttribute("rolePermissionsMap", rolePermissionsMap);
        return "pages/role/view";
    }

    /**
     *  获取所有的未删除角色
     * @return
     */
    @PreAuthorize("hasAuthority('role:list')")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public List<SysRole> allList() {
        // 返回角色
        return sysRoleService.sysRoleAllList();
    }

    /**
     * 保存
     * @param roleMap
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('role:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> roleMap){
        packageAndValid(null, roleMap, "sysRoleValidator", SysPermissionValidator.IDENTITY_CREATE);
        ServiceResponse result = sysRoleService.save(roleMap);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("新增成功");
    }


    /**
     * 更新
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('role:edit')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Map<String, Object> roleMap){
        roleMap.put("id", id);
        final Map<String, Object> error = packageAndValid(null, roleMap, "sysRoleValidator", SysRoleValidator.IDENTITY_PATCH);
        ServiceResponse result = sysRoleService.update(roleMap);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('role:edit')")
    public ResponseEntity delete(@PathVariable Long id){
        Map<String, Object> permissionMap = new HashMap<String, Object>();
        permissionMap.put("id", id);
        packageAndValid(null, permissionMap, "sysRoleValidator", SysRoleValidator.IDENTITY_DELETE);
        boolean hasRoles = sysUserRoleService.hasRoles(id.toString());
        if(hasRoles){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("角色已与用户关联不允许删除");
        }
        ServiceResponse result = sysRoleService.deleteById(id);
        if(!result.isSucc()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return  ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    /**
     * 角色名称是否重复
     * @param roleMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/nameCheck", method = RequestMethod.GET)
    public String codeCheck(@RequestParam Map<String, Object> roleMap){
        packageAndValid(null, roleMap, "sysRoleValidator",SysRoleValidator.IDENTITY_CHECK);
        boolean isRepeatName = sysRoleService.checkName(ConvertUtil.convert(roleMap.get("id"), Long.class), ConvertUtil.convert(roleMap.get("name"), String.class));
        if(isRepeatName){
            return "角色名重复";
        }
        return null;
    }



}
