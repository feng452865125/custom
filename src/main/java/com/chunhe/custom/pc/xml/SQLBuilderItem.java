package com.chunhe.custom.pc.xml;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

@Data
public class SQLBuilderItem {
    public Select Select;
    public Save Save;

    @XStreamAsAttribute
    public  String SQLBuilderID;
    @XStreamAsAttribute
    public String Caption;
    @XStreamAsAttribute
    public String FSTATE;
    @XStreamAsAttribute
    public String Enabled;

    @XStreamAsAttribute
    public String TableName;

    public SQLBuilderItem SQLBuilderItem;

    public SQLBuilderItem() {

    }

    public SQLBuilderItem(String SQLBuilderID, String Caption, String FSTATE, String Enabled) {
        this.SQLBuilderID = SQLBuilderID;
        this.Caption = Caption;
        this.FSTATE = FSTATE;
        this.Enabled = Enabled;
    }
}
