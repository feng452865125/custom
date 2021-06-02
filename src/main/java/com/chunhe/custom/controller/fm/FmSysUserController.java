package com.chunhe.custom.controller.fm;

import com.alibaba.fastjson.JSONObject;
import com.chunhe.custom.datatables.DataTablesRequest;
import com.chunhe.custom.datatables.DataTablesResponse;
import com.chunhe.custom.entity.*;
import com.chunhe.custom.exception.ResultBody;
import com.chunhe.custom.mybatis.BaseController;
import com.chunhe.custom.response.ServiceResponse;
import com.chunhe.custom.service.fm.FmSysRoleService;
import com.chunhe.custom.service.fm.FmSysUserRoleService;
import com.chunhe.custom.service.fm.FmSysUserService;
import com.chunhe.custom.utils.ConvertUtils;
import com.chunhe.custom.utils.DictUtils;
import com.github.pagehelper.ISelect;
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


/**
 * <p>
 * 账号 freemarker-控制层
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Controller
@RequestMapping("/SysUser")
public class FmSysUserController extends BaseController {

    @Autowired
    private FmSysUserService fmSysUserService;

    @Autowired
    private FmSysRoleService fmSysRoleService;

    @Autowired
    private FmSysUserRoleService fmSysUserRoleService;


    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('sysUser:add')")
    public String add(Model model) {
        List<SysRole> roles = fmSysRoleService.sysRoleAllList();
        model.addAttribute("roles", roles);
        return "pages/sysUser/add";
    }


    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('sysUser:edit')")
    public String edit(@PathVariable Long id, Model model) {
        SysUser sysUser = fmSysUserService.selectByKey(id);
        model.addAttribute("sysUser",sysUser);
        // 角色用户关联表
        SysUserRole sysUserRole = fmSysUserRoleService.selectByKey(id);
        model.addAttribute("sysUserRole", sysUserRole);
        //所有角色列表
        List<SysRole> roles = fmSysRoleService.sysRoleAllList();
        model.addAttribute("roles", roles);
        return "pages/sysUser/edit";
    }


    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('sysUser:view')")
    public String view(@PathVariable Long id, Model model) {
        // 用户
        SysUser sysUser = fmSysUserService.selectByKey(id);
        model.addAttribute("sysUser",sysUser);
        // 角色用户关联表
        SysUserRole sysUserRole = fmSysUserRoleService.selectByKey(id);
        List<SysRole> roles = fmSysRoleService.selectRoles(sysUserRole);
        model.addAttribute("roles", roles);
        return "pages/sysUser/view";
    }


    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('sysUser:page')")
    public String sysUserList(Model model) {
        JSONObject isEnable = DictUtils.findDictByType(Constant.DICT_IS_ENABLE);
        JSONObject isLock = DictUtils.findDictByType(Constant.DICT_IS_LOCK);
        model.addAttribute("isEnable", isEnable);
        model.addAttribute("isLock", isLock);
        return "pages/sysUser/list";
    }


    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('sysUser:list')")
    public DataTablesResponse<SysUser> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<SysUser> data = fmSysUserService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        fmSysUserService.sysUserList(dataTablesRequest);
                    }
                });
        return data;
    }


    /**
     * 删除某个用户
     * @param id
     * @return
     */
    @RequestMapping(value= "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasAuthority('sysUser:delete')")
    public ResponseEntity delete(@PathVariable Integer id) {
        ServiceResponse result = fmSysUserService.deleteById(id);
        if(!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return  ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('sysUser:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = fmSysUserService.save(map);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("新增成功");
    }


    /**
     * 检查账户是否被使用
     */
    @RequestMapping(value = "/check/{type}", method = RequestMethod.GET)
    @ResponseBody
    public String codeCheck(@RequestParam Map<String, Object> map, @PathVariable String type){
        Integer id = ConvertUtils.convert(map.get("id"), Integer.class);
        if(type.equals("code")){
            String code = ConvertUtils.convert(map.get("code"), String.class);
            if(code != null){
                boolean isExistCode = fmSysUserService.isExistByParam(id, "code", code);
                if(isExistCode){
                    return "用户编码已存在";
                }
            }
        }else if(type.equals("username")){
            String username = ConvertUtils.convert(map.get("username"), String.class);
            if(username != null){
                boolean isExistUserName = fmSysUserService.isExistByParam(id, "username", username);
                if(isExistUserName){
                    return "登录账号已存在";
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('sysUser:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = fmSysUserService.update(map);
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
        String oldPassword = StringUtils.isEmpty(map.get("oldPassword")) ? null : ConvertUtils.convert(map.get("oldPassword"), String.class);
        String password = StringUtils.isEmpty(map.get("password")) ? null : ConvertUtils.convert(map.get("password"), String.class);
        String rePassword = StringUtils.isEmpty(map.get("rePassword")) ? null : ConvertUtils.convert(map.get("rePassword"), String.class);
        if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(password) || StringUtils.isEmpty(rePassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密码不能为空");
        }
        SysUser sysUser = ((PlatformUser)authentication.getPrincipal()).getSysUser();
        Integer userId = sysUser.getId();
        ServiceResponse result = fmSysUserService.changePassword(userId, oldPassword, password, rePassword);
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
     * 锁定/解锁某个用户
     * @param id
     * @return
     */
    @RequestMapping(value= "/lock/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<String> lock(@PathVariable Integer id, Model model) {
        if(fmSysUserService.lock(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }


    /**
     * 启用/禁用某个用户
     * @param id
     * @return
     */
    @RequestMapping(value= "/enable/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<String> enable(@PathVariable Integer id, Model model) {
        if(fmSysUserService.enable(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }

//    /**
//     * 启用/禁用某个用户
//     * @param id
//     * @return
//     */
//    @RequestMapping(value= "/enable/{id}", method = RequestMethod.PATCH)
//    @ResponseBody
//    public ResultBody enable(@PathVariable Integer id, Model model) {
//        if(fmSysUserService.enable(id)) {
//           return ResultBody.success(Constant.MESSAGE_UPDATE_SUCCESS);
//        } else {
//            return ResultBody.error(Constant.MESSAGE_UPDATE_ERROR);
//        }
//    }
}