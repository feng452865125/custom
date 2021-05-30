package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 戒托表
 */
@Getter
@Setter
@XmlRootElement
public class Product extends BaseEntity {
    /**
     * 钻石类型 1、男性，2、女性
     */
    public static final int TYPE_MALE = 1;
    public static final int TYPE_FEMALE = 2;

    public Product(){

    }
    public Product (String exJbKd, String exSc, String exFs, String exYy, String jscz, String jsys){
        this.exJbKd = exJbKd;
        this.exSc = exSc;
        this.exFs = exFs;
        this.exYy = exYy;
        this.jscz = jscz;
        this.jsys = jsys;
    }

    public Product (String code, String ktCode, String name, String exJbKd, Integer price, String exZsYs, String exZsJd, Integer type,
                    String exSc, String exFs, String exYy, Integer exFsCount, String jscz, String nxbs, String jsys){
        this.code = code;
        this.ktCode = ktCode;
        this.name = name;
        this.exJbKd = exJbKd;
        this.price = price;
        this.exZsYs = exZsYs;
        this.exZsJd = exZsJd;
        this.type = type;
        this.exSc = exSc;
        this.exFs = exFs;
        this.exYy = exYy;
        this.exFsCount = exFsCount;
        this.jscz = jscz;
        this.nxbs = nxbs;
        this.jsys = jsys;
    }

    /**
     * 编号
     */
    @Column(name = "`code`")
    private String code;

    /**
     * kt码
     */
    @Column(name = "`kt_code`")
    private String ktCode;

    /**
     * 产品名
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 戒臂宽度
     */
    @Column(name = "`ex_jb_kd`")
    private String exJbKd;

    @Column(name = "`price`")
    private Integer price;

    @Column(name = "`ex_zs_ys`")
    private String exZsYs;

    @Column(name = "`ex_zs_jd`")
    private String exZsJd;

    @Column(name = "`style_id`")
    private Long styleId;

    /**
     * 成品的类型（1、男戒，2女戒）
     */
    @Column(name = "`type`")
    private Integer type;

    @Column(name = "`ex_sc`")
    private String exSc;

    @Column(name = "`ex_fs`")
    private String exFs;

    /**
     * 生成规格范围值，定制中判断选择钻石符合的主数据
     */
    @Column(name = "`ex_diamond_fs_min`")
    private Integer exDiamondFsMin;

    /**
     * 生成规格范围值，定制中判断选择钻石符合的主数据
     */
    @Column(name = "`ex_diamond_fs_max`")
    private Integer exDiamondFsMax;

    @Column(name = "`ex_yy`")
    private String exYy;

    @Column(name = "ex_fs_count")
    private Integer exFsCount;

    @Column(name = "`jscz`")
    private String jscz;

    @Column(name = "`nxbs`")
    private String nxbs;

    @Column(name = "`jsys`")
    private String jsys;

    /**
     * 缩略图文件路径
     */
    @Column(name = "`img_max_url`")
    private String imgMaxUrl;

    /**
     * 试戴（30分）
     */
    @Column(name = "`wear_url30`")
    private String wearUrl30;

    /**
     * 试戴（50分）
     */
    @Column(name = "`wear_url50`")
    private String wearUrl50;

    /**
     * 试戴（70分）
     */
    @Column(name = "`wear_url70`")
    private String wearUrl70;

    /**
     * 试戴（100分）
     */
    @Column(name = "`wear_url100`")
    private String wearUrl100;

    /**
     * 是否内镶宝石
     */
    @Column(name = "`is_nxbs`")
    private String isNxbs;

    /*****************************************/

    @Transient
    private String flowerHeadNum = "";

    @Transient
    private String ringArmNum = "";

    @Transient
    private String diamondNum = "";

//    @Transient
//    private String partsCode;

    @Transient
    private String search;

    @Transient
    private Integer searchLength;

    @Transient
    private String groupBy;

    @Transient
    private String orderBy;

    @Transient
    private Style style;

    @Transient
    private String flowerHeadRemark;

    @Transient
    private String ringArmRemark;

    @Transient
    private String diamondRemark;

    /**
     * 花头样式，戒臂样式，样式id
     */
    @Transient
    private String jbYs;

    @Transient
    private String htYs;

    @Transient
    private String diamondCode;

    /**
     * 拆分手寸数据List
     */
    @Transient
    private List handList;

    /**
     * 查找成品列表（确认款式，选择钻石后的查询）
     */
    @Transient//戒臂宽度
    private List jbkdList;
    @Transient//手寸
    private List scList;
    @Transient//金属材质
    private List jsczList;
    @Transient//金属颜色
    private List jsysList;
    @Transient//内镶宝石
    private List nxbsList;

    @Transient//石头重量
    private Integer zl;
}