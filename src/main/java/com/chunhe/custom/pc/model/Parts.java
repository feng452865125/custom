package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钻石表
 */
@Getter
@Setter
//@EqualsAndHashCode
public class Parts extends BaseEntity{
    /**
     * 钻石类型 1、现货，2、全球
     */
    public static final String DIAMOND_STATUS = "diamondStatus";
    /**
     * 钻石排序
     */
    public static final String DIAMOND_ORDER_BY = "diamondOrderBy";
    /**
     * 类型，1钻石，2戒臂，3花头，4成品sku
     */
    public static final int TYPE_DIAMOND = 1;
    public static final int TYPE_RING_ARM = 2;
    public static final int TYPE_FLOWER_HEAD = 3;
    public static final int TYPE_SKU = 4;
    /**
     * 样式查询时--isShow=true
     */
    public static final Boolean IS_SHOW_TRUE = Boolean.TRUE;
    public static final Boolean IS_SHOW_FALSE = Boolean.FALSE;


    public Parts (){

    }

    public Parts (String code, String name, Integer type, String exYy, String exQghy, String exJscz,
                  String exJsys, Integer exJszl, String kk, String ys, String fsKd, String exSc){
        this.code = code;
        this.name = name;
        this.type = type;
        this.exYy = exYy;
        this.exQghy = exQghy;
        this.exJscz = exJscz;
        this.exJsys = exJsys;
        this.exJszl = exJszl;
        this.kk = kk;
        this.ys = ys;
        this.fsKd = fsKd;
        this.exSc = exSc;
    }
    /**
     * 编码
     */
    @Column(name = "`code`")
    private String code;

    @Column(name = "`name`")
    private String name;

    /**
     * 1钻石2戒壁3花头
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 分组
     */
    @Column(name = "`group_type`")
    private Integer groupType;

    /**
     * 1现货，2全球
     */
    @Column(name = "`status`")
    private Integer status;

    @Column(name = "`price`")
    private Integer price;

    @Column(name = "`imgs`")
    private String imgs;

    @Column(name = "`img_url`")
    private String imgUrl;

    @Column(name = "`video`")
    private String video;

    /**
     * 描述
     */
    @Column(name = "`remark`")
    private String remark;

    @Column(name = "`note`")
    private String note;

    /**
     * 寓意
     */
    @Column(name = "`ex_yy`")
    private String exYy;

    /**
     * 情感含义
     */
    @Column(name = "`ex_qghy`")
    private String exQghy;

    /**
     * 戒壁宽度
     */
    @Column(name = "`ex_jbkd`")
    private Integer exJbkd;

    /**
     * 金属材质
     */
    @Column(name = "`ex_jscz`")
    private String exJscz;

    /**
     * 金属颜色
     */
    @Column(name = "`ex_jsys`")
    private String exJsys;

    /**
     * 金属重量
     */
    @Column(name = "`ex_jszl`")
    private Integer exJszl;

    /**
     * 钻石形状
     */
    @Column(name = "`ex_zs_xz`")
    private String exZsXz;

    /**
     * 钻石证书
     */
    @Column(name = "`ex_zs_zs`")
    private String exZsZs;

    /**
     * 钻石重量
     */
    @Column(name = "`ex_zs_zl`")
    private Integer exZsZl;

    /**
     * 钻石颜色
     */
    @Column(name = "`ex_zs_ys`")
    private String exZsYs;

    /**
     * 钻石净度
     */
    @Column(name = "`ex_zs_jd`")
    private String exZsJd;

    /**
     * 钻石切工
     */
    @Column(name = "`ex_zs_qg`")
    private String exZsQg;

    /**
     * 钻石抛光
     */
    @Column(name = "`ex_zs_pg`")
    private String exZsPg;

    /**
     * 钻石对称
     */
    @Column(name = "`ex_zs_dc`")
    private String exZsDc;

    /**
     * 钻石荧光
     */
    @Column(name = "`ex_zs_yg`")
    private String exZsYg;

    /**
     * 钻石编号
     */
    @Column(name = "`ex_zs_bh`")
    private String exZsBh;

    /**
     * 钻石腰码
     */
    @Column(name = "`ex_zs_ym`")
    private String exZsYm;

    /**
     * 生产要求备注里，开口（花头、戒臂）
     */
    @Column(name = "`kk`")
    private String kk;

    /**
     * 生产要求备注里，样式（花头、戒臂）
     */
    @Column(name = "`ys`")
    private String ys;

    /**
     * 生产要求备注里，分数/宽度（花头fs，戒臂kd）
     */
    @Column(name = "`fs_kd`")
    private String fsKd;

    /**
     * 是否展示（同个样式，展示这个）
     */
    @Column(name = "`is_show`")
    private Boolean isShow;

    /**
     * 30分3D
     */
    @Column(name = "`three_d30`")
    private String threeD30;

    /**
     * 50分3D
     */
    @Column(name = "`three_d50`")
    private String threeD50;

    /**
     * 70分3D
     */
    @Column(name = "`three_d70`")
    private String threeD70;

    /**
     * 100分3D
     */
    @Column(name = "`three_d100`")
    private String threeD100;

    /**
     * 手寸
     */
    @Column(name = "`ex_sc`")
    private String exSc;

    /**
     * 是否推荐
     */
    @Column(name = "`is_recommend`")
    private Boolean isRecommend;

    @Column(name = "`jb_ys_recommend`")
    private String jbYsRecommend;

    @Column(name = "`side_img_url`")
    private String sideImgUrl;

    @Column(name = "`level`")
    private Integer level;

    @Column(name = "`company`")
    private String company;

    @Column(name = "`lock_status`")
    private Integer lockStatus;

    /**
     * 黑点
     */
    @Column(name = "`black_inc`")
    private String blackInc;
    /**
     * 中间黑点
     */
    @Column(name = "`bic`")
    private String bic;

    /**
     * 边缘黑点
     */
    @Column(name = "`bis`")
    private String bis;


    /**
     * 奶色
     */
    @Column(name = "`milky`")
    private String milky;

    /**
     * 肉眼干净
     */
    @Column(name = "`eyeclean`")
    private String eyeclean;

    /**
     * 绿色
     */
    @Column(name = "`green`")
    private String green;

    /**
     * 台宽比
     */
    @Column(name = "`table`")
    private String table;


    /**
     * 咖色
     */
    @Column(name = "`colsh`")
    private String colsh;


    /**
     * 色调
     */
    @Column(name = "`hues`")
    private String hues;


    /**
     * 供应商报价
     */
    @Column(name = "`dollar_price`")
    private Integer dollarPrice;


    /**
     * 国际报价
     */
    @Column(name = "`inter_price`")
    private Integer interPrice;

    /**
     * 退点
     */
    @Column(name = "`sale_back`")
    private String saleBack;

    /**
     * 钻石出厂的位置，地址
     */
    @Column(name = "`location`")
    private String location;

    /**
     * 批次号
     */
    @Column(name = "`batch`")
    private String batch;

    /**
     * 默认下架（0下架，1上架）
     */
    @Column(name = "`enable_status`")
    private Integer enableStatus;

    /**
     * 采购员操作上架时间
     */
    @Column(name = "`enable_date`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enableDate;

    /**
     * 采购员结束操作时间
     */
    @Column(name = "`enable_over_date`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enableOverDate;

    /*********************************************************/

    @Transient
    private boolean locationIndia;


    @Transient
    private String groupTypeName;

    @Transient
    private int exZsZlMin;

    /**
     * 用于查询钻石表的最小克拉
     */
    @Transient
    private int zsZlMin;


    @Transient
    private int exZsZlMax;

    /**
     * 用于查询钻石表的最大克拉
     */
    @Transient
    private int zsZlMax;

    /**
     * 查组件（珍珠）价格上下限
     */
    @Transient
    private BigDecimal priceMin;

    @Transient
    private BigDecimal priceMax;

    /**
     * 组件选择，查询可以匹配的另外一个组件
     */
    @Transient
    private String jbYs;

    @Transient
    private String htYs;

    /**
     * 查询可以适合该样式id的钻石
     */
    @Transient
    private Long styleId;

    @Transient
    private String statusName;

    /**
     * 钻石颜色，钻石净度（多选，in)
     */
    @Transient
    private String exZsYsArr;

    @Transient
    private String exZsJdArr;

    /**
     * 前端传进排序类型，后端匹配具体排序
     */
    @Transient
    private Integer orderByType;

    @Transient
    private String orderBy;

    /**
     * 分组，推荐，样式详情里
     */
    @Transient
    private String groupYs;

    @Transient
    private String groupBy;

    @Transient
    private Boolean hasSku;

    /**
     * 需求变更新逻辑，先选属性，再选钻石
     * 同款样式，手寸全部相同，则此处不做参数
     */
    @Transient
    private String productExSc;

    @Transient
    private String productJbkd;

    @Transient
    private String productJscz;

    @Transient
    private String productJsys;

    @Transient
    private String productKtCode;

    @Transient
    private Integer productPrice;

    @Transient
    private Integer baseId;

    @Transient
    private String sapCode;

    @Transient
    private String priceMultiple1;

    @Transient
    private String priceMultiple2;

    @Transient
    private String userId;

    @Transient
    private String pg;

    @Transient
    private String dc;

    @Transient
    private Long productId;

    public Parts newParts() {
        Parts p1 = new Parts();
        return p1;
    }

    @Transient
    private Boolean vg3;


    @Transient
    private String thirdStoneZlMin;
    @Transient
    private String thirdStoneZlMax;
    /**
     * 搜索条件
     */
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @Transient
    private String filterAddress;

    //门店系数2.3，2021年2月2日20:00:08
    @Transient
    private String priceStoreMultiple;
}