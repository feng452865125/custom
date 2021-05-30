package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * <p>
 * 权限控制
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-14
 */

@Table(name = "sys_access")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class SysAccess extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 角色id，role表
     */
    private Integer sysAccessRoleId;

    /**
     * 菜单id，menu表
     */
    private Integer sysAccessMenuId;


}