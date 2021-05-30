package com.chunhe.custom.service.fm;

import com.chunhe.custom.datatables.DataTablesRequest;
import com.chunhe.custom.entity.*;
import com.chunhe.custom.mapper.SysUserMapper;
import com.chunhe.custom.mybatis.BaseService;
import com.chunhe.custom.response.ServiceResponse;
import com.chunhe.custom.utils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

/**
 * <p>
 * 账号 freemarker-服务层
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Service
public class FmSysUserService extends BaseService<SysUser>  implements UserDetailsService {

    private Logger logger = LogManager.getLogger(getClass());


    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Autowired
    private FmSysRoleService fmSysRoleService;

    @Autowired
    private FmSysRolePermissionService fmSysRolePermissionService;

    @Autowired
    private FmSysPermissionService fmSysPermissionService;
    
    @Autowired
    private FmSysUserRoleService fmSysUserRoleService;

    @Autowired
    private FmSysConfigService fmSysConfigService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String password = (String) auth.getCredentials();

        SysUser sysUser = this.selectByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("账号或密码错误");
        }
        if (!sysUser.getSysUserIsEnable()) {
            throw new DisabledException("该账户已被禁用，请联系管理员");

        } else if (sysUser.getSysUserIsLocked()) {
            throw new LockedException("该账号已被锁定，请联系管理员");

        }
        List<SysRole> roleArray;
        String[] permissions;
        String[] roles;

        if (Constant.USER_ADMIN.equals(sysUser.getSysUserUsername())) {
            roleArray = fmSysRoleService.sysRoleAllList();
            roles = new String[roleArray.size()];
            for (int i = 0; i < roleArray.size(); i++) {
                SysRole sysRole = roleArray.get(i);
                roles[i] = sysRole.getSysRoleName();
            }
            List<SysPermission> permissionArray = fmSysPermissionService.sysPermissionAllList();
            permissions = new String[permissionArray.size() + 1];
            for (int i = 0; i < permissionArray.size(); i++) {
                SysPermission sysPermission = permissionArray.get(i);
                permissions[i] = sysPermission.getSysPermissionCode();
            }
            permissions[permissionArray.size()] = "admin";
        } else {
            long[] roleIds = fmSysUserRoleService.selectRoleIdsByUserId(sysUser.getId());
            roleArray = fmSysRoleService.selectByKeys(roleIds);
            roles = new String[roleArray.size()];
            for (int i = 0; i < roleArray.size(); i++) {
                SysRole sysRole = roleArray.get(i);
                roles[i] = sysRole.getSysRoleName();
            }
            permissions = fmSysRolePermissionService.selectByRoleIds(roleIds);
        }
        return new PlatformUser(sysUser, roles, permissions);
    }
    
    /**
     * 同步数据时用到，所有用户信息（门店）
     *
     * @return
     */
    public List<SysUser> findSysUserList() {
        List<SysUser> sysUserList = sysUserMapper.findSysUserList();
        return sysUserList;
    }

    /**
     * 用户编码是否重复，code
     * 登录账号是否重复，username
     */
    public Boolean isExistByParam(Long id, String property, String value) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(property, value)
                .andIsNull("deleteDate");
        if (null != id) {
            criteria.andNotEqualTo("id", id);
        }
        return getMapper().selectCountByExample(example) > 0;
    }

    /**
     * 通过username找数据
     */
    public SysUser selectByUsername(String username) {
        SysUser sysUser = new SysUser();
        sysUser.setSysUserUsername(username);
        sysUser.setSysUserIsEnable(true);
        sysUser.setSysUserIsLocked(false);
        return sysUserMapper.selectByUsername(sysUser);
    }

    /**
     * 查询数据
     */
    public List<SysUser> sysUserList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(SysUser.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        //店铺编码（登录账号）
        Example.Criteria criteria = example.createCriteria();
        DataTablesRequest.Column username = dataTablesRequest.getColumn("username");
        if (StringUtils.isNotBlank(username.getSearch().getValue())) {
            criteria.andLike("username", username.getSearch().getValue());
        }
        //店铺名称
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (StringUtils.isNotBlank(name.getSearch().getValue())) {
            criteria.andLike("name", name.getSearch().getValue());
        }
        //营业状态
        DataTablesRequest.Column status = dataTablesRequest.getColumn("status");
        if (StringUtils.isNotBlank(status.getSearch().getValue())) {
            criteria.andEqualTo("status", status.getSearch().getValue());
        }
        criteria.andIsNull("deleteDate")
                .andNotEqualTo("username", Constant.USER_ADMIN)
                .andNotEqualTo("status", "5");
        List<SysUser> sysUserList = getMapper().selectByExample(example);
        return sysUserList;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> sysUserMap) {
        SysUser sysUser = new SysUser();
        String username = ConvertUtils.convert(sysUserMap.get("username"), String.class);
        String password = ConvertUtils.convert(sysUserMap.get("password"), String.class);
        String rePassword = ConvertUtils.convert(sysUserMap.get("rePassword"), String.class);
        String name = ConvertUtils.convert(sysUserMap.get("name"), String.class);
        String uuid = ConvertUtils.convert(sysUserMap.get("uuid"), String.class);
        String priceMultiple = ConvertUtils.convert(sysUserMap.get("priceMultiple"), String.class);

        if (!StringUtil.isEmpty(password) && !password.equals(rePassword)) {
            return ServiceResponse.error("两次密码不一致");
        }

        if (isExistByParam(null, "username", username)) {
            return ServiceResponse.error("登录账号已存在");
        }

        sysUser.setSysUserUsername(username);
        sysUser.setSysUserName(name);
        sysUser.setSysUserPassword(bCryptPasswordEncoder.encode(password));
        sysUser.setSysUserIsEnable(ConvertUtils.convert(sysUserMap.get("isEnabled"), Boolean.class));
        Integer userId = sysUser.getId();
        if (insert(sysUser) != 1) {
            return ServiceResponse.error("保存失败");
        }

        //  增加角色
        if (!this.createSysUserRole(sysUserMap, userId)) {
            return ServiceResponse.error("保存失败-角色保存失败");
        }
        return ServiceResponse.succ("保存成功");
    }

    /**
     * 锁定/解锁
     *
     * @param isLocked 是否锁定
     */
    @Transactional
    public Boolean locked(Long id, Boolean isLocked) {
        SysUser sysUser = selectByKey(id);
        sysUser.setSysUserIsLocked(isLocked);
        return update(sysUser) == 1;
    }

    /**
     * 可用/不可用
     *
     * @param isEnabled 是否可用
     */
    @Transactional(readOnly = false)
    public Boolean enabled(Long id, Boolean isEnabled) {
        SysUser sysUser = selectByKey(id);
        sysUser.setSysUserIsEnable(isEnabled);
        return update(sysUser) == 1;
    }

    /**
     * 修改用户密码
     *
     * @param id          用户编号（userId）
     * @param oldPassword 老密码
     * @param password    新密码
     * @param repassword  确认新密码
     * @return 是否操作成功等信息
     */
    @Transactional(readOnly = false)
    public ServiceResponse changePassword(Integer id, String oldPassword, String password, String repassword) {
        SysUser sysUser = selectByKey(id);
        // 判断两次密码是否已一直
        if (!password.equals(repassword)) {
            return ServiceResponse.error("两次密码不一致");
        }
        // 如果和原密码不符合
        if (!bCryptPasswordEncoder.matches(oldPassword, sysUser.getSysUserPassword())) {
            return ServiceResponse.error("与之前密码不一致");
        }
        sysUser.setSysUserPassword(bCryptPasswordEncoder.encode(password));
        Boolean result = update(sysUser) == 1;
        if (!result) {
            return ServiceResponse.error("密码更新失败！");
        }
        return ServiceResponse.succ("密码更新成功！");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> sysUserMap) {
        Integer userId = ConvertUtils.convert(sysUserMap.get("id"), Integer.class);
        SysUser sysUser = selectByKey(userId);
        String password = ConvertUtils.convert(sysUserMap.get("password"), String.class);
        String rePassword = ConvertUtils.convert(sysUserMap.get("rePassword"), String.class);
        String loLa = ConvertUtils.convert(sysUserMap.get("loLa"), String.class);
        String uuid = ConvertUtils.convert(sysUserMap.get("uuid"), String.class);
        String priceMultiple = ConvertUtils.convert(sysUserMap.get("priceMultiple"), String.class);

        if (!StringUtil.isEmpty(password)) {
            // 判断两次密码是否已一直
            if (!password.equals(rePassword)) {
                return ServiceResponse.error("两次密码不一致");
            }
            sysUser.setSysUserPassword(bCryptPasswordEncoder.encode(ConvertUtils.convert(sysUserMap.get("password"), String.class)));
        }
        sysUser.setSysUserIsEnable(ConvertUtils.convert(sysUserMap.get("isEnabled"), Boolean.class));
        if (update(sysUser) != 1) {
            return ServiceResponse.error("更新失败");
        }

        // 删除角色
        if (fmSysUserRoleService.deleteByKey(userId) < 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.error("保存失败-角色保存失败");
        }

        //  增加角色
        if (!this.createSysUserRole(sysUserMap, userId)) {
            return ServiceResponse.error("保存失败-角色保存失败");
        }

        return ServiceResponse.succ("更新成功");
    }

    /**
     * 删除某个
     */
    @Transactional
    public ServiceResponse deleteById(Integer id) {
        SysUser user = selectByKey(id);
        user.setId(id);
        user.setDeleteDate(new Date());
        if (update(user) != 1) {
            return ServiceResponse.error("删除用户失败");
        }

        // 删除角色
        if (fmSysUserRoleService.deleteByKey(id) < 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.error("删除用户角色关联失败");
        }

        return ServiceResponse.succ("删除用户成功");
    }

    @Transactional
    public boolean createSysUserRole(Map<String, Object> sysUserMap, Integer userId) {
        SysUserRole sysUserRole = new SysUserRole();
        if (null != sysUserMap.get("roles")) {
            String[] roleArray = ConvertUtils.convert(sysUserMap.get("roles"), String[].class);
            sysUserRole.setSysUserRoleUserId(userId);
            sysUserRole.setSysUserRoleRoles(StringUtils.join(roleArray, ","));
            if (fmSysUserRoleService.insert(sysUserRole) != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }
        return true;
    }

    /**
     * 批量修改店铺密码
     *
     * @param password   新密码
     * @param repassword 确认新密码
     * @return 是否操作成功等信息
     */
    @Transactional(readOnly = false)
    public ServiceResponse changeAllPassword(String password, String repassword) {
        // 判断两次密码是否已一致
        if (!password.equals(repassword)) {
            return ServiceResponse.error("两次密码不一致");
        }
//        Example example = new Example(SysUser.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andIsNull("expireDate")
//                .andNotEqualTo("username", SystemConstant.USER_ADMIN);
//        List<SysUser> sysUserList = getMapper().selectByExample(example);
//        for (int i = 0; i < sysUserList.size(); i++) {
//            SysUser sysUser = sysUserList.get(i);
//            sysUser.setPassword(bCryptPasswordEncoder.encode(password));
//            updateNotNull(sysUser);
//        }
        //2019年4月26日21:07:07---直接update
        SysUser sysUser = new SysUser();
        sysUser.setSysUserPassword(bCryptPasswordEncoder.encode(password));
        sysUserMapper.setNewPassword(sysUser);
        return ServiceResponse.succ("密码批量修改成功！");
    }




    
}