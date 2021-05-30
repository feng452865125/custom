package com.chunhe.custom.pc.sku;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@Data
@XStreamAlias("XMLSkuParam")
public class XMLSkuParam {
    @XStreamAsAttribute
    public  String type;
    @XStreamAsAttribute
    public String code;
    @XStreamAsAttribute
    public String ktCode;
    @XStreamAsAttribute
    public String name;
    @XStreamAsAttribute
    public String jsCz;
    @XStreamAsAttribute
    public String zsLx;
    @XStreamAsAttribute
    public String pdLx;
    @XStreamAsAttribute
    public String jsYs;
    @XStreamAsAttribute
    public String zsYs;
    @XStreamAsAttribute
    public String zsJd;
    @XStreamAsAttribute
    public String fsCount;
    @XStreamAsAttribute
    public String sc;
    @XStreamAsAttribute
    public String yy;
    @XStreamAsAttribute
    public String fs;
    @XStreamAsAttribute
    public String kd;
    @XStreamAsAttribute
    public String ys;
    @XStreamAsAttribute
    public String kk;
    @XStreamAsAttribute
    public String price;
    @XStreamAsAttribute
    public String series;
    @XStreamAsAttribute
    public String isKt;
    @XStreamAsAttribute
    public String gs;
    @XStreamAsAttribute
    public String qghy;
    @XStreamAsAttribute
    public String kstd;
    @XStreamAsAttribute
    public String sex;
    @XStreamAsAttribute
    public String isZs;
    @XStreamAsAttribute
    public String jszl;
    @XStreamAsAttribute
    public String ktPrice;
    @XStreamAsAttribute
    public String dzPrice;
    @XStreamAsAttribute
    public String ZF1;
}
