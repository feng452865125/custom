package com.chunhe.custom.pc.model.DiamondData;

import lombok.Data;

@Data
public class JP2Diamond {

    //形状
    private String Shape;
    //克拉
    private String Carat;
    //颜色
    private String Color;
    //净度
    private String Clarity;
    //切工
    private String Cut;
    //抛光
    private String Polish;
    //对称
    private String Symmetry;
    //荧光
    private String Fluo;
    //奶色 无奶
    private String Milky;
    //咖色 无咖
    private String Colsh;
    //黑点 无黑
    private String Black;
    //尺寸
    private String Measurement;
    //证书类型
    private String Lab;
    //证书号
    private String ReportNo1;
    //钻石编码（下单、锁货使用，不能当证书号）
    private String ReportNo;
    //国际报价
    private String onlineprice;
    //售退点
    private String AllDiscount;
    //地点
    private String Location;
    //售人民币
    private String SellRMBPrices;
    //图片
    private String ImgUrl;

/*****文档里没备注*******************************/

    //图片
    private String Guid;
    private String StoneId;
    private String Green;//无绿
    private String ReportUrl;
    private String DiamondType;
    private String Supstoneid;
    private String LastMinute;
    private String PriceVesion;
    private String IsSpot;
    private String ExchangeRate;
    private String Discount;
    private String PresellUsdPrice;
    private String totalMultiply;
    private String CartStates;
}
