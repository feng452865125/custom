package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-22
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
    private String permissionCode;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 权限分类
     */
    private String permissionPname;

    /**
     * 禁用/启用
     */
    private Boolean permissionIsEnable;

    /**
     * 是否系统内置
     */
    private Boolean permissionIsSystem;

    /**
     * 描述
     */
    private String permissionRemark;


}