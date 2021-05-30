package com.chunhe.custom.pc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 订单查询,
 */
@Getter
@Setter
public class Orders extends BaseEntity {
    /**
     * 1、男戒 2、女戒
     */
    public static int ORDER_SEX_TYPE_MALE = 1;
    public static int ORDER_SEX_TYPE_FEMALE = 2;

    /**
     * 订单编号
     */
    @Column(name = "`code`")
    private String code;

    /**
     * kt码
     */
    @Column(name = "`kt_code`")
    private String ktCode;

    /**
     * 4C标准的id
     */
    @Column(name = "`base_id`")
    private Integer baseId;

    /**
     * 颜色
     */
    @Column(name = "`ex_ys`")
    private String exYs;

    /**
     * 净度
     */
    @Column(name = "`ex_jd`")
    private String exJd;

    /**
     * 抛光
     */
    @Column(name = "`ex_pg`")
    private String exPg;

    /**
     * 对称
     */
    @Column(name = "`ex_dc`")
    private String exDc;

    /**
     * 荧光
     */
    @Column(name = "`ex_yg`")
    private String exYg;

    /**
     * 手寸
     */
    @Column(name = "`hand`")
    private String hand;

    /**
     * 材质
     */
    @Column(name = "`material`")
    private String material;

    /**
     * 颜色
     */
    @Column(name = "`color`")
    private String color;

    /**
     * 印花编号
     */
    @Column(name = "`printing_id`")
    private Long printingId;

    /**
     * 备注
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 客户信息姓名
     */
    @Column(name = "`customer_name`")
    private String customerName;

    /**
     * 客户信息号码
     */
    @Column(name = "`customer_mobile`")
    private String customerMobile;

    /**
     * 客户信息地址
     */
    @Column(name = "`customer_address`")
    private String customerAddress;

    /**
     * 门店编号
     */
    @Column(name = "`user_id`")
    private Integer userId;

    /**
     * 制单人
     */
    @Column(name = "`user_operator_id`")
    private Integer userOperatorId;

    /**
     * 制单人
     */
    @Column(name = "`user_sales_id`")
    private Integer userSalesId;

    /**
     * 刻字信息
     */
    @Column(name = "`lettering`")
    private String lettering;

    /**
     * 价格
     */
    @Column(name = "`price`")
    private Integer price;

    /**
     * 样式code
     */
    @Column(name = "`style_id`")
    private Long styleId;

    /**
     * 类型（1、男戒，2、女戒）
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 内嵌宝石
     */
    @Column(name = "`gems`")
    private String gems;

    /**
     * 附加费用id
     */
    @Column(name = "`add_costs_id`")
    private Long addCostsId;

    /**
     * 附加费用price
     */
    @Column(name = "`add_costs_price`")
    private Integer addCostsPrice;

    @Column(name = "`diamond_code`")
    private String diamondCode;

    @Column(name = "`product_code`")
    private String productCode;

    @Column(name = "`pos_url`")
    private String posUrl;

    @Column(name = "`pos_xml`")
    private String posXml;

    @Column(name = "`company`")
    private String company;

    @Column(name = "`plan_time`")
    private Date planTime;

    @Column(name = "`product_id`")
    private String productId;

    @Column(name = "`ex_jb_kd`")
    private String exJbKd;

    //hb、jp 第三方返回钻石下单编号
    @Column(name = "`voucherno`")
    private String voucherno;

    /**
     * 国检证书
     */
    @Column(name = "`certificate`")
    private boolean certificate;

    @Column(name = "`series`")
    private String series;

    /**
     * 生辰石
     */
    @Column(name = "`born_stone`")
    private String bornStone;

    /**
     * 石头质量
     */
    @Column(name = "`stone_zl`")
    private String stoneZl;
    /**
     * 石头采购价
     */
    @Column(name = "`stone_cgj`")
    private String stoneCgj;

    /**
     * 钻石编号
     */
    @Column(name = "`stone_zsbh`")
    private String stoneZsbh;

    /**
     * 主数据寓意
     */
    @Column(name = "`product_yy`")
    private String productYy;

    /**
     * 大克拉钻石类型（small,big)，前端传进
     */
    @Column(name = "`stone_type`")
    private String stoneType;

    /**
     * 内镶宝石
     */
    @Column(name = "`stone_nxbs`")
    private String stoneNxbs;

    /**
     * 批次
     */
    @Column(name = "`stone_batch`")
    private String stoneBatch;

    /*************************************************/

    @Transient
    private String orderBy;

    @Transient
    private String productName;

    /**
     * mysql查询用，product.price as productPrice
     */
    @Transient
    private Integer productPrice;

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @Transient
    private Style style;

    @Transient
    private Product product;

    @Transient
    private Parts parts;

    @Transient
    private String ringArmRemark;

    @Transient
    private String diamondRemark;

    @Transient
    private List<Orders> contentList;

    @Transient
    private String styleMoral;

    /**
     * 店铺，制单人，导购员
     */
    @Transient
    private String userName;

    @Transient
    private String userOperatorName;

    @Transient
    private String userSalesName;

    @Transient
    private String addCostsName;

    /**
     * app我的订单，顾客信息搜索
     */
    @Transient
    private String customer;

    /**
     * 2018年9月10日11:35:56 添加, 增加查询店铺编号, 店铺名称
     */
    @Transient
    private String storeName;

    @Transient
    private String storeCode;

    @Transient
    private String partsId;

    @Transient
    private String zsBh;
}