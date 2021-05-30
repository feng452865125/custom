package com.chunhe.custom.pc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Table(name = "rel_ys")
public class RelYs {
    /**
     * 是否匹配
     */
    public final static int REL_CONNECTION_YES = 1;
    public final static int REL_CONNECTION_NO = 0;

    /**
     * 是否最佳-是
     */
    public static final Boolean BEST_TRUE = Boolean.TRUE;

    /**
     * * 是否最佳-否
     */
    public static final Boolean BEST_FALSE = Boolean.FALSE;

    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`kk`")
    private String kk;

    @Column(name = "`ht_ys`")
    private String htYs;

    @Column(name = "`fs`")
    private String fs;

    @Column(name = "`jb_ys`")
    private String jbYs;

    @Column(name = "`kd`")
    private String kd;

    /*********************************/

    @Transient
    private Integer ifConnection;

    @Transient
    private String threeDHt;

    @Transient
    private String threeDJb;

    @Transient
    private List handList;

    @Transient
    private String styleName;

    @Transient
    private String styleMoral;

    @Transient
    private Boolean best;

    @Transient
    private String productExSc;
}