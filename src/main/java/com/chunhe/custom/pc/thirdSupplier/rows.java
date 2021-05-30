package com.chunhe.custom.pc.thirdSupplier;

import lombok.Data;

@Data
public class rows {
    public String stoneid;
    public String productid;
    public String carat;//克拉
    public String color;//颜色
    public String clarity;//净度
    public String cut;//切工
    public String polish;//抛光
    public String symmetry;//对称
    public String fluorescence;//荧光


    /**
     * 色调
     */
    private String tone;


    /**
     * 奶色 M0:无，M1:轻，M2:中
     */
    public String milky;

    /**
     * 咖色  B0:无，B1:轻，B2:中
     */
    private String colsh;


    /**
     * 绿色 G0:无，G1:轻，G2:中
     */
    public String green;

    /**
     * 黑点 BL0:无，BL1:轻，BL2:中
     */
    public String black;

    /**
     * 肉眼干净级别 EC0肉眼干净 EC1：90%肉眼干净，EC2：肉眼不干净
     */
    public String eyeclean;


    public String measurement;
    public String report;
    public String reportno;
    public String address; //地址
    public String saledollorprice;
    public String depth_scale;

    /**
     * 台宽比
     */
    public String table_scale;
    public String stockstatus;
    public Long orderId;
    public Long taskId;

    public String shape;//形状
    public String certificate;//证书编号
    public String inscriptions;//腰码
    public String rapprice;//国际报价(美元)
    public String saleback;//退点

    //配置
    public String name;
    public String key;
    public String value;

    public String company;






    //hb 钻石下单编号
    public String voucherno;

    /**
     * 大克拉钻石类型（small,big)，前端传进
     */
    public String stoneType;
    /**
     * 成品寓意，石头颜色，石头净度，发邮件
     */
    public String productYy;
    public String stoneYs;
    public String stoneJd;
    public String stoneZsh;
    public String stoneZl;
    public String stoneCgj;

    //keersapchuan传订单号和批次号
    private String stoneOrderCode;
    private String stoneOrderCharg;

}
