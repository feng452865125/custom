package com.chunhe.custom.controller.fm;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.datatables.DataTablesRequest;
import com.chunhe.custom.datatables.DataTablesResponse;
import com.chunhe.custom.entity.Constant;
import com.chunhe.custom.entity.SysPermission;
import com.chunhe.custom.entity.SysRole;
import com.chunhe.custom.mybatis.BaseController;
import com.chunhe.custom.response.ServiceResponse;
import com.chunhe.custom.service.fm.FmSysPermissionService;
import com.chunhe.custom.service.fm.FmSysRolePermissionService;
import com.chunhe.custom.service.fm.FmSysRoleService;
import com.chunhe.custom.service.fm.FmSysUserRoleService;
import com.chunhe.custom.utils.ConvertUtils;
import com.chunhe.custom.utils.DictUtils;
import com.github.pagehelper.ISelect;
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


/**
 * <p>
 * 角色 freemarker-控制层
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Controller
@RequestMapping("/SysRole")
public class FmSysRoleController extends BaseController {

    @Autowired
    private FmSysRoleService fmSysRoleService;

    @Autowired
    private FmSysRolePermissionService fmSysRolePermissionService;

    @Autowired
    private FmSysPermissionService fmSysPermissionService;

    @Autowired
    private FmSysUserRoleService fmSysUserRoleService;


    /**
     * 增加页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('sysRole:add')")
    public String add(Model model) {
        Map<String, List<SysPermission>> permissionsMap = fmSysPermissionService.findAllPermissions();
        model.addAttribute("permissionsMap", permissionsMap);
        return "pages/sysRole/add";
    }

    /**
     * 编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('sysRole:edit')")
    public String editView(@PathVariable Long id, Model model) {
        SysRole sysRole = fmSysRoleService.selectByKey(id);
        List<String> rolePermissions = Lists.newArrayList(fmSysRolePermissionService.selectByRoleId(id));
        Map<String, List<SysPermission>> permissionsMap = fmSysPermissionService.findAllPermissions();
        model.addAttribute("role", sysRole);
        model.addAttribute("rolePermissions", rolePermissions);
        model.addAttribute("permissionsMap", permissionsMap);
        return "pages/sysRole/edit";
    }

    /**
     * 查看页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('sysRole:view')")
    public String view(@PathVariable Long id, Model model) {
        SysRole sysRole = fmSysRoleService.selectByKey(id);
        Map<String, List<SysPermission>> rolePermissionsMap = fmSysPermissionService.getRolePermissionsMap(id);
        model.addAttribute("role", sysRole);
        model.addAttribute("rolePermissionsMap", rolePermissionsMap);
        return "pages/sysRole/view";
    }

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('sysRole:page')")
    public String list(Model model) {
        JSONObject isEnable = DictUtils.findDictByType(Constant.DICT_IS_ENABLE);
        model.addAttribute("isEnable", isEnable);
        return "pages/sysRole/list";
    }


    /**
     * 分页查询
     *
     * @param dataTablesRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('sysRole:list')")
    public DataTablesResponse<SysRole> serverPagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<SysRole> data = fmSysRoleService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        fmSysRoleService.sysRoleList(dataTablesRequest);
                    }
                });
        return data;
    }

    /**
     * 获取所有的未删除角色
     *
     * @return
     */
    @PreAuthorize("hasAuthority('sysRole:list')")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public List<SysRole> allList() {
        // 返回角色
        return fmSysRoleService.sysRoleAllList();
    }

    /**
     * 保存
     *
     * @param roleMap
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('sysRole:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> roleMap) {
        ServiceResponse result = fmSysRoleService.save(roleMap);
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
    @PreAuthorize("hasAuthority('sysRole:edit')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Map<String, Object> roleMap) {
        roleMap.put("id", id);
        ServiceResponse result = fmSysRoleService.update(roleMap);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('sysRole:edit')")
    public ResponseEntity delete(@PathVariable Integer id) {
        Map<String, Object> permissionMap = new HashMap<String, Object>();
        permissionMap.put("id", id);
        boolean hasRoles = fmSysUserRoleService.hasRoles(id.toString());
        if (hasRoles) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("角色已与用户关联不允许删除");
        }
        ServiceResponse result = fmSysRoleService.deleteById(id);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    /**
     * 角色名称是否重复
     *
     * @param roleMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/nameCheck", method = RequestMethod.GET)
    public String codeCheck(@RequestParam Map<String, Object> roleMap) {
        boolean isRepeatName = fmSysRoleService.checkName(ConvertUtils.convert(roleMap.get("id"), Integer.class), ConvertUtils.convert(roleMap.get("name"), String.class));
        if (isRepeatName) {
            return "角色名重复";
        }
        return null;
    }

}