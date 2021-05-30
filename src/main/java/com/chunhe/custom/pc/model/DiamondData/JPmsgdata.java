package com.chunhe.custom.pc.model.DiamondData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JPmsgdata {
   //登录
   private String token;
   private String vipaccount;
   private String name;
   private String creattime;
   private String gender;
   private String tellphone;
   private String skype;
   private String qq;
   private String email;
   private String address;
   private String ischeckname;
   private String remark;
   private String companyname;
   //同步
   private String total;
   private JPDiamond[] rows;
   //下单
   private String productid;//钻石id
   private int operatestatus;//当前钻石下单状态1：下单成功 2：下单失败
   private String operatemessage;//下单结果说明
   private String orderid;//订单号。失败则为空
}
