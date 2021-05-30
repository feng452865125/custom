package com.chunhe.custom.pc.xml;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

@Data
public class ROWDATA {
    @XStreamImplicit
    public ROW[] ROW;
}
