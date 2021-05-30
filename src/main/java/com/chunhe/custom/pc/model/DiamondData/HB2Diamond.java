package com.chunhe.custom.pc.model.DiamondData;

import lombok.Data;

@Data
public class HB2Diamond {
    //
    private String id;
    //形状
    private String shape;
    //大小
    private String d_size;
    //颜色
    private String color;
    //净度
    private String clarity;
    //切工
    private String cut;
    //抛光
    private String polish;
    //对称
    private String sym;
    //荧光
    private String flour;
    //直径
    private String m1;
    private String m2;
    private String m3;
    //深度
    private String d_depth;
    //台面
    private String d_table;
    //货号
    private String d_ref;
    //证书号
    private String reportNo;
    //RAPID 或 内部供应商 ID
    private String detail;
    //折扣
    private String disc;
    //美元价格
    private String rate;
    //地区
    private String location;
    //证书号(他们公司内部证书号)
    private String certNo;
    //
    private String girdle;
    //
    private String natts;
    //
    private String tableInclusion;
    //奶色
    private String milky;
    //
    private String eyeClean;
    //咖色
    private String browness;
    //
    private String status;
    //
    private String isbuy;
    //数据来源
    private String los;
    //
    private String rate1;
    //是否精准数据
    private String isdisp;
    //证书类型
    private String cert;
    //目前用于记录特定接口来源
    private String types;
    //供应商加点
    private String disc1;
    //
    private String md5;
    //
    private String ghs;
    //
    private String cgno;
    //
    private String oldRef;
    //国际报价
    private String rap;
    //
    private String update_time;
    // 0订货     1在库     2预售       3借出     4锁定  9已售
    private String sys_status;
    //
    private String pic;
    //视频地址
    private String video;
    //绿
    private String green;
    //黑点
    private String black;
    //证书图片地址
    private String certificate;
    //裸钻图片地址
    private String daylight;

    //证书号(reportNo) + 供应商id(detail) + 货号(d_ref)
    private String diamondCode;

}
