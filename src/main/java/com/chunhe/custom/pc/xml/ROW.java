package com.chunhe.custom.pc.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@Data
@XStreamAlias("ROW")
public class ROW {
    @XStreamAsAttribute
    public String RowState;

    @XStreamAsAttribute
    public String FCODE;
    @XStreamAsAttribute
    public String FNAME;
    @XStreamAsAttribute
    public String FORG_CODE;
    @XStreamAsAttribute
    public String FORG_NAME;

    @XStreamAsAttribute
    public String FRE_CODE;
    @XStreamAsAttribute
    public String FAMOUNT_BASE_YD;//总金额（skuPrice + zsPrice）
    @XStreamAsAttribute
    public String FCUSTOMER;
    @XStreamAsAttribute
    public String FMOBILE;
    @XStreamAsAttribute
    public String FBIZTIME;
    @XStreamAsAttribute
    public String FPALNTIME;
    @XStreamAsAttribute
    public String FSFFS;
    @XStreamAsAttribute
    public String FADD_FEE;
    @XStreamAsAttribute
    public String FEXPLANE;
    @XStreamAsAttribute
    public String FREMARK;//备注
    @XStreamAsAttribute
    public String FOPERATOR;//制单人
    @XStreamAsAttribute
    public String FOPERATORID;//制单人编号



    @XStreamAsAttribute
    public String FSKU_CODE;
    @XStreamAsAttribute
    public String FGOODS_CODE;
    @XStreamAsAttribute
    public String FQTY_BASE;//数量，固定"1"
    @XStreamAsAttribute
    public String FAMOUNT_BASE;//总金额（skuPrice + zsPrice）
    @XStreamAsAttribute
    public String FAMOUNT_SASE;
    @XStreamAsAttribute
    public String ZYY;
    @XStreamAsAttribute
    public String ZLSYS;
    @XStreamAsAttribute
    public String ZJSYS;
    @XStreamAsAttribute
    public String ZJSCA;
    @XStreamAsAttribute
    public String ZJD;
    @XStreamAsAttribute
    public String ZPG;
    @XStreamAsAttribute
    public String ZDC;
    @XStreamAsAttribute
    public String ZYG;
    @XStreamAsAttribute
    public String ZCD;
    @XStreamAsAttribute
    public String ZZSZ;
    @XStreamAsAttribute
    public String ZQG;
    @XStreamAsAttribute
    public String ZPPZY;
    @XStreamAsAttribute
    public String ZZSXZ;
    @XStreamAsAttribute
    public String ZZSSL;
    @XStreamAsAttribute
    public String ZYH;
    @XStreamAsAttribute
    public String ZYM;
    @XStreamAsAttribute
    public String FINDEX;//单据类型"K10" : "K20"

    @XStreamAsAttribute
    public String FSTATE;
    @XStreamAsAttribute
    public String FDZ;

    //2019年4月22日16:44:38
    //钻石供应商编码
    @XStreamAsAttribute
    public String FGYSBM;
    //钻石编码
    @XStreamAsAttribute
    public String FZSBM;
    // 采购价格
    @XStreamAsAttribute
    public String FJG;
    //內镶石
    @XStreamAsAttribute
    public String FNXS;
    //2020年7月27日20:45:04
    //刻字要求
    @XStreamAsAttribute
    public String FBY4;//FKZYQ 2020年8月15日19:42:55 改为 FBY4
    //主石证书号
    @XStreamAsAttribute
    public String FBYZD4;
    //镶嵌方式
    @XStreamAsAttribute
    public String FBYZD5;

    public ROW() {
    }

    //表头
    public ROW(
            String RowState,
            String FCODE,
            String FRE_CODE,
            String FQTY_BASE,
            String FAMOUNT_BASE,
            String FAMOUNT_BASE_YD,
            String FCUSTOMER,
            String FMOBILE,
            String FBIZTIME,
            String FPALNTIME,
            String FSFFS,
            String FADD_FEE,
            String FEXPLANE,
            String FREMARK,
            String FOPERATOR,
            String FOPERATORID,
            String FINDEX,
            String FBY4
            ) {
        this.RowState = RowState;
        this.FCODE = FCODE;
        this.FRE_CODE = FRE_CODE;
        this.FQTY_BASE = FQTY_BASE;
        this.FAMOUNT_BASE = FAMOUNT_BASE;
        this.FAMOUNT_BASE_YD = FAMOUNT_BASE_YD;
        this.FCUSTOMER = FCUSTOMER;
        this.FMOBILE = FMOBILE;
        this.FBIZTIME = FBIZTIME;
        this.FPALNTIME = FPALNTIME;
        this.FSFFS = FSFFS;
        this.FADD_FEE = FADD_FEE;
        this.FEXPLANE = FEXPLANE;
        this.FREMARK = FREMARK;
        this.FOPERATOR = FOPERATOR;
        this.FOPERATORID = FOPERATORID;
        this.FINDEX = FINDEX;
        this.FBY4 = FBY4;
    }

    //明细
    public ROW(String RowState,
               String FCODE,
               String FSKU_CODE,
               String FGOODS_CODE,
               String FQTY_BASE,
               String FAMOUNT_SASE,
               String FAMOUNT_BASE,
               String ZYY,
               String ZLSYS,
               String ZJSYS,
               String ZJSCA,
               String ZJD,
               String ZPG,
               String ZDC,
               String ZYG,
               String ZCD,
               String ZZSZ,
               String ZYM,
               String ZQG,
               String FREMARK,
               String ZPPZY,
               String ZZSXZ,
               String ZZSSL,
               String ZYH,
               String FGYSBM,
               String FZSBM,
               String FJG,
               String FNXS,
               String FBYZD4,
               String FBYZD5) {
        this.RowState = RowState;
        this.FCODE = FCODE;
        this.FSKU_CODE = FSKU_CODE;
        this.FGOODS_CODE = FGOODS_CODE;
        this.FQTY_BASE = FQTY_BASE;
        this.FAMOUNT_BASE = FAMOUNT_BASE;
        this.FAMOUNT_SASE = FAMOUNT_SASE;
        this.ZYY = ZYY;
        this.ZLSYS = ZLSYS;
        this.ZJSYS = ZJSYS;
        this.ZJSCA = ZJSCA;
        this.ZJD = ZJD;
        this.ZPG = ZPG;
        this.ZDC = ZDC;
        this.ZYG = ZYG;
        this.ZCD = ZCD;
        this.ZZSZ = ZZSZ;
        this.ZYM = ZYM;
        this.ZQG = ZQG;
        this.FREMARK = FREMARK;
        this.ZPPZY = ZPPZY;
        this.ZZSXZ = ZZSXZ;
        this.ZZSSL = ZZSSL;
        this.ZYH = ZYH;
        this.FGYSBM = FGYSBM;
        this.FZSBM = FZSBM;
        this.FJG = FJG;
        this.FNXS = FNXS;
        this.FBYZD4 = FBYZD4;
        this.FBYZD5 = FBYZD5;
    }


    /**card POS 缺少的字段****************************************/

    @XStreamAsAttribute
    public String FNCODE;//密码
    @XStreamAsAttribute
    public String FMID;//主键ID	是
    @XStreamAsAttribute
    public String FPID;//顾客档案主键	是 "DZSTKQ"
    @XStreamAsAttribute
    public String FSIMPLENAME;//简称
    @XStreamAsAttribute
    public String FUPDATETIME;//卡的修改时间	是
    @XStreamAsAttribute
    public String FCREATETIME;//卡的创建时间	是
    @XStreamAsAttribute
    public String FBEGINTIME;//开始时间	是
    @XStreamAsAttribute
    public String FENDTIME;//结束时间	是
    @XStreamAsAttribute
    public String FUPDATE;//发卡规则	否   文档FUPDATE_ID ?
    @XStreamAsAttribute
    public String FFMT;//卡号规则	是  "QST[*]"
    @XStreamAsAttribute
    public String FLENGTH;//号长	是  "10"
    @XStreamAsAttribute
    public String FPWD;//密码	否
    @XStreamAsAttribute
    public String FLOCKED;//是否启用密码	是  "FALSE"
    @XStreamAsAttribute
    public String FREUSED;//是否重复使用	是  "FALSE"
    @XStreamAsAttribute
    public String FSCORED;//是否积分	是  "FALSE"
    @XStreamAsAttribute
    public String FSTORED;//是否储值	是  "FALSE"
    @XStreamAsAttribute
    public String FGOLD;//是否储金	是  "FALSE"
    @XStreamAsAttribute
    public String FLEVEL;//级别	是  "1"
    @XStreamAsAttribute
    public String FTIMELIMIT;//有效天数	是  "360"
    @XStreamAsAttribute
    public String FDISCNTRATE;//折扣	是   "1"
    @XStreamAsAttribute
    public String FSCORE_UNIT;//积分倍数	是  "0"
    @XStreamAsAttribute
    public String FISCARDS;//重复发卡	是  "TRUE"
    @XStreamAsAttribute
    public String FORG_ID;//卡组织ID	否
    @XStreamAsAttribute

    public String FAID;//支付单主键	否
    @XStreamAsAttribute
    public String FCID;//顾客档案主键	是  "DZSTKQ"
    @XStreamAsAttribute
    public String FAMOUNT_SALE;//卡金额	是
    @XStreamAsAttribute
    public String FWEIGHT_BASE;//卡存金	否
    @XStreamAsAttribute
    public String FSCORE;//卡积分	否


    public ROW(String RowState, String FMID, String FPID, String FCODE, String FNCODE, String FNAME,
               String FSIMPLENAME, String FSTATE, String FUPDATETIME, String FCREATETIME, String FOPERATORID,
               String FREMARK, String FINDEX, String FBEGINTIME, String FENDTIME, String FUPDATE,
               String FFMT, String FLENGTH, String FPWD, String FLOCKED, String FREUSED, String FSCORED,
               String FSTORED, String FGOLD, String FLEVEL, String FTIMELIMIT, String FDISCNTRATE,
               String FSCORE_UNIT, String FISCARDS, String FORG_ID, String FOPERATOR) {
        this.RowState = RowState;
        this.FMID = FMID;
        this.FPID = FPID;
        this.FCODE = FCODE;
        this.FNCODE = FNCODE;
        this.FNAME = FNAME;
        this.FSIMPLENAME = FSIMPLENAME;
        this.FSTATE = FSTATE;
        this.FUPDATETIME = FUPDATETIME;
        this.FCREATETIME = FCREATETIME;
        this.FOPERATORID = FOPERATORID;
        this.FREMARK = FREMARK;
        this.FINDEX = FINDEX;
        this.FBEGINTIME = FBEGINTIME;
        this.FBEGINTIME = FBEGINTIME;
        this.FENDTIME = FENDTIME;
        this.FUPDATE = FUPDATE;
        this.FFMT = FFMT;
        this.FLENGTH = FLENGTH;
        this.FPWD = FPWD;
        this.FLOCKED = FLOCKED;
        this.FREUSED = FREUSED;
        this.FSCORED = FSCORED;
        this.FSTORED = FSTORED;
        this.FGOLD = FGOLD;
        this.FLEVEL = FLEVEL;
        this.FTIMELIMIT = FTIMELIMIT;
        this.FDISCNTRATE = FDISCNTRATE;
        this.FSCORE_UNIT = FSCORE_UNIT;
        this.FISCARDS = FISCARDS;
        this.FORG_ID = FORG_ID;
        this.FOPERATOR = FOPERATOR;
    }

    public ROW(String RowState, String FMID, String FPID, String FCODE, String FNAME,
               String FSIMPLENAME, String FSTATE, String FUPDATETIME, String FCREATETIME, String FOPERATORID,
               String FREMARK, String FAID, String FCID, String FAMOUNT_SALE,
               String FAMOUNT_BASE, String FWEIGHT_BASE, String FSCORE) {
        this.RowState = RowState;
        this.FMID = FMID;
        this.FPID = FPID;
        this.FCODE = FCODE;
        this.FNAME = FNAME;
        this.FSIMPLENAME = FSIMPLENAME;
        this.FSTATE = FSTATE;
        this.FUPDATETIME = FUPDATETIME;
        this.FCREATETIME = FCREATETIME;
        this.FOPERATORID = FOPERATORID;
        this.FREMARK = FREMARK;
        this.FAID = FAID;
        this.FCID = FCID;
        this.FAMOUNT_SALE = FAMOUNT_SALE;
        this.FAMOUNT_BASE = FAMOUNT_BASE;
        this.FWEIGHT_BASE = FWEIGHT_BASE;
        this.FSCORE = FSCORE;
    }

}
