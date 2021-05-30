package com.chunhe.custom.pc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.chunhe.custom.framework.mybatis.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据同步
 */
@Getter
@Setter
@Table(name = "data_synch")
public class DataSynch extends BaseEntity {
    /**
     *
     */
    public static final String DATA_SYNCH = "dataSynch";
    public static final int DATA_SYNCH_STORE = 1;
    public static final int DATA_SYNCH_STORE_USER = 2;
    public static final String DATA_SYNCH_STORE_NAME = "店铺信息";
    public static final String DATA_SYNCH_STORE_USER_NAME = "员工信息";
    public static final String DATA_SYNCH_RESOURCE_POS = "POS";
    public static final String DATA_SYNCH_RESOURCE_SAP = "SAP";

    @Column(name = "`name`")
    private String name;

    /**
     * 类型
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 来源，pos系统，SAP系统
     */
    @Column(name = "`resource`")
    private String resource;

    /**
     * 操作更新的用户（sys_user表的username）
     */
    @Column(name = "`user_code`")
    private String userCode;

    /******************************************/

    @Transient
    private String orderBy;

    @Transient
    private String userName;

    @Transient
    private List<DataSynch> dataSynchList;
}