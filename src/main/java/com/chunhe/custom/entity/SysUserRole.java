package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.Data;
import javax.persistence.Table;

/**
 * <p>
 * 用户角色关联表
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Table(name = "sys_user_role")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer sysUserRoleUserId;

    private String sysUserRoleRoles;


}