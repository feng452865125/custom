package com.chunhe.custom.framework.service;


import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import com.chunhe.custom.framework.model.SysPermission;
import com.chunhe.custom.framework.mapper.SysPermissionMapper;
import com.chunhe.custom.framework.model.SysRolePermission;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;

import java.util.*;


@Service
public class SysPermissionService extends MyService<SysPermission> {

    @Autowired
    public SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 查询数据
     */
    public List<SysPermission> sysPermissionList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(SysPermission.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (StringUtils.isNotBlank(name.getSearch().getValue())) {
            criteria.andLike("name", TableUtil.toFuzzySql(name.getSearch().getValue()) + "%");
        }
        DataTablesRequest.Column code = dataTablesRequest.getColumn("code");
        if (StringUtils.isNotBlank(code.getSearch().getValue())) {
            criteria.andLike("code", TableUtil.toFuzzySql(code.getSearch().getValue()) + "%");
        }
        criteria.andIsNull("expireDate");
        return getMapper().selectByExample(example);
    }

    /**
     * 是否删除成功
     *
     * @param id
     * @return
     */
    public ServiceResponse deleteById(Long id) {
        SysPermission permission = selectByKey(id);
        if (null == permission) {
            return ServiceResponse.error("权限不存在");
        }

        Example example = new Example(SysRolePermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("permissions", "%" + permission.getCode() + "%");
        if (sysRolePermissionService.selectCountByExample(example) > 0) {
            return ServiceResponse.error("已有角色拥有此权限不允许删除");
        }
        permission.setExpireDate(new Date());
        if (updateNotNull(permission) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }


    /**
     * 是否更新成功
     *
     * @return
     */
    public ServiceResponse update(Map<String, Object> permissionMap) {
        Date date = new Date();
        SysPermission permission = new SysPermission();
        permission.setId(ConvertUtil.convert(permissionMap.get("id"), Long.class));
        permission.setName(ConvertUtil.convert(permissionMap.get("name"), String.class));
        permission.setPname(ConvertUtil.convert(permissionMap.get("pname"), String.class));
        permission.setIsSystem(ConvertUtil.convert(permissionMap.get("isSystem"), Boolean.class));
        permission.setDescription(ConvertUtil.convert(permissionMap.get("description"), String.class));
        boolean result = updateNotNull(permission) == 1;
        if (!result) {
            return ServiceResponse.error("更新失败");
        }
        return ServiceResponse.succ("更新成功");
    }

    /**
     * 是否保存成功
     *
     * @return
     */
    public ServiceResponse save(Map<String, Object> permissionMap) {
        Date date = new Date();
        SysPermission permission = new SysPermission();
        String code = ConvertUtil.convert(permissionMap.get("code"), String.class);
        boolean result = checkCode(null, code);
        if (result) {
            return ServiceResponse.error("权限编码重复");
        }

        permission.setCode(ConvertUtil.convert(permissionMap.get("code"), String.class));
        permission.setName(ConvertUtil.convert(permissionMap.get("name"), String.class));
        permission.setPname(ConvertUtil.convert(permissionMap.get("pname"), String.class));
        permission.setIsSystem(ConvertUtil.convert(permissionMap.get("isSystem"), Boolean.class));
        permission.setDescription(ConvertUtil.convert(permissionMap.get("description"), String.class));
        result = insertNotNull(permission) == 1;
        if (!result) {
            return ServiceResponse.error("保存失败");
        }
        return ServiceResponse.succ("保存成功");
    }

    /**
     * 权限编码是否重复
     */
    public boolean checkCode(Long id, String code) {
        Example example = new Example(SysPermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", code);
        if (null != id) {
            criteria.andNotEqualTo("id", id);
        }
        return getMapper().selectCountByExample(example) > 0;
    }

    /**
     * 返回所有未删除的权限
     */
    public List<SysPermission> sysPermissionAllList() {
        Example example = new Example(SysPermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNull("expireDate");
        return getMapper().selectByExample(example);
    }


    /**
     * 以分类名为key处理权限
     */
    public Map<String, List<SysPermission>> findAllPermissions() {
        Example example = new Example(SysPermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNull("expireDate");
        List<SysPermission> permissionList = getMapper().selectByExample(example);
        return permissionMap(permissionList);
    }

    /**
     * 根据角色编号数组获取所有权限并且以pname分类
     */
    public Map<String, List<SysPermission>> getRolePermissionsMap(Long roleId) {
        Map<String, List<SysPermission>> resultMap = new HashMap<>();

        String[] permissionCodes = sysRolePermissionService.selectByRoleId(roleId);
        if (null == permissionCodes || permissionCodes.length == 0) {
            return resultMap;
        } else {
            List<SysPermission> permissionList = selectBycodes(permissionCodes);
            return permissionMap(permissionList);
        }
    }

    /**
     * 处理权限以pname为key
     */
    private Map<String, List<SysPermission>> permissionMap(List<SysPermission> permissionList) {
        Map<String, List<SysPermission>> resultMap = new HashMap<>();
        for (SysPermission sysPermission : permissionList) {
            if (sysPermission.getExpireDate() != null)
                continue;
            List<SysPermission> value = null;
            if (resultMap.containsKey(sysPermission.getPname())) {
                // 已经存在目录
                value = resultMap.get(sysPermission.getPname());
            } else {
                // 不存在目录
                value = Lists.newArrayList();
            }
            value.add(sysPermission);
            resultMap.put(sysPermission.getPname(), value);
        }
        return resultMap;
    }


    /**
     * 通过权限编码数组查询权限数据
     *
     * @param permissionCodes
     * @return
     */
    public List<SysPermission> selectBycodes(String[] permissionCodes) {
        if (permissionCodes.length == 0) {
            return Lists.newArrayList();
        }
        if (permissionCodes.length <= 1000) {
            return ((SysPermissionMapper) getMapper()).selectBycodes(permissionCodes);
        }

        List<SysPermission> result = new ArrayList<>(permissionCodes.length);
        int count = permissionCodes.length / 1000 + (permissionCodes.length % 1000 == 0 ? 0 : 1);
        for (int i = 0; i < count; i++) {
            int start = i * 1000;
            int end = i == (count - 1) ? permissionCodes.length : 1000;
            String[] subarray = ArrayUtils.subarray(permissionCodes, start, end);
            result.addAll(selectBycodes(subarray));
        }
        return result;
    }

}
