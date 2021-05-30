package com.chunhe.custom.pc.posCard;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

@Data
@XStreamAlias("XMLPosCard")
public class XMLPosCard {
    @XStreamImplicit
    public XMLPosCardParam[] XMLPosCardParam;

}
