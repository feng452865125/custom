package com.chunhe.custom.framework.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@ToString
@Entity
@Table(name = "sys_permission")
public class SysPermission extends BaseEntity {

    private String code;

    private String name;

    private String pname;

    private String description;

    private Boolean isSystem;

}
