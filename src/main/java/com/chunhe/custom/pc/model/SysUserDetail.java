package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 导购员模型
 */
@Getter
@Setter
@Table(name = "sys_user_detail")
public class SysUserDetail extends BaseEntity {

    /**
     * 所属店铺(sysUser.getUsername())
     */
    @Column(name = "`store_id`")
    private String storeId;

    /**
     * 员工工号
     */
    @Column(name = "`code`")
    private String code;

    /**
     * 员工姓名
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 员工职位
     */
    @Column(name = "`job`")
    private String job;
}