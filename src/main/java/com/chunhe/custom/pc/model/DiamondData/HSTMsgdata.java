package com.chunhe.custom.pc.model.DiamondData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HSTMsgdata {
   //登录
   private String token;         //用户token,后面所有接口的必要参数
   private String id;
   private String vipaccount;    //账号
   private String name;          //姓名
   private String creattime;     //创建时间
   private String sexname;       //性别
   private String tellphone;     //电话
   private String skype;         //Skype账号
   private String qq;            //QQ号
   private String email;         //邮箱
   private String address;       //地址
   private String ischeckname;   //会员状态
   private String remark;        //备注
   private String companyname;   //公司名称
   private String SaleId;        //销售id
   private String SaleName;      //销售员
   private String isguest;       //是否访客
   //同步
   private String total;
   private HSTDiamond[] rows;
}
