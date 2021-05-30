package com.chunhe.custom.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.utils.DictUtils;
import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.model.SysRole;
import com.chunhe.custom.framework.model.SysUser;
import com.chunhe.custom.framework.model.SysUserRole;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.security.PlatformUser;
import com.chunhe.custom.framework.service.SysRoleService;
import com.chunhe.custom.framework.service.SysUserRoleService;
import com.chunhe.custom.framework.service.SysUserService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('user:page')")
    public String sysUserList(Model model) {
        JSONObject statusList = DictUtils.findDicByType(SysUser.USER_STATUS_NAME);
        model.addAttribute("statusList", statusList);
        return "pages/user/list";
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('user:add')")
    public String add(Model model) {
        List<SysRole> roles = sysRoleService.sysRoleAllList();
        model.addAttribute("roles", roles);
        return "pages/user/add";
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('user:edit')")
    public String edit(@PathVariable Long id, Model model) {
        SysUser sysUser = sysUserService.selectByKey(id);
        sysUser.setStatusName(DictUtils.findValueByTypeAndKey("userStatusName", sysUser.getStatus()));
        model.addAttribute("user",sysUser);

        // 角色用户关联表
        SysUserRole sysUserRole = sysUserRoleService.selectByKey(id);
        model.addAttribute("sysUserRole", sysUserRole);

        //所有角色列表
        List<SysRole> roles = sysRoleService.sysRoleAllList();
        model.addAttribute("roles", roles);

        return "pages/user/edit";
    }

    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('user:view')")
    public String view(@PathVariable Long id, Model model) {
        // 用户
        SysUser sysUser = sysUserService.selectByKey(id);
        sysUser.setStatusName(DictUtils.findValueByTypeAndKey("userStatusName", sysUser.getStatus()));
        model.addAttribute("user",sysUser);

        // 角色用户关联表
        SysUserRole sysUserRole = sysUserRoleService.selectByKey(id);
        List<SysRole> roles = sysRoleService.selectRoles(sysUserRole);
        model.addAttribute("roles", roles);

        return "pages/user/view";
    }

    /**
     * 检查账户是否被使用
     */
    @RequestMapping(value = "/check/{type}", method = RequestMethod.GET)
    @ResponseBody
    public String codeCheck(@RequestParam Map<String, Object> map, @PathVariable String type){
        Long id = ConvertUtil.convert(map.get("id"), Long.class);
        if(type.equals("code")){
            String code = ConvertUtil.convert(map.get("code"), String.class);
            if(code != null){
                boolean isExistCode = sysUserService.isExistByParam(id, "code", code);
                if(isExistCode){
                    return "用户编码已存在";
                }
            }
        }else if(type.equals("username")){
            String username = ConvertUtil.convert(map.get("username"), String.class);
            if(username != null){
                boolean isExistUserName = sysUserService.isExistByParam(id, "username", username);
                if(isExistUserName){
                    return "登录账号已存在";
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('user:list')")
    public DataTablesResponse<SysUser> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<SysUser> data = sysUserService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        sysUserService.sysUserList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('user:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = sysUserService.save(map);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("新增成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('user:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
//        packageAndValid(null, map, "sysUserValidator", SysUserValidator.IDENTITY_PATCH);
        ServiceResponse result = sysUserService.update(map);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
    }

    /**
     * 修改当前用户密码
     * @return
     */
    @RequestMapping(value= "/changePwd", method = RequestMethod.PATCH)
    public ResponseEntity changePwd(@RequestBody Map<String, Object> map, Authentication authentication) {
        String oldPassword = StringUtils.isEmpty(map.get("oldPassword")) ? null : ConvertUtil.convert(map.get("oldPassword"), String.class);
        String password = StringUtils.isEmpty(map.get("password")) ? null : ConvertUtil.convert(map.get("password"), String.class);
        String rePassword = StringUtils.isEmpty(map.get("rePassword")) ? null : ConvertUtil.convert(map.get("rePassword"), String.class);
        if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(password) || StringUtils.isEmpty(rePassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密码不能为空");
        }
        SysUser sysUser = ((PlatformUser)authentication.getPrincipal()).getSysUser();
        Long userId = sysUser.getId();
        ServiceResponse result = sysUserService.changePassword(userId, oldPassword, password, rePassword);
        if(!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("操作成功");
    }

    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @RequestMapping(value= "/changePwd11", method = RequestMethod.POST)
    public ResponseEntity<String> changePwd11(@org.apache.ibatis.annotations.Param("id") String id) {
        String aaa = bCryptPasswordEncoder.encode(id);
        System.out.println("aaa.toString() = " + aaa.toString());
        return ResponseEntity.status(HttpStatus.OK).body("操作成功");
    }

    /**
     * 解锁某个用户
     * @param id
     * @return
     */
    @RequestMapping(value= "/{id}/unlocked", method = RequestMethod.PATCH)
    public ResponseEntity<String> unlocked(@PathVariable long id) {
        if(sysUserService.locked(id, SysUser.LOCKED_FALSE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }

    /**
     * 锁定某个用户
     * @param id
     * @return
     */
    @RequestMapping(value= "/{id}/lock", method = RequestMethod.PATCH)
    public ResponseEntity<String> lock(@PathVariable long id, Model model) {
        if(sysUserService.locked(id, SysUser.LOCKED_TRUE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }

    /**
     * 禁用某个用户
     * @param id
     * @return
     */
    @RequestMapping(value= "/{id}/disabled", method = RequestMethod.PATCH)
    public ResponseEntity<String> disabled(@PathVariable long id) {
        if(sysUserService.enabled(id, SysUser.ENABLED_FALSE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }

    /**
     * 启用某个用户
     * @param id
     * @return
     */
    @RequestMapping(value= "/{id}/enabled", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<String> enabled(@PathVariable Long id, Model model) {
        if(sysUserService.enabled(id, SysUser.ENABLED_TRUE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }

    /**
     * 删除某个用户
     * @param id
     * @return
     */
    @RequestMapping(value= "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity delete(@PathVariable Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        ServiceResponse result = sysUserService.deleteById(id);
        if(!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return  ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    /**
     * 批量修改店铺密码
     * @param map
     * @param authentication
     * @return
     */
    @RequestMapping(value= "/changeAllPwd", method = RequestMethod.POST)
    public ResponseEntity changeAllPwd(@RequestBody Map<String, Object> map, Authentication authentication) {
        String password = StringUtils.isEmpty(map.get("password")) ? null : ConvertUtil.convert(map.get("password"), String.class);
        String rePassword = StringUtils.isEmpty(map.get("rePassword")) ? null : ConvertUtil.convert(map.get("rePassword"), String.class);
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(rePassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密码不能为空");
        }

        ServiceResponse result = sysUserService.changeAllPassword(password, rePassword);
        if(!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("操作成功");
    }
}
