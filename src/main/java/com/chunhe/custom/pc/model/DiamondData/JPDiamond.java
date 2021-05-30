package com.chunhe.custom.pc.model.DiamondData;

import lombok.Data;

@Data
public class JPDiamond {
    //库存号
    private String stoneid;
    //石头号
    private String productid;
    //形状
    private String shape;
    //克拉
    private String carat;
    //颜色
    private String color;
    //净度
    private String clarity;
    //切工
    private String cut;
    //抛光
    private String polish;
    //对称
    private String symmetry;
    //荧光
    private String fluorescence;
    //奶色M0
    private String milky;
    //咖色B0
    private String colsh;
    //绿色G0
    private String green;
    //黑点BL0
    private String black;
    //腰棱
    private String girdle;
    //肉眼干净级别EC0
    private String eyeclean;
    //尺寸
    private String measurement;
    //证书类别GIA
    private String report;
    //证书号
    private String reportno;
    //货地点
    private String address;
    //国际报价
    private String rapprice;
    //退点
    private String saleback;
    //美金价格
    private String saledollorprice;
    //全深
    private String depth_scale;
    //台宽
    private String table_scale;
    //1：正常可用 2：已锁可用 3：已锁不可用
    private String stockstatus;
    //净度特征
    private String clarityflag;

}
