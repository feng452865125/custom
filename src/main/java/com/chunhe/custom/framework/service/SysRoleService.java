package com.chunhe.custom.framework.service;


import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.mapper.SysRoleMapper;
import com.chunhe.custom.framework.response.ServiceResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;
import com.chunhe.custom.framework.model.SysRole;
import com.chunhe.custom.framework.model.SysRolePermission;
import com.chunhe.custom.framework.model.SysUserRole;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class SysRoleService extends MyService<SysRole> {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 查询角色
     */
    public List<SysRole> sysRoleAllList() {
        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNull("expireDate");
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
            criteria.andLike("name", TableUtil.toFuzzySql(name.getSearch().getValue()));
        }
        criteria.andIsNull("expireDate");
        return getMapper().selectByExample(example);
    }

    /**
     * 保存
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> roleMap) {
        Date date = new Date();
        SysRole sysRole = new SysRole();
        String name  = ConvertUtil.convert(roleMap.get("name"), String.class);
        boolean result = checkName(null, name);
        if (result) {
            return ServiceResponse.error("角色名重复");
        }

        sysRole.setName(name);
        sysRole.setIsSystem(ConvertUtil.convert(roleMap.get("isSystem"), boolean.class));
        sysRole.setDescription(ConvertUtil.convert(roleMap.get("description"), String.class));
        result = insertNotNull(sysRole) == 1;
        if (!result) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.error("保存失败");
        }

        String permissions = "";
        if (null != roleMap.get("permissionArray")) {
            String[] permissionArray = ConvertUtil.convert(roleMap.get("permissionArray"), String[].class);
            permissions = StringUtils.join(permissionArray, ",");
        }

        SysRolePermission rolePermission = new SysRolePermission();
        rolePermission.setRoleId(sysRole.getId());
        rolePermission.setPermissions(permissions);
        result = sysRolePermissionService.insertNotNull(rolePermission) == 1;
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
        String name = ConvertUtil.convert(roleMap.get("name"), String.class);
        long id = ConvertUtil.convert(roleMap.get("id"), long.class);
        boolean result = checkName(id, name);
        if (result) {
            return  ServiceResponse.error("角色名重复");
        }

        sysRole.setId(id);
        sysRole.setName(ConvertUtil.convert(roleMap.get("name"), String.class));
        sysRole.setIsSystem(ConvertUtil.convert(roleMap.get("isSystem"), boolean.class));
        sysRole.setDescription(ConvertUtil.convert(roleMap.get("description"), String.class));
        result = updateNotNull(sysRole) == 1;
        if (!result) {
            return  ServiceResponse.error("更新失败");
        }

        String permissions = "";
        if (null != roleMap.get("permissionArray")) {
            String[] permissionArray = ConvertUtil.convert(roleMap.get("permissionArray"), String[].class);
            permissions = StringUtils.join(permissionArray, ",");
        }

        SysRolePermission rolePermission = sysRolePermissionService.selectByKey(sysRole.getId());
        if (null != rolePermission) {
            rolePermission.setPermissions(permissions);
            result = sysRolePermissionService.updateNotNull(rolePermission) == 1;
        } else {
            rolePermission = new SysRolePermission();
            rolePermission.setRoleId(sysRole.getId());
            rolePermission.setPermissions(permissions);
            result = sysRolePermissionService.insertNotNull(rolePermission) == 1;
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
    public boolean checkName(Long id, String name) {
        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", name)
                .andIsNull("expireDate");
        if(null != id){
            criteria.andNotEqualTo("id", id);
        }
        return  getMapper().selectCountByExample(example) > 0;
    }


    /**
     * 删除角色
     */
    @Transactional
    public ServiceResponse deleteById(Long id) {
        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("roles","%" + id + "%");
        if(sysUserRoleService.selectCountByExample(example) > 0){
            return  ServiceResponse.error("已有用户拥有此角色不允许删除");
        }
        Date date = new Date();
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        sysRole.setExpireDate(new Date());
        boolean result = updateNotNull(sysRole) == 1;
        if (!result) {
            return ServiceResponse.error("删除角色失败");
        }
        //如果存在角色权限关联则进行删除
        Example rolePermissionExample = new Example(SysRolePermission.class);
        Example.Criteria roleRoleCriteria = rolePermissionExample.createCriteria();
        roleRoleCriteria.andEqualTo("roleId", id);
        if(sysRolePermissionService.selectCountByExample(rolePermissionExample) > 0 && sysRolePermissionService.deleteByKey(id) < 0){
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
        if(StringUtils.isBlank(sysUserRole.getRoles())){
            return null;
        }
        String[] roleIdsStr = sysUserRole.getRoles().split(",");
        long[] rolesIds = new long[roleIdsStr.length];
        for(int i=0; i<roleIdsStr.length; i++){
            rolesIds[i] = Long.parseLong(roleIdsStr[i]);
        }
        return selectByKeys(rolesIds);
    }
}
