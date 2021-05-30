package com.chunhe.custom.pc.model;

import com.chunhe.custom.framework.mybatis.BaseEntity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * sku模型
 */
@Getter
@Setter
@Table(name = "sap_sku")
public class SapSku extends BaseEntity {
    public static final int TYPE_RING_ARM = 2;
    public static final int TYPE_FLOWER_HEAD = 3;
    public static final int TYPE_PRODUCT = 4;
    /**
     * 原始数据
     */
    @Column(name = "`content`")
    private String content;

    /**
     * 物料编码
     */
    @NonNull
    @Column(name = "`code`")
    private String code;

    /**
     * 客定码
     */
    @Column(name = "`kt_code`")
    private String ktCode;

    /**
     * 商品名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 金属材质
     */
    @Column(name = "`js_cz`")
    private String jsCz;

    /**
     * 主石类型
     */
    @Column(name = "`zs_lx`")
    private String zsLx;

    /**
     * 佩戴类型
     */
    @Column(name = "`pd_lx`")
    private String pdLx;

    /**
     * 金属颜色
     */
    @Column(name = "`js_ys`")
    private String jsYs;

    /**
     * 钻石颜色范围
     */
    @Column(name = "`zs_ys`")
    private String zsYs;

    /**
     * 钻石净度范围
     */
    @Column(name = "`zs_jd`")
    private String zsJd;

    /**
     * 辅石总数
     */
    @Column(name = "`fs_count`")
    private String fsCount;

    /**
     * 手寸
     */
    @Column(name = "`sc`")
    private String sc;

    /**
     * 寓意
     */
    @Column(name = "`yy`")
    private String yy;

    /**
     * 花头分数
     */
    @Column(name = "`fs`")
    private String fs;

    /**
     * 戒臂宽度
     */
    @Column(name = "`kd`")
    private String kd;

    /**
     * 花头样式/戒臂样式
     */
    @Column(name = "`ys`")
    private String ys;

    /**
     * 开口
     */
    @Column(name = "`kk`")
    private String kk;

    /**
     * 价格
     */
    @Column(name = "`price`")
    private String price;

    /**
     * 系列
     */
    @Column(name = "`series`")
    private String series;

    /**
     * 能否客订
     */
    @Column(name = "`is_kt`")
    private String isKt;

    /**
     * 区分对方发来的数据，3、花头，2、戒臂，4成品
     */
    @NonNull
    @Column(name = "`type`")
    private String type;

    /**
     * 故事
     */
    @Column(name = "`gs`")
    private String gs;

    /**
     * 情感含义
     */
    @Column(name = "`qghy`")
    private String qghy;

    /**
     * 款式特点
     */
    @Column(name = "`kstd`")
    private String kstd;

    /**
     * 1男2女
     */
    @Column(name = "`sex`")
    private String sex;

    /**
     * 能否搭配钻石
     */
    @Column(name = "`is_zs`")
    private String isZs;

    /**
     * 能否搭配钻石
     */
    @Column(name = "`jszl`")
    private String jszl;

    /**
     * 空托价格
     */
    @Column(name = "`kt_price`")
    private String ktPrice;

    /**
     * 定制成品价格
     */
    @Column(name = "`dz_price`")
    private String dzPrice;

    /**
     * 内镶石 2020年7月14日19:29:05
     */
    @Column(name = "`nxs`")
    private String nxs;

    public SapSku() {

    }

    /**
     * 2019年11月5日22:21:39 只更新价格
     * 2020年7月14日19:29:05 增加是否支持内镶石
     *
     * @param content
     * @param type
     * @param code
     * @param ktPrice
     * @param dzPrice
     */
    public SapSku(String content, String type, String code, String ktPrice, String dzPrice, String nxs) {
        this.content = content;
        this.type = type;
        this.code = code;
        this.ktPrice = ktPrice;
        this.dzPrice = dzPrice;
        this.nxs = nxs;
    }

    public SapSku(String content, String code, String ktCode, String name, String jsCz, String zsLx, String pdLx, String jsYs,
                  String zsYs, String zsJd, String fsCount, String sc, String yy, String fs, String kd, String ys,
                  String kk, String price, String series, String isKt, String type, String gs, String qghy, String kstd,
                  String sex, String isZs, String jszl) {
        this.content = content;
        this.code = code;
        this.ktCode = ktCode;
        this.name = name;
        this.jsCz = jsCz;
        this.zsLx = zsLx;
        this.pdLx = pdLx;
        this.jsYs = jsYs;
        this.zsYs = zsYs;
        this.zsJd = zsJd;
        this.fsCount = fsCount;
        this.sc = sc;
        this.yy = yy;
        this.fs = fs;
        this.kd = kd;
        this.ys = ys;
        this.kk = kk;
        this.price = price;
        this.series = series;
        this.isKt = isKt;
        this.type = type;
        this.gs = gs;
        this.qghy = qghy;
        this.kstd = kstd;
        this.sex = sex;
        this.isZs = isZs;
        this.jszl = jszl;
    }

    /**********************************************/

    @Transient
    private String htCode;

    @Transient
    private String jbCode;

    @Transient
    private String zsCode;

    @Transient
    private List<SapSku> dataList;

}