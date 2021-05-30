package com.chunhe.custom.pc.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("XMLResponse")
public class XMLResponse {
    public Boolean XMLResult;
    public XMLMessage XMLMessage;
    public XMLMetaData XMLMetaData;
}
