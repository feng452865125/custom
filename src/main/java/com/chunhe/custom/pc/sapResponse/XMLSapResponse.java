package com.chunhe.custom.pc.sapResponse;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("XMLSapResponse")
public class XMLSapResponse {

    public XMLSapResult XMLSapResult;

}
