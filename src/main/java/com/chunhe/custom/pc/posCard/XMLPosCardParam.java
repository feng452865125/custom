package com.chunhe.custom.pc.posCard;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("XMLPosCardParam")
public class XMLPosCardParam {
    @XStreamAlias("XMLPosCPCardCode")
    public XMLPosCPCardCode XMLPosCPCardCode;//卡号
    @XStreamAlias("XMLPosCPCardPassword")
    public XMLPosCPCardPassword XMLPosCPCardPassword;//密码
    @XStreamAlias("XMLPosCPCardStore")
    public XMLPosCPCardStore XMLPosCPCardStore;//使用店铺名
    @XStreamAlias("XMLPosCPCardUser")
    public XMLPosCPCardUser XMLPosCPCardUser;//使用用户名
    @XStreamAlias("XMLPosCPCardUseDate")
    public XMLPosCPCardUseDate XMLPosCPCardUseDate;//使用时间
    @XStreamAlias("XMLPosCPCardPlatform")
    public XMLPosCPCardPlatform XMLPosCPCardPlatform;//平台（天猫，京东）
    @XStreamAlias("XMLPosCPCardPlatformOrder")
    public XMLPosCPCardPlatformOrder XMLPosCPCardPlatformOrder;//平台的订单号
    @XStreamAlias("XMLPosCPCardRemark")
    public XMLPosCPCardRemark XMLPosCPCardRemark;//备注
}
