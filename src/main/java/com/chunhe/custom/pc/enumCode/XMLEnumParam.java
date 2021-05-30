package com.chunhe.custom.pc.enumCode;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@Data
@XStreamAlias("XMLEnumParam")
public class XMLEnumParam {
    @XStreamAsAttribute
    public String ZSXLX;
    @XStreamAsAttribute
    public String ZSXLXMC;
    @XStreamAsAttribute
    public String ZSXBM;
    @XStreamAsAttribute
    public String ZSXMC;
}
