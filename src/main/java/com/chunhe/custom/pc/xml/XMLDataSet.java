package com.chunhe.custom.pc.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@Data
@XStreamAlias("XMLDataSet")
public class XMLDataSet {
    @XStreamAsAttribute
    public  String SQLBuilderID;
    @XStreamAsAttribute
    public String Caption;
    @XStreamAsAttribute
    public String FSTATE;
    @XStreamAsAttribute
    public String Enabled;

    public XMLDataTable XMLDataTable;
    public XMLDataSet XMLDataSet;
}
