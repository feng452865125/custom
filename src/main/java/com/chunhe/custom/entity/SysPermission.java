package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.Data;
import javax.persistence.Table;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Table(name = "sys_permission")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 权限编码
     */
    private String sysPermissionCode;

    /**
     * 权限名
     */
    private String sysPermissionName;

    /**
     * 权限分类
     */
    private String sysPermissionPname;

    /**
     * 禁用启用
     */
    private Boolean sysPermissionIsEnable;

    /**
     * 是否系统内置
     */
    private Boolean sysPermissionIsSystem;

    /**
     * 描述
     */
    private String sysPermissionRemark;


}