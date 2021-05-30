package com.chunhe.custom.service.fm;

import com.chunhe.custom.datatables.DataTablesRequest;
import com.chunhe.custom.entity.SysRolePermission;
import com.chunhe.custom.entity.SysUserRole;
import com.chunhe.custom.mapper.SysRoleMapper;
import com.chunhe.custom.mybatis.BaseService;
import com.chunhe.custom.entity.SysRole;
import com.chunhe.custom.response.ServiceResponse;
import com.chunhe.custom.utils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 freemarker-服务层
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Service
public class FmSysRoleService extends BaseService<SysRole> {

    private Logger logger = LogManager.getLogger(getClass());


    @Autowired
    private FmSysRolePermissionService fmSysRolePermissionService;

    @Autowired
    private FmSysUserRoleService fmSysUserRoleService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 查询角色
     */
    public List<SysRole> sysRoleAllList() {
        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNull("deleteDate");
        return getMapper().selectByExample(example);
    }

    /**
     * 查询角色
     */
    public List<SysRole> sysRoleList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(SysRole.class);
        String orders = dataTablesRequest.orders();
        if(StringUtils.isNotBlank(orders)){
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (StringUtils.isNotBlank(name.getSearch().getValue())) {
            criteria.andLike("name", name.getSearch().getValue());
        }
        criteria.andIsNull("deleteDate");
        return getMapper().selectByExample(example);
    }

    /**
     * 保存
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> roleMap) {
        Date date = new Date();
        SysRole sysRole = new SysRole();
        String name  = ConvertUtils.convert(roleMap.get("name"), String.class);
        boolean result = checkName(null, name);
        if (result) {
            return ServiceResponse.error("角色名重复");
        }

        sysRole.setSysRoleName(name);
        sysRole.setSysRoleRemark(ConvertUtils.convert(roleMap.get("description"), String.class));
        result = insert(sysRole) == 1;
        if (!result) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.error("保存失败");
        }

        String permissions = "";
        if (null != roleMap.get("permissionArray")) {
            String[] permissionArray = ConvertUtils.convert(roleMap.get("permissionArray"), String[].class);
            permissions = StringUtils.join(permissionArray, ",");
        }

        SysRolePermission rolePermission = new SysRolePermission();
        rolePermission.setSysRolePermissionRoleId(sysRole.getId());
        rolePermission.setSysRolePermissionPermissions(permissions);
        result = fmSysRolePermissionService.insert(rolePermission) == 1;
        if (!result) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.error("保存失败");
        }
        return  ServiceResponse.succ("保存成功");
    }

    /**
     * 角色更新
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> roleMap) {
        Date date = new Date();
        SysRole sysRole = new SysRole();
        String name = ConvertUtils.convert(roleMap.get("name"), String.class);
        Integer id = ConvertUtils.convert(roleMap.get("id"), Integer.class);
        boolean result = checkName(id, name);
        if (result) {
            return  ServiceResponse.error("角色名重复");
        }

        sysRole.setId(id);
        sysRole.setSysRoleName(ConvertUtils.convert(roleMap.get("name"), String.class));
        sysRole.setSysRoleRemark(ConvertUtils.convert(roleMap.get("description"), String.class));
        result = update(sysRole) == 1;
        if (!result) {
            return  ServiceResponse.error("更新失败");
        }

        String permissions = "";
        if (null != roleMap.get("permissionArray")) {
            String[] permissionArray = ConvertUtils.convert(roleMap.get("permissionArray"), String[].class);
            permissions = StringUtils.join(permissionArray, ",");
        }

        SysRolePermission rolePermission = fmSysRolePermissionService.selectByKey(sysRole.getId());
        if (null != rolePermission) {
            rolePermission.setSysRolePermissionPermissions(permissions);
            result = fmSysRolePermissionService.update(rolePermission) == 1;
        } else {
            rolePermission = new SysRolePermission();
            rolePermission.setSysRolePermissionRoleId(sysRole.getId());
            rolePermission.setSysRolePermissionPermissions(permissions);
            result = fmSysRolePermissionService.insert(rolePermission) == 1;
        }
        if (!result) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return  ServiceResponse.error("更新失败");
        }

        return  ServiceResponse.succ("更新成功");
    }

    /**
     * 角色名称是否重复
     */
    public boolean checkName(Integer id, String name) {
        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", name)
                .andIsNull("deleteDate");
        if(null != id){
            criteria.andNotEqualTo("id", id);
        }
        return  getMapper().selectCountByExample(example) > 0;
    }


    /**
     * 删除角色
     */
    @Transactional
    public ServiceResponse deleteById(Integer id) {
        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("roles","%" + id + "%");
        if(fmSysUserRoleService.selectCountByExample(example) > 0){
            return  ServiceResponse.error("已有用户拥有此角色不允许删除");
        }
        Date date = new Date();
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        sysRole.setDeleteDate(new Date());
        boolean result = update(sysRole) == 1;
        if (!result) {
            return ServiceResponse.error("删除角色失败");
        }
        //如果存在角色权限关联则进行删除
        Example rolePermissionExample = new Example(SysRolePermission.class);
        Example.Criteria roleRoleCriteria = rolePermissionExample.createCriteria();
        roleRoleCriteria.andEqualTo("roleId", id);
        if(fmSysRolePermissionService.selectCountByExample(rolePermissionExample) > 0 && fmSysRolePermissionService.deleteByKey(id) < 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.error("删除角色关联失败");
        }
        return ServiceResponse.succ("删除角色成功");
    }

    /**
     * 查找用户角色信息
     * @param sysUserRole
     * @return
     */
    public List<SysRole> selectRoles(SysUserRole sysUserRole) {
        if(null == sysUserRole){
            return null;
        }
        if(StringUtils.isBlank(sysUserRole.getSysUserRoleRoles())){
            return null;
        }
        String[] roleIdsStr = sysUserRole.getSysUserRoleRoles().split(",");
        long[] rolesIds = new long[roleIdsStr.length];
        for(int i=0; i<roleIdsStr.length; i++){
            rolesIds[i] = Long.parseLong(roleIdsStr[i]);
        }
        return selectByKeys(rolesIds);
    }
    
}