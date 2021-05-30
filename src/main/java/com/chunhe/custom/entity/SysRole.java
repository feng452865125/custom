package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-14
 */

@Table(name = "sys_role")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class SysRole extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 角色名称
     */
    private String sysRoleName;

    /**
     * 备注
     */
    private String sysRoleRemark;

    /**
     * 禁用/启用
     */
    private Boolean sysRoleIsEnable;

    /**
     * 是否系统内置
     */
    private Boolean sysRoleIsSystem;


    /*******************************************************/

    @Transient
    private List menuList;

    @Transient
    private Integer rolePersonCount;

}