package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 枚举编码
 */
@Getter
@Setter
@Table(name = "enum_code")
public class EnumCode extends BaseEntity {

    @Column(name = "`code`")
    private String code;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`type`")
    private String type;

    @Column(name = "`remark`")
    private String remark;

    public EnumCode(){

    }

    public EnumCode (String code, String name, String type, String remark){
        this.code = code;
        this.name = name;
        this.type = type;
        this.remark = remark;
    }

}