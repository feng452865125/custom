package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * <p>
 * 用户角色关联表
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-22
 */

@Table(name = "sys_user_role")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userRoleUserId;

    /**
     * 角色
     */
    private String userRoleRoles;

}