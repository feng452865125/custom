package com.chunhe.custom.pc.sku;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

@Data
@XStreamAlias("XMLSku")
public class XMLSku {
    @XStreamImplicit
    public XMLSkuParam[] XMLSkuParam;

}
