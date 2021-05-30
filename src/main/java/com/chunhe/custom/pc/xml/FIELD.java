package com.chunhe.custom.pc.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@Data
@XStreamAlias("FIELD")
public class FIELD {
    @XStreamAsAttribute
    public String attrname;
    @XStreamAsAttribute
    public String fieldtype;
    @XStreamAsAttribute
    public String WIDTH;
}
