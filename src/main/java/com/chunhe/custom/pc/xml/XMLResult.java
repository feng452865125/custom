package com.chunhe.custom.pc.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public class XMLResult {
    @XStreamAlias("True")
    public Boolean True;
}
