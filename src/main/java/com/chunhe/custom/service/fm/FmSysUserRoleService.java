package com.chunhe.custom.service.fm;

import com.chunhe.custom.mapper.SysUserRoleMapper;
import com.chunhe.custom.mybatis.BaseService;
import com.chunhe.custom.entity.SysUserRole;
import com.chunhe.custom.utils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * 用户角色关联表 freemarker-服务层
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Service
public class FmSysUserRoleService extends BaseService<SysUserRole> {

    private Logger logger = LogManager.getLogger(getClass());


    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 通过用户编号获取用户对应角色编号数组
     */
    public long[] selectRoleIdsByUserId(long userId) {
        SysUserRole sysUserRole = selectByKey(userId);
        if (sysUserRole == null) {
            return new long[]{};
        }
        String roles = sysUserRole.getSysUserRoleRoles();
        if (StringUtils.isBlank(roles)) {
            return new long[]{};
        }

        String[] roleArray = StringUtils.split(roles, ",");

        return (long[]) ConvertUtils.convert(roleArray, long[].class);
    }

    /**
     * 用户是否关联了角色
     */
    public boolean hasRoles(String roleId) {
        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria =   example.createCriteria();
        criteria.andLike("roles", roleId);
        return getMapper().selectCountByExample(example) > 0;
    }
    
}