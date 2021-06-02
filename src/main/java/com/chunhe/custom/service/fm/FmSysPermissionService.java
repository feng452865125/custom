package com.chunhe.custom.service.fm;

import com.chunhe.custom.datatables.DataTablesRequest;
import com.chunhe.custom.entity.SysRolePermission;
import com.chunhe.custom.mapper.SysPermissionMapper;
import com.chunhe.custom.mybatis.BaseService;
import com.chunhe.custom.entity.SysPermission;
import com.chunhe.custom.response.ServiceResponse;
import com.chunhe.custom.utils.ConvertUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * <p>
 * 权限表 freemarker-服务层
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Service
public class FmSysPermissionService extends BaseService<SysPermission> {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    public FmSysRolePermissionService fmSysRolePermissionService;

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
        DataTablesRequest.Column sysPermissionName = dataTablesRequest.getColumn("sysPermissionName");
        if (StringUtils.isNotBlank(sysPermissionName.getSearch().getValue())) {
            criteria.andLike("sysPermissionName", sysPermissionName.getSearch().getValue());
        }
        DataTablesRequest.Column sysPermissionCode = dataTablesRequest.getColumn("sysPermissionCode");
        if (StringUtils.isNotBlank(sysPermissionCode.getSearch().getValue())) {
            criteria.andLike("sysPermissionCode", sysPermissionCode.getSearch().getValue());
        }
        criteria.andIsNull("deleteDate");
        return getMapper().selectByExample(example);
    }

    /**
     * 是否删除成功
     *
     * @param id
     * @return
     */
    public ServiceResponse deleteById(Integer id) {
        SysPermission permission = selectByKey(id);
        if (null == permission) {
            return ServiceResponse.error("权限不存在");
        }

        Example example = new Example(SysRolePermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("permissions", "%" + permission.getSysPermissionCode() + "%");
        if (fmSysRolePermissionService.selectCountByExample(example) > 0) {
            return ServiceResponse.error("已有角色拥有此权限不允许删除");
        }
        permission.setDeleteDate(new Date());
        if (update(permission) != 1) {
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
        permission.setId(ConvertUtils.convert(permissionMap.get("id"), Integer.class));
        permission.setSysPermissionName(ConvertUtils.convert(permissionMap.get("name"), String.class));
        permission.setSysPermissionPname(ConvertUtils.convert(permissionMap.get("pname"), String.class));
        permission.setSysPermissionIsSystem(ConvertUtils.convert(permissionMap.get("isSystem"), Boolean.class));
        permission.setSysPermissionRemark(ConvertUtils.convert(permissionMap.get("description"), String.class));
        boolean result = update(permission) == 1;
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
        String code = ConvertUtils.convert(permissionMap.get("code"), String.class);
        boolean result = checkCode(null, code);
        if (result) {
            return ServiceResponse.error("权限编码重复");
        }

        permission.setSysPermissionCode(ConvertUtils.convert(permissionMap.get("code"), String.class));
        permission.setSysPermissionName(ConvertUtils.convert(permissionMap.get("name"), String.class));
        permission.setSysPermissionPname(ConvertUtils.convert(permissionMap.get("pname"), String.class));
        permission.setSysPermissionIsSystem(ConvertUtils.convert(permissionMap.get("isSystem"), Boolean.class));
        permission.setSysPermissionRemark(ConvertUtils.convert(permissionMap.get("description"), String.class));
        result = insert(permission) == 1;
        if (!result) {
            return ServiceResponse.error("保存失败");
        }
        return ServiceResponse.succ("保存成功");
    }

    /**
     * 权限编码是否重复
     */
    public boolean checkCode(Integer id, String code) {
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
        criteria.andIsNull("deleteDate");
        return getMapper().selectByExample(example);
    }


    /**
     * 以分类名为key处理权限
     */
    public Map<String, List<SysPermission>> findAllPermissions() {
        Example example = new Example(SysPermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNull("deleteDate");
        List<SysPermission> permissionList = getMapper().selectByExample(example);
        return permissionMap(permissionList);
    }

    /**
     * 根据角色编号数组获取所有权限并且以pname分类
     */
    public Map<String, List<SysPermission>> getRolePermissionsMap(Long roleId) {
        Map<String, List<SysPermission>> resultMap = new HashMap<>();

        String[] permissionCodes = fmSysRolePermissionService.selectByRoleId(roleId);
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
            if (sysPermission.getDeleteDate() != null)
                continue;
            List<SysPermission> value = null;
            if (resultMap.containsKey(sysPermission.getSysPermissionPname())) {
                // 已经存在目录
                value = resultMap.get(sysPermission.getSysPermissionPname());
            } else {
                // 不存在目录
                value = Lists.newArrayList();
            }
            value.add(sysPermission);
            resultMap.put(sysPermission.getSysPermissionPname(), value);
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