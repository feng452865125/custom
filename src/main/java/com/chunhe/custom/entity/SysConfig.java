package com.chunhe.custom.entity;

import com.chunhe.custom.mybatis.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.Data;
import javax.persistence.Table;

/**
 * <p>
 * 常用系统配置
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Table(name = "sys_config")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 前端显示用
     */
    private String sysConfigName;

    /**
     * 查询用
     */
    private String sysConfigKey;

    /**
     * 配置的值
     */
    private String sysConfigValue;

    /**
     * 禁用/启用
     */
    private Boolean sysConfigIsEnable;

    /**
     * 相关备注
     */
    private String sysConfigRemark;


}