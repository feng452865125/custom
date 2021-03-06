package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.Data;
import javax.persistence.Table;

/**
 * <p>
 * 角色权限关联表
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Table(name = "sys_role_permission")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysRolePermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    private Integer sysRolePermissionRoleId;

    /**
     * 角色对应权限
     */
    private String sysRolePermissionPermissions;


}