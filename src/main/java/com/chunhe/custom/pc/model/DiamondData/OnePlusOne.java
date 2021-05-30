package com.chunhe.custom.pc.model.DiamondData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnePlusOne {

    private String c_id;

    /**
     * （钻石类别） 深圳现货|香港现货|国际现货
     */
    private String c_typeName;


    /**
     * 编号
     */
    private String c_serial;


    /**
     * （形状） 圆形|公主方|祖母绿|辐射形|心形|梨形|橄榄形|椭圆形|垫形|阿斯切|雷蒂恩形|三角形|-
     */
    private String c_shapeName;


    /**
     * 重量
     */
    private String c_carat;


    /**
     * (颜色) 彩钻|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z
     */
    private String c_colorName;


    /**
     * （颜色备注）
     */
    private String c_colorMemo;

    /**
     *（净度）FL|IF|VVS1|VVS2|VS1|VS2|SI1|SI2|SI3|I1|I2|I3
     */
    private String c_clarityName;

    /**
     * （切工） ID|EX|VG|G|F|P|-
     */
    private String c_cutName;

    /**
     * （抛光） ID|EX|VG|G|F|P|-
     */
    private String c_polishName;

    /**
     * （对称）ID|EX|VG|G|F|P|-
     */
    private String c_symmetryName;

    /**
     * （荧光）N|VSL|F|M|S|ST|VST
     */
    private String c_fluorescenceName;

    /**
     * （荧光备注）
     */
    private String c_fluorescenceMemo;

    /**
     * （奶色）
     */
    private String c_milky;

    /**
     * （咖色）
     */
    private String c_browness;

    /**
     * （色调）
     */
    private String c_hues;

    /**
     * （心箭）
     */
    private String c_heartArrow;

    /**
     *（台黑）
     */
    private String c_tableBlack;

    /**
     * （边黑）
     */
    private String c_sideBlack;

    /**
     * （台白）
     */
    private String c_tableWhite;

    /**
     * （边白）
     */
    private String c_sideWhite;

    /**
     * （国际报价）
     */
    private String c_USPrice;

    /**
     * （全深比）
     */
    private String c_depthProportion;

    /**
     * （台宽比）
     */
    private String c_tableProportion;

    /**
     * （尺寸）
     */
    private String c_measurement;

    /**
     * （证书类别） OTHER|GIA|IGI|HRD
     */
    private String c_certificateName;

    /**
     * （证书号）
     */
    private String c_certificateNo;

    /**
     * （证书GIA链接）
     */
    private String c_certificateLink;

    /**
     * （备注）
     */
    private String c_memo;

    /**
     * （所在地）
     */
    private String c_areaState;

    /**
     * （状态）
     */
    private String c_stateName;

    /**
     * （克拉单价）
     */
    private String c_userCaratPrice;

    /**
     * （售价）
     */
    private String c_userUnitPrice;

    /**
     * （折扣、钻石下降点）
     */
    private String c_userDiscount;

    /**
     * （图片路径）
     */
    private String c_picturePaths;
}
