package com.chunhe.custom.pc.xml;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@Data
public class XMLMessage {
    @XStreamAsAttribute
    public String FMESSAGEID;
    @XStreamAsAttribute
    public String FMESSAGEINDEX;
    @XStreamAsAttribute
    public String FREMARK;
    @XStreamAsAttribute
    public String FMESSAGEINFO;
}
