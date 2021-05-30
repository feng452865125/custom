package com.chunhe.custom.pc.model.DiamondData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HAOMsgdata {
   //登录
   private String id;
   private String token;
   private String vipaccount;
   private String name;
   private String creattime;
   private String sexname;
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
   private HAODiamond[] rows;
}
