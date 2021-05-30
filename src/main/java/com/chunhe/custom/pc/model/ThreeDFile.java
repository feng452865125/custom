package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
@Table(name = "three_d_file")
public class ThreeDFile extends BaseEntity {

    /**
     * 文件名
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 路径
     */
    @Column(name = "`url`")
    private String url;

    /**
     * 类型
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 其他备注
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 模块
     */
    @Column(name = "`modules`")
    private String modules;

    /***************************************/

    @Transient
    private List<ThreeDFile> childList;
}