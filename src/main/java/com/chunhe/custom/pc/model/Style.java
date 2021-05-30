package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * 样式维护
 */
@Getter
@Setter
public class Style extends BaseEntity implements Cloneable {

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 样式类型
     */
    public static final String STYLE_TYPE_COUPLE = "情侣戒指";
    /**
     * 是否推荐-是
     */
    public static final Boolean RECOMMEND_TRUE = Boolean.TRUE;

    /**
     * * 是否推荐-否
     */
    public static final Boolean RECOMMEND_FALSE = Boolean.FALSE;
    /**
     * 是否客定-是
     */
    public static final Boolean IS_KT_TRUE = Boolean.TRUE;

    /**
     * * 是否客定-否
     */
    public static final Boolean IS_KT_FALSE = Boolean.FALSE;

    /**
     * 启用状态-启用
     */
    public static final Boolean ENABLED_TRUE = Boolean.TRUE;

    /**
     * * 启用状态-禁用
     */
    public static final Boolean ENABLED_FALSE = Boolean.FALSE;

    /**
     * 能否改圈-启用
     */
    public static final Boolean GQ_TRUE = Boolean.TRUE;

    /**
     * * 能否改圈-禁用
     */
    public static final Boolean GQ_FALSE = Boolean.FALSE;

    public Style(){

    }

    public Style (String code, String name, Integer type, String moral, String story, String series, String meaning,
                  String htYs, String jbYs, String introduction, Boolean isKt, Boolean isZs){
        this.code = code;
        this.name = name;
        this.type = type;
        this.moral = moral;
        this.story = story;
        this.series = series;
        this.meaning = meaning;
        this.htYs = htYs;
        this.jbYs = jbYs;
        this.introduction = introduction;
        this.isKt = isKt;
        this.isZs = isZs;
    }

    /**
     * 编号
     */
    @Column(name = "`code`")
    private String code;

    @Column(name = "`name`")
    private String name;

    /**
     * 1项链，2戒指（jewelry_type表）
     */
    @Column(name = "`type`")
    private Integer type;

    @Column(name = "`status`")
    private Integer status;

    @Column(name = "`price`")
    private Integer price;

    /**
     * 详情轮播图文件路径
     */
    @Column(name = "`imgs_url`")
    private String imgsUrl;

    /**
     * 缩略图400*300
     */
    @Column(name = "`img_url`")
    private String imgUrl;

    /**
     * 缩略图1000*750
     */
    @Column(name = "`img_max_url`")
    private String imgMaxUrl;

    /**
     * 视频文件路径
     */
    @Column(name = "`video_url`")
    private String videoUrl;

    /**
     * 3D图文件路径
     */
    @Column(name = "`three_d_url`")
    private String threeDUrl;

    /**
     * 45度角图文件路径
     */
    @Column(name = "`rate45img_url`")
    private String rate45imgUrl;

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
    @Column(name = "`moral`")
    private String moral;

    @Column(name = "`hot_seq`")
    private Integer hotSeq;

    @Column(name = "`recom_seq`")
    private Integer recomSeq;

    @Column(name = "`story`")
    private String story;

    /**
     * 系列id
     */
    @Column(name = "`series`")
    private String series;

    /**
     * 权威认证
     */
    @Column(name = "`auth_cert`")
    private String authCert;

    /**
     * 情感含义
     */
    @Column(name = "`meaning`")
    private String meaning;

    /**
     * 是否推荐
     */
    @Column(name = "`is_recommend`")
    private Boolean isRecommend;

    /**
     * 戒臂样式
     */
    @Column(name = "`jb_ys`")
    private String jbYs;

    /**
     * 花头样式
     */
    @Column(name = "`ht_ys`")
    private String htYs;

    /**
     * 工艺介绍
     */
    @Column(name = "`introduction`")
    private String introduction;

    /**
     * 款式特点
     */
    @Column(name = "`features`")
    private String features;

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
     * 是否客定
     */
    @Column(name = "`is_kt`")
    private Boolean isKt;

    /**
     * 正面图文件路径
     */
    @Column(name = "`zheng_url`")
    private String zhengUrl;

    /**
     * 反面图文件路径
     */
    @Column(name = "`fan_url`")
    private String fanUrl;

    /**
     * 是否搭配钻石
     */
    @Column(name = "`is_zs`")
    private Boolean isZs;

    /**
     * unique系列下的筛选分类
     */
    @Column(name = "`unique_group_id`")
    private Integer uniqueGroupId;

    /**
     * 是否可以印花
     */
    @Column(name = "`is_printing`")
    private Boolean isPrinting;

    /**
     * 是否内镶宝石
     */
    @Column(name = "`is_nxbs`")
    private Boolean isNxbs;

    /**
     * 是否启用展示
     */
    @Column(name = "`enabled`")
    private Boolean enabled;

    /**
     * 能否改圈
     */
    @Column(name = "`is_gq`")
    private Boolean isGq;

    //2021年2月4日21:07:34， 新增 高客单价产品 模块
    @Column(name = "`gd_sc`")
    private String gdSc;//手寸或长度
    @Column(name = "`gd_jsys`")
    private String gdJsys;//金属颜色
    @Column(name = "`gd_ysfw`")
    private String gdYsfw;//颜色范围
    @Column(name = "`gd_jdfw`")
    private String gdJdfw;//净度范围
    @Column(name = "`gd_price_min`")
    private String gdPriceMin;//直营上柜价最抵
    @Column(name = "`gd_price_max`")
    private String gdPriceMax;//直营上柜价最高
    @Column(name = "`gd_zl_all`")
    private String gdZlAll;//总重
    @Column(name = "`gd_zl_min`")
    private String gdZlMin;//直营克重最低
    @Column(name = "`gd_zl_max`")
    private String gdZlMax;//直营克重最高
    /**************************************************************/

    @Transient
    private String typeName;

    @Transient
    private Parts flowerHead;

    @Transient
    private Parts ringArm;

    @Transient
    private List<Parts> partsList;

    @Transient
    private String orderBy;

    @Transient
    private List imgsUrlList;

    @Transient
    private String search;

    @Transient
    private String uniqueGroup;

    @Transient
    private Long advertisementId;

    @Transient
    private Long seriesId;

    @Transient
    private String partsScMin;

    @Transient
    private String partsScMax;

    @Transient
    private Long styleId;

    /**
     * 拆分手寸数据List
     */
    @Transient
    private List handList;

    @Transient
    private String maleRemark;

    @Transient
    private String femaleRemark;

    /**
     * 定制的可定分数范围
     */
    @Transient
    private String fsRange;

    /**
     * 定制的戒臂范围
     */
    @Transient
    private String kdRange;

    /**
     * 分组，推荐，样式详情里
     */
    @Transient
    private String groupYs;

    /**
     * 工艺介绍文字
     */
    @Transient
    private String introductionWord;

    /**
     * 工艺介绍视频
     */
    @Transient
    private String introductionVideo;

    /**
     * 工艺介绍图拆分后list
     */
    @Transient
    private String introductionImgs;
    @Transient
    private List introductionImgsList = new ArrayList();

    /**
     * 情侣戒男女戒的详情图，及各自试戴图
     */
    @Transient
    private String imgUrlBoy;
    @Transient
    private String wearUrlBoy30;
    @Transient
    private String wearUrlBoy50;
    @Transient
    private String wearUrlBoy70;
    @Transient
    private String wearUrlBoy100;
    @Transient
    private String imgUrlGirl;
    @Transient
    private String wearUrlGirl30;
    @Transient
    private String wearUrlGirl50;
    @Transient
    private String wearUrlGirl70;
    @Transient
    private String wearUrlGirl100;
    @Transient
    private String imgUrlPro;
}