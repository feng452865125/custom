package com.chunhe.custom.framework.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by xuqiang on 2017/6/11.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "sys_role_permission")
public class SysRolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roleId;

    private String permissions;

}
