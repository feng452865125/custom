package com.chunhe.custom.pc.enumCode;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

@Data
@XStreamAlias("XMLEnum")
public class XMLEnum {
    @XStreamImplicit
    public XMLEnumParam[] XMLEnumParam;
}
