package com.chunhe.custom.pc.sapResponse;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public class XMLSapResult {

    @XStreamAlias("result")
    public String result;

    @XStreamAlias("tip")
    public String tip;
}
