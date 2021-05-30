package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单控制
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-14
 */

@Table(name = "sys_menu")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    private String sysMenuName;

    /**
     * 图标
     */
    private String sysMenuImg;

    /**
     * 类型，0菜单，1按钮，2外部跳转链接
     */
    private Integer sysMenuType;

    /**
     * 菜单等级，0初始，1级，2级，3级
     */
    private Integer sysMenuLevel;

    /**
     * 排序，desc，从大到小
     */
    private Integer sysMenuSort;

    /**
     * 上一级（父类）id
     */
    private Integer sysMenuPid;

    /**
     * 相对路径
     */
    private String sysMenuUrl;

    /**
     * 禁用/启用
     */
    private Boolean sysMenuIsEnable;

    /**************************************************************/

    @Transient
    private List<SysMenu> menuChildList = new ArrayList<>();

    @Transient
    private Integer hasChecked;

}